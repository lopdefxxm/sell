package aero.framework.control;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.bstek.dorado.annotation.DataProvider;
import com.bstek.dorado.annotation.DataResolver;

import aero.framework.manage.SysMenuManage;

@Component
public class SysMenu {

	@Resource
	SysMenuManage manage;
	
	@DataProvider
	public List<Map<String,Object>> getNavigateMenu(){
		//svn test
		return manage.getNavigateMenu();
	}
	
	@DataProvider
	public List<Map<String,Object>> getChildMenu(String pkParent){
		return manage.getChildMenu(pkParent);
		
	}

	@DataResolver
	public void saveAll(List<Map<String, Object>> datas,Map<String, Object> parameter) throws Exception {
		manage.saveAll(datas,parameter);
	}
	
}
