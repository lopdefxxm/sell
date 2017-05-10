package aero.kd.manage.biz;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import aero.framework.domain.BaseManage;

@Component
public class KdBalanceManage extends BaseManage {

	public List<Map<String, Object>> getAll(Map<String, Object> parameter)
			throws Exception {
		
		String sql = "SELECT A.*,B.NAME MEM_NAME,D.NAME ROOM_NAME FROM KD_BALANCE A "
				+ " LEFT JOIN KD_MEMBER B ON B.PK_KD_MEMBER = A.PK_KD_MEMBER "
				+ " LEFT JOIN KD_ROOM D ON D.PK_KD_ROOM = A.PK_KD_ROOM "
				+ " WHERE A.ACTIVE = '1' ";
		List<Object> params = new ArrayList<>();
		if(parameter!=null && !parameter.isEmpty()){
			sql += commonUtil.parseCondition(parameter.get("YEAR"), params, "DATE_FORMAT(A.BILL_DATE,'%Y')");
			sql += commonUtil.parseCondition(parameter.get("MONTH"), params, "DATE_FORMAT(A.BILL_DATE,'%m')");
			sql += commonUtil.parseCondition(parameter.get("BEG_DATE"), params, "A.BILL_DATE",">=");
			sql += commonUtil.parseCondition(parameter.get("END_DATE"), params, "A.BILL_DATE","<=");
			sql += commonUtil.parseCondition(parameter.get("BILL_DATE"), params, "A.BILL_DATE");
		}
		sql += "ORDER BY A.BILL_DATE DESC ";
		return jdbcUtil.getJdbcTemplate().queryForList(sql,params.toArray());
	}


}
