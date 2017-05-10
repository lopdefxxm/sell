package aero.framework.mail;

import java.io.File;
import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;


/**
 * 0.根据邮件名进行smtp服务解析，得到Session<br/>
 * 1.传入参数 account,password 得到Transport对象<br/>
 * 2.开始写信<br/>
 * 3.设置收件人<br/>
 * 4.设置邮件标题<br/>
 * 5.邮件内容<br/>
 * 6.上传附件<br/>
 * 7.发送邮件<br/>
 * 8.是否操作成功<br/>
 */
@Component
public class MailTemplate{
	@Resource
	MailDao mailDao;

	public void setAccount(String account, String password){
		mailDao.setAccount(account, password);
	}
	
	public void setAccount(){
		mailDao.setAccount();
	}
	
	public void sendMail(String address,String title,Date date,String content,File...files) throws Exception{
		if(StringUtils.isEmpty(content) && (files==null || files.length==0)){
			throw new Exception("错误：邮件内容和附件不能同时为空！");
		}
		mailDao.loginEmail();
		mailDao.setAddress(address);
		mailDao.setTitle(title);
		mailDao.setDate(date);
		mailDao.setContent(content);
		if(files!=null && files.length>0){
			for(File file:files){
				mailDao.setFile(file);
			}
		}
		mailDao.send();
		mailDao.close();
	}
	
	
	public void sendMail(String address,String title,String content,File...files)throws Exception{
		if(StringUtils.isEmpty(content) && ( files==null || files.length==0)){
			throw new Exception("错误：邮件内容和附件不能同时为空！");
		}
		sendMail(address, title, new Date(), content, files);
	}
	
	public void sendMail(String address,String title,String content)throws Exception{
		if(StringUtils.isEmpty(content)){
			throw new Exception("错误：邮件内容和附件不能同时为空！");
		}
		sendMail(address, title, new Date(), content);
	}
	
	public void sendMail(String address,String title,File...files)throws Exception{
		if(files==null || files.length==0){
			throw new Exception("错误：邮件内容和附件不能同时为空！");
		}
		sendMail(address, title, new Date(), null,files);
	}
	
	public void sendMail(String address,File...files)throws Exception{
		if(files==null || files.length==0){
			throw new Exception("错误：邮件内容和附件不能同时为空！");
		}
		String title = files[0].getName()+"相关";
		sendMail(address, title, new Date(), null,files);
	}
	
	public void sendMail(String address,String title,String content,String path)throws Exception{
		File file = new File(path);
		sendMail(address, title, new Date(), content, file);
	}

}
