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
<html lang="zh-cmn-Hans">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">

<meta name="viewport" content="width=device-width, initial-scale=1.0" />

<base href="<%=basePath%>">
<title>医生详情</title>

<style>
div {
	color: #000000;
	text-family: "微软雅黑";
	font-size: 20px;
}
</style>
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
					<li><a href="index.jsp"> <i class="linecons-user"></i> <span
							class="title">回到首页</span>
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
					<h1 class="title">医生详情</h1>
				</div>
			</div>

			<div>
				<c:if test="${msg!=null}">
					<div class="alert alert-success">
						<button type="button" class="close" data-dismiss="alert">
							<span aria-hidden="true">&times;</span> <span class="sr-only">Close</span>
						</button>

						${msg}
					</div>

				</c:if>

				<div class="panel panel-default">
					<!-- start content -->
					<div class="panel-body">

						<div class="form-group">姓名 :${doctorInfo.doctorName}</div>
						<div class="form-group">
							头像：
							<c:if test="${doctorInfo.haveIcon==false}">
							无
							</c:if>
							<br>
							<c:if test="${doctorInfo.haveIcon==true}">
								<img width="200" height="200" class="image"
									src="<%=path%>/img/headIcon/${doctorInfo.doctorPhone}.jpg">
							</c:if>
						</div>
						<c:if test="${doctorInfo.sex =='man'}">
							<span>性别:男</span>
							<br />
						</c:if>
						<c:if test="${doctorInfo.sex !='man'}">
							<span>性别:女</span>
							<br />
						</c:if>
						<span>生日:${doctorInfo.birthday}</span>
						<div class="form-group">
							资质证书编号:${doctorInfo.qualificationNo}</div>
						<div class="form-group">身份证号:${doctorInfo.idCard}</div>
						<div class="form-group">支付宝账号:${doctorInfo.alipayNo}</div>
						<div class="form-group">科室:${doctorInfo.department}</div>
						<div class="form-group">所属医院:${doctorInfo.hospital}</div>
						<div class="form-group">特长:${doctorInfo.specialty}</div>
						<div class="form-group">
							医生种类：
							<c:if test="${doctorInfo.doctorType=='doctor'}">
								医生
							</c:if>
							<c:if test="${doctorInfo.doctorType=='expert'}">
								护士
							</c:if>
						</div>
						<div class="form-group">医生编号：${doctorInfo.doctorNumber}</div>
						<div class="form-group">医生星级：${doctorInfo.starLevel}</div>
						<div class="form-group">临床经验：${doctorInfo.qualification}</div>
						<div class="form-group">服务次数：${doctorInfo.serviceCount}</div>
						<div class="form-group">
							服务状态：
							<c:if test="${doctorInfo.serviceStatus=='open'}">
							开启中
							</c:if>
							<c:if test="${doctorInfo.serviceStatus=='service'}">
							服务中
							</c:if>
						</div>
						证书：
						<div class="form-group">
							<img class="image"
								src="<%=path%>/img/allowWork/${doctorInfo.doctorPhone}.jpg">

							<img class="image"
								src="<%=path%>/img/idCard/${doctorInfo.doctorPhone}.jpg">
							<img class="image"
								src="<%=path%>/img/qulification/${doctorInfo.doctorPhone}.jpg">
						</div>
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
