<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="tf" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html>
<html>
<head>
	<title>회원정보</title>
</head>
<body>
	<p>아이디: ${member.id}</p>
	<p>이메일: ${member.email}</p>
	<p>이름: ${member.name}</p>
<%--	<p>가입일: ${tf:formateDate}</p>--%>
</body>
</html>
