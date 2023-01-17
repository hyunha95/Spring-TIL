<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!doctype html>
<html>
<head>
  <title><spring:message code="login.title"/> </title>
</head>
<body>
	<p>
		<spring:message code="login.done"/>
	</p>
	<p>
		<a href="<c:url value='/main/'/>">
			[<spring:message code="go.main"/>]
		</a>
	</p>
</body>
</html>