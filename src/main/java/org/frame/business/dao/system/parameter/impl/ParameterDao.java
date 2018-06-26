package org.frame.business.dao.system.parameter.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.frame.business.dao.system.parameter.IParameterDao;
import org.frame.business.model.system.Parameter;
import org.frame.repository.sql.model.Page;
import org.frame.web.dao.BaseDao;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;


@Repository("parameterDao")
@Scope("singleton")
public class ParameterDao extends BaseDao implements IParameterDao {
	
	@SuppressWarnings("unused")
	private Log logger = LogFactory.getLog(ParameterDao.class);
	
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
	public Parameter find(Long id) {
		StringBuffer sbufSql = new StringBuffer(SQL_SELECT_TEMPLET);
		sbufSql.append(" and a.id = ?");
		
		Object[] parameters = {id};
		
		return (Parameter) find(sbufSql.toString(), parameters, new ParameterMapper());
	}

	@Override
	public Long insert(Parameter parameter) {
		StringBuffer sbufSql = new StringBuffer(SQL_INSERT_TEMPLET);
		
		Object parameters[] = new Object[5];
		parameters[0] = parameter.getId();
		parameters[1] = parameter.getTitle();
		parameters[2] = parameter.getCode();
		parameters[3] = parameter.getFlag();
		parameters[4] = parameter.getRemark();
		
		return (Long) insert(sbufSql.toString(), parameters);
	}

	@Override
	public Page pagination(String title, Integer flag, Page page) {
		StringBuffer sbufSql = new StringBuffer(SQL_SELECT_TEMPLET);
		sbufSql.append(" and (? is null or a.title like ?)");
		sbufSql.append(" and (? is null or a.flag = ?)");
		sbufSql.append(" order by id desc");

		Object[] parameters = {title, "%" + title + "%", flag, flag};
		
		return pagination(sbufSql.toString(), parameters, new ParameterMapper(), page);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Parameter> select(String title, Integer flag) {
		StringBuffer sbufSql = new StringBuffer(SQL_SELECT_TEMPLET);
		sbufSql.append(" and (? is null or a.title like ?)");
		sbufSql.append(" and (? is null or a.flag = ?)");

		Object[] parameters = {title, "%" + title + "%", flag, flag};
		
		return (List<Parameter>) select(sbufSql.toString(), parameters, new ParameterMapper());
	}

	@Override
	public Integer update(Parameter parameter) {
		StringBuffer sbufSql = new StringBuffer(SQL_UPDATE_TEMPLET);
		sbufSql.append(" and id = ?");

		Object parameters[] = new Object[5];
		parameters[0] = parameter.getTitle();
		parameters[1] = parameter.getCode();
		parameters[2] = parameter.getFlag();
		parameters[3] = parameter.getRemark();
		parameters[4] = parameter.getId();

		return update(sbufSql.toString(), parameters);
	}
	
	@SuppressWarnings({ "rawtypes" })
	protected class ParameterMapper implements RowMapper {  
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {  
			Parameter parameter = new Parameter();  
			parameter.setId(rs.getLong("id"));
			parameter.setTitle(rs.getString("title"));
			parameter.setCode(rs.getString("code"));
			parameter.setFlag(rs.getInt("flag"));
			parameter.setRemark(rs.getString("remark"));

			return parameter;  
		} 
	}
}
