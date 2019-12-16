<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="http://code.jquery.com/jquery-3.2.1.min.js"></script>
</head>
<body oncontextmenu="return false">
<h1>Test Page</h1>
<div><a href="/kpu-schedule/home">HOME GO</a></div>
<div><a href="/kpu-schedule/lectureLoading/G">LectureLoading JSON Test</a></div>
<div id="list" ></div>

<script type="text/javascript">
$(document).ready(function(){
	var url = "/kpu-schedule/lectureLoading/G";
	$.ajax({
		type: 'GET',
		url: url,
		dataType: 'json',
		success: function(data,result) {
			var htmls = "";
			var lecture = data;
			var dataSize = lecture.lectureList.length;
			$("#list").html();
			htmls += '<div style="background-color: #00bcd4;"> ';
			htmls += 'DataSize-->'+dataSize;
			htmls += '</div> ';
			if(result.length < 1){
				htmls += '등록된 댓글이 없습니다.';
			} else {
				$(data).each(function(){
					for(var i = 0; i < dataSize; i++) {
						htmls += '<div>';
						htmls += '<strong>' + i + '</strong>' + '. '+ lecture.lectureList[i].lectureIdx;
						htmls += ' '+ lecture.lectureList[i].lectureYear;
						htmls += ' '+ lecture.lectureList[i].lectureName;
						htmls += ' '+ lecture.lectureList[i].professorName;
						htmls += ' '+ lecture.lectureList[i].lectureDate;
						htmls += ' '+ lecture.lectureList[i].lectureCode;
						htmls += '</div>';
					}
				});	//each end
			}
			$("#list").html(htmls);
			}// Ajax success end
			, error:function(request,status,error){
	        	alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
	     	}
	});// Ajax end
	
});
</script>
</body>

</html>