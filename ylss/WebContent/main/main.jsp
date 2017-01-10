<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="zh-cmn-Hans">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>医来伸手后台管理系统</title>
    </head>
	<frameset cols="20%,80%" frameborder="0">
		<frame src="left.jsp" name="leftFrame" scrolling="no">
		</frame>
		<frameset name="rightFrame" rows="10%,*,10%" frameborder="0">
			<frame src="header.jsp" name="headerFrame" scrolling="no" noresize="noresize">
			</frame>
			<frame name="bodyFrame" scrolling="no" noresize="noresize" name="leftFrame">
			</frame>
			<frame src="bottom.jsp" name="bottomFrame" scrolling="no" noresize="noresize">
			</frame>
		</frameset>
			<noframes>
				<body>
					<a>您的浏览器不支持frame页面请升级浏览器</a>
				</body>
			</noframes>
	</frameset>
</html>