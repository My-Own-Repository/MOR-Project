<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="true" %>
<!DOCTYPE html>
<html lang="ko">
<head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="././resources/css/join.css">
    <link rel="icon" type="image/jpg" href="././resources/img/MORicon.jpg">
    
    <title>회원가입</title>
    <script src='{% static "js/jquery-1.11.3.min.js" %}'></script>
    <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
    <script
  src="https://code.jquery.com/jquery-3.4.1.js"
  integrity="sha256-WpOohJOqMqqyKL9FccASB9O0KwACQJpFTUBLTYOVvVU="
  crossorigin="anonymous"></script>
   
   <style>
   		/* 
   			회원가입 방식을 window.open()을 통한 새 팝업창으로 바꾸었는데,
   			팝업창에서는 jsp내의 스타일만 적용돼서 style 설정을 따로 추가했다. 
   		*/
   		@media ( max-width: 1024px ) {
        .main-logo {
        	position: relation;
			width: 86px;
			height: 86px;
        }
        .input_info {
        	position: relation;
			width: 50%;
			height: auto;
		}
        .tables {
        	position: relation;
			width: 100%;
			height: auto;
        }
        .join_button {
        	position: relation;
			width: 100%;
			height: 40px;
        }
        .is_same {
        	position: relation;
			width: 100px;
			height: 25px;
        }
        .nick_issame{
        	position: relation;
			width: 100px;
			height: 25px;
		}
	}
   </style>
   
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
</head>	
<body>
	<a href="/1">
		<img class="main-logo" src="././resources/img/MOR_symbol_logo.svg" />
	</a>
	<p align="center">*는 필수 입력 항목입니다.</p><br><br><br><br>
<form id = join_form class="wrap" method="get"> 
	<table class="tables">
		<tr>
			<th>* 아이디<br><br></th>
			<td>
				<input type="text" title="아이디" id="id" name="id" class="input_info"/>
				<input type="button" class="is_same" id="is_same" value="아이디 확인"/><br><br>

			</td>
		</tr>
		<tr>
			<th>* 비밀번호<br><br></th>
			<td>
				<input type="password" title="비밀번호" id="pw" name="pw" class="input_info"/><br><br>
			</td>
		</tr>
		<tr>
			<th>* 비밀번호 확인<br><br></th>
			<td>
				<input type="password" id="pw2" title="비밀번호" class="input_info"/><br><br>
			</td>
			
		</tr>
		<tr>
			<th>* 성명<br><br></th>
			<td>
				<input type="text" title="성명" id="name" name="name" class="input_info"/><br><br>
			</td>
		</tr>
		<tr>
			<th>* 닉네임<br><br></th>
			<td>
				<input type="text" title="닉네임" id="nickname" name="nickname" class="input_info"/>
				<input type="button" class="nick_issame" value="닉네임 확인"/><br><br>
			</td>
		</tr>
		<tr>
			<th>전화번호<br><br></th>
			<td>
				<input type="text" title="전화번호" id="phone_number" name="phone_number" class="input_info"/><br><br>
			</td>
		</tr>
		<tr>
			<th>이메일<br><br></th>
			<td>
				<input type="text" title="이메일" id="email" name="email" class="input_info"/><br><br>
			</td>
		</tr>
	</table>
	<br><br><br>
	<input type="button" class="join_button" value="회원가입">
	<br>
	


</form>
 
<script>
var integration_id = false;	// jsp단에서 쉽게 아이디가 중복됐는지 아닌지 유효성을 나타내는 전역변수.
var keep_id = null;				// 객체에 private 한 변수를 선언할 수 없어서 아이디 체크 함수에서
							// id의 값을 전역변수인 id2에 넣는 구문이다. 
							
var integration_nickname = false;	// 사용 가능한 닉네임이면 true							
var keep_nickname = null;	// 닉네임 중복검사에 사용 되는 전역변수
							
var is_not_error = false;	// (수정 요망) 입력 조건에 맞지 않으면 회원가입 불가능의 기능을 구현할 수 있는 대체용 전역변수 선언. 
							
function effectiveness() {			// 사용자 회원 가입 정보 유효성 및 무결성 검사 함수
    var pw = $("#pw").val();
    var pw2 = $("#pw2").val();
    var name = $("#name").val();
    
    if(keep_id == null){
        alert("ERROR\n아이디 입력 및 중복 확인을 해주세요.");
        return false;
    }
    
    else if(pw.length == 0){
        alert("ERROR\n비밀번호를 입력해주세요."); 
        $("#pw").focus();
        return false;
    }
 
    else if(pw2 != pw){
        alert("ERROR\n비밀번호가 서로 다릅니다. 비밀번호를 확인해주세요."); 
        $("#pw2").focus();
        return false; 
    }
 
    else if(name.length == 0){
        alert("ERROR\n이름을 입력해주세요.");
        $("#name").focus();
        return false;
    }	
    
    else if(keep_nickname == null){			
		alert("ERROR\n닉네임 입력 및 중복 확인을 해주세요.");
		$("#name").focus();
        return false;
	}
    
    else if(integration_id == false){
    	alert("ERROR\n아이디 중복 확인을 해주세요.");
        return false;
    }  
    
    else if(integration_nickname == false){		
			alert("ERROR\n닉네임 중복 확인을 해주세요.");
    		return false;  	
    }

    else if(integration_id == true && integration_nickname == true){		// (fin) 모든 양식과 무결성을 만족했을 때 실행되는 조건문
    	if(confirm("회원가입을 하시겠습니까?")){
            if(integration_id = true && integration_nickname == true) {
            	alert("회원가입을 축하합니다");
            	is_not_error = true;		
            	interation_id = false;
            	integration_nickname = false;
        	}
    	}
    }
}

function checkID(){			// 아이디 중복 체크함수
	var id = $("#id").val();
	keep_id = id;
	$.ajax({
		url : 'idCheck',
		type : 'get',
		dataType : "json",
		data : {id,id},
		success:function(result){
			if((id.search(/\W|\s/g) > -1) || id == ""){			// id 문자열에 특수문자,공백,빈칸이 포함되어있는지 확인하는 조건문
		    	alert("ERROR\n아이디는 영문/숫자만 입력해주세요.");
		    }
			else if(id.length > 16){
				alert("ERROR\n아이디는 16자 이내로 입력해주세요.");
			}			
			else if(result == 0){
				alert("SUCCESS : " + id + "\n사용 가능한 아이디입니다.");
				$("#id").attr("readonly",true); 	// 아이디 중복 체크가 성공적으로 완료되면 악용을 방지하기위해 아이디 입력 태그를 잠금.
				integration_id = true;
			}
			else {
				alert("ERROR : " + id + "\n이미 사용중인 아이디입니다.");
				keep_id = null;
				integration_id = false;
			}
		},	
		error:function(){
			alert("프로그램 에러입니다.");
		}
	});
}


function checkNICKNAME(){			// 닉네임 중복 체크함수	
    var nickname = $("#nickname").val();    
    keep_nickname = nickname;
	$.ajax({
		url : 'nickCheck',
		type : 'get',
		dataType : "json",
		data : {nickname,nickname},	
		success:function(result){
			if((nickname.search(/\W|\s/g) > -1) || nickname == ""){			// nickname 문자열에 특수문자,공백이 포함되어있는지 확인하는 조건문
		    	alert("ERROR\n닉네임은 영문/숫자만 입력해주세요.");
		    }
			else if(nickname.length > 8){
				alert("ERROR\n닉네임은 8자 이내로 입력해주세요.");
			}
			else if(result == 0){
				alert("SUCCESS : " + nickname + "\n사용 가능한 닉네임입니다.");
				$("#nickname").attr("readonly",true); 	// 아이디 중복 체크가 성공적으로 완료되면 악용을 방지하기위해 아이디 입력 태그를 잠금.
				integration_nickname = true;
			}
			else {
				alert("ERROR : " + nickname + "\n이미 사용중인 닉네임입니다.");
				keep_nickname = null;
				integration_nickname = false;
			}
		},	
		error:function(){	
			alert("프로그램 에러입니다.");
		}
	});
	
}
	
	$(document).ready(function(){	
		// 아이디 중복 확인
		$(".is_same").click(function(){
			checkID();
		});
		
		// 닉네임 중복 확인
		$(".nick_issame").click(function(){		//nick_issame
			checkNICKNAME();
		});
		
		//회원가입 버튼(회원가입 기능 작동)
		$(".join_button").click(function(){
			effectiveness();	
			if(is_not_error == true) {		
				$("#join_form").attr("action", "join");
				$("#join_form").submit();
			}
			
		});

	});
</script>  

	<c:if test="${sign_complete_msg == true}">
		<script>
			window.close();
		</script>
	</c:if>

</body>
</html>