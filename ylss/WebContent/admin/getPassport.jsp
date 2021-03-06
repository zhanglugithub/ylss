<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/admin/";
%>
<!DOCTYPE html>
<html lang="zh-cmn-Hans">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<base href="<%=basePath%>">
<title>身份证认证</title>
<!-- 小图标 -->
<link rel="stylesheet" href="assets/css/fonts/linecons/css/linecons.css">
<!-- 侧滑下拉 -->
<link rel="stylesheet"
	href="assets/css/fonts/fontawesome/css/font-awesome.min.css">
<!-- 关键布局 -->
<link rel="stylesheet" href="assets/css/bootstrap.css">
<!-- 关键布局 -->
<link rel="stylesheet" href="assets/css/xenon-core.css">
<script src="assets/js/jquery-1.11.1.min.js"></script>
</head>
<body class="page-body">
	<div class="page-container">

		<div class="sidebar-menu toggle-others fixed">

			<div class="sidebar-menu-inner">

				<header class="logo-env">

					<!-- logo -->
					<div class="logo"></div>

				</header>

				<ul id="main-menu" class="main-menu">

					<li><a href="index.jsp"> <span class="title">返回首页</span>
					</a></li>
					<li><a href="#"> <i class="linecons-cog"></i> <span
							class="title">参数修改</span>
					</a>
						<ul>
							<li><a href="alterLength.jsp"> <span class="title">可见距离</span>
							</a></li>
							<li><a href="alterPercentDoctorGet.jsp"> <span
									class="title">平台抽成</span>
							</a></li>

						</ul></li>


					<li><a href="lockUser.jsp"> <i class="linecons-user"></i>
							<span class="title">用户管理</span>
					</a></li>
					<li><a href="listDoctorVerify.do?pageNo=1&pageSize=10"> <i
							class="linecons-star"></i><span class="title">医生审核</span>
					</a></li>


					<li><a href="listFeedback.do?pageNo=1&pageSize=10"> <i
							class="linecons-heart"></i> <span class="title">查看意见</span>
					</a></li>
					<li><a href=""> <i class="linecons-mail"></i> <span
							class="title">订单管理</span>
					</a>

						<ul>
							<li><a href="listAllOrder.do?pageNo=1&pageSize=10"> <span
									class="title">查看订单</span>
							</a></li>
							<li><a href="cancelOrder.jsp"> <span class="title">取消订单</span>
							</a></li>

						</ul></li>
					<li><a href="#"> <i class="linecons-money"></i> <span
							class="title">提现管理</span>
					</a>
						<ul>
							<li><a href="listWithdraw.do?pageNo=1&pageSize=10"> <span
									class="title">提现审核</span>
							</a></li>
							<li><a href="alterDoctorAlipay.jsp"> <span class="title">医生支付宝帐号修改</span>
							</a></li>
						</ul></li>
					<li><a href="#"> <i class="linecons-note"></i> <span
							class="title">招聘管理</span>
					</a>
						<ul>
							<li><a href="listApplyOffer.do?pageNo=1&pageSize=10"> <span
									class="title">查看应聘</span>
							</a></li>
							<li><a href="addOffer.jsp"> <span class="title">添加招聘</span>
							</a></li>
						</ul></li>

				</ul>

			</div>

		</div>

		<div class="main-content">

			<!-- User Info, Notifications and Menu Bar -->
			<nav class="navbar user-info-navbar" role="navigation">
				<ul class="user-info-menu middle-links list-inline list-unstyled"
					style="padding-left: 90px; color: black;">
					<li><h3 align="center">
							<strong> 欢迎访问医来伸手后台管理系统 </strong>
						</h3></li>
				</ul>
				<!-- Right links for user info navbar -->
				<ul class="user-info-menu right-links list-inline list-unstyled">

					<li class="dropdown user-profile"><a href="#"
						data-toggle="dropdown"> <img src="assets/images/user-4.png"
							alt="user-image" class="img-circle img-inline userpic-32"
							width="28" /> <span>管理员<i class="fa-angle-down"></i>
						</span>
					</a>

						<ul class="dropdown-menu user-profile-menu list-unstyled">
							<li><a href="#help"> <i class="fa-info"></i> 帮助
							</a></li>
							<li class="last"><a href="logout.do"> <i class="fa-lock"></i>
									退出
							</a></li>
						</ul></li>


				</ul>

			</nav>
			<div class="page-title">

				<div class="title-env">
					<h1 class="title">审核医生</h1>
					<p class="description">审核</p>
				</div>



			</div>

			<div>
				<c:if test="${msg!=null}">
					<c:if test="${passport.errNum !=0}">
						<div class="alert alert-success">
							<button type="button" class="close" data-dismiss="alert">
								<span aria-hidden="true">&times;</span> <span class="sr-only">Close</span>
							</button>

							${msg}
						</div>
					</c:if>
				</c:if>

				<div class="panel panel-default">
					<!-- start content -->
					<c:if test="${passport.errNum!=0}">
						<div class="panel-body">
							<p>
								该医生：${passport.retMsg}<br>
							</p>

						</div>
					</c:if>
					<c:if test="${passport.errNum ==0}">
						<div class="panel-body">
							<p>
								姓名 : ${passport.doctorName}<br /> 性别: ${passport.sex} <br />
								生日: ${passport.birthday} <br /> 地址: ${passport.address} <br />
								身份证号: ${passport.idCard} <br />
							</p>
						</div>
					</c:if>

				</div>
				<div class="panel-body">

					<form:form commandName="doctor" role="form" method="post"
						class="validate"
						action="getDoctorVerify.do?phoneNo=${passport.phoneNo}">

						<div class="form-group">
							<button type="submit" class="btn btn-success">跳转到医生审核</button>
						</div>

					</form:form>

				</div>



			</div>
			<footer class="main-footer sticky footer-type-1">

				<div class="footer-inner">

					<div class="footer-text">
						&copy; 2015 <strong>医来伸手</strong> 版权所有 <a href="#"> 使用前必读</a>

					</div>

					<div class="go-up">
						<a href="#" rel="go-top"> <i class="fa-angle-up"></i>
						</a>
					</div>

				</div>

			</footer>
		</div>

	</div>


	</div>

	<script src="assets/js/bootstrap.min.js"></script>
	<!-- 侧边栏显示 -->
	<script src="assets/js/TweenMax.min.js"></script>
	<script src="assets/js/resizeable.js"></script>



	<script src="assets/js/xenon-custom.js"></script>

</body>
</html>
