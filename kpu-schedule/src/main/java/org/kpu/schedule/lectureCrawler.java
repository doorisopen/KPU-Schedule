package org.kpu.schedule;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

import com.sun.org.apache.xpath.internal.operations.Variable;


@Service
public class lectureCrawler {
	
	

	/*	페이지 개수 구하기
	 * 
	 * 
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
	/* 강의 정보 데이터 스크래핑 
	 * 
	 * @Return JSONArray
	 */
	@SuppressWarnings("unchecked")
	public JSONArray lectureCrawling(JSONArray jsonArray, int pageNo, String SCH_ORG_SECT) {
		JSONObject lectureInfo = new JSONObject();
		try {
			URL url = new URL("http://eclass.kpu.ac.kr/ilos/st/main/course_ing_list.acl?SCH_ORG_SECT="+SCH_ORG_SECT+"&start="+pageNo);
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), "utf-8"));
			
			String line = reader.readLine();
			line.trim();
			lectureInfo.clear();
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
					String lectureYear = line.split(">")[1].split("<")[0].substring(0,4);
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
	
	
	
	
	
	/*	강의 리스트 스크래퍼 -----------------------------------------------------------------------------------
	 * 
	 * 
	 * */
	public List<lectureVO> lectureCrawlingList(int pageNo, String SCH_ORG_SECT) {
		List<lectureVO> lectureInfo = new ArrayList<lectureVO>();
		lectureVO vo = new lectureVO();
//		 Variable 
//		String lectureIdx = "";
//		String code= "";
//		String lectureCode= "";
//		String lectureGubun= "";
//		String lectureName= "";
//		String lectureDate= "";
//		String lectureYear= "";
//		String lectureSemester= "";
//		String professorName= "";
		
		try {
			URL url = new URL("http://eclass.kpu.ac.kr/ilos/st/main/course_ing_list.acl?SCH_ORG_SECT="+SCH_ORG_SECT+"&start="+pageNo);
			
			System.out.println(url);
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), "utf-8"));
			
			String line = reader.readLine();
			line.trim();
			
			while((line = reader.readLine()) != null) {
				if(line.contains("<tr   id=")) {
					lectureInfo = new ArrayList<lectureVO>();
					
					String code = line.split(">")[0];
					code.trim();
					code = code.substring(20, 36);
					String lectureGubun = code.substring(0,1);
					String lectureCode = code.substring(6,14);
					System.out.println(code);
					System.out.println(lectureGubun);
					System.out.println(lectureCode);
					
				} else if(line.contains("<tr class=\"list\"")) {
					lectureInfo = new ArrayList<lectureVO>();
					
					String code = line.split(">")[0];
					code.trim();
					code = code.substring(32, 48);
					String lectureGubun = code.substring(0,1);
					String lectureCode = code.substring(6,14);
					System.out.println(code);
					System.out.println(lectureGubun);
					System.out.println(lectureCode);
				}

				if(line.contains("<td class=\"number")) {
					String lectureIdx = line.split(">")[1].split("<")[0];
					System.out.println(lectureIdx);
				}
				
				if(line.contains("<td class=\"name") && line.contains("<br>")) {
					String lectureDate = line.split(">")[1].split("<")[0];
					String lectureSemester = line.split(">")[2].split("<")[0];
					System.out.println(lectureDate);
					System.out.println(lectureSemester);
				}
				
				if(line.contains("<td class=\"left") && line.contains("<br>")) {
					String lectureName = line.split(">")[1].split("<")[0];
					String professorName = line.split(">")[2].split("<")[0];
					System.out.println(lectureName);
					System.out.println(professorName);
					
				} else if(line.contains("<td class=\"left")) {
					String lectureDate = line.split(">")[1].split("/td")[0];
					if(lectureDate.substring(0).equals("<")) {
						lectureDate="NULL";
					} else {
						lectureDate = line.split(">")[1].split("</td")[0];
						lectureDate.trim();
					}
					System.out.println(lectureDate);
				}
				if(line.contains("</tr")) {
	
					reader.close();
					return lectureInfo;
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
		return lectureInfo;
	}
	

}
