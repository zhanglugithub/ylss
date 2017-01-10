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
<title>审核医生</title>

</head>
<body class="page-body">



	<div class="page-container">

		<div class="sidebar-menu toggle-others fixed">

			<div class="sidebar-menu-inner">

				<header class="logo-env">

					<!-- logo -->
					<div class="logo"></div>

				</header>

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

						<form:form commandName="admin" role="form" method="get"
							class="validate" action="getCode.do">


							<div class="form-group">
								<label class="control-label">号码：</label>
								<%-- 								<form:input type="number" path="phoneNo" class="form-control" --%>
								<%-- 									data-validate="number" /> --%>
								<input type="number" name="phoneNo" class="form-control" value="">
							</div>

							<div class="form-group">
								<button type="submit" class="btn btn-success">查询</button>

							</div>

						</form:form>
					</div>

				</div>
				<footer class="main-footer sticky footer-type-1">

					<div class="footer-inner">


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
</html>
