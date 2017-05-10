package aero.framework.mail;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@Component
public class MailDao implements InitializingBean{

	
	// 非默认邮箱登录
	private boolean other = false;

	private Properties config;
	private Transport transport;
	private MimeMessage message;
	private Session session;
	private Multipart multipart;

	// 账号信息
	private String account;
	private String password;

	public void setAccount(){
		account = config.getProperty("default.account");
		password = config.getProperty("default.password");
	}
	
	public void setAccount(String account, String password){
		this.account = account;
		this.password = password;
	}

	/**
	 * 使用Spring容器管理，在bean初始化完成之后执行
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		config = new Properties();
		try {
			config.load(this.getClass().getResourceAsStream("email.properties"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 方法描述： 校验邮箱格式 合法E-mail地址： 1. 必须包含一个并且只有一个符号“@” 2. 第一个字符不得是“@”或者“.” 3.
	 * 不允许出现“@.”或者.@ 4. 结尾不得是字符“@”或者“.” 5. 允许“@”前的字符中出现“＋” 6. 不允许“＋”在最前面，或者“＋@”
	 * 
	 * @author: Taoyuqiang
	 * @Date：2016-1-28
	 */
	private void checkMail(String mail) throws Exception {
		String regex = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
		Pattern pattern = Pattern.compile(regex);
		if (!pattern.matcher(mail).matches()) {
			throw new Exception("错误：" + mail + "，邮箱格式不正确！");
		}
	}

	private void openSession() throws Exception {
		Pattern pDoMain = Pattern
				.compile("@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+");
		Matcher matcher = pDoMain.matcher(account);
		if (!matcher.find()) {
			throw new Exception("错误：邮箱格式不正确！");
		}
		String mailHost = matcher.group();
		mailHost = mailHost.substring(1, mailHost.length() - 1);

		Properties props = new Properties();
		// 使用smtp：简单邮件传输协议
		props.put("mail.smtp.host", config.getProperty("smtp." + mailHost));
		props.put("mail.smtp.auth", "true");
		this.session = Session.getInstance(props);

	}

	MailDao loginEmail() throws Exception {
		checkMail(this.account);
		openSession();
		this.transport = session.getTransport("smtp");
		try {
			transport.connect(account, password);
		} catch (Exception e) {
			throw new Exception("错误：邮箱账号密码错误！");
		}
		return this;
	}

	MailDao setAddress(String address) throws AddressException,
			MessagingException {
		message = new MimeMessage(session);
		multipart = new MimeMultipart();
		message.setFrom(new InternetAddress(this.account));
		message.setRecipient(Message.RecipientType.TO, new InternetAddress(
				address));// 设置收件人,并设置其接收类型为TO
		return this;
	}

	MailDao setDate(Date date) throws MessagingException {
		message.setSentDate(date);
		return this;
	}

	MailDao setTitle(String title) throws Exception {
		if (StringUtils.isEmpty(title)) {
			throw new Exception("错误：邮件标题不能为空！");
		}
		message.setSubject(title);
		return this;
	}

	MailDao setContent(String content) throws MessagingException {
		if (StringUtils.isNotEmpty(content)) {
			BodyPart contentPart = new MimeBodyPart();
			contentPart.setContent(content, "text/html;charset=UTF-8");
			multipart.addBodyPart(contentPart);
		}
		return this;
	}

	MailDao setFile(File file) throws MessagingException,
			UnsupportedEncodingException, FileNotFoundException {
		if (file == null || !file.exists()) {
			throw new FileNotFoundException("错误：文件不存在！");
		}
		if (!file.isFile()) {
			throw new FileNotFoundException("错误：" + file.getName() + "，不是一个文件");
		}
		BodyPart attachmentBodyPart = new MimeBodyPart();
		DataSource source = new FileDataSource(file);
		attachmentBodyPart.setDataHandler(new DataHandler(source));
		// 网上流传的解决文件名乱码的方法，其实用MimeUtility.encodeWord就可以很方便的搞定
		// 这里很重要，通过下面的Base64编码的转换可以保证你的中文附件标题名在发送时不会变成乱码
		// sun.misc.BASE64Encoder enc = new sun.misc.BASE64Encoder();
		// messageBodyPart.setFileName("=?GBK?B?" +
		// enc.encode(attachment.getName().getBytes()) + "?=");
		// MimeUtility.encodeWord可以避免文件名乱码
		attachmentBodyPart.setFileName(MimeUtility.encodeWord(file.getName()));
		multipart.addBodyPart(attachmentBodyPart);
		return this;
	}

	void send() throws MessagingException {
		message.setContent(multipart);
		message.saveChanges();
		transport.sendMessage(message, message.getAllRecipients());// 发送邮件,其中第二个参数是所有已设好的收件人地址
	}

	void close() throws MessagingException {
		if (other) {// 非默认邮箱登录
			if (transport != null) {
				transport.close();
			}
		}
	}

}
