<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@	taglib	uri="http://www.springframework.org/tags/form"	prefix="form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Confirm delete</title>
</head>
<body>

<h3>Do you really want to delete ${entityName} with id ${id}?</h3>
<a href='${pageContext.request.contextPath}/${entityName}/delete/${id}' >Confirm</a>
<!--  
<c:if test="${entityName eq 'school form'}">
<a href='${pageContext.request.contextPath}/schoolform/list' >Cancel</a>
</c:if>
-->
<c:if test="${entityName ne 'Category'}">
<a href='${pageContext.request.contextPath}/${entityName}/list' >Cancel</a>
</c:if>
</body>
</html>