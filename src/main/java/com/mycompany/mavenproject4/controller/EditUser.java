
package com.mycompany.mavenproject4.controller;
import com.mycompany.mavenproject4.entidades.User;
import com.mycompany.mavenproject4.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import org.springframework.http.ResponseEntity;


@RestController
@RequestMapping("/abm")
public class EditUser {
    
        @Autowired
    private UserRepository userRepository;
        
        
    @PatchMapping("/edituser/{id}")
    public ResponseEntity<?> updateUserPartially(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Actualiza los campos enviados en el body
        updates.forEach((key, value) -> {
            switch (key) {
                case "nombre" -> user.setNombre((String) value);
                case "apellido" -> user.setApellido((String) value);
                case "email" -> user.setEmail((String) value);
                default -> throw new IllegalArgumentException("Campo no válido: " + key);
            }
        });

        userRepository.save(user);
       return ResponseEntity.ok(Map.of("status", "ok"));
    }


    
}
