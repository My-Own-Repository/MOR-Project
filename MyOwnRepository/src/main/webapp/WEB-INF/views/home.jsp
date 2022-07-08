<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
<title>MOR</title>
</head>
<body>
    <h1>MOR MEMBERS</h1>
 
    <table>
        <thead>
            <tr>
                <th>아이디</th>
                <th>비밀번호</th>
                <th>이름</th>
                <th>닉네임</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${memberList}" var="member">
                <tr>
                    <td>${member.id}</td>
                    <td>${member.pw}</td>
                    <td>${member.name}</td>
                    <td>${member.nickname}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
 
 
</body>
</html>
