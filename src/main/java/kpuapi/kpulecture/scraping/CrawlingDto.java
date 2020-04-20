package kpuapi.kpulecture.scraping;

import lombok.Data;

@Data
public class CrawlingDto {

    private String lectureIdx;
    private String lectureCode;
    private String lectureName;
    private String lectureGubun;
    private String lectureYear;
    private String professorName;
    private String lectureSemester;
    private String lectureDate;
    private String lectureRoom;

    public CrawlingDto(String lectureIdx, String lectureCode, String lectureName, String lectureGubun,
                       String lectureYear, String professorName, String lectureSemester, String lectureDate, String lectureRoom) {
        this.lectureIdx = lectureIdx;
        this.lectureCode = lectureCode;
        this.lectureName = lectureName;
        this.lectureGubun = lectureGubun;
        this.lectureYear = lectureYear;
        this.professorName = professorName;
        this.lectureSemester = lectureSemester;
        this.lectureDate = lectureDate;
        this.lectureRoom = lectureRoom;
    }
}
