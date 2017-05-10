package aero.framework.manage;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import aero.framework.domain.ContextHolder;
import aero.framework.domain.SInfomation;
import aero.framework.utils.EncodeUtil;
import aero.framework.utils.JdbcUtil;

import com.bstek.dorado.annotation.Expose;
import com.bstek.dorado.web.DoradoContext;


@Component
public class LoginManage {

	@Resource
	JdbcUtil jdbcUtil;
	
	@Expose
	public void login(Map<String,Object> parameter) throws Exception{
		
		String USER = (String)parameter.get("USER");
		String PASSWORD = (String)parameter.get("PASSWORD");
		if(StringUtils.isEmpty(USER)){
			throw new Exception("请输入用户名！");
		}
		if(StringUtils.isEmpty(PASSWORD)){
			throw new Exception("请输入密码！");
		}
		//密码进行base64编码
		PASSWORD = EncodeUtil.encodeBase64(PASSWORD);
		String checkSql = "SELECT A.* FROM SYS_USER A WHERE 1=1 "
				+ " AND A.USER_ = ? AND PASSWORD_ = ? ";
		List<Map<String,Object>> list = jdbcUtil.getJdbcTemplate().queryForList(checkSql,new Object[]{USER,PASSWORD});
		if(list==null || list.isEmpty()){
			throw new Exception("账号密码错误！");
		}
		HttpSession session =  DoradoContext.getAttachedRequest().getSession();
		session.setAttribute(SInfomation.USER_SESSION_KEY, list.get(0));
		ContextHolder.setSession(session);
		ContextHolder.setLoginUserName((String)list.get(0).get("NAME_"));
	}
	
}
