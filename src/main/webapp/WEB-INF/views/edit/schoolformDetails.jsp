<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@	taglib	uri="http://www.springframework.org/tags/form"	prefix="form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>School form information</title>
</head>
<body>

<h1>School form information</h1>

<form method="GET" action='${pageContext.request.contextPath}/schoolform/save'>
<input type="submit" value="Add new school form">
</form>

<table border='1px'>

<thead>
<tr>
    <th>Id</th><th>Form</th><th>Action</th>
</tr>
</thead>
<tbody>

<c:forEach items='${schoolformItems}' var='form'>
<tr>
	<td>${form.id}</td>
	<td>${form.name}</td>
	<td><a href='${pageContext.request.contextPath}/schoolform/delete/${form.id}/confirm'>Delete</a>
	<a href='${pageContext.request.contextPath}/schoolform/update/${form.id}'>Update</a>
</tr>
</c:forEach>

</tbody>
</table><br><br>


</body>
</html>