package aero.framework.manage;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.bstek.dorado.data.entity.EntityState;
import com.bstek.dorado.data.entity.EntityUtils;

import aero.framework.domain.AbstractManage;
import aero.framework.utils.EncodeUtil;

@Component
public class SysUserManage extends AbstractManage {

	public List<Map<String, Object>> getAll(Map<String,Object> parameter)throws Exception {
		String sql = "select * from SYS_USER  a order by ID_ ";
		return jdbcUtil.getJdbcTemplate().queryForList(sql);
	}
	
	@SuppressWarnings("unchecked")
	public void updatePasward(Map<String,Object> parameter)throws Exception {
		Map<String,Object> data = (Map<String,Object>)parameter.get("data");
		String newPsd = (String)parameter.get("NEW_PSD");
		data.put("PASSWORD_",EncodeUtil.encodeBase64(newPsd));
		EntityUtils.setState(data, EntityState.MODIFIED);
		this.saveData(data, 0);
	}
	
	@Override
	protected String getTableName(int index) {
		// TODO Auto-generated method stub
		return "SYS_USER";
	}

	@Override
	protected String getTableKeys(int index) {
		// TODO Auto-generated method stub
		return "ID_";
	}

	@Override
	protected Set<String> getExcludeFields(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void beforeInsert(Map<String, Object> data, int index)
			throws Exception {
		//添加默认密码 123
		data.put("PASSWORD_",EncodeUtil.encodeBase64("123"));
		data.put("ID_", jdbcUtil.getSequence());

	}

	@Override
	protected void beforeUpdate(Map<String, Object> data, int index)
			throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	protected void beforeDelete(Map<String, Object> data, int index)
			throws Exception {
		if("admin".equals(data.get("USER_"))){
			throw new Exception("系统管理员不允许删除！");
		}
		// TODO Auto-generated method stub

	}

}
