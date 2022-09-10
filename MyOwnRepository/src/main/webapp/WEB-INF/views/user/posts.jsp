<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@100&display=swap" rel="stylesheet">
	<script src="https://kit.fontawesome.com/5309915bbd.js" crossorigin="anonymous" defer></script>
    <link rel="stylesheet" type="text/css" href="../../../resources/css/posts.css">

	<style>
		p {
			text-align: center;
			font-size:25px;
			color:blue;
		}
	</style>

<%
	String userNickname = (String) session.getAttribute("userNickname");  
%> 

	<script>
 		src="https://code.jquery.com/jquery-3.4.1.js"
 		integrity="sha256-WpOohJOqMqqyKL9FccASB9O0KwACQJpFTUBLTYOVvVU="
 		crossorigin="anonymous"></script>
 		<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">

<title>나만의 저장소 - MOR !</title>
</head>
<body>
	<a href="/user/userMain">
		<img class="main-logo" src="../../../resources/img/MOR_symbol_logo.svg" />
	</a>
	<div>
		<input type="text" placeholder="검색어 입력">
		<button>검색</button>
	</div>
	
	<ul class="menu">
		<li>
			<a href="#">게시판</a>
			<ul class="submenu">
				<li><a href="/user/userMain">자유게시판</a></li>
				<li><a href="/user/secret_board">비밀게시판</a></li>
			</ul>
		</li>
		<li>
			<a href="#">저장소</a>
			<ul class="submenu">
				<li><a href="/my_repo">나만의 저장소</a></li>
				<li><a href="/our_repo">공유 저장소</a></li>
			</ul>
		</li>
		<li>
			<a href="/user/mypage">${userNickname}</a>
		</li>
	</ul>
		<hr>
	<p>자유게시판</p><hr><br><br>
	<table border="1">
		<c:forEach items="${SelectPost}" var="letter">	
			<tr>
				<td><b>&nbsp;No.${letter.num}</b></td>
			</tr>
			<table border="1" class="title_table">			
				<tr>
					<td align="center"><b>제목</b></td>
					<td colspan="3">&nbsp;${letter.title}</td>
				</tr>
				<tr>
					<td align="center" style="color:blue">${letter.nickname}</td>
					<td>&nbsp;댓글 0</td>
					<td>&nbsp;${letter.date}</td>
					<td>&nbsp;조회수 ${letter.view}</td>
				</tr>
			</table>
			<table border="1" class="content_table">
				<tr>
					<td colspan="4">&nbsp;${letter.content}</td>
				</tr>
			</table>

		
		</c:forEach>
	</table>
	<c:if test="${msg == false}">
	<script>
		alert('ERROR\n이미 삭제된 게시글 입니다!!');
	</script>
</c:if>
</body>
</html>