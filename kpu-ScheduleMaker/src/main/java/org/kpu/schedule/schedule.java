package org.kpu.schedule;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class schedule {
	
	private static int totalList;
	
	//���� �ϼ��� JSONObject ����(��ü)
	static JSONObject jsonObject = new JSONObject();

    //person�� JSON������ ���� Array ����
    static JSONArray jsonArray = new JSONArray();

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		int listNo = 1;
		
		totalList = pageInit();
		
		System.out.println("TOTAL LIST -> "+totalList);	
		
//		while(listNo < totalList) {
//			schoolCrawling(listNo);
//			listNo += 10;
//		}
		
		schoolCrawling(listNo);
		//JSONObject�� String ��ü�� �Ҵ�
        String jsonInfo = jsonObject.toJSONString();
 
        System.out.println(jsonInfo);
		
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
	public static void schoolCrawling(int listNo) {
		JSONObject scheduleInfo = new JSONObject();
		
		try {
			URL url = new URL("http://eclass.kpu.ac.kr/ilos/st/main/course_ing_list.acl?start="+listNo);
			
			System.out.println(url);
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), "utf-8"));
			
			String line = reader.readLine();
			line.trim();
			while((line = reader.readLine()) != null) {
				if(line.contains("<tr   id=")) {
					// ���� ������ �� JSONObject ����
					scheduleInfo = new JSONObject();
					String code = line.split(">")[0];
					code.trim();
					
					scheduleInfo.put("code", code.substring(20, 36));
					
				} else if(line.contains("<tr class=\"list\"")) {
					// ���� ������ �� JSONObject ����
					scheduleInfo = new JSONObject();
					String code = line.split(">")[0];
					code.trim();

					scheduleInfo.put("code", code.substring(32, 48));
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
