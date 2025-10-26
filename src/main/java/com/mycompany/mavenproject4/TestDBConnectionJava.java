
package com.mycompany.mavenproject4;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class TestDBConnectionJava implements CommandLineRunner {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void run(String... args) {
        try {
            jdbcTemplate.execute("SELECT 1");
            System.out.println("✅ Conexión a MySQL exitosa!");
        } catch (Exception e) {
            System.out.println("❌ Error al conectar a MySQL:");
            e.printStackTrace();
        }
    }
}