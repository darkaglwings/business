package org.frame.business.dao.system.menu.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.frame.business.constant.IBusinessConstant;
import org.frame.business.dao.system.menu.IMenuDao;
import org.frame.business.model.system.Menu;
import org.frame.business.system.Config;
import org.frame.common.constant.ISymbolConstant;
import org.frame.common.lang.StringHelper;
import org.frame.repository.sql.model.Page;
import org.frame.web.dao.BaseDao;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;


@Repository("menuDao")
@Scope("singleton")
public class MenuDao extends BaseDao implements IMenuDao {
	
	@SuppressWarnings("unused")
	private Log logger = LogFactory.getLog(MenuDao.class);
	
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
	public Menu find(Long id) {
		StringBuffer sbufSql = new StringBuffer(SQL_SELECT_TEMPLET);
		sbufSql.append(" and a.id = ?");
		
		Object[] parameters = {id};
		
		return (Menu) find(sbufSql.toString(), parameters, new MenuMapper());
	}

	@Override
	public Long insert(Menu menu) {
		StringBuffer sbufSql = new StringBuffer(SQL_INSERT_TEMPLET);
		
		Object parameters[] = new Object[9];
		parameters[0] = menu.getId();
		parameters[1] = menu.getParentid();
		parameters[2] = menu.getSystem();
		parameters[3] = menu.getTitle();
		parameters[4] = menu.getRank();
		parameters[5] = menu.getUri();
		parameters[6] = menu.getDisplay();
		parameters[7] = menu.getFlag();
		parameters[8] = menu.getRemark();
		
		return (Long) insert(sbufSql.toString(), parameters);
	}

	@Override
	public Page pagination(Long parentid, String parentname, String system, String title, Long rank, Integer flag, Page page) {
		StringBuffer sbufSql = new StringBuffer(SQL_SELECT_TEMPLET);
		sbufSql.append(" and (? is null or a.parentid = ?)");
		sbufSql.append(" and (? is null or b.title like ?)");
		sbufSql.append(" and (? is null or a.system = ?)");
		sbufSql.append(" and (? is null or a.title like ?)");
		sbufSql.append(" and (? is null or a.rank = ?)");
		sbufSql.append(" and (? is null or a.flag = ?)");
		sbufSql.append(" order by a.id desc");

		Object[] parameters = {parentid, parentid, parentname, "%" + parentname + "%", system, system, title, "%" + title + "%", rank, rank, flag, flag};
		
		return pagination(sbufSql.toString(), parameters, new MenuMapper(), page);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Menu> select(Long userid, String system) {
		StringBuffer sbufSql = new StringBuffer("");
		Map<String, Object> parameters =  new HashMap<String, Object>();
		
		Config config = new Config(IBusinessConstant.DEFAULT_CONFIG_PROPERTIES);
		String menuRelated = config.get(IBusinessConstant.MENU_RELATED).toLowerCase();
		String userRelated = config.get(IBusinessConstant.USER_RELATED).toLowerCase();
		String departmentPattern = config.get(IBusinessConstant.RELATED_TYPE_DEPARTMENT).toLowerCase();
		String postPattern = config.get(IBusinessConstant.RELATED_TYPE_POST).toLowerCase();
		
		if (StringHelper.isNull(menuRelated)) menuRelated = "none";
		if (ISymbolConstant.FLAG_ALL.equals(menuRelated)) menuRelated = "department, post, role, user";
		
		if (StringHelper.isNull(userRelated)) userRelated = "none";
		if (ISymbolConstant.FLAG_ALL.equals(userRelated)) userRelated = "department, post, role";
		
		if (menuRelated.contains("department") && userRelated.contains("department")) {
			if (!"".equals(sbufSql.toString())) {
				sbufSql.append(" union ");
			}
			
			if (ISymbolConstant.FLAG_MULTI.equals(departmentPattern)) {
				sbufSql.append(SQL_MENU_DEPARTMENT_MULTI_TEMPLET);
			} else {
				sbufSql.append(SQL_MENU_DEPARTMENT_SINGLE_TEMPLET);
			}
		}
		
		if (menuRelated.contains("none")) {
			sbufSql = new StringBuffer(SQL_MENU_NONE_TEMPLET);
		}
		
		if (menuRelated.contains("post") && userRelated.contains("post")) {
			if (!"".equals(sbufSql.toString())) {
				sbufSql.append(" union ");
			}
			
			if (ISymbolConstant.FLAG_MULTI.equals(postPattern)) {
				sbufSql.append(SQL_MENU_POST_MULTI_TEMPLET);
			} else {
				sbufSql.append(SQL_MENU_POST_SINGLE_TEMPLET);
			}
		}
		
		if (menuRelated.contains("role") && userRelated.contains("role")) {
			if (!"".equals(sbufSql.toString())) {
				sbufSql.append(" union ");
			}
			
			sbufSql.append(SQL_MENU_ROLE_TEMPLET);
		}
		
		if (menuRelated.contains("user")) {
			if (!"".equals(sbufSql.toString())) {
				sbufSql.append(" union ");
			}
			
			sbufSql.append(SQL_MENU_USER_TEMPLET);
		}
		
		sbufSql.append(" order by display asc, id desc");
		
		parameters.put("flag", ISymbolConstant.FLAG_AVAILABLE);
		parameters.put("id", userid);
		parameters.put("system", system);
		parameters.put("system_name", "%" + system + "%");
		
		if (!"".equals(sbufSql)) {
			return (List<Menu>) select(sbufSql.toString(), parameters, new MenuMapper());
		} else
			return new ArrayList<Menu>();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Menu> select(Long parentid, String parentname, String system, String title, Long rank, Integer flag) {
		StringBuffer sbufSql = new StringBuffer(SQL_SELECT_TEMPLET);
		sbufSql.append(" and (? is null or a.parentid = ?)");
		sbufSql.append(" and (? is null or b.title like ?)");
		sbufSql.append(" and (? is null or a.system like ?)");
		sbufSql.append(" and (? is null or a.title like ?)");
		sbufSql.append(" and (? is null or a.rank = ?)");
		sbufSql.append(" and (? is null or a.flag = ?)");
		sbufSql.append(" order by a.display asc, a.id desc");
		
		Object[] parameters = {parentid, parentid, parentname, "%" + parentname + "%", system, system, title, "%" + title + "%", rank, rank, flag, flag};
		
		return (List<Menu>) select(sbufSql.toString(), parameters, new MenuMapper());
	}

	@Override
	public Integer update(Menu menu) {
		StringBuffer sbufSql = new StringBuffer(SQL_UPDATE_TEMPLET);
		sbufSql.append(" and id = ?");

		Object parameters[] = new Object[9];
		parameters[0] = menu.getParentid();
		parameters[1] = menu.getSystem();
		parameters[2] = menu.getTitle();
		parameters[3] = menu.getRank();
		parameters[4] = menu.getUri();
		parameters[5] = menu.getDisplay();
		parameters[6] = menu.getFlag();
		parameters[7] = menu.getRemark();
		parameters[8] = menu.getId();

		return update(sbufSql.toString(), parameters);
	}
	
	@SuppressWarnings({ "rawtypes" })
	protected class MenuMapper implements RowMapper {  
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {  
			Menu menu = new Menu();  
			menu.setId(rs.getLong("id"));
			menu.setParentid(rs.getLong("parentid"));
			menu.setParentname(rs.getString("parentname"));
			menu.setSystem(rs.getString("system"));
			menu.setTitle(rs.getString("title"));
			menu.setRank(rs.getLong("rank"));
			menu.setUri(rs.getString("uri"));
			menu.setFlag(rs.getInt("flag"));
			menu.setDisplay(rs.getLong("display"));
			menu.setFlagmeaning(rs.getString("flagmeaning"));
			menu.setRemark(rs.getString("remark"));

			return menu;  
		} 
	}

}
