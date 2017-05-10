package aero.kd.control.biz;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import aero.kd.manage.biz.KdBizMainManage;

import com.bstek.dorado.annotation.DataProvider;
import com.bstek.dorado.annotation.DataResolver;
import com.bstek.dorado.annotation.Expose;

@Component
public class KdBizMain {
	
	@Resource
	KdBizMainManage manage;
	
	@DataProvider
	public List<Map<String,Object>> getRoom(Map<String,Object> parameter)throws Exception{
		return manage.getRoom(parameter);
	}
	
	@DataProvider
	public List<Map<String,Object>> getPsn(Map<String,Object> parameter)throws Exception{
		return manage.getPsn(parameter);
	}
	
	@DataProvider
	public List<Map<String,Object>> getBalRoom(Map<String,Object> parameter)throws Exception{
		return manage.getBalRoom(parameter);
	}
	
	
	@DataProvider
	public List<Map<String,Object>> getMemPou(String PK_KD_MEMBER)throws Exception{
		return manage.getMemPou(PK_KD_MEMBER);
	}
	
	@DataProvider
	public List<Map<String, Object>> getMember(String filter)throws Exception {
		return manage.getMember(filter);
	}
	
	@Expose
	public void saveBalCalculate(Map<String,Object> parameter)throws Exception{
		manage.saveBalCalculate(parameter);
	}
	
//	@Expose
//	public Map<String,Object> memberPay(Map<String,Object> parameter)throws Exception{
//		return manage.memberPay(parameter);
//	}
	
	@DataResolver
	public void saveAll(List<Map<String, Object>> datas,Map<String, Object> parameter) throws Exception {
		manage.saveAll(datas,parameter);
	}
	
	
}
