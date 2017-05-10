package aero.framework.server;

import java.io.File;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import aero.framework.mail.MailUtil;
import aero.framework.utils.ExportExcelService;

import com.bstek.dorado.annotation.Expose;

@Component
public class MailServer {

	@Resource
	MailUtil mailUtil;
	
	@Resource
	ExportExcelService excelService;
	
	@Expose
	public void doExcelAndMail(Map<String, Object> parameter) throws Exception {
		File excel = excelService.createExcelFile(parameter);
		String address = (String)parameter.get("address");
		mailUtil.getMailTemplate().sendMail(address, excel);;
	}
	
}
