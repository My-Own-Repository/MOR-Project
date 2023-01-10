<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>나만의 저장소 MOR</title>
    <link rel="stylesheet" href="././resources/css/login.css">
    <!-- autocomplete from jQuery Ui -->
    <script src='{% static "js/jquery-1.11.3.min.js" %}'></script>
    <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script>
 		src="https://code.jquery.com/jquery-3.4.1.js"
 		integrity="sha256-WpOohJOqMqqyKL9FccASB9O0KwACQJpFTUBLTYOVvVU="
 		crossorigin="anonymous"></script>
 		<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
</head>

<body>
	<form action="/LoginPage" method="post">
		<div class="login_container">
        <div class="inner">
            <div class="guidetext">
                <h1>안녕하세요:)<br>MOR입니다.</h1>
                <p>My Own Repository 로그인 페이지입니다.</p>
            </div>
            <div class="login_contents">
                <div class="input_wrap"><input type="text" name="id" placeholder="아이디 입력"></div>
                <div class="input_wrap"><input type="password" name="pw" placeholder="비밀번호 입력"></div>
                <div class="login_btn_area"><input type="submit" class="login_btn" value="로그인"></div>
                <div class="find_area">
                    <a href="#">아이디찾기</a>
                    <a href="#">비밀번호 찾기</a>
                    <a href="javascript:join_window()">회원가입</a>
                </div>
            </div>

            <div class="social_area">
                <h2 class="social_line">
                    <span>더욱 간편한 로그인/회원가입</span>
                </h2>

                <div class="social_btn_area">
                    <a href="#" role="button" class="btn_kakao">
                        <img src="././resources/img/icon-kakao-login-26-pt.svg" alt="카카오로그인버튼">
                        카카오로 10초만에 시작하기
                    </a>
                    <a href="#" role="button" class="btn_naver"></a>
                    <a href="#" role="button" class="btn_facebook"></a>
                    <a href="#" role="button" class="btn_google"></a>
                    <a href="#" role="button" class="btn_apple"></a>
                </div>

            </div>


        </div>
       
        
    </div>
	</form>

	<script type="text/javascript">
		function join_window(){
			// resizable은 더이상 지원을 안하네요...
			window.open("<%= request.getContextPath()%>/join", "", "width=640, height=800, resizable=no, fullscreen=no scrollbars=no, status=yes, left=600, top=100");
		}
	</script>
  
<c:if test="${msg == false}">
	<script>
		alert('ERROR\n아이디 또는 비밀번호가 일치하지 않습니다.');
	</script>
</c:if>

</body>
</html>