<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Hashtag Input</title>
</head>
<body bgcolor="#7fffd4">
<h2>Checking Hashtag Form </h2>
<div align="left">
    <form action="${pageContext.request.contextPath}/ht/">
        Input yo hashtag here:<input type="text" name="hashtag"/><br>
        Input # of tweets here:<input type="text" name="numberOfTweetsPerPage"/><br>
        <input type="submit">
    </form>
</div>
</body>
</html>
