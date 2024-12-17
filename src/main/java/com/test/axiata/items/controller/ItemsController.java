package com.test.axiata.items.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.test.axiata.items.DTO.ItemRequestDTO;
import com.test.axiata.items.service.ItemsService;
import com.test.axiata.utils.Response;
import com.test.axiata.utils.SessionService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
@RestController
@RequestMapping("api/v1/items")

public class ItemsController {
    private final SessionService sessionService;

    @Autowired
    public ItemsController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @Autowired
    ItemsService itemsService;

   @GetMapping()
    public Response<Object> getItems(@RequestParam (required = false) Integer id){
        if(id== null){
            return itemsService.getItems();
        }else{
            return itemsService.findItemById(id);
        }
    }

    @PostMapping()
    public Response<Object> createItemResponse(@Valid @RequestBody ItemRequestDTO request , HttpSession session){
        String userSession = sessionService.getUsernameFromSession(session);
        return itemsService.addItem(request, userSession);
    }

    @PutMapping()
    public Response<Object> updateItemResponse(@Valid @RequestBody ItemRequestDTO request ,Integer id, HttpSession session){
        String userSession = sessionService.getUsernameFromSession(session);
        return itemsService.updateItem(request, id, userSession);
    }

    @DeleteMapping()
    public Response<Object> deleteItem(Integer id){
        return itemsService.deleteItem(id);

    }

}
