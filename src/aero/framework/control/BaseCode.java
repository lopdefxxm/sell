package aero.framework.control;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import aero.framework.manage.BaseCodeManage;

import com.bstek.dorado.annotation.DataProvider;
import com.bstek.dorado.annotation.DataResolver;

@Component
public class BaseCode {

	@Resource
	BaseCodeManage manage;
	
	@DataProvider
	public List<Map<String,Object>> getCodeClass(Map<String,Object> parameter){
		return manage.getCodeClass(parameter);
		
	}
	
	@DataProvider
	public List<Map<String,Object>> getCode(String CodeClass){
		return manage.getCode(CodeClass);
	}
	

	@DataResolver
	public void saveAll(List<Map<String, Object>> datas,Map<String, Object> parameter) throws Exception {
		manage.saveAll(datas,parameter);
	}
	
}
