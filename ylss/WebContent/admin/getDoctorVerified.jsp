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
<title>重审医生</title>

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
					<h1 class="title">重审医生</h1>
					<p class="description">重审</p>
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
						<form:form commandName="doctor" method="post" class="validate"
							action="revalidateDoctorVerified.do?doctorId=${doctorInfo.getDoctorId()}">

								姓名 :
							<div class="form-group">
								<input type="text" name="doctorName" class="form-control"
									value="${doctorInfo.getUser().getUserName()}">
							</div>
							
							<div class="form-group">
							性别:<br>
								<c:if test="${doctorInfo.getSex() =='man'}">
									<input type="radio" name="sex" value="man" checked="checked">男
									<input type="radio" name="sex" value=woman>女
									 <br />
								</c:if>
								<c:if test="${doctorInfo.getSex() =='woman'}">
									<input type="radio" name="sex" value="man">男 
									<input type="radio" name="sex" value="woman" checked="checked">女
									 <br />
								</c:if>
							</div>
								生日:
							<div class="form-group">
								<input type="date" name="birthday" class="form-control"
									required="required" value="${doctorInfo.getBirthday()}" />
							</div>
								资质证书编号:
							<div class="form-group">
								<form:input type="number" path="qualificationNo"
									class="form-control" data-validate="number"
									value="${doctorInfo.getQualificationNo()}" />
							</div>
							
							身份证号:
							<div class="form-group">
								<form:input path="idCard" class="form-control" type="text"
									data-validate="text" value="${doctorInfo.getIdCard()}" />
							</div>
							支付宝账号:
							<div class="form-group">
								<form:input path="alipayNo" class="form-control" type="text"
									data-validate="text" value="${doctorInfo.getAlipayNo()}" />
							</div>
							科室:
							<div class="form-group">
								<form:input path="department" class="form-control" type="text"
									data-validate="text" value="${doctorInfo.getDepartment()}" />
							</div>
							所属医院:
							<div class="form-group">
								<form:input path="hospital" class="form-control" type="text"
									data-validate="text" value="${doctorInfo.getHospital()}" />
							</div>
							特长:
							<div class="form-group">
								<form:input path="specialty" class="form-control" type="text"
									data-validate="text" value="${doctorInfo.getSpecialty()}" />
							</div>
							<div class="form-group">
								医生种类：<br>
							<c:if test="${doctorInfo.getDoctorType()=='doctor'}">
								<input type="radio" name="doctorType" value="doctor" checked="checked">医生
							<input type="radio" name="doctorType" value="expert">护士<br>
							</c:if>
							<c:if test="${doctorInfo.getDoctorType()=='expert'}">
							<input type="radio" name="doctorType" value="doctor">医生
							<input type="radio" name="doctorType" value="expert" checked="checked">护士<br>
							</c:if>
							</div>
								医生编号：
							<div class="form-group">
								<form:input path="doctorNumber" class="form-control" type="text" disabled="true"
									data-validate="text" value="${doctorInfo.getDoctorNumber()}" />
							</div>
								医生星级：
							<div class="form-group">
								<form:input path="starLevel" class="form-control" type="text"
									data-validate="text" value="${doctorInfo.getStarLevel()}" />
							</div>
								临床经验：
							<div class="form-group">
								<form:input path="qualification" class="form-control"
									type="text" data-validate="text"
									value="${doctorInfo.getQualification()}" />
							</div>
							<div class="form-group">
								<button type="submit" class="btn btn-success">确认修改医生认证</button>
							</div>
						</form:form>
						<div class="form-group">
						<img class="image"
							src="../img/allowWork/${doctorInfo.getUser().getPhoneNo()}.jpg">

						<img class="image"
							src="../img/idCard/${doctorInfo.getUser().getPhoneNo()}.jpg">
						<img class="image"
							src="../img/qulification/${doctorInfo.getUser().getPhoneNo()}.jpg">
						<br>
							<a href="toUpLoad.do?doctorId=${doctorInfo.getDoctorId()}"
								class="btn btn-success"> 修改证书 </a>
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
