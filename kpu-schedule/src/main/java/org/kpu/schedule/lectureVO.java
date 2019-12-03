package org.kpu.schedule;

public class lectureVO {
	
	private String lectureInfo;
	private String made;
	
	private String lectureYear;		// 강의 연도
	private String lectureIdx;		// 강의 Index
	private String lectureGubun;	/* 강의 구분 
									 * + G 대학원
									 * + A 대학
	 								 */ 
	private String lectureName;		// 강의 이름
	private String code;			// 코드
	private String lectureSemester;	// 강의 학기
	private String lectureDate;		// 강의 시간
	private String professorName;	// 교수 이름
	private String lectureCode;		/* 강의 코드 
									 * + 기계공학과		:AME
									 * + 기계설계공학과		:AMD
									 * + 메카트로닉스공학과	:AEE
									 * + 전자공학부		:AEE
									 * + 컴퓨터공학부		:ACS
									 * + 게임공학부		:AMM
									 * + 신소재공학과		:AMT
									 * + 생명화학공학과		:ACH
									 * + 디자인학부		:AID
									 * + 경영학부			:AEB
									 * + 나노 광공학과		:ANO
									 * + 에너지 전기 공학과	:AEN
									 * + 교양 기초 교육원	:AAK, AAJ
	 								 */
	private int totalList;
	private int dataSize;

	
	public int getDataSize() {
		return dataSize;
	}
	public void setDataSize(int dataSize) {
		this.dataSize = dataSize;
	}
	public String getLectureInfo() {
		return lectureInfo;
	}
	public void setLectureInfo(String lectureInfo) {
		this.lectureInfo = lectureInfo;
	}
	public String getMade() {
		return made;
	}
	public void setMade(String made) {
		this.made = made;
	}
	
	public String getLectureYear() {
		return lectureYear;
	}
	public void setLectureYear(String lectureYear) {
		this.lectureYear = lectureYear;
	}
	public String getLectureIdx() {
		return lectureIdx;
	}
	public void setLectureIdx(String lectureIdx) {
		this.lectureIdx = lectureIdx;
	}
	public String getLectureGubun() {
		return lectureGubun;
	}
	public void setLectureGubun(String lectureGubun) {
		this.lectureGubun = lectureGubun;
	}
	public String getLectureName() {
		return lectureName;
	}
	public void setLectureName(String lectureName) {
		this.lectureName = lectureName;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getLectureSemester() {
		return lectureSemester;
	}
	public void setLectureSemester(String lectureSemester) {
		this.lectureSemester = lectureSemester;
	}
	public String getLectureDate() {
		return lectureDate;
	}
	public void setLectureDate(String lectureDate) {
		this.lectureDate = lectureDate;
	}
	public String getProfessorName() {
		return professorName;
	}
	public void setProfessorName(String professorName) {
		this.professorName = professorName;
	}
	public String getLectureCode() {
		return lectureCode;
	}
	public void setLectureCode(String lectureCode) {
		this.lectureCode = lectureCode;
	}
	
	public int getTotalList() {
		return totalList;
	}
	public void setTotalList(int totalList) {
		this.totalList = totalList;
	}
}
