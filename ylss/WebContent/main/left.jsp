<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="base.jsp"%>
			<div class="sidebar-menu toggle-others fixed">
				<div class="sidebar-menu">
					<header class="logo-env">
						<!-- logo -->
						<div class="logo"></div>
					</header>
					<ul id="main-menu" class="main-menu">
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
						<li><a href="#"><i class="linecons-cog"></i><span
								class="title">用户管理</span></a>
							<ul>
								<li><a href="lockUser.jsp"> <i class="linecons-user"></i>
										<span class="title">用户管理</span>
								</a></li>
								<li><a href="listUser.do?pageNo=1&pageSize=10"><i
										class="linecons-user"></i><span class="title">所有用户</span></a></li>
							</ul></li>
						<li><a href="#"><i class="linecons-cog"></i><span
								class="title">医生审核</span></a>
							<ul>
								<li><a href="listDoctorVerify.do?pageNo=1&pageSize=10">
										<i class="linecons-star"></i><span class="title">医生审核</span>
								</a></li>
								<li><a href="listDoctorVerified.do?pageNo=1&pageSize=10">
										<i class="linecons-star"></i><span class="title">医生重审</span>
								</a></li>
								<li><a href="listDoctor.do?pageNo=1&pageSize=10"><i
										class="linecons-heart"></i><span class="title">实名认证</span></a></li>
							</ul></li>
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
		
						<li><a href=""> <i class="linecons-mail"></i> <span
								class="title">搜索模块管理</span>
						</a>
							<ul>
								<li><a href="getHospital.do?pageNo=1&pageSize=10"> <span
										class="title">查看医院</span>
								</a></li>
								<li><a href="getDoctorType.do?pageNo=1&pageSize=10"> <span
										class="title">查看医生分类</span>
								</a></li>
								<li><a href="getKeShi.do?pageNo=1&pageSize=10"> <span
										class="title">查看科室</span>
								</a></li>
								<li><a href="getCategory.do?pageNo=1&pageSize=10"> <span
										class="title">医生分类管理</span>
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
										class="title">查看应聘</span></a></li>
								<li><a href="addOffer.jsp"> <span class="title">添加招聘</span></a>
								</li>
							</ul></li>
						<li><a onclick="window.open('getDoctorPosition.jsp')"
							style="cursor: pointer;"> <i class="linecons-user"></i> <span
								class="title">医生地图</span>
						</a></li>
						<li><a href="listMedia.do?pageNo=1&pageSize=10"> <i
								class="linecons-user"></i> <span class="title">健康资讯</span></a></li>
						<li><a href="countDoctor.do?pageNo=1&pageSize=10"> <i
								class="linecons-user"></i> <span class="title">医生统计</span></a></li>
					</ul>
				</div>
			<!--</div>-->
		</div>
	</body>
</html>