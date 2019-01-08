package cps2.project.temperature.Controller;


import cps2.project.temperature.Entity.Calendars.Lesson;
import cps2.project.temperature.Entity.Calendars.LessoneSchedulesData;
import cps2.project.temperature.Entity.Calendars.Specialities;
import cps2.project.temperature.Repository.RepLesson;
import cps2.project.temperature.Repository.RepLessonSpecialities;
import cps2.project.temperature.Repository.RepLessoneSchedulesData;
import cps2.project.temperature.Service.ServiceCalendaresConvert;
import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.ComponentList;
import net.fortuna.ical4j.model.component.CalendarComponent;
import net.fortuna.ical4j.util.MapTimeZoneCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
import java.util.Scanner;
import java.util.UUID;

@Controller
@RequestMapping(path = "/calendare")
public class CalendareContr {

    @Autowired
    private ServiceCalendaresConvert serviceCalendaresConvert;

    @Autowired
    private RepLesson repLesson;

    @Autowired
    private RepLessoneSchedulesData repLessoneSchedulesData;

    @Autowired
    private RepLessonSpecialities repLessonSpecialities;

    @GetMapping("/specialist")
    public String GetSpecialist(Model model){
        model.addAttribute("specialist", repLessonSpecialities.findAll());
        return "lesson_spec";
    }

    @PostMapping("/specialist")
    public String PostSpecialist(@RequestParam("id") Specialities specialities, Model model){
        repLessonSpecialities.delete(specialities);
        model.addAttribute("specialist", repLessonSpecialities.findAll());
        return "lesson_spec";
    }

    @GetMapping("/lessons/{id}")
    public String GetLessons(@PathVariable("id") Specialities specialities, Model model){
        model.addAttribute("lessons", repLesson.findBySpecialities(specialities));
        model.addAttribute("id", String.valueOf(specialities.getId()));
        return "lessons";
    }

    @PostMapping("/lessons/{id}")
    public String PostLessons(@PathVariable("id") Specialities specialities, @RequestParam("id") Lesson lesson, Model model){
        repLesson.delete(lesson);
        model.addAttribute("lessons", repLesson.findBySpecialities(specialities));
        model.addAttribute("id", String.valueOf(specialities.getId()));
        return "lessons";
    }

    @GetMapping("/lessons/schedule/{id}")
    public String GetLessonsScheduleList(@PathVariable("id") Lesson lesson, Model model){
        model.addAttribute("lessondaties", repLessoneSchedulesData.findByLesson(lesson));
        model.addAttribute("id", lesson.getId());
        return "lessons_data";
    }

    @PostMapping("/lessons/schedule/{id}")
    public String GetLessonsScheduleList(@PathVariable("id") Lesson lesson, @RequestParam("id") LessoneSchedulesData lessoneSchedulesData, Model model){
        repLessoneSchedulesData.delete(lessoneSchedulesData);
        model.addAttribute("lessondaties", repLessoneSchedulesData.findByLesson(lesson));
        model.addAttribute("id", lesson.getId());
        return "lessons_data";
    }

    @GetMapping
    public String GetCalendare(Model model) {
        return "calendare";
    }

    @PostMapping
    public String PostCalendare(@RequestParam(name = "lesson") String les, @RequestParam(name = "file") MultipartFile mlfile, Model model) throws IOException, ParserException, ParseException {

        if (!StringUtils.isEmpty(les)) {
            if (mlfile != null && !mlfile.getOriginalFilename().isEmpty()) {

                UUID uuid = UUID.randomUUID();
                String path = System.getProperty("user.dir") + "/src/main/resources/files/" + uuid + mlfile.getOriginalFilename();
                File file = new File(path);
                mlfile.transferTo(file);

                FileReader fileReader = new FileReader(file);
                Scanner sc = new Scanner(fileReader);

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

                model.addAttribute("mess", "");
            } else {
                model.addAttribute("mess", "File is emty or emty");
            }
        } else {
            model.addAttribute("mess", "Name of lesson is emty");
        }
        return "calendare";
    }


}
