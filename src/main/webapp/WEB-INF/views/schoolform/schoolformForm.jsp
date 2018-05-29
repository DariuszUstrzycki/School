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
<c:if test="${schoolformDto['new']}">
	<c:set var = "actionType" scope = "page" value = "Add"/>
</c:if>
<c:if test="${ not schoolformDto['new']}">
	<c:set var = "actionType" scope = "page" value = "Update"/>
</c:if>	

<h1>${actionType} a school form</h1>

<form:form action="#" method="POST" modelAttribute="schoolformDto">

<form:hidden path="isDeleted"/>
<form:hidden path="students" />
<form:hidden path="teacherSubjects" />

<label for="name">Name:</label>
<form:input placeholder="eg 1B, 2C, 4D, etc." path="name" />
<form:errors path="name" cssClass='error'></form:errors>
<br><br>
<input type="submit" value="${actionType}" />
<input type="reset" value="Reset" />
</form:form>
</div>


<div>
	<c:if test="${empty notFound}">
		<%@ include file="../schoolform/jspf/withTeacherSubjects.jspf" %>
	</c:if>
</div>

<div>
	<c:if test="${empty notFound}">
		<%@ include file="../schoolform/jspf/withStudents.jspf" %>
	</c:if>
</div>

<div id="footer">
    <%@ include file="../jspf/footer.jspf" %>
</div>
</body>
</html>