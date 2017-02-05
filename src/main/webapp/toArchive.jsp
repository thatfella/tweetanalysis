<%@ page import="java.sql.ResultSet" %>
<%@ page import="java.sql.Statement" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.DriverManager" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Insert title here</title>
</head>
<body bgcolor="#7fffd4">


<form method="post">

    <table border="2">
        <tr>
            <td>ID</td>
            <td>Hashtag</td>
            <td>Result</td>


        </tr>
        <%
            try {
                Class.forName("org.sqlite.JDBC");
                String url = "jdbc:sqlite:E:/TwitAnalyze/mytweetanalysis.sqlite";
                String username = "";
                String password = "";
                String query = "SELECT * FROM mytwanres";
                Connection conn = DriverManager.getConnection(url, username, password);
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                while (rs.next()) {

        %>
        <tr>
            <td><%=rs.getString("idmytweetres") %>
            </td>
            <td><%=rs.getString("hashtagmytweetres") %>
            </td>
            <td><%=rs.getString("resultmytweetres") %>
            </td>
        </tr>
        <%

            }
        %>
    </table>
    <%
            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


    %>

</form>
</body>
</html>