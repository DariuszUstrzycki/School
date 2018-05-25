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



<c:if test="${TSSDto['new']}">
	<c:set var = "actionType" scope = "page" value = "Add"/>
</c:if>
<c:if test="${ not TSSDto['new']}">
	<c:set var = "actionType" scope = "page" value = "Update"/>
</c:if>	

<h1>${actionType} a TSS</h1>

<div>
<form:form action="#" method="POST" modelAttribute="TSSDto">
<form:hidden path="isDeleted"/>

<table border='1px'>
<thead>
<tr><th>School forms</th><th>Subjects</th><th>Teachers</th><th>Action</th></tr>
</thead>
<tbody>
<tr><td><form:select	path="schoolform.id">
				<!-- <form:option	label="undefined" value="100"/> -->
				<form:options	items="${schoolformItems}" itemLabel="name" itemValue="id"/>
		</form:select></td>
	<td><form:select	path="schoolform.id">
				<!-- <form:option	label="undefined" value="100"/> -->
				<form:options	items="${subjectItems}" itemLabel="name" itemValue="id"/>
	</form:select></td>
<td><form:select	path="schoolform.id">
				<!-- <form:option	label="undefined" value="100"/> -->
				<form:options	items="${teacherItems}" itemLabel="lastName" itemValue="id"/>
	</form:select></td>
<td>
	<input type="submit" value="${actionType}" />
</td>
</tr>
</tbody>
</table>

</form:form>
</div>

<div id="header">
    <%@ include file="../jspf/footer.jspf" %>
</div>
</body>
</html>