package org.frame.business.dao.system.usermenu.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.frame.business.dao.system.usermenu.IUserMenuDao;
import org.frame.business.model.system.UserMenu;
import org.frame.repository.sql.model.Page;
import org.frame.web.dao.BaseDao;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;


@Repository("userMenuDao")
@Scope("singleton")
public class UserMenuDao extends BaseDao implements IUserMenuDao {
	
	@SuppressWarnings("unused")
	private Log logger = LogFactory.getLog(UserMenuDao.class);
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Integer> abandon(List<Map<String, Object>> parameter) {
		StringBuffer sbufSql = new StringBuffer(SQL_ABANDON_TEMPLET);
		sbufSql.append(" and (? is null or id = ?)");
		sbufSql.append(" and (? is null or menu = ?)");
		sbufSql.append(" and (? is null or user = ?)");

		List<Object[]> parameters = new ArrayList<Object[]>();
		for (Map<String, Object> map : parameter) {
			parameters.add(new Object[]{map.get("flag"), map.get("id"), map.get("id"), map.get("menuid"), map.get("menuid"), map.get("userid"), map.get("userid")});
		}
		
		return (List<Integer>) batchExecute(sbufSql.toString(), parameters);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Integer> delete(List<Map<String, Object>> parameter) {
		StringBuffer sbufSql = new StringBuffer(SQL_DELETE_TEMPLET);
		sbufSql.append(" and (? is null or id = ?)");
		sbufSql.append(" and (? is null or menu = ?)");
		sbufSql.append(" and (? is null or user = ?)");

		List<Object[]> parameters = new ArrayList<Object[]>();
		for (Map<String, Object> map : parameter) {
			parameters.add(new Object[]{map.get("id"), map.get("id"), map.get("menuid"), map.get("menuid"), map.get("userid"), map.get("userid")});
		}
		
		return (List<Integer>) batchExecute(sbufSql.toString(), parameters);
	}
	
	@Override
	public UserMenu find(Long id) {
		StringBuffer sbufSql = new StringBuffer(SQL_SELECT_TEMPLET);
		sbufSql.append(" and a.id = ?");
		
		Object[] parameters = {id};
		
		return(UserMenu) find(sbufSql.toString(), parameters, new UserMenuMapper());
	}

	@Override
	public Long insert(UserMenu userMenu) {
		StringBuffer sbufSql = new StringBuffer(SQL_INSERT_TEMPLET);
		Object parameters[] = new Object[4];
		parameters[0] = userMenu.getId();
		parameters[1] = userMenu.getUser();
		parameters[2] = userMenu.getMenu();
		parameters[3] = userMenu.getFlag();
		
		return (Long) insert(sbufSql.toString(), parameters);
	}

	@Override
	public Page pagination(Long userid, String user, Long menuid, String menu, Integer flag, Page page) {
		StringBuffer sbufSql = new StringBuffer(SQL_SELECT_TEMPLET);
		sbufSql.append(" and (? is null or a.user = ?)");
		sbufSql.append(" and (? is null or a.menu = ?)");
		sbufSql.append(" and (? is null or a.flag = ?)");
		sbufSql.append(" and (? is null or c.title like ?)");
		sbufSql.append(" and (? is null or d.title like ?)");
		sbufSql.append(" order by a.id desc");
		
		Object[] parameters = {userid, userid, menuid, menuid, flag, flag, user, "%" + user + "%", menu, "%" + menu + "%"};
		
		return pagination(sbufSql.toString(), parameters, new UserMenuMapper(), page);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<UserMenu> select(Long userid, String user, Long menuid, String menu, Integer flag) {
		StringBuffer sbufSql = new StringBuffer(SQL_SELECT_TEMPLET);
		sbufSql.append(" and (? is null or a.user = ?)");
		sbufSql.append(" and (? is null or a.menu = ?)");
		sbufSql.append(" and (? is null or a.flag = ?)");
		sbufSql.append(" and (? is null or c.title like ?)");
		sbufSql.append(" and (? is null or d.title like ?)");
		
		Object[] parameters = {userid, userid, menuid, menuid, flag, flag, user, "%" + user + "%", menu, "%" + menu + "%"};
		
		return (List<UserMenu>) select(sbufSql.toString(), parameters, new UserMenuMapper());
	}

	@Override
	public Integer update(UserMenu userMenu) {
		StringBuffer sbufSql = new StringBuffer(SQL_UPDATE_TEMPLET);
		sbufSql.append(" and id = ?");

		Object parameters[] = new Object[4];
		parameters[0] = userMenu.getUser();
		parameters[1] = userMenu.getMenu();
		parameters[2] = userMenu.getFlag();
		parameters[3] = userMenu.getId();

		return update(sbufSql.toString(), parameters);
	}
	
	@SuppressWarnings({ "rawtypes" })
	protected class UserMenuMapper implements RowMapper {  
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {  
			UserMenu userMenu = new UserMenu();  
			userMenu.setId(rs.getLong("id"));
			userMenu.setUser(rs.getLong("user"));
			userMenu.setUsername(rs.getString("username"));
			userMenu.setMenu(rs.getLong("menu"));
			userMenu.setMenuname(rs.getString("menuname"));
			userMenu.setFlag(rs.getInt("flag"));
			userMenu.setFlagmeaning(rs.getString("flagmeaning"));
			
			return userMenu;  
		}
	}
}
