package org.frame.business.dao.system.department.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.frame.business.constant.IBusinessConstant;
import org.frame.business.dao.system.department.IDepartmentDao;
import org.frame.business.model.system.Department;
import org.frame.business.system.Config;
import org.frame.common.constant.ISymbolConstant;
import org.frame.common.lang.StringHelper;
import org.frame.repository.sql.model.Page;
import org.frame.web.dao.BaseDao;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;


@Repository("departmentDao")
@Scope("singleton")
public class DepartmentDao extends BaseDao implements IDepartmentDao {
	
	private Log logger = LogFactory.getLog(DepartmentDao.class);
	
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
	public Department find(Long id) {
		StringBuffer sbufSql = new StringBuffer(SQL_SELECT_TEMPLET);
		sbufSql.append(" and a.id = ?");
		
		Object[] parameters = {id};
		
		return (Department) find(sbufSql.toString(), parameters, new DepartmentMapper());
	}

	@Override
	public Long insert(Department department) {
		StringBuffer sbufSql = new StringBuffer(SQL_INSERT_TEMPLET);
		
		Object parameters[] = new Object[7];
		parameters[0] = department.getId();
		parameters[1] = department.getParentid();
		parameters[2] = department.getRank();
		parameters[3] = department.getFullname();
		parameters[4] = department.getTitle();
		parameters[5] = department.getFlag();
		parameters[6] = department.getRemark();
		
		return (Long) insert(sbufSql.toString(), parameters);
	}

	@Override
	public Page pagination(Long parentid, String parentname, String title, Integer flag, Page page) {
		StringBuffer sbufSql = new StringBuffer(SQL_SELECT_TEMPLET);
		sbufSql.append(" and (? is null or a.parentid = ?)");
		sbufSql.append(" and (? is null or b.title like ?)");
		sbufSql.append(" and (? is null or a.title like ?)");
		sbufSql.append(" and (? is null or a.flag = ?)");
		sbufSql.append(" order by id desc");

		Object[] parameters = {parentid, parentid, parentname, "%" + parentname + "%", title, "%" + title + "%", flag, flag};
		
		return pagination(sbufSql.toString(), parameters, new DepartmentMapper(), page);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Department> select(Long userid) {
		String pattern = new Config(IBusinessConstant.DEFAULT_CONFIG_PROPERTIES).get(IBusinessConstant.RELATED_TYPE_DEPARTMENT).toLowerCase();
		if (StringHelper.isNull(pattern)) pattern = ISymbolConstant.FLAG_SINGLE;
		
		StringBuffer sbufSql = new StringBuffer("");
		
		if (ISymbolConstant.FLAG_SINGLE.equals(pattern)) {
			sbufSql = new StringBuffer(SQL_USER_DEPARTMENT_SINGLE_TEMPLET);
			sbufSql.append(" and a.flag = ?");
			sbufSql.append(" and (? is null or b.id = ?)");
		} else if (ISymbolConstant.FLAG_MULTI.equals(pattern)) {
			sbufSql = new StringBuffer(SQL_USER_DEPARTMENT_MULTI_TEMPLET);
			sbufSql.append(" and a.flag = ?");
			sbufSql.append(" and (? is null or b.user = ?)");
		} else {
			logger.error("unknown department pattern.");
			return null;
		}
		
		Object[] parameters = {ISymbolConstant.FLAG_AVAILABLE, userid, userid};
		return (List<Department>) select(sbufSql.toString(), parameters, new DepartmentMapper());
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Department> select(Long parentid, String parentname, String title, Integer flag) {
		StringBuffer sbufSql = new StringBuffer(SQL_SELECT_TEMPLET);
		sbufSql.append(" and (? is null or a.parentid = ?)");
		sbufSql.append(" and (? is null or b.title like ?)");
		sbufSql.append(" and (? is null or a.title like ?)");
		sbufSql.append(" and (? is null or a.flag = ?)");

		Object[] parameters = {parentid, parentid, parentname, "%" + parentname + "%", title, "%" + title + "%", flag, flag};
		
		return (List<Department>) select(sbufSql.toString(), parameters, new DepartmentMapper());
	}

	@Override
	public Integer update(Department department) {
		StringBuffer sbufSql = new StringBuffer(SQL_UPDATE_TEMPLET);
		sbufSql.append(" and id = ?");

		Object parameters[] = new Object[7];
		parameters[0] = department.getParentid();
		parameters[1] = department.getRank();
		parameters[2] = department.getFullname();
		parameters[3] = department.getTitle();
		parameters[4] = department.getFlag();
		parameters[5] = department.getRemark();
		parameters[6] = department.getId();
		
		return update(sbufSql.toString(), parameters);
	}
	
	@SuppressWarnings({ "rawtypes" })
	public class DepartmentMapper implements RowMapper {  
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {  
			Department department = new Department();  
			department.setId(rs.getLong("id"));
			department.setParentid(rs.getLong("parentid"));
			department.setParentname(rs.getString("parentname"));
			department.setRank(rs.getLong("rank"));
			department.setFullname(rs.getString("fullname"));
			department.setTitle(rs.getString("title"));
			department.setFlag(rs.getInt("flag"));
			department.setFlagmeaning(rs.getString("flagmeaning"));
			department.setRemark(rs.getString("remark"));

			return department;  
		} 
	}
	
}
