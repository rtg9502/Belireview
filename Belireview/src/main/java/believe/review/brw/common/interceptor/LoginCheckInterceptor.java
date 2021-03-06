package believe.review.brw.common.interceptor;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class LoginCheckInterceptor extends HandlerInterceptorAdapter{

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		HttpSession session = request.getSession();
		String id = (String) session.getAttribute("ID");
		int admin;
		String uri = request.getRequestURI();
		
		if(id == null || id.trim().length() <= 0 || id == "") {
			if(uri.contains("member")) {
				return true;
			}
			response.sendRedirect("/brw/member/loginForm.br");
			return false;
		}else {
			admin = ((BigDecimal)session.getAttribute("ADMIN")).intValue();
			if(uri.contains("admin")||(uri.contains("member")&&!uri.contains("logout"))) {
				if(admin == 0) {
					response.sendRedirect("/brw/main.br");
					
					return false;
				}
			}
			return true;
		}
	}
}
