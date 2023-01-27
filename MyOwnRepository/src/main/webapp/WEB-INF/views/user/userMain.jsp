<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
    <link rel="stylesheet" type="text/css" href="../../../resources/css/main.css">
    <link rel="icon" type="image/jpg" href="../../../resources/img/MORicon.jpg">


<%
	//String userNickname = (String) session.getAttribute("userNickname");  
	int select_page = (int) request.getAttribute("select_page");		// 현재 선택한 페이지
	int first_page = (int) request.getAttribute("first_page");			// 현재 선택한 페이지 기준에서 첫 번째 페이지
	int last_page = (int) request.getAttribute("last_page");			// 현재 선택한 페이지 기준에서 마지막 페이지
	int page_count = (int) request.getAttribute("page_count");			// 총 페이지 개수
%> 

	<script>
 		src="https://code.jquery.com/jquery-3.4.1.js"
 		integrity="sha256-WpOohJOqMqqyKL9FccASB9O0KwACQJpFTUBLTYOVvVU="
 		crossorigin="anonymous"></script>
 		<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">

<title>나만의 저장소 - MOR !</title>
</head>
<body>
	<div class="empty_main_div">
	<div class="total_main_div">
	
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
				<li><a href="#">공유 저장소</a></li>
				<li><a href="#">나만의 저장소</a></li>
			</ul>
		</li>
		<li>
			<a href="/user/mypage">${member.nickname}</a>
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
				<td>닉네임</td>
				<td>조회</td>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${adminFoldList}" var="letter">	
				<tr>
					<td>${letter.num}</td>
                    <td><a href="/user/posts?urlnum=${letter.num}" class="admin_title_a">${letter.title}</a><font size="2px" color="red" class="board_comment_font">&nbsp;&nbsp;[${letter.comment}]</font></td>
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
				<td>닉네임</td>
				<td>조회</td>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${adminBoardList}" var="letter">	
				<tr>
					<td>${letter.num}</td>
                    <td><a href="/user/posts?urlnum=${letter.num}" class="admin_title_a">${letter.title}</a><font size="2px" color="red" class="board_comment_font">&nbsp;&nbsp;[${letter.comment}]</font></td>
                    <td style="color:red">${letter.nickname}</td>
                    <td>${letter.view}</td>
				</tr>
            </c:forEach>
		</tbody>
	</table>
	<input type="button" value="^ 접기" id="fold_btn" class="fold_btn" onClick="admin_folding();">
	</div>
	
	<br><br>
	<p>자유게시판</p><br>
	<div class="main_div">
	<table border="1" class="board_table">
		<thead class="board_head">
			<tr>
				<td>No</td>
				<td>제목</td>
				<td>닉네임</td>
				<td>등록일</td>
				<td>조회</td>
			</tr>
		</thead>
		
		<tbody>
			<c:forEach items="${BoardList}" var="letter">					
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
	
	<br><br><input type="button" onclick="location.href='/user/write_board/0'" class="write_button" value="글쓰기">
	<br><br><br><br>
	<div class="bottom_div">
		<c:if test="${first_page > 10}">
			<a href="/user/userMain/${first_page-10}"><b>&lt;&nbsp;이전</b></a>&emsp;
		</c:if>
		<div class="paging_div">
			<div class="paging_div2">
			<c:forEach begin="${first_page}" end="${last_page}" var="p">
				<c:choose>
					<c:when test="${p == select_page}">
						<b class="b_sty">${p}&emsp;</b>
					</c:when>
					<c:when test="${p != select_page}">
						<a href="/user/userMain/${p}">${p}</a>&emsp;
					</c:when>
				</c:choose>
			</c:forEach>
			</div>
		</div>
		<c:if test="${last_page != page_count}">
			&emsp;
			<a href="/user/userMain/${last_page+1}"><b>다음&nbsp;&lt;</b></a>
		</c:if>
		<hr class="hr_sty">
		<div class="board_search_div">		
				<select name='search_filter' class="in_board_search_menu">
					<option value="search_title">제목</option>
					<option value="search_cotent">내용</option>
					<option value="search_tit_cot">제목 + 내용</option>
					<option value="search_writer">작성자</option>
				</select>								
			<button class="in_board_search_btn">검색</button>
			<input type="text" class="in_board_search_text" placeholder="검색어를 입력해주세요.">	
		</div>
	</div>
	<br><br>
	</div>

	<c:if test="${session_msg == false}">
		<script>
			alert('ERROR\n세션이 만료되었습니다.\n다시 로그인 해주세요!!');
		</script>
	</c:if>
	</div>
	</div>
	
	<script>
		function admin_folding(){
			var show_div = document.getElementById("fold_div");
			var hide_div = document.getElementById("spread_div");
			
			hide_div.style.display = "none";
			show_div.style.display = "block";
		}
		
		function admin_spreading(){
			var show_div = document.getElementById("spread_div");
			var hide_div = document.getElementById("fold_div");
			
			hide_div.style.display = "none";
			show_div.style.display = "block";
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
	                </div>
				</address>

                <div class="safety"></div>
            </div>
        </footer>
	
</body>
</html>