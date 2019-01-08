package cps2.project.temperature.Entity.Calendars;


import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "lessons")
public class Lesson {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "lesson", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<LessoneSchedulesData> lessoneSchedulesData;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lesson_specialities_id")
    private Specialities specialities;

    public Lesson() {
    }

    public Lesson(String name) {
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

    public void setLessoneSchedulesData(List<LessoneSchedulesData> lessoneSchedulesData) {
        this.lessoneSchedulesData = lessoneSchedulesData;
    }

    public void setSpecialities(Specialities specialities) {
        this.specialities = specialities;
    }
}
