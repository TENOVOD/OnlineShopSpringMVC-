package com.stapelok.stapelok.restsevice.controllers;

import com.stapelok.stapelok.models.Products;
import com.stapelok.stapelok.repositories.CartRepository;
import com.stapelok.stapelok.repositories.ProductsRepository;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class MainPageController {

    @Autowired
    private ProductsRepository productsRepository;

    @Autowired
    private CartRepository cartRepository;

    @GetMapping("/")
    private String getMainPage(Model model, @CookieValue(value = "userid",defaultValue = "newus") String usid, HttpServletResponse response, HttpServletRequest request){
        Iterable<Products> products=productsRepository.findAll();
        model.addAttribute("products",products);
        System.out.println("usid="+usid);
        if(usid.equals("newus")){

            int numuser= cartRepository.maxval()+1;
            Cookie cookie=new Cookie("userid",String.valueOf(numuser));
            response.addCookie(cookie);
            System.out.println("changedcookie="+numuser);
        }
        Cookie[] cookies=request.getCookies();
        if(cookies!=null){
            System.out.println( Arrays.stream(cookies).map(c->c.getName()+"="+c.getValue()).collect(Collectors.joining(", ")));

        }
        return "index";
    }
    @GetMapping("/category/{n}")
    private String getFiltProd(@PathVariable(value = "n") int number,Model model){
        Iterable<Products> products=productsRepository.findAll();
        ArrayList<Products> arrProd= (ArrayList<Products>) products;
        ArrayList<Products> resArr=new ArrayList<>();
        for(int i=0;i<arrProd.size();i++){
            Products prod=arrProd.get(i);
            if(Integer.parseInt(prod.getType())==number){
                resArr.add(prod);
            }
        }
        model.addAttribute("products",resArr);
        return "index";
    }

    @GetMapping("/details/{id}")
    private String getDetails(@PathVariable(value="id") long id,Model model){
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

        return "prod_details";
    }
    @GetMapping("/cart")
    private String getCart(Model model){
        return "cart";
    }

}
