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

<h1>Teacher/Subject/Schoolform</h1>

<div id="content">
	<%@ include file="../jspf/tssAddButton.jspf" %>
	<%@ include file="../jspf/tssListFrag.jspf" %>
</div>

<div id="header">
    <%@ include file="../jspf/footer.jspf" %>
</div>
</body>
</html>