<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta charset="EUC-KR">
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
	
	<title>������ ����� - MOR</title>
	
</head>
 
<body>
	<a href="/user/userMain">
		<img class="main-logo" src="../../../resources/img/MOR_symbol_logo.svg" />
	</a>
	<div>
		<input type="text" placeholder="�˻��� �Է�">
		<button>�˻�</button>
	</div>
	
	<ul class="menu">
		<li>
			<a href="#">�Խ���</a>
			<ul class="submenu">
				<li><a href="/user/userMain">�����Խ���</a></li>
				<li><a href="/user/secret_board">��аԽ���</a></li>
			</ul>
		</li>
		<li>
			<a href="#">�����</a>
			<ul class="submenu">
				<li><a href="/my_repo">������ �����</a></li>
				<li><a href="/our_repo">���� �����</a></li>
			</ul>
		</li>
		<li>
			<a href="/user/mypage">${userNickname}</a>
		</li>
	</ul>
		<hr>
	<p>�����Խ��� - �۾���</p><hr><br><br>
	<table cellpadding="0" cellspacing="0" border="1">
			<tr>
 				<td><b>�ۼ���</b></td>
 				<td style="text-align: left">&nbsp;&nbsp;${userNickname}</td>
			</tr>
			<tr>
				<td><b>����</b></td>
				<td> <input type="text" name="title" size = "65"> </td>
 			</tr>
 
			<tr>
				<td><b>����</b></td> 
				<td> <textarea name="content" rows="20" cols="60" ></textarea> </td>
			</tr>

	<tr>
		<td colspan="2"><input type="file" name="file"><input type="submit" value="�۾���" class="small_write_btn"></td>
	</tr>
 
 
</table>
</body>
</html>