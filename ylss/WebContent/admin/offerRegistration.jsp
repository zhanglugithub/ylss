<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/admin/";
%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
<title>在线报名</title>
<body text="#999">
	<div class="odform">
		<c:if test="${code ==1}">
			<img height="100%" width="100%" src="images/offer.jpg">
		</c:if>
		<c:if test="${code ==0}">
			<a style="font-size: 25px;">${msg}</a>
		</c:if>
	</div>
</body>
</html>