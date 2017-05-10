package aero.kd.manage.biz;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bstek.dorado.data.entity.EntityState;
import com.bstek.dorado.data.entity.EntityUtils;

import aero.framework.domain.AbstractManage;

@Component
public class KdBizMainManage extends AbstractManage{

	public List<Map<String,Object>> getRoom(Map<String,Object> parameter)throws Exception{
		String sql = "SELECT A.*,B.CODE_NAME STATUS_NAME FROM KD_ROOM A "
				+ " LEFT JOIN BASE_CODE B ON B.CODE_CLASS ='ROOM_STATUS' AND A.STATUS = B.CODE_ID "
				+ "WHERE A.ACTIVE = '1' "
				+ " ORDER BY A.CODE ";
		return jdbcUtil.getJdbcTemplate().queryForList(sql);
	}
	
	/**
	 * 查询所有的技师
	 */
	public List<Map<String,Object>> getPsn(Map<String,Object> parameter)throws Exception{
		if(parameter==null){
			parameter = new HashMap<String, Object>();
		}
		String status = (String)parameter.get("STATUS");
		String sql = "SELECT A.* FROM KD_PERSON A "
				+ " WHERE A.ACTIVE = '1' AND A.TYPE = '02' "
				+ (StringUtils.isNotEmpty(status)?" AND A.STATUS IN ("+status+")" : "")
				+ " ORDER BY STATUS,A.CODE ";
		return jdbcUtil.getJdbcTemplate().queryForList(sql);
	}
	
	/**
	 * 查询包厢消费
	 */
	public List<Map<String,Object>> getBalRoom(Map<String,Object> parameter)throws Exception{
		if(parameter  == null){
			return null;
		}
		String sql = "SELECT A.*,B.NAME PSN_NAME,C.NEED_PSN FROM KD_BALROOM A  "
				 + " LEFT JOIN KD_PERSON B ON A.PK_KD_PERSON = B.PK_KD_PERSON "
				 + " LEFT JOIN KD_CHARGEITEM C ON A.PK_KD_CHARGEITEM = C.PK_KD_CHARGEITEM "
				 + " WHERE PK_KD_ROOM = ? AND A.CHARGE_STATUS = '01' ORDER BY A.PK_KD_BALROOM ";
		return jdbcUtil.getJdbcTemplate().queryForList(sql,parameter.get("PK_KD_ROOM"));
	}
	
	
	public List<Map<String, Object>> getMember(String filter)throws Exception {
		if(StringUtils.isEmpty(filter)){
			return null;
		}
		String sql = "SELECT A.* FROM KD_MEMBER A WHERE A.ACTIVE = '1' ";
		String regEx1 = "[a-zA-Z]*";
		String regEx2 = "[0-9]*";
		List<Object> params = new ArrayList<>();
		Matcher matcher1 = Pattern.compile(regEx1).matcher(filter);
		Matcher matcher2 = Pattern.compile(regEx2).matcher(filter);
		if(matcher1.matches()){
			sql += commonUtil.parseCondition(filter, params, "A.SIMPLE_NAME","LIKE");
		}else if(matcher2.matches()){
			sql += commonUtil.parseCondition(filter, params, "A.PHONE","=");
		}else{
			sql += commonUtil.parseCondition(filter, params, "A.NAME","LIKE");
		}
		return jdbcUtil.getJdbcTemplate().queryForList(sql,params.toArray());
	}
	
	/**
	 * 查询会员优惠券
	 * @param parameter
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,Object>> getMemPou(String PK_KD_MEMBER)throws Exception{
		String sql = "SELECT A.PK_KD_CHARGEITEM,C.NAME,count(*) COUNT FROM KD_MEMBER_POU A  "
				 + " LEFT JOIN KD_CHARGEITEM C ON A.PK_KD_CHARGEITEM = C.PK_KD_CHARGEITEM "
				 + " WHERE PK_KD_MEMBER = ? AND A.CAN_USED = '1' "
				 + " GROUP BY C.NAME,A.PK_KD_CHARGEITEM ";
		return jdbcUtil.getJdbcTemplate().queryForList(sql,	PK_KD_MEMBER);
	}
	
	/**
	 * 结账保存
	 * @param parameter
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public void saveBalCalculate(Map<String,Object> parameter)throws Exception{
		List<Map<String,Object>> roombals = (List<Map<String,Object>>)parameter.get("roombals");
		Map<String,Object> member = (Map<String,Object>)parameter.get("member");
		Map<String,Object> balance = (Map<String,Object>)parameter.get("balance");
		Map<String,Object> room = (Map<String,Object>)parameter.get("room");
		
		String IS_MEMBER = (String)balance.get("IS_MEMBER");
		String IS_DEDUCTION = (String)balance.get("IS_DEDUCTION");
		
		Object PK_KD_ROOM = room.get("PK_KD_ROOM");//包厢
		String PK_KD_BALANCE = jdbcUtil.getSequence();//结算单
		Date sysdate = new Date();
		for(Map<String,Object> c : roombals){
			EntityUtils.setState(c, EntityState.MODIFIED);
			c.put("CHARGE_STATUS","02");
			c.put("PK_KD_BALANCE", PK_KD_BALANCE);//将结算单据记录
			if("1".equals(IS_MEMBER)){//会员消费
				if("1".equals(IS_DEDUCTION)){
					String sql = " UPDATE KD_MEMBER_POU SET CAN_USED = '0' WHERE PK_KD_MEMBER = ? AND PK_KD_CHARGEITEM = ? AND CAN_USED = '1' LIMIT ? ";
					jdbcUtil.getJdbcTemplate().update(sql,member.get("PK_KD_MEMBER"), c.get("PK_KD_CHARGEITEM"), c.get("QTY"));
				}
			}
			//修改包厢消费单
			this.saveData(c, 0);
			
		}
		
		balance.put("PK_KD_BALANCE", PK_KD_BALANCE);
		balance.put("PK_KD_ROOM", PK_KD_ROOM);
		balance.put("BILL_DATE", sysdate);//单据日期
		
		if("1".equals(IS_MEMBER)){
			balance.put("PK_KD_MEMBER", member.get("PK_KD_MEMBER"));
		}
		EntityUtils.setState(balance, EntityState.NEW);
		this.saveData(balance,4);
		
		//房间 技师状态修改
		String sql = "UPDATE KD_ROOM SET STATUS = '01' ,GUSTS = 0,ORDER_TIME=NULL,USE_TIME=NULL WHERE PK_KD_ROOM = ?  ";
		jdbcUtil.getJdbcTemplate().update(sql,room.get("PK_KD_ROOM"));
		changePsnStatus();
		
	}
	
	
//	/**
//	 * 会员支付
//	 * @param parameter
//	 * @throws Exception
//	 */
//	@SuppressWarnings("unchecked")
//	@Transactional(propagation=Propagation.REQUIRES_NEW)
//	public Map<String,Object> memberPay(Map<String,Object> parameter)throws Exception{
//		Map<String,Object> member = (Map<String,Object>)parameter.get("member");
//		Map<String,Object> payInfo = (Map<String,Object>)parameter.get("payInfo");
//		List<Map<String,Object>> couitems = (List<Map<String,Object>>)parameter.get("couitems");
//		List<Map<String,Object>> pays = (List<Map<String,Object>>)parameter.get("pays");
//		
//		String IS_COUPON = (String)payInfo.get("IS_COUPON");
//		Double REAL_AMT = (Double)payInfo.get("REAL_AMT");
//		Object PK_KD_MEMBER = member.get("PK_KD_MEMBER");
//		
//		if("1".equals(IS_COUPON)){//参与活动的情况，给人员添加优惠券
//			Map<String,Object> data = new HashMap<>();
//			for(Map<String,Object> c : couitems){
//				int qty = (Integer)c.get("QTY");
//				for(int i=0;i<qty;i++){
//					data.put("PK_KD_MEMBER",PK_KD_MEMBER);
//					data.put("PK_KD_CHARGEITEM",c.get("PK_KD_CHARGEITEM"));
//					this.saveData(data, 3);
//				}
//			}
//		}
//		//更新人员上的卡余额
//		String sql = "UPDATE KD_MEMBER SET CARD_AMT = CARD_AMT + ? WHERE PK_KD_MEMBER = ? ";
//		jdbcUtil.getJdbcTemplate().update(sql,REAL_AMT, PK_KD_MEMBER);
//		//插入支付信息
//		Date sysdate = new Date();
//		for(Map<String,Object> p : pays){
//			EntityUtils.setState(p, EntityState.NEW);
//			p.put("PK_KD_MEMBER",PK_KD_MEMBER);//单据来源，会员充值
//			p.put("BILL_SOURCE","01");//单据来源，会员充值
//			p.put("BILL_DATE",sysdate);//单据日期
//			this.saveData(p, 4);
//		}
//		
//		sql = "SELECT A.* FROM KD_MEMBER A WHERE A.PK_KD_MEMBER = ? ";
//		try {
//			return jdbcUtil.getJdbcTemplate().queryForMap(sql,PK_KD_MEMBER);
//		} catch (Exception e) {
//			return null;
//		}
//	}
	
	@Override
	protected String getTableName(int index) {
		switch (index) {
		case 0:
			return "KD_BALROOM";
		case 1:
			return "KD_ROOM";
		case 2:
			return "KD_MEMBER";
		case 3:
			return "KD_MEMBER_POU";
		case 4:
			return "KD_BALANCE";
		}
		return null;
	}

	@Override
	protected String getTableKeys(int index) {
		switch (index) {
		case 0:
			return "PK_KD_BALROOM";
		case 1:
			return "PK_KD_ROOM";
		case 2:
			return "PK_KD_MEMBER";
		case 3:
			return "PK_KD_MEMBER_POU";
		case 4:
			return "PK_KD_BALANCE";
		}
		return null;
	}

	@Override
	protected Set<String> getExcludeFields(int index) {
		Set<String> set = new HashSet<>();
		switch (index) {
		case 0:
			set.add("PSN_NAME");
			set.add("NEED_PSN");
			break;
		case 1:
			set.add("STATUS_NAME");
			break;
		case 2:
			set.add("PK_KD_BALROOM");
			set.add("PK_KD_ROOM");
			break;
		}
		return set;
	}

	@Override
	protected void beforeInsert(Map<String, Object> data, int index)
			throws Exception {
		data.put(getTableKeys(index),jdbcUtil.getSequence());
		commonUtil.attachCreateUserInfo(data);
		commonUtil.attachUpdateUserInfo(data);
	}

	@Override
	protected void beforeUpdate(Map<String, Object> data, int index)
			throws Exception {
		commonUtil.attachUpdateUserInfo(data);
	}
	
	@Override
	protected void afterInsert(Map<String, Object> data, int index)
			throws Exception {
		switch (index) {
		case 0:
			changePsnStatus();
			break;
		}
	}
	@Override
	protected void afterUpdate(Map<String, Object> data, int index)
			throws Exception {
		switch (index) {
		case 0:
			changePsnStatus();
			break;
		}
	}
	@Override
	protected void afterDelete(Map<String, Object> data, int index)
			throws Exception {
		switch (index) {
		case 0:
			changePsnStatus();
			break;
		}
	}
	
	private void changePsnStatus(){
		String sql = "UPDATE KD_PERSON A SET STATUS = '02' WHERE EXISTS (SELECT 1 FROM KD_BALROOM C WHERE C.PK_KD_PERSON = A.PK_KD_PERSON AND CHARGE_STATUS = '01' ) ";
		String sql1 = "UPDATE KD_PERSON A SET STATUS = '01' WHERE NOT EXISTS (SELECT 1 FROM KD_BALROOM C WHERE C.PK_KD_PERSON = A.PK_KD_PERSON AND CHARGE_STATUS = '01' ) AND STATUS = '02' ";
		jdbcUtil.getJdbcTemplate().update(sql);
		jdbcUtil.getJdbcTemplate().update(sql1);
		
	}

	@Override
	protected void beforeDelete(Map<String, Object> data, int index)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

}
