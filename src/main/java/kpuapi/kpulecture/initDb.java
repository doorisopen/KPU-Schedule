package kpuapi.kpulecture;

import kpuapi.kpulecture.domain.Lecture;
import kpuapi.kpulecture.domain.Professor;
import kpuapi.kpulecture.repository.MajorJpaRepository;
import kpuapi.kpulecture.repository.ProfessorJpaRepository;
import kpuapi.kpulecture.scraping.CrawlingDto;
import kpuapi.kpulecture.domain.MajorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


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
            initService.saveData(data);
        }

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
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final EntityManager em;
        private final ProfessorJpaRepository professorRepository;
        private final MajorJpaRepository majorJpaRepository;


        public MajorCode findCode(String code) {
            for (MajorCode value : MajorCode.values()) {
                if(value.getCode().equals(code)) {
                    return value;
                }
            }
            return null;
        }
        /**
         * Major 데이터 체크 -> Professor 데이터 체크 -> Lecture 등록
         *
         */
        public void saveData(CrawlingDto crawlingDto) {
            Lecture lecture = new Lecture();
//            Major major = new Major();
            Professor professor = new Professor();

            //전공 찾기
//            String code = crawlingDto.getLectureCode().substring(0,3);
//            List<Major> majors = majorRepository.findByMajorCode(code);
//            if (majors.isEmpty() && findCode(code) != null) {
//                if(findCode(code) != null) {
//                    major.setMajorCode(findCode(code));
//                    em.persist(major);
//                }
//            }

            //교수 데이터 체크
            List<Professor> findProfessors = professorRepository.findByProfessorName(crawlingDto.getProfessorName());

            if(findProfessors.isEmpty()) {
                professor.setProfessorName(crawlingDto.getProfessorName());
//                professor.setMajor(major);
                em.persist(professor);

                lecture.setProfessor(professor);
            } else {
                Professor findProfessor = professorRepository.findOne(findProfessors.get(0).getId());
                lecture.setProfessor(findProfessor);
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
    }

}
