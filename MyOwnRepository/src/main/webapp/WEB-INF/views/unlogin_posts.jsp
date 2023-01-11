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
	<a href="/1">
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
				<li><a href="/1">자유게시판</a></li>
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
			<a href="/LoginPage">내정보</a>
		</li>
	</ul>
		<hr>
	<p>자유게시판</p><hr><br><br>
	
	<table border="1">
		
			<tr>
				<td><b>&nbsp;No.${SelectPost.num}</b></td>
			</tr>
			<table border="1" class="title_table">			
				<tr>
					<td align="center"><b>제목</b></td>
					<td colspan="3">&nbsp;${SelectPost.title}</td>
				</tr>
				<tr>
					<td align="center" style="color:blue">${SelectPost.nickname}</td>
					<td>&nbsp;댓글 <font color="red">${SelectPost.comment}</font></td>
					<td>&nbsp;${SelectPost.date}</td>
					<td>&nbsp;조회수 ${SelectPost.view}</td>
				</tr>
			</table>
			<table border="1" class="content_table">
				<tr>
					<td colspan="4" class="content_td">
							<c:forEach items="${fileViewer}" var="files">
									<img src="/loadfiles.do/${files.file_num}" class="upload_imgs" id="${files.file_num}" alt="">
									<br>
									<video src="/loadfiles.do/${files.file_num}" class="upload_videos" id="${files.stored_file_name}" controls></video>
									
									<br>
									
									<script>								
										function is_exist_file(){	
											// img src와 video src의 각각 고유의 id를 가지기 위해 
											// 이미지는 파일 번호, 동영상은 파일 이름을 아이디 값으로 주었다.
											
											// 각각 고유 id를 가지게 한 이유는 각 파일마다 숨길지 혹은 보이게 할지 판별할때 보다 용이하기 때문이다.
											
											const img_id = '${files.file_num}';
											const video_id = '${files.stored_file_name}';
																	
											const upload_img = document.getElementById(img_id);
											const upload_video = document.getElementById(video_id);
											
											var fileType = '${files.type}';
											
											var img_exist = false;
											var video_exist = false;
											
											// 각 해당 파일이 존재하면 exist 변수가 true가 됨
											
											if(fileType.includes("image") == true){
												img_exist = true;
											}				
											else if(fileType.includes("video") == true){
												video_exist = true;
											}
											else {
												alert("ERROR");
											}
											
											// 각 exist 변수가 true일 경우 보이고 false일 경우 숨김 처리됨
											
											
											if(img_exist == false){
												upload_img.style.display = 'none';
											}
											else if(img_exist == true){
												upload_img.style.display = 'block';
											}
														
											if(video_exist == false){
												upload_video.style.display = 'none';
											}
											else if(video_exist == true){
												upload_video.style.display = 'block';
											}	
											
											
										}
										is_exist_file();
							
									</script>
								</c:forEach>
								<br>
														
							<br>
							<br>						
										
						${SelectPost.content}					
					</td>
				</tr>
			</table>
	
		
	</table>
	
	<br>

	<div>
		<table border="1" class="downFile_table">
			<tr>
				<th class="downFile_th">&nbsp;첨부파일</th>
			</tr>
			<tr>
				<td class="downFile_td">
					<a href="/LoginPage"><input type="text" class="file_down_login" value="로그인이 필요한 서비스입니다."></a>
				</td>
			</tr>		
		</table>
		<br><br>
	</div>

	<table>	
	
				<tr>
					<td>이전글&nbsp;&nbsp;<a href="/unlogin_posts?urlnum=${pre_posts.num}">${pre_posts.title}</a></td>			
					<td>다음글&nbsp;&nbsp;<a href="/unlogin_posts?urlnum=${next_posts.num}">${next_posts.title}</a></td>
				</tr>		

	</table>
		<br><br><br><hr><br><br>
	
	
		<table class="title_table">	
			<tr>
				<td colspan="4">&nbsp;<b>댓글&nbsp;</b><font size="3px", color="red">${SelectPost.comment}</font></td>
			</tr>
		</table>
	
	
	
	<br>
	<c:forEach items="${printComment}" var="cmt">
		<table class="comment_table">
			<tr>
				<th>&nbsp;&nbsp;${cmt.nickname}</th>
				<td>&nbsp;&nbsp;${cmt.date}</td>					
			</tr>
		</table>
		<table class="comment_main">
			<tr>
				<td colspan="4">&nbsp;${cmt.content}<br></td>
			</tr>
		</table>
		<br><br><br>
	</c:forEach>
	
	<hr><br>
	
	<div class="write_comment_div">
	<table border="1" class="comment_table">			
		<tr>
			<td colspan="4"><a href="/LoginPage"><textarea name="content">로그인이 필요한 서비스입니다.</textarea></a></td>
		</tr>
		<tr>
			<td colspan="4"><a href="/LoginPage"><input type="file" name="file"></a></td>
		</tr>		
	</table>
	<br>
	<input type="button" onClick="window.location.reload()" value="새로고침" class="reload_btn">
	<input type="button" onClick="location.href='/LoginPage'" value="작성" class="comment_btn">
	</div>
	

	<c:if test="${msg == false}">
		<script>
			alert('ERROR\n이미 삭제된 게시글입니다!!');
		</script>
	</c:if>
</body>
</html>