package kpuapi.kpulecture.repository;

import kpuapi.kpulecture.domain.Major;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface MajorRepository extends JpaRepository<Major, Long> {
}
