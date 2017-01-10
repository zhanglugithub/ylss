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
<title>用户列表</title>

<!-- 小图标 -->
<link rel="stylesheet" href="assets/css/fonts/linecons/css/linecons.css">

<!-- 侧滑下拉 -->
<link rel="stylesheet"
	href="assets/css/fonts/fontawesome/css/font-awesome.min.css">
<!-- 关键布局 -->
<link rel="stylesheet" href="assets/css/bootstrap.css">
<!-- 关键布局 -->
<link rel="stylesheet" href="assets/css/xenon-core.css">

<style type="text/css">
#ys {
	font-size: 20px;
	font-family: "微软雅黑";
	/* 	background: #80ff80; */
	color: #ffff;
	/* 	text-align: center; */
	/* 	border: 0; */
	/* 	border-radius: 10px; */
	/* 	padding: 3%; */
	/* 	width: 100%; */
}
</style>

<script src="assets/js/jquery-1.11.1.min.js"></script>
<script type="text/javascript">
// window.onload=getUsers;
// 	function getUsers(){
// 		var reUsers = $.ajax({
// 			type:"get",
// 			//listUser.do?pageNo=1&pageSize=10
// 			url:"listUser.do?pageNo=1&pageSize=10",
// 			async:false//是否异步请求
// 		}).response;
// 		alert(reUsers);
// 	}
</script>
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
					<li><a href="index.jsp"> <i class="linecons-cog"></i><span
							class="title">返回首页</span></a></li>
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
					<h1 class="title">用户</h1>
					<p class="description">显示所有用户</p>
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
					<table>
						<thead>
							<tr id="ys">
								<th>&nbsp;&nbsp;昨日注册人数&nbsp;&nbsp;&nbsp;</th>
								<th>&nbsp;&nbsp;昨日注册医生数&nbsp;&nbsp;&nbsp;</th>
								<th>&nbsp;&nbsp;昨日注册用户数&nbsp;&nbsp;&nbsp;</th>
								<th>&nbsp;&nbsp;在线用户人数&nbsp;&nbsp;&nbsp;</th>
								<th>&nbsp;&nbsp;注册总人数&nbsp;&nbsp;&nbsp;</th>
								<th>&nbsp;&nbsp;注册医生数&nbsp;&nbsp;&nbsp;</th>
								<th>&nbsp;&nbsp;注册用户数&nbsp;&nbsp;&nbsp;</th>
							</tr>
						</thead>
						<tbody>
							<tr id="ys">
								<td align="center">${yesterday.yesterday==null?0:yesterday.yesterday}</td>
								<td align="center">${yesterday.yesDoctor==null?0:yesterday.yesDoctor}</td>
								<td align="center">${yesterday.yesPatient==null?0:yesterday.yesPatient}</td>
								<td align="center">${yesterday.online==null?0:yesterday.online}</td>
								<td align="center">${yesterday.userNo==null?0:yesterday.userNo}</td>
								<td align="center">${yesterday.doctorNo==null?0:yesterday.doctorNo}</td>
								<td align="center">${yesterday.patientNo==null?0:yesterday.patientNo}</td>
							</tr>
						</tbody>
					</table>
					<table class="table table-model-2 table-hover">
						<thead>
							<tr>
								<th>#</th>
								<th>头像</th>
								<th>姓名</th>
								<th>手机号</th>
								<th>归属地</th>
								<th>类型</th>
								<th>创建时间</th>
							</tr>
						</thead>

						<tbody>
							<c:forEach var="user" items="${listUser}">
								<c:if test="${user !=yesterday}">
									<tr>
										<td>${user.userId}</td>
										<c:if test="${user.haveIcon}">
											<td><img width="50" height="50" class="image"
												src="../img/headIcon/${user.phoneNo}.jpg"></td>
										</c:if>
										<c:if test="${!user.haveIcon}">
											<td>无</td>
										</c:if>
										<td>${user.userName}</td>
										<td>${user.phoneNo}</td>
										<td>${user.attribution}</td>
										<c:if test="${user.userType=='doctor'}">
											<td>医生</td>
										</c:if>
										<c:if test="${user.userType=='patient'}">
											<td>用户</td>
										</c:if>
										<c:if test="${user.userType=='service'}">
											<td>管理员</td>
										</c:if>
										<td>${user.createTime}</td>
									</tr>
								</c:if>
							</c:forEach>
						</tbody>
					</table>
					<div class="title-env">
						<c:forEach var="pageNo" begin="1" end="${totalNo/10+1}">
							<c:if test="${pageNo<41}">
								<a href="listUser.do?pageSize=10&pageNo=${pageNo}">
									${pageNo} </a>&nbsp;&nbsp;
						</c:if>
						</c:forEach>
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
