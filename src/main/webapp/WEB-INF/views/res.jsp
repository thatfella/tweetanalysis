<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body bgcolor="#7fffd4">
<h1>OneString Results on your Query</h1>
<br>${hst}<br>
Your hashtag was <c:out value="${hashtag}"></c:out> <br>
<br>
<img src="/piechart" alt="PieChart" height="480" width="700"/>
<br>
</body>
</html>
