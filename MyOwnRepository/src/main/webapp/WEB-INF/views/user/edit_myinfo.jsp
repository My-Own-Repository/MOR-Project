<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="true" %>
<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
     <link rel="icon" type="image/jpg" href="../../../resources/img/MORicon.jpg">
     
    <title>회원정보 수정</title>
	<script
 		src="https://code.jquery.com/jquery-3.4.1.js"
 		integrity="sha256-WpOohJOqMqqyKL9FccASB9O0KwACQJpFTUBLTYOVvVU="
 		crossorigin="anonymous"></script>
 	<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
   
   <style>
   		/* 
   			회원가입 방식을 window.open()을 통한 새 팝업창으로 바꾸었는데,
   			팝업창에서는 jsp내의 스타일만 적용돼서 style 설정을 따로 추가했다. 
   		*/
   		@media ( max-width: 1024px ) {
   		.total_div{
   			margin:auto;
   			text-align: center;
   		}
        .main-logo {
			width: 86px;
			height: 86px;
        }
        .pre_tables{
        	width: 100%;
			height: auto;
			margin: auto;
        }
        .pre_tables th{
        	width: 20%;
        }
        .pre_tables td{
        	width: 40%;
        }
        /*
        .pre_info{
        	width: 40%;
			height: auto;
        }
        */
        .input_info {
			width: 56%;
			height: auto;
		}
		.tables_div{
			width: 96%;
			height: auto;
			padding: 10px;
			border: 1px solid black;
			margin: auto;
			text-align: center;
		}
		
        .tables {
			width: 100%;
			height: auto;
			margin: auto;
			text-align: center;
        }
        .tables th {
        	width: 20%;
        }
        .tables td {
        	width: 40%;
        }
        .email_checkNum_input{
        	width: 28%;
			height: auto;
        }
        .edit_button {
        	position: relation;
			width: 40%;
			height: 40px;
			margin: 5px;
        }

		.mail_confirm_b{
			width: 25px;
			height: auto;
		}
		.edit_kinds_div{
			width: 100%;
			height: auto;
		}
		.edit_kinds_btn{
			width: 20%;
			background-color: white;
			border: 0px;
			border-bottom: 1px solid black;
		}
		.email_check_btn{
			width: auto;
			height: auto;
		}
		#edit_pw_btn{
			color: blue;
		}
		#edit_email_table{
			display: none;
		}
		#edit_phone_table{
			display: none;
		}
	}
   </style>
   
</head>	
<body>
	<div class="total_div">
		<a href="/1">
			<img class="main-logo" src="../../../resources/img/MOR_symbol_logo.svg" />
		</a><br>
		<div class="edit_kinds_div">
			<input type="button" id="edit_pw_btn" class="edit_kinds_btn" value="비밀번호" onClick="select_change('pw');">
			<input type="button" id="edit_email_btn" class="edit_kinds_btn" value="이메일" onClick="select_change('email');">
			<input type="button" id="edit_phone_btn" class="edit_kinds_btn" value="전화번호" onClick="select_change('phone');">
		</div>
		<br><br><br>
			<table class="pre_tables">
				<tr>
					<th>아이디</th>
					<td>
						${member.id}
					</td>
				</tr>
				<tr>
					<th>현재 비밀번호</th>
					<td>
						<input type="password" title="비밀번호" id="pre_pw" name="pre_pw" class="pre_info" placeholder="기존 비밀번호 입력" onChange="check_origin_pw(this.value)">
						<span id="origin_pw_confirm_msg"></span>
					</td>
				</tr>
			</table>
			<br><br><br>
		<form id="edit_form">
		<div class="tables_div">
			<table id="edit_pw_table" class="tables">
				<tr>
					<th>비밀번호</th>
					<td>
						<input type="password" title="비밀번호" id="pw" name="pw" class="input_info" placeholder="새 비밀번호"/>
					</td>
				</tr>
				<tr>
					<th>비밀번호 확인</th>
					<td>
						<input type="password" id="pw2" title="비밀번호" class="input_info" placeholder="새 비밀번호 확인"/>
						<span id="new_pw_confirm_msg"></span>
						
					</td>		
				</tr>
			</table>
			<table id="edit_email_table" class="tables">
				<tr>
					<th>새 이메일</th>
					<td>
						<input type="text" title="이메일" id="email" name="email" class="input_info" placeholder="${member.email}"/>		
						<input type="button" class="email_check_btn" value="인증번호 전송" onClick="CheckEmail();"><br>						
					</td>
				</tr>
				<tr>
					<th></th>
					<td>
						<input type="text" id="email_checkNum" class="email_checkNum_input" placeholder="인증번호 6자리" disabled="disabled" maxLength="6" onChange="CheckConfirmNum(this.value);">
						<span id="checkNum_span"></span>
					</td>
				</tr>
			</table>
			<table id="edit_phone_table" class="tables">
				<tr>
					<th>새 전화번호</th>
					<td>
						<input type="number" title="전화번호" id="phone_number" name="phone_number" class="input_info" placeholder="${member.phone_number}" maxLength="11"/>
					</td>
				</tr>
			</table>
		</div>
		<input type="hidden" name="id" value="${member.id}">
		</form>
		<br><br><br>
		<input type="button" class="edit_button" value="닫기" onClick="window.close();">
		<input type="button" class="edit_button" value="저장" onClick="edit_save();">
		<br>
	</div>


 
<script>						
	var is_confirm = false;
	var pw_confirm = false;
	var email_confirm_num = -1;		// 생성된 이메일 인증번호를 저장하는 전역변수
	var email_confirm = false; 
	
	var what_change = 'pw';		// 무엇을 변경하고 있는지 판단할수있도록 전역변수 선언
	
	function check_origin_pw(input){
		var origin_pw = '${member.pw}';
		var view_origin_confirm = document.getElementById("origin_pw_confirm_msg");
		var pre_pw_input = document.getElementById("pre_pw");
		if(origin_pw == input){
			pre_pw_input.disabled = true;
			pw_confirm = true;
			var content = '<b style="color:green">&nbsp;인증 성공!</b>';
			view_origin_confirm.innerHTML = content;
			
		}
		else {
			pre_pw_input.disabled = false;
			pw_confirm = false;
			var content = '<b style="color:red">&nbsp;인증 실패</b>';
			view_origin_confirm.innerHTML = content;
		}
	}
	
	function select_change(select){
		var pw_btn = document.getElementById("edit_pw_btn");
		var email_btn = document.getElementById("edit_email_btn");
		var phone_btn = document.getElementById("edit_phone_btn");
		
		var pw_table = document.getElementById("edit_pw_table");
		var email_table = document.getElementById("edit_email_table");
		var phone_table = document.getElementById("edit_phone_table");
		
		if(select == 'pw'){
			pw_btn.style.color = "blue";
			email_btn.style.color = "black";
			phone_btn.style.color = "black";
			
			email_table.style.display = "none";
			phone_table.style.display = "none";
			pw_table.style.display = "block";
			
			what_change = 'pw';
		}
		else if(select == 'email'){
			pw_btn.style.color = "black";
			email_btn.style.color = "blue";
			phone_btn.style.color = "black";
			
			email_table.style.display = "block";
			phone_table.style.display = "none";
			pw_table.style.display = "none";
			
			what_change = 'email';
		}
		else if(select == 'phone'){
			pw_btn.style.color = "black";
			email_btn.style.color = "black";
			phone_btn.style.color = "blue";
			
			email_table.style.display = "none";
			phone_table.style.display = "block";
			pw_table.style.display = "none";
			
			what_change = 'phone';
		}
	}
	
	function CheckEmail(){
		//alert("함수는 실행되는데");
		var userEmail = document.getElementById("email").value;
		var checkNum = document.getElementById("email_checkNum");
		
		if(userEmail.length == 0){
			alert("ERROR\n이메일을 입력해주세요.");
		}
		else{
			$.ajax({
				type : 'get',
				url : "../editCheckEmail.do?userEmail="+userEmail,
				success:function(result){
					checkNum.disabled = false;
					email_confirm_num = result;
					alert("인증번호가 전송되었습니다.");
				},
				error:function(){	
					alert("이메일 인증 에러입니다.");
				}
			});
		}	
	}
	
	function CheckConfirmNum(inputNum){
		var checkNum = document.getElementById("email_checkNum");		// 인증번호 입력칸
		
		// 인증 성공
		if(email_confirm_num == inputNum){
			$("#email").attr("readonly",true); 	// 이메일 인증이 성공적으로 완료되면 악용을 방지하기위해 이메일 입력 태그를 잠금.
			checkNum.disabled = true;	// 인증번호는 컨트롤러로 재차 넘길 필요가 없기때문에 disabled로 입력태그 잠금
			email_confirm = true;	// 인증성공을 알리기 위한 전역변수
			var checkNum_span = document.getElementById("checkNum_span");
			var checkNum_span_content = '<b style="color:green" class="mail_confirm_b">이메일 인증완료</b>';
			checkNum_span.innerHTML = checkNum_span_content;
		}
		
		//인증 실패
		else{
			$("#email").attr("readonly",false);
			checkNum.disabled = false;
			email_confirm = false;
			var checkNum_span = document.getElementById("checkNum_span");
			var checkNum_span_content = '<b style="color:red" class="mail_confirm_b">인증번호 불일치</b>';
			checkNum_span.innerHTML = checkNum_span_content;
		}
	}
	
	function edit_save(){
		
		// 비밀번호 변경시 유효성 체크
		if(what_change == 'pw'){
		    var pw = $("#pw").val();
		    var pw2 = $("#pw2").val();
		    
		    if(pw.length == 0){
		    	alert("ERROR\n변경할 비밀번호를 입력해주세요.");
		    	$("pw").focus();
		    }
		    else if(pw.length > 20){
		    	alert("ERROR\n비밀번호는 20자 이내로 입력해주세요.");
		    	$("pw").focus();
		    }
		    else if(pw2 != pw){
		        alert("ERROR\n새 비밀번호가 서로 다릅니다. 비밀번호를 확인해주세요."); 
		        $("#pw2").focus();
		    }
		    else{
				if(pw_confirm == true){
					document.getElementById("email").value = '';
					document.getElementById("phone_number").value = '';
					is_confirm = true;
				}
				else{
					is_confirm = false;
					alert("기존 비밀번호 인증을 완료해주세요.");
				}
		    }
		}
		else if(what_change == 'email'){		// 이메일 변경시 유효성 체크
			if(email_confirm == true){
				if(pw_confirm == true){
					document.getElementById("pw").value = '';
					document.getElementById("phone_number").value = '';
					is_confirm = true;
				}
				else{
					is_confirm = false;
					alert("기존 비밀번호 인증을 완료해주세요.");
				}	
			}
			else if(email_confirm == false){
				is_confirm = false;
				alert("이메일 인증을 완료해주세요.");
			}
		}
		else if(what_change == 'phone'){		// 전화번호 변경시 유효성 체크	
			if($("#phone_number").val().length == 0){
				alert("전화번호를 입력해주세요.");
			}
			else if($("#phone_number").val().length > 11 || $("#phone_number").val().length < 10){
				alert("올바른 전화번호를 입력해주세요.");
			}
			else {
				if(pw_confirm == true){
					document.getElementById("pw").value = '';
					document.getElementById("email").value = '';
					is_confirm = true;
				}
				else{
					alert("기존 비밀번호 인증을 완료해주세요.");
				}
			}
		}
	
		if(is_confirm == true){
			$.ajax({
				type : 'post',
				url : "saveUserInfo.do",
				data : $("#edit_form").serialize(),
				dataType : 'json',
				success:function(result){
					if(result.check == 'OK'){
						alert("SUCCESS\n정상적으로 수정이 완료되었습니다.\n다시 로그인 해주세요!!");
						window.close();
					}
					else if(result.check == 'NO'){
						alert("FAIL\n회원정보 수정 실패!!");
					}
				},
				error:function(){	
					alert("회원정보 저장 프로그램 오류입니다.");
				}
			});
		}
	}
</script>  


</body>
</html>