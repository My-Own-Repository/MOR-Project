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

<title>������ ����� - MOR !</title>
</head>
<body>
	<a href="/">
		<img class="main-logo" src="././resources/img/MOR_symbol_logo.svg" />
	</a>
	<div>
		<input type="text" placeholder="�˻��� �Է�">
		<button>�˻�</button>
	</div>
	
	<ul class="menu">
		<li>
			<a href="#">�Խ���</a>
			<ul class="submenu">
				<li><a href="/">�����Խ���</a></li>
				<li><a href="/LoginPage">��аԽ���</a></li>
			</ul>
		</li>
		<li>
			<a href="#">�����</a>
			<ul class="submenu">
				<li><a href="/LoginPage">������ �����</a></li>
				<li><a href="/LoginPage">���� �����</a></li>
			</ul>
		</li>
		<li>
			<a href="/LoginPage">�� ����</a>
		</li>
	</ul>
	<hr>
	<p>�����Խ���</p><hr><br><br>
	<table border="1" class="board_table">
	<thead class="board_head">
		<tr>
			<td>No</td>
			<td>����</td>
			<td>�г���</td>
			<td>�����</td>
			<td>��ȸ</td>
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
	<br><input type="button" onclick="location.href='LoginPage'" class="write_button" value="�۾���">
</body>
</html>