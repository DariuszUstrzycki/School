<%@ include file="../jspf/taglibs.jspf"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="../jspf/cssBootstrapLinks.jspf"%>
</head>
<body>
	<%@ include file="../jspf/header.jspf"%>
	
	<div class="container">
		<h2>CREATE/UPDATE&nbspa&nbspteacher</h2>
			<h3>Personal data</h3>
		<form:form class="form-horizontal" action="#" method="POST" modelAttribute="teacherDto">
			<form:hidden path="isDeleted" />
			<form:hidden path="tsses" />
			<form:hidden path="password" />
			<form:hidden path="email" />

			<div class="form-group row">
				<div class="col-xs-2">
					<label for="firstName">First name</label>
					<form:input type="text" class="form-control" placeholder=""
						path="firstName" />
					<form:errors path="firstName" cssClass='error'></form:errors>
				</div>

				<div class="col-xs-3">
					<label for="ex2">Last name</label>
					<form:input type="text" class="form-control" placeholder=""
						path="lastName" />
					<form:errors path="lastName" cssClass='error'></form:errors>
				</div>
			</div>

			<div class="form-group row">
				<div class="col-xs-5">
					<label for="ex3">Address</label>
					<form:input type="text" class="form-control" placeholder=""
						path="address" />
					<form:errors path="address" cssClass='error'></form:errors>
				</div>
			</div>

			<div class="form-group row">
				<div class="col-xs-2">
					<label for="ex1">Telephone</label>
					<form:input type='tel' class="form-control" placeholder=""
						path="telephone" />
					<form:errors path="telephone" cssClass='error'></form:errors>
				</div>

				<div class="col-xs-3">
					<label for="ex2">Date of birth</label>
					<form:input type="date" class="form-control" placeholder=""
						path="birthDate" />
					<form:errors path="birthDate" cssClass='error'></form:errors>
				</div>
			</div>

			<%@ include file="../jspf/formAddResetButtons.jspf"%>

		</form:form>
	</div>

	<div class="container">
		<c:if test="${empty notFound}">
			<c:if test="${ not teacherDto['new']}">
				<%@ include file="../teacher/jspf/withSubjects.jspf"%>
			</c:if>
		</c:if>
	</div>




	<%@ include file="../jspf/footer.jspf"%>

</body>
</html>