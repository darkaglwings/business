/**
 * This interface should be renamed to BusinessMBean and class named BusinessMBean should be renamed to Business when publish jmx without spring.
 */
package org.frame.business.management;


public interface IBusinessMBean {
	
	public String getLogin_password_allownull();

	public void setLogin_password_allownull(String login_password_allownull);

	public String getDepartment_delete();

	public void setDepartment_delete(String department_delete);

	public String getDictionary_delete();

	public void setDictionary_delete(String dictionary_delete);

	public String getMenu_delete();

	public void setMenu_delete(String menu_delete);

	public String getParameter_delete();

	public void setParameter_delete(String parameter_delete);

	public String getPost_delete();

	public void setPost_delete(String post_delete);

	public String getRole_delete();

	public void setRole_delete(String role_delete);

	public String getUser_delete();

	public void setUser_delete(String user_delete);

	public String getRelated_type_department();

	public void setRelated_type_department(String related_type_department);

	public String getRelated_type_post();

	public void setRelated_type_post(String related_type_post);

	public String getMenu_related();

	public void setMenu_related(String menu_related);

	public String getUser_related();

	public void setUser_related(String user_related);

	public String getSynchronization_enable_department();

	public void setSynchronization_enable_department(String synchronization_enable_department);

	public String getSynchronization_enable_departmentmenu();

	public void setSynchronization_enable_departmentmenu(String synchronization_enable_departmentmenu);

	public String getSynchronization_enable_dictionary();

	public void setSynchronization_enable_dictionary(String synchronization_enable_dictionary);

	public String getSynchronization_enable_menu();

	public void setSynchronization_enable_menu(String synchronization_enable_menu);

	public String getSynchronization_enable_parameter();

	public void setSynchronization_enable_parameter(String synchronization_enable_parameter);

	public String getSynchronization_enable_post();

	public void setSynchronization_enable_post(String synchronization_enable_post);

	public String getSynchronization_enable_postmenu();

	public void setSynchronization_enable_postmenu(String synchronization_enable_postmenu);

	public String getSynchronization_enable_role();

	public void setSynchronization_enable_role(String synchronization_enable_role);

	public String getSynchronization_enable_rolemenu();

	public void setSynchronization_enable_rolemenu(String synchronization_enable_rolemenu);

	public String getSynchronization_enable_user();

	public void setSynchronization_enable_user(String synchronization_enable_user);

	public String getSynchronization_enable_userdepartment();

	public void setSynchronization_enable_userdepartment(String synchronization_enable_userdepartment);

	public String getSynchronization_enable_usermenu();

	public void setSynchronization_enable_usermenu(String synchronization_enable_usermenu);

	public String getSynchronization_enable_userpost();

	public void setSynchronization_enable_userpost(String synchronization_enable_userpost);

	public String getSynchronization_enable_userrole();

	public void setSynchronization_enable_userrole(String synchronization_enable_userrole);

	public String getSynchronization_in();

	public void setSynchronization_in(String synchronization_in);

	public String getSynchronization_out();

	public void setSynchronization_out(String synchronization_out);

	public String getSynchronization_mode();

	public void setSynchronization_mode(String synchronization_mode);

	public String getSynchronization_passage();

	public void setSynchronization_passage(String synchronization_passage);
	
}
