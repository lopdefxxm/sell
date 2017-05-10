package aero.framework.utils;

import java.util.List;
import java.util.Map;

public class ExcelData {
	List<Map<String, Object>> mListBody;
	ExcelHeader mExcelHeader;
	/**
	 * Excel表体类
	 * @param mList 前台的表头封装成的list
	 * @author WangTianHai
	 */
	public ExcelData(List<Map<String, Object>> mList,ExcelHeader mExcelHeader) {
		this.mListBody = mList;
		this.mExcelHeader = mExcelHeader;
	}
	
	/**
	 * 根据位置获取值
	 * @param i 行位置 不含标题从0开始数
	 * @param j 列位置 从0开始数
	 * @return
	 * @author WangTianHai
	 */
	public Object getVlaueByPos(int i,int j){
		Object obj =null;
		Map<String, Object> rowdata =  mListBody.get(i);
		String code = mExcelHeader.getCODES().get(j);
		obj= rowdata.get(code);
		if(mExcelHeader.getMappings().containsKey(code)){
			obj = mExcelHeader.getMappings().get(code).get(obj);
		}
		return obj;	
	}
	/**
	 * 根据行位置和标题code获取值
	 * @param i 行位置 不含标题从0开始数
	 * @param key 对应的code
	 * @return
	 * @author WangTianHai
	 */
	public Object getVlaueByKey(int i,String key){
		Object obj =null;
		Map<String, Object> rowdata = mListBody.get(i);
		obj= rowdata.get(key);
		return obj;	
	}
	
	/**
	 * 根据行位置和名称获取值
	 * @param i 行位置 不含标题从0开始数
	 * @param colname 标题名称
	 * @return
	 * @author WangTianHai
	 */
	public Object getValueByColName(int i,String colname){
		Object obj =null;
		String key = mExcelHeader.getKeyByValue(colname);
		Map<String, Object> rowdata = mListBody.get(i);
		obj= rowdata.get(key);
		return obj;	
	}
	
	/**
	 * 获取数据行数
	 * @return
	 * @author WangTianHai
	 */
	public Integer getDataSize(){
		if(this.mListBody==null) return 0; 
		return this.mListBody.size() ;
		
	}
	
}
