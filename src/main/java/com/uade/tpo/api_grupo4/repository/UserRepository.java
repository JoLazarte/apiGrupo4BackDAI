package com.uade.tpo.api_grupo4.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.uade.tpo.api_grupo4.entity.User;



@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    boolean existsByUsername(String username);
    User findByEmail(String email);
    boolean existsByEmail(String email);
   
}
