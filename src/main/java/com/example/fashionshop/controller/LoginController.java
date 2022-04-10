package com.example.fashionshop.controller;

import com.example.fashionshop.model.User;
import com.example.fashionshop.model.dto.requestDto.ResponseDto;
import com.example.fashionshop.service.UserService;
import com.example.fashionshop.validation.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/v1/login")
public class LoginController {

    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    ResponseEntity<ResponseDto> signup(@RequestBody User user){
        if (!UserValidator.checkUserSignUp(user)){
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "user data is invalid to signUp" );
        }
        User login = userService.create(user);

        ResponseDto responseDto = new ResponseDto("User logged in.");
        responseDto.addInfo("UserId", String.valueOf(user));
        return ResponseEntity.ok(responseDto);
    }
}
