<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>마이페이지</title>
    <!-- autocomplete from jQuery Ui -->
    <script src='{% static "js/jquery-1.11.3.min.js" %}'></script>
    <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
    <link rel="stylesheet" type="text/css" href="../../../resources/css/mypage.css">
    <link rel="icon" type="image/jpg" href="../../../resources/img/MORicon.jpg">
    
    <!-- JQuery에서 제공하는 이미지 클릭시 크게보기 
    <link rel="stylesheet" href="/styles/vendor/jquery.fancybox.min.css">
	<script src="/scripts/vendor/jquery.fancybox.min.js"></script>
    -->
    
	<script
 		src="https://code.jquery.com/jquery-3.4.1.js"
 		integrity="sha256-WpOohJOqMqqyKL9FccASB9O0KwACQJpFTUBLTYOVvVU="
 		crossorigin="anonymous"></script>
</head>
<body>
	<div class="total_div">
		<div class="mypage_top">
			<a href="/user/userMain/1">
				<span class="go_main_MOR">
					MOR
				</span>
			</a>
			<a href="/user/userMain/1">
				<span class="go_board_OR_repo">
					자유게시판
				</span>
			</a>
			<a href="/user/secretBoard/1">
				<span class="go_board_OR_repo">
					비밀게시판
				</span>
			</a>
			<a href="/user/sharingRepo/1">
				<span class="go_board_OR_repo">
					공유저장소
				</span>
			</a>
			<a href="/user/myRepo/1">
				<span class="go_board_OR_repo">
					비밀저장소
				</span>
			</a>
		</div>
		<br><br><br><br>
		<div id="info_total_content_div" class="total_content_div">
			<div class="mypage_middle">
				<div class="mypage_title">
					<b class="mypage_title_b">마이페이지</b>
				</div>
				<div class="mypage_select">
					<span id="my_info_span" class="my_info_AND_file_span">
						<input type="button" id="my_info_span_btn" class="clicked_btn" value="내정보" onClick="mypage_infoMode();">
					</span>
					<span id="my_file_span" class="my_info_AND_file_span">
						<input type="button" id="my_file_span_btn" class="noneClicked_btn" value="내파일" onClick="mypage_fileMode();">
					</span>
				</div>
			</div>
			<br><br>
			<div class="mypage_bottom">
				<br><br>
				<div class="mypage_bottom_div">
					<div class="my_info_div">
						<div class="my_info_div_top">
							<img src="../../../resources/img/MOR_symbol_logo.svg" class="MOR_logo">
							<h4>${member.nickname}님, 나만의 저장소 mor을 이용해주셔서 감사합니다.</h4>
						</div>
						<div class="my_info_div_bottom">
							<table>
								<tr>
									<th>아이디</th>
									<td>&emsp;&emsp;${member.id}</td>
								</tr>
								<tr>
									<th>이름&emsp;</th>
									<td>&emsp;&emsp;${member.name}</td>
								</tr>
								<tr>
									<th>이메일</th>
									<td>&emsp;&emsp;${member.email}</td>
								</tr>
							</table>
							<input type="button" class="my_info_edit_btn" value="정보수정" onClick="edit_myInfo();">
						</div>
						<input type="button" class="my_info_unregister_btn" value="회원탈퇴" onClick="unregister();">
					</div>
					<br><br>
					<div class="my_post_div">
						<p class="my_post_AND_comment_title_p">
							내가 쓴 글
						</p>
						<hr class="my_post_AND_comment_hr">
						<div class="my_post_AND_comment_div_content">
							<div class="my_posts_AND_comments_List_div">
								<c:forEach items="${myPosts}" var="p">
									<p class="my_posts_AND_comments_p">${p.num}&emsp;&emsp;
									<a href="/user/posts?urlnum=${p.num}">${p.title}</a>
									<font color="red" size="1px">&ensp;[${p.comment}]</font></p>
									<hr class="my_posts_AND_comments_List_div_hr">
								</c:forEach>
							</div>
						</div>
					</div>
					<div class="my_comment_div">
						<p class="my_post_AND_comment_title_p">
							내가 쓴 댓글
						</p>
						<hr class="my_post_AND_comment_hr">
						<div class="my_post_AND_comment_div_content">
							<div class="my_posts_AND_comments_List_div">
								<c:forEach items="${myComments}" var="c">
									<p class="my_posts_AND_comments_p">${c.b_num}&emsp;&emsp;
									<a href="/user/posts?urlnum=${c.b_num}">${c.content}</a></p>
									<hr class="my_posts_AND_comments_List_div_hr">
								</c:forEach>
							</div>
						</div>
					</div>	
				</div>		
			</div>
		</div>
		
		<div id="file_total_content_div" class="total_content_div">
			<div class="mypage_middle">
				<div class="mypage_title">
					<b class="mypage_title_b">마이페이지</b>
				</div>
				<div class="mypage_select">
					<span id="my_info_span" class="my_info_AND_file_span">
						<input type="button" id="my_info_span_btn" class="noneClicked_btn" value="내정보" onClick="mypage_infoMode();">
					</span>
					<span id="my_file_span" class="my_info_AND_file_span">
						<input type="button" id="my_file_span_btn" class="clicked_btn" value="내파일" onClick="mypage_fileMode();">
					</span>
				</div>
			</div>
			<br><br>
			<div class="mypage_bottom">
				<br><br>
				<div class="mypage_file_bottom_div">
					<div class="myfile_img_video_file_div">
						<img src="../../../resources/img/camera.png" class="myfile_img">
						
						<div class="myfile_content">
							<c:forEach items="${myImage}" var="imgs">
								<div class="files_view_div">
									<img src="/loadfiles.do/${imgs.file_num}" class="myfile_imgs" alt="">
									<br>
									<a href="/downfiles.do/${imgs.file_num}" class="files_view_div_download">${imgs.original_file_name}</a>
								</div>
							</c:forEach>
						</div>
						
					</div>
					<br><br>
					<div class="myfile_img_video_file_div">
						<img src="../../../resources/img/video.png" class="myfile_video">
						
						<div class="myfile_content">
							<c:forEach items="${myVideo}" var="videos">
								<div class="files_view_div">
									<video src="/loadfiles.do/${videos.file_num}" class="myfile_videos" controls></video>
									<br>
									<a href="/downfiles.do/${videos.file_num}" class="files_view_div_download">${videos.original_file_name}</a>
								</div>								
							</c:forEach>
						</div>
						
					</div>
					<br><br>
					<div class="myfile_img_video_file_div">
						<img src="../../../resources/img/file.png" class="myfile_file">
						
						<div class="myfile_content">
							<c:forEach items="${myExcept}" var="excepts">
								<div class="files_view_div">
									<img src="../../../resources/img/fileImg.png" class="myfile_excepts" alt="">
									<br>
									<a href="/downfiles.do/${excepts.file_num}" class="files_view_div_download">${excepts.original_file_name}</a>
								</div>
							</c:forEach>
						</div>
			
					</div>
					<br><br>
				</div>
			</div>
		</div>	
	</div>
	
	<script>
		// 마이페이지 - 내정보
		function mypage_infoMode(){	
			window.location.reload();
		}
		
		// 마이페이지 - 내파일
		function mypage_fileMode(){			
			var info_btn_span = document.getElementById("my_info_span");
			var file_btn_span = document.getElementById("my_file_span");
			
			var info_btn = document.getElementById("my_info_span_btn");
			var file_btn = document.getElementById("my_file_span_btn");
			
			var create_info_btn = document.createElement("input");
			create_info_btn.type = "button";
			create_info_btn.id = "my_info_span_btn";
			create_info_btn.value = "내정보";
			create_info_btn.className = "noneClicked_btn";
			
			var create_file_btn = document.createElement("input");
			create_file_btn.type = "button";
			create_file_btn.id = "my_file_span_btn";
			create_file_btn.value = "내파일";
			create_file_btn.className = "clicked_btn";
			
			info_btn.remove();
			file_btn.remove();
			
			info_btn_span.appendChild(create_info_btn);
			file_btn_span.appendChild(create_file_btn);
					
			$("#info_total_content_div").css({
				"display":"none"
			})
			$("#file_total_content_div").css({
				"display":"block"
			})
			
		}
		
		// 마이페이지 - 내정보 수정
		function edit_myInfo(){
			window.open("<%= request.getContextPath()%>/user/edit_myinfo", "", "width=600, height=600, resizable=no, fullscreen=no scrollbars=no, status=yes, left=600, top=100");
		}
		
		// 마이페이지 - 내파일 이미지 원본크기로 보기
		var img = document.getElementsByClassName("myfile_imgs");
		for (var i = 0; i < img.length; i++) {
			img.item(i).onclick=function() {window.open(this.src)}; 
		}
		
		
		// 회원탈퇴
		function unregister(){
			if(confirm("정말로 회원탈퇴를 하시겠습니까?")){
				alert("회원탈퇴가 완료되었습니다.");
				var unregister_ID = '${member.id}';
				window.location.href = '../unregister.do/'+unregister_ID;
			}
		}
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
	                    <div class="bundle">
	                        <a href="https://www.flaticon.com/kr/free-icons/" title="사진술 아이콘">사진술 아이콘  제작자: Good Ware - Flaticon</a>
	                    </div>
	                    <div class="bundle">
	                        <a href="https://www.flaticon.com/kr/free-icons/" title="비디오 아이콘">비디오 아이콘  제작자: Iconjam - Flaticon</a>
	                    </div>
	                    <div class="bundle">
	                        <a href="https://www.flaticon.com/kr/free-icons/" title="폴더 아이콘">폴더 아이콘  제작자: Freepik - Flaticon</a>
	                    </div>
	                    <div class="bundle">
	                        <a href="https://www.flaticon.com/kr/free-icons/" title="파일 아이콘">파일 아이콘  제작자: DinosoftLabs - Flaticon</a>
	                    </div>
	                </div>
				</address>

                <div class="safety"></div>
            </div>
        </footer>
	
	
	<c:if test="${logout_msg == true}">
		<script>
			alert('정상적으로 로그아웃 되었습니다!');
		</script>
	</c:if>
	
	<c:if test="${session_msg == false}">
		<script>
			alert('ERROR\n세션이 만료되었습니다.\n다시 로그인 해주세요!!');
		</script>
	</c:if>
</body>
</html>