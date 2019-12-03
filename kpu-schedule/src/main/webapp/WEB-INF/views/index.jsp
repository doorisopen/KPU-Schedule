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
<header>
한국산업기술대학교 강의 시간표 만들기
</header>
<div class="content-wrapper">
	<div class="timetable">
		시간표
	</div>
	<div class="lectureList">
		<table id="lectureList" border="1"></table>
	</div>
</div>
<footer>
github. https://github.com/doorisopen
</footer>
<script type="text/javascript">
$(document).ready(function(){
showList();
});
function showList() {
	var url = "/schedule/lectureLoading";
	$.ajax({
        type: 'GET',
        url: url,
        dataType: 'json',
        success: function(data, result) {
           	var htmls = "";
           	var lecture = data;
          		htmls += '<colgroup>';
			 	htmls += '<col width="160"><col width="160"><col width="160">';
			 	htmls += '<col width="160"><col width="160"><col width="160">';
			 	htmls += '<col width="160"><col width="160"><col width="160">';
			 	htmls += '</colgroup>';
			 	htmls += '<thead>';
			 	htmls += '<th>1</th><th>2</th><th>3</th>';
			 	htmls += '<th>4</th><th>5</th><th>6</th>';
			 	htmls += '<th>7</th><th>8</th><th>9</th>';
			 	htmls += '</thead>';
		if(result.length < 1){
			htmls += 'Data Not Found..';
		} else {
			var undefine = 1; 
			$(data).each(function(){
			for(var i = 1; i <= lecture.lectureList.length - 1; i++) {
				if(i !== 11*undefine) {
					htmls += '<tbody><tr>';
					htmls += '<td>'+lecture.lectureList[i].lectureIdx+'</td>';
					htmls += '<td>'+lecture.lectureList[i].code+'</td>';
					htmls += '<td>'+lecture.lectureList[i].lectureCode+'</td>';
					htmls += '<td>'+lecture.lectureList[i].lectureGubun+'</td>';
					htmls += '<td>'+lecture.lectureList[i].lectureName+'</td>';
					htmls += '<td>'+lecture.lectureList[i].lectureDate+'</td>';
					htmls += '<td>'+lecture.lectureList[i].lectureYear+'</td>';
					htmls += '<td>'+lecture.lectureList[i].lectureSemester+'</td>';
					htmls += '<td>'+lecture.lectureList[i].professorName+'</td>';
				 	htmls += '</tr></tbody>';
				} else {
					undefine++;
					htmls += '</br>';
				}
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