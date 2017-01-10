package ylss.controller.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;

import ylss.model.constant.databaseConstant.UserType;

public class AdminFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String[] doNotFilter = new String[] { "login.do", "addOffer.do",
				"getCode.do" };

		String requestURI = request.getRequestURI();

		boolean needFilter = true;

		for (String doNotFiterPart : doNotFilter) {
			if (requestURI.indexOf(doNotFiterPart) != -1) {
				needFilter = false;
				break;
			}
		}

		String userType = (String) request.getSession()
				.getAttribute("userType");
		if (userType == null) {
			userType = "null";
		}

		if (needFilter) {
			if (userType.equals(UserType.service)) {
				filterChain.doFilter(request, response);

			} else {
				response.sendRedirect("login.jsp");
			}

		} else {
			filterChain.doFilter(request, response);
		}
	}

}
