package org.frame.business.dao.system.post;

import java.util.List;
import java.util.Map;

import org.frame.business.model.system.Post;
import org.frame.repository.sql.model.Page;


public interface IPostDao {
	
	final String SQL_ABANDON_TEMPLET = "update sys_post set flag = ? where 1 = 1";
	
	final String SQL_DELETE_TEMPLET = "delete from sys_post where 1 = 1";
	
	final String SQL_INSERT_TEMPLET = "insert into sys_post(id, parentid, rank, fullname, title, flag, remark) values(?, ?, ?, ?, ?, ?, ?)";
	
	final String SQL_SELECT_TEMPLET = "select a.id, a.parentid, b.title as parentname, a.rank, a.fullname, a.title, a.flag, c.meaning as flagmeaning,  a.remark from sys_post a left join sys_post b on a.parentid = b.id left join sys_dictionary c on a.flag = c.code and c.sort='FLAG' where 1 = 1";
	
	final String SQL_UPDATE_TEMPLET = "update sys_post set parentid = ?, rank = ?, fullname = ?, title = ?, flag = ?, remark = ? where 1 = 1";
	
	final String SQL_USER_POST_MULTI_TEMPLET = "select a.id, a.parentid, '' as parentname, a.rank, a.fullname, a.title, a.flag, '' as flagmeaning, a.remark from sys_post a, sys_userpost b where a.id = b.post";
	
	final String SQL_USER_POST_SINGLE_TEMPLET = "select a.id, a.parentid, '' as parentname, a.rank, a.fullname, a.title, a.flag, '' as flagmeaning, a.remark from sys_post a, sys_user b where a.id = b.post";
	
	public List<Integer> abandon(List<Map<String, Object>> parameter);
	
	public List<Integer> delete(List<Map<String, Object>> parameter);

	public Post find(Long id);

	public Long insert(Post post);

	public Page pagination(Long parentid, String parentname, String title, Integer flag, Page page);
	
	public List<Post> select(Long userid);
	
	public List<Post> select(Long parentid, String parentname, String title, Integer flag);

	public Integer update(Post post);
	
}
