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
<title>添加或修改科室</title>

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


<script src="<%=path%>/admin/assets/js/jquery-1.11.1.min.js"></script>




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
					<h1 class="title">添加或修改科室</h1>
					<p class="description">科室</p>
				</div>
			</div>

			<div>
				<c:if test="${deKeShi.code==0}">
					<div class="alert alert-success">
						<button type="button" class="close" data-dismiss="alert">
							<span aria-hidden="true">&times;</span> <span class="sr-only">Close</span>
						</button>

						${deKeShi.msg}
					</div>

				</c:if>

				<div class="panel panel-default">
					<!-- start content -->
					<div class="panel-body">
						<c:if test="${deKeShi.code==1}">
							<c:if test="${deKeShi.msg!=null}">
								<p style="font-size: 30px; color: black;">${deKeShi.msg}</p>
							</c:if>
							<c:if test="${deKeShi.msg==null}">
								<form class="validate"
									action="detailKeShi.do?keShiId=${deKeShi.keShi.getKeShiId()}&createTime=${deKeShi.keShi.getCreateTime()}"
									method="post">
									<p>
										科室ID：<a class="form-control">${deKeShi.keShi.getKeShiId()}</a>
									</p>
									<input type="hidden" name="uid"
										value="${deKeShi.keShi.getUid()}">
									<p>
										科室名称： <input name="keShi" value="${deKeShi.keShi.getKeShi()}"
											type="text" class="form-control" required="required">
									</p>
									<p>
										是否删除：
										<c:if test="${deKeShi.keShi.getIsDel()==1}">
											<input type="radio" value="1" name="isDel" checked="checked">未删除
											<input type="radio" value="0" name="isDel">删除
										</c:if>
										<c:if test="${deKeShi.keShi.getIsDel()==0}">
											<input type="radio" value="1" name="isDel"> 未删除
											<input type="radio" value="0" name="isDel" checked="checked">删除
										</c:if>
									</p>
									<button type="submit" class="btn btn-success">确认修改</button>
								</form>
							</c:if>
						</c:if>
						<c:if test="${deKeShi.code==null}">
							<form action="setKeShi.do" method="post">
								<p>
									科室名称： <input name="keShi" type="text">
								</p>
								<p>
									<button type="submit" class="btn btn-success">添加科室</button>
								</p>
							</form>
						</c:if>
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

	<script src="<%=path%>/admin/assets/js/bootstrap.min.js"></script>
	<!-- 侧边栏显示 -->
	<script src="<%=path%>/admin/assets/js/TweenMax.min.js"></script>
	<script src="<%=path%>/admin/assets/js/resizeable.js"></script>



	<script src="<%=path%>/admin/assets/js/xenon-custom.js"></script>

</body>
</html>
