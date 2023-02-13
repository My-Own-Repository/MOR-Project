<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="EUC-KR">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
 	<link rel="icon" type="image/jpg" href="../../resources/img/MORicon.jpg">
 	
	<script
 		src="https://code.jquery.com/jquery-3.4.1.js"
 		integrity="sha256-WpOohJOqMqqyKL9FccASB9O0KwACQJpFTUBLTYOVvVU="
 		crossorigin="anonymous"></script>
 	<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">	
 	
 	<style>
 		body{
 			background-color: darkgray;
 			margin: auto;
 		}
 		.total_div{
 			background-color: white;
 			width: 380px;
 			height: 280px;
 			padding: 10px;
 			margin: auto;
 			text-align: center;
 		}
 		.content_div{
 			display: flex;
 			width: 90%;
 			height: 60%;
 			margin: auto;
 			text-align: center;
 			border: 1px solid black;
 			align-items: center;
            justify-content: center;
            padding: 4%;
 		}
 		p{
 			float: left;
 		}
 		.content_table{
 			width: 100%;
 			height: auto;
 			margin: auto;
 			text-align: center;
 		}
 		.content_table th{
 			background-color: lightgray;
 		}
 		
 		@media ( max-width: 720px ) {
	 		body{
	 			background-color: darkgray;
	 			margin: auto;
	 		}
	 		.total_div{
	 			position: absolute;
	 			background-color: white;
	 			width: 96%;
	 			height: 66%;
	 			padding: 10px;
	 			margin: auto;
	 			text-align: center;
	 			top: 18%;
	 			bottom: 18%;
	 		}
	 		.content_div{
	 			display: flex;
	 			width: 90%;
	 			height: 60%;
	 			margin: auto;
	 			text-align: center;
	 			border: 1px solid black;
	 			align-items: center;
	            justify-content: center;
	            padding: 4%;
	 		}
	 		p{
	 			float: left;
	 		}
	 		.content_table{
	 			width: 100%;
	 			height: auto;
	 			margin: auto;
	 			text-align: center;
	 		}
	 		.content_table th{
	 			background-color: lightgray;
	 		}
 		}
 	</style>
 	
<c:choose>
	<c:when test="${which == 'id'}">
		<title>내 아이디 찾기</title>
	</c:when>
	<c:when test="${which == 'pw'}">
		<title>비밀번호 찾기</title>
	</c:when>
</c:choose>
</head>
<body>
	<div class="total_div">
		<c:choose>
			<c:when test="${which == 'id'}">
				<p>* 고객님의 정보와 일치하는 아이디 목록입니다</p>
				<div class="content_div">
					<c:forEach items="${userInfo}" var="user">
						<table class="content_table">
							<tr>
								<th>${user.id}</th>
								<td>${user.sign_day}</td>
							</tr>
						</table>
					</c:forEach>
				</div>			
			</c:when>
			<c:when test="${which == 'pw'}">
				<p>* 비밀번호 확인 후 변경하시기 바랍니다.</p>
				<div class="content_div">
					<c:forEach items="${userInfo}" var="user">
						<table class="content_table">
							<tr>
								<th>${user.id}</th>
								<td>${user.pw}</td>
							</tr>
						</table>
					</c:forEach>
				</div>	
			</c:when>
		</c:choose>

	</div>
</body>
</html>