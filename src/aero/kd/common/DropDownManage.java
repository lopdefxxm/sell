package aero.kd.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import aero.framework.utils.JdbcUtil;

import com.bstek.dorado.annotation.DataProvider;

@Component
public class DropDownManage {

	@Resource
	JdbcUtil jdbcUtil;
	
	/**
	 * Y/N的下拉菜单
	 * @return
	 */
	@DataProvider
	public List<Map<String,Object>> getYnMaps(){
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("key","1");
		map.put("value","是");
		list.add(map);
		map = new HashMap<String, Object>();
		map.put("key","0");
		map.put("value","否");
		list.add(map);
		return list;
	}
	
	/**
	 * 从通用数据编码里面取得下拉菜单
	 * @param code
	 * @return
	 */
	@DataProvider
	public List<Map<String,Object>> getCodes(String code){
		if(StringUtils.isEmpty(code)){
			return null;
		}
		String sql = "SELECT CODE_ID ,CODE_NAME FROM BASE_CODE "
				+ " WHERE ACTIVE = '1' AND CODE_CLASS = ? ORDER BY CODE_ID ";
		return jdbcUtil.getJdbcTemplate().queryForList(sql,code);
	}
	
}
