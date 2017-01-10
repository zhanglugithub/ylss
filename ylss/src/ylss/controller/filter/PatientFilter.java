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

public class PatientFilter extends OncePerRequestFilter {
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

		String requestURI = request.getRequestURI();
		String[] doNotFilter = new String[] { "getAroundDoctor.do",
				"getAroundDoctor2.do", "getDoctorStatus.do",
				"getAroundDoctor3.do", "getAroundDoctorBy.do", "getShuoAll.do",
				"getAroundPatient.do", "searchByIllness.do",
				"searchByDcotorType.do", "searchByHospital.do",
				"searchByKeShi.do", "detailDoctor.do", "getDoctorType.do",
				"getHospital.do", "getKeShi.do", "searchByNurse.do",
				"getMedia2.do", "getAroundDoctor31.do", "updateLocation.do" };
		boolean needFilter = true;

		for (String doNotFiterPart : doNotFilter) {
			if (requestURI.indexOf(doNotFiterPart) != -1) {
				needFilter = false;
				break;
			}
		}
		if (needFilter) {
			boolean patientIdOk = false;
			String urlPhoneNo = request.getParameter("phoneNo");
			String urlPatientIdStr = request.getParameter("patientId");
			int urlPatientId = -1;
			if (urlPatientIdStr == null) {
				patientIdOk = true;
			} else {
				urlPatientId = Integer.parseInt(urlPatientIdStr);
			}
			User aUser = userDao.getByPhoneNo(urlPhoneNo);
			int dbUserId = aUser.getUserId();

			if (dbUserId == urlPatientId) {
				patientIdOk = true;
			}

			if (patientIdOk) {
				filterChain.doFilter(request, response);
			} else {
				request.getRequestDispatcher(
						"/error/error.do?msg=patientId not equal userPhone")
						.forward(request, response);

			}
		} else {
			filterChain.doFilter(request, response);
		}
	}

}
