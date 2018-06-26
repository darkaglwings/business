package org.frame.business.dao.system.user.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.frame.business.constant.IBusinessConstant;
import org.frame.business.dao.system.department.IDepartmentDao;
import org.frame.business.dao.system.post.IPostDao;
import org.frame.business.dao.system.user.IUserDao;
import org.frame.business.model.system.User;
import org.frame.business.system.Config;
import org.frame.common.algorithm.Hash;
import org.frame.common.algorithm.Hash.HASH_TYPE;
import org.frame.common.constant.ISymbolConstant;
import org.frame.common.lang.StringHelper;
import org.frame.repository.sql.model.Page;
import org.frame.web.dao.BaseDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;


@Repository("userDao")
@Scope("singleton")
public class UserDao extends BaseDao implements IUserDao {
	
	@SuppressWarnings("unused")
	private Log logger = LogFactory.getLog(UserDao.class);

	@Resource
	@Autowired
	IDepartmentDao departmentDao;
	
	@Resource
	@Autowired
	IPostDao postDao;
	
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
	public User find(Long id) {
		String related = new Config(IBusinessConstant.DEFAULT_CONFIG_PROPERTIES).get(IBusinessConstant.USER_RELATED).toLowerCase();
		if (related == null) related = "";
		
		StringBuffer sbufSql = new StringBuffer();
		if (related.contains("department")) {
			sbufSql = new StringBuffer(SQL_SELECT_RELATED_DEPARTMENT_TEMPLET);
			sbufSql.append(" and a.id = :id");
		} else if (related.contains("post")) {
			sbufSql = new StringBuffer(SQL_SELECT_RELATED_POST_TEMPLET);
			sbufSql.append(" and a.id = :id");
		} else if (ISymbolConstant.FLAG_ALL.equals(related)) {
			sbufSql = new StringBuffer(SQL_SELECT_RELATED_ALL_TEMPLET);
			sbufSql.append(" and a.id = :id");
		} else {
			sbufSql = new StringBuffer(SQL_SELECT_TEMPLET);
			sbufSql.append(" and a.id = :id");
		}

		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("id", id);

		return this.adjust((User) find(sbufSql.toString(), parameters, User.class));
	}
	
	@Override
	public Long insert(User user) {
		StringBuffer sbufSql = new StringBuffer(SQL_INSERT_TEMPLET);

		Object[] parameters = new Object[14];
		parameters[0] = user.getId();
		parameters[1] = user.getUsername();
		parameters[2] = new Hash().hash(user.getPassword(), HASH_TYPE.MD5);
		parameters[3] = user.getTitle();
		parameters[4] = user.getSex();
		parameters[5] = user.getDepartment();
		parameters[6] = user.getPost();
		parameters[7] = user.getTelephone();
		parameters[8] = user.getAddress();
		parameters[9] = user.getCellphone();
		parameters[10] = user.getEmail();
		parameters[11] = user.getFlag();
		parameters[12] = user.getOnline();
		parameters[13] = user.getRemark();

		return (Long) insert(sbufSql.toString(), parameters);

	}
	
	@Override
	@SuppressWarnings("unchecked")
	public Page pagination(String title, String username, String sex, String department, String post, Integer flag, Page page) {
		StringBuffer sbufSql = new StringBuffer();

		String related = new Config(IBusinessConstant.DEFAULT_CONFIG_PROPERTIES).get(IBusinessConstant.USER_RELATED).toLowerCase();
		if (related == null) related = "";
		
		if (related.contains("department")) {
			sbufSql = new StringBuffer(SQL_SELECT_RELATED_DEPARTMENT_TEMPLET);
			sbufSql.append(" and (:title is null or a.title like :title_value)");
			sbufSql.append(" and (:username is null or a.username = :username)");
			sbufSql.append(" and (:sex is null or a.sex = :sex)");
			sbufSql.append(" and (:department is null or e.title like :department_value)");
			sbufSql.append(" and (:post is null or a.post like :post_value)");
			sbufSql.append(" and (:flag is null or a.flag = :flag)");
			sbufSql.append(" order by id desc");
		} else if (related.contains("post")) {
			sbufSql = new StringBuffer(SQL_SELECT_RELATED_POST_TEMPLET);
			sbufSql.append(" and (:title is null or a.title like :title_value)");
			sbufSql.append(" and (:username is null or a.username = :username)");
			sbufSql.append(" and (:sex is null or a.sex = :sex)");
			sbufSql.append(" and (:department is null or a.department like :department_value)");
			sbufSql.append(" and (:post is null or f.title like :post_value)");
			sbufSql.append(" and (:flag is null or a.flag = :flag)");
			sbufSql.append(" order by id desc");
		} else if ("all".equals(related)) {
			sbufSql = new StringBuffer(SQL_SELECT_RELATED_ALL_TEMPLET);
			sbufSql.append(" and (:title is null or a.title like :title_value)");
			sbufSql.append(" and (:username is null or a.username = :username)");
			sbufSql.append(" and (:sex is null or a.sex = :sex)");
			sbufSql.append(" and (:department is null or e.title like :department_value)");
			sbufSql.append(" and (:post is null or f.title like :post_value)");
			sbufSql.append(" and (:flag is null or a.flag = :flag)");
			sbufSql.append(" order by id desc");
		} else {
			sbufSql = new StringBuffer(SQL_SELECT_TEMPLET);
			sbufSql.append(" and (:title is null or a.title like :title_value)");
			sbufSql.append(" and (:username is null or a.username = :username)");
			sbufSql.append(" and (:sex is null or a.sex = :sex)");
			sbufSql.append(" and (:department is null or a.department like :department_value)");
			sbufSql.append(" and (:post is null or a.post like :post_value)");
			sbufSql.append(" and (:flag is null or a.flag = :flag)");
			sbufSql.append(" order by id desc");
		}
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("department", department);
		parameters.put("department_value", "%" + department + "%");
		parameters.put("flag", flag);
		parameters.put("post", post);
		parameters.put("post_value", "%" + post + "%");
		parameters.put("sex", sex);
		parameters.put("title", title);
		parameters.put("title_value", "%" + title + "%");
		parameters.put("username", username);
		
		page = pagination(sbufSql.toString(), parameters, User.class, page);
		
		for (User user : (List<User>) page.getData()) {
			this.adjust(user);
		}
		
		return page;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<User> select(Long id, String department, String post, Integer flag) {
		StringBuffer sbufSql = new StringBuffer();

		sbufSql = new StringBuffer(SQL_SELECT_TEMPLET);
		sbufSql.append(" and (:id is null or a.id like :id)");
		sbufSql.append(" and (:department is null or a.department like :department_value)");
		sbufSql.append(" and (:post is null or a.post like :post_value)");
		sbufSql.append(" and (:flag is null or a.flag = :flag)");
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("id", id);
		parameters.put("department", department);
		parameters.put("department_value", "%" + department + "%");
		parameters.put("flag", flag);
		parameters.put("post", post);
		parameters.put("post_value", "%" + post + "%");
		
		List<User> users = (List<User>) select(sbufSql.toString(), parameters, User.class);
		for (User user : users) {
			this.adjust(user);
		}
		
		return users;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<User> select(String title, String username, String sex, String department, String post, Integer flag) {
		StringBuffer sbufSql = new StringBuffer();
		
		String related = new Config(IBusinessConstant.DEFAULT_CONFIG_PROPERTIES).get(IBusinessConstant.USER_RELATED).toLowerCase();
		if (related == null) related = "";
		
		if (related.contains("department")) {
			sbufSql = new StringBuffer(SQL_SELECT_RELATED_DEPARTMENT_TEMPLET);
			sbufSql.append(" and (:title is null or a.title like :title_value)");
			sbufSql.append(" and (:username is null or a.username = :username)");
			sbufSql.append(" and (:sex is null or a.sex = :sex)");
			sbufSql.append(" and (:department is null or e.title like :department_value)");
			sbufSql.append(" and (:post is null or a.post like :post_value)");
			sbufSql.append(" and (:flag is null or a.flag = :flag)");
			sbufSql.append(" order by id desc");
		} else if (related.contains("post")) {
			sbufSql = new StringBuffer(SQL_SELECT_RELATED_POST_TEMPLET);
			sbufSql.append(" and (:title is null or a.title like :title_value)");
			sbufSql.append(" and (:username is null or a.username = :username)");
			sbufSql.append(" and (:sex is null or a.sex = :sex)");
			sbufSql.append(" and (:department is null or a.department like :department_value)");
			sbufSql.append(" and (:post is null or f.title like :post_value)");
			sbufSql.append(" and (:flag is null or a.flag = :flag)");
			sbufSql.append(" order by id desc");
		} else if ("all".equals(related)) {
			sbufSql = new StringBuffer(SQL_SELECT_RELATED_ALL_TEMPLET);
			sbufSql.append(" and (:title is null or a.title like :title_value)");
			sbufSql.append(" and (:username is null or a.username = :username)");
			sbufSql.append(" and (:sex is null or a.sex = :sex)");
			sbufSql.append(" and (:department is null or e.title like :department_value)");
			sbufSql.append(" and (:post is null or f.title like :post_value)");
			sbufSql.append(" and (:flag is null or a.flag = :flag)");
			sbufSql.append(" order by id desc");
		} else {
			sbufSql = new StringBuffer(SQL_SELECT_TEMPLET);
			sbufSql.append(" and (:title is null or a.title like :title_value)");
			sbufSql.append(" and (:username is null or a.username = :username)");
			sbufSql.append(" and (:sex is null or a.sex = :sex)");
			sbufSql.append(" and (:department is null or a.department like :department_value)");
			sbufSql.append(" and (:post is null or a.post like :post_value)");
			sbufSql.append(" and (:flag is null or a.flag = :flag)");
			sbufSql.append(" order by id desc");
		}
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("department", department);
		parameters.put("department_value", "%" + department + "%");
		parameters.put("flag", flag);
		parameters.put("post", post);
		parameters.put("post_value", "%" + post + "%");
		parameters.put("sex", sex);
		parameters.put("title", title);
		parameters.put("title_value", "%" + title + "%");
		parameters.put("username", username);
		
		List<User> users = (List<User>) select(sbufSql.toString(), parameters, User.class);
		for (User user : users) {
			this.adjust(user);
		}
		
		return users;
	}
	
	@Override
	public Integer update(User user) {
		StringBuffer sbufSql = new StringBuffer(SQL_UPDATE_TEMPLET);
		sbufSql.append(" and id = ?");

		Object[] parameters = new Object[14];
		parameters[0] = user.getUsername();
		parameters[1] = new Hash().hash(user.getPassword(), HASH_TYPE.MD5);
		parameters[2] = user.getTitle();
		parameters[3] = user.getSex();
		parameters[4] = user.getDepartment();
		parameters[5] = user.getPost();
		parameters[6] = user.getTelephone();
		parameters[7] = user.getAddress();
		parameters[8] = user.getCellphone();
		parameters[9] = user.getEmail();
		parameters[10] = user.getFlag();
		parameters[11] = user.getOnline();
		parameters[12] = user.getRemark();
		parameters[13] = user.getId();

		return update(sbufSql.toString(), parameters);
	}

	private User adjust(User user) {
		if (user != null) {
			Config config = new Config(IBusinessConstant.DEFAULT_CONFIG_PROPERTIES);
			String related = config.get(IBusinessConstant.USER_RELATED).toLowerCase();
			if (related == null) related = "";
			String department = config.get(IBusinessConstant.RELATED_TYPE_DEPARTMENT).toLowerCase();
			String post = config.get(IBusinessConstant.RELATED_TYPE_POST).toLowerCase();
			if (ISymbolConstant.FLAG_ALL.equals(related) || related.contains("department")) {
				if (ISymbolConstant.FLAG_MULTI.equals(department)) {
					String departments = "", name;
					if (!StringHelper.isNull(user.getDepartment())) {
						String[] ids = user.getDepartment().split(",");
						for (String id : ids) {
							if (!StringHelper.isNull(id)) {
								name = departmentDao.find(Long.parseLong(id)).getTitle();
							} else {
								name = "";
							}
							
							if (departments.equals("")) {
								departments = name;
							} else {
								departments += "," + name;
							}
						}
						
						user.setDepartmentname(departments);
					}
				} else ;
			} else if (ISymbolConstant.FLAG_ALL.equals(related) || related.contains("post")) {
				if (ISymbolConstant.FLAG_MULTI.equals(post)) {
					String posts = "", name;
					if (!StringHelper.isNull(user.getPost())) {
						String[] ids = user.getPost().split(",");
						for (String id : ids) {
							if (!StringHelper.isNull(id)) {
								name = postDao.find(Long.parseLong(id)).getTitle();
							} else {
								name = "";
							}
							
							if (posts.equals("")) {
								posts = name;
							} else {
								posts += "," + name;
							}
						}
						
						user.setPostname(posts);
					}
				} else ;
			} else ;
		}
		
		return user;
	}
	
	@SuppressWarnings({ "rawtypes" })
	protected class UserMapper implements RowMapper {  
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {  
			User user = new User();  
			user.setId(rs.getLong("id"));
			user.setUsername(rs.getString("username"));
			user.setPassword(rs.getString("password"));
			user.setTitle(rs.getString("title"));
			user.setSex(rs.getString("sex"));
			user.setSexname(rs.getString("sexname"));
			user.setDepartment(rs.getString("department"));
			user.setDepartmentname(rs.getString("departmentname"));
			user.setPost(rs.getString("post"));
			user.setPostname(rs.getString("postname"));
			user.setTelephone(rs.getString("telephone"));
			user.setAddress(rs.getString("address"));
			user.setCellphone(rs.getString("cellphone"));
			user.setEmail(rs.getString("email"));
			user.setFlag(rs.getInt("flag"));
			user.setFlagmeaning(rs.getString("flagmeaning"));
			user.setOnline(rs.getInt("online"));
			user.setOnlinestate(rs.getString("onlinestate"));
			user.setRemark(rs.getString("remark"));

			return user;  
		} 
	}
	
}
