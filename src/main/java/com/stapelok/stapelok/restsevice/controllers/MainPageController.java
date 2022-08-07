package com.stapelok.stapelok.restsevice.controllers;

import com.stapelok.stapelok.models.Products;
import com.stapelok.stapelok.repositories.ProductsRepository;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.Optional;

@Controller
public class MainPageController {

    @Autowired
    private ProductsRepository productsRepository;

    @GetMapping("/")
    private String getMainPage(Model model){
        Iterable<Products> products=productsRepository.findAll();
        model.addAttribute("products",products);
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
