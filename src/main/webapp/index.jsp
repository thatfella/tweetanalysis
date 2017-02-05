<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<html>
<head>
    <title>Hashtag Input</title>
</head>
<body bgcolor="#7fffd4">
<h2>Checking Hashtag Form </h2>
<br>
You may analyze a group of tweets<br>
Or just watch history<br>

<div align="left" title="ANALYZER">
    <form action="${pageContext.request.contextPath}/ht/">
        Input yo hashtag here:<input type="text" name="hashtag"/><br>
        Input # of tweets here:<input type="text" name="numberOfTweetsPerPage"/><br>

        <input type="submit">

        <br>


    </form>
</div>
<br>

<a href="toArchive.jsp">TOARCHIVE</a>
</body>
</html>
