package cps2.project.temperature.Repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import cps2.project.temperature.Entity.Advice;

@Repository
public interface RepAdvice extends JpaRepository<Advice, Long> {

	@Query(value = "SELECT * FROM advice WHERE id = ?1", nativeQuery = true)
	Advice findByAdviceId(Long id);
	
	List<Advice> findAll();

	@Query(value = "SELECT * FROM advice WHERE active = 1", nativeQuery = true)
	List<Advice> findAllWhereActive();

}
