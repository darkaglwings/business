package org.frame.business.service.synchronization;

public interface ISynchronizationService {
	
	public String exportDepartment();

	public String exportDepartmentMenu();

	public String exportDictionary();

	public String exportMenu();

	public String exportParameter();

	public String exportPost();

	public String exportPostMenu();

	public String exportRole();

	public String exportRoleMenu();

	public String exportUser();

	public String exportUserDepartment();

	public String exportUserMenu();

	public String exportUserPost();

	public String exportUserRole();

	public void importDepartment(String info);

	public void importDepartmentMenu(String info);

	public void importDictionary(String info);

	public void importMenu(String info);

	public void importParameter(String info);

	public void importPost(String info);

	public void importPostMenu(String info);

	public void importRole(String info);

	public void importRoleMenu(String info);

	public void importUser(String info);

	public void importUserDepartment(String info);

	public void importUserMenu(String info);

	public void importUserPost(String info);

	public void importUserRole(String info);

}