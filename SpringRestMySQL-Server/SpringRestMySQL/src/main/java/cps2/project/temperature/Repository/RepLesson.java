package cps2.project.temperature.Repository;

import cps2.project.temperature.Entity.Calendars.Lesson;
import cps2.project.temperature.Entity.Calendars.Specialities;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RepLesson extends JpaRepository<Lesson, Long> {

    Lesson findByName(String name);
    List<Lesson> findBySpecialities(Specialities specialities);

}
