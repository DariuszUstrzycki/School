<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@	taglib	uri="http://www.springframework.org/tags/form"	prefix="form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<%@ include file="../jspf/cssBootstrapLinks.jspf" %>
</head>
<body>

<div id="header">
    <%@ include file="../jspf/header.jspf" %>
</div>
<div>

<h3>Do you really want to delete <c:out value="${entityName}" default="item"/> with id ${id}?</h3>

<form action='${pageContext.request.contextPath}/${entityName}/delete/${id}' method="GET">
<input type="submit" value="Confirm">
</form><br>

<form action='${pageContext.request.contextPath}/${entityName}/list/' method="GET">
<input type="submit" value="Cancel">
</form>


</div>
<div id="header">
    <%@ include file="../jspf/footer.jspf" %>
</div>
</body>
</html>