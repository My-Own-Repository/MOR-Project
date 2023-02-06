<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@100&display=swap" rel="stylesheet">
	<script src="https://kit.fontawesome.com/5309915bbd.js" crossorigin="anonymous" defer></script>
    <link rel="stylesheet" type="text/css" href="../../../resources/css/main.css">
	<link rel="icon" type="image/jpg" href="../../../resources/img/MORicon.jpg">
	
	<script
 		src="https://code.jquery.com/jquery-3.4.1.js"
 		integrity="sha256-WpOohJOqMqqyKL9FccASB9O0KwACQJpFTUBLTYOVvVU="
 		crossorigin="anonymous"></script>
 	<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">	


<title>나만의 저장소 - MOR !</title>
</head>
<body>
	<div class="empty_main_div">
		<div class="total_main_div">
			<a href="/1">
				<img class="main-logo" src="../../../resources/img/MOR_symbol_logo.svg" />
			</a>
			<div class="search_div">
		<input type="text" id="total_search_input" class="search_input" placeholder="검색어 입력">
		<input type="button" value="검색" class="search" onClick="total_search();">
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
						<li><a href="/user/sharingRepo/1">공유 저장소</a></li>
						<li><a href="/LoginPage">나만의 저장소</a></li>
					</ul>
				</li>
				<li>
					<c:choose>
						<c:when test="${member != null}">
							<a href="/user/mypage">${member.nickname}</a>
						</c:when>
						<c:when test="${member == null}">
							<a href="/LoginPage">내정보</a>
						</c:when>
					</c:choose>
				</li>
			</ul>
			<br><br>
			<div id="fold_div" class="admin_div">
				<p class="admin_p">공지사항</p><br>
				<table border="1" class="admin_table">
					<thead class="admin_head">
						<tr>
							<td>No</td>
							<td>제목</td>
							<td>작성자</td>
							<td>조회</td>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${adminFoldList}" var="letter">	
							<tr>
								<td>${letter.num}</td>
			                    <td><a href="/unlogin_posts?urlnum=${letter.num}" class="admin_title_a">${letter.title}</a><font size="2px" color="red" class="board_comment_font">&nbsp;&nbsp;[${letter.comment}]</font></td>
			                    <td style="color:red">${letter.nickname}</td>
			                    <td>${letter.view}</td>
							</tr>
			            </c:forEach>
					</tbody>
				</table>
				<input type="button" value="v 펼치기" id="spread_btn" class="spread_btn" onClick="admin_spreading();">
			</div>
			
			<div id="spread_div" class="admin_div">
				<p class="admin_p">공지사항</p><br>
				<table border="1" class="admin_table">
					<thead class="admin_head">
						<tr>
							<td>No</td>
							<td>제목</td>
							<td>작성자</td>
							<td>조회</td>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${adminBoardList}" var="letter">	
							<tr>
								<td>${letter.num}</td>
			                    <td><a href="/unlogin_posts?urlnum=${letter.num}" class="admin_title_a">${letter.title}</a><font size="2px" color="red" class="board_comment_font">&nbsp;&nbsp;[${letter.comment}]</font></td>
			                    <td style="color:red">${letter.nickname}</td>
			                    <td>${letter.view}</td>
							</tr>
			            </c:forEach>
					</tbody>
				</table>
				<input type="button" value="^ 접기" id="fold_btn" class="fold_btn" onClick="admin_folding();">
			</div>
			<br><br><br><br>
			<hr class="hr_sty">
			<br><br><br><br>
			<p class="totalPost_p">자유게시판</p><br>
			<div id="s0r0_fold_div" class="main_div">
				<table border="1" class="board_table">
					<thead class="board_head">
						<tr>
							<td class="No_headTd">No</td>
							<td class="title_headTd">제목</td>
							<td class="nickname_headTd">작성자</td>
							<td class="date_headTd">등록일</td>
							<td class="view_headTd">조회</td>
						</tr>
					</thead>
					<tbody id="boardList_tbody">
						<c:forEach items="${fold_freeB_List}" var="letter">	
							<tr>
								<td>${letter.num}</td>
				                <td><a href="/user/posts?urlnum=${letter.num}" class="board_title_a">${letter.title}</a><font size="2px" color="red" class="board_comment_font">&nbsp;&nbsp;[${letter.comment}]</font></td>
				                <td>${letter.nickname}</td>
				                <td>${letter.date}</td>
				                <td>${letter.view}</td>
							</tr>
				    	</c:forEach>	
					</tbody>		
				</table>
				<input type="button" value="v 펼치기" id="s0r0_spread_btn" class="totalpost_spread_btn" onClick="totalPost_spreading('s0r0');">
			</div>
			
			<div id="s0r0_spread_div" class="main_div">
				<table border="1" class="board_table">
					<thead class="board_head">
						<tr>
							<td class="No_headTd">No</td>
							<td class="title_headTd">제목</td>
							<td class="nickname_headTd">작성자</td>
							<td class="date_headTd">등록일</td>
							<td class="view_headTd">조회</td>
						</tr>
					</thead>
					<tbody id="boardList_tbody">
						<c:forEach items="${freeB_List}" var="letter">	
							<tr>
								<td>${letter.num}</td>
				                <td><a href="/user/posts?urlnum=${letter.num}" class="board_title_a">${letter.title}</a><font size="2px" color="red" class="board_comment_font">&nbsp;&nbsp;[${letter.comment}]</font></td>
				                <td>${letter.nickname}</td>
				                <td>${letter.date}</td>
				                <td>${letter.view}</td>
							</tr>
				    	</c:forEach>	
					</tbody>		
				</table>
				<input type="button" value="^ 접기" id="s0r0_fold_btn" class="totalpost_fold_btn" onClick="totalPost_folding('s0r0');">
			</div>
			<br><br><br><br><br>
			<p class="totalPost_p">공유저장소</p><br>
			<div id="s0r1_fold_div" class="main_div">
				<table border="1" class="board_table">
					<thead class="board_head">
						<tr>
							<td class="No_headTd">No</td>
							<td class="title_headTd">제목</td>
							<td class="nickname_headTd">작성자</td>
							<td class="date_headTd">등록일</td>
							<td class="view_headTd">조회</td>
						</tr>
					</thead>
					<tbody id="boardList_tbody">
						<c:forEach items="${fold_sharingR_List}" var="letter">	
							<tr>
								<td>${letter.num}</td>
				                <td><a href="/user/posts?urlnum=${letter.num}" class="board_title_a">${letter.title}</a><font size="2px" color="red" class="board_comment_font">&nbsp;&nbsp;[${letter.comment}]</font></td>
				                <td>${letter.nickname}</td>
				                <td>${letter.date}</td>
				                <td>${letter.view}</td>
							</tr>
				    	</c:forEach>	
					</tbody>		
				</table>
				<input type="button" value="v 펼치기" id="s0r1_spread_btn" class="totalpost_spread_btn" onClick="totalPost_spreading('s0r1');">
			</div>
			
			<div id="s0r1_spread_div" class="main_div">
				<table border="1" class="board_table">
					<thead class="board_head">
						<tr>
							<td class="No_headTd">No</td>
							<td class="title_headTd">제목</td>
							<td class="nickname_headTd">작성자</td>
							<td class="date_headTd">등록일</td>
							<td class="view_headTd">조회</td>
						</tr>
					</thead>
					<tbody id="boardList_tbody">
						<c:forEach items="${sharingR_List}" var="letter">	
							<tr>
								<td>${letter.num}</td>
				                <td><a href="/user/posts?urlnum=${letter.num}" class="board_title_a">${letter.title}</a><font size="2px" color="red" class="board_comment_font">&nbsp;&nbsp;[${letter.comment}]</font></td>
				                <td>${letter.nickname}</td>
				                <td>${letter.date}</td>
				                <td>${letter.view}</td>
							</tr>
				    	</c:forEach>	
					</tbody>		
				</table>
				<input type="button" value="^ 접기" id="s0r1_fold_btn" class="totalpost_fold_btn" onClick="totalPost_folding('s0r1');">
			</div>
			<c:choose>
				<c:when test="${login == true}">
					<br><br><br><br><br>
					<p class="totalPost_p">비밀게시판</p><br>
					<div id="s1r0_fold_div" class="main_div">
						<table border="1" class="board_table">
							<thead class="board_head">
								<tr>
									<td class="No_headTd">No</td>
									<td class="title_headTd">제목</td>
									<td class="nickname_headTd">작성자</td>
									<td class="date_headTd">등록일</td>
									<td class="view_headTd">조회</td>
								</tr>
							</thead>
							<tbody id="boardList_tbody">
								<c:forEach items="${fold_secretB_List}" var="letter">	
					                <tr>
					                	<td>${letter.num}</td>
					                    <td id="secret_${letter.num}">
					                    	<a href="javascript:secret_unlockMode(${letter.num});" class="board_title_a">${letter.title}</a><font size="2px" color="red" class="board_comment_font">&nbsp;&nbsp;[${letter.comment}]</font>
					                    	<a href="javascript:secret_unlockMode(${letter.num});"><img src="../../../resources/img/lock.png" class="secret_password_img"></a>
					                    </td>
					                    <td id="secret_unlockMode_${letter.num}" class="secret_unlockMode_td">
					                    	<a href="#" class="board_title_a">${letter.title}</a><font size="2px" color="red" class="board_comment_font">&nbsp;&nbsp;[${letter.comment}]</font>
											<input type="password" id="secret_input_${letter.num}" maxLength='4' class="tryUnlock_input" onChange="secret_tryUnlock(${letter.num})"><img src="../../../resources/img/key.png" class="secret_password_img">
					                    </td>
					                    <td id="secret_unlockComplete_${letter.num}" class="secret_unlockComplete_td">
					                    	<a href="/user/posts?urlnum=${letter.num}" class="board_title_a">${letter.title}</a><font size="2px" color="red" class="board_comment_font">&nbsp;&nbsp;[${letter.comment}]</font>
					                    	<img src="../../../resources/img/unlock.png" class="secret_password_img">
					                    </td>
					                    <td>${letter.nickname}</td>
					                    <td>${letter.date}</td>
					                    <td>${letter.view}</td>
					                </tr> 
						    	</c:forEach>	
							</tbody>		
						</table>
						<input type="button" value="v 펼치기" id="s1r0_spread_btn" class="totalpost_spread_btn" onClick="totalPost_spreading('s1r0');">
					</div>
					
					<div id="s1r0_spread_div" class="main_div">
						<table border="1" class="board_table">
							<thead class="board_head">
								<tr>
									<td class="No_headTd">No</td>
									<td class="title_headTd">제목</td>
									<td class="nickname_headTd">작성자</td>
									<td class="date_headTd">등록일</td>
									<td class="view_headTd">조회</td>
								</tr>
							</thead>
							<tbody id="boardList_tbody">
								<c:forEach items="${secretB_List}" var="letter">	
					                <tr>
					                	<td>${letter.num}</td>
					                    <td id="secret_${letter.num}">
					                    	<a href="javascript:secret_unlockMode(${letter.num});" class="board_title_a">${letter.title}</a><font size="2px" color="red" class="board_comment_font">&nbsp;&nbsp;[${letter.comment}]</font>
					                    	<a href="javascript:secret_unlockMode(${letter.num});"><img src="../../../resources/img/lock.png" class="secret_password_img"></a>
					                    </td>
					                    <td id="secret_unlockMode_${letter.num}" class="secret_unlockMode_td">
					                    	<a href="#" class="board_title_a">${letter.title}</a><font size="2px" color="red" class="board_comment_font">&nbsp;&nbsp;[${letter.comment}]</font>
											<input type="password" id="secret_input_${letter.num}" maxLength='4' class="tryUnlock_input" onChange="secret_tryUnlock(${letter.num})"><img src="../../../resources/img/key.png" class="secret_password_img">
					                    </td>
					                    <td id="secret_unlockComplete_${letter.num}" class="secret_unlockComplete_td">
					                    	<a href="/user/posts?urlnum=${letter.num}" class="board_title_a">${letter.title}</a><font size="2px" color="red" class="board_comment_font">&nbsp;&nbsp;[${letter.comment}]</font>
					                    	<img src="../../../resources/img/unlock.png" class="secret_password_img">
					                    </td>
					                    <td>${letter.nickname}</td>
					                    <td>${letter.date}</td>
					                    <td>${letter.view}</td>
					                </tr> 
						    	</c:forEach>	
							</tbody>		
						</table>
						<input type="button" value="^ 접기" id="s1r0_fold_btn" class="totalpost_fold_btn" onClick="totalPost_folding('s1r0');">
					</div>
					<br><br><br><br><br>
					<p class="totalPost_p">비밀저장소</p><br>
					<div id="s1r1_fold_div" class="main_div">
						<table border="1" class="board_table">
							<thead class="board_head">
								<tr>
									<td class="No_headTd">No</td>
									<td class="title_headTd">제목</td>
									<td class="nickname_headTd">작성자</td>
									<td class="date_headTd">등록일</td>
									<td class="view_headTd">조회</td>
								</tr>
							</thead>
							<tbody id="boardList_tbody">
								<c:forEach items="${fold_secretR_List}" var="letter">	
					                 <tr>
					                	<td>${letter.num}</td>
					                    <td id="secret_${letter.num}">
					                    	<a href="javascript:secret_unlockMode(${letter.num});" class="board_title_a">${letter.title}</a><font size="2px" color="red" class="board_comment_font">&nbsp;&nbsp;[${letter.comment}]</font>
					                    	<a href="javascript:secret_unlockMode(${letter.num});"><img src="../../../resources/img/lock.png" class="secret_password_img"></a>
					                    </td>
					                    <td id="secret_unlockMode_${letter.num}" class="secret_unlockMode_td">
					                    	<a href="#" class="board_title_a">${letter.title}</a><font size="2px" color="red" class="board_comment_font">&nbsp;&nbsp;[${letter.comment}]</font>
											<input type="password" id="secret_input_${letter.num}" maxLength='4' class="tryUnlock_input" onChange="secret_tryUnlock(${letter.num})"><img src="../../../resources/img/key.png" class="secret_password_img">
					                    </td>
					                    <td id="secret_unlockComplete_${letter.num}" class="secret_unlockComplete_td">
					                    	<a href="/user/posts?urlnum=${letter.num}" class="board_title_a">${letter.title}</a><font size="2px" color="red" class="board_comment_font">&nbsp;&nbsp;[${letter.comment}]</font>
					                    	<img src="../../../resources/img/unlock.png" class="secret_password_img">
					                    </td>
					                    <td>${letter.nickname}</td>
					                    <td>${letter.date}</td>
					                    <td>${letter.view}</td>
					                </tr>
						    	</c:forEach>	
							</tbody>		
						</table>
						<input type="button" value="v 펼치기" id="s1r1_spread_btn" class="totalpost_spread_btn" onClick="totalPost_spreading('s1r1');">
					</div>
					
					<div id="s1r1_spread_div" class="main_div">
						<table border="1" class="board_table">
							<thead class="board_head">
								<tr>
									<td class="No_headTd">No</td>
									<td class="title_headTd">제목</td>
									<td class="nickname_headTd">작성자</td>
									<td class="date_headTd">등록일</td>
									<td class="view_headTd">조회</td>
								</tr>
							</thead>
							<tbody id="boardList_tbody">
								<c:forEach items="${secretR_List}" var="letter">	
					                 <tr>
					                	<td>${letter.num}</td>
					                    <td id="secret_${letter.num}">
					                    	<a href="javascript:secret_unlockMode(${letter.num});" class="board_title_a">${letter.title}</a><font size="2px" color="red" class="board_comment_font">&nbsp;&nbsp;[${letter.comment}]</font>
					                    	<a href="javascript:secret_unlockMode(${letter.num});"><img src="../../../resources/img/lock.png" class="secret_password_img"></a>
					                    </td>
					                    <td id="secret_unlockMode_${letter.num}" class="secret_unlockMode_td">
					                    	<a href="#" class="board_title_a">${letter.title}</a><font size="2px" color="red" class="board_comment_font">&nbsp;&nbsp;[${letter.comment}]</font>
											<input type="password" id="secret_input_${letter.num}" maxLength='4' class="tryUnlock_input" onChange="secret_tryUnlock(${letter.num})"><img src="../../../resources/img/key.png" class="secret_password_img">
					                    </td>
					                    <td id="secret_unlockComplete_${letter.num}" class="secret_unlockComplete_td">
					                    	<a href="/user/posts?urlnum=${letter.num}" class="board_title_a">${letter.title}</a><font size="2px" color="red" class="board_comment_font">&nbsp;&nbsp;[${letter.comment}]</font>
					                    	<img src="../../../resources/img/unlock.png" class="secret_password_img">
					                    </td>
					                    <td>${letter.nickname}</td>
					                    <td>${letter.date}</td>
					                    <td>${letter.view}</td>
					                </tr>
						    	</c:forEach>	
							</tbody>		
						</table>
						<input type="button" value="^ 접기" id="s1r1_fold_btn" class="totalpost_fold_btn" onClick="totalPost_folding('s1r1');">
					</div>
				</c:when>
			</c:choose>
			<br><br><br><br><br><br>
			
			
		
			<hr class="hr_sty">
		
			<br><br>
		</div>
	</div>
	
	<script>
		// 공지사항 접기
		function admin_folding(){
			var show_div = document.getElementById("fold_div");
			var hide_div = document.getElementById("spread_div");
			
			hide_div.style.display = "none";
			show_div.style.display = "block";
		}
		// 공지사항 펼치기
		function admin_spreading(){
			var show_div = document.getElementById("spread_div");
			var hide_div = document.getElementById("fold_div");
			
			hide_div.style.display = "none";
			show_div.style.display = "block";
		}
		
		
		// 전체검색 리스트들 각각 접기
		function totalPost_folding(type){
			var show_divID = type+'_fold_div';
			var hide_divID = type+'_spread_div';
			
			var show_div = document.getElementById(show_divID);
			var hide_div = document.getElementById(hide_divID);
			
			hide_div.style.display = "none";
			show_div.style.display = "block";
			/*
			if(type == 's0r0'){
				var show_div = document.getElementById("s0r0_fold_div");
				var hide_div = document.getElementById("s0r0_spread_div");
				
				hide_div.style.display = "none";
				show_div.style.display = "block";
			}
			else if(type == 's0r1'){
				var show_div = document.getElementById("s0r1_fold_div);
				var hide_div = document.getElementById("s0r1_spread_div");
				
				hide_div.style.display = "none";
				show_div.style.display = "block";
			}
			else if(type == 's1r0'){
				var show_div = document.getElementById("s1r0_fold_div");
				var hide_div = document.getElementById("s1r0_spread_div");
				
				hide_div.style.display = "none";
				show_div.style.display = "block";
			}
			else if(type == 's1r1'){
				var show_div = document.getElementById("s1r1_fold_div");
				var hide_div = document.getElementById("s1r1_spread_div");
				
				hide_div.style.display = "none";
				show_div.style.display = "block";
			}
			*/
		}
		// 전체검색 리스트들 각각 펼치기
		function totalPost_spreading(type){
			var hide_divID = type+'_fold_div';
			var show_divID = type+'_spread_div';
			
			var show_div = document.getElementById(show_divID);
			var hide_div = document.getElementById(hide_divID);
			
			hide_div.style.display = "none";
			show_div.style.display = "block";
		}
		
		function secret_unlockMode(b_num){
			// alert(b_num);
			var unlockTd_ID = 'secret_'+b_num;
			var unlockTd = document.getElementById(unlockTd_ID);
			
            
			var unlockModeTd_ID = 'secret_unlockMode_'+b_num;
 			var unlockModeTd = document.getElementById(unlockModeTd_ID);
 			
 			unlockTd.style.display = "none";
 			unlockModeTd.style.display = "block";
			
		}
		
		function secret_tryUnlock(num){
			// 비밀번호를 입력한 input 값 가져오기
			var unlockInput_ID = 'secret_input_'+num;
			var unlockInput = document.getElementById(unlockInput_ID).value;
			
			// 초기에 잠겨있는 td 요소 가져오기
			var unlockTd_ID = 'secret_'+num;
			var unlockTd = document.getElementById(unlockTd_ID);			
			
			// 잠금을 풀기위한 td 요소 가져오기
			var unlockModeTd_ID = 'secret_unlockMode_'+num;
			var unlockModeTd = document.getElementById(unlockModeTd_ID);
			
			// 잠금이 해제된 td 요소 가져오기
			var unlockComplete_ID = 'secret_unlockComplete_'+num;
			var unlockComplete = document.getElementById(unlockComplete_ID);
			// dataType : "json",  contentType: "application/json",
			$.ajax({
				type : 'post',
				url : 'CheckPassword.do',
				dataType : "json",
				data : {num:num, input:unlockInput},				
				success:function(result){				
					if(result == 1){		// 비밀번호가 일치 할 경우
						unlockTd.style.display = "none";
						unlockModeTd.style.display = "none";
						unlockComplete.style.display = "block";
					}
					else {		// 비밀번호가 일치하지 않을 경우
						alert("ERROR\n입력한 비밀번호 : "+ unlockInput +"\n비밀번호가 일치하지 않습니다.");
					}
				},
				error:function(){
					alert("ERROR\n비밀번호 체크 에러입니다.");
				}
			}); 
				
		}
		
		
		// 전체검색
		function total_search(){
			var search = document.getElementById("total_search_input").value;
			
			if(search != ''){
				var path = 'totalSearch?search='+search;
				window.location.href = path;
			}
			else{
				alert("검색어를 입력해주세요!");
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