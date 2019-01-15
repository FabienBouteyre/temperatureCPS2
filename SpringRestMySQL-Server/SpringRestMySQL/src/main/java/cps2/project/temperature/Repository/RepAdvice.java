package cps2.project.temperature.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cps2.project.temperature.Entity.Advice;

@Repository
public interface RepAdvice extends JpaRepository<Advice, Long> {

	List<Advice> findAll();

}
