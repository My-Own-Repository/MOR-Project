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
	
    <link rel="stylesheet" type="text/css" href="../../../resources/css/posts.css">
    <link rel="icon" type="image/jpg" href="../../../resources/img/MORicon.jpg">

	<style>

		.update_cmt {
			display: none;
		}
	</style>

<%
	String userNickname = (String) request.getAttribute("userNickname");  	// 로그인한 유저의 nickname
	String page_name = (String) request.getAttribute("page_name");		// 글 작성자의 nickname
	Integer page_num = (Integer) request.getAttribute("page_num");		// 정수형 변수는 int가 안되고 오로지 Integer로 선언해야함
%> 

	<script
 		src="https://code.jquery.com/jquery-3.4.1.js"
 		integrity="sha256-WpOohJOqMqqyKL9FccASB9O0KwACQJpFTUBLTYOVvVU="
 		crossorigin="anonymous"></script>
 		<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">



<title>나만의 저장소 - MOR !</title>
</head>
<body>
	<div class="empty_post_div">
	<div class="total_post_div">
	<a href="/user/userMain/1">
		<img class="main-logo" src="../../../resources/img/MOR_symbol_logo.svg" />
	</a>
	<div class="search_div">
		<input type="text" class="search_input" placeholder="검색어 입력">
		<input type="button" value="검색" class="search">
	</div>
	
	<ul class="menu">
		<li>
			<a href="#">게시판</a>
			<ul class="submenu">
				<li><a href="/user/userMain/1">자유게시판</a></li>
				<li><a href="/user/secretBoard/1">비밀게시판</a></li>
			</ul>
		</li>
		<li>
			<a href="#">저장소</a>
			<ul class="submenu">
				<li><a href="#">공유</a></li>
				<li><a href="#">나만의 저장소</a></li>
			</ul>
		</li>
		<li>
			<a href="/user/mypage">${member.nickname}</a>
		</li>
	</ul>
	<br><br>
	<p id="user_board_p" class="user_board_p">자유게시판</p>
	<p id="secret_board_p" class="secret_board_p">비밀게시판</p>
	<p id="admin_board_p" class="admin_board_p">공지사항</p>	
	<br>
	

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
							<div id="changed_content_div">
						
							</div>				
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
					<c:forEach items="${fileDown}" var="list">
						<a href="/downfiles.do/${list.file_num}"><font size="2px" color="blue">&nbsp;${list.original_file_name}</font></a>
						<span class="downFile_span">&nbsp;&nbsp;${list.file_size}&nbsp;kb</span>
						<br>
					</c:forEach>
				</td>
			</tr>		
		</table>
		<br><br>
	</div>
	
	<table>	
	
				<tr>
					<td class="prePost_td"><div id="preANDnext_div1">이전글&nbsp;&nbsp;<a href="/user/posts?urlnum=${pre_post.num}">${pre_post.title}</a></div></td>			
					<td class="nextPost_td"><div id="preANDnext_div2">다음글&nbsp;&nbsp;<a href="/user/posts?urlnum=${next_post.num}">${next_post.title}</a></div></td>
				</tr>		

	</table>
	
	<br><br>
	
	<div class="btn_div">
		<input type="button" onclick="delete_board(${page_num})" id="delete_btn" class="delete_btn" value="삭제">
		<input type="button" onclick="location.href='/user/update_board?urlnum=${page_num}'" value="수정" id="edit_btn" class="edit_btn">
	</div>
	
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
				<td colspan="4">&nbsp;<b>댓글&nbsp;</b><font size="3px" color="red">${SelectPost.comment}</font></td>
			</tr>
		</table>
	
	
	
	<br>
	<c:forEach items="${printComment}" var="cmt">
		<table class="comment_table">
			<tr>
				<th>&nbsp;&nbsp;${cmt.nickname}
				
				<a href="javascript:delete_cmt(${cmt.b_num}, ${cmt.c_num})"><img src="../../../resources/img/Red_X_img.png" class="imgs"></a>
				<a href="javascript:update_cmt(${cmt.c_num}, ${cmt.b_num + cmt.c_num})"><img src="../../../resources/img/Pencil_img.png" class="imgs"></a>
				&nbsp;&nbsp;
				
				</th>
				<td>&nbsp;&nbsp;${cmt.date}</td>					
			</tr>
		</table>
		
		<table class="comment_main">
			<tr>
				<td colspan="4" id="${cmt.c_num}"><br>
					<div id="changed_comment_div_${cmt.c_num}">
					
					</div>
				<br><br></td>
				<td colspan="4" id="${cmt.b_num + cmt.c_num}" class="update_cmt"> 
					<form id="${(cmt.b_num + cmt.c_num) * -1}" action="/user/posts/comment_update" method="post">
						<textarea name="content" class="update_comment_textarea">${cmt.content}</textarea>
						<input type="hidden" name="b_num" value="${cmt.b_num}">
						<input type="hidden" name="c_num" value="${cmt.c_num}">
						<input type="hidden" name="is_exist" value="${cmt.is_exist}">
						<input type="button" value="수정 완료" class="update_comment_btn" onClick="submit_update_cmt(${cmt.c_num},${cmt.b_num + cmt.c_num});">
						<input type="button" value="취소" class="update_cancle_btn" onClick="window.location.reload()">
					</form>
				</td>
			</tr>		
		</table>
		
			<script>			
				function changeComment_hyperlink_url() {
					var print_comment_divID = 'changed_comment_div_'+${cmt.c_num};
					var comment_div = document.getElementById(print_comment_divID);	// 수정된 댓글 내용을 넣어줄 장소 가져오기
							
					var originalComment = '${cmt.content}';
							
					if(originalComment != null){
								originalComment = originalComment.replace(/<br>/ig, '\n');		// html 태그인 <br>을 프로그래밍 언어의 줄바꿈 문자(\n)로 바꾸어 줌.
								
					var regURL = new RegExp("(http|https|ftp|telnet|news|irc)://([-/.a-zA-Z0-9_~#%$?&=:200-377()]+)","gi");
					var changedComment = originalComment.replace(regURL, "<a href='$1://$2' target='_blank'>$1://$2</a>");
					changedComment = changedComment.replace(/\n/ig, '<br>');		// \n을 <br>태그로 바꾸어서 html상으로 줄바꿈이 정상적으로 나오게 바꿔줌.
								
					comment_div.innerHTML = changedComment;
					}
				}
				changeComment_hyperlink_url();
			</script>
		
		<script type="text/javascript">	// HTML5에서는 디폴트 값인 text/javascript를 따로 선언할 필요는 없지만
										// 이번 경우에는 혹시 모를 호환성을 위해 적었다.
			function update_cmt(view_num, form_num) {
				var showed_updateComment = document.getElementById(view_num);		// 수정 할 댓글 가져오기
				var updating_comment = document.getElementById(form_num);	// 댓글 수정 폼 가져오기
								
				showed_updateComment.style.display = "none";	// 수정 할 댓글 뷰 숨기기
				updating_comment.style.display = "block";		// 댓글 수정 폼 보이기
			}
		
			function submit_update_cmt(view_num, form_num){		
				var form_submit_num = form_num * -1;
				document.getElementById(form_submit_num).submit();	// 댓글 수정 폼 컨트롤러로 submit 하기
			}
			
			function viewmode_update_cmt(view_num, form_num){
				var showed_updateComment = document.getElementById(view_num);		// 수정 할 댓글 가져오기
				var updating_comment = document.getElementById(form_num);	// 댓글 수정 폼 가져오기
				
				updating_comment.style.display = "none";		// 댓글 수정 폼 숨기기
				showed_updateComment.style.display = "block";	// 수정한 댓글 보이기								
			}
			
			function delete_cmt(b_num, c_num){		// 댓글삭제에 대한 확인 의사를 재차 묻기위해 실행되는 함수 
				var path = 'posts/comment_delete/';
				if(confirm("댓글을 삭제 하시겠습니까?")){
					path += b_num;
					path += '/';
					path += c_num;
					
					location.href = path;
				}
			}			
		</script>
		
		<c:if test="${cu_msg == true}">		
			<script>	
				// 댓글의 수정 폼을 숨기고 view 모드로 전환하기 위한 컨트롤러와의 통신수단. 
				// 댓글의 정보를 함수 인자로 사용하기 위해 forEach문 안에 배치했다.
				viewmode_update_cmt(${cmt.c_num}, ${cmt.b_num + cmt.c_num});
				
			</script>
		</c:if>
		
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
	
	<div class="write_comment_div">
	<form action="/user/posts/comment" method="post" enctype="multipart/form-data">
		<input type="hidden" name="b_num" value="<%=page_num %>">
		<input type="hidden" name="nickname" value="<%=userNickname %>">
		<table border="1" class="comment_table">		
			<tr>
				<td colspan="4"><textarea id="comment_id" name="content"></textarea></td>
			</tr>
			<tr>
				<td colspan="4"><input type="file" name="uploadfile"></td>
			</tr>		
		</table>
		<br>
		
			<input type="button" onClick="window.location.reload()" value="새로고침" class="reload_btn">
			<input type="submit" value="작성" class="comment_btn">
		<br><br>
	</form>
	</div>
	<br><br>
	</div>
	</div>
	
	<script>
		function is_whatBoard(){
			var is_admin = '${SelectPost.id}';
			var is_secret = '${what}';
			
			// 게시판의 종류에따라 게시판의 이름이 달라짐
			var user_p = document.getElementById('user_board_p');		// 자유게시판
			var secret_p = document.getElementById('secret_board_p');	// 비밀게시판
			var admin_p = document.getElementById('admin_board_p');		// 공지사항
			
			// 비밀 게시판이면 이전글 다음글을 숨기기 위함
			var hideTable1 = document.getElementById('preANDnext_div1');
			var hideTable2 = document.getElementById('preANDnext_div2');
			
			if(is_admin == "admin"){
				user_p.style.display = "none";	
				secret_p.style.display = "none";
				admin_p.style.display = "block";
				hideTable1.style.display = "block";
				hideTable2.style.display = "block";
			}
			else{
				if(is_secret == 0){
					secret_p.style.display = "none";
					admin_p.style.display = "none";
					user_p.style.display = "block";
					hideTable1.style.display = "block";
					hideTable2.style.display = "block";
				}
				else{
					user_p.style.display = "none";
					admin_p.style.display = "none";
					secret_p.style.display = "block";
					hideTable1.style.display = "none";
					hideTable2.style.display = "none";
				}		
			}
			
			
		}
		is_whatBoard();
	
		function change_hyperlink_url() {		// url 주소를 인식하여 하이퍼 링크로 변환시켜주는 함수
							// url 하이퍼링크 정규식은 구글링을 통해 복사해왔다.	https://aljjabaegi.tistory.com/280
			var content_div = document.getElementById("changed_content_div");
						
			var originalContent = '${SelectPost.content}';
			
			originalContent = originalContent.replace(/<br>/ig, '\n');		// html 태그인 <br>을 프로그래밍 언어의 줄바꿈 문자(\n)로 바꾸어 줌.
			

			var regURL = new RegExp("(http|https|ftp|telnet|news|irc)://([-/.a-zA-Z0-9_~#%$?&=:200-377()]+)","gi");
			var changedContent = originalContent.replace(regURL, "<a href='$1://$2' target='_blank'>$1://$2</a>");
			changedContent = changedContent.replace(/\n/ig, '<br>');		// \n을 <br>태그로 바꾸어서 html상으로 줄바꿈이 정상적으로 나오게 바꿔줌.
			
			content_div.innerHTML = changedContent;
		}
		change_hyperlink_url();
	
	</script>
	
	
		<br><br><br><br><br><br><br><br>
        <footer>
            <div class="foot-sector">
                <div class="footer-underline">
                <nav class="footerinfo-division-top">
                    <div class="inner">
                        <div class="link-about">
                            <a href="#">이용약관</a>
                            <a href="#"><b>개인정보처리방침</b></a>
                            <a href="#">사업자정보확인</a>

                        </div>
                        <div class="link-social">
                            <div class="link-social-item">
                                <a href="#">
                                    <img src="../../../resources/img/ic-faq-32.svg" alt="question-rogo">
                                    <span>FAQ</span>
                                </a>
                                <a href="#">
                                    <img src="../../../resources/img/ic-facebook-rogo-32.svg" alt="facebook-rogo">
                                    <span>페이스북</span>
                                </a>
                                <a href="#">
                                    <img src="../../../resources/img/ic-kakaoplus-rogo-32.svg" alt="kakaoplus-rogo">
                                    <span>카카오플러스</span>
                                </a>
                                <a href="#">
                                    <img src="../../../resources/img/ic-insta-rogo-32.svg" alt="insta-rogo">
                                    <span>인스타그램</span>
                                </a>
                            </div>
                        </div>
                    </div>
                </nav>
            </div>
                <address class = "footerinfo-division-bottom">
                
                <div class="company-info">
                    <div class="company-name">(주)MOR</div>
                    <div class="bundle">
                        <span>대표이사 : 주은상</span>
                        <span>사업자등록번호 : 000-00-00000</span>
                    </div>
                    <div class="bundle">
                        <span>호스팅사업자 : (주)MOR</span>
                        <span>주소 : 안산시 단원구 MOR (호수동)</span>
                    </div>
                    <div class="bundle">
                        <span>개인정보관리책임자 : 홍길동</span>
                    </div>
                </div>
                
                <div class="servicecenter">
                    <div class="center-phonenumber">
                        <b>고객센터 0000-0000</b>
                    </div>
                    <div class="center-info">
                        <span>영업시간</span>
                        <time>AM 00:00</time>
                        ~
                        <time>PM 11:59</time>
                        (주말 및 공휴일 휴무)
                    </div>
                    <div class="center-info">
                        <span>점심시간</span>
                        <time>AM 11:00</time>
                        ~
                        <time>PM 01:00</time>
                    </div>

                </div>
               
                </address>
                
                <address class = "footerinfo-division-bottom">
					<div class="company-info">               
	                    <div class="company-name">저작권</div>
	                    <div class="bundle">
	                        <span><a href="https://www.flaticon.com/kr/free-icons/" title="폐물 아이콘">폐물 아이콘  제작자: Pavel Kozlov - Flaticon</a></span>
	                    </div>
						<div class="bundle">
	                        <span><a href="https://www.flaticon.com/kr/free-icons/-" title="맹꽁이 자물쇠 아이콘">맹꽁이 자물쇠 아이콘  제작자: DinosoftLabs - Flaticon</a></span>
	                    </div>
	                    <div class="bundle">
	                        <span><a href="https://www.flaticon.com/kr/free-icons/-" title="열린 자물쇠 아이콘">열린 자물쇠 아이콘  제작자: Freepik - Flaticon</a></span>
	                    </div>
	                </div>
				</address>

                <div class="safety"></div>
            </div>
        </footer>
	
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
	
	<c:if test="${session_msg == false}">
		<script>
			alert('ERROR\n세션이 만료되었습니다.\n다시 로그인 해주세요!!');
		</script>
	</c:if>
</body>
</html>