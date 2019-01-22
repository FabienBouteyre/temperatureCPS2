package cps2.project.temperature.Entity.Calendars;


import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "lesson_specialities")
public class Specialities {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "specialities")
    private List<Lesson> lessons;

    public Specialities() {
    }

    public Specialities(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public List<Lesson> getLessons() {
//        return lessons;
//    }

    public void setLessons(List<Lesson> lessons) {
        this.lessons = lessons;
    }
}
