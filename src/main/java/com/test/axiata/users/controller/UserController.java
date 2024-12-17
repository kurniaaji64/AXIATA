package com.test.axiata.users.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.test.axiata.config.SessionFilter;
import com.test.axiata.users.DTO.SessionUserDTO;
import com.test.axiata.users.DTO.UserRequestDTO;
import com.test.axiata.users.service.UserService;
import com.test.axiata.utils.Response;
import com.test.axiata.utils.SessionService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@RestController
@RequestMapping("api/v1/users")
public class UserController {
    
    private final SessionService sessionService;

    @Autowired
    public UserController(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @Autowired
    UserService userService;

    @PostMapping("/setSession")
    public Response<Object>  setSession(HttpSession session, @RequestBody SessionUserDTO user) {
    
        sessionService.setUsernameInSession(session, user.getEmail());
        
        return userService.session(user,session);
    }

    @GetMapping()
    public Response<Object> getUsers(@RequestParam (required = false) Integer id){
        if(id== null){
            return userService.getUsers();
        }else{
            return userService.findUserById(id);
        }
    }

    @PostMapping()
    public Response<Object> addUsers(@Valid @RequestBody UserRequestDTO request,HttpSession session){
        // System.out.println(sessionInitializer.getSessionInfo());
        
        String userSession = sessionService.getUsernameFromSession(session);
        
        return userService.addUser(request, userSession);
    }

    @PutMapping()
    public Response<Object> updateUsers(@Valid @RequestBody UserRequestDTO request,@RequestParam Integer id, HttpSession session){
        String userSession = sessionService.getUsernameFromSession(session);
        return userService.updateUser(request, id,userSession);
    }

    @DeleteMapping()
    public Response<Object> deleteUsers(@RequestParam Integer id){
        return userService.deleteUser( id);
    }
}
