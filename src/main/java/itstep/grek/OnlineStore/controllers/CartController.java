package itstep.grek.OnlineStore.controllers;

import itstep.grek.OnlineStore.Models.*;
import itstep.grek.OnlineStore.repository.ProductRepository;
import itstep.grek.OnlineStore.repository.UserRepository;
import itstep.grek.OnlineStore.services.interfaces.OrderService;
import itstep.grek.OnlineStore.services.interfaces.SalePositionService;
import itstep.grek.OnlineStore.services.interfaces.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;


@Controller

public class CartController {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    private ShoppingCartService shoppingCartService;

    private SalePositionService salePositionService;

    private OrderService orderService;

    @Autowired
    public CartController(ShoppingCartService shoppingCartService, SalePositionService salePositionService,
                          OrderService orderService) {
        this.shoppingCartService = shoppingCartService;
        this.salePositionService = salePositionService;
        this.orderService = orderService;
    }

    /**
     * Возвращает страницу "client/cart" - страница корзины с торговыми
     * позициями, которие сделал клиент.
     * URL запроса "/cart", метод GET.
     *
     * @return Объект класса {@link ModelAndView}.
     */

    @GetMapping("/cart")

    public ModelAndView viewCart() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("sale_positions", this.shoppingCartService.getSalePositions());
        modelAndView.addObject("price_of_cart", this.shoppingCartService.getPrice());
        modelAndView.addObject("sizeCart", this.shoppingCartService.getSize());
        modelAndView.setViewName("cart");
        return modelAndView;
    }


    @PostMapping(value = "/cart/add")
    public String addProductToCart(@RequestParam(value = "id") Long id) {
        int quantity = 1;
        SalePosition position = new SalePosition();
        position.setQuantity(quantity);
        Optional< Product > optionalProduct = productRepository.findById(id);

        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            position.setProduct(product);
        }
        this.shoppingCartService.add(position);
        return "redirect:/cart";
    }

    @PostMapping("/cart/add_1")
    public String incrementProductToCart( @PathVariable("id") Long id){
        int quantity = 1;
        SalePosition position = new SalePosition();
        position.setQuantity(quantity);

        Optional< Product > optionalProduct = productRepository.findById(id);

        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            position.setProduct(product);
        }
        this.shoppingCartService.add(position);
        return "cartAdd";
    }


    @PostMapping("/cart/del")
    public String delOneProduct(@RequestParam(value = "id") Long id) {

        int quantity = 1;
        SalePosition salePosition = new SalePosition();
        salePosition.setQuantity(quantity);
        Optional<Product> optionalProduct = productRepository.findById(id);

        if (optionalProduct.isPresent()){
            Product  product= optionalProduct.get();
           salePosition.setProduct(product);
           this.shoppingCartService.decrementSalePosition(salePosition);
      }
        return  "redirect:/cart";
    }

    @PostMapping("/cart/removePosition")
    public String removePosition(@RequestParam(value = "id") Long id) {
        int quantity = 0;
        SalePosition salePosition = new SalePosition();
        salePosition.setQuantity(quantity);
        Optional<Product> optionalProduct = productRepository.findById(id);

        if (optionalProduct.isPresent()){
            Product  product= optionalProduct.get();
            salePosition.setProduct(product);
            this.shoppingCartService.remove(salePosition);
        }
        return  "redirect:/cart";
    }

    @RequestMapping(value = "/checkout", method = RequestMethod.POST)
    public ModelAndView viewCheckout(
            @RequestParam(value = "number") String number,
            @RequestParam(value = "username") String username,
            @RequestParam(value = "email") String email,
            @RequestParam(value = "phone")  String phone,
            @RequestParam(value = "shippingAddress") String address,
            @RequestParam(value = "shippingDetails") String shippingDetails,
            @RequestParam(value = "description") String description

    ) {
         ModelAndView modelAndView = new ModelAndView();
        if (this.shoppingCartService.getSize() > 0) {
            Order order = new Order();
            order.setNumber(number);
            order.setEmail(email);
            order.setPhone(phone);
            order.setShippingAddress(address);
            order.setUsername(username);
            order.setShippingDetails(shippingDetails);
            order.setDescription(description);

            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String name = auth.getName();
            User user = userRepository.findByUsername(name);
            order.setClient(user);
            order.addSalePositions(this.shoppingCartService.getSalePositions());
            this.orderService.add(order);
            modelAndView.addObject("order", order);
            modelAndView.addObject("sale_positions", order.getSalePositions());
            modelAndView.addObject("price_of_cart", this.shoppingCartService.getPrice());
            this.shoppingCartService.clear();
            modelAndView.addObject("cart_size", this.shoppingCartService.getSize());
            modelAndView.setViewName("checkOutForm");
        } else {
            modelAndView.setViewName("redirect:/home");
        }
        return modelAndView;
    }

    @GetMapping("/orderForm")
    public String orderForm(@ModelAttribute Order order, Model model) {
        return "orderForm";
    }

    @GetMapping("/checkOutForm")
    public String checkoutForm(@ModelAttribute Order order, Model model) {
        return "checkOutForm";
    }

}
