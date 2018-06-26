package org.frame.business.dao.system.postmenu;

import java.util.List;
import java.util.Map;

import org.frame.business.model.system.PostMenu;
import org.frame.repository.sql.model.Page;


public interface IPostMenuDao {
	
	final String SQL_ABANDON_TEMPLET = "update sys_postmenu set flag = ? where 1 = 1";
	
	final String SQL_DELETE_TEMPLET = "delete from sys_postmenu where 1 = 1";
	
	final String SQL_INSERT_TEMPLET = "insert into sys_postmenu(id, post, menu, flag) values(?, ?, ?, ?)";
	
	final String SQL_SELECT_TEMPLET = "select a.id, a.post, c.title as postname, a.menu, d.title as menuname, a.flag, b.meaning as flagmeaning from sys_postmenu a left join sys_dictionary b on a.flag = b.code and b.sort='FLAG', sys_post c, sys_menu d where 1 = 1 and a.post = c.id and a.menu = d.id";
	
	final String SQL_UPDATE_TEMPLET = "update sys_postmenu set post = ?, menu = ?, flag = ? where 1 = 1";
	
	public List<Integer> abandon(List<Map<String, Object>> parameter);
	
	public List<Integer> delete(List<Map<String, Object>> parameter);

	public PostMenu find(Long id);

	public Long insert(PostMenu postMenu);

	public Page pagination(Long postid, String post, Long menuid, String menu, Integer flag, Page page);
	
	public List<PostMenu> select(Long postid, String post, Long menuid, String menu, Integer flag);

	public Integer update(PostMenu postMenu);
	
}
