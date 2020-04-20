package kpuapi.kpulecture.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Professor {

    @Id
    @GeneratedValue
    @Column(name = "professor_id")
    private Long id;

    private String professorName;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "major_id")
    private Major major;

    @OneToMany(mappedBy = "professor")
    private List<Lecture> lectures = new ArrayList<>();

    //==연관관계 메서드==//
//    public void setMajor(Major major) {
//        this.major = major;
//        major.setProfessor(this);
//    }

}