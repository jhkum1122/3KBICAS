<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Books Manage Page</title>
<style>
table,th,td {
	border: 1px solid black;
}
</style>
</head>
<body>

<%-- book Add/Edit logic --%>
	<c:if test="${requestScope.error ne null}">
		<strong style="color: red;">
		<c:out value="${requestScope.error}"></c:out>
		</strong>
	</c:if>
	<c:if test="${requestScope.success ne null}">
		<strong style="color: green;">
		<c:out value="${requestScope.success}"></c:out>
		</strong>
	</c:if>
	<c:url value="/bookAdd" var="addURL"></c:url>
	<c:url value="/bookEdit" var="editURL"></c:url>


<%-- Add Request --%>
	<c:if test="${requestScope.person eq null}">
		<form action='<c:out value="${addURL}"></c:out>' method="post">
			이름 : <input type="text" name="bookName"><br> 
			작가 : <input	type="text" name="country"><br> 
			<input type="submit" value="책 생성">
		</form>
	</c:if>
</body>
</html>