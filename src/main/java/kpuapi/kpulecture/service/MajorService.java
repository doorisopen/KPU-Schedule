package kpuapi.kpulecture.service;

import kpuapi.kpulecture.domain.Major;
import kpuapi.kpulecture.domain.MajorCode;
import kpuapi.kpulecture.repository.MajorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MajorService {

    private final MajorRepository majorRepository;


    @Transactional
    public Long save(Major major) {
        majorRepository.save(major);
        return major.getId();
    }

    public List<Major> findMajor() {
        return majorRepository.findAll();
    }

    public Major findOne(Long majorId) {
        return majorRepository.findOne(majorId);
    }

    @Transactional
    public void updateMajor(Long majorId, MajorCode majorCode) {
        Major findMajor = majorRepository.findOne(majorId);
        findMajor.change(majorCode);
    }
}
