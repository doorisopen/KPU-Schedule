package kpuapi.kpulecture.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PUBLIC) // 생성자 접근 권한 설정: 테스트를 위해 임시로 Public, 의도한 코드는 PROTECTED
@Getter @Setter
public class Lecture {

    @Id
    @GeneratedValue
    @Column(name = "lecture_id")
    private Long id;

    private String lectureCode;
    private String lectureName;
    private String lectureGubun;
    private int lectureYear;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "professor_id")
    private Professor professor;

    private String lectureSemester;
    private String lectureDate;
    private String lectureRoom;

    //==연관관계 메서드==//
    public void setProfessor(Professor professor) {
        this.professor = professor;
        professor.getLectures().add(this);
    }

    //==생성 메서드==//


    //==비즈니스 로직==//
    public void change(String lectureCode, String lectureName, Professor professor, String lectureSemester, String lectureDate, String lectureRoom) {
        this.setLectureCode(lectureCode);
        this.setLectureName(lectureName);
        this.setProfessor(professor);
        this.setLectureSemester(lectureSemester);
        this.setLectureDate(lectureDate);
        this.setLectureRoom(lectureRoom);
    }

}
