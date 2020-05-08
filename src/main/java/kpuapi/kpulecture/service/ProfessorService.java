package kpuapi.kpulecture.service;

import kpuapi.kpulecture.domain.Professor;
import kpuapi.kpulecture.repository.ProfessorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProfessorService {

    private final ProfessorRepository professorRepository;

    @Transactional
    public void save(Professor professor) {
        validateDuplicateMember(professor);
        professorRepository.save(professor);
    }

    private void validateDuplicateMember(Professor professor) {
        List<Professor> findMembers = professorRepository.findByProfessorName(professor.getProfessorName());
        if(!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    @Transactional
    public void changeProfessorName(Long id, String professorName) {
        Optional<Professor> findProfessor = professorRepository.findById(id);
        findProfessor.get().changeProfessorName(professorName);
    }
}
