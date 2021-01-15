<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    <%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>댓글을 작성해주세요</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
</head>
<body>
<h1>${param.num}의 댓글</h1>

	<form action="reply.do" method="post">
		<input type="hidden" name="num" value="${param.num}">
		글쓴이: <input name="author"><br>
		제  목: <input name="title"><br>
		내  용: <br>
		<textarea rows="20" name="content"></textarea>
		<br>
		<input type="submit" value="댓글 작성">
	
	</form>

</body>
</html>