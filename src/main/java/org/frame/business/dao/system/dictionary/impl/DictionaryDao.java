package org.frame.business.dao.system.dictionary.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.frame.business.dao.system.dictionary.IDictionaryDao;
import org.frame.business.model.system.Dictionary;
import org.frame.common.constant.ISymbolConstant;
import org.frame.repository.sql.model.Page;
import org.frame.web.dao.BaseDao;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;


@Repository("dictionaryDao")
@Scope("singleton")
public class DictionaryDao extends BaseDao implements IDictionaryDao {

	@SuppressWarnings("unused")
	private Log logger = LogFactory.getLog(DictionaryDao.class);
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Integer> abandon(List<Map<String, Object>> parameter) {
		StringBuffer sbufSql = new StringBuffer(SQL_ABANDON_TEMPLET);
		sbufSql.append(" and (? is null or id = ?)");

		List<Object[]> parameters = new ArrayList<Object[]>();
		for (Map<String, Object> map : parameter) {
			parameters.add(new Object[]{map.get("flag"), map.get("id"), map.get("id")});
		}
		
		return (List<Integer>) batchExecute(sbufSql.toString(), parameters);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Integer> delete(List<Map<String, Object>> parameter) {
		StringBuffer sbufSql = new StringBuffer(SQL_DELETE_TEMPLET);
		sbufSql.append(" and (? is null or id = ?)");
		
		List<Object[]> parameters = new ArrayList<Object[]>();
		for (Map<String, Object> map : parameter) {
			parameters.add(new Object[]{map.get("id"), map.get("id")});
		}
		
		return (List<Integer>) batchExecute(sbufSql.toString(), parameters);
	}

	@Override
	public Dictionary find(Long id) {
		StringBuffer sbufSql = new StringBuffer(SQL_SELECT_TEMPLET);
		sbufSql.append(" and a.id = ?");
		
		Object[] parameters = {id};
		
		return (Dictionary) find(sbufSql.toString(), parameters, new DictionaryMapper());
	}

	@SuppressWarnings("unchecked")
	public List<Dictionary> group() {
		StringBuffer sbufSql = new StringBuffer(SQL_SELECT_SORT_TEMPLET);
		sbufSql.append(" and a.flag = " + ISymbolConstant.FLAG_AVAILABLE);
		
		return (List<Dictionary>) select(sbufSql.toString(), new DictionarySortMapper());
	}
	
	@Override
	public Long insert(Dictionary dictionary) {
		StringBuffer sbufSql = new StringBuffer(SQL_INSERT_TEMPLET);
		
		Object parameters[] = new Object[8];
		parameters[0] = dictionary.getId();
		parameters[1] = dictionary.getParentid();
		parameters[2] = dictionary.getSort();
		parameters[3] = dictionary.getCode();
		parameters[4] = dictionary.getMeaning();
		parameters[5] = dictionary.getDisplay();
		parameters[6] = dictionary.getFlag();
		parameters[7] = dictionary.getRemark();
		
		return (Long) insert(sbufSql.toString(), parameters);
	}

	@Override
	public Page pagination(String parentname, String sort, Integer flag, Page page) {
		StringBuffer sbufSql = new StringBuffer(SQL_SELECT_TEMPLET);
		sbufSql.append(" and (? is null or b.meaning like ?)");
		sbufSql.append(" and (? is null or a.sort = ?)");
		sbufSql.append(" and (? is null or a.flag = ?)");
		sbufSql.append(" order by id desc");
		
		Object[] parameters = {parentname, "" + parentname + "", sort, sort, flag, flag};
		
		return pagination(sbufSql.toString(), parameters, new DictionaryMapper(), page);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Dictionary> select(Long parentid, String parentname, String sort, Integer flag) {
		StringBuffer sbufSql = new StringBuffer(SQL_SELECT_TEMPLET);
		sbufSql.append(" and (? is null or a.parentid = ?)");
		sbufSql.append(" and (? is null or b.meaning like ?)");
		sbufSql.append(" and (? is null or a.sort = ?)");
		sbufSql.append(" and (? is null or a.flag = ?)");
		sbufSql.append(" order by a.display asc");
		
		Object[] parameters = {parentid, parentid, parentname, "%" + parentname + "%", sort, sort, flag, flag};
		
		return (List<Dictionary>) select(sbufSql.toString(), parameters, new DictionaryMapper());
	}

	@Override
	public Integer update(Dictionary dictionary) {
		StringBuffer sbufSql = new StringBuffer(SQL_UPDATE_TEMPLET);
		sbufSql.append(" and id = ?");

		Object parameters[] = new Object[8];
		parameters[0] = dictionary.getParentid();
		parameters[1] = dictionary.getSort();
		parameters[2] = dictionary.getCode();
		parameters[3] = dictionary.getMeaning();
		parameters[4] = dictionary.getDisplay();
		parameters[5] = dictionary.getFlag();
		parameters[6] = dictionary.getRemark();
		parameters[7] = dictionary.getId();

		return update(sbufSql.toString(), parameters);
	}

	@SuppressWarnings({ "rawtypes" })
	protected class DictionaryMapper implements RowMapper {  
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {  
			Dictionary dictionary = new Dictionary();  
			dictionary.setId(rs.getLong("id"));
			dictionary.setParentid(rs.getLong("parentid"));
			dictionary.setParentname(rs.getString("parentname"));
			dictionary.setSort(rs.getString("sort"));
			dictionary.setCode(rs.getString("code"));
			dictionary.setMeaning(rs.getString("meaning"));
			dictionary.setDisplay(rs.getInt("display"));
			dictionary.setFlag(rs.getInt("flag"));
			dictionary.setFlagmeaning(rs.getString("flagmeaning"));
			dictionary.setRemark(rs.getString("remark"));

			return dictionary;  
		} 
	}
	
	@SuppressWarnings({ "rawtypes" })
	protected class DictionarySortMapper implements RowMapper {  
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {  
			Dictionary dictionary = new Dictionary();  
			dictionary.setSort(rs.getString("sort"));

			return dictionary;  
		} 
	}
	
}
