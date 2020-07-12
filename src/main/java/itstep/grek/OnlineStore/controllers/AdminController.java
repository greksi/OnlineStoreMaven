package itstep.grek.OnlineStore.controllers;


import itstep.grek.OnlineStore.Models.Category;
import itstep.grek.OnlineStore.Models.Order;
import itstep.grek.OnlineStore.Models.Product;
import itstep.grek.OnlineStore.repository.CategoryRepository;
import itstep.grek.OnlineStore.repository.OrderRepository;
import itstep.grek.OnlineStore.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
public class AdminController {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Value("${upload.path}")
    private String upLoadPath;

    @GetMapping("/admin")
    public String admin() {
        return "admin";
    }

    @GetMapping("/productList")
    public String productList(Model model) {
        List< Product > products = new ArrayList<>();
        products = productRepository.findAll();
        model.addAttribute("products", products);
        return "productList";
    }

    @GetMapping("/productAdd")
    public String productAdd(@ModelAttribute Product product, Model model) {
        List< Category > categories = new ArrayList<>();
        try {
            categories = categoryRepository.findAll();
        } catch (Exception e) {
            return "productAdd";
        }
        model.addAttribute("categories", categories);
        return "productAdd";
    }

    @PostMapping("/productAdd")
    public String addCategory(@ModelAttribute Product product, @RequestParam("file") MultipartFile file, Model model) throws IOException {
        if (file != null) {
           String uuidFile = UUID.randomUUID().toString();
           String resultFileName = uuidFile + "." + file.getOriginalFilename();
           file.transferTo(new File(upLoadPath + "/" + resultFileName));
            product.setFileName(resultFileName);
        }

        productRepository.save(product);
        return "redirect:/admin";
    }

    @GetMapping("productList/{product}/edit")
    public String productEdit(@PathVariable Product product, Model model) {
        List< Category > categories = new ArrayList<>();
        try {
            categories = categoryRepository.findAll();

            model.addAttribute("product", product);
            model.addAttribute("categories", categories);
        } catch (Exception e) {
            model.addAttribute("product", product);
            return "productEdit";

        }

        return "productEdit";
    }

    @PostMapping("/productEdit")
    public String productEdit(Product product, @RequestParam("file") MultipartFile file
    ) throws IOException {

        if (!file.isEmpty()) {
            File upLoadDir = new File(upLoadPath);
            if (!upLoadDir.exists()) {
                upLoadDir.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultFileName = uuidFile + "." + file.getOriginalFilename();
            file.transferTo(new File(upLoadPath + "/" + resultFileName));
            product.setFileName(resultFileName);
        }
        productRepository.save(product);
        return "redirect:/productList";
    }

    @RequestMapping(value = "productList/{id}/remove", method = RequestMethod.GET)
    public String delete(@PathVariable("id") Long id) {
        productRepository.deleteById(id);
        return "redirect:/productList";
    }

    @GetMapping("/orderList")
    public String orderList(Model model) {
        List< Order > orders = new ArrayList<>();
        orders = orderRepository.findAll();
        model.addAttribute("orders", orders);
        return "orderList";
    }
}
