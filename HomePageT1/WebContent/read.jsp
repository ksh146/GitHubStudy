<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    <%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>글 자세히 보기 페이지</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<style>
body {
    background: #64BEFF;
    font-size: 1em;
    color: rgba(51,51,51,1);
}

</style>
</head>
<body>
<h1>글 자세히 보기</h1>
글번호: ${dto.num}<br>
작성자: ${dto.author}<br>
제  목: ${dto.title}<br>
작성일: ${dto.writeday}<br>
조회수: ${dto.readcnt}<br>
내  용: <br>
<p>${dto.content}</p>

<a href="replyui.do?num=${dto.num}">댓글</a>
<a href="updateui.do?num=${dto.num}">수정</a>
<a href="delete.do?num=${dto.num}">삭제</a>

</body>
</html>