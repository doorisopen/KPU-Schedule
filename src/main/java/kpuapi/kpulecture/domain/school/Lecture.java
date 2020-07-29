package kpuapi.kpulecture.domain.school;

import kpuapi.kpulecture.controller.form.LectureForm;
import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PUBLIC) // 생성자 접근 권한 설정: 테스트를 위해 임시로 Public, 의도한 코드는 PROTECTED
@Getter @Setter
@ToString
public class Lecture {

    @Id
    @GeneratedValue
    @Column(name = "lecture_id")
    private Long id;

    private String lectureCode;
    private String lectureName;
    private String lectureGubun;
    private int lectureYear;
    private String lectureSemester;
    private String lectureDate;
    private String lectureRoom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "professor_id")
    private Professor professor;


    //==연관관계 메서드==//
    public void setProfessor(Professor professor) {
        this.professor = professor;
        professor.getLectures().add(this);
    }

    //==생성 메서드==//
    public Lecture(String lectureName, Professor professor) {
        this.lectureName = lectureName;
        setProfessor(professor);
    }

    //==비즈니스 로직==//
    public void change(LectureForm form, Professor professor) {
        this.setLectureCode(form.getLectureCode());
        this.setLectureName(form.getLectureName());
        this.setLectureSemester(form.getLectureSemester());
        this.setLectureDate(form.getLectureDate());
        this.setLectureRoom(form.getLectureRoom());
        this.setProfessor(professor);
    }
}
