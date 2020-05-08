package kpuapi.kpulecture.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Professor {

    @Id
    @GeneratedValue
    @Column(name = "professor_id")
    private Long id;

    private String professorName;

    @JsonIgnore
    @OneToMany(mappedBy = "professor")
    private List<Lecture> lectures = new ArrayList<>();

    public Professor(String professorName) {
        this.professorName = professorName;
    }

    public void changeProfessorName(String professorName) {
        this.setProfessorName(professorName);
    }
}