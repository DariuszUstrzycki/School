<%@ include file="../jspf/taglibs.jspf"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>School Manager Project</title>
<%@ include file="../jspf/cssBootstrapLinks.jspf"%>
</head>
<body>
	<%@ include file="../jspf/header.jspf"%>

	<div class="container">
		<div class="row">
			<div class="col-sm-12">
				<h3 class='text-danger'>Do you really want to delete
					<c:out value="${entityName}" default="item" />
					with id ${id}?
				</h3>
				<div class="form-group row">
				<div class="col-xs-1">
					<form action='${pageContext.request.contextPath}/${entityName}/delete/${id}' method="GET">
						<input type="submit" class="btn btn-info   btn-primary" value="SUBMIT">
					</form>
				</div>
				<div class="form-group row">
				<div class="col-xs-1">
					<form action='${pageContext.request.contextPath}/${entityName}/list/' method="GET">
						<input type="submit" class="btn btn-info  btn-primary"  value="CANCEL">
					</form>
				</div>       
				</div>  
			</div>
			</div>
		</div>
	</div>

	<%@ include file="../jspf/footer.jspf"%>

</body>
</html>
