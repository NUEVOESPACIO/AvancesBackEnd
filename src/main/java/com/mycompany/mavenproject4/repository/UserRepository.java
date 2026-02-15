package com.mycompany.mavenproject4.repository;
import com.mycompany.mavenproject4.entidades.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    public boolean existsByUsername(String username);

    public boolean existsByEmail(String email);
}
