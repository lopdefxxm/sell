package aero.kd.manage.base;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Component;

import aero.framework.domain.AbstractManage;

@Component
public class KdPersonManage extends AbstractManage{
	
	public List<Map<String,Object>> getAll(Map<String,Object> parameter)throws Exception{
		String sql = "SELECT A.* FROM KD_PERSON A WHERE A.ACTIVE = '1' ";
		List<Object> params =  new ArrayList<Object>();
		if(parameter!=null && !parameter.isEmpty()){
			sql += commonUtil.parseCondition(parameter.get("CODE"), params, "A.CODE");
			sql += commonUtil.parseCondition(parameter.get("NAME"), params, "A.NAME","LIKE");
			sql += commonUtil.parseCondition(parameter.get("STATUS"), params, "A.STATUS");
		}
		sql += " ORDER BY A.CODE ";
		return jdbcUtil.getJdbcTemplate().queryForList(sql,params.toArray());
	}
	
	

	@Override
	protected String getTableName(int index) {
		// TODO Auto-generated method stub
		return "KD_PERSON";
	}

	@Override
	protected String getTableKeys(int index) {
		// TODO Auto-generated method stub
		return "PK_KD_PERSON";
	}

	@Override
	protected Set<String> getExcludeFields(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void beforeInsert(Map<String, Object> data, int index)
			throws Exception {
		commonUtil.attachCreateUserInfo(data);
		commonUtil.attachUpdateUserInfo(data);
		data.put(getTableKeys(index), jdbcUtil.getSequence());
		
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
