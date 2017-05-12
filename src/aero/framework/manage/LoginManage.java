package aero.framework.manage;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import aero.framework.domain.ContextHolder;
import aero.framework.domain.SInfomation;
import aero.framework.utils.EncodeUtil;
import aero.framework.utils.JdbcUtil;


@Component
public class LoginManage {

	@Resource
	JdbcUtil jdbcUtil;
	
	private Map<String,Object> login(String user,String password) throws Exception{
		if(StringUtils.isEmpty(user)||StringUtils.isEmpty(password)){
			throw new Exception("请输入用户名、密码！");
		}
		//密码进行base64编码
		password = EncodeUtil.encodeBase64(password);
		String checkSql = "SELECT A.* FROM SYS_USER A WHERE 1=1 "
				+ " AND A.USER_ = ? AND PASSWORD_ = ? ";
		List<Map<String,Object>> list = jdbcUtil.getJdbcTemplate().queryForList(checkSql,new Object[]{user,password});
		if(list==null || list.isEmpty()){
			throw new Exception("账号密码错误！");
		}
		return list.get(0);
		
	}
	
	
	public void doLogin(HttpServletRequest request,HttpServletResponse response) throws Exception{
		response.setContentType("application/json;charset=UTF-8");
		JSONObject jsonObject = new JSONObject();
		String status = "1";
		String USER = (String) request.getParameter("username");
		String PASSWORD = request.getParameter("password");
		try {
			Map<String,Object> user = login(USER, PASSWORD);
			storeLoginUser(request.getSession(), user);
		} catch (Exception e) {
			e.printStackTrace();
			status = "0";
			jsonObject.put("msg", e.getMessage());
		}
		jsonObject.put("status", status);
		response.getWriter().write(jsonObject.toString());
	}
	
	private void storeLoginUser(HttpSession session,Map<String,Object> user){
		session.setAttribute(SInfomation.USER_SESSION_KEY, user);
		ContextHolder.setSession(session);
		ContextHolder.setLoginUserName((String)user.get("NAME_"));
	}
	
}
