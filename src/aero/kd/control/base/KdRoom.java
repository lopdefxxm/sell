package aero.kd.control.base;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import aero.kd.manage.base.KdRoomManage;

import com.bstek.dorado.annotation.DataProvider;
import com.bstek.dorado.annotation.DataResolver;

@Component
public class KdRoom {
	
	@Resource
	KdRoomManage manage;
	
	@DataProvider
	public List<Map<String,Object>> getAll(Map<String,Object> parameter) throws Exception{
		return manage.getAll(parameter);
	}

	@DataResolver
	public void saveAll(List<Map<String, Object>> datas,Map<String, Object> parameter) throws Exception {
		manage.saveAll(datas,parameter);
	}
	
}
