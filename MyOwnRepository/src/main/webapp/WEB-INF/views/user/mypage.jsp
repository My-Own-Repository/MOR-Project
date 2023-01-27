<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>내 정보</title>
    <!-- autocomplete from jQuery Ui -->
    <script src='{% static "js/jquery-1.11.3.min.js" %}'></script>
    <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
    <link rel="icon" type="image/jpg" href="../../../resources/img/MORicon.jpg">
    
	<script>
 		src="https://code.jquery.com/jquery-3.4.1.js"
 		integrity="sha256-WpOohJOqMqqyKL9FccASB9O0KwACQJpFTUBLTYOVvVU="
 		crossorigin="anonymous"></script>
</head>
<body>
	<a href="/logout">로그아웃</a>
	
	
	
	
	
	
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