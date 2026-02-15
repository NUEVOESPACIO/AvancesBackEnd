
package com.mycompany.mavenproject4.repository;


import com.mycompany.mavenproject4.entidades.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
