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
<link rel="stylesheet"
	href="<%=path%>/admin/assets/css/fonts/linecons/css/linecons.css">

<!-- 侧滑下拉 -->
<link rel="stylesheet"
	href="<%=path%>/admin/assets/css/fonts/fontawesome/css/font-awesome.min.css">
<!-- 关键布局 -->
<link rel="stylesheet" href="<%=path%>/admin/assets/css/bootstrap.css">
<!-- 关键布局 -->
<link rel="stylesheet" href="<%=path%>/admin/assets/css/xenon-core.css">


<script src="<%=path%>/admin/assets/js/jquery-1.11.1.min.js"
	type="text/javascript"></script>


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
					<h1 class="title">类别</h1>
					<p class="description">所有类别</p>
				</div>



			</div>

			<div>
				<c:if test="${categorys.code==0}">
					<div class="alert alert-success">
						<button type="button" class="close" data-dismiss="alert">
							<span aria-hidden="true">&times;</span> <span class="sr-only">Close</span>
						</button>

						${categorys.msg}
					</div>

				</c:if>

				<c:if test="${categorys.code==1}">
					<div class="panel panel-default">
						<!-- start content -->
						<div align="right">
							<a href="setCategory.do" class="btn btn-success"
								style="font-size: 20px;">为医生添加分类信息</a>
						</div>
						<table class="table table-model-2 table-hover">
							<thead>
								<tr>
									<th>#</th>
									<th>医生</th>
									<th>所在类别</th>
									<th>操作</th>
								</tr>
							</thead>

							<tbody>
								<c:forEach var="category" items="${categorys.catList}">
									<tr>
										<td><a>${category.catId}</a></td>
										<td><a>${category.doctorName}</a></td>
										<td><c:if test="${category.category=='doctorType'}">
												<p>
													医生类别： <a>${category.doctorType}</a>
												</p>
											</c:if> <c:if test="${category.category=='keShi'}">
												<p>
													科室为： <a>${category.keShi}</a>
												</p>
											</c:if> <c:if test="${category.category=='hospital'}">
												<p>
													所在医院： <a>${category.hospital}</a>
												</p>
											</c:if> <c:if test="${category.category=='illness'}">
												<p>
													主治疾病： <a>${category.illness}</a>
												</p>
											</c:if></td>
										<td><a href="detailCategory.do?catId=${category.catId}"
											style="color: blue;">修改</a></td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
						<div class="title-env">
							<c:forEach var="pageNo" begin="1" end="${categorys.pageCount}">
								<a href="getCategory.do?pageSize=10&pageNo=${pageNo}">
									${pageNo} </a>&nbsp;&nbsp;
							</c:forEach>
						</div>
					</div>
				</c:if>
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

</body>

<script src="<%=path%>/admin/assets/js/bootstrap.min.js"></script>
<!-- 侧边栏显示 -->
<script src="<%=path%>/admin/assets/js/TweenMax.min.js"></script>
<script src="<%=path%>/admin/assets/js/resizeable.js"></script>

<script src="<%=path%>/admin/assets/js/xenon-custom.js"></script>

</html>
