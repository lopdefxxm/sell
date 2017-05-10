package aero.framework.utils;

import java.util.Collection;
import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.bstek.dorado.data.provider.Page;

@Component
public class JdbcUtil {

	@Resource
	private JdbcTemplate jdbcTemplate;
	
	public JdbcTemplate getJdbcTemplate(){
		return this.jdbcTemplate;
	}
	
	public String getSequence(){
		return getSequence("SYS_DOC_ID");
	}
	
	public String getSequence(String seqName){
//		String sql = "UPDATE SEQUENCE SET CURRENT = CURRENT + STEP WHERE SEQ_NAME = ? ";
//		this.getJdbcTemplate().update(sql,seqName);
		return UUID.randomUUID().toString();
	}
	
	public int getCurrentSeq(String seqName){
		String sql = "SELECT CURRENT FROM SEQUENCE WHERE SEQ_NAME = ? ";
		return this.getJdbcTemplate().queryForInt(sql,seqName);
	}
	/*
	public String getSequence(){
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		return sdf.format(date);
	}
	*/
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void paginationQuery(String sql,Page<?> page,Object...args){
		String countSql = "SELECT COUNT(*) FROM ("+sql+") C_COUNT ";
		int count = getJdbcTemplate().queryForInt(countSql, args);
		page.setEntityCount(count);
		int begin = page.getFirstEntityIndex();
		int end  = begin + page.getPageSize();
		sql += " LIMIT "+begin+" ,"+end+" ";
		Collection datas = getJdbcTemplate().queryForList(sql, args);
		page.setEntities(datas);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void paginationQuery(String sql,Page<?> page){
		String countSql = "SELECT COUNT(*) FROM ("+sql+") C_COUNT ";
		int count = getJdbcTemplate().queryForInt(countSql);
		page.setEntityCount(count);
		int begin = page.getFirstEntityIndex();
		int end  = begin + page.getPageSize();
		sql += " LIMIT "+begin+" ,"+end+" ";
		Collection datas = getJdbcTemplate().queryForList(sql);
		page.setEntities(datas);
	}
}
