package aero.framework.manage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import aero.framework.domain.AbstractManage;

@Component
public class BaseCodeManage extends AbstractManage{

	public List<Map<String,Object>> getCodeClass(Map<String,Object> parameter){
		String sql = "SELECT A.* FROM BASE_CLASS A WHERE A.ACTIVE = '1' ";
		List<Object> param =  new ArrayList<Object>();
		if(parameter!=null && !parameter.isEmpty()){
			String CLASS_ID = (String)parameter.get("CLASS_ID");
			String CLASS_NAME = (String)parameter.get("CLASS_NAME");
			if(StringUtils.isNotEmpty(CLASS_ID)){
				sql += " AND A.CLASS_ID = ? ";
				param.add(CLASS_ID);
			}
			if(StringUtils.isNotEmpty(CLASS_NAME)){
				sql += " AND A.CLASS_ID LIKE '%"+CLASS_NAME+"%' ";
			}
		}
		sql += " ORDER BY A.CREATE_DATE DESC ";
		return jdbcUtil.getJdbcTemplate().queryForList(sql,param.toArray());
	}
	
	
	public List<Map<String,Object>> getCode(String CodeClass){
		String sql = "SELECT A.* FROM BASE_CODE A WHERE A.ACTIVE = '1' AND A.CODE_CLASS = ? ORDER BY A.CODE_ID ";
		return jdbcUtil.getJdbcTemplate().queryForList(sql,CodeClass);
	}

	@Override
	protected String getTableName(int index) {
		switch (index) {
		case 0:
			return "BASE_CLASS";
		case 1:
			return "BASE_CODE";
		}
		return null;
	}

	@Override
	protected String getTableKeys(int index) {
		switch (index) {
		case 0:
			return "PK_BASE_CLASS";
		case 1:
			return "PK_BASE_CODE";
		}
		return null;
	}

	@Override
	protected Set<String> getExcludeFields(int index) {
		Set<String> set = new HashSet<String>();
		switch (index) {
		case 0:
			set.add("child");
			break;
		}
		return set;
	}

	@Override
	protected void beforeInsert(Map<String, Object> data, int index)
			throws Exception {
		commonUtil.attachCreateUserInfo(data);
		commonUtil.attachUpdateUserInfo(data);
		data.put(getTableKeys(index),jdbcUtil.getSequence());
		
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
