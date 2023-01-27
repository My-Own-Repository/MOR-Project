<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>�� ����</title>
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
	<a href="/logout">�α׾ƿ�</a>
	
	
	
	
	
	
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
	
	
	<c:if test="${logout_msg == true}">
		<script>
			alert('���������� �α׾ƿ� �Ǿ����ϴ�!');
		</script>
	</c:if>
	
	<c:if test="${session_msg == false}">
		<script>
			alert('ERROR\n������ ����Ǿ����ϴ�.\n�ٽ� �α��� ���ּ���!!');
		</script>
	</c:if>
</body>
</html>