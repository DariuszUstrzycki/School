<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@	taglib	uri="http://www.springframework.org/tags/form"	prefix="form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Add a school form</title>
</head>
<body>
<h1>Add a school form</h1><br><br>

<form:form action="${pageContext.request.contextPath}/schoolform/save" method="POST" modelAttribute="schoolForm">
<form:hidden path="id"/>
<label for="name">Name of the form:</label>
<form:input placeholder="eg 1B, 2C, 4D, etc." path="name" />
<form:errors path="name"></form:errors>
<br><br><input type="submit"/>
</form:form>

</body>
</html>