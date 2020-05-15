package kpuapi.kpulecture.domain.school;

import kpuapi.kpulecture.domain.school.Lecture;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LectureRepository extends JpaRepository<Lecture, Long> {

    @Query("select l from Lecture l left join fetch l.professor")
    List<Lecture> findLectureFetchJoin();

}
