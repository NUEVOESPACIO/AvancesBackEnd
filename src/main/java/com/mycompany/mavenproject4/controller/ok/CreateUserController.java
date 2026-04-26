package com.mycompany.mavenproject4.controller.ok;


import com.mycompany.mavenproject4.dto.GeneralResponseOk;
import com.mycompany.mavenproject4.dto.UserCreateRequest;
import com.mycompany.mavenproject4.servicios.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class CreateUserController {

    @Autowired
    private UserService userService;

    @PostMapping("/createuser")
    public ResponseEntity<GeneralResponseOk> createUser(@RequestBody UserCreateRequest request) {

        GeneralResponseOk response = userService.createUser(request);

        return ResponseEntity.ok(response);
    }
}