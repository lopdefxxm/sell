package aero.framework.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelHeader {
	
	List<Map<String, Object>> mListHeader;

	//EXCEL的行头 每次自动生成
	private List<String> CODES = new ArrayList<>();
	private List<String> LABELS = new ArrayList<>();
	private Map<String,Map<String,String>> mappings = new HashMap<String, Map<String,String>>();
	
	/**
	 * Excel表头类
	 * 
	 * @param mList
	 *            前台的表头封装成的list
	 * @author WangTianHai
	 */
	@SuppressWarnings("unchecked")
	public ExcelHeader(List<Map<String, Object>> mList) {
		this.mListHeader = mList;
		this.CODES.clear();
		this.LABELS.clear();
		this.mappings.clear();
		
		for(Map<String, Object> m : mList){
			String code = (String)m.get("code");
			String name = (String)m.get("name");
			CODES.add(code);
			LABELS.add(name);
			if(m.containsKey("mapping")){
				Map<String,String> m1 = new HashMap<>();
				List<Map<String,String>> maps = (List<Map<String,String>>)m.get("mapping");
				for(Map<String,String> map : maps){
					m1.put(map.get("key"), map.get("value"));
				}
				mappings.put(code, m1);
			}
			
		}
		
	}

	public List<String> getCODES() {
		return CODES;
	}

	public List<String> getLABELS() {
		return LABELS;
	}

	public Map<String, Map<String, String>> getMappings() {
		return mappings;
	}

	/**
	 * 根据key获取 此列的位置
	 * 
	 * @param strkey
	 *            表头key
	 * @return 如果返回-1 表示无法根据此Key获取位置
	 * @author WangTianHai
	 */
	public Integer getIndexByKey(String strkey) {
		int index = -1;
		for (int i = 0; i < this.mListHeader.size(); i++) {
			Map<String, Object> colHeader = mListHeader.get(i);
			if (strkey.equals(colHeader.get("code"))) {
				index = i;
				break;
			} else {
				continue;
			}
		}
		return index;
	}

	/**
	 * 根据位置获取Key
	 * 
	 * @param index
	 *            表头列位置
	 * @return 返回null 则表示 无法根据此位置找到对应的key
	 * @author WangTianHai
	 */
	public String getKeyByIndex(Integer index) {
		Map<String, Object> colHeader = mListHeader.get(index);
		return (String)colHeader.get("code");
	}

	
	/**
	 * 根据位置获取Key
	 * 
	 * @param index
	 *            表头列位置
	 * @return 返回null 则表示 无法根据此位置找到对应的key
	 * @author WangTianHai
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getMappingsByIndex(Integer index) {
		Map<String, Object> colHeader = mListHeader.get(index);
		return (List<Map<String, Object>>)colHeader.get("mapping");
	}
	/**
	 * 根据key获取表头列标题
	 * 
	 * @param strkey
	 *            表头列Key
	 * @return 如返回null则表示无法获取该 key 对应的表头标题
	 * @author WangTianHai
	 */
	public String getValueByKey(String strkey) {
		String value = null;
		for (int i = 0; i < this.mListHeader.size(); i++) {
			Map<String, Object> colHeader = mListHeader.get(i);
			if (strkey.equals(colHeader.get("code"))) {
				value = (String)colHeader.get("name");
				break;
			} else {
				continue;
			}
		}

		return value;
	}

	/**
	 * 根据表头标题获取key
	 * 
	 * @param value
	 *            此列表头标题
	 * @return 如返回null则表示无法获取该表头标题 对应的 key
	 * @author WangTianHai
	 */
	public String getKeyByValue(String value) {
		String Key = null;
		for (int i = 0; i < this.mListHeader.size(); i++) {
			Map<String, Object> colHeader = mListHeader.get(i);
			if (value.equals(colHeader.get("name"))) {
				Key = (String)colHeader.get("code");
				break;
			} else {
				continue;
			}
		}

		return Key;
	}

	/**
	 * 根据位置获取表头标题
	 * 
	 * @param index
	 *            此列位置
	 * @return 如返回null则表示无法获取该位置 对应的 表头标题
	 * @author WangTianHai
	 */
	public String getValueByIndex(Integer index) {
		return this.getValueByKey(this.getKeyByIndex(index));
	}

	/**
	 * 根据表头标题获取位置
	 * 
	 * @param value
	 *            表头标题
	 * @return 如果返回-1 表示无法根据此表头标题 获取对应的位置
	 * @author WangTianHai
	 */
	public Integer getIndexByValue(String value) {
		return this.getIndexByKey(this.getKeyByValue(value));

	}

	/**
	 * 获取表头列数
	 * 
	 * @return
	 * @author WangTianHai
	 */
	public Integer getColSize() {
		if (this.mListHeader == null)
			return 0;
		return this.mListHeader.size();

	}

}
