package com.example.bai6.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProductController {

    @GetMapping("/products")
    public String products(Model model) {

        model.addAttribute("products",
                new String[]{"Laptop","Phone","Keyboard"});

        return "products";
    }

    @GetMapping("/products/add")
    public String addProduct(){
        return "add-product";
    }

}