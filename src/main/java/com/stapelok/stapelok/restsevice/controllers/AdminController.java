package com.stapelok.stapelok.restsevice.controllers;

import com.stapelok.stapelok.models.Products;
import com.stapelok.stapelok.repositories.ImagesRepository;
import com.stapelok.stapelok.repositories.ProductsRepository;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.swing.text.html.parser.Entity;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

@Controller
public class AdminController {

    //connect models repo and we can use db methods
    @Autowired
    private ProductsRepository productsRepository;

    @Autowired
    private ImagesRepository imagesRepository;
    @GetMapping("/admin_main_page")
    public String getAdminMainPage(Model model){
        Iterable<Products> products=productsRepository.findAll();
        model.addAttribute("products",products);
        return "/admin_main_page";
    }

    @GetMapping("/post-edit/{id}")
    private String getEditPostPage(Model model,@PathVariable(value="id") long id){
        Optional<Products> products=productsRepository.findById(id);
        ArrayList<Products> arrProd=new ArrayList<>();
        products.ifPresent(arrProd::add);
        model.addAttribute("product",arrProd);
        return "admin_edit_prod";
    }

    //main page
    @GetMapping("/add_prod")
    public  String getAddProductPage(Model model){
        return  "admin_add_prod";
    }

    //form func to add new
    @PostMapping("/addprod")
    public String addProduct(@RequestParam("p_type") String p_type, @RequestParam String title, @RequestParam String description , @RequestParam String price,
                             @RequestParam String stocks, @RequestParam String country,
                             @RequestParam String composition, @RequestParam String width, @RequestParam String density,
                             @RequestParam("file1") MultipartFile file1, @RequestParam("file2") MultipartFile file2, @RequestParam("file3") MultipartFile file3) throws IOException {

        byte[] photo1=null;
        byte[] photo2=null;
        byte[] photo3=null;
        if(!file1.isEmpty()){
            photo1= file1.getBytes();
        }
        if(!file2.isEmpty()){
            photo2= file2.getBytes();
        }
        if(!file3.isEmpty()){
            photo3= file3.getBytes();
        }

        Date date=new Date();
        Products products=new Products(p_type,title,description,price,"0",stocks,"В наявності",date,country,composition,width,density,photo1,photo2,photo3);
        productsRepository.save(products);

        return  "redirect:/add_prod"; //поміняти на силку до показу товару з кнопкою редагування(admin page)

    }
    @PostMapping("/post-edit/{id}")
    private String editProduct(@PathVariable(value="id") long id, @RequestParam("p_type") String p_type, @RequestParam String title, @RequestParam String description ,
                               @RequestParam String price, @RequestParam String stocks, @RequestParam String country,@RequestParam String p_w_sale,
                               @RequestParam String composition, @RequestParam String width, @RequestParam String density,
                               @RequestParam("file1") MultipartFile file1, @RequestParam("file2") MultipartFile file2, @RequestParam("file3") MultipartFile file3) throws IOException {
        Products product=productsRepository.findById(id).orElseThrow();
        byte[] photo1=null;
        byte[] photo2=null;
        byte[] photo3=null;
        if(!file1.isEmpty()){
            photo1= file1.getBytes();
            product.setImage1(photo1);
            System.out.println("file1 empty check"+file3.isEmpty());
        }
        if(!file2.isEmpty()){
            photo2= file2.getBytes();
            product.setImage2(photo2);
            System.out.println("file2 empty check"+file3.isEmpty());
        }
        if(!file3.isEmpty()){
            photo3= file3.getBytes();
            product.setImage3(photo3);
            System.out.println("file3 empty check"+file3.isEmpty());
        }
        product.setDescription(description);
        product.setWidth(width);
        product.setStocks(stocks);
        product.setComposition(composition);
        product.setCountry(country);
        product.setDensity(density);
        product.setPrice(price);
        product.setP_w_sale(p_w_sale);
        product.setTitle(title);
        product.setType(p_type);
        productsRepository.save(product);

        return "redirect:/admin_main_page";
    }
    @GetMapping("/image/{id}")
    private void showFirstImage(@PathVariable long id, HttpServletResponse response) throws IOException{
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


}
