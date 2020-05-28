package kpuapi.kpulecture;

import kpuapi.kpulecture.api.dto.UsageResponseDto;
import kpuapi.kpulecture.domain.Usage;
import kpuapi.kpulecture.domain.UsageRepository;
import kpuapi.kpulecture.domain.school.Lecture;
import kpuapi.kpulecture.domain.school.Professor;
import kpuapi.kpulecture.domain.school.ProfessorRepository;
import kpuapi.kpulecture.domain.user.User;
import kpuapi.kpulecture.domain.user.UserRepository;
import kpuapi.kpulecture.scraping.CrawlingDto;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Profile("local")
@Component
@RequiredArgsConstructor
public class initDb {

    private final InitService initService;

    @PostConstruct
    public void init() {

        String lectureGubun = "A";
        int totalPage = getPageCnt(lectureGubun);

        int pageNo = 1;
        List<CrawlingDto> result = lectureCrawling(pageNo, lectureGubun);

        for (CrawlingDto data : result) {
            initService.saveLectureData(data);
        }
        initService.initUsage();
    }

    /**
     * 페이지 개수 구하기
     * @Return int
     */
    public int getPageCnt(String SCH_ORG_SECT) {

        int totalPage = 0;
        try {
            URL url = new URL("http://eclass.kpu.ac.kr/ilos/st/main/course_ing_list.acl?SCH_ORG_SECT="+SCH_ORG_SECT);
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), "utf-8"));

            String line = reader.readLine();

            while((line = reader.readLine()) != null) {
                if(line.contains("<a href=javascript:listPage('") && line.contains("img")) {
                    String tmp = line.split("'")[1];
                    tmp.trim();
                    totalPage = Integer.parseInt(line.split("'")[1]);
                }
            }
            reader.close();

        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return totalPage;
    }

    /**
     * 강의 정보 데이터 스크래핑
     * @Return List
     */
    public List<CrawlingDto> lectureCrawling(int pageNo, String SCH_ORG_SECT) {
        List<CrawlingDto> data = new ArrayList<>();

        try {
            URL url = new URL("http://eclass.kpu.ac.kr/ilos/st/main/course_ing_list.acl?SCH_ORG_SECT="+SCH_ORG_SECT+"&start="+pageNo);
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), "utf-8"));
            String line = reader.readLine();
            line.trim();

            String lectureIdx = "";
            String lectureCode = "";
            String lectureName = "";
            String professorName = "";
            String lectureGubun = "";
            String lectureYear = "";
            String lectureSemester = "";
            String lectureDate = "";
            String lectureRoom = "";

            while((line = reader.readLine()) != null) {
                if(line.contains("<tr   id=")) {
                    String code = line.split(">")[0];
                    code.trim();
                    code = code.substring(20, 36);
                    lectureGubun = code.substring(0,1);
                    lectureCode = code.substring(6,14);
                } else if(line.contains("<tr class=\"list\"")) {
                    String code = line.split(">")[0];
                    code.trim();
                    code = code.substring(32, 48);
                    lectureGubun = code.substring(0,1);
                    lectureCode = code.substring(6,14);
                }

                if(line.contains("<td class=\"number")) {
                    lectureIdx = line.split(">")[1].split("<")[0];
                    lectureIdx.trim();
                }

                if(line.contains("<td class=\"name") && line.contains("<br>")) {
                    lectureYear = line.split(">")[1].split("<")[0].substring(0,4);
                    lectureSemester = line.split(">")[2].split("<")[0];
                }

                if(line.contains("<td class=\"left") && line.contains("<br>")) {
                    lectureName = line.split(">")[1].split("<")[0];
                    lectureName.trim();
                    professorName = line.split(">")[2].split("<")[0];

                }
                else if(line.contains("<td class=\"left")) {
                    lectureDate = line.split(">")[1].split("/td")[0];
                    if(lectureDate.substring(0,0).equals("<")) {
                        lectureDate="NULL";
                        lectureRoom="NULL";
                    } else {
                        lectureRoom = lectureDate.substring(lectureDate.indexOf("(")+1,lectureDate.indexOf(")"));
                        lectureDate = line.split(">")[1].split("</td")[0];
                        lectureDate = lectureDate.substring(0,lectureDate.indexOf("("));
                        lectureDate.trim();
                        lectureRoom.trim();
                    }

                }
                if(line.contains("</tr")) {
                    //db save
                    CrawlingDto crawlingDto = new CrawlingDto(lectureIdx,
                            lectureCode,
                            lectureName,
                            lectureGubun,
                            lectureYear,
                            professorName,
                            lectureSemester,
                            lectureDate,
                            lectureRoom);
                    if(lectureIdx != "") {
                        data.add(crawlingDto);
                    }
                }
            }
            reader.close();

        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return data;
    }

    @Component
    @RequiredArgsConstructor
    static class InitService {
        @PersistenceContext
        private final EntityManager em;
        private final ProfessorRepository professorRepository;
        private final UsageRepository usageRepository;

        /**
         * Major 데이터 체크 -> Professor 데이터 체크 -> Lecture 등록
         */
        @Transactional
        public void saveLectureData(CrawlingDto crawlingDto) {
            Lecture lecture = new Lecture();


            //교수 데이터 체크
            List<Professor> findProfessors = professorRepository.findByProfessorName(crawlingDto.getProfessorName());

            if(findProfessors.isEmpty()) {
                Professor professor = new Professor(crawlingDto.getProfessorName());
                em.persist(professor);
                lecture.setProfessor(professor);
            } else {
                Optional<Professor> findProfessor = professorRepository.findById(findProfessors.get(0).getId());
                lecture.setProfessor(findProfessor.get());
            }
            //강의 등록
            lecture.setLectureCode(crawlingDto.getLectureCode());
            lecture.setLectureName(crawlingDto.getLectureName());
            lecture.setLectureGubun(crawlingDto.getLectureGubun());
            lecture.setLectureYear(Integer.parseInt(crawlingDto.getLectureYear()));

            lecture.setLectureSemester(crawlingDto.getLectureSemester());
            lecture.setLectureDate(crawlingDto.getLectureDate());
            lecture.setLectureRoom(crawlingDto.getLectureRoom());

            em.persist(lecture);
        }

        @Transactional
        public void initUsage() {
            usageRepository.save(Usage.builder()
                    .date(LocalDate.of(2020, 05, 16))
                    .used(25)
                    .build());
            usageRepository.save(Usage.builder()
                    .date(LocalDate.of(2020, 05, 17))
                    .used(10)
                    .build());
            usageRepository.save(Usage.builder()
                    .date(LocalDate.of(2020, 05, 18))
                    .used(20)
                    .build());
            usageRepository.save(Usage.builder()
                    .date(LocalDate.of(2020, 05, 19))
                    .used(30)
                    .build());
        }
    }

}
