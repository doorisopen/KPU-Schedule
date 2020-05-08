package kpuapi.kpulecture.repository;

import kpuapi.kpulecture.domain.Lecture;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LectureRepository extends JpaRepository<Lecture, Long> {

    @Query("select l from Lecture l left join fetch l.professor")
    List<Lecture> findLectureFetchJoin();

}
