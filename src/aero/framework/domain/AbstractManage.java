package aero.framework.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import aero.framework.utils.CommonUtil;
import aero.framework.utils.JdbcUtil;

import com.bstek.dorado.data.entity.EntityState;
import com.bstek.dorado.data.entity.EntityUtils;

@Component
public abstract class AbstractManage {
	
	private final int INSERT_STATUS = 1;
	private final int UPDATE_STATUS = 2;
	private final int DELETE_STATUS = 3;
	
	@Resource
	protected JdbcUtil jdbcUtil;
	
	@Resource
	protected CommonUtil commonUtil;
	
	/**
	 * 获取要操作的数据库表的名称
	 * 用以区分不同DataType对应的不同的数据表
	 */
	protected abstract String getTableName(int index);
	
	/**
	 * 获取要操作的数据库表的主键
	 * 用以区分不同DataType对应的不同的数据表
	 */
	protected abstract String getTableKeys(int index);
	
	/**
	 * 设置不存在于要操作的数据库表的字段
	 * 用以区分不同DataType对应的不同的数据表
	 */
	protected abstract Set<String> getExcludeFields(int index);
	
	/**
	 * 新增前进行的操作
	 * 用以区分不同DataType对应的不同的数据表
	 */
	protected abstract void beforeInsert(Map<String, Object> data, int index) throws Exception;
	/**
	 * 更新前进行的操作
	 * 用以区分不同DataType对应的不同的数据表
	 */
	protected abstract void beforeUpdate(Map<String, Object> data, int index) throws Exception;
	/**
	 * 删除前进行的操作
	 * 用以区分不同DataType对应的不同的数据表
	 */
	protected abstract void beforeDelete(Map<String, Object> data, int index) throws Exception;

	/**
	 * 新增后进行的操作
	 * 用以区分不同DataType对应的不同的数据表
	 */
	protected void afterInsert(Map<String, Object> data, int index) throws Exception{};
	/**
	 * 更新后进行的操作
	 * 用以区分不同DataType对应的不同的数据表
	 */
	protected void afterUpdate(Map<String, Object> data, int index) throws Exception{};
	/**
	 * 删除后进行的操作
	 * 用以区分不同DataType对应的不同的数据表
	 */
	protected void afterDelete(Map<String, Object> data, int index) throws Exception{};
	
	/** 
	 * 针对并发，更新或删除记录前，需要校验哪些字段
	 */ 
	protected String[] getCheckFields(int index){
	    return null;
	}
	
	/**
	 * 针对并发的校验方法
	 */
	private void checkConcurrency (Map<String, Object> data, int index)throws Exception {
        String[] fields = getCheckFields(index);
        if (fields == null || fields.length == 0)
            return;
        for (String key : fields)
        {
            if(data.get(key)==null) return;
        }
        List<Object> args = new ArrayList<Object>();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT COUNT(*) FROM ").append(getTableName(index)).append(" WHERE ").append(this.getTableKeys(index)).append(" = ? AND ");
        args.add(data.get(this.getTableKeys(index)));
        for (String key : fields)
        {
            sql.append(key).append("=? AND ");
            args.add(data.get(key));
        }
        sql.append(" 1=1 ");
        int num = jdbcUtil.getJdbcTemplate().queryForInt(sql.toString(), args.toArray());
        if (num == 0)
            throw new Exception("数据已产生后道处理，请刷新页面以获取最新数据！");
	}
	
	/**
	 * 对数据库进行CURD处理的方法
	 * 
	 * @param datas
	 * @throws Exception
	 */
	public void saveAll(List<Map<String, Object>> datas,Map<String, Object> parameter) throws Exception {
		int index = -1;
		if(parameter!=null && !parameter.isEmpty()){
			String i = (String)parameter.get("index");
			index = Integer.valueOf(i);
		}
		for (Map<String, Object> data:datas) {
			saveData(data, index);
		}
	}
	
	protected void saveData(Map<String, Object> data,int index) throws Exception{
		EntityState state = EntityUtils.getState(data);
		if (EntityState.DELETED == state) {
			this.delete(data, index);
		} else if (EntityState.MODIFIED == state) {
			this.update(data, index);
		} else{ // if (EntityState.NEW == state) {
			this.insert(data, index);
		}
	}
	
	private void insert(Map<String, Object> data, int index) throws Exception {
		this.beforeInsert(data, index);
		Object[] ps = this.getSql(data, INSERT_STATUS, index);
		jdbcUtil.getJdbcTemplate().update((String) ps[0], (Object[]) ps[1]);
		afterInsert(data, index);
	}

	private void update(Map<String, Object> data, int index) throws Exception {
	    this.checkConcurrency(data, index);
		this.beforeUpdate(data, index);
		Object[] ps = this.getSql(data, UPDATE_STATUS, index);
		jdbcUtil.getJdbcTemplate().update((String) ps[0], (Object[]) ps[1]);
		afterUpdate(data, index);
	}

	private void delete(Map<String, Object> data, int index) throws Exception {
	    this.checkConcurrency(data, index);
		this.beforeDelete(data, index);
		Object[] ps = this.getSql(data, DELETE_STATUS, index);
		jdbcUtil.getJdbcTemplate().update((String) ps[0], (Object[]) ps[1]);
		afterDelete(data, index);
	}

	private Object[] getSql(Map<String, Object> data, int type, int index) throws Exception {
		//定义信息
		String table = this.getTableName(index);
		if(StringUtils.isEmpty(table)){
			throw new Exception("操作异常：未指定数据库表！");
		}
		String pkFiled = this.getTableKeys(index);
		if(StringUtils.isEmpty(pkFiled)){
			throw new Exception("操作异常：未指定数据库表主键！");
		}
		Set<String> ef = this.getExcludeFields(index);
		
		long ctm = System.currentTimeMillis();
		StringBuffer sql = new StringBuffer(200);
		List<Object> args = new ArrayList<Object>();
		switch (type) {
		case INSERT_STATUS:
			sql.append("INSERT INTO "+table+" (");
			StringBuffer values = new StringBuffer(" VALUES(");
			for (String key:data.keySet()) {
				if(ef!=null && ef.contains(key)){
					continue;
				}
				sql.append(key+",");
				values.append("?,");
				args.add(data.get(key));
			}
			sql.deleteCharAt(sql.length()-1);
			sql.append(")").append(values.deleteCharAt(values.length()-1)+")");
			break;
		case UPDATE_STATUS:
			sql.append("UPDATE "+table+" SET ");
			for (String key:data.keySet()) {
				if(ef!=null && ef.contains(key)){
					continue;
				}
				sql.append(key+" = ? ,");
				args.add(data.get(key));
			}
			sql.deleteCharAt(sql.length()-1);
			sql.append( " WHERE "+pkFiled+" = ? AND "+ctm+"="+ctm);
			args.add(data.get(pkFiled));
			break;
		case DELETE_STATUS:
			sql.append("DELETE FROM " +table + " WHERE "+pkFiled+" = ? AND "+ctm+"="+ctm);
			args.add(data.get(pkFiled));
			break;
		}
		return new Object[] { sql.toString(), args.toArray() };
	}
}
