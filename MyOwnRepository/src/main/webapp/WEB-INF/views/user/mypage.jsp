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