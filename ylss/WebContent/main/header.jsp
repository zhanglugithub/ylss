<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="base.jsp"%>

			<div class="main-content">
				<!-- User Info, Notifications and Menu Bar -->
		 		<nav role="navigation" class="navbar user-info-navbar"> 
				<!--<nav role="navigation">-->
					<ul class="user-info-menu middle-links list-inline list-unstyled"
						style="padding-left: 90px; color: black;">
						<li><h3 align="center">
								<strong> 欢迎访问医来伸手后台管理系统 </strong>
							</h3></li>
					</ul>
					<!-- Right links for user info navbar -->
					<ul class="user-info-menu right-links list-inline list-unstyled">
						<li class="dropdown user-profile">
							<a href="#" data-toggle="dropdown"> 
								<img src="../admin/assets/images/user-4.png" alt="user-image" class="img-circle img-inline userpic-32" width="28" />
								<span>管理员<i class="fa-angle-down"></i></span>
							</a>
						</li>
						<li>
							<ul class="dropdown-menu user-profile-menu list-unstyled">
								<li>
									<a href="#help">
										<i class="fa-info"></i> 帮助
									</a>
								</li>
								<li class="last">
									<a href="logout.do">
										<i class="fa-lock"></i>退出
									</a>
								</li>
							</ul>
						</li>
					</ul>
				</nav>
			</div>
		</div>
	</body>
</html>