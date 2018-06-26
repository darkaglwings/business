package org.frame.business.management;

import org.frame.business.constant.IBusinessConstant;
import org.frame.common.util.Properties;
import org.frame.repository.management.Repository;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedResource;

@ManagedResource (objectName= "org.frame.management.business:name=business") 
public class BusinessMBean extends Repository implements IBusinessMBean {

	private String login_password_allownull;

	private String department_delete;
	
	private String dictionary_delete;
	
	private String menu_delete;
	
	private String parameter_delete;
	
	private String post_delete;
	
	private String role_delete;
	
	private String user_delete;
	
	private String related_type_department;
	
	private String related_type_post;
	
	private String menu_related;
	
	private String user_related;
	
	private String synchronization_enable_department;
	
	private String synchronization_enable_departmentmenu;
	
	private String synchronization_enable_dictionary;
	
	private String synchronization_enable_menu;
	
	private String synchronization_enable_parameter;
	
	private String synchronization_enable_post;
	
	private String synchronization_enable_postmenu;
	
	private String synchronization_enable_role;
	
	private String synchronization_enable_rolemenu;
	
	private String synchronization_enable_user;
	
	private String synchronization_enable_userdepartment;
	
	private String synchronization_enable_usermenu;
	
	private String synchronization_enable_userpost;
	
	private String synchronization_enable_userrole;
	
	private String synchronization_in;
	
	private String synchronization_out;
	
	private String synchronization_mode;
	
	private String synchronization_passage;

	@ManagedAttribute
	public String getLogin_password_allownull() {
		return login_password_allownull;
	}

	@ManagedAttribute
	public void setLogin_password_allownull(String login_password_allownull) {
		Properties properties = new Properties(IBusinessConstant.DEFAULT_CONFIG_PROPERTIES);
		properties.write(IBusinessConstant.LOGIN_PASSWORD_ALLOWNULL, login_password_allownull);
		
		this.login_password_allownull = login_password_allownull;
	}

	@ManagedAttribute
	public String getDepartment_delete() {
		return department_delete;
	}

	@ManagedAttribute
	public void setDepartment_delete(String department_delete) {
		Properties properties = new Properties(IBusinessConstant.DEFAULT_CONFIG_PROPERTIES);
		properties.write(IBusinessConstant.DEPARTMENT_DELETE, department_delete);
		
		this.department_delete = department_delete;
	}

	@ManagedAttribute
	public String getDictionary_delete() {
		return dictionary_delete;
	}

	@ManagedAttribute
	public void setDictionary_delete(String dictionary_delete) {
		Properties properties = new Properties(IBusinessConstant.DEFAULT_CONFIG_PROPERTIES);
		properties.write(IBusinessConstant.DEPARTMENT_DELETE, dictionary_delete);
		
		this.dictionary_delete = dictionary_delete;
	}

	@ManagedAttribute
	public String getMenu_delete() {
		return menu_delete;
	}

	@ManagedAttribute
	public void setMenu_delete(String menu_delete) {
		Properties properties = new Properties(IBusinessConstant.DEFAULT_CONFIG_PROPERTIES);
		properties.write(IBusinessConstant.MENU_DELETE, menu_delete);
		
		this.menu_delete = menu_delete;
	}

	@ManagedAttribute
	public String getParameter_delete() {
		return parameter_delete;
	}

	@ManagedAttribute
	public void setParameter_delete(String parameter_delete) {
		Properties properties = new Properties(IBusinessConstant.DEFAULT_CONFIG_PROPERTIES);
		properties.write(IBusinessConstant.PARAMETER_DELETE, parameter_delete);
		
		this.parameter_delete = parameter_delete;
	}

	@ManagedAttribute
	public String getPost_delete() {
		return post_delete;
	}

	@ManagedAttribute
	public void setPost_delete(String post_delete) {
		Properties properties = new Properties(IBusinessConstant.DEFAULT_CONFIG_PROPERTIES);
		properties.write(IBusinessConstant.POST_DELETE, post_delete);
		
		this.post_delete = post_delete;
	}

	@ManagedAttribute
	public String getRole_delete() {
		return role_delete;
	}

	@ManagedAttribute
	public void setRole_delete(String role_delete) {
		Properties properties = new Properties(IBusinessConstant.DEFAULT_CONFIG_PROPERTIES);
		properties.write(IBusinessConstant.ROLE_DELETE, role_delete);
		
		this.role_delete = role_delete;
	}

	@ManagedAttribute
	public String getUser_delete() {
		return user_delete;
	}

	@ManagedAttribute
	public void setUser_delete(String user_delete) {
		Properties properties = new Properties(IBusinessConstant.DEFAULT_CONFIG_PROPERTIES);
		properties.write(IBusinessConstant.USER_DELETE, user_delete);
		
		this.user_delete = user_delete;
	}

	@ManagedAttribute
	public String getRelated_type_department() {
		return related_type_department;
	}

	@ManagedAttribute
	public void setRelated_type_department(String related_type_department) {
		Properties properties = new Properties(IBusinessConstant.DEFAULT_CONFIG_PROPERTIES);
		properties.write(IBusinessConstant.RELATED_TYPE_DEPARTMENT, related_type_department);
		
		this.related_type_department = related_type_department;
	}

	@ManagedAttribute
	public String getRelated_type_post() {
		return related_type_post;
	}

	@ManagedAttribute
	public void setRelated_type_post(String related_type_post) {
		Properties properties = new Properties(IBusinessConstant.DEFAULT_CONFIG_PROPERTIES);
		properties.write(IBusinessConstant.RELATED_TYPE_POST, related_type_post);
		
		this.related_type_post = related_type_post;
	}

	@ManagedAttribute
	public String getMenu_related() {
		return menu_related;
	}

	@ManagedAttribute
	public void setMenu_related(String menu_related) {
		Properties properties = new Properties(IBusinessConstant.DEFAULT_CONFIG_PROPERTIES);
		properties.write(IBusinessConstant.MENU_RELATED, menu_related);
		
		this.menu_related = menu_related;
	}

	@ManagedAttribute
	public String getUser_related() {
		return user_related;
	}

	@ManagedAttribute
	public void setUser_related(String user_related) {
		Properties properties = new Properties(IBusinessConstant.DEFAULT_CONFIG_PROPERTIES);
		properties.write(IBusinessConstant.USER_RELATED, user_related);
		
		this.user_related = user_related;
	}

	@ManagedAttribute
	public String getSynchronization_enable_department() {
		return synchronization_enable_department;
	}

	@ManagedAttribute
	public void setSynchronization_enable_department(String synchronization_enable_department) {
		Properties properties = new Properties(IBusinessConstant.DEFAULT_CONFIG_PROPERTIES);
		properties.write(IBusinessConstant.SYNCHRONIZATION_ENABLE_DEPARTMENT, synchronization_enable_department);
		
		this.synchronization_enable_department = synchronization_enable_department;
	}

	@ManagedAttribute
	public String getSynchronization_enable_departmentmenu() {
		return synchronization_enable_departmentmenu;
	}

	@ManagedAttribute
	public void setSynchronization_enable_departmentmenu(String synchronization_enable_departmentmenu) {
		Properties properties = new Properties(IBusinessConstant.DEFAULT_CONFIG_PROPERTIES);
		properties.write(IBusinessConstant.SYNCHRONIZATION_ENABLE_DEPARTMENTMENU, synchronization_enable_departmentmenu);
		
		this.synchronization_enable_departmentmenu = synchronization_enable_departmentmenu;
	}

	@ManagedAttribute
	public String getSynchronization_enable_dictionary() {
		return synchronization_enable_dictionary;
	}

	@ManagedAttribute
	public void setSynchronization_enable_dictionary(String synchronization_enable_dictionary) {
		Properties properties = new Properties(IBusinessConstant.DEFAULT_CONFIG_PROPERTIES);
		properties.write(IBusinessConstant.SYNCHRONIZATION_ENABLE_DICTIONARY, synchronization_enable_dictionary);
		
		this.synchronization_enable_dictionary = synchronization_enable_dictionary;
	}

	@ManagedAttribute
	public String getSynchronization_enable_menu() {
		return synchronization_enable_menu;
	}

	@ManagedAttribute
	public void setSynchronization_enable_menu(String synchronization_enable_menu) {
		Properties properties = new Properties(IBusinessConstant.DEFAULT_CONFIG_PROPERTIES);
		properties.write(IBusinessConstant.SYNCHRONIZATION_ENABLE_MENU, synchronization_enable_menu);
		
		this.synchronization_enable_menu = synchronization_enable_menu;
	}

	@ManagedAttribute
	public String getSynchronization_enable_parameter() {
		return synchronization_enable_parameter;
	}

	@ManagedAttribute
	public void setSynchronization_enable_parameter(String synchronization_enable_parameter) {
		Properties properties = new Properties(IBusinessConstant.DEFAULT_CONFIG_PROPERTIES);
		properties.write(IBusinessConstant.SYNCHRONIZATION_ENABLE_MENU, synchronization_enable_menu);
		
		this.synchronization_enable_parameter = synchronization_enable_menu;
	}

	@ManagedAttribute
	public String getSynchronization_enable_post() {
		return synchronization_enable_post;
	}

	@ManagedAttribute
	public void setSynchronization_enable_post(String synchronization_enable_post) {
		Properties properties = new Properties(IBusinessConstant.DEFAULT_CONFIG_PROPERTIES);
		properties.write(IBusinessConstant.SYNCHRONIZATION_ENABLE_POST, synchronization_enable_post);
		
		this.synchronization_enable_post = synchronization_enable_post;
	}

	@ManagedAttribute
	public String getSynchronization_enable_postmenu() {
		return synchronization_enable_postmenu;
	}

	@ManagedAttribute
	public void setSynchronization_enable_postmenu(String synchronization_enable_postmenu) {
		Properties properties = new Properties(IBusinessConstant.DEFAULT_CONFIG_PROPERTIES);
		properties.write(IBusinessConstant.SYNCHRONIZATION_ENABLE_POSTMENU, synchronization_enable_postmenu);
		
		this.synchronization_enable_postmenu = synchronization_enable_postmenu;
	}

	@ManagedAttribute
	public String getSynchronization_enable_role() {
		return synchronization_enable_role;
	}

	@ManagedAttribute
	public void setSynchronization_enable_role(String synchronization_enable_role) {
		Properties properties = new Properties(IBusinessConstant.DEFAULT_CONFIG_PROPERTIES);
		properties.write(IBusinessConstant.SYNCHRONIZATION_ENABLE_ROLE, synchronization_enable_role);
		
		this.synchronization_enable_role = synchronization_enable_role;
	}

	@ManagedAttribute
	public String getSynchronization_enable_rolemenu() {
		return synchronization_enable_rolemenu;
	}

	@ManagedAttribute
	public void setSynchronization_enable_rolemenu(String synchronization_enable_rolemenu) {
		Properties properties = new Properties(IBusinessConstant.DEFAULT_CONFIG_PROPERTIES);
		properties.write(IBusinessConstant.SYNCHRONIZATION_ENABLE_ROLEMENU, synchronization_enable_rolemenu);
		
		this.synchronization_enable_rolemenu = synchronization_enable_rolemenu;
	}

	@ManagedAttribute
	public String getSynchronization_enable_user() {
		return synchronization_enable_user;
	}

	@ManagedAttribute
	public void setSynchronization_enable_user(String synchronization_enable_user) {
		Properties properties = new Properties(IBusinessConstant.DEFAULT_CONFIG_PROPERTIES);
		properties.write(IBusinessConstant.SYNCHRONIZATION_ENABLE_USER, synchronization_enable_user);
		
		this.synchronization_enable_user = synchronization_enable_user;
	}

	@ManagedAttribute
	public String getSynchronization_enable_userdepartment() {
		return synchronization_enable_userdepartment;
	}

	@ManagedAttribute
	public void setSynchronization_enable_userdepartment(String synchronization_enable_userdepartment) {
		Properties properties = new Properties(IBusinessConstant.DEFAULT_CONFIG_PROPERTIES);
		properties.write(IBusinessConstant.SYNCHRONIZATION_ENABLE_USERDEPARTMENT, synchronization_enable_userdepartment);
		
		this.synchronization_enable_userdepartment = synchronization_enable_userdepartment;
	}

	@ManagedAttribute
	public String getSynchronization_enable_usermenu() {
		return synchronization_enable_usermenu;
	}

	@ManagedAttribute
	public void setSynchronization_enable_usermenu(String synchronization_enable_usermenu) {
		Properties properties = new Properties(IBusinessConstant.DEFAULT_CONFIG_PROPERTIES);
		properties.write(IBusinessConstant.SYNCHRONIZATION_ENABLE_USERMENU, synchronization_enable_usermenu);
		
		this.synchronization_enable_usermenu = synchronization_enable_usermenu;
	}

	@ManagedAttribute
	public String getSynchronization_enable_userpost() {
		return synchronization_enable_userpost;
	}

	@ManagedAttribute
	public void setSynchronization_enable_userpost(String synchronization_enable_userpost) {
		Properties properties = new Properties(IBusinessConstant.DEFAULT_CONFIG_PROPERTIES);
		properties.write(IBusinessConstant.SYNCHRONIZATION_ENABLE_USERPOST, synchronization_enable_userpost);
		
		this.synchronization_enable_userpost = synchronization_enable_userpost;
	}

	@ManagedAttribute
	public String getSynchronization_enable_userrole() {
		return synchronization_enable_userrole;
	}

	@ManagedAttribute
	public void setSynchronization_enable_userrole(String synchronization_enable_userrole) {
		Properties properties = new Properties(IBusinessConstant.DEFAULT_CONFIG_PROPERTIES);
		properties.write(IBusinessConstant.SYNCHRONIZATION_ENABLE_USERROLE, synchronization_enable_userrole);
		
		this.synchronization_enable_userrole = synchronization_enable_userrole;
	}

	@ManagedAttribute
	public String getSynchronization_in() {
		return synchronization_in;
	}

	@ManagedAttribute
	public void setSynchronization_in(String synchronization_in) {
		Properties properties = new Properties(IBusinessConstant.DEFAULT_CONFIG_PROPERTIES);
		properties.write(IBusinessConstant.SYNCHRONIZATION_IN, synchronization_in);
		
		this.synchronization_in = synchronization_in;
	}

	@ManagedAttribute
	public String getSynchronization_out() {
		return synchronization_out;
	}

	@ManagedAttribute
	public void setSynchronization_out(String synchronization_out) {
		Properties properties = new Properties(IBusinessConstant.DEFAULT_CONFIG_PROPERTIES);
		properties.write(IBusinessConstant.SYNCHRONIZATION_OUT, synchronization_out);
		
		this.synchronization_out = synchronization_out;
	}

	@ManagedAttribute
	public String getSynchronization_mode() {
		return synchronization_mode;
	}

	@ManagedAttribute
	public void setSynchronization_mode(String synchronization_mode) {
		Properties properties = new Properties(IBusinessConstant.DEFAULT_CONFIG_PROPERTIES);
		properties.write(IBusinessConstant.SYNCHRONIZATION_MODE, synchronization_mode);
		
		this.synchronization_mode = synchronization_mode;
	}

	@ManagedAttribute
	public String getSynchronization_passage() {
		return synchronization_passage;
	}

	@ManagedAttribute
	public void setSynchronization_passage(String synchronization_passage) {
		Properties properties = new Properties(IBusinessConstant.DEFAULT_CONFIG_PROPERTIES);
		properties.write(IBusinessConstant.SYNCHRONIZATION_PASSAGE, synchronization_passage);
		
		this.synchronization_passage = synchronization_passage;
	}
	
	/*public static void main(String[] args) {
		Map<String, Object> map = new HashMap<String, Object>();
		//map.put("org.frame.management.common:name=common", new Common());
		//map.put("org.frame.management.repository:name=repository", new Repository());
		map.put("org.frame.management.business:name=business", new BusinessMBean());
		
		new Register(map);

		try {
			String path = "/org/frame/business/management/applicationContext-jmx.xml";
			ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(path);
			context.start();
			while (true) {
				Thread.sleep(10000);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}*/
	
}
