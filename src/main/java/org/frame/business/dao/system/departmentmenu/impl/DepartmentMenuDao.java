package org.frame.business.dao.system.departmentmenu.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.frame.business.dao.system.departmentmenu.IDepartmentMenuDao;
import org.frame.business.model.system.DepartmentMenu;
import org.frame.repository.sql.model.Page;
import org.frame.web.dao.BaseDao;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;


@Repository("departmentMenuDao")
@Scope("singleton")
public class DepartmentMenuDao extends BaseDao implements IDepartmentMenuDao {
	
	@SuppressWarnings("unused")
	private Log logger = LogFactory.getLog(DepartmentMenuDao.class);
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Integer> abandon(List<Map<String, Object>> parameter) {
		StringBuffer sbufSql = new StringBuffer(SQL_ABANDON_TEMPLET);
		sbufSql.append(" and (? is null or id = ?)");
		sbufSql.append(" and (? is null or department = ?)");
		sbufSql.append(" and (? is null or menu = ?)");

		List<Object[]> parameters = new ArrayList<Object[]>();
		for (Map<String, Object> map : parameter) {
			parameters.add(new Object[]{map.get("flag"), map.get("id"), map.get("id"), map.get("departmentid"), map.get("departmentid"), map.get("menuid"), map.get("menuid")});
		}
		
		return (List<Integer>) batchExecute(sbufSql.toString(), parameters);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Integer> delete(List<Map<String, Object>> parameter) {
		StringBuffer sbufSql = new StringBuffer(SQL_DELETE_TEMPLET);
		sbufSql.append(" and (? is null or id = ?)");
		sbufSql.append(" and (? is null or department = ?)");
		sbufSql.append(" and (? is null or menu = ?)");

		List<Object[]> parameters = new ArrayList<Object[]>();
		for (Map<String, Object> map : parameter) {
			parameters.add(new Object[]{map.get("id"), map.get("id"), map.get("departmentid"), map.get("departmentid"), map.get("menuid"), map.get("menuid")});
		}
		
		return (List<Integer>)  batchExecute(sbufSql.toString(), parameters);
	}
	
	@Override
	public DepartmentMenu find(Long id) {
		StringBuffer sbufSql = new StringBuffer(SQL_SELECT_TEMPLET);
		sbufSql.append(" and a.id = ?");
		
		Object[] parameters = {id};

		return (DepartmentMenu) find(sbufSql.toString(), parameters, new DepartmentMenuMapper());
	}

	@Override
	public Long insert(DepartmentMenu departmentMenu) {
		StringBuffer sbufSql = new StringBuffer(SQL_INSERT_TEMPLET);
		Object parameters[] = new Object[4];
		parameters[0] = departmentMenu.getId();
		parameters[1] = departmentMenu.getDepartment();
		parameters[2] = departmentMenu.getMenu();
		parameters[3] = departmentMenu.getFlag();
		
		return (Long) insert(sbufSql.toString(), parameters);
	}

	@Override
	public Page pagination(Long departmentid, String department, Long menuid, String menu, Integer flag, Page page) {
		StringBuffer sbufSql = new StringBuffer(SQL_SELECT_TEMPLET);
		sbufSql.append(" and (? is null or a.department = ?)");
		sbufSql.append(" and (? is null or a.menu = ?)");
		sbufSql.append(" and (? is null or a.flag = ?)");
		sbufSql.append(" and (? is null or c.title like ?)");
		sbufSql.append(" and (? is null or d.title like ?)");
		sbufSql.append(" order by a.id desc");
		
		Object[] parameters = {departmentid, departmentid, menuid, menuid, flag, flag, department, "%" + department + "%", menu, "%" + menu + "%"};
		
		return pagination(sbufSql.toString(), parameters, new DepartmentMenuMapper(), page);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<DepartmentMenu> select(Long departmentid, String department, Long menuid, String menu, Integer flag) {
		StringBuffer sbufSql = new StringBuffer(SQL_SELECT_TEMPLET);
		sbufSql.append(" and (? is null or a.department = ?)");
		sbufSql.append(" and (? is null or a.menu = ?)");
		sbufSql.append(" and (? is null or a.flag = ?)");
		sbufSql.append(" and (? is null or c.title like ?)");
		sbufSql.append(" and (? is null or d.title like ?)");
		
		Object[] parameters = {departmentid, departmentid, menuid, menuid, flag, flag, department, "%" + department + "%", menu, "%" + menu + "%"};
		
		return (List<DepartmentMenu>) select(sbufSql.toString(), parameters, new DepartmentMenuMapper());
	}

	@Override
	public Integer update(DepartmentMenu departmentMenu) {
		StringBuffer sbufSql = new StringBuffer(SQL_UPDATE_TEMPLET);
		sbufSql.append(" and id = ?");

		Object parameters[] = new Object[4];
		parameters[0] = departmentMenu.getDepartment();
		parameters[1] = departmentMenu.getMenu();
		parameters[2] = departmentMenu.getFlag();
		parameters[3] = departmentMenu.getId();

		return update(sbufSql.toString(), parameters);
	}
	
	@SuppressWarnings({ "rawtypes" })
	protected class DepartmentMenuMapper implements RowMapper {  
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {  
			DepartmentMenu departmentMenu = new DepartmentMenu();  
			departmentMenu.setId(rs.getLong("id"));
			departmentMenu.setDepartment(rs.getLong("department"));
			departmentMenu.setDepartmentname(rs.getString("departmentname"));
			departmentMenu.setMenu(rs.getLong("menu"));
			departmentMenu.setMenuname(rs.getString("menuname"));
			departmentMenu.setFlag(rs.getInt("flag"));
			departmentMenu.setFlagmeaning(rs.getString("flagmeaning"));

			return departmentMenu;  
		}
	}

}
