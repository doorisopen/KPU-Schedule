package kpuapi.kpulecture.domain.school;

import kpuapi.kpulecture.domain.school.Major;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface MajorRepository extends JpaRepository<Major, Long> {
}
