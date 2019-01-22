package cps2.project.temperature.Controller;


import cps2.project.temperature.Entity.Calendars.Lesson;
import cps2.project.temperature.Entity.Calendars.LessoneSchedulesData;
import cps2.project.temperature.Entity.Calendars.Specialities;
import cps2.project.temperature.Entity.Mess;
import cps2.project.temperature.Repository.RepLesson;
import cps2.project.temperature.Repository.RepLessonSpecialities;
import cps2.project.temperature.Repository.RepLessoneSchedulesData;
import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.ComponentList;
import net.fortuna.ical4j.model.component.CalendarComponent;
import net.fortuna.ical4j.util.MapTimeZoneCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/calendare")
public class CalendareRestContr {

    @Autowired
    private RepLesson repLesson;

    @Autowired
    private RepLessoneSchedulesData repLessoneSchedulesData;

    @Autowired
    private RepLessonSpecialities repLessonSpecialities;

    @Transactional(readOnly=true)
    @GetMapping("/specialist")
    public List<Specialities> GetSpecialist(){
       return repLessonSpecialities.findAll();
    }

    @Transactional(readOnly=false)
    @PostMapping("/specialist")
    public List<Specialities> PostSpecialist(@RequestParam("id") Specialities specialities, Model model){
        repLessonSpecialities.delete(specialities);
        return repLessonSpecialities.findAll();
    }

    @Transactional(readOnly=true)
    @GetMapping("/lessons/{id}")
    public List<Lesson> GetLessons(@PathVariable("id") Specialities specialities){
        return repLesson.findBySpecialities(specialities);
    }

    @Transactional(readOnly=false)
    @PostMapping("/lessons/{id}")
    public List<Lesson> PostLessons(@PathVariable("id") Specialities specialities, @RequestParam("id") Lesson lesson){
        repLesson.delete(lesson);
        return repLesson.findBySpecialities(specialities);
    }

    @Transactional(readOnly=true)
    @GetMapping("/lessons/schedule/{id}")
    public List<LessoneSchedulesData> GetLessonsScheduleList(@PathVariable("id") Lesson lesson){
        return repLessoneSchedulesData.findByLesson(lesson);
    }

    @Transactional(readOnly=false)
    @PostMapping("/lessons/schedule/{id}")
    public List<LessoneSchedulesData> GetLessonsScheduleList(@PathVariable("id") Lesson lesson, @RequestParam("id") LessoneSchedulesData lessoneSchedulesData){
        repLessoneSchedulesData.delete(lessoneSchedulesData);
        return repLessoneSchedulesData.findByLesson(lesson);
    }

    @GetMapping
    public Mess GetCalendare(){
        return new Mess("This map support post for download calendare data with formate *.ics");
    }

    @Transactional(readOnly=false)
    @PostMapping
    public Mess PostCalendare(@RequestParam(name = "lesson") String les, @RequestParam(name = "file") MultipartFile mlfile, Model model) throws IOException, ParserException, ParseException {

        if (!StringUtils.isEmpty(les)) {
            if (mlfile != null && !mlfile.getOriginalFilename().isEmpty()) {

                UUID uuid = UUID.randomUUID();
                String path = System.getProperty("user.dir") + "/src/main/resources/files/" + uuid + mlfile.getOriginalFilename();
                File file = new File(path);
                mlfile.transferTo(file);

                FileReader fileReader = new FileReader(file);
                new Scanner(fileReader);

                System.setProperty("ical4j.unfolding.relaxed", "true");
                System.setProperty("net.fortuna.ical4j.timezone.cache.impl", MapTimeZoneCache.class.getName());

                FileInputStream fileInputStream = new FileInputStream(file);
                CalendarBuilder cb = new CalendarBuilder();

                Calendar calendar = cb.build(fileInputStream);
                ComponentList<CalendarComponent> componentList = calendar.getComponents();

                Specialities specialities = repLessonSpecialities.findByName(les);

                if (specialities == null) {
                    specialities = new Specialities(les);
                    repLessonSpecialities.save(specialities);
                }

                for (Component cmp : componentList) {

                    String name = cmp.getProperty("SUMMARY").getValue();
                    Lesson lesson = repLesson.findByName(name);

                    if (lesson == null) {
                        lesson = new Lesson(name);
                        repLesson.save(lesson);
                    }

                    lesson.setSpecialities(specialities);

                    SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd'T'HHmmss'Z'");
                    LessoneSchedulesData lesScedData = new LessoneSchedulesData();
                    lesScedData.setLocation(StringUtils.isEmpty(cmp.getProperty("LOCATION").getValue()) ? "null" : cmp.getProperty("LOCATION").getValue());
                    lesScedData.setCreated(StringUtils.isEmpty(cmp.getProperty("CREATED").getValue()) ? null : format.parse(cmp.getProperty("CREATED").getValue()));
                    lesScedData.setLastmodified(StringUtils.isEmpty(cmp.getProperty("LAST-MODIFIED").getValue()) ? null : format.parse(cmp.getProperty("LAST-MODIFIED").getValue()));
                    lesScedData.setStart(StringUtils.isEmpty(cmp.getProperty("DTSTART").getValue()) ? null : format.parse(cmp.getProperty("DTSTART").getValue()));
                    lesScedData.setStop(StringUtils.isEmpty(cmp.getProperty("DTEND").getValue()) ? null : format.parse(cmp.getProperty("DTEND").getValue()));
                    lesScedData.setDescrib(StringUtils.isEmpty(cmp.getProperty("DESCRIPTION").getValue()) ? "null" : cmp.getProperty("DESCRIPTION").getValue());
                    lesScedData.setUuid(StringUtils.isEmpty(cmp.getProperty("UID").getValue()) ? "null" : cmp.getProperty("UID").getValue());
                    lesScedData.setLesson(lesson);
                    repLessoneSchedulesData.save(lesScedData);

                }

                return new Mess("Data successfuly saved");
            } else {
                return new Mess("File is emty or emty");
            }
        } else {
            return new Mess("Name of lesson is emty");
        }
    }
}
