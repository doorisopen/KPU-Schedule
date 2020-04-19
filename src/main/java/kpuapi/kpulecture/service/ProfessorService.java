package kpuapi.kpulecture.service;

import kpuapi.kpulecture.domain.Major;
import kpuapi.kpulecture.domain.Professor;
import kpuapi.kpulecture.repository.MajorRepository;
import kpuapi.kpulecture.repository.ProfessorRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProfessorService {

    private final ProfessorRepository professorRepository;
    private final MajorRepository majorRepository;

    /**
     * 교수 등록
     * @param professor
     * @return id
     */
    @Transactional
    public Long join(Professor professor, Long majorId) {
        validateDuplicateProfessor(professor); // 중복 등록 검증

        // 전공 정보 등록
        Major major = majorRepository.findOne(majorId);
        professor.setMajor(major);

        professorRepository.save(professor);
        return professor.getId();
    }

    /**
     * 중복 등록 검증
     * @param professor
     */
    private void validateDuplicateProfessor(Professor professor) {
        List<Professor> findProfessors = professorRepository.findByProfessorName(professor.getProfessorName());
        if(!findProfessors.isEmpty()) {
            throw new IllegalStateException("이미 등록된 정보입니다.");
        }
    }

    /**
     * 전체 교수 조회
     * @return professor list
     */
    public List<Professor> findProfessors() {
        return professorRepository.findAll();
    }

    /**
     * 교수 조회
     * @param professorId
     * @return professor
     */
    public Professor findOne(Long professorId) {
        return professorRepository.findOne(professorId);
    }
}
