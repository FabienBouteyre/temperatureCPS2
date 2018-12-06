package cps2.project.temperature.Repository;


import cps2.project.temperature.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepUser extends JpaRepository<User, Long> {

    User findByEmail(String email);
}
