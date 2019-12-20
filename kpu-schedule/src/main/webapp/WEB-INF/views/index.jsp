<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="./resources/main.css">
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="http://code.jquery.com/jquery-3.2.1.min.js"></script>
</head>
<body>
<div class="lecture-page">
	<header class="lecture-page-header">
		한국산업기술대학교 강의 시간표 만들기
	</header>
	
		<div class="contents-container">
			<div class="contents-wrapper">
				<div class="timetable">
					시간표
				</div>
				<div class="lectureList-Controller">lectureList-Controller</div>
				<div class="lectureList">
					<table id="lectureList" border="1">
					</table>
				</div>
			</div>
		</div>
		
	<footer class="lecture-page-footer">
		github. https://github.com/doorisopen
	</footer>
</div>

<script type="text/javascript">

var htmls = "";

window.onload = function(){
	var url = "/kpu-schedule/lectureLoading/A";
	$.ajax({
        type: 'GET',
        url: url,
        dataType: 'json',
        success: function(data, result) {
        	Loading = true;
        	lectureList.style.display = "block";
           	htmls = "";
           	console.log(result);
           	var lecture = data;
          		htmls += '<colgroup>';
			 	htmls += '<col width="160"><col width="160"><col width="160">';
			 	htmls += '<col width="160"><col width="160"><col width="160">';
			 	htmls += '<col width="160"><col width="160"><col width="160">';
			 	htmls += '</colgroup>';
			 	htmls += '<thead>';
			 	htmls += '<th>NO</th><th>Code</th><th>강의코드</th>';
			 	htmls += '<th>강의 구분(대학/원)</th><th>강의 명</th><th>강의 시간</th>';
			 	htmls += '<th>강의 연도</th><th>학기</th><th>교수</th>';
			 	htmls += '</thead>';
		if(result.length < 1){
			htmls += 'Data Not Found..';
		} else {
			$(data).each(function(){
			for(var i = 0; i < lecture.lectureList.length; i++) {
				htmls += '<tbody><tr>';
				htmls += '<td>'+lecture.lectures[i].lectureIdx+'</td>';
				htmls += '<td>'+lecture.lectures[i].code+'</td>';
				htmls += '<td>'+lecture.lectures[i].lectureCode+'</td>';
				htmls += '<td>'+lecture.lectures[i].lectureGubun+'</td>';
				htmls += '<td>'+lecture.lectures[i].lectureName+'</td>';
				htmls += '<td>'+lecture.lectures[i].lectureDate+'</td>';
				htmls += '<td>'+lecture.lectures[i].lectureYear+'</td>';
				htmls += '<td>'+lecture.lectures[i].lectureSemester+'</td>';
				htmls += '<td>'+lecture.lectures[i].professorName+'</td>';
			 	htmls += '</tr></tbody>';
			}
			});	//each end
		}
		$("#lectureList").html(htmls);
		}// Ajax success end
		,  error:function(request,status,error){
     	alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
     	console.log(error);
  	}

	});// Ajax end
}
</script>

</body>
</html>