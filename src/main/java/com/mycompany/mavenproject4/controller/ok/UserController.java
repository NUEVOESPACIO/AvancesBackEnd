package com.mycompany.mavenproject4.controller.ok;

import com.mycompany.mavenproject4.dto.GeneralResponseOk;
import com.mycompany.mavenproject4.dto.UserCreateRequest;
import com.mycompany.mavenproject4.dto.UserInfo;
import com.mycompany.mavenproject4.dto.UserUpdateRequest;
import com.mycompany.mavenproject4.servicios.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/createuser")
    public ResponseEntity<GeneralResponseOk> createUser(@RequestBody UserCreateRequest request) {

        GeneralResponseOk response = userService.createUser(request);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GeneralResponseOk> updateUser(
            @PathVariable Long id,
            @RequestBody UserUpdateRequest request) {

        GeneralResponseOk response = userService.updateUser(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<GeneralResponseOk> deleteUser(@PathVariable Long id) {

        GeneralResponseOk response = userService.deleteUser(id);

        return ResponseEntity.ok(response);
    }
    
        // =========================
    // LIST USERS (PAGINADO)
    // =========================
    @GetMapping
    public ResponseEntity<Page<UserInfo>> listUsers(Pageable pageable) {

        Page<UserInfo> users = userService.listUsers(pageable);
        return ResponseEntity.ok(users);
    }

}
