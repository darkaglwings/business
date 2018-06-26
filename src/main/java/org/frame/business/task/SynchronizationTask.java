package org.frame.business.task;

import org.frame.business.constant.IBusinessConstant;
import org.frame.business.service.synchronization.ISynchronizationService;
import org.frame.business.service.synchronization.impl.SynchronizationService;
import org.frame.business.system.Config;
import org.frame.common.constant.ISymbolConstant;
import org.frame.common.io.File;
import org.frame.common.webservice.client.Client;
import org.frame.common.xml.XMLBinding;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class SynchronizationTask {
	
	//@Scheduled(cron = "0 1 01 30 * ?")
	@Scheduled(fixedDelay = 5000)
	public void emport() {
		Config config = new Config(IBusinessConstant.DEFAULT_CONFIG_PROPERTIES);
		String mode = config.get(IBusinessConstant.SYNCHRONIZATION_MODE);
		
		if (mode != null && mode.contains(IBusinessConstant.SYNCHRONIZATION_MODE_AUTO)) {
			@SuppressWarnings("unused")
			boolean isServer = false, isClient = false;
			String type = config.get(IBusinessConstant.SYNCHRONIZATION_TYPE);
			if(type != null && type.contains(IBusinessConstant.SYNCHRONIZATION_TYPE_SERVER)) {
				isServer = true;
			} else if(type != null && type.contains(IBusinessConstant.SYNCHRONIZATION_TYPE_CLIENT)) {
				isClient = true;
			}
			
			if (isClient) {
				String passage = config.get(IBusinessConstant.SYNCHRONIZATION_PASSAGE);
				
				String department = config.get(IBusinessConstant.SYNCHRONIZATION_ENABLE_DEPARTMENT);
				String departmentmenu = config.get(IBusinessConstant.SYNCHRONIZATION_ENABLE_DEPARTMENTMENU);
				String dictionary =  config.get(IBusinessConstant.SYNCHRONIZATION_ENABLE_DICTIONARY);
				String menu = config.get(IBusinessConstant.SYNCHRONIZATION_ENABLE_MENU);
				String parameter = config.get(IBusinessConstant.SYNCHRONIZATION_ENABLE_PARAMETER);
				String post = config.get(IBusinessConstant.SYNCHRONIZATION_ENABLE_POST);
				String postmenu = config.get(IBusinessConstant.SYNCHRONIZATION_ENABLE_POSTMENU);
				String role = config.get(IBusinessConstant.SYNCHRONIZATION_ENABLE_ROLE);
				String rolemenu = config.get(IBusinessConstant.SYNCHRONIZATION_ENABLE_ROLEMENU);
				String user = config.get(IBusinessConstant.SYNCHRONIZATION_ENABLE_USER);
				String userdepartment = config.get(IBusinessConstant.SYNCHRONIZATION_ENABLE_USERDEPARTMENT);
				String usermenu = config.get(IBusinessConstant.SYNCHRONIZATION_ENABLE_USERMENU);
				String userpost = config.get(IBusinessConstant.SYNCHRONIZATION_ENABLE_USERPOST);
				String userrole = config.get(IBusinessConstant.SYNCHRONIZATION_ENABLE_USERROLE);
				
				if (passage != null && passage.contains(IBusinessConstant.SYNCHRONIZATION_PASSAGE_FILE)) {
					XMLBinding xmlBinding = new XMLBinding();
					ISynchronizationService synchronizationService = new SynchronizationService();
					
					File in;
					String path = config.get(IBusinessConstant.SYNCHRONIZATION_IN);
					if (path != null && !"".equals(path)) {
						in = new File(path);
						if (!in.exists()) in.mkdirs();
					} else {
						in = null;
					}
					
					for (java.io.File file : in.listFiles()) {
						if (file.getName().toLowerCase().endsWith(".xml")) {
							if (file.getName().toLowerCase().startsWith("department_")) {
								if (department != null && ISymbolConstant.FLAG_TRUE.equals(department)) {
									synchronizationService.importDepartment(xmlBinding.xml2string(file));
									((File) file).delete();
								}
							}
							
							if (file.getName().toLowerCase().startsWith("departmentmenu_")) {
								if (departmentmenu != null && ISymbolConstant.FLAG_TRUE.equals(departmentmenu)) {
									synchronizationService.importDepartmentMenu(xmlBinding.xml2string(file));
									((File) file).delete();
								}
							}
							
							if (file.getName().toLowerCase().startsWith("dictionary_")) {
								if (dictionary != null && ISymbolConstant.FLAG_TRUE.equals(dictionary)) {
									synchronizationService.importDictionary(xmlBinding.xml2string(file));
									((File) file).delete();
								}
							}
							
							if (file.getName().toLowerCase().startsWith("menu_")) {
								if (menu != null && ISymbolConstant.FLAG_TRUE.equals(menu)) {
									synchronizationService.importMenu(xmlBinding.xml2string(file));
									((File) file).delete();
								}
							}
							
							if (file.getName().toLowerCase().startsWith("parameter_")) {
								if (parameter != null && ISymbolConstant.FLAG_TRUE.equals(parameter)) {
									synchronizationService.importParameter(xmlBinding.xml2string(file));
									((File) file).delete();
								}
							}
							
							if (file.getName().toLowerCase().startsWith("post_")) {
								if (post != null && ISymbolConstant.FLAG_TRUE.equals(post)) {
									synchronizationService.importPost(xmlBinding.xml2string(file));
									((File) file).delete();
								}
							}
							
							if (file.getName().toLowerCase().startsWith("postmenu_")) {
								if (postmenu != null && ISymbolConstant.FLAG_TRUE.equals(postmenu)) {
									synchronizationService.importPostMenu(xmlBinding.xml2string(file));
									((File) file).delete();
								}
							}
							
							if (file.getName().toLowerCase().startsWith("role_")) {
								if (role != null && ISymbolConstant.FLAG_TRUE.equals(role)) {
									synchronizationService.importRole(xmlBinding.xml2string(file));
									((File) file).delete();
								}
							}
							
							if (file.getName().toLowerCase().startsWith("rolemenu_")) {
								if (rolemenu != null && ISymbolConstant.FLAG_TRUE.equals(rolemenu)) {
									synchronizationService.importRoleMenu(xmlBinding.xml2string(file));
									((File) file).delete();
								}
							}
							
							if (file.getName().toLowerCase().startsWith("user_")) {
								if (user != null && ISymbolConstant.FLAG_TRUE.equals(user)) {
									synchronizationService.importUser(xmlBinding.xml2string(file));
									((File) file).delete();
								}
							}
							
							if (file.getName().toLowerCase().startsWith("userdepartment_")) {
								if (userdepartment != null && ISymbolConstant.FLAG_TRUE.equals(userdepartment)) {
									synchronizationService.importUserDepartment(xmlBinding.xml2string(file));
									((File) file).delete();
								}
							}
							
							if (file.getName().toLowerCase().startsWith("usermenu_")) {
								if (usermenu != null && ISymbolConstant.FLAG_TRUE.equals(usermenu)) {
									synchronizationService.importUserMenu(xmlBinding.xml2string(file));
									((File) file).delete();
								}
							}
							
							if (file.getName().toLowerCase().startsWith("userpost_")) {
								if (userpost != null && ISymbolConstant.FLAG_TRUE.equals(userpost)) {
									synchronizationService.importUserPost(xmlBinding.xml2string(file));
									((File) file).delete();
								}
							}
							
							if (file.getName().toLowerCase().startsWith("userrole_")) {
								if (userrole != null && ISymbolConstant.FLAG_TRUE.equals(userrole)) {
									synchronizationService.importUserRole(xmlBinding.xml2string(file));
									((File) file).delete();
								}
							}
						}
					}
				}
				
				if (passage != null && passage.contains(IBusinessConstant.SYNCHRONIZATION_PASSAGE_WEBSERVICE)) {
					String url = new Config(IBusinessConstant.DEFAULT_CONFIG_PROPERTIES).get(IBusinessConstant.SYNCHRONIZATION_URL);
					if (url != null && !"".equals(url) && !"null".equals(url)) {
						Object[] info;
						String namespace = "http://impl.synchronization.server.webservice.frame.org";
						
						Client client = new Client();
						ISynchronizationService synchronizationService = new SynchronizationService();
						
						if (department != null && ISymbolConstant.FLAG_TRUE.equals(department)) {
							info = client.invoke(url, namespace, "exportDepartment", new Class<?>[]{}, new Object[]{});
							if (info != null && info.length > 0) {
								synchronizationService.importDepartment(String.valueOf(info[0]));
							} else {
								System.err.println("no department info found.");
							}
						}
						
						if (departmentmenu != null && ISymbolConstant.FLAG_TRUE.equals(departmentmenu)) {
							info = client.invoke(url, namespace, "exportDepartmentMenu", new Class<?>[]{}, new Object[]{});
							if (info != null && info.length > 0) {
								synchronizationService.importDepartmentMenu(String.valueOf(info[0]));
							} else {
								System.err.println("no departmentmenu info found.");
							}
						}
						
						if (dictionary != null && ISymbolConstant.FLAG_TRUE.equals(dictionary)) {
							info = client.invoke(url, namespace, "exportDictionary", new Class<?>[]{}, new Object[]{});
							if (info != null && info.length > 0) {
								synchronizationService.importDictionary(String.valueOf(info[0]));
							} else {
								System.err.println("no dictionary info found.");
							}
						}
						
						if (menu != null && ISymbolConstant.FLAG_TRUE.equals(menu)) {
							info = client.invoke(url, namespace, "exportMenu", new Class<?>[]{}, new Object[]{});
							if (info != null && info.length > 0) {
								synchronizationService.importMenu(String.valueOf(info[0]));
							} else {
								System.err.println("no menu info found.");
							}
						}
						
						if (parameter != null && ISymbolConstant.FLAG_TRUE.equals(parameter)) {
							info = client.invoke(url, namespace, "exportParameter", new Class<?>[]{}, new Object[]{});
							if (info != null && info.length > 0) {
								synchronizationService.importParameter(String.valueOf(info[0]));
							} else {
								System.err.println("no parameter info found.");
							}
						}
						
						if (post != null && ISymbolConstant.FLAG_TRUE.equals(post)) {
							info = client.invoke(url, namespace, "exportPost", new Class<?>[]{}, new Object[]{});
							if (info != null && info.length > 0) {
								synchronizationService.importPost(String.valueOf(info[0]));
							} else {
								System.err.println("no post info found.");
							}
						}
						
						if (postmenu != null && ISymbolConstant.FLAG_TRUE.equals(postmenu)) {
							info = client.invoke(url, namespace, "exportPostMenu", new Class<?>[]{}, new Object[]{});
							if (info != null && info.length > 0) {
								synchronizationService.importPostMenu(String.valueOf(info[0]));
							} else {
								System.err.println("no postmenu info found.");
							}
						}
						
						if (role != null && ISymbolConstant.FLAG_TRUE.equals(role)) {
							info = client.invoke(url, namespace, "exportRole", new Class<?>[]{}, new Object[]{});
							if (info != null && info.length > 0) {
								synchronizationService.importRole(String.valueOf(info[0]));
							} else {
								System.err.println("no role info found.");
							}
						}
						
						if (rolemenu != null && ISymbolConstant.FLAG_TRUE.equals(rolemenu)) {
							info = client.invoke(url, namespace, "exportRoleMenu", new Class<?>[]{}, new Object[]{});
							if (info != null && info.length > 0) {
								synchronizationService.importRoleMenu(String.valueOf(info[0]));
							} else {
								System.err.println("no rolemenu info found.");
							}
						}
						
						if (user != null && ISymbolConstant.FLAG_TRUE.equals(user)) {
							info = client.invoke(url, namespace, "exportUser", new Class<?>[]{}, new Object[]{});
							if (info != null && info.length > 0) {
								synchronizationService.importUser(String.valueOf(info[0]));
							} else {
								System.err.println("no user info found.");
							}
						}
						
						if (userdepartment != null && ISymbolConstant.FLAG_TRUE.equals(userdepartment)) {
							info = client.invoke(url, namespace, "exportUserDepartment", new Class<?>[]{}, new Object[]{});
							if (info != null && info.length > 0) {
								synchronizationService.importUserDepartment(String.valueOf(info[0]));
							} else {
								System.err.println("no userdepartment info found.");
							}
						}
						
						if (usermenu != null && ISymbolConstant.FLAG_TRUE.equals(usermenu)) {
							info = client.invoke(url, namespace, "exportUserMenu", new Class<?>[]{}, new Object[]{});
							if (info != null && info.length > 0) {
								synchronizationService.importUserMenu(String.valueOf(info[0]));
							} else {
								System.err.println("no usermenu info found.");
							}
						}
						
						if (userpost != null && ISymbolConstant.FLAG_TRUE.equals(userpost)) {
							info = client.invoke(url, namespace, "exportUserPost", new Class<?>[]{}, new Object[]{});
							if (info != null && info.length > 0) {
								synchronizationService.importUserPost(String.valueOf(info[0]));
							} else {
								System.err.println("no userpost info found.");
							}
						}
						
						if (userrole != null && ISymbolConstant.FLAG_TRUE.equals(userrole)) {
							info = client.invoke(url, namespace, "exportUserRole", new Class<?>[]{}, new Object[]{});
							if (info != null && info.length > 0) {
								synchronizationService.importUserRole(String.valueOf(info[0]));
							} else {
								System.err.println("no userrole info found.");
							}
						}
					}
				}
			}
		}
	}
	
	//@Scheduled(cron = "0 1 01 30 * ?")
	@Scheduled(fixedDelay = 1000 * 60 * 60)
	public void export() {
		Config config = new Config(IBusinessConstant.DEFAULT_CONFIG_PROPERTIES);
		String mode = config.get(IBusinessConstant.SYNCHRONIZATION_MODE);
		
		if (mode != null && mode.contains(IBusinessConstant.SYNCHRONIZATION_MODE_AUTO)) {
			@SuppressWarnings("unused")
			boolean isServer = false, isClient = false;
			String type = config.get(IBusinessConstant.SYNCHRONIZATION_TYPE);
			if(type != null && type.contains(IBusinessConstant.SYNCHRONIZATION_TYPE_SERVER)) {
				isServer = true;
			} else if(type != null && type.contains(IBusinessConstant.SYNCHRONIZATION_TYPE_CLIENT)) {
				isClient = true;
			}

			if (isServer) {
				String passage = config.get(IBusinessConstant.SYNCHRONIZATION_PASSAGE);
				if (passage != null && passage.contains(IBusinessConstant.SYNCHRONIZATION_PASSAGE_FILE)) {
					String department = config.get(IBusinessConstant.SYNCHRONIZATION_ENABLE_DEPARTMENT);
					String departmentmenu = config.get(IBusinessConstant.SYNCHRONIZATION_ENABLE_DEPARTMENTMENU);
					String dictionary =  config.get(IBusinessConstant.SYNCHRONIZATION_ENABLE_DICTIONARY);
					String menu = config.get(IBusinessConstant.SYNCHRONIZATION_ENABLE_MENU);
					String parameter = config.get(IBusinessConstant.SYNCHRONIZATION_ENABLE_PARAMETER);
					String post = config.get(IBusinessConstant.SYNCHRONIZATION_ENABLE_POST);
					String postmenu = config.get(IBusinessConstant.SYNCHRONIZATION_ENABLE_POSTMENU);
					String role = config.get(IBusinessConstant.SYNCHRONIZATION_ENABLE_ROLE);
					String rolemenu = config.get(IBusinessConstant.SYNCHRONIZATION_ENABLE_ROLEMENU);
					String user = config.get(IBusinessConstant.SYNCHRONIZATION_ENABLE_USER);
					String userdepartment = config.get(IBusinessConstant.SYNCHRONIZATION_ENABLE_USERDEPARTMENT);
					String usermenu = config.get(IBusinessConstant.SYNCHRONIZATION_ENABLE_USERMENU);
					String userpost = config.get(IBusinessConstant.SYNCHRONIZATION_ENABLE_USERPOST);
					String userrole = config.get(IBusinessConstant.SYNCHRONIZATION_ENABLE_USERROLE);
					
					ISynchronizationService synchronizationService = new SynchronizationService();

					if (department != null && ISymbolConstant.FLAG_TRUE.equals(department)) {
						synchronizationService.exportDepartment();
					}

					if (departmentmenu != null && ISymbolConstant.FLAG_TRUE.equals(departmentmenu)) {
						synchronizationService.exportDepartmentMenu();
					}

					if (dictionary != null && ISymbolConstant.FLAG_TRUE.equals(dictionary)) {
						synchronizationService.exportDictionary();
					}

					if (menu != null && ISymbolConstant.FLAG_TRUE.equals(menu)) {
						synchronizationService.exportMenu();
					}

					if (parameter != null && ISymbolConstant.FLAG_TRUE.equals(parameter)) {
						synchronizationService.exportParameter();
					}

					if (post != null && ISymbolConstant.FLAG_TRUE.equals(post)) {
						synchronizationService.exportPost();
					}

					if (postmenu != null && ISymbolConstant.FLAG_TRUE.equals(postmenu)) {
						synchronizationService.exportPostMenu();
					}

					if (role != null && ISymbolConstant.FLAG_TRUE.equals(role)) {
						synchronizationService.exportRole();
					}

					if (rolemenu != null && ISymbolConstant.FLAG_TRUE.equals(rolemenu)) {
						synchronizationService.exportRoleMenu();
					}

					if (user != null && ISymbolConstant.FLAG_TRUE.equals(user)) {
						synchronizationService.exportUser();
					}

					if (userdepartment != null && ISymbolConstant.FLAG_TRUE.equals(userdepartment)) {
						synchronizationService.exportUserDepartment();
					}

					if (usermenu != null && ISymbolConstant.FLAG_TRUE.equals(usermenu)) {
						synchronizationService.exportUserMenu();
					}

					if (userpost != null && ISymbolConstant.FLAG_TRUE.equals(userpost)) {
						synchronizationService.exportUserPost();
					}

					if (userrole != null && ISymbolConstant.FLAG_TRUE.equals(userrole)) {
						synchronizationService.exportUserRole();
					}
				}
			}
		}
	}
	
	@Scheduled(fixedDelay = 3000)
	public void exportLinstener() {
		Config config = new Config(IBusinessConstant.DEFAULT_CONFIG_PROPERTIES);
		@SuppressWarnings("unused")
		boolean isServer = false, isClient = false;
		String type = config.get(IBusinessConstant.SYNCHRONIZATION_TYPE);
		if(type != null && type.contains(IBusinessConstant.SYNCHRONIZATION_TYPE_SERVER)) {
			isServer = true;
		} else if(type != null && type.contains(IBusinessConstant.SYNCHRONIZATION_TYPE_CLIENT)) {
			isClient = true;
		}

		if (isServer) {
			String passage = config.get(IBusinessConstant.SYNCHRONIZATION_PASSAGE);
			if (passage != null && passage.contains(IBusinessConstant.SYNCHRONIZATION_PASSAGE_FILE)) {
				String department = config.get(IBusinessConstant.SYNCHRONIZATION_ENABLE_DEPARTMENT);
				String departmentmenu = config.get(IBusinessConstant.SYNCHRONIZATION_ENABLE_DEPARTMENTMENU);
				String dictionary =  config.get(IBusinessConstant.SYNCHRONIZATION_ENABLE_DICTIONARY);
				String menu = config.get(IBusinessConstant.SYNCHRONIZATION_ENABLE_MENU);
				String parameter = config.get(IBusinessConstant.SYNCHRONIZATION_ENABLE_PARAMETER);
				String post = config.get(IBusinessConstant.SYNCHRONIZATION_ENABLE_POST);
				String postmenu = config.get(IBusinessConstant.SYNCHRONIZATION_ENABLE_POSTMENU);
				String role = config.get(IBusinessConstant.SYNCHRONIZATION_ENABLE_ROLE);
				String rolemenu = config.get(IBusinessConstant.SYNCHRONIZATION_ENABLE_ROLEMENU);
				String user = config.get(IBusinessConstant.SYNCHRONIZATION_ENABLE_USER);
				String userdepartment = config.get(IBusinessConstant.SYNCHRONIZATION_ENABLE_USERDEPARTMENT);
				String usermenu = config.get(IBusinessConstant.SYNCHRONIZATION_ENABLE_USERMENU);
				String userpost = config.get(IBusinessConstant.SYNCHRONIZATION_ENABLE_USERPOST);
				String userrole = config.get(IBusinessConstant.SYNCHRONIZATION_ENABLE_USERROLE);
				
				ISynchronizationService synchronizationService = new SynchronizationService();
				
				File in;
				String path = config.get(IBusinessConstant.SYNCHRONIZATION_IN);
				if (path != null && !"".equals(path)) {
					in = new File(path);
					if (!in.exists()) in.mkdirs();
				} else {
					in = null;
				}
				
				for (java.io.File file : in.listFiles()) {
					if (file.getName().toLowerCase().endsWith(".xml")) {
						if (file.getName().toLowerCase().startsWith("export_department_")) {
							if (department != null && ISymbolConstant.FLAG_TRUE.equals(department)) {
								synchronizationService.exportDepartment();
								((File) file).delete();
							}
						}
						
						if (file.getName().toLowerCase().startsWith("export_departmentmenu_")) {
							if (departmentmenu != null && ISymbolConstant.FLAG_TRUE.equals(departmentmenu)) {
								synchronizationService.exportDepartmentMenu();
								((File) file).delete();
							}
						}
						
						if (file.getName().toLowerCase().startsWith("export_dictionary_")) {
							if (dictionary != null && ISymbolConstant.FLAG_TRUE.equals(dictionary)) {
								synchronizationService.exportDictionary();
								((File) file).delete();
							}
						}
						
						if (file.getName().toLowerCase().startsWith("export_menu_")) {
							if (menu != null && ISymbolConstant.FLAG_TRUE.equals(menu)) {
								synchronizationService.exportMenu();
								((File) file).delete();
							}
						}
						
						if (file.getName().toLowerCase().startsWith("export_parameter_")) {
							if (parameter != null && ISymbolConstant.FLAG_TRUE.equals(parameter)) {
								synchronizationService.exportParameter();
								((File) file).delete();
							}
						}
						
						if (file.getName().toLowerCase().startsWith("export_post_")) {
							if (post != null && ISymbolConstant.FLAG_TRUE.equals(post)) {
								synchronizationService.exportPost();
								((File) file).delete();
							}
						}
						
						if (file.getName().toLowerCase().startsWith("export_postmenu_")) {
							if (postmenu != null && ISymbolConstant.FLAG_TRUE.equals(postmenu)) {
								synchronizationService.exportPostMenu();
								((File) file).delete();
							}
						}
						
						if (file.getName().toLowerCase().startsWith("export_role_")) {
							if (role != null && ISymbolConstant.FLAG_TRUE.equals(role)) {
								synchronizationService.exportRole();
								((File) file).delete();
							}
						}
						
						if (file.getName().toLowerCase().startsWith("export_rolemenu_")) {
							if (rolemenu != null && ISymbolConstant.FLAG_TRUE.equals(rolemenu)) {
								synchronizationService.exportRoleMenu();
								((File) file).delete();
							}
						}
						
						if (file.getName().toLowerCase().startsWith("export_user_")) {
							if (user != null && ISymbolConstant.FLAG_TRUE.equals(user)) {
								synchronizationService.exportUser();
								((File) file).delete();
							}
						}
						
						if (file.getName().toLowerCase().startsWith("export_userdepartment_")) {
							if (userdepartment != null && ISymbolConstant.FLAG_TRUE.equals(userdepartment)) {
								synchronizationService.exportUserDepartment();
								((File) file).delete();
							}
						}
						
						if (file.getName().toLowerCase().startsWith("export_usermenu_")) {
							if (usermenu != null && ISymbolConstant.FLAG_TRUE.equals(usermenu)) {
								synchronizationService.exportUserMenu();
								((File) file).delete();
							}
						}
						
						if (file.getName().toLowerCase().startsWith("export_userpost_")) {
							if (userpost != null && ISymbolConstant.FLAG_TRUE.equals(userpost)) {
								synchronizationService.exportUserPost();
								((File) file).delete();
							}
						}
						
						if (file.getName().toLowerCase().startsWith("export_userrole_")) {
							if (userrole != null && ISymbolConstant.FLAG_TRUE.equals(userrole)) {
								synchronizationService.exportUserRole();
								((File) file).delete();
							}
						}
					}
				}
			}
		}
	}
	
}
