package org.frame.business.dao.system.userrole.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.frame.business.dao.system.userrole.IUserRoleDao;
import org.frame.business.model.system.UserRole;
import org.frame.repository.sql.model.Page;
import org.frame.web.dao.BaseDao;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;


@Repository("userRoleDao")
@Scope("singleton")
public class UserRoleDao extends BaseDao implements IUserRoleDao {
	
	@SuppressWarnings("unused")
	private Log logger = LogFactory.getLog(UserRoleDao.class);
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Integer> abandon(List<Map<String, Object>> parameter) {
		StringBuffer sbufSql = new StringBuffer(SQL_ABANDON_TEMPLET);
		sbufSql.append(" and (? is null or id = ?)");
		sbufSql.append(" and (? is null or role = ?)");
		sbufSql.append(" and (? is null or user = ?)");

		List<Object[]> parameters = new ArrayList<Object[]>();
		for (Map<String, Object> map : parameter) {
			parameters.add(new Object[]{map.get("flag"), map.get("id"), map.get("id"), map.get("roleid"), map.get("roleid"), map.get("userid"), map.get("userid")});
		}
		
		return (List<Integer>) batchExecute(sbufSql.toString(), parameters);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Integer> delete(List<Map<String, Object>> parameter) {
		StringBuffer sbufSql = new StringBuffer(SQL_DELETE_TEMPLET);
		sbufSql.append(" and (? is null or id = ?)");
		sbufSql.append(" and (? is null or role = ?)");
		sbufSql.append(" and (? is null or user = ?)");

		List<Object[]> parameters = new ArrayList<Object[]>();
		for (Map<String, Object> map : parameter) {
			parameters.add(new Object[]{map.get("id"), map.get("id"), map.get("roleid"), map.get("roleid"), map.get("userid"), map.get("userid")});
		}
		
		return (List<Integer>) batchExecute(sbufSql.toString(), parameters);
	}
	
	@Override
	public UserRole find(Long id) {
		StringBuffer sbufSql = new StringBuffer(SQL_SELECT_TEMPLET);
		sbufSql.append(" and a.id = ?");
		
		Object[] parameters = {id};
		
		return (UserRole) find(sbufSql.toString(), parameters, new UserRoleMapper());
	}

	@Override
	public Long insert(UserRole userRole) {
		StringBuffer sbufSql = new StringBuffer(SQL_INSERT_TEMPLET);
		
		Object parameters[] = new Object[4];
		parameters[0] = userRole.getId();
		parameters[1] = userRole.getUser();
		parameters[2] = userRole.getRole();
		parameters[3] = userRole.getFlag();
		
		return (Long) insert(sbufSql.toString(), parameters);
	}

	@Override
	public Page pagination(Long userid, String user, Long roleid, String role, Integer flag, Page page) {
		StringBuffer sbufSql = new StringBuffer(SQL_SELECT_TEMPLET);
		sbufSql.append(" and (? is null or a.user = ?)");
		sbufSql.append(" and (? is null or a.role = ?)");
		sbufSql.append(" and (? is null or a.flag = ?)");
		sbufSql.append(" and (? is null or c.title like ?)");
		sbufSql.append(" and (? is null or d.title like ?)");
		sbufSql.append(" order by a.id desc");
		
		Object[] parameters = {userid, userid, roleid, roleid, flag, flag, user, "%" + user + "%", role, "%" + role + "%"};
		
		return pagination(sbufSql.toString(), parameters, new UserRoleMapper(), page);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<UserRole> select(Long userid, String user, Long roleid, String role, Integer flag) {
		StringBuffer sbufSql = new StringBuffer(SQL_SELECT_TEMPLET);
		sbufSql.append(" and (? is null or a.user = ?)");
		sbufSql.append(" and (? is null or a.role = ?)");
		sbufSql.append(" and (? is null or a.flag = ?)");
		sbufSql.append(" and (? is null or c.title like ?)");
		sbufSql.append(" and (? is null or d.title like ?)");
		
		Object[] parameters = {userid, userid, roleid, roleid, flag, flag, user, "%" + user + "%", role, "%" + role + "%"};
		
		return (List<UserRole>) select(sbufSql.toString(), parameters, new UserRoleMapper());
	}

	@Override
	public Integer update(UserRole userRole) {
		StringBuffer sbufSql = new StringBuffer(SQL_UPDATE_TEMPLET);
		sbufSql.append(" and id = ?");

		Object parameters[] = new Object[4];
		parameters[0] = userRole.getUser();
		parameters[1] = userRole.getRole();
		parameters[2] = userRole.getFlag();
		parameters[3] = userRole.getId();

		return update(sbufSql.toString(), parameters);
	}
	
	@SuppressWarnings({ "rawtypes" })
	protected class UserRoleMapper implements RowMapper {  
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {  
			UserRole userRole = new UserRole();  
			userRole.setId(rs.getLong("id"));
			userRole.setUser(rs.getLong("user"));
			userRole.setUsername(rs.getString("username"));
			userRole.setRole(rs.getLong("role"));
			userRole.setRolename(rs.getString("rolename"));
			userRole.setFlag(rs.getInt("flag"));
			userRole.setFlagmeaning(rs.getString("flagmeaning"));
			
			return userRole;
		}
	}

}
