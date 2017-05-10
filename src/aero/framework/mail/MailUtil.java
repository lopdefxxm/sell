package aero.framework.mail;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

@Component
public class MailUtil {
	
	@Resource
	MailTemplate mailTemplate;
	

	/** 方法描述： 获得默认的邮件对象
	 * @author: Taoyuqiang
	 * @Date：2016-1-29
	 */
	public MailTemplate getMailTemplate() {
		mailTemplate.setAccount();
		return mailTemplate;
	}
//	
//	public MailTemplate createTemplate() {
//		
//	}
//	
	/**
	 * 方法描述： 根据指定账号密码获得邮件对象
	 * @author: Taoyuqiang
	 * @Date：2016-1-29
	 */
	public MailTemplate getMailTemplate(String account,String password) {
		mailTemplate.setAccount(account, password);
		return mailTemplate;
	}
	
}
