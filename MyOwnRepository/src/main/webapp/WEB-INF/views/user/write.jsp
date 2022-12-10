<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@100&display=swap" rel="stylesheet">
	<script src="https://kit.fontawesome.com/5309915bbd.js" crossorigin="anonymous" defer></script>
	<link rel="stylesheet" type="text/css" href="../../../resources/css/write.css">
	
	<%
		String userNickname = (String) session.getAttribute("userNickname");  
	%> 
	
	<style>
		p {
			text-align: center;
			font-size:25px;
			color:blue;
		}
	</style>
	
	<title>나만의 저장소 - MOR</title>
	
	<script>
 		src="https://code.jquery.com/jquery-3.4.1.js"
 		integrity="sha256-WpOohJOqMqqyKL9FccASB9O0KwACQJpFTUBLTYOVvVU="
 		crossorigin="anonymous"></script>
 		<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">

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
	<p>자유게시판 - 글쓰기</p><hr><br><br>
	<form action="/user/write" method="post">
		<table cellpadding="0" cellspacing="0" border="1">
			<tr>
 				<td><b>작성자</b></td>
 				<td style="text-align: left">&nbsp;&nbsp;${userNickname}</td>
			</tr>
			<tr>
				<td><b>제목</b></td>
				<td> <input type="text" name="title" size = "65"> </td>
 			</tr>
 
			<tr>
				<td><b>내용</b></td> 
				<td> <textarea name="content" rows="20" cols="60" ></textarea> </td>
			</tr>

			<tr>
				<td colspan="2"><input type="file" name="file"><input type="submit" value="글쓰기" class="small_write_btn"></td>
			</tr>
		</table>
	</form>
	
	<c:if test="${b_msg == false}">
		<script>
			alert('ERROR\n1~1000자 이내의 내용을 입력해주세요!!');
		</script>
	</c:if>
	
</body>
</html>