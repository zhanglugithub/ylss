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
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
<style type="text/css">
body, html, #allmap {
	width: 100%;
	height: 100%;
	overflow: hidden;
	margin: 0;
	font-family: "微软雅黑";
}

table {
	border: solid 1px #000;
	background: #F1F1F1;
}

/* * { */
/* 	font-size: 12px; */
/* 	font-family: Verdana, 宋体; */
/* } */
html, body {
	margin: 0px;
	padding: 0px;
	overflow: hidden;
}

.b {
	margin: 0px;
	padding: 0px;
	overflow: auto;
	z-index: -1;
}

.line0 {
	line-height: 20px;
	background-color: lightyellow;
	padding: 0px 15px;
}

.line1 {
	line-height: 18px;
	background-color: lightblue;
	padding: 0px 10px;
}

.w {
	position: absolute;
	left: 55px;
	top: 10px;
	/* 	right: 10px; */
	/* 	bottom: 10px; */
	width: auto;
	height: auto;
	overflow: hidden;
	border: 2px groove #281;
	cursor: default;
	-moz-user-select: none;
	z-index: 9999;
	-moz-user-select: none;
}

.t {
	line-height: 20px;
	height: auto;
	width: auto;
	overflow: hidden;
	background-color: #27C;
	color: white;
	font-weight: bold;
	border-bottom: 1px outset blue;
	text-align: center;
}

.winBody {
	height: auto;
	width: auto;
	overflow-x: hidden;
	overflow-y: auto;
	border-top: 1px inset blue;
	padding: 10px;
	text-indent: 10px;
	background-color: white;
}
</style>
<script type="text/javascript"
	src="http://api.map.baidu.com/api?v=2.0&ak=GR3jpe6pB6PfYc3GNKh74wM6"></script>
<title>浏览器定位</title>
</head>

<body>
	<div class="w">
		<div id="t" class="t">统计</div>
		<div id="wBody" class="winBody">
			<a id="doctor"></a>&nbsp;&nbsp; <a id="expert"></a>
		</div>
	</div>
	<div id="allmap"></div>
</body>
</html>

<script type="text/javascript" src="assets/js/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="assets/js/json2.js"></script>
<script type="text/javascript">

	var map = new BMap.Map("allmap"); //在container容器中创建一个地图,参数container为div的id属性;  
	var point = new BMap.Point(116.442915, 40.080116);
	var marker = new BMap.Marker(point); // 在当前地址处创建标注   
	map.addControl(new BMap.NavigationControl()); //向地图添加一个平移缩放控件  
	map.addControl(new BMap.MapTypeControl()); //地图类型控件，默认位于地图右上方  
	map.addControl(new BMap.ScaleControl()); //一个比例尺控件  
	map.addControl(new BMap.OverviewMapControl()); //一个缩略图控件  
	map.enableScrollWheelZoom(); //启用地图滚轮放大缩小  
	map.enableDoubleClickZoom(); //启用鼠标双击放大，默认启用(可不写)  
	map.enableDragging(); //启用地图拖拽事件，默认启用(可不写)  
	map.enableKeyboard(); //启用键盘上下左右键移动地图  
	map.centerAndZoom(point, 18); //将point移到浏览器中心，并且地图大小调整为15;

	//获取医生地理信息
	function getDoctorPositon() {
		var returnJSON = $.ajax({
			type : "post",
			url : "getDoctor.do?t=" + new Date().getTime(),
			async : false
		}).responseText;
		returnJSON = $.parseJSON(returnJSON);
		return returnJSON;
	}

	
	referneceHTML();//初始化添加标注

	function referneceHTML() {//将获取到的数据显示添加到百度地图上
		map.clearOverlays(); //移除之前添加的所有覆盖物
		var doctorObj = getDoctorPositon();
		var obj=doctorObj.doctorsList;
		for (var i = 0; i < obj.length; i++) {
			var point = new BMap.Point(obj[i].longitude, obj[i].latitude);
			var doctorId = obj[i].doctorId;
			var doctorType = obj[i].doctorType;
			addMarker(point, doctorId,doctorType);
		}
		doctorNum(doctorObj);
	}
	
	function doctorNum(doctorObj){
		var dbDoctor = doctorObj.doctor;
		var dbExpert= doctorObj.expert;
	   	var doctor = document.getElementById("doctor");
	   	var expert = document.getElementById("expert");
	   	doctor.innerHTML = "在线医生为："+dbDoctor+"位";
	   	expert.innerHTML = "在线护士为："+dbExpert+"位";
	}
	
	function doctorCount (w, b, c, d, o) {
		d = document;
		b = d.body;
		o = b.childNodes;
		c = "className";
		b.appendChild(w = d.createElement("div"))[c] = "b";
		for (var i = 0; i < o.length - 1; i++){
		    if (o[i][c] != "w")
			w.appendChild(o[i]), i--;
		}
		(window.onresize = function() {
		    w.style.width = d.documentElement.clientWidth + "px";
		    w.style.height = d.documentElement.clientHeight + "px";
		})();
	}
	
	// 编写自定义函数,创建标注
	function addMarker(point, doctorId,doctorType) {
		 var img = '';
		 if(doctorType=="expert"){
		 	img ='nurse.png';
		 }else{
		 	img = 'ys.png';
		 }

		var myIcon = new BMap.Icon(
// 				"http://www.cpylss.com/ys.png",
				"assets/images/"+img,
				new BMap.Size(300, 157));
		var marker = new BMap.Marker(point, {
			icon : myIcon
		}); // 创建标注
		map.addOverlay(marker); // 将标注添加到地图中
		addClickHandler(doctorId, marker);//添加点击事件
	}

	function addClickHandler(doctorId, marker) {
		var did = doctorId;
		marker.addEventListener("click", function(e) {
			openInfo(did, e);
		});
	}

	var opts = {
		width : 350, // 信息窗口宽度
		height : 180 // 信息窗口高度
	//设置允许信息窗发送短息
	};

	function openInfo(did, e) {
		var sContent = "";
		var doctorObj = getDoctorPositon();
		var obj=doctorObj.doctorsList;
		var imgStr = "";
		var haveIcon="";
		for (var i = 0; i < obj.length; i++) {

			var doctorType = '';
			var sex = '';
			if (obj[i].doctorId == did) {
				if (obj[i].doctorType == 'expert') {
					doctorType = '护士';
				} else {
					doctorType = '医生';
				}

				if (obj[i].sex == 'man') {
					sex = '男';
				} else {
					sex = '女';
				}

				if (obj[i].starLevel < 0.5 && obj[i].starLevel > 0) {
					imgStr = "assets/images/zero.png";
				}else if(obj[i].starLevel <1.5 && obj[i].starLevel > 1){
					imgStr = "assets/images/one.png";
				}else if(obj[i].starLevel <2.5 && obj[i].starLevel > 1.5){
					imgStr = "assets/images/two.png";
				}else if(obj[i].starLevel <3.5 && obj[i].starLevel > 2.5){
					imgStr = "assets/images/three.png";
				}else if(obj[i].starLevel <4.5 && obj[i].starLevel > 3.5){
					imgStr = "assets/images/four.png";
				}else{
					imgStr = "assets/images/fife.png";
				}
				if (!obj[i].haveIcon) {
				    isHaveIcon="assets/images/docotr.png";
				}
				if (obj[i].haveIcon) {
				    isHaveIcon="../img/headIcon/"+obj[i].doctorPhone+".jpg";
				}
				//弹出信息提示框
				sContent = "<table width='330' border='0'> "
						+ "<tr> "
						+ "<td width='102' rowspan='6'><img width='100px' height='140px' src='"+isHaveIcon+"'></td>"
						+ " <td width='212'>姓名："
						+ obj[i].doctorName
						+ "</td> "
						+ "</tr>"
						+ " <tr> "
						+ "<td>性别："
						+ sex
						+ "</td>"
						+ " </tr> "
						+ "<tr> "
						+ "<td>类型："
						+ doctorType
						+ "</td> "
						+ "</tr> "
						+ "<tr> "
						+ "<td>科室："
						+ obj[i].department
						+ "</td>"
						+ " </tr> "
						+ "<tr> "
						+ "<td>联系方式："
						+ obj[i].doctorPhone
						+ "</td> "
						+ "</tr>"
						+ " <tr>"
						+ " <td><img src='"+imgStr+"' height='36'/></td>"
						+ " </tr>" + " </table>";
			}
		}
		
		var p = e.target;
		var point = new BMap.Point(p.getPosition().lng, p.getPosition().lat);
		var infoWindow = new BMap.InfoWindow(sContent, opts); // 创建信息窗口对象 
		map.openInfoWindow(infoWindow, point); //开启信息窗口
	}
	window.setInterval('referneceHTML()',10000);
</script>