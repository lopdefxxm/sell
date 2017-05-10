package aero.framework.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import aero.framework.utils.JdbcUtil;

import com.bstek.dorado.annotation.Expose;

@Component
public class IChartSupport {

	@Resource
	JdbcUtil jdbcUtil;
	
	@SuppressWarnings("unchecked")
	@Expose
	public List<Map<String,Object>> dataSupport(Map<String,Object> parameter)throws Exception{
		String table = (String)parameter.get("table");
		String name = (String)parameter.get("name");
		String value = (String)parameter.get("value");
		Map<String,Object> condition = (Map<String,Object>)parameter.get("condition");
		if(StringUtils.isEmpty(table) || StringUtils.isEmpty(table) || StringUtils.isEmpty(table)){
			return null;
		}
		String sql = getSql(table, name, value,condition);
		List<Map<String,Object>> list = jdbcUtil.getJdbcTemplate().queryForList(sql);
		
		return getIChartData(list);
	}

	/**
	 * 将sql查询值整理成符合ichat的data类型
	 * 	{
	 * 		name:name,
	 * 		value:value,
	 * 		color:color
	 *  }
	 * @param list
	 * @return
	 */
	private List<Map<String,Object>> getIChartData(List<Map<String, Object>> list) {
		if(list == null || list.isEmpty()){
			return null;
		}
		List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
		Map<String,Object> res = null;
		for(Map<String,Object> map:list){
			res = new HashMap<String,Object>();
			res.put("name", map.get("NAME"));
			double d = 0;
			try {
				d = Double.valueOf(map.get("VALUE")+"");
			} catch (Exception e) {
				d = 0;
			}
			res.put("value", d);
			res.put("color",getColor(d));
			result.add(res);
		}
		return result;
	}

	/**
	 * 根据表名，指定的字段拼装查询sql
	 * @param table 表名	
	 * @param name 横向显示字段值
	 * 		显示的字段为关连字段时，使用joinTable-column格式（默认使用主键关连，并且主键为PK_TABLENAME格式）
	 * @param value 纵向显示字段
	 * @return
	 */
	private String getSql(String table, String name, String value,Map<String,Object> condition) {
		String functon = "SUM";
		//存在价格时使用平均数函数
		if(value.toUpperCase().indexOf("PRICE")!=-1){
			functon = "AVG";
		}
		String conditionSql = getConditionSql(condition);
		String sql = "SELECT "+functon+"("+value+") AS VALUE,"+name+" AS NAME FROM "+table+" A WHERE 1=1 "+conditionSql+" GROUP BY "+name;
		if(name.indexOf("-")!=-1){
			//TABLE.FILED
			String[] names = name.split("-");
			String joinTable = names[0];
			String pk = "PK_"+joinTable;
			String field = names[1];
			sql = "SELECT SUM(A."+value+") AS VALUE,B."+field+" AS NAME FROM "+table+" A "
					+ " LEFT JOIN "+joinTable+" B ON A."+pk+" = B."+pk
					+ " WHERE 1=1 "+conditionSql
					+ " GROUP BY B."+field;
			
		}
		return sql;
	}
	
	/**
	 * 简单的将传入参数拼装成sql
	 * @param condition
	 * @return
	 */
	private String getConditionSql(Map<String,Object> condition){
		if(condition==null){
			return "";
		}
		String sql = "";
		for(Map.Entry<String,Object> entity:condition.entrySet()){
			String key = entity.getKey();
			Object value = entity.getValue();
			sql += " AND A."+key+" = "+value;
		}
		return sql;
		
	}
	
	/**
	 * 根据纵向显示的值得到一个16位颜色编码
	 * 算法：获取当前数据的hex值，超过6位则截取6位，否则将数值进行平方递归
	 * @param value
	 * @return
	 */
	private String getColor(double value){
		int i = Double.valueOf(value).intValue();
		String str = Integer.toHexString(i);
		if(str.length()>=6){
			return "#"+ str.substring(0,6);
		}
		int a = 30;
		return getColor((i+a)*(i+100-a));
	}
	
}
