package org.kpu.schedule;

import java.nio.charset.Charset;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class lectureController {
	
	
	@Autowired
	lectureCrawler crawling;
	
	/*	Lecture List
	 *	 
	 * 
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/lectureLoading", method = RequestMethod.GET)
	public JSONObject lectureLoading(Model model 
							, lectureVO vo) throws Exception {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
		headers.set("My-Header", "MyHeaderValue");
		
		JSONObject jsonObject = new JSONObject();
		JSONObject lectureInfo = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		
		// 강의 구분자
		String SCH_ORG_SECT = "G";
		// 페이지 전체 개수 
		int pageNo = 1;
		int totalPage = crawling.getPageCnt(SCH_ORG_SECT);
		System.out.println("totalPage--->"+totalPage);
		
		lectureInfo.put("lectureInfo", "한국산업기술대학교 강의 정보");
		lectureInfo.put("made", "이태웅");
		
		while(pageNo <= totalPage) {
			jsonArray = crawling.lectureCrawling(pageNo, SCH_ORG_SECT);
			jsonObject.put("lectureList", jsonArray);
			pageNo = pageNo + 10;
			lectureInfo = new JSONObject();
			lectureInfo.put("dataSize", jsonArray.size());
		}
		
		
		model.addAttribute("lectureObject",jsonObject);
		
		return jsonObject;
	}

}
