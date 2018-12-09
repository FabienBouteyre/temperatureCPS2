package cps2.project.temperature.Repository;

import cps2.project.temperature.Entity.Calendars.Lesson;
import cps2.project.temperature.Entity.Calendars.LessoneSchedulesData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RepLessoneSchedulesData extends JpaRepository<LessoneSchedulesData, Long> {

    List<LessoneSchedulesData> findByLesson(Lesson lesson);
}
