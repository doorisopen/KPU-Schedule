package org.kpu.schedule;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.nio.charset.Charset;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class lectureController2 {

	
	private static int totalList;
	

	static JSONObject jsonObject = new JSONObject();
	
	JSONObject lectureInfo = new JSONObject();

	static JSONArray jsonArray = new JSONArray();
	
	

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/lectureList", method = RequestMethod.GET)
	public JSONObject getLectureList(Model model) throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		headers.set("My-Header", "MyHeaderValue");
		jsonObject = new JSONObject();
		int pageNo = 1;
		String SCH_ORG_SECT = "G";
		totalList = pageInit(SCH_ORG_SECT);
		System.out.println("totalList: "+totalList);
		
		lectureInfo.put("lectureInfo", "한국산업기술대학교 강의 정보");
		lectureInfo.put("made", "이태웅");
		lectureInfo.put("totalList", totalList);
		
		while(pageNo <= totalList) {
			jsonArray = schoolCrawling(pageNo, SCH_ORG_SECT);
			jsonObject.put("lectureList", jsonArray);
			pageNo = pageNo + 10;
			lectureInfo = new JSONObject();
			lectureInfo.put("dataSize", jsonArray.size());
		}
		
		return jsonObject;
	}
	
	/* getEndPage
	 * @param SCH_ORG_SECT
	 * @return int
	 */
	public static int pageInit(String SCH_ORG_SECT) {
		try {
			// 				   http://eclass.kpu.ac.kr/ilos/st/main/course_ing_list.acl?SCH_ORG_SECT=G
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
	
	@SuppressWarnings("unchecked")
	public JSONArray schoolCrawling(int pageNo, String SCH_ORG_SECT) {
	
		try {
			URL url = new URL("http://eclass.kpu.ac.kr/ilos/st/main/course_ing_list.acl?SCH_ORG_SECT="+SCH_ORG_SECT+"&start="+pageNo);
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), "utf-8"));
			
			String line = reader.readLine();
			line.trim();
			while((line = reader.readLine()) != null) {
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

				if(line.contains("<td class=\"number")) {
					String lectureIdx = line.split(">")[1].split("<")[0];
					lectureIdx.trim();

					lectureInfo.put("lectureIdx", lectureIdx);
				}
				
				if(line.contains("<td class=\"name") && line.contains("<br>")) {
					String date_Year = line.split(">")[1].split("<")[0].substring(0,4);
					String lectureSemester = line.split(">")[2].split("<")[0];

					lectureInfo.put("lectureYear", date_Year);
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
	

	

}
