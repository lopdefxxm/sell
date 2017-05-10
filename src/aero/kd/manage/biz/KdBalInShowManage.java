package aero.kd.manage.biz;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import aero.framework.domain.BaseManage;

@Component
public class KdBalInShowManage extends BaseManage{

	public List<Map<String,Object>> getAll(Map<String,Object> parameter)throws Exception{
		
		String sql = " SELECT DATE_FORMAT(BILL_DATE,'%Y-%m-%d') NAME ,SUM(REAL_AMT) VALUE  "
				+ " FROM KD_BALANCE A "
				+ " WHERE PAY_TYPE IS NOT NULL ";
		List<Object> params = new ArrayList<>();
		if(parameter!=null && !parameter.isEmpty()){
			sql += commonUtil.parseCondition(parameter.get("YEAR"), params, "DATE_FORMAT(A.BILL_DATE,'%Y')");
			sql += commonUtil.parseCondition(parameter.get("MONTH"), params, "DATE_FORMAT(A.BILL_DATE,'%m')");
			sql += commonUtil.parseCondition(parameter.get("BEG_DATE"), params, "A.BILL_DATE",">=");
			sql += commonUtil.parseCondition(parameter.get("END_DATE"), params, "A.BILL_DATE","<=");
		}
		sql += " GROUP BY BILL_DATE ORDER BY BILL_DATE" ;
		return jdbcUtil.getJdbcTemplate().queryForList(sql,params.toArray());
	}

}
