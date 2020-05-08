package kpuapi.kpulecture.service;

import kpuapi.kpulecture.controller.form.MajorForm;
import kpuapi.kpulecture.domain.Major;
import kpuapi.kpulecture.repository.MajorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MajorService {

    private final MajorRepository majorRepository;

    @Transactional
    public void save(MajorForm form) {
        Major major = new Major(form.getMajorName());
        majorRepository.save(major);
    }

    @Transactional
    public void changeMajorName(Long id, String majorName) {
        Optional<Major> findMajor = majorRepository.findById(id);
        findMajor.get().changeMajorName(majorName);
    }
}
