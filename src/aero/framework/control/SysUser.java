package aero.framework.control;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import aero.framework.manage.SysUserManage;

import com.bstek.dorado.annotation.DataProvider;
import com.bstek.dorado.annotation.DataResolver;
import com.bstek.dorado.annotation.Expose;

@Component
public class SysUser {

	@Resource
	SysUserManage manage;
	
	
	@DataProvider
	public List<Map<String, Object>> getAll(Map<String,Object> parameter)throws Exception {
		return manage.getAll(parameter);
	}
	
	@Expose
	public void updatePasward(Map<String,Object> parameter)throws Exception {
		manage.updatePasward(parameter);
	}
	
	@DataResolver
	public void saveAll(List<Map<String, Object>> datas,Map<String, Object> parameter) throws Exception {
		manage.saveAll(datas,parameter);
	}
	
}
