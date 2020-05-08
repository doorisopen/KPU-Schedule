package kpuapi.kpulecture.service;

import kpuapi.kpulecture.domain.Major;
import kpuapi.kpulecture.domain.Professor;
import kpuapi.kpulecture.repository.MajorRepository;
import kpuapi.kpulecture.repository.ProfessorRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.Assert.*;

@SpringBootTest
@Transactional
public class ProfessorServiceTest {

    @Autowired ProfessorService professorService;
    @Autowired EntityManager em;

    /**
     * 중복 등록 예외가 발생한다.
     * @throws Exception
     */
    @Test
    public void 중복_등록_예외() throws Exception {
        //given
        Professor professor1 = new Professor("Kim");
        Professor professor2 = new Professor("Kim");

        //when
        professorService.save(professor1);
        professorService.save(professor2); // 예외 발생

        //then
        fail("예외가 발생해야 하는데...?");
    }

}
