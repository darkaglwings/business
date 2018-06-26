package org.frame.business.dao.system.userdepartment.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.frame.business.dao.system.userdepartment.IUserDepartmentDao;
import org.frame.business.model.system.UserDepartment;
import org.frame.repository.sql.model.Page;
import org.frame.web.dao.BaseDao;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;


@Repository("userDepartmentDao")
@Scope("singleton")
public class UserDepartmentDao extends BaseDao implements IUserDepartmentDao {
	
	@SuppressWarnings("unused")
	private Log logger = LogFactory.getLog(UserDepartmentDao.class);
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Integer> abandon(List<Map<String, Object>> parameter) {
		StringBuffer sbufSql = new StringBuffer(SQL_ABANDON_TEMPLET);
		sbufSql.append(" and (? is null or id = ?)");
		sbufSql.append(" and (? is null or department = ?)");
		sbufSql.append(" and (? is null or user = ?)");

		List<Object[]> parameters = new ArrayList<Object[]>();
		for (Map<String, Object> map : parameter) {
			parameters.add(new Object[]{map.get("flag"), map.get("id"), map.get("id"), map.get("departmentid"), map.get("departmentid"), map.get("userid"), map.get("userid")});
		}
		
		return (List<Integer>) batchExecute(sbufSql.toString(), parameters);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Integer> delete(List<Map<String, Object>> parameter) {
		StringBuffer sbufSql = new StringBuffer(SQL_DELETE_TEMPLET);
		sbufSql.append(" and (? is null or id = ?)");
		sbufSql.append(" and (? is null or department = ?)");
		sbufSql.append(" and (? is null or user = ?)");

		List<Object[]> parameters = new ArrayList<Object[]>();
		for (Map<String, Object> map : parameter) {
			parameters.add(new Object[]{map.get("id"), map.get("id"), map.get("departmentid"), map.get("departmentid"), map.get("userid"), map.get("userid")});
		}
		
		return (List<Integer>) batchExecute(sbufSql.toString(), parameters);
	}
	
	@Override
	public UserDepartment find(Long id) {
		StringBuffer sbufSql = new StringBuffer(SQL_SELECT_TEMPLET);
		sbufSql.append(" and a.id = ?");
		
		Object[] parameters = {id};
		
		return (UserDepartment) find(sbufSql.toString(), parameters, new UserDepartmentMapper());
	}

	@Override
	public Long insert(UserDepartment userDepartment) {
		StringBuffer sbufSql = new StringBuffer(SQL_INSERT_TEMPLET);
		
		Object parameters[] = new Object[4];
		parameters[0] = userDepartment.getId();
		parameters[1] = userDepartment.getUser();
		parameters[2] = userDepartment.getDepartment();
		parameters[3] = userDepartment.getFlag();
		
		return (Long) insert(sbufSql.toString(), parameters);
	}
	
	@Override
	public Page pagination(Long userid, String user, Long departmentid, String department, Integer flag, Page page) {
		StringBuffer sbufSql = new StringBuffer(SQL_SELECT_TEMPLET);
		sbufSql.append(" and (? is null or a.user = ?)");
		sbufSql.append(" and (? is null or a.department = ?)");
		sbufSql.append(" and (? is null or a.flag = ?)");
		sbufSql.append(" and (? is null or c.title like ?)");
		sbufSql.append(" and (? is null or d.title like ?)");
		sbufSql.append(" order by a.id desc");
		
		Object[] parameters = {userid, userid, departmentid, departmentid, flag, flag, user, "%" + user + "%", department, "%" + department + "%"};
		
		return pagination(sbufSql.toString(), parameters, new UserDepartmentMapper(), page);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<UserDepartment> select(Long userid, String user, Long departmentid, String department, Integer flag) {
		StringBuffer sbufSql = new StringBuffer(SQL_SELECT_TEMPLET);
		sbufSql.append(" and (? is null or a.user = ?)");
		sbufSql.append(" and (? is null or a.department = ?)");
		sbufSql.append(" and (? is null or a.flag = ?)");
		sbufSql.append(" and (? is null or c.title like ?)");
		sbufSql.append(" and (? is null or d.title like ?)");
		
		Object[] parameters = {userid, userid, departmentid, departmentid, flag, flag, user, "%" + user + "%", department, "%" + department + "%"};
		
		return (List<UserDepartment>) select(sbufSql.toString(), parameters, new UserDepartmentMapper());
	}

	@Override
	public Integer update(UserDepartment userDepartment) {
		StringBuffer sbufSql = new StringBuffer(SQL_UPDATE_TEMPLET);
		sbufSql.append(" and id = ?");

		Object parameters[] = new Object[4];
		parameters[0] = userDepartment.getUser();
		parameters[1] = userDepartment.getDepartment();
		parameters[2] = userDepartment.getFlag();
		parameters[3] = userDepartment.getId();

		return update(sbufSql.toString(), parameters);
	}
	
	@SuppressWarnings({ "rawtypes" })
	protected class UserDepartmentMapper implements RowMapper {  
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {  
			UserDepartment userDepartment = new UserDepartment();  
			userDepartment.setId(rs.getLong("id"));
			userDepartment.setUser(rs.getLong("user"));
			userDepartment.setUsername(rs.getString("username"));
			userDepartment.setDepartment(rs.getLong("department"));
			userDepartment.setDepartmentname(rs.getString("departmentname"));
			userDepartment.setFlag(rs.getInt("flag"));
			userDepartment.setFlagmeaning(rs.getString("flagmeaning"));
			
			return userDepartment;  
		}
	}

}
