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
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width">
<title>申请成为医生</title>
<link rel="stylesheet" href="css/style.css">
<link rel="stylesheet" href="css/fort.css">
<style>
.top-banner {
	background-color: #333;
}

.nav {
	margin-bottom: 30px;
}

.nav li.current a {
	background-color: #009DFF;
	color: #fff;
	padding: 10px;
}

.nav a {
	margin: 5px;
	color: #333;
	text-decoration: none;
}
</style>
<script src="js/fort.js"></script>
<script type="text/javascript">
	function MM_popupMsg(msg) { //v1.0
		alert(msg);
	}
</script>
</head>
<body>
	<p></p>
	<p>
		<img src="images/123.png" width="100%" height="100%" alt="" />
	</p>
	<form name="form" action="addOffer.do" method="post">
		<div class="form">
			<div class="form-item">
				<input type="text" name="name" required placeholder="姓名"
					autocomplete="off">
			</div>
			<div class="form-item">
				<input type="number" name="phoneNo" required placeholder="手机号"
					autocomplete="off">
			</div>
			<div class="form-item">
				<input type="text" name="hospital" required placeholder="所在医院"
					autocomplete="off">
			</div>
			<div class="form-item">
				<input type="text" name="requirement" required placeholder="科室"
					autocomplete="off">
			</div>
			<div class="button-panel">
				<input type="submit" class="button" title="Sign In" value="立即提交">
				<!-- 				<input type="submit" class="button" title="Sign In" -->
				<!-- 					onClick="MM_popupMsg('提交成功')" value="立即提交"> -->
			</div>
		</div>
	</form>
	<div class="footer-banner" style="width: 728px; margin: 200px auto 0"></div>
	<script>
		solid();
	</script>

	<div
		style="text-align: center; margin: 50px 0; font: normal 14px/24px 'MicroSoft YaHei';">
	</div>
</body>
</html>
