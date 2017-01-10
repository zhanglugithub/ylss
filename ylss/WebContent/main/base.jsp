<%@page import="java.io.File"%>
<%@page import="java.lang.ProcessBuilder.Redirect"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/admin/";
%>
<html lang="zh-cmn-Hans">
    <head>
    	<base href="<%=basePath%>"/>
    	<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
		<title>欢迎访问医来伸手后台管理系统</title>
		<!-- 关键布局 -->
        <link rel="stylesheet" href="../admin/assets/css/bootstrap.css" />
        <link rel="stylesheet" href="../admin/assets/css/xenon-core.css" />
        <!--<link rel="stylesheet" href="../admin/assets/css/fonts/fontawesome/css/font-awesome.css" />-->
        <!-- 侧滑下拉 -->
        <link rel="stylesheet" href="../admin/assets/css/fonts/fontawesome/css/font-awesome.min.css" />
        <!--<link rel="stylesheet" href="../admin/assets/css/fonts/linecons/css/animation.css" />-->
        <!--<link rel="stylesheet" href="../admin/assets/css/fonts/linecons/css/linecons-codes.css" />-->
        <!--<link rel="stylesheet" href="../admin/assets/css/fonts/linecons/css/linecons-embedded.css" />-->
        <!--<link rel="stylesheet" href="../admin/assets/css/fonts/linecons/css/linecons-ie7-codes.css" />-->
        <!--<link rel="stylesheet" href="../admin/assets/css/fonts/linecons/css/linecons-ie7.css" />-->
        <!-- 小图标 -->
        <link rel="stylesheet" href="../admin/assets/css/fonts/linecons/css/linecons.css" />
        <!--<link rel="stylesheet" href="../admin/assets/css/xenon-components.css" />-->
        <!-- 关键布局 -->
        <!--<link rel="stylesheet" href="../admin/css/fort.css" />
        <link rel="stylesheet" href="../admin/css/style.css" />-->
        <!--<script type="text/javascript" src="../admin/assets/js/jquery-1.11.1.min.js" ></script>-->
        <!--<script type="text/javascript" src="../admin/assets/js/json2.js" ></script>-->
        <script type="text/javascript" src="../admin/assets/js/bootstrap.min.js" ></script> 
        <!-- 侧边栏显示 -->
        <script type="text/javascript" src="../admin/assets/js/TweenMax.min.js" ></script>
        <script type="text/javascript" src="../admin/assets/js/resizeable.js" ></script>
        <script type="text/javascript" src="../admin/assets/js/xenon-custom.js" ></script>
        <!--<script type="text/javascript" src="../admin/jQueryAssets/jquery-1.8.3.min.js" ></script>--> 
        <!--<script type="text/javascript" src="../admin/jQueryAssets/jquery-ui-effects.custom.min.js" ></script>-->
        <!--<script type="text/javascript" src="../admin/js/fort.js" ></script>-->
        <script type="text/javascript" src="../admin/js/jquery-1.12.0.js" ></script>
        <!--<script type="text/javascript" src="../admin/js/jquery-2.2.0.js" ></script>-->
    </head>
	<body class="page-body">
		<div class="page-container">