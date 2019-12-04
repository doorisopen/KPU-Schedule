package org.kpu.schedule;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class crawlingFunction {
	
	private static int totalList;
	

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		JSONObject jsonObject = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		JSONArray lectureInfoArray = new JSONArray();
		
		int pageNo = 1;
		String SCH_ORG_SECT = "G";
		
		totalList = pageInit(SCH_ORG_SECT);
		System.out.println("TOTAL LIST -> "+ totalList);	
		
//		lectureInfo.put("lectureInfo", "한국산업기술대학교 강의 정보");
//		lectureInfo.put("made", "이태웅");
//		lectureInfo.put("totalList", totalList);
		
		while(pageNo <= totalList) {
			jsonArray = schoolCrawling(jsonArray ,pageNo, SCH_ORG_SECT);
			pageNo = pageNo + 10;
		}
		System.out.println("jsonArray: "+ jsonArray);
		
		/*
		 *	 
		 */
		for(int i = 0; i < jsonArray.size(); i++) {
			JSONObject object = (JSONObject) jsonArray.get(i);
			if(!object.isEmpty()) {
				JSONObject lectureInfo = new JSONObject();
				String lectureYear = object.get("lectureYear").toString();
				String lectureIdx = object.get("lectureIdx").toString();		
				String lectureGubun = object.get("lectureGubun").toString();
				String lectureName = object.get("lectureName").toString();
				String code = object.get("code").toString();		
				String lectureSemester = object.get("lectureSemester").toString();
				String lectureDate = object.get("lectureDate").toString();
				String professorName = object.get("professorName").toString();
				String lectureCode = object.get("lectureCode").toString();
				
				lectureInfo.put("lectureYear", lectureYear);
				lectureInfo.put("lectureIdx", lectureIdx);
				lectureInfo.put("lectureGubun", lectureGubun);
				lectureInfo.put("lectureName", lectureName);
				lectureInfo.put("code", code);
				lectureInfo.put("lectureSemester", lectureSemester);
				lectureInfo.put("lectureDate", lectureDate);
				lectureInfo.put("professorName", professorName);
				lectureInfo.put("lectureCode", lectureCode);
				lectureInfoArray.add(lectureInfo);
			}
		}
		jsonObject.put("lectureList", lectureInfoArray);
		System.out.println("lectureInfoArray--->"+lectureInfoArray);
		System.out.println("jsonArray--->"+jsonArray.size());
		System.out.println("lectureInfoArray--->"+lectureInfoArray.size());
		System.out.println("jsonObject--->"+jsonObject.size());
		
	}

	public static int pageInit(String SCH_ORG_SECT) {
		try {
			URL url = new URL("http://eclass.kpu.ac.kr/ilos/st/main/course_ing_list.acl?SCH_ORG_SECT="+SCH_ORG_SECT);
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), "utf-8"));
			
			String line = reader.readLine();
				
			while((line = reader.readLine()) != null) {
				if(line.contains("<a href=javascript:listPage('") && line.contains("img")) {
					String tmp = line.split("'")[1];
					tmp.trim();
					totalList = Integer.parseInt(line.split("'")[1]);
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
		
		return totalList;
	}
	
	@SuppressWarnings({ "unchecked", "unused" })
	public static JSONArray schoolCrawling(JSONArray jsonArray, int pageNo, String SCH_ORG_SECT) {
		JSONObject lectureInfo = new JSONObject();
		try {
			URL url = new URL("http://eclass.kpu.ac.kr/ilos/st/main/course_ing_list.acl?SCH_ORG_SECT="+SCH_ORG_SECT+"&start="+pageNo);
			
			System.out.println(url);
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), "utf-8"));
			
			String line = reader.readLine();
			line.trim();
			lectureInfo.clear();
			while((line = reader.readLine()) != null) {
				
				if(line.contains("<tr")) {
					if(line.contains("<tr   id=")) {
						lectureInfo = new JSONObject();
						
						String code = line.split(">")[0];
						code.trim();
						code = code.substring(20, 36);
						String lectureGubun = code.substring(0,1);
						String lectureCode = code.substring(6,14);
						lectureInfo.put("code", code);
						lectureInfo.put("lectureGubun", lectureGubun);
						lectureInfo.put("lectureCode", lectureCode);
						
					} else if(line.contains("<tr class=\"list\"")) {
						lectureInfo = new JSONObject();
						
						String code = line.split(">")[0];
						code.trim();
						code = code.substring(32, 48);
						String lectureGubun = code.substring(0,1);
						String lectureCode = code.substring(6,14);
						lectureInfo.put("code", code);
						lectureInfo.put("lectureGubun", lectureGubun);
						lectureInfo.put("lectureCode", lectureCode);
					}
				}
				if(line.contains("<td class=\"number")) {
					String lectureIdx = line.split(">")[1].split("<")[0];
					lectureIdx.trim();

					lectureInfo.put("lectureIdx", lectureIdx);
				}
				
				if(line.contains("<td class=\"name") && line.contains("<br>")) {
					String lectureYear = line.split(">")[1].split("<")[0];
					String lectureSemester = line.split(">")[2].split("<")[0];

					lectureInfo.put("lectureYear", lectureYear);
					lectureInfo.put("lectureSemester", lectureSemester);
				}
				
				if(line.contains("<td class=\"left") && line.contains("<br>")) {
					String lectureName = line.split(">")[1].split("<")[0];
					lectureName.trim();

					String professorName = line.split(">")[2].split("<")[0];

					lectureInfo.put("lectureName", lectureName);
					lectureInfo.put("professorName", professorName);
					
				} else if(line.contains("<td class=\"left")) {
					String lectureDate = line.split(">")[1].split("/td")[0];
					if(lectureDate.substring(0).equals("<")) {
						lectureDate="NULL";
					} else {
						lectureDate = line.split(">")[1].split("</td")[0];
						lectureDate.trim();
					}
					lectureInfo.put("lectureDate", lectureDate);
				}
				if(line.contains("</tr")) {
					jsonArray.add(lectureInfo);
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
		return jsonArray;
	}
	
	
//	@SuppressWarnings("unchecked")
//	public static void schoolCrawling_save(int listNo) {
//		
//		try {
//			URL url = new URL("http://eclass.kpu.ac.kr/ilos/st/main/course_ing_list.acl?start="+listNo);
//			
//			System.out.println(url);
//			
//			BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), "utf-8"));
//			
//			String line = reader.readLine();
//			line.trim();
//			while((line = reader.readLine()) != null) {
//				if(line.contains("<tr   id=")) {
//					jsonArray.add(scheduleInfo);
//					jsonObject.put("schedule", jsonArray);
//					System.out.println();
//					System.out.println("--------------");
//					line.trim();
//					String code = line.split(">")[0];
//					code.trim();
//					System.out.println("code: "+code.substring(20, 36));
//					scheduleInfo.put("code", code.substring(20, 36));
//				} else if(line.contains("<tr class=\"list\"")) {
//					jsonArray.add(scheduleInfo);
//					jsonObject.put("schedule", jsonArray);
//					System.out.println();
//					System.out.println("--------------");
//					line.trim();
//					String code = line.split(">")[0];
//					
//					code.trim();
//					System.out.println("code: "+code.substring(32, 48));
//					scheduleInfo.put("code", code.substring(32, 48));
//				}
//				
//				if() {
//					if(line.contains("<td class=\"number")) {
//						String no = line.split(">")[1].split("<")[0];
//						no.trim();
//						System.out.println("No: "+no);
//						scheduleInfo.put("no", no);
//					}
//					if(line.contains("<td class=\"name") && line.contains("<br>")) {
//						String date_Year = line.split(">")[1].split("<")[0];
//						String date_Semester = line.split(">")[2].split("<")[0];
//						System.out.println("��¥: "+date_Year + " " + date_Semester);
//						scheduleInfo.put("date_Year", date_Year);
//						scheduleInfo.put("date_Semester", date_Semester);
//					}
//					if(line.contains("<td class=\"left") && line.contains("<br>")) {
//						String lecture_Name = line.split(">")[1].split("<")[0];
//						lecture_Name.trim();
//						System.out.println("���� ��: "+lecture_Name);
//						String professor_Name = line.split(">")[2].split("<")[0];
//						System.out.println("����: "+professor_Name);
//						scheduleInfo.put("lecture_Name", lecture_Name);
//						scheduleInfo.put("professor_Name", professor_Name);
//					} else if(line.contains("<td class=\"left")) {
//						String lecture_Date = line.split(">")[1].split("</td")[0];
//						lecture_Date.trim();
//						System.out.println("���� �Ͻ�: "+lecture_Date);
//						scheduleInfo.put("lecture_Date", lecture_Date);
//					}
//				}
//				
//			}
//			reader.close();
//
//		} catch (UnsupportedEncodingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
	
}
