package com.test.axiata.users.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.test.axiata.users.DTO.SessionUserDTO;
import com.test.axiata.users.DTO.UserRequestDTO;
import com.test.axiata.users.entity.Users;
import com.test.axiata.users.repository.UserRepository;
import com.test.axiata.utils.Response;
import com.test.axiata.utils.SessionService;

import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;

import com.test.axiata.utils.Constant;
@Service
public class UserService {
    private final SessionService sessionService;

    @Autowired
    public UserService(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @Autowired
    UserRepository userRepository;

    public Response<Object> session(SessionUserDTO userDTO, HttpSession session){
        Optional<Users> user = userRepository.findByEmail(userDTO.getEmail());

        if(user.isEmpty()){
            return Response.builder()
                .responseCode(HttpStatus.BAD_REQUEST.value())
                .responseMessage("Email tidak terdaftar")
                .build();
        }
        
        sessionService.setUsernameInSession(session, user.get().getFirst_name());

         return Response.builder()
        .responseCode(HttpStatus.OK.value())
        .responseMessage("Session "+user.get().getFirst_name()+" active")
        .build();
    }

    public Response<Object> getUsers(){

        List<Users> result = userRepository.findAllUser();
        return Response.builder()
                .responseCode(Constant.Response.SUCCESS_CODE)
                .responseMessage(Constant.Response.SUCCESS_MESSAGE)
                .data(result)
                .build();
    }


    public Response<Object> findUserById(Integer Id){
        Optional<Users> result = userRepository.findById(Id);

        if(result.isEmpty()){
            return Response.builder()
                .responseCode(HttpStatus.BAD_REQUEST.value())
                .responseMessage("User tidak terdaftar")
                .build();
        }
        return Response.builder()
                .responseCode(Constant.Response.SUCCESS_CODE)
                .responseMessage(Constant.Response.SUCCESS_MESSAGE)
                .data(result.get())
                .build();
    }
    
    @Transactional
    public Response<Object> addUser(UserRequestDTO request, String userSession){

        Optional<Users> user = userRepository.findByEmail(request.getEmail());
        if(user.isPresent()){
            return Response.builder()
                .responseCode(HttpStatus.BAD_REQUEST.value())
                .responseMessage("Email sudah terdaftar")
                .build();
        }

        Users dtUser = new Users();
        dtUser.setFirst_name(request.getFirst_name());
        dtUser.setLast_name(request.getLast_name());
        dtUser.setEmail(request.getEmail());
        dtUser.setPhone(request.getPhone());
        dtUser.setCeated_by(userSession);
        dtUser.setIsDeleted(false);
        
        return Response.builder()
        .responseCode(Constant.Response.SUCCESS_CODE)
        .responseMessage(Constant.Response.SUCCESS_MESSAGE)
        .data(userRepository.save(dtUser))
        .build();
    }

    @Transactional
    public Response<Object> updateUser(UserRequestDTO request,Integer id, String userSession){

        Optional<Users> user = userRepository.findById(id);
        if(user.isEmpty()){
            return Response.builder()
                .responseCode(HttpStatus.BAD_REQUEST.value())
                .responseMessage(Constant.Message.NOT_FOUND_DATA_MESSAGE)
                .build();
        }

        Users dtUser = user.get();
        dtUser.setFirst_name(request.getFirst_name());
        dtUser.setLast_name(request.getLast_name());
        dtUser.setEmail(request.getEmail());
        dtUser.setPhone(request.getPhone());
        dtUser.setUpdated_by(userSession);

        return Response.builder()
        .responseCode(Constant.Response.SUCCESS_CODE)
        .responseMessage(Constant.Response.SUCCESS_MESSAGE)
        .data(userRepository.save(dtUser))
        .build();
    }

    @Transactional
    public Response<Object> deleteUser(Integer id){

        Optional<Users> user = userRepository.findById(id);
        if(user.isEmpty()){
            return Response.builder()
                .responseCode(HttpStatus.BAD_REQUEST.value())
                .responseMessage(Constant.Message.NOT_FOUND_DATA_MESSAGE)
                .build();
        }

        Users dtUser = user.get();
        dtUser.setIsDeleted(true);

        return Response.builder()
        .responseCode(Constant.Response.SUCCESS_CODE)
        .responseMessage(Constant.Response.SUCCESS_MESSAGE)
        .data(userRepository.save(dtUser))
        .build();
    }


}
