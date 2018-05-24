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

<c:if test="${studentDto['new']}">
	<c:set var = "actionType" scope = "page" value = "Add"/>
</c:if>
<c:if test="${ not studentDto['new']}">
	<c:set var = "actionType" scope = "page" value = "Update"/>
</c:if>	

<h1>${actionType} a student</h1>

<form:form action="#" method="POST" modelAttribute="studentDto">

<form:hidden path="isDeleted"/>
<form:hidden path="password" />
<form:hidden path="email" />

<label for="firstName">First name:</label>
<form:input placeholder="" path="firstName" />
<form:errors path="firstName" cssClass='error'></form:errors><br>

<label for="lastName">Last name:</label>
<form:input placeholder="" path="lastName" />
<form:errors path="lastName" cssClass='error'></form:errors><br>

<label for="address">Address:</label>
<form:input placeholder="" path="address" />
<form:errors path="address" cssClass='error'></form:errors><br>

<label for="telephone">Telephone:</label>
<form:input placeholder="" path="telephone" />
<form:errors path="telephone" cssClass='error'></form:errors><br>

<label for="birthDate">Date of birth:</label>
<form:input placeholder="" path="birthDate" />
<form:errors path="birthDate" cssClass='error'></form:errors><br><br>

<form:select	path="schoolform.id">
				<!-- <form:option	label="undefined" value="100"/> -->
				<form:options	items="${schoolformItems}" itemLabel="name" itemValue="id"/>
</form:select>
<form:errors path='schoolform.id' cssClass='error'></form:errors><br><br>

<input type="submit" value="${actionType}" />
<input type="reset" value="Reset" />
</form:form>

</div>

<div id="header">
    <%@ include file="../jspf/footer.jspf" %>
</div>
</body>
</html>