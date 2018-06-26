package org.frame.business.dao.system.userpost;

import java.util.List;
import java.util.Map;

import org.frame.business.model.system.UserPost;
import org.frame.repository.sql.model.Page;


public interface IUserPostDao {

	final String SQL_ABANDON_TEMPLET = "update sys_userpost set flag = ? where 1 = 1";
	
	final String SQL_DELETE_TEMPLET = "delete from sys_userpost where 1 = 1";
	
	final String SQL_INSERT_TEMPLET = "insert into sys_userpost(id, user, post, flag) values(?, ?, ?, ?)";
	
	final String SQL_SELECT_TEMPLET = "select a.id, a.user, c.title as username, a.post, d.title as postname, a.flag, b.meaning as flagmeaning from sys_userpost a left join sys_dictionary b on a.flag = b.code and b.sort='FLAG', sys_user c, sys_post d where 1 = 1 and a.user = c.id and a.post = d.id";
	
	final String SQL_UPDATE_TEMPLET = "update sys_userpost set user = ?, post = ?, flag = ? where 1 = 1";
	
	public List<Integer> abandon(List<Map<String, Object>> parameter);
	
	public List<Integer> delete(List<Map<String, Object>> parameter);

	public UserPost find(Long id);

	public Long insert(UserPost userPost);

	public Page pagination(Long userid, String user, Long postid, String post, Integer flag, Page page);
	
	public List<UserPost> select(Long userid, String user, Long postid, String post, Integer flag);

	public Integer update(UserPost userPost);
	
}
