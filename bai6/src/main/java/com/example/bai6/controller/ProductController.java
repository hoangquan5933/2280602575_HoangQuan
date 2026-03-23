package com.example.bai6.controller;

import com.example.bai6.entity.*;
import com.example.bai6.model.CartItem;
import com.example.bai6.repository.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.example.bai6.entity.Order;
import com.example.bai6.entity.OrderDetail;
import java.util.*;

@Controller
public class ProductController {

    @Autowired
    private ProductRepository productRepo;

    @Autowired
    private CategoryRepository categoryRepo;

    @Autowired
    private OrderRepository orderRepo;

    @Autowired
    private OrderDetailRepository orderDetailRepo;

    // ================= LIST PRODUCT =================
    @GetMapping("/products")
    public String listProducts(Model model,
                               @RequestParam(required = false) String keyword,
                               @RequestParam(required = false) Long categoryId,
                               @RequestParam(required = false) String sort,
                               @RequestParam(defaultValue = "0") int page) {

        Pageable pageable;

        if ("asc".equals(sort)) {
            pageable = PageRequest.of(page, 5, Sort.by("price").ascending());
        } else if ("desc".equals(sort)) {
            pageable = PageRequest.of(page, 5, Sort.by("price").descending());
        } else {
            pageable = PageRequest.of(page, 5);
        }

        Page<Product> productPage = productRepo.findAll(pageable);
        List<Product> products = productPage.getContent();

        // SEARCH
        if (keyword != null && !keyword.isEmpty()) {
            products = products.stream()
                    .filter(p -> p.getName().toLowerCase().contains(keyword.toLowerCase()))
                    .toList();
        }

        // FILTER
        if (categoryId != null) {
            products = products.stream()
                    .filter(p -> p.getCategory() != null &&
                            p.getCategory().getId().equals(categoryId))
                    .toList();
        }

        model.addAttribute("products", products);
        model.addAttribute("categories", categoryRepo.findAll());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", productPage.getTotalPages());

        return "product/index";
    }
    @GetMapping("/products/create")
    public String createForm(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("categories", categoryRepo.findAll());
        return "product/create";
    }

    @PostMapping("/products/save")
    public String saveProduct(@RequestParam String name,
                              @RequestParam double price,
                              @RequestParam Long categoryId) {

        Category category = categoryRepo.findById(categoryId).orElse(null);

        Product product = new Product();
        product.setName(name);
        product.setPrice(price);
        product.setCategory(category);

        productRepo.save(product);

        return "redirect:/products";
    }
    @GetMapping("/products/edit/{id}")
    public String editForm(@PathVariable Long id, Model model) {
        Product product = productRepo.findById(id).orElse(null);
        model.addAttribute("product", product);
        model.addAttribute("categories", categoryRepo.findAll());
        return "product/edit";
    }
    @PostMapping("/products/update")
    public String updateProduct(@RequestParam Long id,
                                @RequestParam String name,
                                @RequestParam double price,
                                @RequestParam Long categoryId) {

        Product product = productRepo.findById(id).orElse(null);

        if (product != null) {
            Category category = categoryRepo.findById(categoryId).orElse(null);

            product.setName(name);
            product.setPrice(price);
            product.setCategory(category);

            productRepo.save(product);
        }

        return "redirect:/products";
    }
    @GetMapping("/products/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productRepo.deleteById(id);
        return "redirect:/products";
    }
    // ================= ADD TO CART =================
    @GetMapping("/add-to-cart/{id}")
    public String addToCart(@PathVariable Long id, HttpSession session) {

        Product product = productRepo.findById(id).orElse(null);

        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");

        if (cart == null) cart = new ArrayList<>();

        boolean found = false;

        for (CartItem item : cart) {
            if (item.getProductId().equals(id)) {
                item.setQuantity(item.getQuantity() + 1);
                found = true;
                break;
            }
        }

        if (!found && product != null) {
            CartItem item = new CartItem();
            item.setProductId(product.getId());
            item.setName(product.getName());
            item.setPrice(product.getPrice());
            item.setQuantity(1);
            cart.add(item);
        }

        session.setAttribute("cart", cart);
        return "redirect:/products";
    }

    // ================= CART =================
    @GetMapping("/cart")
    public String cart(HttpSession session, Model model) {

        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");

        if (cart == null) cart = new ArrayList<>();

        double total = cart.stream()
                .mapToDouble(i -> i.getPrice() * i.getQuantity())
                .sum();

        model.addAttribute("cart", cart);
        model.addAttribute("total", total);

        return "cart/index";
    }

    @GetMapping("/checkout")
    public String checkout(HttpSession session) {

        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");

        if (cart == null || cart.isEmpty())
            return "redirect:/cart";

        com.example.bai6.entity.Order order = new com.example.bai6.entity.Order();
        order = orderRepo.save(order);

        double total = 0;

        for (CartItem item : cart) {

            OrderDetail detail = new OrderDetail();
            detail.setProductId(item.getProductId());
            detail.setQuantity(item.getQuantity());
            detail.setPrice(item.getPrice());
            detail.setOrder(order);

            orderDetailRepo.save(detail);

            total += item.getPrice() * item.getQuantity();
        }

        order.setTotal(total);
        orderRepo.save(order);

        session.removeAttribute("cart");

        return "redirect:/products";
    }
}