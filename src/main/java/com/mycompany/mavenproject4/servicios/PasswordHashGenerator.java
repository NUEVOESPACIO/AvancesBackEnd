
package com.mycompany.mavenproject4.servicios;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordHashGenerator {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String raw = "Espana87";
        String hashed = encoder.encode(raw);
        System.out.println(hashed);
    }
}
    

