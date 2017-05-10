package aero.framework.domain;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.AbstractHandlerExceptionResolver;

public class DefaultHanlerExceptionResolver extends
		AbstractHandlerExceptionResolver implements ApplicationContextAware {
	
	private String defaultErrorView;

	@Override
	protected ModelAndView doResolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {
		String key = "errorForward___";
		if (request.getAttribute(key) == null) {
			// 判断当前错误有没有进行过forward，如果有就不再进行任何操作，以免产生死循环
			request.setAttribute(key, "1");
			response.setStatus(500);
			ex.printStackTrace();
			request.setAttribute("exception_", ex);
			return new ModelAndView(defaultErrorView);
		} else {
			return null;
		}
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
	}

	public String getDefaultErrorView() {
		return defaultErrorView;
	}

	public void setDefaultErrorView(String defaultErrorView) {
		this.defaultErrorView = defaultErrorView;
	}

}