package aero.framework.utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import aero.framework.domain.ContextHolder;
import aero.framework.domain.SInfomation;

import com.bstek.dorado.annotation.Expose;

@Component
public class CommonUtil {

	@Resource
	JdbcUtil jdbcUtil;
	
	@Expose
	@SuppressWarnings("unchecked")
	public Map<String,Object> getLoginUser(){
		if(ContextHolder.getSession()==null){
			return new HashMap<String,Object>();
		}
		return (Map<String,Object>)ContextHolder.getSession().getAttribute(SInfomation.USER_SESSION_KEY);
	}
	
	@Expose
	public String getLoginUserName(){
		return ContextHolder.getLoginUserName();
	}
	
	@Expose
	public String getLoginUserId(){
		Map<String,Object> user = getLoginUser();
		if(user!=null){
			return (String)user.get("ID_");
		}
		return null;
	}
	
	@Expose
	public Date getCurretnData() {
		return new Date();
	}
	
	@Expose
	public Map<String,Object> getCurrentMonth(){
		Map<String,Object> data = new HashMap<>();
		Calendar calendar = Calendar.getInstance();
		String year = String.valueOf(calendar.get(Calendar.YEAR));
		String month = StringUtils.leftPad(String.valueOf(calendar.get(Calendar.MONTH)+1), 2, "0");
		calendar.set(Calendar.DAY_OF_MONTH,1);
		Date BEG_DATE = calendar.getTime();
		calendar.add(Calendar.MONTH,1);
		calendar.add(Calendar.DAY_OF_MONTH,-1);
		Date END_DATE = calendar.getTime();
		int days = calendar.get(Calendar.DAY_OF_MONTH);
		
		data.put("YEAR", year);
		data.put("MONTH", month);
		data.put("BEG_DATE", BEG_DATE);
		data.put("END_DATE", END_DATE);
		data.put("DAYS", days);
		return data;
	}
	
	public static void main(String[] args) {
		System.out.println(new CommonUtil().getCurrentMonth());
	}
	
	/**
	 * 生成随机号
	 */
	@Expose
	public String getRandomCode() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		return sdf.format(date) + getLoginUserId();
	}
	
	/**
	 *生成单据号
	 * @author Aero
	 * @return
	 */
	public String getBillNO(String table,String field){
		String billNo = null;
		String sql = "SELECT MAX("+field+") VALUE FORM "+table+" WHERE "+field+" LIKE  CONCAT('%', DATE_FORMAT(SYSDATE(),'%Y%m%e'),'%') ";
		try {
			Map<String,Object> map = jdbcUtil.getJdbcTemplate().queryForMap(sql);
			String value = (String)map.get("VALUE");
			billNo = Long.valueOf(value)+1 +"";
		} catch (Exception e) {
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			billNo = sdf.format(date)+StringUtils.leftPad("1",3,"0");
		}
		//如果不存在，捕捉异常
		return billNo;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void attachUpdateUserInfo(Map data) {
		data.put("UPDATE_DATE", new java.util.Date());
		data.put("UPDATE_USER", this.getLoginUserName());
	}

	/**
	 * 新增时制单人及记录创建时间的添加
	 * 
	 * @param data
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void attachCreateUserInfo(Map data) {
		data.put("CREATE_DATE", new java.util.Date());
		data.put("CREATE_USER", this.getLoginUserName());
	}
	
	public Set<String> getAttachSet(){
		Set<String> set = new HashSet<>();
		set.add("CREATE_USER_NAME");
		set.add("UPDATE_USER_NAME");
		return set;
	}
	
	/**
	 * 将日期设置为当天0点0分0秒
	 *  @param date
	 * @return
	 */
	public Date setBeginDate(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY,0);
		calendar.set(Calendar.MINUTE,0);
		calendar.set(Calendar.SECOND,0);
		return calendar.getTime();
	}
	
	public Date setEndDate(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY,23);
		calendar.set(Calendar.MINUTE,59);
		calendar.set(Calendar.SECOND,59);
		return calendar.getTime();
	}
	
	/**
	 * 根据给定的字段和操作符生成sql条件语句<br/>
	 * @param obj 参数为日期类型时，使用oracle的trunc方法进行去除时分秒比较
	 * @param params 参数列表
	 * @param field 表字段
	 * @param operator 操作符
	 * @return
	 */
	public String parseCondition(Object obj , List<Object> params , String field , String operator){
		if(obj == null){
			return "";
		}
		if (obj instanceof String && StringUtils.isEmpty((String) obj)) {
			return "";
		}
		if (params == null) params = new ArrayList<Object>();
		
		operator = operator == null ? "=" : operator;
		String valueExp = " ? ";
		if ("like".equals(operator.toLowerCase())) {
			return" AND " + field + " " + operator + "'%"+obj+"%' " ;
		}
		if (obj instanceof Date) {
			field = " DATE_FORMAT(" + field + ", '%Y-%m-%d') ";
			valueExp = " DATE_FORMAT(?, '%Y-%m-%d') ";
		}
		params.add(obj);
		return " AND " + field + " " + operator + " " + valueExp;
	}
	
	public String parseCondition(Object obj , List<Object> params , String field){
		return parseCondition(obj, params, field,"=");
	}
	
	public String parseCondition(Object obj , List<Object> params , String field, boolean flag){
		if(obj == null){
			return " AND 1=2 ";
		}
		return parseCondition(obj, params, field,"=");
	}
	
}
