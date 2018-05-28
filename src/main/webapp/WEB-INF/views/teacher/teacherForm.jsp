<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@	taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="../jspf/cssBootstrapLinks.jspf"%>
</head>
<body>

	<div id="header">
		<%@ include file="../jspf/header.jspf"%>
	</div>

		<c:if test="${teacherDto['new']}">
			<c:set var="actionType" scope="page" value="Add" />
		</c:if>
		<c:if test="${ not teacherDto['new']}">
			<c:set var="actionType" scope="page" value="Update" />
		</c:if>

		<h1>${actionType} a teacher</h1>
		
	<div>
		<form:form action="#" method="POST" modelAttribute="teacherDto">

			<form:hidden path="isDeleted" />
			<form:hidden path="password" />
			<form:hidden path="email" />
			<form:hidden path="subjects" />

			<label for="firstName">First name:</label>
			<form:input placeholder="" path="firstName" />
			<form:errors path="firstName" cssClass='error'></form:errors>
			<br>

			<label for="lastName">Last name:</label>
			<form:input placeholder="" path="lastName" />
			<form:errors path="lastName" cssClass='error'></form:errors>
			<br>

			<label for="address">Address:</label>
			<form:input placeholder="" path="address" />
			<form:errors path="address" cssClass='error'></form:errors>
			<br>

			<label for="telephone">Telephone:</label>
			<form:input placeholder="" path="telephone" />
			<form:errors path="telephone" cssClass='error'></form:errors>
			<br>

			<label for="birthDate">Date of birth:</label>
			<form:input placeholder="" path="birthDate" />
			<form:errors path="birthDate" cssClass='error'></form:errors>
			<br>
			<br>

			<input type="submit" value="${actionType}" />
			<input type="reset" value="Reset" />
		</form:form>

	</div>

	<div>
		<c:if test="${empty notFound}">
			<%@ include file="../teacher/jspf/subjectsTaught.jspf"%>
		</c:if>
	</div>

	<div id="footer">
		<%@ include file="../jspf/footer.jspf"%>
	</div>
</body>
</html>