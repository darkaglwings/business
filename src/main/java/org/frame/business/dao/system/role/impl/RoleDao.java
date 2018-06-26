package org.frame.business.dao.system.role.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.frame.business.dao.system.role.IRoleDao;
import org.frame.business.model.system.Role;
import org.frame.common.constant.ISymbolConstant;
import org.frame.repository.sql.model.Page;
import org.frame.web.dao.BaseDao;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;


@Repository("roleDao")
@Scope("singleton")
public class RoleDao extends BaseDao implements IRoleDao {
	
	@SuppressWarnings("unused")
	private Log logger = LogFactory.getLog(RoleDao.class);
	
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
	public Role find(Long id) {
		StringBuffer sbufSql = new StringBuffer(SQL_SELECT_TEMPLET);
		sbufSql.append(" and a.id = ?");
		
		Object[] parameters = {id};
		
		return (Role) find(sbufSql.toString(), parameters, new RoleMapper());
	}

	@Override
	public Long insert(Role role) {
		StringBuffer sbufSql = new StringBuffer(SQL_INSERT_TEMPLET);
		
		Object parameters[] = new Object[6];
		parameters[0] = role.getId();
		parameters[1] = role.getCode();
		parameters[2] = role.getTitle();
		parameters[3] = role.getRank();
		parameters[4] = role.getFlag();
		parameters[5] = role.getRemark();
		
		return (Long) insert(sbufSql.toString(), parameters);
	}

	@Override
	public Page pagination(String title, Long rank, Integer flag, Page page) {
		StringBuffer sbufSql = new StringBuffer(SQL_SELECT_TEMPLET);
		sbufSql.append(" and (? is null or a.title like ?)");
		sbufSql.append(" and (? is null or a.rank = ?)");
		sbufSql.append(" and (? is null or a.flag = ?)");
		sbufSql.append(" order by id desc");

		Object[] parameters = {title, "%" + title + "%", rank, rank, flag, flag};
		
		return pagination(sbufSql.toString(), parameters, new RoleMapper(), page);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Role> select(Long userid) {
		StringBuffer sbufSql = new StringBuffer(SQL_USER_ROLE_TEMPLET);
		
		Object[] parameters = {ISymbolConstant.FLAG_AVAILABLE, userid};
		
		return (List<Role>) select(sbufSql.toString(), parameters, new RoleMapper());
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Role> select(String title, Long rank, Integer flag) {
		StringBuffer sbufSql = new StringBuffer(SQL_SELECT_TEMPLET);
		sbufSql.append(" and (? is null or a.title like ?)");
		sbufSql.append(" and (? is null or a.rank = ?)");
		sbufSql.append(" and (? is null or a.flag = ?)");
		
		Object[] parameters = {title, "%" + title + "%", rank, rank, flag, flag};
		
		return (List<Role>) select(sbufSql.toString(), parameters, new RoleMapper());
	}

	@Override
	public Integer update(Role role) {
		StringBuffer sbufSql = new StringBuffer(SQL_UPDATE_TEMPLET);
		sbufSql.append(" and id = ?");

		Object parameters[] = new Object[6];
		parameters[0] = role.getCode();
		parameters[1] = role.getTitle();
		parameters[2] = role.getRank();
		parameters[3] = role.getFlag();
		parameters[4] = role.getRemark();
		parameters[5] = role.getId();

		return update(sbufSql.toString(), parameters);
	}
	
	@SuppressWarnings({ "rawtypes" })
	protected class RoleMapper implements RowMapper {  
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {  
			Role role = new Role();  
			role.setId(rs.getLong("id"));
			role.setCode(rs.getString("code"));
			role.setTitle(rs.getString("title"));
			role.setRank(rs.getLong("rank"));
			role.setFlag(rs.getInt("flag"));
			role.setFlagmeaning(rs.getString("flagmeaning"));
			role.setRemark(rs.getString("remark"));

			return role;  
		} 
	}

}
