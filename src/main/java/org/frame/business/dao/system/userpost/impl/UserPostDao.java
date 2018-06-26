package org.frame.business.dao.system.userpost.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.frame.business.dao.system.userpost.IUserPostDao;
import org.frame.business.model.system.UserPost;
import org.frame.repository.sql.model.Page;
import org.frame.web.dao.BaseDao;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;


@Repository("userPostDao")
@Scope("singleton")
public class UserPostDao extends BaseDao implements IUserPostDao {
	
	@SuppressWarnings("unused")
	private Log logger = LogFactory.getLog(UserPostDao.class);
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Integer> abandon(List<Map<String, Object>> parameter) {
		StringBuffer sbufSql = new StringBuffer(SQL_ABANDON_TEMPLET);
		sbufSql.append(" and (? is null or id = ?)");
		sbufSql.append(" and (? is null or post = ?)");
		sbufSql.append(" and (? is null or user = ?)");

		List<Object[]> parameters = new ArrayList<Object[]>();
		for (Map<String, Object> map : parameter) {
			parameters.add(new Object[]{map.get("flag"), map.get("id"), map.get("id"), map.get("postid"), map.get("postid"), map.get("userid"), map.get("userid")});
		}
		
		return (List<Integer>) batchExecute(sbufSql.toString(), parameters);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Integer> delete(List<Map<String, Object>> parameter) {
		StringBuffer sbufSql = new StringBuffer(SQL_DELETE_TEMPLET);
		sbufSql.append(" and (? is null or id = ?)");
		sbufSql.append(" and (? is null or post = ?)");
		sbufSql.append(" and (? is null or user = ?)");

		List<Object[]> parameters = new ArrayList<Object[]>();
		for (Map<String, Object> map : parameter) {
			parameters.add(new Object[]{map.get("id"), map.get("id"), map.get("postid"), map.get("postid"), map.get("userid"), map.get("userid")});
		}
		
		return (List<Integer>) batchExecute(sbufSql.toString(), parameters);
	}
	
	@Override
	public UserPost find(Long id) {
		StringBuffer sbufSql = new StringBuffer(SQL_SELECT_TEMPLET);
		sbufSql.append(" and a.id = ?");
		
		Object[] parameters = {id};
		
		return (UserPost) find(sbufSql.toString(), parameters, new UserPostMapper());
	}

	@Override
	public Long insert(UserPost userPost) {
		StringBuffer sbufSql = new StringBuffer(SQL_INSERT_TEMPLET);
		
		Object parameters[] = new Object[4];
		parameters[0] = userPost.getId();
		parameters[1] = userPost.getUser();
		parameters[2] = userPost.getPost();
		parameters[3] = userPost.getFlag();
		
		return (Long) insert(sbufSql.toString(), parameters);
	}

	@Override
	public Page pagination(Long userid, String user, Long postid, String post, Integer flag, Page page) {
		StringBuffer sbufSql = new StringBuffer(SQL_SELECT_TEMPLET);
		sbufSql.append(" and (? is null or a.user = ?)");
		sbufSql.append(" and (? is null or a.post = ?)");
		sbufSql.append(" and (? is null or a.flag = ?)");
		sbufSql.append(" and (? is null or c.title like ?)");
		sbufSql.append(" and (? is null or d.title like ?)");
		sbufSql.append(" order by a.id desc");
		
		Object[] parameters = {userid, userid, postid, postid, flag, flag, user, "%" + user + "%", post, "%" + post + "%"};
		
		return pagination(sbufSql.toString(), parameters,  new UserPostMapper(), page);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<UserPost> select(Long userid, String user, Long postid, String post, Integer flag) {
		StringBuffer sbufSql = new StringBuffer(SQL_SELECT_TEMPLET);
		sbufSql.append(" and (? is null or a.user = ?)");
		sbufSql.append(" and (? is null or a.post = ?)");
		sbufSql.append(" and (? is null or a.flag = ?)");
		sbufSql.append(" and (? is null or c.title like ?)");
		sbufSql.append(" and (? is null or d.title like ?)");
		
		Object[] parameters = {userid, userid, postid, postid, flag, flag, user, "%" + user + "%", post, "%" + post + "%"};
		
		return (List<UserPost>) select(sbufSql.toString(), parameters, new UserPostMapper());
	}

	@Override
	public Integer update(UserPost userPost) {
		StringBuffer sbufSql = new StringBuffer(SQL_UPDATE_TEMPLET);
		sbufSql.append(" and id = ?");

		Object parameters[] = new Object[4];
		parameters[0] = userPost.getUser();
		parameters[1] = userPost.getPost();
		parameters[2] = userPost.getFlag();
		parameters[3] = userPost.getId();

		return update(sbufSql.toString(), parameters);
	}
	
	@SuppressWarnings({ "rawtypes" })
	protected class UserPostMapper implements RowMapper {  
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {  
			UserPost userPost = new UserPost();  
			userPost.setId(rs.getLong("id"));
			userPost.setUser(rs.getLong("user"));
			userPost.setUsername(rs.getString("username"));
			userPost.setPost(rs.getLong("post"));
			userPost.setPostname(rs.getString("postname"));
			userPost.setFlag(rs.getInt("flag"));
			userPost.setFlagmeaning(rs.getString("flagmeaning"));
			
			return userPost;  
		}
	}

}
