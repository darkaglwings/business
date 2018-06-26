package org.frame.business.dao.system.postmenu.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.frame.business.dao.system.postmenu.IPostMenuDao;
import org.frame.business.model.system.PostMenu;
import org.frame.repository.sql.model.Page;
import org.frame.web.dao.BaseDao;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;


@Repository("postMenuDao")
@Scope("singleton")
public class PostMenuDao extends BaseDao implements IPostMenuDao {
	
	@SuppressWarnings("unused")
	private Log logger = LogFactory.getLog(PostMenuDao.class);
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Integer> abandon(List<Map<String, Object>> parameter) {
		StringBuffer sbufSql = new StringBuffer(SQL_ABANDON_TEMPLET);
		sbufSql.append(" and (? is null or id = ?)");
		sbufSql.append(" and (? is null or post = ?)");
		sbufSql.append(" and (? is null or menu = ?)");

		List<Object[]> parameters = new ArrayList<Object[]>();
		for (Map<String, Object> map : parameter) {
			parameters.add(new Object[]{map.get("flag"), map.get("id"), map.get("id"), map.get("postid"), map.get("postid"), map.get("menuid"), map.get("menuid")});
		}
		
		return (List<Integer>) batchExecute(sbufSql.toString(), parameters);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Integer> delete(List<Map<String, Object>> parameter) {
		StringBuffer sbufSql = new StringBuffer(SQL_DELETE_TEMPLET);
		sbufSql.append(" and (? is null or id = ?)");
		sbufSql.append(" and (? is null or post = ?)");
		sbufSql.append(" and (? is null or menu = ?)");

		List<Object[]> parameters = new ArrayList<Object[]>();
		for (Map<String, Object> map : parameter) {
			parameters.add(new Object[]{map.get("id"), map.get("id"), map.get("postid"), map.get("postid"), map.get("menuid"), map.get("menuid")});
		}
		
		return (List<Integer>) batchExecute(sbufSql.toString(), parameters);
	}
	
	@Override
	public PostMenu find(Long id) {
		StringBuffer sbufSql = new StringBuffer(SQL_SELECT_TEMPLET);
		sbufSql.append(" and a.id = ?");
		
		Object[] parameters = {id};
		
		return (PostMenu) find(sbufSql.toString(), parameters, new PostMenuMapper());
	}

	@Override
	public Long insert(PostMenu postMenu) {
		StringBuffer sbufSql = new StringBuffer(SQL_INSERT_TEMPLET);
		
		Object parameters[] = new Object[4];
		parameters[0] = postMenu.getId();
		parameters[1] = postMenu.getPost();
		parameters[2] = postMenu.getMenu();
		parameters[3] = postMenu.getFlag();
		
		return (Long) insert(sbufSql.toString(), parameters);
	}

	@Override
	public Page pagination(Long postid, String post, Long menuid, String menu, Integer flag, Page page) {
		StringBuffer sbufSql = new StringBuffer(SQL_SELECT_TEMPLET);
		sbufSql.append(" and (? is null or a.post = ?)");
		sbufSql.append(" and (? is null or a.menu = ?)");
		sbufSql.append(" and (? is null or a.flag = ?)");
		sbufSql.append(" and (? is null or c.title like ?)");
		sbufSql.append(" and (? is null or d.title like ?)");
		sbufSql.append(" order by a.id desc");
		
		Object[] parameters = {postid, postid, menuid, menuid, flag, flag, post, "%" + postid + "%", menu, "%" + menu + "%"};
		
		return pagination(sbufSql.toString(), parameters, new PostMenuMapper(), page);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<PostMenu> select(Long postid, String post, Long menuid, String menu, Integer flag) {
		StringBuffer sbufSql = new StringBuffer(SQL_SELECT_TEMPLET);
		sbufSql.append(" and (? is null or a.post = ?)");
		sbufSql.append(" and (? is null or a.menu = ?)");
		sbufSql.append(" and (? is null or a.flag = ?)");
		sbufSql.append(" and (? is null or c.title like ?)");
		sbufSql.append(" and (? is null or d.title like ?)");
		
		Object[] parameters = {postid, postid, menuid, menuid, flag, flag, post, "%" + postid + "%", menu, "%" + menu + "%"};
		
		return (List<PostMenu>) select(sbufSql.toString(), parameters, new PostMenuMapper());
	}

	@Override
	public Integer update(PostMenu postMenu) {
		StringBuffer sbufSql = new StringBuffer(SQL_UPDATE_TEMPLET);
		sbufSql.append(" and id = ?");

		Object parameters[] = new Object[4];
		parameters[0] = postMenu.getPost();
		parameters[1] = postMenu.getMenu();
		parameters[2] = postMenu.getFlag();
		parameters[3] = postMenu.getId();

		return update(sbufSql.toString(), parameters);
	}
	
	@SuppressWarnings({ "rawtypes" })
	protected class PostMenuMapper implements RowMapper {  
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {  
			PostMenu postMenu = new PostMenu();  
			postMenu.setId(rs.getLong("id"));
			postMenu.setPost(rs.getLong("post"));
			postMenu.setPostname(rs.getString("postname"));
			postMenu.setMenu(rs.getLong("menu"));
			postMenu.setMenuname(rs.getString("menuname"));
			postMenu.setFlag(rs.getInt("flag"));
			postMenu.setFlagmeaning(rs.getString("flagmeaning"));
			
			return postMenu;  
		}
	}

}
