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
    <link rel="icon" type="image/jpg" href="../../resources/img/MORicon.jpg">



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
	<div class="empty_post_div">
	<div class="total_post_div">
	<a href="/1">
		<img class="main-logo" src="../../../resources/img/MOR_symbol_logo.svg" />
	</a>
	<div class="search_div">
		<input type="text" id="total_search_input" class="search_input" placeholder="검색어 입력">
		<input type="button" value="검색" class="search" onClick="total_search();">
	</div>
	
	<ul class="menu">
		<li>
			<a href="/1">게시판</a>
			<ul class="submenu">
				<li><a href="/1">자유게시판</a></li>
				<li><a href="/LoginPage">비밀게시판</a></li>
			</ul>
		</li>
		<li>
			<a href="#">저장소</a>
			<ul class="submenu">
				<li><a href="/user/sharingRepo/1">공유 저장소</a></li>
				<li><a href="/LoginPage">나만의 저장소</a></li>
			</ul>
		</li>
		<li>
			<a href="/LoginPage">내정보</a>
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
									<div id="view_content_div">
									
									</div>
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
					<a href="/LoginPage"><input type="text" class="file_down_login" value="로그인이 필요한 서비스입니다."></a>
				</td>
			</tr>		
		</table>
		<br><br>
	</div>

	<table>	
	
				<tr>
					<td>이전글&nbsp;&nbsp;<a href="/unlogin_posts?urlnum=${pre_post.num}">${pre_post.title}</a></td>			
					<td>다음글&nbsp;&nbsp;<a href="/unlogin_posts?urlnum=${next_post.num}">${next_post.title}</a></td>
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
				<td colspan="4">
					<div id="changed_comment_div_${cmt.c_num}">
					
					</div>
				<br></td>
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
	<br><br>
	</div>

	
	<script>
		function is_whatBoard(){
			var is_admin = '${SelectPost.id}';
			var is_secret = '${what}';
			
			var user_p = document.getElementById('user_board_p');
			var secret_p = document.getElementById('secret_board_p');
			var admin_p = document.getElementById('admin_board_p');
			
			if(is_admin == "admin"){
				user_p.style.display = "none";
				secret_p.style.display = "none";
				admin_p.style.display = "block";				
			}
			else{
				if(is_secret == 0){
					secret_p.style.display = "none";
					admin_p.style.display = "none";
					user_p.style.display = "block";
				}
				else{
					user_p.style.display = "none";
					admin_p.style.display = "none";
					secret_p.style.display = "block";
				}		
			}
		}
		is_whatBoard();
	
		function changeContent_hyperlink_url() {		// url 주소를 인식하여 하이퍼 링크로 변환시켜주는 함수
							// url 하이퍼링크 정규식은 구글링을 통해 복사해왔다.	https://aljjabaegi.tistory.com/280
			
			var content_div = document.getElementById("changed_content_div");	// 수정된 게시글 내용을 넣어줄 장소 가져오기
			
			
			var originalContent = '${SelectPost.content}';		// 원본 게시글 내용 삽입
			
			if(originalContent != null){
				originalContent = originalContent.replace(/<br>/ig, '\n');		// html 태그인 <br>을 프로그래밍 언어의 줄바꿈 문자(\n)로 바꾸어 줌.
				
				var regURL = new RegExp("(http|https|ftp|telnet|news|irc)://([-/.a-zA-Z0-9_~#%$?&=:200-377()]+)","gi");
				var changedContent = originalContent.replace(regURL, "<a href='$1://$2' target='_blank'>$1://$2</a>");
				changedContent = changedContent.replace(/\n/ig, '<br>');		// \n을 <br>태그로 바꾸어서 html상으로 줄바꿈이 정상적으로 나오게 바꿔줌.
				
				content_div.innerHTML = changedContent;
			}
				
		}
		changeContent_hyperlink_url();
	
		function total_search(){
			var search = document.getElementById("total_search_input").value;

			if(search != ''){
				var path = 'user/totalSearch?search='+search;
				window.location.href = path;
			}
			else{
				alert("검색어를 입력해주세요!");
			}
		}
	</script>

	<c:if test="${msg == false}">
		<script>
			alert('ERROR\n이미 삭제된 게시글입니다!!');
		</script>
	</c:if>
	<br><br>
	</div>
	</div>
	
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
                                    <img src="../../resources/img/ic-faq-32.svg" alt="question-rogo">
                                    <span>FAQ</span>
                                </a>
                                <a href="#">
                                    <img src="../../resources/img/ic-facebook-rogo-32.svg" alt="facebook-rogo">
                                    <span>페이스북</span>
                                </a>
                                <a href="#">
                                    <img src="../../resources/img/ic-kakaoplus-rogo-32.svg" alt="kakaoplus-rogo">
                                    <span>카카오플러스</span>
                                </a>
                                <a href="#">
                                    <img src="../../resources/img/ic-insta-rogo-32.svg" alt="insta-rogo">
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
</body>
</html>