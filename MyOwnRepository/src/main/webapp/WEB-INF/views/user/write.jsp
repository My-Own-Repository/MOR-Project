<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!-- 
cellpadding="0" cellspacing="0"을 쓰기위한 설정
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 5.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"> 
-->

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
	<link rel="stylesheet" type="text/css" href="../../../resources/css/write.css">
	
	<%
		//String userNickname = (String) session.getAttribute("userNickname");  
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
	<a href="/user/userMain/1">
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
			<a href="/user/mypage">${member.nickname}</a>
		</li>
	</ul>
		<hr>
		<!-- <table cellpadding="0" cellspacing="0" border="1"> -->
		
	<p>자유게시판 - 글쓰기</p><hr><br><br>

	<form id="write_form" action="/user/write" method="post" enctype="multipart/form-data">
		<table border="1">
			<tr>
 				<td><b>작성자</b></td>
 				<td style="text-align: left">&nbsp;&nbsp;${member.nickname}</td>
			</tr>
			<tr>
				<td><b>제목</b></td>
				<td> <input type="text" name="title" size = "65"> </td>
 			</tr>
 
			<tr>
				<td><b>내용</b></td> 
				<td>
					<div contenteditable="true" id="write_content_div" class="write_content_div"></div>
					<textarea id="write_content_textarea" name="content" style=display:none></textarea>
				</td>
				
			</tr>

			<tr>
				<td colspan="2"><input type="file" name="files" multiple="multiple" onchange="is_img_video(this)"><input type="button" value="글쓰기" class="small_write_btn" onclick="submit_btn();"></td>
			</tr>
		</table>
	
	</form>
	
	
	<script>
	
	function is_img_video(f) {
		var file = f.files;
		
		for(var i=0; i<file.length; i++) {
			var fileList = f.files;		// 파일 받아와서 스크립트 내부 변수에 저장.
			var fileName = fileList[i].name;		// 파일 이름 추출
			
			var fileLength = fileName.length;		// 파일명 길이 추출		
			var fileDot = fileName.lastIndexOf(".");	// 파일의 확장자 추출		
			var fileType = fileName.substr(fileDot+1, fileLength).toLowerCase();	// 추출한 확장자를 소문자로 변경한다.
				
            var reader = new FileReader();
			
            reader.onload = function (e) {
            	
            	// 파일이 이미지일때 수행
            	if("jpg" == fileType || "jpeg" == fileType || "gif" == fileType || "png" == fileType || "bmp" == fileType){
            		var previewImg = document.createElement("img");
                	previewImg.setAttribute("src", e.target.result);
                	document.querySelector("div#write_content_div").appendChild(previewImg);
            	}
            	// 파일이 동영상/오디오 일때 수행
				else if("mpg" == fileType || "mpeg" == fileType || "mp4" == fileType || "ogg" == fileType || "webm" == fileType || "avi" == fileType || "wmv" == fileType || "mov" == fileType || "rm" == fileType || "ram" == fileType || 
						"swf" == fileType || "flv" == fileType || "wav" == fileType || "mp3" == fileType){
					var previewVideo = document.createElement("video");
					previewVideo.setAttribute("src", e.target.result);
                	document.querySelector("div#write_content_div").appendChild(previewVideo);
				}

            }
            reader.readAsDataURL(f.files[i]);
            
        }
		
    }
	
	function submit_btn(){
		var copy = document.getElementById('write_content_div').innerText;
		//var paste = document.getElementById('write_content_textarea');
		
		document.getElementById('write_content_textarea').value = copy;
				
		document.getElementById('write_form').submit();
	}

	</script>
	
	<c:if test="${b_msg == false}">
		<script>
			alert('ERROR\n1~1000자 이내의 내용을 입력해주세요!!');
		</script>
	</c:if>
	
	<c:if test="${session_msg == false}">
		<script>
			alert('ERROR\n세션이 만료되었습니다.\n다시 로그인 해주세요!!');
		</script>
	</c:if>
	
</body>
</html>