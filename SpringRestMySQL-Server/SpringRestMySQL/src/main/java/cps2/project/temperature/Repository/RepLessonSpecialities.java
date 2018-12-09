package cps2.project.temperature.Repository;

import cps2.project.temperature.Entity.Calendars.Specialities;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepLessonSpecialities extends JpaRepository<Specialities, Long> {

    Specialities findByName(String name);

}
