package aero.framework.domain;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.springframework.web.context.support.WebApplicationContextUtils;


public class SpringInit implements ServletContextListener {
     
     
    @Override
	public void contextInitialized(ServletContextEvent event) {
    	ContextHolder.setSpringContext( WebApplicationContextUtils.getWebApplicationContext(event.getServletContext()));
    	event.getServletContext().addListener(new HttpSessionListener() {
			
			@Override
			public void sessionDestroyed(HttpSessionEvent event) {
				// TODO Auto-generated method stub
				ContextHolder.setSession(null);
			}
			
			@Override
			public void sessionCreated(HttpSessionEvent event) {
				HttpSession session = event.getSession();
				session.setMaxInactiveInterval(0);
				ContextHolder.setSession(session);
			}
		});
    }
     
    @Override
	public void contextDestroyed(ServletContextEvent event) {
    	// TODO Auto-generated method stub
    }
     
     
}