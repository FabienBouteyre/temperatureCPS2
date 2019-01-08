package cps2.project.temperature.Entity.Calendars;


import org.springframework.context.annotation.Primary;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "lesson_schedules_data")
public class LessoneSchedulesData {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;


    @Column(name = "start")
    private Date start;

    @Column(name = "stop")
    private Date stop;

    @Column(name = "location")
    private String location;

    @Column(name = "lastmodified")
    private Date lastmodified;

    @Column(name = "created")
    private Date created;

    @Column(name = "describ")
    private String describ;

    @Column(name = "uuid")
    private String uuid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lesson_id")
    private Lesson lesson;

    public LessoneSchedulesData() {
    }

    public LessoneSchedulesData(Date start, Date stop, String location, Date lastmodified, Date created, String describ, String uuid, Lesson lesson) {
        this.start = start;
        this.stop = stop;
        this.location = location;
        this.lastmodified = lastmodified;
        this.created = created;
        this.describ = describ;
        this.uuid = uuid;
        this.lesson = lesson;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getStop() {
        return stop;
    }

    public void setStop(Date stop) {
        this.stop = stop;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getLastmodified() {
        return lastmodified;
    }

    public void setLastmodified(Date lastmodified) {
        this.lastmodified = lastmodified;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public void setLesson(Lesson lesson) {
        this.lesson = lesson;
    }

    public String getDescrib() {
        return describ;
    }

    public void setDescrib(String describ) {
        this.describ = describ;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
