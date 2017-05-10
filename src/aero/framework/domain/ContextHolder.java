package aero.framework.domain;

import javax.servlet.http.HttpSession;

import org.springframework.web.context.WebApplicationContext;

public class ContextHolder{
	
	private static HttpSession session;
	
	private static String loginUserName;
	
	public static String getLoginUserName() {
		return loginUserName;
	}

	public static void setLoginUserName(String loginUserName) {
		ContextHolder.loginUserName = loginUserName;
	}

	private static WebApplicationContext springContext;
	
	
	public static WebApplicationContext getSpringContext() {
		return springContext;
	}

	public static void setSpringContext(WebApplicationContext springContext) {
		ContextHolder.springContext = springContext;
	}

	public static HttpSession getSession(){
		return session;
	}
	
	public static void setSession(HttpSession s){
		session = s;
	}
}
