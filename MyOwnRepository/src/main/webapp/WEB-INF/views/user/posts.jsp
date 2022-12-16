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
	String userNickname = (String) session.getAttribute("userNickname");  	// 로그인한 유저의 nickname
	String page_name = (String) request.getAttribute("page_name");		// 글 작성자의 nickname
	Integer page_num = (Integer) request.getAttribute("page_num");		// 정수형 변수는 int가 안되고 오로지 Integer로 선언해야함
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
	<div class="search_div">
		<input type="text" placeholder="검색어 입력">
		<input type="button" value="검색" class="search">
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
	<c:forEach items="${SelectPost}" var="letter">

	<table border="1">	
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
					<td>&nbsp;댓글 <font color="red">${letter.comment}</font></td>
					<td>&nbsp;${letter.date}</td>
					<td>&nbsp;조회수 ${letter.view}</td>
				</tr>
			</table>
			<table border="1" class="content_table">
				<tr>
					<td colspan="4">&nbsp;${letter.content}</td>
				</tr>
			</table>
	
		
	</table>
	
	<br>

	<table>	
		<c:forEach items="${pre_post}" var="pre">	
			<c:forEach items="${next_post}" var="next">		
				<tr>
					<td>이전글&nbsp;&nbsp;<a href="/user/posts/${pre.num}">${pre.title}</a></td>			
					<td>다음글&nbsp;&nbsp;<a href="/user/posts/${next.num}">${next.title}</a></td>
				</tr>		
			</c:forEach>	
		</c:forEach>
	</table>
	
	<br><br>
	
	<input type="button" onclick="location.href='/user/update_board'" value="수정" id="edit_btn" class="edit_btn">
	<input type="button" onclick="delete_board(${page_num})" id="delete_btn" class="delete_btn" value="삭제">

	<script>	
		// 게시글 작성자와 현재 게시글을 열람하는 사용자가 동일 인물일 경우
		// 게시글 수정 및 삭제 버튼이 표시되고, 동일 인물이 아닐경우 버튼을 숨긴다.
		function is_mine(){
			const edit_btn = document.getElementById('edit_btn');
			const delete_btn = document.getElementById('delete_btn');
			
			var page_id = '${page_name}';
			var session_id = '${userNickname}';
			
			
			if(page_id != session_id){
				edit_btn.style.display = 'none';		// 버튼 숨김
				delete_btn.style.display = 'none';
			}
			else if(page_id == session_id){
				edit_btn.style.display = 'block';		// 버튼 활성화
				delete_btn.style.display = 'block';
			}
			else {
				alert("ERROR");
			}
		}
		is_mine();
		
		function delete_board(num) {
			if(confirm("게시글을 삭제하시겠습니까?")){
				alert("SUCCESS\n정상적으로 삭제되었습니다.");
				location.href='/user/delete_board?num='+num;		// 컨트롤러에 삭제할 게시글 번호를 전송
			}
		}		
	</script>
		<br><br><br><hr><br><br>
	
			
		<table class="title_table">	
			<tr>
				<td colspan="4">&nbsp;<b>댓글&nbsp;</b><font size="3px", color="red">${letter.comment}</font></td>
			</tr>
		</table>
	</c:forEach>
	
	
	<br>
	<c:forEach items="${printComment}" var="cmt">
		<table class="comment_table">
			<tr>
				<th>&nbsp;&nbsp;${cmt.nickname}
				
				<a href="#"><img src="../../../resources/img/Red_X_img.png" class="imgs"></a>
				<a href="#"><img src="../../../resources/img/Pencil_img.png" class="imgs"></a>
				&nbsp;&nbsp;
				
				</th>
				<td>&nbsp;&nbsp;${cmt.date}</td>					
			</tr>
		</table>
		
		<table class="comment_main">
			<tr>
				<td colspan="4"><br>&nbsp;${cmt.content}<br></td>
			</tr>
		</table>
		
		<script>
			function is_mine_img(){		// 댓글을 작성자와 로그인을 하여 댓글을 열람중인 사용자가 동일인물 이면
										// 댓글에 대한 삭제 및 수정을 할 수 있는 이미지가 보이고, 동일인물이 아니면 숨긴다.
										
				const control_imgs = document.getElementsByClassName('imgs');
		
				var comment_id = '${cmt.nickname}';
				var session_id = '${userNickname}';				
				
				/*
					forEach문안에 있기 때문에 댓글이 작성 될때마다 is_mine_img() 함수가 호출된다.
					이때 img의 class 인덱스 값은 0부터 1씩 점점 증가하게 되고, 하나의 댓글에는 img가 두개가 들어간다.
					따라서 댓글 한개가 생성이 되면 class의 인덱스 값은 (0,1) 두개가 되고
					댓글 두개가 생성이 되면 class의 인덱스 값은 (0,1,2,3) 네개가 된다.
					
				 	이를 감안하여 댓글이 생성될때마다 불필요한 반복을 피하기 위해
				 	새로 생긴 댓글에 한해서만 사용자 식별용 댓글 수정/삭제 이미지 검사 반복문이 실행되도록 구현했다.
				*/
				for(var i=control_imgs.length-2; i<control_imgs.length; i++){
					if(comment_id != session_id){
						control_imgs[i].style.display = "none";		// 이미지 숨기기
					}
					
					else if(comment_id == session_id){
						control_imgs[i].style.display = "block";	// 이미지 보이기
					}
					
					else {
						alert("ERROR\n댓글 오류 발생!!!");
					}
					
				}
				
			}
			is_mine_img();
			
			function delete_comment(num) {
				if(confirm("댓글을 삭제하시겠습니까?")){
					alert("SUCCESS\n정상적으로 삭제되었습니다.");
					location.href='/user/delete_board?num='+num;		// 컨트롤러에 삭제할 게시글 번호를 전송
				}
			}
			
		</script>
		
		<br><br><br>
	</c:forEach>
	
	<hr><br>
	
	
	<form action="/user/posts/comment" method="post">
		<input type="hidden" name="b_num" value="<%=page_num %>">
		<input type="hidden" name="nickname" value="<%=userNickname %>">
		<table border="1" class="comment_table">			
			<tr>
				<td colspan="4"><textarea name="content"></textarea></td>
			</tr>
			<tr>
				<td colspan="4"><input type="file" name="file"></td>
			</tr>		
		</table>
		<br>
		<input type="button" onClick="window.location.reload()" value="새로고침" class="reload_btn">
		<input type="submit" value="작성" class="comment_btn">
	</form>
	
	
	
	<c:if test="${c_msg == false}">
		<script>
			alert('ERROR\n1~300자 이내의 내용을 입력해주세요!!');
		</script>
	</c:if>

	<c:if test="${msg == false}">
		<script>
			alert('ERROR\n이미 삭제된 게시글입니다!!');
		</script>
	</c:if>
	
</body>
</html>