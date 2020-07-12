package itstep.grek.OnlineStore.controllers;

import itstep.grek.OnlineStore.Models.Product;
import itstep.grek.OnlineStore.Models.User;
import itstep.grek.OnlineStore.repository.ProductRepository;
import itstep.grek.OnlineStore.repository.UserRepository;
import itstep.grek.OnlineStore.services.interfaces.ShoppingCartService;
import itstep.grek.OnlineStore.storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class MainController {
    private ShoppingCartService shoppingCartService;
    private final StorageService storageService;

    @Autowired
    public MainController(StorageService storageService, ShoppingCartService shoppingCartService) {
        this.storageService = storageService;
        this.shoppingCartService = shoppingCartService;
    }

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;

    @GetMapping("/files/{filename}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

        Resource file = storageService.loadAsResource(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @RequestMapping( value = { "", "/", "/index", "/home" }, method = RequestMethod.GET )
    public String home(@RequestParam(value = "id", required = false) Long id,Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        List< Product > products = new ArrayList<>();

        Set<String> roles = auth.getAuthorities().stream()
                .map(r -> r.getAuthority()).collect(Collectors.toSet());

        try {

            products = productRepository.findAll();

            User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String name = user.getUsername();//get logged in username
            if(user.isAdmin()) {
                model.addAttribute("user", user);

            }

            int sizeCart = shoppingCartService.getSize();
            model.addAttribute("sizeCart", sizeCart);
        }
        catch (Exception ex){

        }
        finally {
            model.addAttribute("products", products);
            model.addAttribute("id", id);

            return "home";

        }
    }

    @GetMapping("/contact")
    public String contact(Model model) {
        int sizeCart = shoppingCartService.getSize();
        model.addAttribute("sizeCart", sizeCart);
        return "contact";
    }

    @GetMapping("/delivery")
    public String delivery(Model model) {
        int sizeCart = shoppingCartService.getSize();
        model.addAttribute("sizeCart", sizeCart);
        return "delivery";
    }

}

