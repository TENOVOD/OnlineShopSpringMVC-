package com.stapelok.stapelok.restsevice.controllers;

import com.stapelok.stapelok.models.Order;
import com.stapelok.stapelok.models.PreOrder;
import com.stapelok.stapelok.models.User;
import com.stapelok.stapelok.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

@Controller
@RequestMapping("/user/")
public class UserController {

    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductsRepository productsRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private PreOrderRepository preOrderRepository;
    @Autowired
    private OrderRepository orderRepository;
    @GetMapping("/person")
    public String getPersonOffice(Model model, @CookieValue(value = "userid", defaultValue = "newus") String usid){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("checkLogin",getLoginStatus());
        Optional<User> user=userRepository.findByEmail(auth.getName());
        String fullName="Вітаємо, "+user.get().getFirst_name()+" "+user.get().getLast_name()+"!";
        model.addAttribute("fullName",fullName);
        model.addAttribute("user",user);
        ArrayList<Order> arrayList=orderRepository.getOrdersByUserEmail(auth.getName());
        Collections.reverse(arrayList);
        if(arrayList.isEmpty()){
            arrayList=null;
        }
        model.addAttribute("orders",arrayList);
        ArrayList<PreOrder> arrayFilterEmailPreOrder=preOrderRepository.getPreOrdersByUserEmail(auth.getName());
        int cntProdInCart = Integer.parseInt(cartRepository.countProd(usid));
        model.addAttribute("countProd", cntProdInCart);
        model.addAttribute("products",arrayFilterEmailPreOrder);
        return "personal_office";
    }
    boolean getLoginStatus() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findByEmail(auth.getName()).isEmpty();
    }

    @GetMapping("/edit-profile")
    public  String getEditProfilePage(Model model){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<User> opUser=userRepository.findByEmail(auth.getName());
        ArrayList<User> arrUser=new ArrayList<>();
        opUser.ifPresent(arrUser::add);
        User user=arrUser.get(0);
        model.addAttribute("user",user);
        return "/edit_profile";
    }


    @PostMapping("/change-info")
    private String setNewInfo(Model model, @RequestParam String first_name,@RequestParam String last_name,
                              @RequestParam String middle_name, @RequestParam String phone_num){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<User> opUser=userRepository.findByEmail(auth.getName());
        ArrayList<User> arrUser=new ArrayList<>();
        opUser.ifPresent(arrUser::add);
        User user=arrUser.get(0);
        user.setMiddle_name(middle_name);
        user.setLast_name(last_name);
        user.setFirst_name(first_name);
        user.setPhoneNumber(phone_num);
        model.addAttribute("success_edit","*дані успішно зміненні");
        userRepository.save(user);
        return getEditProfilePage(model);
    }

    @PostMapping("/change-password")
    private String setNewPassword(Model model,@RequestParam String old_password, @RequestParam String new_password,
                                  @RequestParam String repeat_password){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Optional<User> opUser=userRepository.findByEmail(auth.getName());
        ArrayList<User> arrUser=new ArrayList<>();
        opUser.ifPresent(arrUser::add);
        User user=arrUser.get(0);
        BCryptPasswordEncoder bcrypt=new BCryptPasswordEncoder();
        boolean checkOldPassword=bcrypt.matches(old_password,user.getPassword());
        if(checkOldPassword){
            if(new_password.equals(repeat_password)&&!(new_password.isEmpty())){
                user.setPassword(passwordEncoder.encode(new_password));
                userRepository.save(user);
                model.addAttribute("success","1");
            }else{
                model.addAttribute("incorrect_repeat_password","1");
            }
        }else {
            model.addAttribute("incorrect_old_password","1");
        }

        return getEditProfilePage(model);
    }

}
