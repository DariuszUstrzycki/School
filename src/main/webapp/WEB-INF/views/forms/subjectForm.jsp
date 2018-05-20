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

<c:if test="${subject['new']}">
	<c:set var = "actionType" scope = "page" value = "Add"/>
</c:if>
<c:if test="${ not subject['new']}">
	<c:set var = "actionType" scope = "page" value = "Update"/>
</c:if>	

<h1>${actionType} a subject</h1>

<form:form action="#" method="POST" modelAttribute="subjectDto">

<form:hidden path="isDeleted"/>
<label for="name">Name of the subject:</label>
<form:input placeholder="eg 1B, 2C, 4D, etc." path="name" />
<form:errors path="name" cssClass='error'></form:errors>
<br><br>
<input type="submit" value="${actionType}" />
<input type="reset" value="Reset" />
</form:form>

</div>

<div id="header">
    <%@ include file="../jspf/footer.jspf" %>
</div>
</body>
</html>