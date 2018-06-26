package org.frame.business.dao.system.user;

import java.util.List;
import java.util.Map;

import org.frame.business.model.system.User;
import org.frame.repository.sql.model.Page;


public interface IUserDao {
	
	final String SQL_ABANDON_TEMPLET = "update sys_user set flag = ? where 1 = 1";
	
	final String SQL_DELETE_TEMPLET = "delete from sys_user where 1 = 1";
	
	final String SQL_INSERT_TEMPLET = "insert into sys_user(id, username, password, title, sex, department, post, telephone, address, cellphone, email, flag, online, remark) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	
	final String SQL_SELECT_TEMPLET = "select a.id, a.username, a.password, a.title, a.sex, b.meaning as sexname, a.department, '' as departmentname, a.post, '' as postname, a.telephone, a.address, a.cellphone, a.email, a.flag, c.meaning as flagmeaning, a.online, d.meaning as onlinestate, a.remark from sys_user a left join sys_dictionary b on a.sex = b.code and b.sort = 'SEX' left join sys_dictionary c on a.flag = c.code and c.sort = 'FLAG' left join sys_dictionary d on a.online = d.code and d.sort = 'ONLINE' where 1 = 1";
	
	final String SQL_SELECT_RELATED_ALL_TEMPLET = "select a.id, a.username, a.password, a.title, a.sex, b.meaning as sexname, a.department, e.title as departmentname, a.post, f.title as postname, a.telephone, a.address, a.cellphone, a.email, a.flag, c.meaning as flagmeaning, a.online, d.meaning as onlinestate, a.remark from sys_user a left join sys_dictionary b on a.sex = b.code and b.sort = 'SEX' left join sys_dictionary c on a.flag = c.code and c.sort = 'FLAG' left join sys_dictionary d on a.online = d.code and d.sort = 'ONLINE' left join sys_department e on a.department = e.id left join sys_post f on a.post = f.id where 1 = 1";
	
	final String SQL_SELECT_RELATED_DEPARTMENT_TEMPLET = "select a.id, a.username, a.password, a.title, a.sex, b.meaning as sexname, a.department, e.title as departmentname, a.post, '' as postname, a.telephone, a.address, a.cellphone, a.email, a.flag, c.meaning as flagmeaning, a.online, d.meaning as onlinestate, a.remark from sys_user a left join sys_dictionary b on a.sex = b.code and b.sort = 'SEX' left join sys_dictionary c on a.flag = c.code and c.sort = 'FLAG' left join sys_dictionary d on a.online = d.code and d.sort = 'ONLINE' left join sys_department e on a.department = e.id where 1 = 1";
	
	final String SQL_SELECT_RELATED_POST_TEMPLET = "select a.id, a.username, a.password, a.title, a.sex, b.meaning as sexname, a.department, '' as departmentname, a.post, f.title as postname, a.telephone, a.address, a.cellphone, a.email, a.flag, c.meaning as flagmeaning, a.online, d.meaning as onlinestate, a.remark from sys_user a left join sys_dictionary b on a.sex = b.code and b.sort = 'SEX' left join sys_dictionary c on a.flag = c.code and c.sort = 'FLAG' left join sys_dictionary d on a.online = d.code and d.sort = 'ONLINE' left join sys_post f on a.post = f.id where 1 = 1";
	
	final String SQL_UPDATE_TEMPLET = "update sys_user set username = ?, password = ?, title = ?, sex = ?, department = ?, post = ?, telephone = ?, address = ?, cellphone = ?, email = ?, flag = ?, online = ?, remark = ? where 1 = 1";
	
	public List<Integer> abandon(List<Map<String, Object>> parameter);
	
	public List<Integer> delete(List<Map<String, Object>> parameter);
	
	public User find(Long id);
	
	public Long insert(User user);
	
	public Page pagination(String title, String username, String sex, String department, String post, Integer flag, Page page);
	
	public List<User> select(Long id, String department, String post, Integer flag);
	
	public List<User> select(String title, String username, String sex, String department, String post, Integer flag);
	
	public Integer update(User user);

}
