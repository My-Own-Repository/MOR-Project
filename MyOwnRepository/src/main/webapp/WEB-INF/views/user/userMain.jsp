<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="EUC-KR">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@100&display=swap" rel="stylesheet">
	<script src="https://kit.fontawesome.com/5309915bbd.js" crossorigin="anonymous" defer></script>
    <link rel="stylesheet" type="text/css" href="../../../resources/css/main.css">

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
	
	<table border="1" class="board_table">
		<thead class="board_head">
			<tr>
				<td>No</td>
				<td>제목</td>
				<td>닉네임</td>
				<td>등록일</td>
				<td>조회</td>
			</tr>
		</thead>
		
		<tbody>
			<c:forEach items="${BoardList}" var="letter">					
                <tr>
                	<td>${letter.num}</td>
                    <td><a href="/user/posts/${letter.num}">${letter.title}</a></td>
                    <td>${letter.nickname}</td>
                    <td>${letter.date}</td>
                    <td>${letter.view}</td>
                </tr>       
            </c:forEach>
		</tbody>
	
	</table>
	
	<br><input type="button" onclick="location.href='/user/write'" class="write_button" value="글쓰기">
	


	
</body>
</html>