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
	int select_page = (int) request.getAttribute("select_page");		// ���� ������ ������
	int first_page = (int) request.getAttribute("first_page");			// ���� ������ ������ ���ؿ��� ù ��° ������
	int last_page = (int) request.getAttribute("last_page");			// ���� ������ ������ ���ؿ��� ������ ������
	int page_count = (int) request.getAttribute("page_count");			// �� ������ ����
%> 

	<script
 		src="https://code.jquery.com/jquery-3.4.1.js"
 		integrity="sha256-WpOohJOqMqqyKL9FccASB9O0KwACQJpFTUBLTYOVvVU="
 		crossorigin="anonymous"></script>
 		<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">

<title>������ ����� - MOR !</title>
</head>
<body>
	<div class="empty_main_div">
	<div class="total_main_div">
	
	<a href="/user/userMain/1">
		<img class="main-logo" src="../../../resources/img/MOR_symbol_logo.svg" />
	</a>
	<div class="search_div">
		<input type="text" class="search_input" placeholder="�˻��� �Է�">
		<input type="button" value="�˻�" class="search">
	</div>
	
	<ul class="menu">
		<li>
			<a href="#">�Խ���</a>
			<ul class="submenu">
				<li><a href="/user/userMain/1">�����Խ���</a></li>
				<li><a href="/user/secretBoard/1">��аԽ���</a></li>
			</ul>
		</li>
		<li>
			<a href="#">�����</a>
			<ul class="submenu">
				<li><a href="/user/sharingRepo/1">���� �����</a></li>
				<li><a href="/user/myRepo/1">������ �����</a></li>
			</ul>
		</li>
		<li>
			<a href="/user/mypage">${member.nickname}</a>
		</li>
	</ul>
	
	<br><br>
	<div id="fold_div" class="admin_div">
	<p class="admin_p">��������</p><br>
	<table border="1" class="admin_table">
		<thead class="admin_head">
			<tr>
				<td>No</td>
				<td>����</td>
				<td>�г���</td>
				<td>��ȸ</td>
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
	<input type="button" value="v ��ġ��" id="spread_btn" class="spread_btn" onClick="admin_spreading();">
	</div>
	
	<div id="spread_div" class="admin_div">
	<p class="admin_p">��������</p><br>
	<table border="1" class="admin_table">
		<thead class="admin_head">
			<tr>
				<td>No</td>
				<td>����</td>
				<td>�г���</td>
				<td>��ȸ</td>
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
	<input type="button" value="^ ����" id="fold_btn" class="fold_btn" onClick="admin_folding();">
	</div>
	
	
	<br><br><br><br>
	<p>��аԽ���</p><br>
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
	
	<br><br>
	<c:choose>
		<c:when test="${member.id == null}">
			<input type="button" onclick="location.href='/LoginPage'" class="write_button" value="�۾���">
		</c:when>
		
		<c:when test="${member.id != null}">
			<input type="button" onclick="location.href='/user/write_board/s1r0'" class="write_button" value="�۾���">
		</c:when>
	</c:choose>

	<br><br><br><br>
	<div class="bottom_div">
		<c:if test="${first_page > 5}">
			<a href="/user/secretBaord/${first_page-5}"><b>&lt;&nbsp;����</b></a>&emsp;
		</c:if>
		<div class="paging_div">
			<div class="paging_div2">
			<c:forEach begin="${first_page}" end="${last_page}" var="p">
				<c:choose>
					<c:when test="${p == select_page}">
						<b class="b_sty">${p}&emsp;</b>
					</c:when>
					<c:when test="${p != select_page}">
						<a href="/user/secretBaord/${p}">${p}</a>&emsp;
					</c:when>
				</c:choose>
			</c:forEach>
			</div>
		</div>
		<c:if test="${last_page != page_count}">
			&emsp;
			<a href="/user/secretBaord/${last_page+1}"><b>����&nbsp;&lt;</b></a>
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

	<c:if test="${session_msg == false}">
		<script>
			alert('ERROR\n������ ����Ǿ����ϴ�.\n�ٽ� �α��� ���ּ���!!');
		</script>
	</c:if>
	<br><br>
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
			// ��й�ȣ�� �Է��� input �� ��������
			var unlockInput_ID = 'secret_input_'+num;
			var unlockInput = document.getElementById(unlockInput_ID).value;
			
			// �ʱ⿡ ����ִ� td ��� ��������
			var unlockTd_ID = 'secret_'+num;
			var unlockTd = document.getElementById(unlockTd_ID);			
			
			// ����� Ǯ������ td ��� ��������
			var unlockModeTd_ID = 'secret_unlockMode_'+num;
			var unlockModeTd = document.getElementById(unlockModeTd_ID);
			
			// ����� ������ td ��� ��������
			var unlockComplete_ID = 'secret_unlockComplete_'+num;
			var unlockComplete = document.getElementById(unlockComplete_ID);
			// dataType : "json",  contentType: "application/json",
			$.ajax({
				type : 'post',
				url : 'CheckPassword.do',
				dataType : "json",
				data : {num:num, input:unlockInput},				
				success:function(result){				
					if(result == 1){		// ��й�ȣ�� ��ġ �� ���
						unlockTd.style.display = "none";
						unlockModeTd.style.display = "none";
						unlockComplete.style.display = "block";
					}
					else {		// ��й�ȣ�� ��ġ���� ���� ���
						alert("ERROR\n�Է��� ��й�ȣ : "+ unlockInput +"\n��й�ȣ�� ��ġ���� �ʽ��ϴ�.");
					}
				},
				error:function(){
					alert("ERROR\n��й�ȣ üũ �����Դϴ�.");
				}
			}); 
			
			
/*
 			var correct_num;
			
			$.ajax({
				type : 'post',
				url : 'CheckPassword.do',
				dataType : "json",
				data : {num:num},
				success:function(result){
					correct_num = result;
					
					if(correct_num == Number(unlockInput)){
						unlockTd.style.display = "none";
						unlockModeTd.style.display = "none";
						unlockComplete.style.display = "block";
					}
					else {
						alert("ERROR\n�Է��� ��й�ȣ : "+ unlockInput +"\n��й�ȣ�� ��ġ���� �ʽ��ϴ�.");
					}
				},
				error:function(){
					alert("ERROR\n��й�ȣ üũ �����Դϴ�.");
				}
			}); 
 
 */
					
		
		}
	</script>
	
	
		<br><br><br><br><br><br><br><br>
        <footer>
            <div class="foot-sector">
                <div class="footer-underline">
                <nav class="footerinfo-division-top">
                    <div class="inner">
                        <div class="link-about">
                            <a href="#">�̿���</a>
                            <a href="#"><b>��������ó����ħ</b></a>
                            <a href="#">���������Ȯ��</a>

                        </div>
                        <div class="link-social">
                            <div class="link-social-item">
                                <a href="#">
                                    <img src="../../../resources/img/ic-faq-32.svg" alt="question-rogo">
                                    <span>FAQ</span>
                                </a>
                                <a href="#">
                                    <img src="../../../resources/img/ic-facebook-rogo-32.svg" alt="facebook-rogo">
                                    <span>���̽���</span>
                                </a>
                                <a href="#">
                                    <img src="../../../resources/img/ic-kakaoplus-rogo-32.svg" alt="kakaoplus-rogo">
                                    <span>īī���÷���</span>
                                </a>
                                <a href="#">
                                    <img src="../../../resources/img/ic-insta-rogo-32.svg" alt="insta-rogo">
                                    <span>�ν�Ÿ�׷�</span>
                                </a>
                            </div>
                        </div>
                    </div>
                </nav>
            </div>
                <address class = "footerinfo-division-bottom">
                
                <div class="company-info">
                    <div class="company-name">(��)MOR</div>
                    <div class="bundle">
                        <span>��ǥ�̻� : ������</span>
                        <span>����ڵ�Ϲ�ȣ : 000-00-00000</span>
                    </div>
                    <div class="bundle">
                        <span>ȣ���û���� : (��)MOR</span>
                        <span>�ּ� : �Ȼ�� �ܿ��� MOR (ȣ����)</span>
                    </div>
                    <div class="bundle">
                        <span>������������å���� : ȫ�浿</span>
                    </div>
                </div>
                
                <div class="servicecenter">
                    <div class="center-phonenumber">
                        <b>������ 0000-0000</b>
                    </div>
                    <div class="center-info">
                        <span>�����ð�</span>
                        <time>AM 00:00</time>
                        ~
                        <time>PM 11:59</time>
                        (�ָ� �� ������ �޹�)
                    </div>
                    <div class="center-info">
                        <span>���ɽð�</span>
                        <time>AM 11:00</time>
                        ~
                        <time>PM 01:00</time>
                    </div>

                </div>
               
                </address>
                
                <address class = "footerinfo-division-bottom">
					<div class="company-info">               
	                    <div class="company-name">���۱�</div>
	                    <div class="bundle">
	                        <span><a href="https://www.flaticon.com/kr/free-icons/" title="�� ������">�� ������  ������: Pavel Kozlov - Flaticon</a></span>
	                    </div>
						<div class="bundle">
	                        <span><a href="https://www.flaticon.com/kr/free-icons/-" title="�Ͳ��� �ڹ��� ������">�Ͳ��� �ڹ��� ������  ������: DinosoftLabs - Flaticon</a></span>
	                    </div>
	                    <div class="bundle">
	                        <span><a href="https://www.flaticon.com/kr/free-icons/-" title="���� �ڹ��� ������">���� �ڹ��� ������  ������: Freepik - Flaticon</a></span>
	                    </div>
	                </div>
				</address>

                <div class="safety"></div>
            </div>
        </footer>
        
</body>
</html>