<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="EUC-KR">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@100&display=swap" rel="stylesheet">
	<script src="https://kit.fontawesome.com/5309915bbd.js" crossorigin="anonymous" defer></script>
    <link rel="stylesheet" type="text/css" href="././resources/css/main.css">

	
	<script>
 		src="https://code.jquery.com/jquery-3.4.1.js"
 		integrity="sha256-WpOohJOqMqqyKL9FccASB9O0KwACQJpFTUBLTYOVvVU="
 		crossorigin="anonymous"></script>
 	<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">	

	<%
		int select_page = (int) request.getAttribute("select_page");		// ���� ������ ������
		int first_page = (int) request.getAttribute("first_page");			// ���� ������ ������ ���ؿ��� ù ��° ������
		int last_page = (int) request.getAttribute("last_page");			// ���� ������ ������ ���ؿ��� ������ ������
		int page_count = (int) request.getAttribute("page_count");			// �� ������ ����
	%>

<title>������ ����� - MOR !</title>
</head>
<body>
	<div class="empty_main_div">
	<div class="total_main_div">
	<a href="/1">
		<img class="main-logo" src="././resources/img/MOR_symbol_logo.svg" />
	</a>
	<div class="search_div">
		<input type="text" class="search_input" placeholder="�˻��� �Է�">
		<input type="submit" value="�˻�" class="search">
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
	<br><br>
	<p>�����Խ���</p><br>
	<div class="main_div">
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
			<c:forEach items="${BoardList}" var="letter">	
				<tr>
					<td>${letter.num}</td>
                    <td><a href="/unlogin_posts?urlnum=${letter.num}" class="board_title_a">${letter.title}</a><font size="2px" color="red" class="board_comment_font">&nbsp;&nbsp;[${letter.comment}]</font></td>
                    <td>${letter.nickname}</td>
                    <td>${letter.date}</td>
                    <td>${letter.view}</td>
				</tr>
            </c:forEach>
		</tbody>
	</table>
	
	<br><br><input type="button" onclick="location.href='LoginPage'" class="write_button" value="�۾���">
	<br><br><br><br><br><br>
	<div class="bottom_div">
		<c:if test="${first_page > 10}">
			<a href="/${first_page-10}"><b>&lt;&nbsp;����</b></a>&emsp;
		</c:if>
		<div class="paging_div">
			<div class="paging_div2">
			<c:forEach begin="${first_page}" end="${last_page}" var="p">
				<c:choose>
					<c:when test="${p == select_page}">
						<b class="b_sty">${p}&emsp;</b>
					</c:when>
					<c:when test="${p != select_page}">
						<a href="/${p}">${p}</a>&emsp;
					</c:when>
				</c:choose>
			</c:forEach>
			</div>
		</div>
		<c:if test="${last_page != page_count}">
			&emsp;
			<a href="/${last_page+1}"><b>����&nbsp;&lt;</b></a>
		</c:if>
		<hr class="hr_sty">
		<div class="board_search_div">		
				<select name='search_filter' class="in_board_search_menu">
					<option value="search_title">����</option>
					<option value="search_cotent">����</option>
					<option value="search_tit_cot">���� + ����</option>
					<option value="search_writer">�ۼ���</option>
				</select>								
			<button class="in_board_search_btn">�˻�</button>
			<input type="text" class="in_board_search_text" placeholder="�˻�� �Է����ּ���.">	
		</div>
	</div>
	<br><br><br><br>
	</div>
	<hr class="bottom_hr">
	</div>
	</div>
</body>
</html>