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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class scheduleController {

	
	private static int totalList;
	
	//최종 완성될 JSONObject 선언(전체)
	static JSONObject jsonObject = new JSONObject();

	//person의 JSON정보를 담을 Array 선언
	static JSONArray jsonArray = new JSONArray();
	
	@RequestMapping(value = "/lectureList", method = RequestMethod.GET)
	public JSONObject getReplyList() throws Exception {

		int listNo = 1;

		totalList = pageInit();
		
		HttpHeaders headers = new HttpHeaders();
		
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		
		headers.set("My-Header", "MyHeaderValue");
		
		System.out.print("totalList: "+totalList);
		
//		int mod = totalList % 10 - 1;
//		totalList = totalList - 21;
//		while(listNo <= totalList) {
//			
//			
//			jsonObject = schoolCrawling(listNo);
//			
//			listNo += 10;
//			if(listNo == totalList && mod > 0) {
//				listNo = listNo + 1;
//				jsonObject = schoolCrawling(listNo);
//			}
//		}
		while(listNo <= 111) {
			jsonObject = schoolCrawling(listNo);
			listNo += 10;
		}
		System.out.print("listNo: "+listNo);
		
		return jsonObject;

	}
	
	
	public static int pageInit() {

		try {
			URL url = new URL("http://eclass.kpu.ac.kr/ilos/st/main/course_ing_list.acl");
			
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
	public JSONObject schoolCrawling(int listNo) {
		JSONObject scheduleInfo = new JSONObject();
		
		try {
			URL url = new URL("http://eclass.kpu.ac.kr/ilos/st/main/course_ing_list.acl?start="+listNo);
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), "utf-8"));
			
			String line = reader.readLine();
			line.trim();
			while((line = reader.readLine()) != null) {
				if(line.contains("<tr   id=")) {
					// 과목 정보가 들어갈 JSONObject 선언
					scheduleInfo = new JSONObject();
					
					String code = line.split(">")[0];
					code.trim();
					code = code.substring(20, 36);
					scheduleInfo.put("code", code);
					
				} else if(line.contains("<tr class=\"list\"")) {
					// 과목 정보가 들어갈 JSONObject 선언
					scheduleInfo = new JSONObject();
					
					String code = line.split(">")[0];
					code.trim();
					code = code.substring(32, 48);
					scheduleInfo.put("code", code);
				}

				if(line.contains("<td class=\"number")) {
					String no = line.split(">")[1].split("<")[0];
					no.trim();

					scheduleInfo.put("no", no);
				}
				
				if(line.contains("<td class=\"name") && line.contains("<br>")) {
					String date_Year = line.split(">")[1].split("<")[0];
					String date_Semester = line.split(">")[2].split("<")[0];

					scheduleInfo.put("date_Year", date_Year);
					scheduleInfo.put("date_Semester", date_Semester);
				}
				
				if(line.contains("<td class=\"left") && line.contains("<br>")) {
					String lecture_Name = line.split(">")[1].split("<")[0];
					lecture_Name.trim();

					String professor_Name = line.split(">")[2].split("<")[0];

					scheduleInfo.put("lecture_Name", lecture_Name);
					scheduleInfo.put("professor_Name", professor_Name);
					
				} else if(line.contains("<td class=\"left")) {
					String lecture_Date = line.split(">")[1].split("</td")[0];
					lecture_Date.trim();

					scheduleInfo.put("lecture_Date", lecture_Date);
				}
				if(line.contains("</tr")) {
					
					jsonArray.add(scheduleInfo);
					jsonObject.put("schedule", jsonArray);
					
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
		
		return jsonObject;
	}
}
