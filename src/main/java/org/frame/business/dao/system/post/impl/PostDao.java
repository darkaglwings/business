package org.frame.business.dao.system.post.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.frame.business.constant.IBusinessConstant;
import org.frame.business.dao.system.post.IPostDao;
import org.frame.business.model.system.Post;
import org.frame.business.system.Config;
import org.frame.common.constant.ISymbolConstant;
import org.frame.common.lang.StringHelper;
import org.frame.repository.sql.model.Page;
import org.frame.web.dao.BaseDao;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;


@Repository("postDao")
@Scope("singleton")
public class PostDao extends BaseDao implements IPostDao {
	
	private Log logger = LogFactory.getLog(PostDao.class);
	
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
	public Post find(Long id) {
		StringBuffer sbufSql = new StringBuffer(SQL_SELECT_TEMPLET);
		sbufSql.append(" and a.id = ?");
		
		Object[] parameters = {id};
		
		return (Post) find(sbufSql.toString(), parameters, new PostMapper());
	}

	@Override
	public Long insert(Post post) {
		StringBuffer sbufSql = new StringBuffer(SQL_INSERT_TEMPLET);
		
		Object parameters[] = new Object[7];
		parameters[0] = post.getId();
		parameters[1] = post.getParentid();
		parameters[2] = post.getRank();
		parameters[3] = post.getFullname();
		parameters[4] = post.getTitle();
		parameters[5] = post.getFlag();
		parameters[6] = post.getRemark();
		
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
		
		return pagination(sbufSql.toString(), parameters, new PostMapper(), page);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Post> select(Long userid) {
		String pattern = new Config(IBusinessConstant.DEFAULT_CONFIG_PROPERTIES).get(IBusinessConstant.RELATED_TYPE_DEPARTMENT).toLowerCase();
		if (StringHelper.isNull(pattern)) pattern = ISymbolConstant.FLAG_SINGLE;
		
		StringBuffer sbufSql = new StringBuffer("");
		
		if (ISymbolConstant.FLAG_SINGLE.equals(pattern)) {
			sbufSql = new StringBuffer(SQL_USER_POST_SINGLE_TEMPLET);
			sbufSql.append(" and a.flag = ?");
			sbufSql.append(" and (? is null or b.id = ?)");
		} else if (ISymbolConstant.FLAG_MULTI.equals(pattern)) {
			sbufSql = new StringBuffer(SQL_USER_POST_MULTI_TEMPLET);
			sbufSql.append(" and a.flag = ?");
			sbufSql.append(" and (? is null or b.user = ?)");
		} else {
			logger.error("unknown department pattern.");
			return null;
		}
		
		Object[] parameters = {ISymbolConstant.FLAG_AVAILABLE, userid, userid};
		return (List<Post>) select(sbufSql.toString(), parameters, new PostMapper());
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Post> select(Long parentid, String parentname, String title, Integer flag) {
		StringBuffer sbufSql = new StringBuffer(SQL_SELECT_TEMPLET);
		sbufSql.append(" and (? is null or a.parentid = ?)");
		sbufSql.append(" and (? is null or b.title like ?)");
		sbufSql.append(" and (? is null or a.title like ?)");
		sbufSql.append(" and (? is null or a.flag = ?)");

		Object[] parameters = {parentid, parentid, parentname, "%" + parentname + "%", title, "%" + title + "%", flag, flag};
		
		return (List<Post>) select(sbufSql.toString(), parameters, new PostMapper());
	}

	@Override
	public Integer update(Post post) {
		StringBuffer sbufSql = new StringBuffer(SQL_UPDATE_TEMPLET);
		sbufSql.append(" and id = ?");

		Object parameters[] = new Object[7];
		parameters[0] = post.getParentid();
		parameters[1] = post.getRank();
		parameters[2] = post.getFullname();
		parameters[3] = post.getTitle();
		parameters[4] = post.getFlag();
		parameters[5] = post.getRemark();
		parameters[6] = post.getId();
		
		return update(sbufSql.toString(), parameters);
	}
	
	@SuppressWarnings({ "rawtypes" })
	protected class PostMapper implements RowMapper {  
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {  
			Post post = new Post();  
			post.setId(rs.getLong("id"));
			post.setParentid(rs.getLong("parentid"));
			post.setParentname(rs.getString("parentname"));
			post.setRank(rs.getLong("rank"));
			post.setFullname(rs.getString("fullname"));
			post.setTitle(rs.getString("title"));
			post.setFlag(rs.getInt("flag"));
			post.setFlagmeaning(rs.getString("flagmeaning"));
			post.setRemark(rs.getString("remark"));

			return post;  
		} 
	}
}
