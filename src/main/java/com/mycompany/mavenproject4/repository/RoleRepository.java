package com.mycompany.mavenproject4.repository;

import com.mycompany.mavenproject4.entidades.Role;
import com.mycompany.mavenproject4.entidades.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByNombre(RoleName roleName);
}