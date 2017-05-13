package aero.framework.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import aero.framework.domain.ContextHolder;
import aero.framework.domain.SInfomation;
import aero.framework.domain.SpecialUrcAccessDefinition;

/**
 * 登陆过滤器
 * @author Aero
 */
public class LoginFilter implements Filter {
	
	SpecialUrcAccessDefinition specialUrcAccessDefinition;

	@Override
	public void init(FilterConfig fConfig) throws ServletException {
		specialUrcAccessDefinition = (SpecialUrcAccessDefinition)ContextHolder.getSpringContext().getBean("specialUrcAccessDefinition");
	}
	
	

	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		Object loginUser = req.getSession().getAttribute(SInfomation.USER_SESSION_KEY);
		if (loginUser != null && ContextHolder.getSession()!=null ) {
			chain.doFilter(req, res);
			return;
		}
		String path = getUrl(req);
		if (specialUrcAccessDefinition.isSpecialFile(path) || specialUrcAccessDefinition.isSpecialUrl(path)) {
			chain.doFilter(req, res);
		} else {
			res.sendRedirect(req.getContextPath()+ "/login.html");
		}
	}

	private String getUrl(HttpServletRequest request) {
		String contextPath = request.getContextPath();
		String uri = request.getRequestURI();
		int pathParamIndex = uri.indexOf(';');
		if (pathParamIndex > 0) {
			// strip everything from the first semi-colon
			uri = uri.substring(0, pathParamIndex);
		}
		int queryParamIndex = uri.indexOf('?');
		if (queryParamIndex > 0) {
			// strip everything from the first question mark
			uri = uri.substring(0, queryParamIndex);
		}
		if (contextPath.length() > 1) {
			uri = uri.substring(contextPath.length()+1, uri.length());
		}
		return uri;
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

}
