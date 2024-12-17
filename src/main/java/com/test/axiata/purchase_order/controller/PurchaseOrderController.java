package com.test.axiata.purchase_order.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.test.axiata.purchase_order.DTO.request.OrderRequestDTO;
import com.test.axiata.purchase_order.DTO.request.update.updateOrderDTO;
import com.test.axiata.purchase_order.service.OrderService;
import com.test.axiata.utils.Response;
import com.test.axiata.utils.SessionService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("api/v1/orders")
public class PurchaseOrderController {
    

    private final SessionService sessionService;

    @Autowired
    public PurchaseOrderController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @Autowired
    OrderService orderService;

    @GetMapping()
    public Response<Object> getPurchaseOrder(@RequestParam (required = false) Integer id){
        if(id== null){
            return orderService.listOrder();
        }else{
            return orderService.findOrderById(id);
        }
        
    }

    @PostMapping()
    public Response<Object> PurchaseOrder(@RequestBody OrderRequestDTO request,HttpSession session){
        String userSession = sessionService.getUsernameFromSession(session);
        return orderService.createOrder(request, userSession);
        
    }

    @PutMapping()
    public Response<Object> updatePurchaseOrder(@RequestBody updateOrderDTO request,Integer order_id,HttpSession session){
        String userSession = sessionService.getUsernameFromSession(session);
        return orderService.updateOrder( request,order_id,userSession);
        
    }

    @DeleteMapping()
    public Response<Object> deletePurchaseOrder(@RequestParam Integer order_id){
        return orderService.deleteOrder(order_id);
        
    }
}
