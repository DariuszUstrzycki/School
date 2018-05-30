<%@ include file="../jspf/taglibs.jspf"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="../jspf/cssBootstrapLinks.jspf"%>
</head>
<body>
	<%@ include file="../jspf/header.jspf"%>

	<div class="container">
		<h2>${actionType}&nbspa&nbspsubject</h2>
		<form:form class="form-horizontal" action="#" method="POST" modelAttribute="subjectDto">
					<form:hidden path="isDeleted" />
					<form:hidden path="tsses" />
					
			<div class="form-group row">
				<div class="col-xs-4">
					<label for="name">Name of the subject:</label>
					<form:input class="form-control"  placeholder="eg 1B, 2C, 4D, etc." path="name" />
					<form:errors path="name" cssClass='error'></form:errors>
				</div>
			</div>

			<%@ include file="../jspf/formAddResetButtons.jspf"%>
			
		</form:form>
	</div>

	<%@ include file="../jspf/footer.jspf"%>
</body>
</html>