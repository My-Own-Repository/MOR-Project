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
    <link rel="stylesheet" type="text/css" href="././resources/css/find.css">
	<link rel="icon" type="image/jpg" href="././resources/img/MORicon.jpg">
	
	<script
 		src="https://code.jquery.com/jquery-3.4.1.js"
 		integrity="sha256-WpOohJOqMqqyKL9FccASB9O0KwACQJpFTUBLTYOVvVU="
 		crossorigin="anonymous"></script>
 	<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">	

	<style>
		h3{
			float: left;
		}
		b{
			float: left;
		}
	@media ( max-width: 1024px ) {
		body{
			background-color: lightgray;
		}
		
		input, button{
			/* ios 기본 스타일 제거 */
			appearance: none;
		    -moz-appearance: none;
		    -webkit-appearance: none;
		    border-radius: 0;
		    -webkit-border-radius: 0;
		    -moz-border-radius: 0;
		}
		
		.find_total_div{
			background-color: white;
			width: 100%;
			height: 720px;
			margin: auto;
			text-align: center;
		}
	
		.main-logo {
			display: block;
	  		width: 30%;
	  		height: 10%;
	  		background-position: center;
	 		background-size: 100% 100%;
	  		margin:auto;
	  		padding-top: 5%;
		}
		
		.find_IDPW_btn_div{
			width: 80%;
			height: 5%;
			margin: auto;
			padding-top: 10%;
			padding-bottom: 10%;
		}
		
		/* 선택시 #64AAFF, 비선택시 #BEEFFF */
		.find_ID_btn_span{
			display: flex;
			width: 40%;
			height: 100%;
			border: 1px solid #64AAFF;		
			color: black;
			align-items: center;
			text-align:	center;
			margin:auto;
			float: left;
			background-color: #64AAFF;
		}
		
		.find_PW_btn_span{
			display: flex;
			width: 40%;
			height: 100%;
			border: 1px solid #64AAFF;
			color: black;
			align-items: center;
			text-align:	center;
			margin:auto;
			float: right;
		}
		
		.find_ID_btn{
			background-color: #64AAFF;
			color:white;
			font-size: 100%;
			border: 0px;
			text-align:	center;
			margin:auto;
			
		}
		
		.find_PW_btn{
			background-color: white;
			font-size: 100%;
			border: 0px;
			text-align:	center;
			margin:auto;
		}
		
		h3{
			float: left;
		}
		b{
			float: left;
		}
		
		.find_IDPW_div{
			width: 100%;
			height: 70%;
			padding: 5%;
		}
		
		.find_ID_div{
			width: 100%;
			height: auto;
			display: block;
		}
		
		.find_PW_div{
			width: 100%;
			height: auto;
			display: none;
		}
		
		.find_table{
			padding-top: 10%;
			width:100%;
			height: auto;
			margin: auto;
		}
		
		.find_table th{
			float: left;
		}
		
		.input_info{
			width: 50%;
			height: 10%;
			float: left;
		}
		
		.input_info_btn{
			position: relative;
			width: 30%;
			height: 10%;
			background-color: #64AAFF;
			color: white;
			border: 0px;
			right: 4%;
		}
		
		.input_confirmNum{
			width: 50%;
			float: left;
		}
		
		
		/* 여기부턴 찾은 id/pw를 보여주는 부분 */		
		.view_myID_div{
			display: none;
		}
		
		.view_myPW_div{
			display: none;
		}
		
		.info_list_div{
			width: 100%;
			height: 60%;
			border: 1px solid blue;
			padding: 10px;
		}
	}
	</style>

<title>MOR - 아이디/비밀번호 찾기</title>
</head>
<body>
</head>	
<body>
	<div class="find_total_div">
		<a href="/1">
			<img class="main-logo" src="././resources/img/MOR_symbol_logo.svg" />
		</a>
		<div class="find_IDPW_btn_div">
			<span id="find_ID_btn_span" class="find_ID_btn_span">
				<input type="button" id="find_ID_btn" class="find_ID_btn" value="아이디 찾기" onClick="find_ID_JS();">
			</span>
			<span id="find_PW_btn_span" class="find_PW_btn_span">
				<input type="button" id="find_PW_btn" class="find_PW_btn" value="비밀번호 찾기" onClick="find_PW_JS();">
			</span>
		</div>

		<div class="find_IDPW_div">
			<div id="find_ID_div" class="find_ID_div">
				<h3>본인확인 이메일로 인증 - ID</h3><br><br><br>
				<b>본인인증 이메일 주소와 동일한 이메일을 입력해야,</b><br>			 
				<b>인증번호를 받을 수 있습니다.</b><br><br><br>
				<form id="find_ID_form">
					<table class="find_table">
						<tr>
							<th>이름</th>
							<td>
								<input type="text" id="name" name="name" class="input_info">
							</td>
						<tr>
						<tr>
							<th>이메일 주소</th>
							<td>
								<input type="text" id="email_id" name="email" class="input_info">
								<input type="button" class="input_info_btn" value="인증번호 받기" onClick="checkEmail();">
							</td>
						</tr>
						<tr>
							<th></th>
							<td>
								<input type="text" id="id_input_confirmNum" class="input_confirmNum" maxLength="6" placeholder="인증번호 6자리 숫자 입력" disabled="disabled" onChange="view_ID(this.value)">
								<div id="id_confirm_msg"></div>
							</td>
						</tr>
					</table>
					<input type="hidden" name="which" value="id">
					<input type="hidden" name="id" value="-1">
				</form>
			</div>
			<div id="find_PW_div" class="find_PW_div">
				<h3>본인확인 이메일로 인증 - PW</h3><br><br><br>
				<b>본인인증 이메일 주소와 동일한 이메일을 입력해야,</b><br>			 
				<b>인증번호를 받을 수 있습니다.</b><br><br><br>
				<form id="find_PW_form">
					<table class="find_table">
						<tr>
							<th>아이디</th>
							<td>
								<input type="text" id="id" name="id" class="input_info">
								
							</td>
						<tr>				
						<tr>
							<th>이메일 주소</th>
							<td>
								<input type="text" id="email_pw" name="email" class="input_info">
								<input type="button" class="input_info_btn" value="인증번호 받기" onClick="checkEmail();">
							</td>
						</tr>
						<tr>
							<th></th>
							<td>
								<input type="text" id="pw_input_confirmNum" class="input_confirmNum" maxLength="6" placeholder="인증번호 6자리 숫자 입력" disabled="disabled" onChange="view_PW(this.value)">
								<div id="pw_confirm_msg"></div>
							</td>
						</tr>
					</table>
					<input type="hidden" name="which" value="pw">
					<input type="hidden" name="name" value="-1">
				</form>
			</div>
			<div id="view_myID_div" class="view_myID_div">
				<h3>아이디 찾기</h3><br>
				<b>고객님의 정보와 일치하는 아이디 목록입니다.</b><br><br>
				<div class="info_list_div">
					
				</div>
			</div>
			<div id="view_myPW_div" class="view_myPW_div">
				<h3>비밀번호 찾기</h3>
				<b>비밀번호 확인 후 변경하시기 바랍니다.</b><br><br>
				<div class="info_list_div">
				
				</div>
			</div>
		</div>
	</div>
	<script>
	// 스타일이 제대로 적용이 되지않아서 초기값을 js로 설정.
		function start_find(){
			// 아이디/비번 찾기 div 가져오기
			document.getElementById("find_ID_div").style.display = "block";
			document.getElementById("find_PW_div").style.display = "none";
				
			// 찾은 아이디/비번 보여주는 div 가져오기
			document.getElementById("view_myID_div").style.display = "none";
			document.getElementById("view_myPW_div").style.display = "none";
		}
		start_find();
	</script>
	
	<script>
		var email_confirm_num = -1;		// 생성된 이메일 인증번호를 저장하는 전역변수
		var get_id = '';
		var get_pw = '';
		var get_signDay = '';
		
		function find_ID_JS(){		// 아이디 찾기 view
			// 아이디/비번 찾기 버튼이 있는 스판 가져오기
			var find_ID_span = document.getElementById("find_ID_btn_span");
			var find_PW_span = document.getElementById("find_PW_btn_span");
			
			// 아이디/비번 찾기 버튼 가져오기
			var find_ID_btn = document.getElementById("find_ID_btn");
			var find_PW_btn = document.getElementById("find_PW_btn");
			
			// 아이디/비번 찾기 div 가져오기
			var viewDiv = document.getElementById("find_ID_div");
			var hiddenDiv = document.getElementById("find_PW_div");
			
			// 찾은 아이디/비번 보여주는 div 가져오기
			var myID_div = document.getElementById("view_myID_div");
			var myPW_div = document.getElementById("view_myPW_div");
			
			
			find_ID_span.style.background = "#64AAFF";
			find_ID_btn.style.background = "#64AAFF";
			find_ID_btn.style.color = "white";
			
			find_PW_span.style.background = "white";
			find_PW_btn.style.background = "white";
			find_PW_btn.style.color = "black";
			
			viewDiv.style.display = "block";
			hiddenDiv.style.display = "none";
			myID_div.style.display = "none";
			myPW_div.style.display = "none";
			
		}
		
		function find_PW_JS(){		// 비밀번호 찾기 view
			// 아이디/비번 찾기 버튼이 있는 스판 가져오기
			var find_ID_span = document.getElementById("find_ID_btn_span");
			var find_PW_span = document.getElementById("find_PW_btn_span");
			
			// 아이디/비번 찾기 버튼 가져오기
			var find_ID_btn = document.getElementById("find_ID_btn");
			var find_PW_btn = document.getElementById("find_PW_btn");
			
			// 아이디/비번 찾기 div 가져오기
			var viewDiv = document.getElementById("find_PW_div");
			var hiddenDiv = document.getElementById("find_ID_div");
			
			// 찾은 아이디/비번 보여주는 div 가져오기
			var myID_div = document.getElementById("view_myID_div");
			var myPW_div = document.getElementById("view_myPW_div");
			
			
			find_PW_span.style.background = "#64AAFF";
			find_PW_btn.style.background = "#64AAFF";
			find_PW_btn.style.color = "white";
			
			find_ID_span.style.background = "white";
			find_ID_btn.style.background = "white";
			find_ID_btn.style.color = "black";
					
			viewDiv.style.display = "block";
			hiddenDiv.style.display = "none";
			myID_div.style.display = "none";
			myPW_div.style.display = "none";
			
		}

		function checkEmail(){
			// 아이디 찾기 인증메일
			if(document.getElementById("find_ID_div").style.display == "block"){
				var userName = document.getElementById("name").value;
				var userEmail = document.getElementById("email_id").value;			
				
				if(userName.length == 0){
					alert("ERROR\n이름을 입력해주세요.");
				}
				else if(userEmail.length == 0){
					alert("ERROR\n이메일을 입력해주세요.");
				}
				else{
					var checkNum = document.getElementById("id_input_confirmNum");				
					$.ajax({
						type : 'post',
						url : "findIDPW.do",
						data : $("#find_ID_form").serialize(),
						dataType : 'json',
						success:function(result){
							if(result.check == "OK"){
								checkNum.disabled = false;
								email_confirm_num = result.confirmNum;
								alert("인증번호가 전송되었습니다.");
							}
							else if(result.check == "NO"){
								checkNum.disabled = true;
								alert("FAIL\n입력하신 이름과 이메일에 일치하는 아이디가 없습니다.");
							}
						},
						error:function(){	
							alert("이메일 인증 에러입니다.");
						}
					});
				}
			}
			// 비밀번호 찾기 인증메일
			else if(document.getElementById("find_PW_div").style.display == "block"){
				var userId = document.getElementById("id").value;
				var userEmail = document.getElementById("email_pw").value;	
				
				if(userId.length == 0){
					alert("ERROR\n찾으려는 아이디를 입력해주세요.");
				}
				else if(userEmail.length == 0){
					alert("ERROR\n이메일을 입력해주세요.");
				}
				else{
					var checkNum = document.getElementById("pw_input_confirmNum");				
					$.ajax({
						type : 'post',
						url : "findIDPW.do",
						data : $("#find_PW_form").serialize(),
						dataType : 'json',
						success:function(result){
							if(result.check == "OK"){
								checkNum.disabled = false;
								email_confirm_num = result.confirmNum;
								alert("인증번호가 전송되었습니다.");
							}
							if(result.check == "NO"){
								checkNum.disabled = true;
								alert("FAIL\n입력하신 이메일과 일치하는 아이디가 없습니다.");
							}
						},
						error:function(){	
							alert("이메일 인증 에러입니다.");
						}
					});
				}
			}
			

		}
		// 찾은 아이디/비밀번호 보여줌
		function view_ID(input_num){
			if(input_num == email_confirm_num){
				// 입력칸 모두 잠금 - 인증번호칸은 컨트롤러로 넘길 필요가 없기때문에 disabled 사용
				$("#name").attr("readonly",true);
				$("#email_id").attr("readonly",true);
				document.getElementById("id_input_confirmNum").disabled = true;
				
				var view_msg = document.getElementById("id_confirm_msg");
				var content = '<b style="color:green">&nbsp;인증 성공!</b>';
				view_msg.innerHTML = content;
				
				var which = 'id';
				var user = document.getElementById("name").value;
				var email = document.getElementById("email_id").value;
				
				var path = 'show_myIDPW/'+user;
				path += '/'+email;
				path += '/'+which;
				
				window.open("<%= request.getContextPath()%>/"+path, "", "width=420, height=320, resizable=no, fullscreen=no scrollbars=no, status=yes, left=600, top=100");
			}
			else{
				var view_msg = document.getElementById("id_confirm_msg");
				var content = '<b style="color:red">&nbsp;인증번호 불일치</b>';
				view_msg.innerHTML = content;
			}
		}
		function view_PW(input_num){
			if(input_num == email_confirm_num){
				// 입력칸 모두 잠금 - 인증번호칸은 컨트롤러로 넘길 필요가 없기때문에 disabled 사용
				$("#id").attr("readonly",true);
				$("#email_pw").attr("readonly",true);
				document.getElementById("pw_input_confirmNum").disabled = true;
				
				var view_msg = document.getElementById("pw_confirm_msg");
				var content = '<b style="color:green">&nbsp;인증 성공!</b>';
				view_msg.innerHTML = content;
				
				var which = 'pw';
				var user = document.getElementById("id").value;
				var email = document.getElementById("email_pw").value;
				
				var path = 'show_myIDPW/'+user;
				path += '/'+email;
				path += '/'+which;
				
				window.open("<%= request.getContextPath()%>/"+path, "", "width=480, height=480, resizable=no, fullscreen=no scrollbars=no, status=yes, left=600, top=100");
			}
			else{
				var view_msg = document.getElementById("pw_confirm_msg");
				var content = '<b style="color:red">&nbsp;인증번호 불일치</b>';
				view_msg.innerHTML = content;
			}
		}
	</script>
</body>
</html>