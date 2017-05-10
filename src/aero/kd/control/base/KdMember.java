package aero.kd.control.base;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import aero.kd.manage.base.KdMemberManage;

import com.bstek.dorado.annotation.DataProvider;
import com.bstek.dorado.annotation.DataResolver;
import com.bstek.dorado.annotation.Expose;

@Component
public class KdMember {
	
	@Resource
	KdMemberManage manage;
	
	@DataProvider
	public List<Map<String,Object>> getAll(Map<String,Object> parameter) throws Exception{
		return manage.getAll(parameter);
	}
	
	@DataProvider
	public List<Map<String,Object>> getPou(String PK_KD_MEMBER) throws Exception{
		return manage.getPou(PK_KD_MEMBER);
	}
	
	@Expose
	public void savePou(Map<String,Object> parameter) throws Exception{
		manage.savePou(parameter);
	}

	@DataResolver
	public void saveAll(List<Map<String, Object>> datas,Map<String, Object> parameter) throws Exception {
		manage.saveAll(datas,parameter);
	}
	
}
