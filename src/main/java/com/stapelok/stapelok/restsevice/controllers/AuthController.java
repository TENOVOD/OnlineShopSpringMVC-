package com.stapelok.stapelok.restsevice.controllers;

import com.stapelok.stapelok.models.Role;
import com.stapelok.stapelok.models.Status;
import com.stapelok.stapelok.models.User;
import com.stapelok.stapelok.models.Utility;
import com.stapelok.stapelok.repositories.CartRepository;
import com.stapelok.stapelok.repositories.UserRepository;
import com.stapelok.stapelok.service.UserServices;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    JavaMailSender mailSender;
    @Autowired
    UserServices userServices;
    @Autowired
    private  UserRepository userRepository;
    private String prod_count_on_cart;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @GetMapping("/login")
    public String getLoginPage(@CookieValue(value = "userid", defaultValue = "newus") String usid,Model model) {
        int cntProdInCart = Integer.parseInt(cartRepository.countProd(usid));
        prod_count_on_cart = String.valueOf(cntProdInCart);
        model.addAttribute("countProd", cntProdInCart);
        return "login";
    }

    @GetMapping("/success")
    public String getSuccessPage() {
        return "index";
    }

    @PostMapping("/logout")
    public  String getLogout(){
        return "redirect:";
    }

    @GetMapping("/registration")
    public String getRegistrationPage(Model model){
        model.addAttribute("countProd", prod_count_on_cart);
        return "registration_page";
    }

    @PostMapping("/registration2")
    protected String getRegistration(@RequestParam String first_name, @RequestParam String last_name,
                                     @RequestParam String surname, @RequestParam String email,
                                     @RequestParam String phone_number, @RequestParam String password,
                                     @RequestParam String repeat_password, Model model){
        if(!(first_name.isEmpty())){
            model.addAttribute("first_name",first_name);
        }if(!(last_name.isEmpty())){
            model.addAttribute("last_name",last_name);
        }if(!(surname.isEmpty())){
            model.addAttribute("surname",surname);
        }if(!(email.isEmpty())){
            model.addAttribute("email",email);
        }if(!(phone_number.isEmpty())){
            model.addAttribute("phone_number",phone_number);
        }

        if(!(userRepository.findByEmail(email).isEmpty())) {
            model.addAttribute("simple_email","1");
            System.out.println("email"+userRepository.findByEmail(email).isEmpty());
            return getRegistrationPage(model);
        }else if(password.isEmpty()|repeat_password.isEmpty()){
            model.addAttribute("password_check","1");
            System.out.println(2);
            return getRegistrationPage(model);
        }else if(first_name.isEmpty()|last_name.isEmpty()|phone_number.isEmpty()|email.isEmpty()){
            model.addAttribute("check_empty","1");
            System.out.println(3);
            return getRegistrationPage(model);
        }else if((!(first_name.isEmpty()))&&(!(last_name.isEmpty()))&&(!(email.isEmpty()))
                &&(!(phone_number.isEmpty()))&&(!(password.isEmpty()))&&(!(repeat_password.isEmpty()))
        &&password.equals(repeat_password)){
                User user=new User();
                user.setFirst_name(first_name);
                user.setLast_name(last_name);
                user.setEmail(email);
                user.setRole(Role.USER);
                user.setPassword(passwordEncoder.encode(password));
                if(surname.isEmpty()){
                    user.setMiddle_name("none");
                }else{
                    user.setMiddle_name(surname);
                }
                user.setPhoneNumber(phone_number);
                user.setStatus(Status.ACTIVE);
            System.out.println(user);
                userRepository.save(user);
                return "redirect:/auth/login";
        }
        return getRegistrationPage(model);
    }

    @GetMapping("/forgot-password")
    public String getForgotPassPage(Model model){
        return "/forgot_password";
    }
    @PostMapping("/forgot-password")
    private String sendResetPasswordList(Model model, HttpServletRequest request){
        String email= request.getParameter("email");
        String token= RandomString.make(30);

        try {
            userServices.updateResetPasswordToken(email,token);
            String resetPasswordLink= Utility.getSiteURL(request)+"/reset_password?token="+token;
            sendEmail(email,resetPasswordLink);
            model.addAttribute("message","Листа було відпрвленно. Перевірте вашу пошту!");
        } catch (MessagingException e) {
            model.addAttribute("message","На дану пошту не знайдено");
            throw new RuntimeException(e);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

        return getForgotPassPage(model);
    }

    public void sendEmail(String recipientEmail, String link)
            throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom("stapelok.shop@protonmail.com", "Stapelok Shop");
        helper.setTo(recipientEmail);

        String subject = "Here's the link to reset your password";

        String content = "<p>Hello,</p>"
                + "<p>You have requested to reset your password.</p>"
                + "<p>Click the link below to change your password:</p>"
                + "<p><a href=\"" + link + "\">Change my password</a></p>"
                + "<br>"
                + "<p>Ignore this email if you do remember your password, "
                + "or you have not made the request.</p>";

        helper.setSubject(subject);

        helper.setText(content, true);

        mailSender.send(message);
    }


}
