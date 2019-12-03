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

<a href="/schedule/lectureList">JSON GO</a>
<button id="btn">Try it</button>

<div id="list" >


</div>
<script type="text/javascript">

function noRefresh()
{
    /* CTRL + N키 막음. */
    if ((event.keyCode == 78) && (event.ctrlKey == true))
    {
        event.keyCode = 0;
        return false;
    }
    /* F5 번키 막음. */
    if(event.keyCode == 116)
    {
        event.keyCode = 0;
        return false;
    }
}

document.onkeydown = noRefresh ;

$(document).ready(function(){

	var url = "/schedule/lectureList";
	
	$.ajax({
		type: 'GET',
		url: url,
		dataType: 'json',
		success: function(data,result) {
			var htmls = "";
			var lecture = data;
			var length = lecture.lectureList[1].lectureIdx;
			var dataSize = lecture.lectureList.length;
			htmls += 'length: '+length;
			htmls += '<div style="background-color: #00bcd4;"> ';
			htmls += dataSize;
			htmls += '</div> ';
			
			if(result.length < 1){
				htmls += '등록된 댓글이 없습니다.';
			} else {
				var undefine = 1; 
				$(data).each(function(){
					for(var i = 1; i <= dataSize - 1; i++) {
						if(i !== 11*undefine) {
							htmls += '<div>';
							htmls += '<strong>' + i + '</strong>' + '. '+ lecture.lectureList[i].lectureIdx;
							htmls += ' '+ lecture.lectureList[i].lectureYear;
							htmls += ' '+ lecture.lectureList[i].lectureName;
							htmls += ' '+ lecture.lectureList[i].professorName;
							htmls += ' '+ lecture.lectureList[i].lectureDate;
							htmls += ' '+ lecture.lectureList[i].lectureCode;
							htmls += '</div>';
						} else {
							undefine++;
							htmls += '</br>';
						}
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