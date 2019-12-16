package org.kpu.schedule.lecture;

import java.nio.charset.Charset;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class lectureController {
	
	
	@Autowired
	lectureService service;
	
	
	
	/*	Lecture List
	 *	 
	 * 
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/lectureLoading/{schorgsect}", method = RequestMethod.GET)
	public JSONObject lectureLoading(Model model 
							, lectureVO vo
							,@PathVariable String schorgsect) throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		headers.set("My-Header", "MyHeaderValue");
		
		JSONObject jsonObject = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		JSONArray lectureInfoArray = new JSONArray();
		
		// 강의 구분자 ( A or G )
		String SCH_ORG_SECT = schorgsect;
		// 현재 페이지 
		int pageNo = 1;
		// 페이지 전체 개수
		int totalPage = service.getPageCnt(SCH_ORG_SECT);
//		System.out.println("totalPage--->"+totalPage);
		
		
		while(pageNo <= totalPage) {
			jsonArray = service.lectureCrawling(jsonArray, pageNo, SCH_ORG_SECT);
			pageNo = pageNo + 10;
		}
		
		/*	공백 객체 제거
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
//
//		System.out.println("jsonArray--->"+jsonArray.size());
//		System.out.println("lectureInfoArray--->"+lectureInfoArray.size());
//		System.out.println("jsonObject--->"+jsonObject.size());
		
		return jsonObject;
	}

}
