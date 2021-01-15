<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<style>
body {
    background: #3d516b;
}
form {
    width: 670px;
    height : 600px;
    padding: 60px 25px 80px;
    margin: 150px auto;
    background: #f8d348;
    display: flex;
    flex-direction: column;
    flex-wrap: wrap;
}
input {
	width: 250px;
    font-size: 16px;
    border: 0;
    border-radius: 5px;
    outline: 0;
    padding: 10px 15px;
    margin-top: 15px;
}
.v2{
	width: 250px;
	display: flex;
    flex-direction: row;
    flex-wrap: nowrap;
    justify-content: center;
	margin: 10px 10px;
	padding: 0px;
}
.v1{
	height: 600px;
	border-left: 1px solid #3d516b;
	margin-top: 80px;
}
</style>
<title>insert</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="http://lab.alexcican.com/set_cookies/cookie.js"
	type="text/javascript"></script>
</head>
<body>
<form action="insert.do" method="post">
<h1>회원가입</h1>
ID: <input name="id" style="display: inline;"><button style=" border:5px; border-radius : 5px; display:inline; width: 280px; padding :10px 15px; margin-top: 15px; color: white; background-color: #3A1D1D">아이디 중복 체크</button><span></span><br>
Password: <input type="password" name="pw1"><br>
Password(확인): <input type="password" name="pw2"><br>
이름: <input name="name"><br>
성별:<div class="v2">
<input name="gender" type="radio" checked value="m" >남<input name="gender" type="radio" value="f">여
</div>
<div class="v1"></div><br><br><br><br>
email: <input name="email" type="email"><br>
핸드폰: <input name="phoneNumber" type="tel"><br>
주소: <input name="address" type="text"><br>
<br><br><br><br><br><br><br>
<input type="submit" value="가입" style="width: 280px; color: white; background-color: #3A1D1D">
<input type="hidden" name="checkedid" value="0">
</form>
<script type="text/javascript">
	$(document).ready(function(){
		$("button").click(function(event){
			event.preventDefault();
			
			var id = $("input[name='id']").val();
			$.ajax({
				type: 'get',
	            url: 'idcheck',
	            data: {
	               id : id
	            },
	            dataType: 'text',
	            success: function(idCheckResult) {
	            	console.log(idCheckResult);
	            	if(idCheckResult=="사용 가능합니다."){
					$("input[name='checkedid']").val(id);            		
	            	}
	            	alert(idCheckResult);
	            	var nid = "<c:out value='${nid}'/>";
	            },
	            error: function(request, status, error) {
	               console.log(error);
	               alert(idCheckResult);
	            },
			});
		});
		$("input[type='submit']").click(function(event){
			var id = $("input[name='id']").val();
			var serchid = null;
			event.preventDefault();
			
			var id2 = $("input[name=id]").val();
			var pw1 = $("input[name=pw1]").val();
			var pw2 = $("input[name=pw2]").val();
			var name = $("input[name=name]").val();
			var email = $("input[name=email]").val();
			var phoneNumber = $("input[name=phoneNumber]").val();
			var address = $("input[name=address]").val();
			
			if(id2 == ""){
				alert("ID를 입력해 주세요.");
				console.log(id2);
				return;
			}
			if(pw1 == "" || pw2 == ""){
				alert("Password를 입력해 주세요.");
				return;
			}
			if(pw1 != pw2){
				alert("비밀번호가 일치하지 않습니다.");
				$("input[name=pw2]").focus();
				$("input[name=pw2]").select();
				return;
			}
			if(name == ""){
				alert("이름을 입력해 주세요.");
				return;
			}
			if(email == ""){
				console.log(gender);
				alert("email을 입력해 주세요.");
				return;
			}
			if(phoneNumber == ""){
				alert("핸드폰 번호를 입력해 주세요.");
				return;
			}
			if(address == ""){
				alert("주소를 입력해 주세요.");
				return;
			}
			
			if(id==$("input[name='checkedid']").val()){
				$("form").submit();
			}else{
				console.log(id2);
				alert("아이디 중복 체크를 해주세요.");
			}
			
		});
	});
</script>
</body>
</html>