package kpuapi.kpulecture.service;

import kpuapi.kpulecture.domain.Major;
import kpuapi.kpulecture.domain.Professor;
import kpuapi.kpulecture.repository.MajorJpaRepository;
import kpuapi.kpulecture.repository.ProfessorJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProfessorService {

    private final ProfessorJpaRepository professorJpaRepository;
    private final MajorJpaRepository majorJpaRepository;

    /**
     * 교수 등록
     * @param professor
     * @return id
     */
    @Transactional
    public Long join(Professor professor, Long majorId) {
        validateDuplicateProfessor(professor); // 중복 등록 검증

        // 전공 정보 등록
        Major major = majorJpaRepository.findOne(majorId);
        professor.setMajor(major);

        professorJpaRepository.save(professor);
        return professor.getId();
    }

    /**
     * 중복 등록 검증
     * @param professor
     */
    private void validateDuplicateProfessor(Professor professor) {
        List<Professor> findProfessors = professorJpaRepository.findByProfessorName(professor.getProfessorName());
        if(!findProfessors.isEmpty()) {
            throw new IllegalStateException("이미 등록된 정보입니다.");
        }
    }

    /**
     * 전체 교수 조회
     * @return professor list
     */
    public List<Professor> findProfessors() {
        return professorJpaRepository.findAll();
    }

    /**
     * 교수 조회
     * @param professorId
     * @return professor
     */
    public Professor findOne(Long professorId) {
        return professorJpaRepository.findOne(professorId);
    }
}
