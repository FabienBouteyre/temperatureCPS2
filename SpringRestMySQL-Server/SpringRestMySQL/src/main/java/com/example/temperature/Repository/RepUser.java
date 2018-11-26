package com.example.temperature.Repository;

import com.example.temperature.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepUser extends JpaRepository<User, Long> {

    User findByEmail(String email);
}
