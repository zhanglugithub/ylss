<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/admin/";
%>

<html>
<head>
<base href="<%=basePath%>">

<link rel="stylesheet" href="assets/css/fonts/linecons/css/linecons.css">
<link rel="stylesheet"
	href="assets/css/fonts/fontawesome/css/font-awesome.min.css">
<link rel="stylesheet" href="assets/css/bootstrap.css">
<link rel="stylesheet" href="assets/css/xenon-core.css">

<link rel="stylesheet" href="assets/css/xenon-components.css">

<script src="assets/js/jquery-1.11.1.min.js"></script>


</head>


<body class="page-body login-page login-light"
	style="background-image: url('assets/images/bg.jpg'); background-size: cover;">


	<div class="login-container">

		<div class="row">

			<div class="col-sm-6">


				<!-- Add class "fade-in-effect" for login form effect -->
				<form name="loginForm" method="post" action="login.do"
					class="login-form "
					style="left: 190px; top: 128px; background: skyblue;">

						
			<c:if test="${result!=null}">
				<div class="alert alert-danger">
				<button type="button" class="close" data-dismiss="alert">
					<span aria-hidden="true">&times;</span> <span class="sr-only">Close</span>
				</button>

				${result.get("msg")}
			</div>
			</c:if>
					<div class="form-group">

						<input type="text" class="form-control" style="width: 100%"
							name="phoneNo" id="username" placeholder="手机号" />
					</div>

					<div class="form-group">

						<input type="password" class="form-control" style="width: 100%"
							name="password" id="passwd" placeholder="密码" />
					</div>

					<div class="form-group">
						<button type="submit" class="btn btn-primary  btn-block text-left"
							style="background-color: #2BB0F2;">
							<i class="fa-lock"></i> 登录
						</button>
					</div>

				</form>

			</div>

		</div>

	</div>



	<!-- Bottom Scripts -->
	<script src="assets/js/bootstrap.min.js"></script>
	<script src="assets/js/TweenMax.min.js"></script>
	<script src="assets/js/resizeable.js"></script>


	<!-- JavaScripts initializations and stuff -->
	<script src="assets/js/xenon-custom.js"></script>

</body>

</html>
