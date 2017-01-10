package ylss.controller.filter;

import java.io.IOException;

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

public class DoctorFilter extends OncePerRequestFilter {
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

		boolean doctorIdOk = false;
		String urlPhoneNo = request.getParameter("phoneNo");
		String urlDoctorIdStr = request.getParameter("doctorId");
		int urlDoctorId = -1;
		if (urlDoctorIdStr == null) {
			doctorIdOk = true;
		} else {
			urlDoctorId = Integer.parseInt(urlDoctorIdStr);
		}	
		User aUser = userDao.getByPhoneNo(urlPhoneNo);
		int dbUserId = aUser.getUserId();

		if (dbUserId == urlDoctorId) {
			doctorIdOk = true;
		} 

		if (doctorIdOk) {
			filterChain.doFilter(request, response);
		} else {
		
			request.getRequestDispatcher("/error/error.do?msg=DoctorId not equal userPhone").forward(request, response);
		}

	}
}
