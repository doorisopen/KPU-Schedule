package kpuapi.kpulecture.service;

import kpuapi.kpulecture.domain.Major;
import kpuapi.kpulecture.domain.MajorCode;
import kpuapi.kpulecture.repository.MajorJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MajorService {

    private final MajorJpaRepository majorJpaRepository;


    @Transactional
    public Long save(Major major) {
        majorJpaRepository.save(major);
        return major.getId();
    }

    public List<Major> findMajor() {
        return majorJpaRepository.findAll();
    }

    public Major findOne(Long majorId) {
        return majorJpaRepository.findOne(majorId);
    }

    @Transactional
    public void updateMajor(Long majorId, MajorCode majorCode) {
        Major findMajor = majorJpaRepository.findOne(majorId);
        findMajor.change(majorCode);
    }
}
