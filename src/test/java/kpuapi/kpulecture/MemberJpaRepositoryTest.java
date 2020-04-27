package kpuapi.kpulecture;

import kpuapi.kpulecture.domain.Member;
import kpuapi.kpulecture.repository.MemberJpaRepository;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;


@RunWith(SpringRunner.class)
@SpringBootTest
public class MemberJpaRepositoryTest {

    @Autowired
    MemberJpaRepository memberJpaRepository;

    @Test
    @Transactional
    @Rollback(false)
    public void testMember() {
        Member member = new Member();
        member.setName("MemberA");
        long savedId = memberJpaRepository.save(member);

        Member findMember = memberJpaRepository.find(savedId);

        Assertions.assertThat(findMember.getId()).isEqualTo(member.getId());
        Assertions.assertThat(findMember.getName()).isEqualTo(member.getName());

        Assertions.assertThat(findMember).isEqualTo(member); //JPA 엔티티 동일성 보장
    }


}
