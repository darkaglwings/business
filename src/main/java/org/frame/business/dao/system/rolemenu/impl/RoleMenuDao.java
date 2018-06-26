package org.frame.business.dao.system.rolemenu.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.frame.business.dao.system.rolemenu.IRoleMenuDao;
import org.frame.business.model.system.RoleMenu;
import org.frame.repository.sql.model.Page;
import org.frame.web.dao.BaseDao;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;


@Repository("roleMenuDao")
@Scope("singleton")
public class RoleMenuDao extends BaseDao implements IRoleMenuDao {
	
	@SuppressWarnings("unused")
	private Log logger = LogFactory.getLog(RoleMenuDao.class);
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Integer> abandon(List<Map<String, Object>> parameter) {
		StringBuffer sbufSql = new StringBuffer(SQL_ABANDON_TEMPLET);
		sbufSql.append(" and (? is null or id = ?)");
		sbufSql.append(" and (? is null or role = ?)");
		sbufSql.append(" and (? is null or menu = ?)");

		List<Object[]> parameters = new ArrayList<Object[]>();
		for (Map<String, Object> map : parameter) {
			parameters.add(new Object[]{map.get("flag"), map.get("id"), map.get("id"), map.get("roleid"), map.get("roleid"), map.get("menuid"), map.get("menuid")});
		}
		
		return (List<Integer>) batchExecute(sbufSql.toString(), parameters);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Integer> delete(List<Map<String, Object>> parameter) {
		StringBuffer sbufSql = new StringBuffer(SQL_DELETE_TEMPLET);
		sbufSql.append(" and (? is null or id = ?)");
		sbufSql.append(" and (? is null or role = ?)");
		sbufSql.append(" and (? is null or menu = ?)");

		List<Object[]> parameters = new ArrayList<Object[]>();
		for (Map<String, Object> map : parameter) {
			parameters.add(new Object[]{map.get("id"), map.get("id"), map.get("roleid"), map.get("roleid"), map.get("menuid"), map.get("menuid")});
		}
		
		return (List<Integer>) batchExecute(sbufSql.toString(), parameters);
	}
	
	@Override
	public RoleMenu find(Long id) {
		StringBuffer sbufSql = new StringBuffer(SQL_SELECT_TEMPLET);
		sbufSql.append(" and a.id = ?");
		
		Object[] parameters = {id};
		
		return (RoleMenu) find(sbufSql.toString(), parameters, new RoleMenuMapper());
	}

	@Override
	public Long insert(RoleMenu roleMenu) {
		StringBuffer sbufSql = new StringBuffer(SQL_INSERT_TEMPLET);
		
		Object parameters[] = new Object[4];
		parameters[0] = roleMenu.getId();
		parameters[1] = roleMenu.getRole();
		parameters[2] = roleMenu.getMenu();
		parameters[3] = roleMenu.getFlag();
		
		return (Long) insert(sbufSql.toString(), parameters);
	}

	@Override
	public Page pagination(Long roleid, String role, Long menuid, String menu, Integer flag, Page page) {
		StringBuffer sbufSql = new StringBuffer(SQL_SELECT_TEMPLET);
		sbufSql.append(" and (? is null or a.role = ?)");
		sbufSql.append(" and (? is null or a.menu = ?)");
		sbufSql.append(" and (? is null or a.flag = ?)");
		sbufSql.append(" and (? is null or c.title like ?)");
		sbufSql.append(" and (? is null or d.title like ?)");
		sbufSql.append(" order by a.id desc");
		
		Object[] parameters = {roleid, roleid, menuid, menuid, flag, flag, role, "%" + role + "%", menu, "%" + menu + "%"};
		
		return pagination(sbufSql.toString(), parameters, new RoleMenuMapper(), page);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<RoleMenu> select(Long roleid, String role, Long menuid, String menu, Integer flag) {
		StringBuffer sbufSql = new StringBuffer(SQL_SELECT_TEMPLET);
		sbufSql.append(" and (? is null or a.role = ?)");
		sbufSql.append(" and (? is null or a.menu = ?)");
		sbufSql.append(" and (? is null or a.flag = ?)");
		sbufSql.append(" and (? is null or c.title like ?)");
		sbufSql.append(" and (? is null or d.title like ?)");
		
		Object[] parameters = {roleid, roleid, menuid, menuid, flag, flag, role, "%" + role + "%", menu, "%" + menu + "%"};
		
		return (List<RoleMenu>) select(sbufSql.toString(), parameters, new RoleMenuMapper());
	}

	@Override
	public Integer update(RoleMenu roleMenu) {
		StringBuffer sbufSql = new StringBuffer(SQL_UPDATE_TEMPLET);
		sbufSql.append(" and id = ?");

		Object parameters[] = new Object[4];
		parameters[0] = roleMenu.getRole();
		parameters[1] = roleMenu.getMenu();
		parameters[2] = roleMenu.getFlag();
		parameters[3] = roleMenu.getId();

		return update(sbufSql.toString(), parameters);
	}
	
	@SuppressWarnings({ "rawtypes" })
	protected class RoleMenuMapper implements RowMapper {  
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {  
			RoleMenu roleMenu = new RoleMenu();  
			roleMenu.setId(rs.getLong("id"));
			roleMenu.setRole(rs.getLong("role"));
			roleMenu.setRolename(rs.getString("rolename"));
			roleMenu.setMenu(rs.getLong("menu"));
			roleMenu.setMenuname(rs.getString("menuname"));
			roleMenu.setFlag(rs.getInt("flag"));
			roleMenu.setFlagmeaning(rs.getString("flagmeaning"));
			
			return roleMenu;  
		}
	}

}
