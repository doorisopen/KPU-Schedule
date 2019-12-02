<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="http://code.jquery.com/jquery-3.2.1.min.js"></script>
</head>
<body>
<div id="list"></div>
<script type="text/javascript">

var list = document.getElementById('list');

$(document).ready(function(){
	showList();
});

function showList(){
	
	var url = "/schedule/lectureList";
	$.ajax({
		type: 'POST',
		url: url,
		data: paramData,
		dataType: 'json',
		success: function(data) {
			list.innerHTML = data;
		}// Ajax success end
		,  error:function(request,status,error){
        	alert("code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
     	}

	});// Ajax end
}


</script>
</body>
</html>