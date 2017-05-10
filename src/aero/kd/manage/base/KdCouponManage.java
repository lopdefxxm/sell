package aero.kd.manage.base;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Component;

import aero.framework.domain.AbstractManage;

@Component
public class KdCouponManage extends AbstractManage{
	
	public List<Map<String,Object>> getAll(Map<String,Object> parameter)throws Exception{
		String sql = "SELECT A.* FROM KD_COUPON A WHERE A.ACTIVE = '1' ";
		List<Object> params =  new ArrayList<Object>();
		if(parameter!=null && !parameter.isEmpty()){
			sql += commonUtil.parseCondition(parameter.get("CODE"), params, "A.CODE");
			sql += commonUtil.parseCondition(parameter.get("NAME"), params, "A.NAME","LIKE");
		}
		sql += " ORDER BY A.CODE ";
		return jdbcUtil.getJdbcTemplate().queryForList(sql,params.toArray());
	}
	
	public List<Map<String,Object>> getItem(String parameter)throws Exception{
		if(parameter == null ){
			return null;
		}
		String sql = "SELECT * "
				+ " FROM KD_COUPON_ITEM A "
				+ " LEFT JOIN KD_CHARGEITEM B ON A.PK_KD_CHARGEITEM = B.PK_KD_CHARGEITEM "
				+ " WHERE A.ACTIVE = '1' AND A.PK_KD_COUPON = ? ";
		return jdbcUtil.getJdbcTemplate().queryForList(sql,parameter);
	}
	
	

	@Override
	protected String getTableName(int index) {
		switch (index) {
		case 1:
			return "KD_COUPON_ITEM";
		}
		return "KD_COUPON";
	}

	@Override
	protected String getTableKeys(int index) {
		switch (index) {
		case 1:
			return "PK_KD_COUPON_ITEM";
		}
		return "PK_KD_COUPON";
	}

	@Override
	protected Set<String> getExcludeFields(int index) {
		Set<String> set = new HashSet<String>();
		switch (index) {
		case 1:
			set.add("CODE");
			set.add("NAME");
			set.add("DES");
			set.add("PRICE");
			set.add("MEM_PRICE"); 
			break;
		}
		// TODO Auto-generated method stub
		return set;
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
