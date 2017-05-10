package aero.kd.control.biz;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import aero.kd.manage.biz.KdBalInShowManage;

import com.bstek.dorado.annotation.DataProvider;
import com.bstek.dorado.annotation.Expose;

@Component
public class KdBalInShow{
	
	@Resource
	KdBalInShowManage manage;

	@Expose
	@DataProvider
	public List<Map<String,Object>> getAll(Map<String,Object> parameter)throws Exception{
		return manage.getAll(parameter);
	}

}
