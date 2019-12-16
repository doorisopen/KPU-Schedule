<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<title>Home</title>
</head>
<body>
<h1>
	KPU Lecture REST api Test Page
</h1>
<P>  The time on the server is ${serverTime}. </P>

<h2>INDEX Page</h2>
<div><a href="/kpu-schedule/">INDEX Page GO</a></div>

<h2>lectureListPage Page</h2>
<div><a href="/kpu-schedule/lectureListPage">lectureListPage GO</a></div>

<h2>JSON TEST</h2>
<div><a href="/kpu-schedule/lectureLoading">LectureLoading JSON Test</a></div>





</body>
</html>
