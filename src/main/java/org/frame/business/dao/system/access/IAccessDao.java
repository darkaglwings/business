package org.frame.business.dao.system.access;



public interface IAccessDao {
	
	final String SQL_LOGIN_TEMPLET = "select id, username, password, title, sex, department, post, telephone, address, cellphone, email, flag, online from sys_user a where flag = ? and username = ?";
	
	final String SQL_MENU_TEMPLET = "select d.id, d.parentid, d.system, d.title, d.rank, d.uri, d.flag, d.remark from sys_role a, sys_user b, sys_userrole c, sys_menu d, sys_menurole e where a.id = c.role and b.id = c.user and a.flag = 0 and a.id = e.role and d.id = e.menu and b.id = ?";
	
	final String SQL_ONLINE_TEMPLET = "update sys_user set online = ? where id = ?";
	
	final String SQL_ROLE_TEMPLET = "select a.id, a.code, a.title, a.flag, a.rank, a.remark from sys_role a, sys_user b, sys_userrole c where a.id = c.role and b.id = c.user and a.flag = 0 and b.id = ?";
	
	public Long login(String username, String password);
	
	public Integer logout(Long id);
	
}
