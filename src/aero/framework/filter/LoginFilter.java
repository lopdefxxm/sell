package aero.framework.filter;

import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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

/**
 * 是否登陆的过滤器
 * @author Aero
 */
public class LoginFilter implements Filter {
	
	private Set<String> specialURL;

	private String[] specialFile;


	@Override
	public void init(FilterConfig fConfig) throws ServletException {
		this.specialURL = new HashSet<>();
		this.specialURL.add("/aero.framework.view.login.d");
		this.specialURL.add("/login.html");
		this.specialFile = new String[]{"css","dpkg","js","gif","jpg"};
	}

	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		Object loginUser = req.getSession().getAttribute(SInfomation.USER_SESSION_KEY);
		if (loginUser != null && ContextHolder.getSession()!=null ) {
			chain.doFilter(req, res);
			return;
		}
		String loginURL = req.getContextPath()+ "/login.html";
		Map<String, String[]> map = req.getParameterMap();
		String path = getUrl(req);
		if(path.endsWith("login.d") && map.isEmpty()){
			res.sendRedirect(loginURL);
			return;
		}
		if (isSpecialURLS(path)) {
			chain.doFilter(req, res);
		} else {
			res.sendRedirect(loginURL);
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
			uri = uri.substring(contextPath.length(), uri.length());
		}
		return uri;
	}

	public boolean isSpecialURLS(String path) {
		if (specialURL.contains(path)) {
			return true;
		}
		if(path.startsWith("/dorado/view-service")){
			return true;
		}
		for(String suffix:this.specialFile){
			if(path.endsWith("."+suffix)){
				return true;
			}
		}
		return false;
	}

	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

}
