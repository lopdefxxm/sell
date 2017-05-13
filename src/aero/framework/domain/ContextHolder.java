package aero.framework.domain;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

@Component
public class ContextHolder implements ApplicationContextAware {
	
	private static HttpSession session;
	
	private static String loginUserName;
	
	private static ApplicationContext springContext;
	
	public static String getLoginUserName() {
		return loginUserName;
	}

	public static void setLoginUserName(String loginUserName) {
		ContextHolder.loginUserName = loginUserName;
	}

	
	
	public static ApplicationContext getSpringContext() {
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

	@Override
	public void setApplicationContext(ApplicationContext arg0)
			throws BeansException {
		springContext = arg0;
	}
}
