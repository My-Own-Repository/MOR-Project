<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <link rel="stylesheet" type="text/css" href="././resources/css/main.css">

	<style>
		p {
			text-align: center;
			font-size:25px;
			color:blue;
		}
	</style>

<title>나만의 저장소 - MOR !</title>
</head>
<body>
	<a href="/">
		<img class="main-logo" src="././resources/img/MOR_symbol_logo.svg" />
	</a>
	<div>
		<input type="text" placeholder="검색어 입력">
		<button>검색</button>
	</div>
	
	<ul class="menu">
		<li>
			<a href="#">게시판</a>
			<ul class="submenu">
				<li><a href="/">자유게시판</a></li>
				<li><a href="/LoginPage">비밀게시판</a></li>
			</ul>
		</li>
		<li>
			<a href="#">저장소</a>
			<ul class="submenu">
				<li><a href="/LoginPage">나만의 저장소</a></li>
				<li><a href="/LoginPage">공유 저장소</a></li>
			</ul>
		</li>
		<li>
			<a href="/LoginPage">내 정보</a>
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
			<tr>
				<td>1</td>
				<td><a href="#">TEST TITLE</a> </td>
				<td>TEST NICKNAME</td>
				<td>TEST DAY</td>
				<td>TEST COUNT</td>
			</tr>
	</tbody>
</table>
	<br><input type="button" onclick="location.href='LoginPage'" class="write_button" value="글쓰기">
</body>
</html>