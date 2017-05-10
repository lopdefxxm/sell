package aero.framework.manage;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Component;

import aero.framework.domain.AbstractManage;

@Component
public class SysMenuManage extends AbstractManage{

	public List<Map<String,Object>> getNavigateMenu(){
		String sql = "SELECT A.* FROM SYS_MENU A "
				+ " WHERE A.ACTIVE = '1' AND A.IS_NAVIGATION = 'Y' "
				+ " AND A.PK_PARENT_MENU IS NULL "
				+ " ORDER BY A.MENU_ORDER ";
		return jdbcUtil.getJdbcTemplate().queryForList(sql);
	}
	
	public List<Map<String,Object>> getChildMenu(String pkParent){
		String sql = "SELECT A.* FROM SYS_MENU A "
				+ " WHERE A.ACTIVE = '1' AND A.IS_NAVIGATION = 'N' "
				+ " AND A.PK_PARENT_MENU = ?"
				+ " ORDER BY A.MENU_ORDER ";
		return jdbcUtil.getJdbcTemplate().queryForList(sql,pkParent);
		
	}

	@Override
	protected String getTableName(int index) {
		// TODO Auto-generated method stub
		return "SYS_MENU";
	}

	@Override
	protected String getTableKeys(int index) {
		// TODO Auto-generated method stub
		return "PK_SYS_MENU";
	}

	@Override
	protected Set<String> getExcludeFields(int index) {
		Set<String> set = new HashSet<String>();
		set.add("child");
		return set;
	}

	@Override
	protected void beforeInsert(Map<String, Object> data, int index)
			throws Exception {
		commonUtil.attachCreateUserInfo(data);
		commonUtil.attachUpdateUserInfo(data);
		data.put(getTableKeys(0),jdbcUtil.getSequence());
		
	}

	@Override
	protected void beforeUpdate(Map<String, Object> data, int index)
			throws Exception {
		commonUtil.attachUpdateUserInfo(data);
		
	}

	@Override
	protected void beforeDelete(Map<String, Object> data, int index)
			throws Exception {
		// TODO Auto-generated method stub
		
	}
	
}
