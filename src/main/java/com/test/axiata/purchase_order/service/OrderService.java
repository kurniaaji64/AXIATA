package com.test.axiata.purchase_order.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.test.axiata.utils.Constant;
import com.test.axiata.utils.Response;

import jakarta.transaction.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.axiata.items.entity.Item;
import com.test.axiata.items.repository.ItemRepository;
import com.test.axiata.purchase_order.DTO.order_list.OrderDTO;
import com.test.axiata.purchase_order.DTO.order_list.OrderDetailDTO;
import com.test.axiata.purchase_order.DTO.request.DataOrder;
import com.test.axiata.purchase_order.DTO.request.OrderRequestDTO;
import com.test.axiata.purchase_order.DTO.request.RequestOrderDTO;
import com.test.axiata.purchase_order.DTO.request.update.DataOrderDTO;
import com.test.axiata.purchase_order.DTO.request.update.updateOrderDTO;
import com.test.axiata.purchase_order.entity.OrderDetails;
import com.test.axiata.purchase_order.entity.Orders;
import com.test.axiata.purchase_order.repository.OrderDetailsRepository;
import com.test.axiata.purchase_order.repository.OrderRepository;


@Service
public class OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    OrderDetailsRepository orderDetailsRepository;

    public  Response<Object> listOrder(){
        // List<OrderDetails> orders = orderDetailsRepository.findAll();

        List<Object[]> orders = orderRepository.getOrderAndDetails();

        // Menyimpan hasil DTO
        List<OrderDTO> orderDetailsList = new ArrayList<>();
        
        for (Object[] dtOrder : orders) {
            final Integer id = (Integer) dtOrder[0];
            String description = (String) dtOrder[1];
            Integer totalPrice = (Integer) dtOrder[2];
            Integer totalCost = (Integer) dtOrder[3];
            String detailOrdersJson = (String) dtOrder[4]; // Hasil JSON array dalam bentuk string
    
            // Mengubah string JSON menjadi List<OrderDetailDTO>
            List<OrderDetailDTO> detailOrders = parseDetailOrdersJson(detailOrdersJson);
    
            // Membuat DTO untuk Order beserta detailnya
            OrderDTO orderDTO = new OrderDTO(id, description, totalPrice, totalCost, detailOrders);
            
            // Menambahkan ke list
            orderDetailsList.add(orderDTO);
        }
        return Response.builder()
                .responseCode(Constant.Response.SUCCESS_CODE)
                .responseMessage(Constant.Response.SUCCESS_MESSAGE)
                .data(orderDetailsList)
                .build();
    }

    public  Response<Object> findOrderById(Integer Id){
        // List<OrderDetails> orders = orderDetailsRepository.findAll();

        List<Object[]> orders = orderRepository.getOrderIdAndDetails(Id);

        // Menyimpan hasil DTO
        List<OrderDTO> orderDetailsList = new ArrayList<>();
        
        for (Object[] dtOrder : orders) {
            final Integer id = (Integer) dtOrder[0];
            String description = (String) dtOrder[1];
            Integer totalPrice = (Integer) dtOrder[2];
            Integer totalCost = (Integer) dtOrder[3];
            String detailOrdersJson = (String) dtOrder[4]; // Hasil JSON array dalam bentuk string
    
            // Mengubah string JSON menjadi List<OrderDetailDTO>
            List<OrderDetailDTO> detailOrders = parseDetailOrdersJson(detailOrdersJson);
    
            // Membuat DTO untuk Order beserta detailnya
            OrderDTO orderDTO = new OrderDTO(id, description, totalPrice, totalCost, detailOrders);
            
            // Menambahkan ke list
            orderDetailsList.add(orderDTO);
        }
        return Response.builder()
                .responseCode(Constant.Response.SUCCESS_CODE)
                .responseMessage(Constant.Response.SUCCESS_MESSAGE)
                .data(orderDetailsList)
                .build();
    }

    private List<OrderDetailDTO> parseDetailOrdersJson(String detailOrders) {

        // Membuat ObjectMapper
        ObjectMapper objectMapper = new ObjectMapper();
        List<OrderDetailDTO> orderDetails = null;
        try {
            // Mapping JSON string menjadi List<OrderDetailDTO>
            orderDetails = objectMapper.readValue(detailOrders, objectMapper.getTypeFactory().constructCollectionType(List.class, OrderDetailDTO.class));

            // Menampilkan hasil mapping
            for (OrderDetailDTO orderDetail : orderDetails) {
                System.out.println("Item ID: " + orderDetail.getItem_id());
                System.out.println("Item Qty: " + orderDetail.getItem_qty());
                System.out.println("Item Cost: " + orderDetail.getItem_cost());
                System.out.println("Item Price: " + orderDetail.getItem_price());
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return orderDetails;
        
    }

    @Transactional
    public  Response<Object> createOrder(OrderRequestDTO request, String userSession){

        Integer totalPrice = 0;
        Integer TotalCost = 0;
        List<DataOrder> orders = request.getOrders();
        Integer[] itemsOrder = new Integer[orders.size()];
        
        for (int i = 0; i < orders.size(); i++) {
            itemsOrder[i] = orders.get(i).getItem_id();
            Optional<Item> item = itemRepository.findById(orders.get(i).getItem_id());
            if(item.isEmpty()){
                return Response.builder()
                    .responseCode(HttpStatus.BAD_REQUEST.value())
                    .responseMessage("Item ID "+orders.get(i).getItem_id()+" tidak terdaftar")
                    .build();
            }

            System.out.println(item.get().getName()+" (PRICE) :"+item.get().getPrice()+"-"+orders.get(i).getItem_qty());
            System.out.println(item.get().getName()+" (COST)  :"+item.get().getCost()+"-"+orders.get(i).getItem_qty());
            totalPrice +=orders.get(i).getItem_qty() * item.get().getPrice();
            TotalCost +=orders.get(i).getItem_qty() * item.get().getCost();
        }
        
        Orders Porder = new Orders();
        Porder.setDescription(request.getDescription());
        Porder.setTotal_cost(TotalCost);
        Porder.setTotal_price(totalPrice);
        Porder.setCeated_by(userSession);
        
        Orders dtOrder = orderRepository.save(Porder);

        List<Item> itemOrder = itemRepository.findItemsById(itemsOrder);
        for (int i = 0; i < orders.size(); i++) {
            for (int j = 0; j < itemsOrder.length; j++) {
                if(orders.get(i).getItem_id().equals(itemOrder.get(j).getId())){
                    OrderDetails details= new OrderDetails();
                    details.setItem_cost(itemOrder.get(j).getCost());
                    details.setItem_price(itemOrder.get(j).getPrice());
                    details.setItem_qty(orders.get(i).getItem_qty());
                    details.setItems(itemOrder.get(j));
                    details.setOrder(dtOrder);

                    orderDetailsRepository.save(details);
                }
            }
        }
       
         return Response.builder()
                .responseCode(Constant.Response.SUCCESS_CODE)
                .responseMessage(Constant.Response.SUCCESS_MESSAGE)
                .build();
    }

    @Transactional
    public  Response<Object> updateOrder( updateOrderDTO request ,Integer Id,  String userSession){

        Integer totalPrice = 0;
        Integer TotalCost = 0;

        Optional<Orders> order = orderRepository.findById(Id);
        List<DataOrderDTO> updateOrderDetails = request.getOrders();
       
        if(order.isEmpty()){
            return Response.builder()
                .responseCode(HttpStatus.BAD_REQUEST.value())
                .responseMessage("Order tidak di terdaftar")
                .build();
        }

        List<OrderDetails> detailOrder = orderDetailsRepository.findByOrderId(order.get().getId());
        Integer[] orderDetail = new Integer[detailOrder.size()];
        for (int i = 0; i < detailOrder.size(); i++) {
            orderDetail[i] = detailOrder.get(i).getId();
            Optional<Item> item = itemRepository.findById(detailOrder.get(i).getItems().getId());

            if(item.isEmpty()){
                return Response.builder()
                    .responseCode(HttpStatus.BAD_REQUEST.value())
                    .responseMessage("Item ID "+detailOrder.get(i).getItems().getId()+" tidak terdaftar")
                    .build();
            }

            System.out.println(item.get().getName()+" (PRICE) :"+item.get().getPrice()+"-"+detailOrder.get(i).getItem_qty());
            System.out.println(item.get().getName()+" (COST)  :"+item.get().getCost()+"-"+detailOrder.get(i).getItem_qty());
            totalPrice +=updateOrderDetails.get(i).getItem_qty() * item.get().getPrice();
            TotalCost +=updateOrderDetails.get(i).getItem_qty() * item.get().getCost();
        }

        System.out.println("TOTAL PRICE : "+totalPrice);
        System.out.println("TOTAL COST : "+TotalCost);
        Orders Porder = order.get();
        Porder.setTotal_cost(TotalCost);
        Porder.setTotal_price(totalPrice);
        Porder.setUpdated_by(userSession);
        
        Orders dtOrder = orderRepository.save(Porder);

        for (int i = 0; i < detailOrder.size(); i++) {
            for(int j= 0 ;j< updateOrderDetails.size() ; j++){
                if(detailOrder.get(i).getId().equals(updateOrderDetails.get(j).getOrder_detail_id())){
                    OrderDetails details= new OrderDetails();

                    Optional<Item> item = itemRepository.findById(detailOrder.get(i).getItems().getId());
                    details.setId(detailOrder.get(i).getId());
                    details.setItem_cost(item.get().getCost());
                    details.setItem_price(item.get().getPrice());
                    details.setItem_qty(updateOrderDetails.get(i).getItem_qty());
                    details.setItems(item.get());
                    details.setOrder(dtOrder);

                    orderDetailsRepository.save(details);
                }
            }
        }
         return Response.builder()
                .responseCode(Constant.Response.SUCCESS_CODE)
                .responseMessage(Constant.Response.SUCCESS_MESSAGE)
                .data(detailOrder)
                .build();
    }


    @Transactional
    public  Response<Object> deleteOrder( Integer Id){
        Optional<Orders> detailOrder = orderRepository.findById(Id);

        if(detailOrder.isEmpty()){
            return Response.builder()
                    .responseCode(HttpStatus.BAD_REQUEST.value())
                    .responseMessage("ID Order tidak terdaftar")
                    .build();
        }

        orderRepository.delete(detailOrder.get());
        return Response.builder()
                .responseCode(Constant.Response.SUCCESS_CODE)
                .responseMessage(Constant.Response.SUCCESS_MESSAGE)
                .data(detailOrder)
                .build();

    }


}




