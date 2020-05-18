package kpuapi.kpulecture.domain;

import org.springframework.data.jpa.repository.JpaRepository;


public interface UsageRepository extends JpaRepository<Usage, Long> {

    Usage findTopByOrderByDateDesc();
}
