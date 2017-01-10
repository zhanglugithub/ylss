<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/admin/";
%>
<!doctype html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
<title>在线报名</title>
<style>
body, input, button {
	font: normal 14px "Microsoft Yahei";
	margin: 0;
	padding: 0
}

.odform-tit {
	font-weight: normal;
	font-size: 25px;
	color: #999;
	line-height: 40px;
	text-align: center;
	border-bottom: 1px solid #c9cacb;
	margin: 0;
	padding: 5% 0
}

.odform-tit img {
	height: 40px;
	vertical-align: middle;
	margin-right: 15px
}

.odform {
	padding: 5%;
	text-align: left;
}

.input-group {
	margin-bottom: 5%;
	position: relative;
}

.input-group label {
	padding: 2% 0;
	position: absolute;
	color: #595757
}

.input-group input {
	margin-left: 5em;
	padding: 3% 5%;
	box-sizing: border-box;
	background: #efeff0;
	border: 0;
	border-radius: 5px;
	color: #595757;
	width: 75%
}

.odform button {
	background: #8ec31f;
	color: #fff;
	text-align: center;
	border: 0;
	border-radius: 10px;
	padding: 3%;
	width: 100%;
	font-size: 16px
}

.odform .cal {
	background-image: url(assets/images/daetixian-cal.png);
	background-repeat: no-repeat;
	background-position: 95% center;
	background-size: auto 50%
}

.odform .xl {
	background-image: url(assets/images/daetixian-xl.png);
	background-repeat: no-repeat;
	background-position: 95% center;
	background-size: auto 20%
}
</style>
<script type="text/javascript" src="assets/js/jquery-1.11.1.min.js"></script>
<script type="text/javascript">
	$(function() {
		$("#btn_post").click(function() {
			var name = $(".name").val();
			var phoneNo = $(".phoneNo").val();
			var hospital = $(".hospital").val();
			var requirement = $(".requirement").val();
			if (name == '') {
				alert("用户名不能为空");
				return;
			}
			if (phoneNo == '') {
				alert("手机号码不能为空");
				return;
			}
			if (hospital == '') {
				alert("医院不能为空");
				return;
			}
			if (requirement == '') {
				alert("科室不能拿空");
				return;
			}
			$.ajax({
				type : 'POST',
				url : 'addOffer.do',
				data : {
					'name' : name,
					'phoneNo' : phoneNo,
					'hospital' : hospital,
					'requirement' : requirement
				},
				dataType : 'json',
				success : function(data) {
					alert("fdasfdsafdsafsadfd");
				}
			});
		});
	});
</script>
</head>

<body text="#999">
	<h1 align="center" class="odform-tit">
		<span class="input-group"><span class="odform-tit"><span
				class="xl"><span class="odform-tit"><span class="cal">在线报名</span></span></span></span></span>
	</h1>
	<div class="odform">

		<div>
			<span style="font-size: 10pt;">&nbsp;<span class="input-group"
				style="font-size: 15px"><strong><img
						src="assets/images/1.png" alt="" width="25" height="25"
						align="absbottom" />报名须知</strong></span></span><strong><span
				style="font-size: 14px">：</span></strong>
		</div>
		<div>
			<p>1.报名信息必须真实有效;</p>
			<p>2.提交认证自愿使用医来伸手平台服务；</p>
			<p>3.请填写以下资料申请：</p>
		</div>
		<form id="myform">
			<p align="left">
				<span style="font-size: 10pt;">&nbsp;<span
					class="input-group" style="font-size: 15px"><strong><img
							src="assets/images/2.png" alt="" width="23" height="23"
							align="absbottom" />&nbsp;报名信息</strong></span></span><strong><span
					style="font-size: 14px">：</span></strong>
			</p>
			<div class="input-group">
				<label for="wdname">姓名</label> <input type="text" id="khname"
					class="name" name="name" placeholder="请输入您的用户姓名">
			</div>
			<div class="input-group">
				<label for="khname">手机号码</label> <input type="text" id="khname"
					class="phoneNo" name="phoneNo" placeholder="请输入您的手机号码">
			</div>
			<div class="input-group">
				<label for="khname">医院</label> <input type="text" id="khname"
					class="hospital" name="hospital" placeholder="请输入您的医院">
			</div>
			<div class="input-group">
				<label for="khname">科室</label> <input type="text" id="khname"
					class="requirement" name="requirement" placeholder="请输入所在科室">
			</div>
			<button id="btn_post">提交申请</button>
		</form>
	</div>
</body>
</html>