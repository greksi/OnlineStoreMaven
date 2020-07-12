package itstep.grek.OnlineStore.controllers;

import itstep.grek.OnlineStore.Models.Order;
import itstep.grek.OnlineStore.Models.SalePosition;
import itstep.grek.OnlineStore.services.interfaces.OrderService;
import itstep.grek.OnlineStore.services.interfaces.SalePositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Collection;

@Controller
public class OrderController {

    private SalePositionService salePositionService;
    private OrderService orderService;

    @Autowired
    public OrderController(SalePositionService salePositionService, OrderService orderService) {
        this.salePositionService = salePositionService;
        this.orderService = orderService;
    }

    @GetMapping("/orderList/{order}/sale")
    public String orderSale(@PathVariable Order order, Model model) {


        Collection< SalePosition > salePositions = order.getSalePositions();
        model.addAttribute("salePositions", salePositions);
        return "salePositions";
    }
}
