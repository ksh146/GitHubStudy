<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>임시 메인</title>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
</head>
<body>
      	<c:if test="${login == null }">
      	<script type="text/javascript">
      	location.href = "login.jsp";
      	</script>
      	</c:if>
      	<button onclick="logout_confirm()">로그아웃</button>

      <script type="text/javascript">
      	function logout_confirm(){
      		var answer=confirm("정말 로그아웃 하시겠습니까?");
      		
      		if(answer==true){
      			location.href = "logout.do";
      		}
      	}
      	
      </script>

<!-- 임시 관리자 페이지 이동 시작-->
<c:if test="${login.rights =='admin'}">
<button onclick="go_adminPage()">관리자 페이지</button>
</c:if>
<script type="text/javascript">
function go_adminPage(){
location.href = "adminPage.do";
}
</script>
<!-- 임시 관리자 페이지 이동 끝 -->
      
</body>
</html>