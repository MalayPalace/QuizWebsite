<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<h1>The Name 
	<%
	String username = request.getParameter("username");
	out.println(username); %> is Already In Use</h1>
	<p>Please enter another name and password.</p>
	<form action="NewAccountServlet" method="post">
		User Name: <input type="text" name="username"><br />
		Password: <input type="text" name="password">
		<input type="submit" value="Login"><br />
	</form>
</body>
</html>