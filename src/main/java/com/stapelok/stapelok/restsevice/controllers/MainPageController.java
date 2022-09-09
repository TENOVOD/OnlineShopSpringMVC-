package com.stapelok.stapelok.restsevice.controllers;

import com.stapelok.stapelok.models.Cart;
import com.stapelok.stapelok.models.CartProd;
import com.stapelok.stapelok.models.Products;
import com.stapelok.stapelok.repositories.CartRepository;
import com.stapelok.stapelok.repositories.ProductsRepository;
import com.stapelok.stapelok.repositories.UserRepository;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class MainPageController {

    private String prod_count_on_cart;

    private int tot_price;
    @Autowired
    private ProductsRepository productsRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/")
    private String getMainPage(Model model, @CookieValue(value = "userid",defaultValue = "newus") String usid, HttpServletResponse response, HttpServletRequest request){
        model.addAttribute("checkLogin",getLoginStatus());
        Iterable<Products> products=productsRepository.findAll();
        model.addAttribute("products",products);
        if(usid.equals("newus")){
            System.out.println("DATA BY MAXVAL"+cartRepository.maxval());
            long numuser=0;
            if(cartRepository.maxval()==null){
                numuser= 1;
            }else{
                numuser= cartRepository.maxval()+1;
            }
            int cntProdInCart=Integer.parseInt(cartRepository.countProd(String.valueOf(numuser)));
            model.addAttribute("countProd",cntProdInCart);
            Cookie cookie=new Cookie("userid",String.valueOf(numuser));
            response.addCookie(cookie);
            System.out.println("changedcookie="+numuser);
        }
        else{
            int cntProdInCart=Integer.parseInt(cartRepository.countProd(usid));
            prod_count_on_cart=String.valueOf(cntProdInCart);
            model.addAttribute("countProd",cntProdInCart);
        }
        Cookie[] cookies=request.getCookies();
        if(cookies!=null){
            System.out.println( Arrays.stream(cookies).map(c->c.getName()+"="+c.getValue()).collect(Collectors.joining(", ")));

        }
        return "index";
    }
    @GetMapping("/category/{n}")
    private String getFiltProd(@PathVariable(value = "n") int number,Model model){
        Authentication auth= SecurityContextHolder.getContext().getAuthentication();
        boolean checkLoginUser= userRepository.findByEmail(auth.getName()).isEmpty();
        model.addAttribute("checkLogin",checkLoginUser);
        Iterable<Products> products=productsRepository.findAll();
        ArrayList<Products> arrProd= (ArrayList<Products>) products;
        ArrayList<Products> resArr=new ArrayList<>();
        for(int i=0;i<arrProd.size();i++){
            Products prod=arrProd.get(i);
            if(Integer.parseInt(prod.getType())==number){
                resArr.add(prod);
            }
        }
        model.addAttribute("countProd",prod_count_on_cart);
        model.addAttribute("products",resArr);
        return "index";
    }

    @GetMapping("/add/{id}")
    private String addToCart(@PathVariable(value = "id") long id, @CookieValue(value = "userid") String usid, @RequestParam(required = false) String quantity, Model model){
        Authentication auth= SecurityContextHolder.getContext().getAuthentication();
        boolean checkLoginUser= userRepository.findByEmail(auth.getName()).isEmpty();
        model.addAttribute("checkLogin",checkLoginUser);
        Date date=new Date();
        System.out.println(quantity);
        boolean check=false;
        ArrayList<Cart> usProducts= cartRepository.arrCart(usid);
        for(int i=0;i<usProducts.size();i++){
            Cart cart=usProducts.get(i);
            if(cart.getId_prod()==id&&((quantity==null)||Integer.parseInt(cart.getQuantity())>=Integer.parseInt(quantity))) {
                check = true;
                break;
            }
            else if(cart.getId_prod()==id&&Integer.parseInt(cart.getQuantity())<Integer.parseInt(quantity)){
                cart.setQuantity(quantity);
                cartRepository.save(cart);
                check=true;
                break;
            }
        }
        if(quantity==null&&!check){
            Cart cart=new Cart(Long.parseLong(usid),id,"1",date);
            cartRepository.save(cart);
        }
        else if(quantity!=null&&!check){
            Cart cart=new Cart(Long.parseLong(usid),id,quantity,date);
            cartRepository.save(cart);
        }

        int cntProdInCart=Integer.parseInt(cartRepository.countProd(usid));
        prod_count_on_cart=String.valueOf(cntProdInCart);
        model.addAttribute("countProd",cntProdInCart);
        System.out.println("PROD ADD");
        return "redirect:/";
    }

    @GetMapping("/details/{id}")
    private String getDetails(@PathVariable(value="id") long id,Model model){
        model.addAttribute("checkLogin",getLoginStatus());
        Optional<Products> product=productsRepository.findById(id);
        ArrayList<Products> arrProd=new ArrayList<>();
        product.ifPresent(arrProd::add);
        model.addAttribute("product",arrProd);
        if(arrProd.get(0).getDescription().isEmpty()|arrProd.get(0).getDescription()==null){
            model.addAttribute("description",0);
        }else {
            model.addAttribute("description",1);
        }
        if(arrProd.get(0).getComposition().isEmpty()|arrProd.get(0).getComposition()==null){
            model.addAttribute("composition",0);
        }else {
            model.addAttribute("composition",1);
        }
        if(arrProd.get(0).getCountry().isEmpty()|arrProd.get(0).getCountry()==null){
            model.addAttribute("country",0);
        }else {
            model.addAttribute("country",1);
        }
        if(arrProd.get(0).getDensity().isEmpty()|arrProd.get(0).getDensity()==null){
            model.addAttribute("density",0);
        }else {
            model.addAttribute("density",1);
        }
        model.addAttribute("countProd",prod_count_on_cart);
        return "prod_details";
    }
    @GetMapping("/cart")
    private String getCart(@CookieValue(value = "userid") Cookie usid,Model model){
        model.addAttribute("checkLogin",getLoginStatus());
        Iterable<Products> itProd=productsRepository.findAll();
        ArrayList<Products> products= (ArrayList<Products>) itProd;
        ArrayList<CartProd> resProd=new ArrayList<>();
        ArrayList<Cart> carts=cartRepository.arrCart(usid.getValue());
        int total_price=0;
        for (Cart cart : carts) {
            for (Products products1 : products) {
                if (cart.getId_prod() == products1.getId()) {
                    if(products1.getP_w_sale().equals("0")||products1.getP_w_sale()==null){
                        int all_prod_price=Integer.parseInt(cart.getQuantity())*Integer.parseInt(products1.getPrice());
                        CartProd cartProd=new CartProd(products1.getId(),(products1.getId()+"a"),cart.getId(),products1.getTitle(),
                                String.valueOf(all_prod_price),cart.getQuantity(),products1.getImage1());
                        resProd.add(cartProd);
                        total_price+=all_prod_price;
                    }else if(!products1.getP_w_sale().equals("0") ||products1.getP_w_sale()!=null){
                        int all_prod_price=Integer.parseInt(cart.getQuantity())*Integer.parseInt(products1.getP_w_sale());
                        CartProd cartProd=new CartProd(products1.getId(),(products1.getId()+"a"),cart.getId(),products1.getTitle(),
                                String.valueOf(all_prod_price),cart.getQuantity(),products1.getImage1());
                        resProd.add(cartProd);
                        total_price+=all_prod_price;
                    }

                }
            }
        }
        tot_price=total_price;
        model.addAttribute("countProd",prod_count_on_cart);
        model.addAttribute("products",resProd);
        model.addAttribute("total_price",total_price);
        return "cart";
    }
    @GetMapping("/change-quantity/{id}")
    private String changeQuantity(@PathVariable(value="id") long id, @CookieValue(value="userid") Cookie usid,Model model, @RequestParam String quantity
            ,@RequestParam(name = "sign") String sign){
        //System.out.println("Sign "+sign+" Sign eq +"+sign.equals("+"));
        ArrayList<Cart> carts=cartRepository.arrCart(usid.getValue());
        for(int i=0;i<carts.size();i++){
            Cart cart=carts.get(i);
            if(cart.getId_prod()==id){
                System.out.println(quantity);
                int quant=Integer.parseInt(quantity);
                if(quant>0){
                    if(sign.equals("+")){
                        quant=quant+1;
                    }else if(sign.equals("-")&&quant>1){
                        quant=quant-1;
                    }
                    cart.setQuantity(String.valueOf(quant));
                    cartRepository.save(cart);

                } else if (quant<0) {
                    quant=-quant;
                    if(sign.equals("+")){
                        quant=quant+1;
                    }else if(sign.equals("-")&&quant>1){
                        quant=quant-1;
                    }
                    cart.setQuantity(String.valueOf(quant));
                    cartRepository.save(cart);
                }
            }
        }
        return "redirect:/cart";
    }
    @GetMapping("/image/{id}")
    private void showFirstImage(@PathVariable long id, HttpServletResponse response) throws IOException {
        response.setContentType("image/jpg");
        Optional<Products> products=productsRepository.findById(id);
        if(products.get().getImage1()!=null){
            InputStream is=new ByteArrayInputStream(products.get().getImage1());
            IOUtils.copy(is,response.getOutputStream());
        }
    }

    @GetMapping("/image2/{id}")
    private void showSecondImage(@PathVariable long id, HttpServletResponse response) throws IOException{
        response.setContentType("image/jpg");
        Optional<Products> products=productsRepository.findById(id);
        if(products.get().getImage2()!=null){
            InputStream is=new ByteArrayInputStream(products.get().getImage2());
            IOUtils.copy(is,response.getOutputStream());

        }
    }
    @GetMapping("/image3/{id}")
    private void showThirdImage(@PathVariable long id, HttpServletResponse response) throws IOException{
        response.setContentType("image/jpg");
        Optional<Products> products=productsRepository.findById(id);
        if(products.get().getImage3()!=null){
            InputStream is=new ByteArrayInputStream(products.get().getImage3());
            IOUtils.copy(is,response.getOutputStream());
        }
    }
    @GetMapping("/product-delete/{id}")
    private String deleteFromCart(@PathVariable(value="id") long id,@CookieValue(value="userid") Cookie usid, Model model){
        ArrayList<Cart> carts=cartRepository.arrCart(usid.getValue());
        for(int i=0;i<carts.size();i++){
            if(carts.get(i).getId_prod()==id){
                cartRepository.delete(carts.get(i));
                break;
            }
        }
        return "redirect:/cart";
    }

    boolean getLoginStatus(){
        Authentication auth= SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findByEmail(auth.getName()).isEmpty();
    }

}