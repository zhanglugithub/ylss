package ylss.controller.filter;

import java.io.IOException;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.filter.OncePerRequestFilter;

import ylss.dao.UserDao;
import ylss.model.table.User;
import ylss.service.app.UserService;

/**
 * 此过滤器的作用是在用户没有登陆的时候
 * 
 * @author JACK
 *
 */
public class UserFilter extends OncePerRequestFilter {

	@Autowired
	@Resource
	UserService userServiceImpl;

	@Autowired
	@Resource
	UserDao userDao;

	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String[] doNotFilter = new String[] { "isUserExist.do", "login.do",
				"login2.do", "getValidateCode.do", "validateValidateCode.do",
				"error.do", "admin", "getAroundDoctor2.do",
				"getAroundDoctor.do", "notify.do", "updateImg.do",
				"regists.do", "getDoctor.do", "getDoctorStatus.do",
				"addOffer.do", "login3.do", "getAroundDoctor3.do",
				"getAroundDoctorBy.do", "getShuoAll.do", "getAroundPatient.do",
				"logout.do", "searchByIllness.do", "searchByDcotorType.do",
				"searchByHospital.do", "searchByKeShi.do", "detailDoctor.do",
				"getDoctorType.do", "getHospital.do", "getKeShi.do",
				"searchByNurse.do", "getMedia2.do", "reLogin.do",
				"getAroundDoctor31.do", "updateLocation.do" };

		String requestURI = request.getRequestURI();

		boolean needFilter = true;

		for (String doNotFiterPart : doNotFilter) {
			if (requestURI.indexOf(doNotFiterPart) != -1) {
				needFilter = false;
				break;
			}
		}
		boolean userIdOk = false;

		String urlPhoneNo = request.getParameter("phoneNo");
		String urlToken = request.getParameter("token");
		String urlUserIdStr = request.getParameter("userId");
		int urlUserId = -1;
		if (urlUserIdStr == null) {
			userIdOk = true;
		} else {
			urlUserId = Integer.parseInt(urlUserIdStr);
		}

		if (!needFilter) {
			filterChain.doFilter(request, response);

		} else {
			if (urlPhoneNo != null & urlToken != null) {
				User aUser = userDao.getByPhoneNo(urlPhoneNo);

				if (aUser != null) {
					String dbToken = aUser.getToken();
					Date dbTokenValidateDate = aUser.getTokenValidDate();

					Date nowDate = new Date();
					boolean tokenDateOk = nowDate.before(dbTokenValidateDate);
					if (urlToken.equals(dbToken)) {
						if (aUser.getUserId() == urlUserId) {
							userIdOk = true;
						}
						if (userIdOk) {
							filterChain.doFilter(request, response);
						} else {

							request.getRequestDispatcher(
									"/error/error.do?msg=userId not equal userPhone")
									.forward(request, response);
						}

					} else {

						request.getRequestDispatcher(
								"/error/error.do?msg=token error ").forward(
								request, response);
					}

				} else {

					request.getRequestDispatcher("/error/error.do?msg=用户错误")
							.forward(request, response);
				}
			} else {
				request.getRequestDispatcher(
						"/error/error.do?msg=no token&phoneNo").forward(
						request, response);
			}

		}
		// } else {
		// if (urlPhoneNo != null & urlToken != null) {
		// User aUser = userDao.getByPhoneNo(urlPhoneNo);
		//
		// if (aUser != null) {
		// String dbToken = aUser.getToken();
		// Date dbTokenValidateDate = aUser.getTokenValidDate();
		//
		// Date nowDate = new Date();
		// boolean tokenDateOk = nowDate.before(dbTokenValidateDate);
		// if (urlToken.equals(dbToken)) {
		// if (tokenDateOk) {
		// if (aUser.getUserId() == urlUserId) {
		// userIdOk = true;
		// }
		// if (userIdOk) {
		// filterChain.doFilter(request, response);
		// } else {
		//
		// request.getRequestDispatcher("/error/error.do?msg=userId not equal
		// userPhone")
		// .forward(request, response);
		// }
		// } else {
		//
		// request.getRequestDispatcher("/error/error.do?msg=token out of
		// date").forward(request,
		// response);
		// }
		//
		// } else {
		//
		// request.getRequestDispatcher("/error/error.do?msg=token error
		// ").forward(request, response);
		// }
		//
		// } else {
		//
		// request.getRequestDispatcher("/error/error.do?msg=用户错误").forward(request,
		// response);
		// }
		// } else {
		// request.getRequestDispatcher("/error/error.do?msg=no
		// token&phoneNo").forward(request, response);
		// }
		//
		// }

	}
} // 过滤结束
