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

<h1>Subject details</h1>

<div id="content">
	
	
	<c:if test="${empty notFound}">
	<%@ include file="../subject/jspf/details.jspf" %>
	</c:if>
	
	<c:if test="${not empty notFound}">
		<p class='error'>${notFound}</p>
	</c:if>

</div>

<div id="footer">
    <%@ include file="../jspf/footer.jspf" %>
</div>
</body>
</html>