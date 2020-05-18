package kpuapi.kpulecture.domain;

import kpuapi.kpulecture.service.UsageService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class UsageRepositoryTest {

    @Autowired
    EntityManager em;
    @Autowired
    UsageRepository usageRepository;
    @Autowired
    UsageService usageService;


    @Test
    public void localDate_포멧확인한다() throws Exception {
        //given
        LocalDate localDate = LocalDate.now();
        //when
        //2020-**-**
        System.out.println("localDate ======>" + localDate);
        //then
    }

    @Test
    public void 최초_사용량_등록한다() throws Exception {
        LocalDate now = LocalDate.now();
        //given
        usageRepository.save(Usage.builder()
                .date(now)
                .build());
        //when
        List<Usage> findUsage = usageRepository.findAll();

        //then
        assertThat(findUsage.get(0).getDate()).isEqualTo(now);
        assertThat(findUsage.get(0).getUsed()).isEqualTo(0);
    }

    @Test
    public void 최상위_날짜를_가져온다() throws Exception {
        //given
        LocalDate date1 = LocalDate.of(2020,05,18);
        LocalDate date2 = LocalDate.of(2020,05,17);
        LocalDate date3 = LocalDate.of(2020,05,16);
        usageRepository.save(Usage.builder()
                .date(date2)
                .used(0)
                .build());
        usageRepository.save(Usage.builder()
                .date(date1)
                .used(0)
                .build());
        usageRepository.save(Usage.builder()
                .date(date3)
                .used(0)
                .build());

        //when
        List<Usage> findAll = usageRepository.findAll();
        Usage findTop = usageRepository.findTopByOrderByDateDesc();

        //then
        for (Usage usage : findAll) {
            System.out.println("usage ===> " + usage);
        }
        System.out.println("findTop ===> " + findTop);
    }

    @Test
    public void 테이블_비어있을때_null() throws Exception {
        Usage findTop = usageRepository.findTopByOrderByDateDesc();
        System.out.println("findTop = " + findTop);
    }

    @Test
    public void 중복_확인후_사용량증가() throws Exception {
        //given
        LocalDate date = LocalDate.now();
        usageRepository.save(Usage.builder()
                .date(date)
                .build());

        //when
        Usage duplicateUsage = Usage.builder()
                .date(date)
                .build();
        usageService.use(duplicateUsage);

        List<Usage> all = usageRepository.findAll();

        //then
        for (Usage usage : all) {
            System.out.println("usage = " + usage);
        }
    }

    @Test
    public void 중복_확인후_새로운사용량_등록한다() throws Exception {
        //given
        LocalDate previousDate = LocalDate.of(2020,05,17);
        Usage preUsage = Usage.builder()
                .date(previousDate)
                .build();
        usageRepository.save(preUsage);

        //when
        LocalDate now = LocalDate.now();
        Usage newUsage = Usage.builder()
                .date(now)
                .build();
        usageService.use(newUsage);

        List<Usage> findAll = usageRepository.findAll();

        //then
        System.out.println("findAll = " + findAll);
    }
}