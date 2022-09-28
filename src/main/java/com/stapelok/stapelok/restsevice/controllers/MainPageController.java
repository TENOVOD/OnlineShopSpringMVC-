package com.stapelok.stapelok.restsevice.controllers;

import com.stapelok.stapelok.models.*;
import com.stapelok.stapelok.repositories.*;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class MainPageController {

    private String prod_count_on_cart;

    private int tot_price;

    @Autowired
    private PreOrderRepository preOrderRepository;
    @Autowired
    private ProductsRepository productsRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private OrderRepository orderRepository;

    @GetMapping("/")
    private String getMainPage(Model model, @CookieValue(value = "userid", defaultValue = "newus") String usid, HttpServletResponse response, HttpServletRequest request) {
        model.addAttribute("checkLogin", getLoginStatus());
        Iterable<Products> iterableProducts = productsRepository.findAll();
        ArrayList<Products> productsArrayList= (ArrayList<Products>) iterableProducts;
        for(int i=0; i<productsArrayList.size();i++){
           Products product=productsArrayList.get(i);
           if(Double.parseDouble(product.getStocks()) <10&&Double.parseDouble(product.getStocks())>1){
               product.setStock_status("Закінчується");
               productsRepository.save(product);
           } else if (Double.parseDouble(product.getStocks())<1) {
               product.setStock_status("Закінчився");
               productsRepository.save(product);
           }
        }
        model.addAttribute("products", productsArrayList);
        if (usid.equals("newus")) {
            System.out.println("DATA BY MAXVAL" + cartRepository.maxval());
            long numuser = 0;
            if (cartRepository.maxval() == null) {
                numuser = 1;
            } else {
                numuser = cartRepository.maxval() + 1;
            }
            int cntProdInCart = Integer.parseInt(cartRepository.countProd(String.valueOf(numuser)));
            model.addAttribute(cntProdInCart);
            model.addAttribute("countProd", cntProdInCart);
            Cookie cookie = new Cookie("userid", String.valueOf(numuser));
            response.addCookie(cookie);
            System.out.println("changedcookie=" + numuser);
        } else {
            int cntProdInCart = Integer.parseInt(cartRepository.countProd(usid));
            prod_count_on_cart = String.valueOf(cntProdInCart);
            model.addAttribute("countProd", cntProdInCart);
        }
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            System.out.println(Arrays.stream(cookies).map(c -> c.getName() + "=" + c.getValue()).collect(Collectors.joining(", ")));

        }
        return "index";
    }

    @GetMapping("/category/{n}")
    private String getFiltProd(@PathVariable(value = "n") int number, Model model) {
        model.addAttribute("checkLogin", getLoginStatus());
        Iterable<Products> products = productsRepository.findAll();
        ArrayList<Products> arrProd = (ArrayList<Products>) products;
        ArrayList<Products> resArr = new ArrayList<>();
        for (int i = 0; i < arrProd.size(); i++) {
            Products prod = arrProd.get(i);
            if (Integer.parseInt(prod.getType()) == number) {
                resArr.add(prod);
            }
        }
        model.addAttribute("countProd", prod_count_on_cart);
        model.addAttribute("products", resArr);
        return "index";
    }

    @GetMapping("/add/{id}")
    private String addToCart(@PathVariable(value = "id") long id, @CookieValue(value = "userid") String usid, @RequestParam(required = false) String quantity, Model model) {
        model.addAttribute("checkLogin", getLoginStatus());
        Date date = new Date();
        System.out.println(quantity);
        boolean check = false;
        ArrayList<Cart> usProducts = cartRepository.arrCart(usid);
        for (int i = 0; i < usProducts.size(); i++) {
            Cart cart = usProducts.get(i);
            if (cart.getId_prod() == id && ((quantity == null) || Integer.parseInt(cart.getQuantity()) >= Integer.parseInt(quantity))) {
                check = true;
                break;
            } else if (cart.getId_prod() == id && Integer.parseInt(cart.getQuantity()) < Integer.parseInt(quantity)) {
                cart.setQuantity(quantity);
                cartRepository.save(cart);
                check = true;
                break;
            }
        }
        if (quantity == null && !check) {
            Cart cart = new Cart(Long.parseLong(usid), id, "1", date);
            cartRepository.save(cart);
        } else if (quantity != null && !check) {
            Cart cart = new Cart(Long.parseLong(usid), id, quantity, date);
            cartRepository.save(cart);
        }

        int cntProdInCart = Integer.parseInt(cartRepository.countProd(usid));
        prod_count_on_cart = String.valueOf(cntProdInCart);
        model.addAttribute("countProd", cntProdInCart);
        System.out.println("PROD ADD");
        return "redirect:/";
    }

    @GetMapping("/details/{id}")
    private String getDetails(@PathVariable(value = "id") long id, Model model) {
        model.addAttribute("checkLogin", getLoginStatus());
        Optional<Products> product = productsRepository.findById(id);
        ArrayList<Products> arrProd = new ArrayList<>();
        product.ifPresent(arrProd::add);
        model.addAttribute("product", arrProd);
        if (arrProd.get(0).getDescription().isEmpty() | arrProd.get(0).getDescription() == null) {
            model.addAttribute("description", 0);
        } else {
            model.addAttribute("description", 1);
        }
        if (arrProd.get(0).getComposition().isEmpty() | arrProd.get(0).getComposition() == null) {
            model.addAttribute("composition", 0);
        } else {
            model.addAttribute("composition", 1);
        }
        if (arrProd.get(0).getCountry().isEmpty() | arrProd.get(0).getCountry() == null) {
            model.addAttribute("country", 0);
        } else {
            model.addAttribute("country", 1);
        }
        if (arrProd.get(0).getDensity().isEmpty() | arrProd.get(0).getDensity() == null) {
            model.addAttribute("density", 0);
        } else {
            model.addAttribute("density", 1);
        }
        model.addAttribute("countProd", prod_count_on_cart);
        return "prod_details";
    }

    @GetMapping("/cart")
    private String getCart(@CookieValue(value = "userid") Cookie usid, Model model) {
        model.addAttribute("checkLogin", getLoginStatus());
        Iterable<Products> itProd = productsRepository.findAll();
        ArrayList<Products> products = (ArrayList<Products>) itProd;
        ArrayList<CartProd> resProd = new ArrayList<>();
        ArrayList<Cart> carts = cartRepository.arrCart(usid.getValue());
        int total_price = 0;
        for (Cart cart : carts) {
            for (Products products1 : products) {
                if (cart.getId_prod() == products1.getId()) {
                    if (products1.getP_w_sale().equals("0") || products1.getP_w_sale() == null) {
                        int all_prod_price = Integer.parseInt(cart.getQuantity()) * Integer.parseInt(products1.getPrice());
                        String script="0";
                        if(model.containsAttribute(products1.getId()+"")){
                            script="1";
                        }
                        CartProd cartProd = new CartProd(products1.getId(), script,Integer.parseInt(products1.getStocks())*10 ,cart.getId(), products1.getTitle(),
                                String.valueOf(all_prod_price), cart.getQuantity(), products1.getImage1());
                        resProd.add(cartProd);
                        total_price += all_prod_price;
                    } else if (!products1.getP_w_sale().equals("0") || products1.getP_w_sale() != null) {
                        String script="0";
                        if(model.containsAttribute(products1.getId()+"")){
                            script="1";
                        }
                        int all_prod_price = Integer.parseInt(cart.getQuantity()) * Integer.parseInt(products1.getP_w_sale());
                        CartProd cartProd = new CartProd(products1.getId(), script,Integer.parseInt(products1.getStocks())*10  ,cart.getId(), products1.getTitle(),
                                String.valueOf(all_prod_price), cart.getQuantity(), products1.getImage1());
                        resProd.add(cartProd);
                        total_price += all_prod_price;
                    }

                }
            }
        }
        tot_price = total_price;
        model.addAttribute("countProd", prod_count_on_cart);
        if(resProd.isEmpty()){
            resProd=null;
        }
        model.addAttribute("products", resProd);
        model.addAttribute("total_price", total_price);
        return "cart";
    }

    @GetMapping("/change-quantity/{id}")
    private String changeQuantity(@PathVariable(value = "id") long id, @CookieValue(value = "userid") Cookie usid, Model model, @RequestParam String quantity
            , @RequestParam(name = "sign") String sign) {
        model.addAttribute("checkLogin", getLoginStatus());
        System.out.println("Sign " + sign + " Sign eq +" + sign.equals("+"));
        ArrayList<Cart> carts = cartRepository.arrCart(usid.getValue());
        for (int i = 0; i < carts.size(); i++) {
            Cart cart = carts.get(i);
            if (cart.getId_prod() == id) {
                Optional<Products> products=productsRepository.findById(cart.getId_prod());
                int quant = Integer.parseInt(quantity);
                if (quant > 0) {
                    if (sign.equals("+")) {
                        if(((Integer.parseInt(products.get().getStocks()))-(quant+1))>=0){
                            quant = quant + 1;
                        }else{
                            model.addAttribute(""+products.get().getId(),1);
                            return getCart(usid,model);
                        }
                    } else if (sign.equals("-") && quant > 1) {
                        quant = quant - 1;
                    }
                    cart.setQuantity(String.valueOf(quant));
                    cartRepository.save(cart);

                } else if (quant < 0) {
                    quant = -quant;
                    if (sign.equals("+")) {
                        quant = quant + 1;
                    } else if (sign.equals("-") && quant > 1) {
                        quant = quant - 1;
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
        Optional<Products> products = productsRepository.findById(id);
        if (products.get().getImage1() != null) {
            InputStream is = new ByteArrayInputStream(products.get().getImage1());
            IOUtils.copy(is, response.getOutputStream());
        }
    }

    @GetMapping("/image2/{id}")
    private void showSecondImage(@PathVariable long id, HttpServletResponse response) throws IOException {
        response.setContentType("image/jpg");
        Optional<Products> products = productsRepository.findById(id);
        if (products.get().getImage2() != null) {
            InputStream is = new ByteArrayInputStream(products.get().getImage2());
            IOUtils.copy(is, response.getOutputStream());

        }
    }

    @GetMapping("/image3/{id}")
    private void showThirdImage(@PathVariable long id, HttpServletResponse response) throws IOException {
        response.setContentType("image/jpg");
        Optional<Products> products = productsRepository.findById(id);
        if (products.get().getImage3() != null) {
            InputStream is = new ByteArrayInputStream(products.get().getImage3());
            IOUtils.copy(is, response.getOutputStream());
        }
    }

    @GetMapping("/product-delete/{id}")
    private String deleteFromCart(@PathVariable(value = "id") long id, @CookieValue(value = "userid") Cookie usid, Model model) {
        ArrayList<Cart> carts = cartRepository.arrCart(usid.getValue());
        for (int i = 0; i < carts.size(); i++) {
            if (carts.get(i).getId_prod() == id) {
                cartRepository.delete(carts.get(i));
                break;
            }
        }
        return "redirect:/cart";
    }

    boolean getLoginStatus() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findByEmail(auth.getName()).isEmpty();
    }

    @GetMapping("/order")
    private String getOrderPage(Model model, @CookieValue(value = "userid") Cookie usid) {
        model.addAttribute("checkLogin", getLoginStatus());
        Iterable<Products> itProd = productsRepository.findAll();
        ArrayList<Products> products = (ArrayList<Products>) itProd;
        ArrayList<CartProd> resProd = new ArrayList<>();
        ArrayList<Cart> carts = cartRepository.arrCart(usid.getValue());
        int total_price = 0;
        for (Cart cart : carts) {
            for (Products products1 : products) {
                if (cart.getId_prod() == products1.getId()) {
                    if(Integer.parseInt(cart.getQuantity())<=Integer.valueOf(products1.getStocks())){
                        if (products1.getP_w_sale().equals("0") || products1.getP_w_sale() == null) {
                            int all_prod_price = Integer.parseInt(cart.getQuantity()) * Integer.parseInt(products1.getPrice());
                            CartProd cartProd = new CartProd(products1.getId(),"a",Integer.parseInt(products1.getStocks()), cart.getId(), products1.getTitle(),
                                    String.valueOf(all_prod_price), cart.getQuantity(), products1.getImage1());
                            resProd.add(cartProd);
                            total_price += all_prod_price;
                        } else if (!products1.getP_w_sale().equals("0") || products1.getP_w_sale() != null) {
                            int all_prod_price = Integer.parseInt(cart.getQuantity()) * Integer.parseInt(products1.getP_w_sale());
                            CartProd cartProd = new CartProd(products1.getId(),"a",Integer.parseInt(products1.getStocks())  , cart.getId(), products1.getTitle(),
                                    String.valueOf(all_prod_price), cart.getQuantity(), products1.getImage1());
                            resProd.add(cartProd);
                            total_price += all_prod_price;
                        }
                    }else if(Integer.parseInt(cart.getQuantity())>Integer.parseInt(products1.getStocks())){
                        cart.setQuantity(String.valueOf(Integer.parseInt(products1.getStocks())));
                        cartRepository.save(cart);
                    }

                }
            }
        }
        tot_price = total_price;
        model.addAttribute("countProd", prod_count_on_cart);
        model.addAttribute("products", resProd);
        model.addAttribute("total_price", total_price);
        if (!getLoginStatus()) {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            Optional<User> user = userRepository.findByEmail(auth.getName());
            model.addAttribute("first_name", user.get().getFirst_name());
            model.addAttribute("last_name", user.get().getLast_name());
            model.addAttribute("surname", user.get().getMiddle_name());
            model.addAttribute("phone_number", user.get().getPhoneNumber());
        }
        return "placing_order";
    }



    @PostMapping("/order")
    public String createOrder(@RequestParam String first_name, @RequestParam String last_name,
                              @RequestParam String surname,@RequestParam String phone_num,
                              @RequestParam(name = "delivery") String delivery, @RequestParam String address,
                              @RequestParam String comment, Model model,@CookieValue(value = "userid") Cookie usid,
                              HttpServletResponse response, HttpServletRequest request){
        if(first_name.isEmpty()|last_name.isEmpty()|surname.isEmpty()|phone_num.isEmpty()|
            address.isEmpty()|phone_num.isEmpty()){
            model.addAttribute("check_all_fields","1");
           return getOrderPage(model,usid);
        }else{
            String user_ac_id;
            Authentication auth=SecurityContextHolder.getContext().getAuthentication();
            if(userRepository.findByEmail(auth.getName()).isEmpty()){
                user_ac_id="";
            }else{
                user_ac_id=auth.getName();
            }

            ArrayList<Cart> carts = cartRepository.arrCart(usid.getValue());
            for (int i=0; i<carts.size();i++){
                Optional<Products> products=productsRepository.findById(carts.get(i).getId_prod());
                double result=0;
                result=Double.parseDouble(products.get().getStocks())-Double.parseDouble(carts.get(i).getQuantity());
                if(result<0){
                    model.addAttribute("check_amount",1);
                    return  getOrderPage(model,usid);
                }
            }
            Date date=new Date();
            String save_comment;
            if(comment.isEmpty()){
                save_comment=" ";
            }else{
                save_comment=comment;
            }
            Order order=new Order(Long.parseLong(usid.getValue()),user_ac_id,String.valueOf(tot_price),first_name,last_name,surname,address,phone_num,save_comment,"Очікуйте дзвінка",date,delivery);
            order=orderRepository.save(order);
            cartRepository.deleteAfterOrder(usid.getValue());
            for (int i=0;i<carts.size();i++){
                Optional<Products> products=productsRepository.findById(carts.get(i).getId_prod());
                int sum=Integer.parseInt(products.get().getStocks())-Integer.parseInt(carts.get(i).getQuantity());
                products.get().setStocks(String.valueOf(sum));
                if(!(user_ac_id.isEmpty())){
                    if(products.get().getP_w_sale().equals("0")){
                        PreOrder preOrder=new PreOrder(user_ac_id,order.getId(),carts.get(i).getId_prod(),
                                carts.get(i).getQuantity(),products.get().getPrice());
                        preOrderRepository.save(preOrder);
                    }else {
                        PreOrder preOrder=new PreOrder(user_ac_id,order.getId(),carts.get(i).getId_prod(),
                                carts.get(i).getQuantity(),products.get().getP_w_sale());
                        preOrderRepository.save(preOrder);
                    }

                }else{
                    if(products.get().getP_w_sale().equals("0")){
                        PreOrder preOrder=new PreOrder("",order.getId(),carts.get(i).getId_prod(),
                                carts.get(i).getQuantity(),products.get().getPrice());
                        preOrderRepository.save(preOrder);
                    }else {
                        PreOrder preOrder=new PreOrder("",order.getId(),carts.get(i).getId_prod(),
                                carts.get(i).getQuantity(),products.get().getP_w_sale());
                        preOrderRepository.save(preOrder);
                    }
                }
                productsRepository.save(products.get());
            }
            model.addAttribute("user_name",first_name);
            model.addAttribute("orders_num",order.getId());
            model.addAttribute("checkLogin", getLoginStatus());
        }
        return getSuccessOrderPage(model);
    }

    @GetMapping("/success_oder")
    public String getSuccessOrderPage(Model model){
        return "thanks_for_order";
    }


}
