<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Insert title here</title>
<style>
table, th, td {
	border: 1px solid black;
}
</style>

<script>
function content_view(idnum){
         var f=document.contents;   //폼 name
         myWin=window.open('','POP','location=no,status=no,toolbar=no,scrollbars=no,width=650,height=650');
         f.id.value = idnum;    //POST방식으로 넘기고 싶은 값(hidden 변수에 값을 넣음)
         f.action="books.jsp"; //이동할 페이지
         f.target="POP";    //폼의 타겟 지정(위의 새창을 지정함)
         f.method="post"; //POST방식
         f.submit();
}
</script>


</head>
<body>

	<c:if test="${not empty requestScope.persons}">
		<table>
			<tbody>
				<tr>
					<th>ID</th>
					<th>Name</th>
					<th>Author</th>
					<th>Edit</th>
					<th>Delete</th>
				</tr>
				<c:forEach items="${requestScope.persons}" var="person">
					<c:url value="/bookEdit" var="editURL">
						<c:param name="id" value="${person.id}"></c:param>
					</c:url>
					<c:url value="/bookDelete" var="deleteURL">
						<c:param name="id" value="${person.id}"></c:param>
					</c:url>
					<tr>
						<td><c:out value="${person.id}"></c:out></td>
						<td><c:out value="${person.name}"></c:out></td>
						<td><c:out value="${person.country}"></c:out></td>
						<td><a
							href='<c:out value="${editURL}" escapeXml="true"></c:out>'>Edit</a></td>
						<td><a
							href='<c:out value="${deleteURL}" escapeXml="true"></c:out>'>Delete</a></td>
					</tr>
				</c:forEach>

			</tbody>
		</table>
	</c:if>

	<form name="contents">
		<input type="hidden" name="id" /> <input type='button' value='책 추가하기'
			onClick='javascript:content_view();' />
	</form>

</body>
</html>