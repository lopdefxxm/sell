package aero.kd.manage.base;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.dorado.data.entity.EntityState;
import com.bstek.dorado.data.entity.EntityUtils;

import aero.framework.domain.AbstractManage;

@Component
public class KdMemberManage extends AbstractManage{
	
	public List<Map<String,Object>> getAll(Map<String,Object> parameter)throws Exception{
		String sql = "SELECT A.* FROM KD_MEMBER A WHERE A.ACTIVE = '1' ";
		List<Object> params =  new ArrayList<Object>();
		if(parameter!=null && !parameter.isEmpty()){
			sql += commonUtil.parseCondition(parameter.get("CODE"), params, "A.CODE");
			sql += commonUtil.parseCondition(parameter.get("NAME"), params, "A.NAME","LIKE");
		}
		sql += " ORDER BY A.CODE ";
		return jdbcUtil.getJdbcTemplate().queryForList(sql,params.toArray());
	}
	
	public List<Map<String,Object>> getPou(String PK_KD_MEMBER) throws Exception{
		String sql = "SELECT A.PK_KD_CHARGEITEM,C.NAME,COUNT(*) COUNT,PK_KD_MEMBER FROM KD_MEMBER_POU A  "
				 + " LEFT JOIN KD_CHARGEITEM C ON A.PK_KD_CHARGEITEM = C.PK_KD_CHARGEITEM "
				 + " WHERE PK_KD_MEMBER = ? AND A.CAN_USED = '1' "
				 + " GROUP BY C.NAME,A.PK_KD_CHARGEITEM,PK_KD_MEMBER";
		return jdbcUtil.getJdbcTemplate().queryForList(sql,	PK_KD_MEMBER);
	}
	

	@SuppressWarnings("unchecked")
	@Transactional
	public void savePou(Map<String,Object> parameter) throws Exception{
		Map<String,Object> data = (Map<String,Object>)parameter.get("data");
		String type = (String)parameter.get("type");
		int count = (Integer)data.get("COUNT");
		if("delete".equals(type)){
			String sql = " UPDATE KD_MEMBER_POU SET CAN_USED = '2',UPDATE_DATE =?,UPDATE_USER =? WHERE PK_KD_MEMBER = ? AND PK_KD_CHARGEITEM = ? AND CAN_USED = '1' LIMIT ? ";
			Object[] args = new Object[]{new Date(),commonUtil.getLoginUserName(),data.get("PK_KD_MEMBER"), data.get("PK_KD_CHARGEITEM"), count};
			jdbcUtil.getJdbcTemplate().update(sql,args);
		}else if("add".equals(type)){
			while(count-->0){
				EntityUtils.setState(data, EntityState.NEW);
				this.saveData(data, 1);
			}
		}
	}
	
	@Override
	protected String getTableName(int index) {
		switch (index) {
		case 1:
			return "KD_MEMBER_POU";
		default:
			return "KD_MEMBER";
		}
		// TODO Auto-generated method stub
	}

	@Override
	protected String getTableKeys(int index) {
		switch (index) {
		case 1:
			return "PK_KD_MEMBER_POU";
		default:
			return "PK_KD_MEMBER";
		}
	}

	@Override
	protected Set<String> getExcludeFields(int index) {
		// TODO Auto-generated method stub
		switch (index) {
		case 1:
			Set<String> set = new HashSet<>();
			set.add("NAME");
			set.add("COUNT");
			return set;
		}
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
