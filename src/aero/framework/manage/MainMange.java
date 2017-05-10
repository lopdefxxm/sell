package aero.framework.manage;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import aero.framework.utils.CommonUtil;
import aero.framework.utils.JdbcUtil;

import com.bstek.dorado.annotation.DataProvider;
import com.bstek.dorado.annotation.Expose;

@Component
public class MainMange {

	@Resource
	JdbcUtil jdbcUtil;
	
	@Resource
	CommonUtil commonUtil;
	
	@DataProvider
	public List<Map<String,Object>> getNav(){
		String sql = "SELECT A.* FROM SYS_MENU A "
				+ " WHERE A.ACTIVE = '1' AND A.IS_NAVIGATION = 'Y' "
				+ ("1".equals(commonUtil.getLoginUserId())?" AND 1=1 ":" AND IS_SHOW = 'Y' ")
				+ " AND A.PK_PARENT_MENU IS NULL "
				+ " ORDER BY A.MENU_ORDER ";
		return jdbcUtil.getJdbcTemplate().queryForList(sql);
	}
	
	@DataProvider
	public List<Map<String,Object>> getChild(String pkParent){
		String sql = "SELECT A.* FROM SYS_MENU A "
				+ " WHERE A.ACTIVE = '1' AND A.IS_NAVIGATION = 'N' "
				+ " AND A.PK_PARENT_MENU = ? "
				+ ("1".equals(commonUtil.getLoginUserId())?" AND 1=1 ":" AND IS_SHOW = 'Y' ")
				+ " ORDER BY A.MENU_ORDER ";
		return jdbcUtil.getJdbcTemplate().queryForList(sql,pkParent);
		
	}
	
	@Expose
	public void updateMenuHis(String pkMenu){
		String loginUser = commonUtil.getLoginUserId();
		String sql = "SELECT COUNT(*) FROM SYS_MENU_HIS WHERE PK_SYS_MENU = ? AND PK_SYS_USER = ? ";
		int c = jdbcUtil.getJdbcTemplate().queryForInt(sql,pkMenu,loginUser);
		Date sysdate = new Date();
		if(c > 0){
			String updateSql = "UPDATE SYS_MENU_HIS SET COUNT=COUNT+1,LAST_CLICK_TIME = ?  WHERE PK_SYS_MENU = ? AND PK_SYS_USER = ? ";
			jdbcUtil.getJdbcTemplate().update(updateSql,sysdate,pkMenu,loginUser);
		}else{
			String updateSql = "INSERT INTO SYS_MENU_HIS (PK_SYS_MENU_HIS,PK_SYS_MENU,PK_SYS_USER,LAST_CLICK_TIME) VALUES(?,?,?,?)";
			jdbcUtil.getJdbcTemplate().update(updateSql,jdbcUtil.getSequence(),pkMenu,loginUser,sysdate);
		}
	}
	
	@DataProvider
	public List<Map<String,Object>> getMenuHis(){
		String loginUser = commonUtil.getLoginUserId();
		String sql = "SELECT B.* FROM SYS_MENU_HIS A "
				+ " LEFT JOIN SYS_MENU B ON A.PK_SYS_MENU = B.PK_SYS_MENU "
				+ " WHERE B.ACTIVE = '1' AND B.IS_NAVIGATION = 'N' "
				+ " AND A.PK_SYS_USER = ? "
				+ " ORDER BY COUNT DESC "
				+ " LIMIT 0,10 ";
		return jdbcUtil.getJdbcTemplate().queryForList(sql,loginUser);
		
	}
}
