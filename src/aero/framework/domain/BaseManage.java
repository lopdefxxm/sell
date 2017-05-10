package aero.framework.domain;

import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Component;

@Component
public class BaseManage extends AbstractManage{

	@Override
	protected String getTableName(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String getTableKeys(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Set<String> getExcludeFields(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void beforeInsert(Map<String, Object> data, int index)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void beforeUpdate(Map<String, Object> data, int index)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void beforeDelete(Map<String, Object> data, int index)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

}
