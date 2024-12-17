package com.test.axiata.items.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.test.axiata.items.repository.ItemRepository;
import com.test.axiata.users.DTO.UserRequestDTO;
import com.test.axiata.users.entity.Users;
import com.test.axiata.items.DTO.ItemRequestDTO;
import com.test.axiata.items.entity.Item;
import com.test.axiata.utils.Constant;
import com.test.axiata.utils.Response;

import jakarta.transaction.Transactional;

@Service
public class ItemsService {
    @Autowired 
    ItemRepository itemsRepository;

    public Response<Object> getItems(){

        List<Item> result = itemsRepository.findAll();
        return Response.builder()
                .responseCode(Constant.Response.SUCCESS_CODE)
                .responseMessage(Constant.Response.SUCCESS_MESSAGE)
                .data(result)
                .build();
    }


    public Response<Object> findItemById(Integer Id){
        Optional<Item> result = itemsRepository.findById(Id);

        if(result.isEmpty()){
            return Response.builder()
                .responseCode(HttpStatus.BAD_REQUEST.value())
                .responseMessage("Item tidak terdaftar")
                .build();
        }
        return Response.builder()
                .responseCode(Constant.Response.SUCCESS_CODE)
                .responseMessage(Constant.Response.SUCCESS_MESSAGE)
                .data(result.get())
                .build();
    }
    
    @Transactional
    public Response<Object> addItem(ItemRequestDTO request, String userSession){

        Optional<Item> item = itemsRepository.findByName(request.getName());
        if(item.isPresent()){
            return Response.builder()
                .responseCode(HttpStatus.BAD_REQUEST.value())
                .responseMessage("Item sudah terdaftar")
                .build();
        }

        Item dtItem = new Item();
        dtItem.setName(request.getName());
        dtItem.setDescription(request.getDescription());
        dtItem.setPrice(request.getPrice());
        dtItem.setCost(request.getCost());
        dtItem.setCeated_by(userSession);

        return Response.builder()
        .responseCode(Constant.Response.SUCCESS_CODE)
        .responseMessage(Constant.Response.SUCCESS_MESSAGE)
        .data(itemsRepository.save(dtItem))
        .build();
    }

    @Transactional
    public Response<Object> updateItem(ItemRequestDTO request, Integer id, String userSession){

        Optional<Item> item = itemsRepository.findById(id);
        if(item.isEmpty()){
            return Response.builder()
                .responseCode(HttpStatus.BAD_REQUEST.value())
                .responseMessage("Item tidak terdaftar")
                .build();
        }

        Item dtItem = item.get();
        dtItem.setName(request.getName());
        dtItem.setDescription(request.getDescription());
        dtItem.setPrice(request.getPrice());
        dtItem.setCost(request.getCost());
        dtItem.setUpdated_by(userSession);

        return Response.builder()
        .responseCode(Constant.Response.SUCCESS_CODE)
        .responseMessage(Constant.Response.SUCCESS_MESSAGE)
        .data(itemsRepository.save(dtItem))
        .build();
    }

    @Transactional
    public Response<Object> deleteItem(Integer id){
        Optional<Item> item = itemsRepository.findById(id);
        if(item.isEmpty()){
            return Response.builder()
                .responseCode(HttpStatus.BAD_REQUEST.value())
                .responseMessage("Item tidak terdaftar")
                .build();
        }

        itemsRepository.delete(item.get());
        return Response.builder()
        .responseCode(Constant.Response.SUCCESS_CODE)
        .responseMessage(Constant.Response.SUCCESS_MESSAGE)
        .build();
    }
}
