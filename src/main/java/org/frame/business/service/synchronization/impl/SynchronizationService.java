package org.frame.business.service.synchronization.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.frame.business.constant.IBusinessConstant;
import org.frame.business.dao.system.department.IDepartmentDao;
import org.frame.business.dao.system.departmentmenu.IDepartmentMenuDao;
import org.frame.business.dao.system.dictionary.IDictionaryDao;
import org.frame.business.dao.system.menu.IMenuDao;
import org.frame.business.dao.system.parameter.IParameterDao;
import org.frame.business.dao.system.post.IPostDao;
import org.frame.business.dao.system.postmenu.IPostMenuDao;
import org.frame.business.dao.system.role.IRoleDao;
import org.frame.business.dao.system.rolemenu.IRoleMenuDao;
import org.frame.business.dao.system.user.IUserDao;
import org.frame.business.dao.system.userdepartment.IUserDepartmentDao;
import org.frame.business.dao.system.usermenu.IUserMenuDao;
import org.frame.business.dao.system.userpost.IUserPostDao;
import org.frame.business.dao.system.userrole.IUserRoleDao;
import org.frame.business.model.synchronization.DepartmentMenus;
import org.frame.business.model.synchronization.Departments;
import org.frame.business.model.synchronization.Dictionarys;
import org.frame.business.model.synchronization.Menus;
import org.frame.business.model.synchronization.Parameters;
import org.frame.business.model.synchronization.PostMenus;
import org.frame.business.model.synchronization.Posts;
import org.frame.business.model.synchronization.RoleMenus;
import org.frame.business.model.synchronization.Roles;
import org.frame.business.model.synchronization.UserDepartments;
import org.frame.business.model.synchronization.UserMenus;
import org.frame.business.model.synchronization.UserPosts;
import org.frame.business.model.synchronization.UserRoles;
import org.frame.business.model.synchronization.Users;
import org.frame.business.model.system.Department;
import org.frame.business.model.system.DepartmentMenu;
import org.frame.business.model.system.Dictionary;
import org.frame.business.model.system.Menu;
import org.frame.business.model.system.Parameter;
import org.frame.business.model.system.Post;
import org.frame.business.model.system.PostMenu;
import org.frame.business.model.system.Role;
import org.frame.business.model.system.RoleMenu;
import org.frame.business.model.system.User;
import org.frame.business.model.system.UserDepartment;
import org.frame.business.model.system.UserMenu;
import org.frame.business.model.system.UserPost;
import org.frame.business.model.system.UserRole;
import org.frame.business.service.synchronization.ISynchronizationService;
import org.frame.business.system.Config;
import org.frame.common.util.Date;
import org.frame.common.xml.XMLBinding;
import org.frame.web.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service("synchronizationService")
@Scope("singleton")
public class SynchronizationService extends BaseService implements ISynchronizationService {
	
	File[] out;
	
	String suffix;
	
	@Resource
	@Autowired
	IDepartmentDao departmentDao;
	
	@Resource
	@Autowired
	IDepartmentMenuDao departmentMenuDao;
	
	@Resource
	@Autowired
	IDictionaryDao dictionaryDao;
	
	@Resource
	@Autowired
	IMenuDao menuDao;
	
	@Resource
	@Autowired
	IParameterDao parameterDao;
	
	@Resource
	@Autowired
	IPostDao postDao;
	
	@Resource
	@Autowired
	IPostMenuDao postMenuDao;
	
	@Resource
	@Autowired
	IRoleDao roleDao;
	
	@Resource
	@Autowired
	IRoleMenuDao roleMenuDao;
	
	@Resource
	@Autowired
	IUserDao userDao;
	
	@Resource
	@Autowired
	IUserDepartmentDao userDepartmentDao;
	
	@Resource
	@Autowired
	IUserMenuDao userMenuDao;
	
	@Resource
	@Autowired
	IUserPostDao userPostDao;
	
	@Resource
	@Autowired
	IUserRoleDao userRoleDao;
	
	public SynchronizationService() {
		File file;
		List<File> files = new ArrayList<File>();
		String paths = new Config(IBusinessConstant.DEFAULT_CONFIG_PROPERTIES).get(IBusinessConstant.SYNCHRONIZATION_OUT);
		if (paths != null && !"".equals(paths)) {
			for (String path : paths.split(",")) {
				if (!"".equals(paths)) {
					file = new File(path);
					if (!file.exists()) {
						file.mkdirs();
					}
					files.add(file);
				}
			}
		}
		out = files.toArray(new File[files.size()]);
		
		this.suffix = "_" + new Date().date2String("yyyyMMddHHmmss");
	}
	
	@Override
	public String exportDepartment() {
		String result = "";
		
		try {
			Config config = new Config(IBusinessConstant.DEFAULT_CONFIG_PROPERTIES);
			XMLBinding xmlBinding = new XMLBinding();
			
			Departments departments = new Departments();
			departments.setDepartments(departmentDao.select(null, null, null, null));
			
			result = xmlBinding.bean2xml(departments);
			
			String passage = config.get(IBusinessConstant.SYNCHRONIZATION_PASSAGE);
			if (passage != null && passage.contains(IBusinessConstant.SYNCHRONIZATION_PASSAGE_FILE)) {
				for (File file : out) {
					xmlBinding.xml2file(file.getAbsolutePath().replace("\\", "/") + "/department" + this.suffix + ".xml", result);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	@Override
	public String exportDepartmentMenu() {
		String result = "";
		
		try {
			Config config = new Config(IBusinessConstant.DEFAULT_CONFIG_PROPERTIES);
			XMLBinding xmlBinding = new XMLBinding();
			
			DepartmentMenus departmentMenus = new DepartmentMenus();
			departmentMenus.setDepartmentMenus(departmentMenuDao.select(null, null, null, null, null));
			
			result = xmlBinding.bean2xml(departmentMenus);
			
			String passage = config.get(IBusinessConstant.SYNCHRONIZATION_PASSAGE);
			if (passage != null && passage.contains(IBusinessConstant.SYNCHRONIZATION_PASSAGE_FILE)) {
				for (File file : out) {
					xmlBinding.xml2file(file.getAbsolutePath().replace("\\", "/") + "/departmentmenu" + this.suffix + ".xml", result);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	@Override
	public String exportDictionary() {
		String result = "";
		
		try {
			Config config = new Config(IBusinessConstant.DEFAULT_CONFIG_PROPERTIES);
			XMLBinding xmlBinding = new XMLBinding();
			
			Dictionarys dictionarys = new Dictionarys();
			dictionarys.setDictionarys(dictionaryDao.select(null, null, null, null));
			
			result = xmlBinding.bean2xml(dictionarys);
			
			String passage = config.get(IBusinessConstant.SYNCHRONIZATION_PASSAGE);
			if (passage != null && passage.contains(IBusinessConstant.SYNCHRONIZATION_PASSAGE_FILE)) {
				for (File file : out) {
					xmlBinding.xml2file(file.getAbsolutePath().replace("\\", "/") + "/dictionary" + this.suffix + ".xml", result);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	@Override
	public String exportMenu() {
		String result = "";
		
		try {
			Config config = new Config(IBusinessConstant.DEFAULT_CONFIG_PROPERTIES);
			XMLBinding xmlBinding = new XMLBinding();
			
			Menus menus = new Menus();
			menus.setMenus(menuDao.select(null, null, null, null, null, null));
			
			result = xmlBinding.bean2xml(menus);
			
			String passage = config.get(IBusinessConstant.SYNCHRONIZATION_PASSAGE);
			if (passage != null && passage.contains(IBusinessConstant.SYNCHRONIZATION_PASSAGE_FILE)) {
				for (File file : out) {
					xmlBinding.xml2file(file.getAbsolutePath().replace("\\", "/") + "/menu" + this.suffix + ".xml", result);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	@Override
	public String exportParameter() {
		String result = "";
		
		try {
			Config config = new Config(IBusinessConstant.DEFAULT_CONFIG_PROPERTIES);
			XMLBinding xmlBinding = new XMLBinding();
			
			Parameters parameters = new Parameters();
			parameters.setParameters(parameterDao.select(null, null));
			
			result = xmlBinding.bean2xml(parameters);
			
			String passage = config.get(IBusinessConstant.SYNCHRONIZATION_PASSAGE);
			if (passage != null && passage.contains(IBusinessConstant.SYNCHRONIZATION_PASSAGE_FILE)) {
				for (File file : out) {
					xmlBinding.xml2file(file.getAbsolutePath().replace("\\", "/") + "/parameter" + this.suffix + ".xml", result);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	@Override
	public String exportPost() {
		String result = "";
		
		try {
			Config config = new Config(IBusinessConstant.DEFAULT_CONFIG_PROPERTIES);
			XMLBinding xmlBinding = new XMLBinding();
			
			Posts posts = new Posts();
			posts.setPosts(postDao.select(null, null, null, null));
			
			result = xmlBinding.bean2xml(posts);
			
			String passage = config.get(IBusinessConstant.SYNCHRONIZATION_PASSAGE);
			if (passage != null && passage.contains(IBusinessConstant.SYNCHRONIZATION_PASSAGE_FILE)) {
				for (File file : out) {
					xmlBinding.xml2file(file.getAbsolutePath().replace("\\", "/") + "/post" + this.suffix + ".xml", result);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	@Override
	public String exportPostMenu() {
		String result = "";
		
		try {
			Config config = new Config(IBusinessConstant.DEFAULT_CONFIG_PROPERTIES);
			XMLBinding xmlBinding = new XMLBinding();
			
			PostMenus postMenus = new PostMenus();
			postMenus.setPostMenus(postMenuDao.select(null, null, null, null, null));
			
			result = xmlBinding.bean2xml(postMenus);
			
			String passage = config.get(IBusinessConstant.SYNCHRONIZATION_PASSAGE);
			if (passage != null && passage.contains(IBusinessConstant.SYNCHRONIZATION_PASSAGE_FILE)) {
				for (File file : out) {
					xmlBinding.xml2file(file.getAbsolutePath().replace("\\", "/") + "/postmenu" + this.suffix + ".xml", result);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	@Override
	public String exportRole() {
		String result = "";
		
		try {
			Config config = new Config(IBusinessConstant.DEFAULT_CONFIG_PROPERTIES);
			XMLBinding xmlBinding = new XMLBinding();
			
			Roles roles = new Roles();
			roles.setRoles(roleDao.select(null, null, null));
			
			result = xmlBinding.bean2xml(roles);
			
			String passage = config.get(IBusinessConstant.SYNCHRONIZATION_PASSAGE);
			if (passage != null && passage.contains(IBusinessConstant.SYNCHRONIZATION_PASSAGE_FILE)) {
				for (File file : out) {
					xmlBinding.xml2file(file.getAbsolutePath().replace("\\", "/") + "/role" + this.suffix + ".xml", result);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	@Override
	public String exportRoleMenu() {
		String result = "";
		
		try {
			Config config = new Config(IBusinessConstant.DEFAULT_CONFIG_PROPERTIES);
			XMLBinding xmlBinding = new XMLBinding();
			
			RoleMenus roleMenus = new RoleMenus();
			roleMenus.setRoleMenus(roleMenuDao.select(null, null, null, null, null));
			
			result = xmlBinding.bean2xml(roleMenus);
			
			String passage = config.get(IBusinessConstant.SYNCHRONIZATION_PASSAGE);
			if (passage != null && passage.contains(IBusinessConstant.SYNCHRONIZATION_PASSAGE_FILE)) {
				for (File file : out) {
					xmlBinding.xml2file(file.getAbsolutePath().replace("\\", "/") + "/rolemenu" + this.suffix + ".xml", result);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	@Override
	public String exportUser() {
		String result = "";
		
		try {
			Config config = new Config(IBusinessConstant.DEFAULT_CONFIG_PROPERTIES);
			XMLBinding xmlBinding = new XMLBinding();
			
			Users users = new Users();
			users.setUsers(userDao.select(null, null, null, null, null, null));
			
			result = xmlBinding.bean2xml(users);
			
			String passage = config.get(IBusinessConstant.SYNCHRONIZATION_PASSAGE);
			if (passage != null && passage.contains(IBusinessConstant.SYNCHRONIZATION_PASSAGE_FILE)) {
				for (File file : out) {
					xmlBinding.xml2file(file.getAbsolutePath().replace("\\", "/") + "/user" + this.suffix + ".xml", result);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	@Override
	public String exportUserDepartment() {
		String result = "";
		
		try {
			Config config = new Config(IBusinessConstant.DEFAULT_CONFIG_PROPERTIES);
			XMLBinding xmlBinding = new XMLBinding();
			
			UserDepartments userDepartments = new UserDepartments();
			userDepartments.setUserDepartments(userDepartmentDao.select(null, null, null, null, null));
			
			result = xmlBinding.bean2xml(userDepartments);
			
			String passage = config.get(IBusinessConstant.SYNCHRONIZATION_PASSAGE);
			if (passage != null && passage.contains(IBusinessConstant.SYNCHRONIZATION_PASSAGE_FILE)) {
				for (File file : out) {
					xmlBinding.xml2file(file.getAbsolutePath().replace("\\", "/") + "/userdepartment" + this.suffix + ".xml", result);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	@Override
	public String exportUserMenu() {
		String result = "";
		
		try {
			Config config = new Config(IBusinessConstant.DEFAULT_CONFIG_PROPERTIES);
			XMLBinding xmlBinding = new XMLBinding();
			
			UserMenus userMenus = new UserMenus();
			userMenus.setUserMenus(userMenuDao.select(null, null, null, null, null));
			
			result = xmlBinding.bean2xml(userMenus);
			
			String passage = config.get(IBusinessConstant.SYNCHRONIZATION_PASSAGE);
			if (passage != null && passage.contains(IBusinessConstant.SYNCHRONIZATION_PASSAGE_FILE)) {
				for (File file : out) {
					xmlBinding.xml2file(file.getAbsolutePath().replace("\\", "/") + "/usermenu" + this.suffix + ".xml", result);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	@Override
	public String exportUserPost() {
		String result = "";
		
		try {
			Config config = new Config(IBusinessConstant.DEFAULT_CONFIG_PROPERTIES);
			XMLBinding xmlBinding = new XMLBinding();
			
			UserPosts userPosts = new UserPosts();
			userPosts.setUserPosts(userPostDao.select(null, null, null, null, null));
			
			result = xmlBinding.bean2xml(userPosts);
			
			String passage = config.get(IBusinessConstant.SYNCHRONIZATION_PASSAGE);
			if (passage != null && passage.contains(IBusinessConstant.SYNCHRONIZATION_PASSAGE_FILE)) {
				for (File file : out) {
					xmlBinding.xml2file(file.getAbsolutePath().replace("\\", "/") + "/userpost" + this.suffix + ".xml", result);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	@Override
	public String exportUserRole() {
		String result = "";
		
		try {
			Config config = new Config(IBusinessConstant.DEFAULT_CONFIG_PROPERTIES);
			XMLBinding xmlBinding = new XMLBinding();
			
			UserRoles userRoles = new UserRoles();
			userRoles.setUserRoles(userRoleDao.select(null, null, null, null, null));
			
			result = xmlBinding.bean2xml(userRoles);
			
			String passage = config.get(IBusinessConstant.SYNCHRONIZATION_PASSAGE);
			if (passage != null && passage.contains(IBusinessConstant.SYNCHRONIZATION_PASSAGE_FILE)) {
				for (File file : out) {
					xmlBinding.xml2file(file.getAbsolutePath().replace("\\", "/") + "/userrole" + this.suffix + ".xml", result);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	@Override
	public void importDepartment(String info) {
		if (info != null && !"".equals(info) && !"null".equals(info)) {
			List<Map<String, Object>> parameters = new ArrayList<Map<String, Object>>();
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", null);
			
			parameters.add(map);
			
			departmentDao.delete(parameters);

			Departments departments = (Departments) new XMLBinding().xmlContent2bean(Departments.class, info);
			for (Department department : departments.getDepartments()) {
				departmentDao.insert(department);
			}
		}
	}

	@Override
	public void importDepartmentMenu(String info) {
		if (info != null && !"".equals(info) && !"null".equals(info)) {
			List<Map<String, Object>> parameters = new ArrayList<Map<String, Object>>();
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", null);
			map.put("departmentid", null);
			map.put("menuid", null);
			
			parameters.add(map);
			
			departmentMenuDao.delete(parameters);

			DepartmentMenus departmentMenus = (DepartmentMenus) new XMLBinding().xmlContent2bean(DepartmentMenus.class, info);
			for (DepartmentMenu departmentMenu : departmentMenus.getDepartmentMenus()) {
				departmentMenuDao.insert(departmentMenu);
			}
		}
	}

	@Override
	public void importDictionary(String info) {
		if (info != null && !"".equals(info) && !"null".equals(info)) {
			List<Map<String, Object>> parameters = new ArrayList<Map<String, Object>>();
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", null);
			
			parameters.add(map);
			
			dictionaryDao.delete(parameters);

			Dictionarys dictionarys = (Dictionarys) new XMLBinding().xmlContent2bean(Dictionarys.class, info);
			for (Dictionary dictionary : dictionarys.getDictionarys()) {
				dictionaryDao.insert(dictionary);
			}
		}
	}

	@Override
	public void importMenu(String info) {
		if (info != null && !"".equals(info) && !"null".equals(info)) {
			List<Map<String, Object>> parameters = new ArrayList<Map<String, Object>>();
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", null);
			
			parameters.add(map);
			
			menuDao.delete(parameters);

			Menus menus = (Menus) new XMLBinding().xmlContent2bean(Menus.class, info);
			for (Menu menu : menus.getMenus()) {
				menuDao.insert(menu);
			}
		}
	}

	@Override
	public void importParameter(String info) {
		if (info != null && !"".equals(info) && !"null".equals(info)) {
			List<Map<String, Object>> param = new ArrayList<Map<String, Object>>();
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", null);
			
			param.add(map);
			
			parameterDao.delete(param);

			Parameters parameters = (Parameters) new XMLBinding().xmlContent2bean(Parameters.class, info);
			for (Parameter parameter : parameters.getParameters()) {
				parameterDao.insert(parameter);
			}
		}
	}

	@Override
	public void importPost(String info) {
		if (info != null && !"".equals(info) && !"null".equals(info)) {
			List<Map<String, Object>> parameters = new ArrayList<Map<String, Object>>();
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", null);
			
			parameters.add(map);
			
			postDao.delete(parameters);

			Posts posts = (Posts) new XMLBinding().xmlContent2bean(Posts.class, info);
			for (Post post : posts.getPosts()) {
				postDao.insert(post);
			}
		}
	}

	@Override
	public void importPostMenu(String info) {
		if (info != null && !"".equals(info) && !"null".equals(info)) {
			List<Map<String, Object>> parameters = new ArrayList<Map<String, Object>>();
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", null);
			map.put("postid", null);
			map.put("menuid", null);
			
			parameters.add(map);
			
			postMenuDao.delete(parameters);

			PostMenus postMenus = (PostMenus) new XMLBinding().xmlContent2bean(PostMenus.class, info);
			for (PostMenu postMenu : postMenus.getPostMenus()) {
				postMenuDao.insert(postMenu);
			}
		}
	}

	@Override
	public void importRole(String info) {
		if (info != null && !"".equals(info) && !"null".equals(info)) {
			List<Map<String, Object>> parameters = new ArrayList<Map<String, Object>>();
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", null);
			
			parameters.add(map);
			
			roleDao.delete(parameters);

			Roles roles = (Roles) new XMLBinding().xmlContent2bean(Roles.class, info);
			for (Role role : roles.getRoles()) {
				roleDao.insert(role);
			}
		}
	}

	@Override
	public void importRoleMenu(String info) {
		if (info != null && !"".equals(info) && !"null".equals(info)) {
			List<Map<String, Object>> parameters = new ArrayList<Map<String, Object>>();
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", null);
			map.put("menuid", null);
			map.put("roleid", null);
			
			parameters.add(map);
			
			roleMenuDao.delete(parameters);

			RoleMenus roleMenus = (RoleMenus) new XMLBinding().xmlContent2bean(RoleMenus.class, info);
			for (RoleMenu roleMenu : roleMenus.getRoleMenus()) {
				roleMenuDao.insert(roleMenu);
			}
		}
	}

	@Override
	public void importUser(String info) {
		if (info != null && !"".equals(info) && !"null".equals(info)) {
			List<Map<String, Object>> parameters = new ArrayList<Map<String, Object>>();
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", null);
			
			parameters.add(map);
			
			userDao.delete(parameters);

			Users users = (Users) new XMLBinding().xmlContent2bean(Users.class, info);
			for (User user : users.getUsers()) {
				userDao.insert(user);
			}
		}
	}

	@Override
	public void importUserDepartment(String info) {
		if (info != null && !"".equals(info) && !"null".equals(info)) {
			List<Map<String, Object>> parameters = new ArrayList<Map<String, Object>>();
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", null);
			map.put("departmentid", null);
			map.put("userid", null);
			
			parameters.add(map);
			
			userDepartmentDao.delete(parameters);

			UserDepartments userDepartments = (UserDepartments) new XMLBinding().xmlContent2bean(UserDepartments.class, info);
			for (UserDepartment userDepartment : userDepartments.getUserDepartments()) {
				userDepartmentDao.insert(userDepartment);
			}
		}
	}

	@Override
	public void importUserMenu(String info) {
		if (info != null && !"".equals(info) && !"null".equals(info)) {
			List<Map<String, Object>> parameters = new ArrayList<Map<String, Object>>();
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", null);
			map.put("menuid", null);
			map.put("userid", null);
			
			parameters.add(map);
			
			userMenuDao.delete(parameters);

			UserMenus userMenus = (UserMenus) new XMLBinding().xmlContent2bean(UserMenus.class, info);
			for (UserMenu userMenu : userMenus.getUserMenus()) {
				userMenuDao.insert(userMenu);
			}
		}
	}

	@Override
	public void importUserPost(String info) {
		if (info != null && !"".equals(info) && !"null".equals(info)) {
			List<Map<String, Object>> parameters = new ArrayList<Map<String, Object>>();
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", null);
			map.put("postid", null);
			map.put("userid", null);
			
			parameters.add(map);
			
			userPostDao.delete(parameters);

			UserPosts userPosts = (UserPosts) new XMLBinding().xmlContent2bean(UserPosts.class, info);
			for (UserPost userPost : userPosts.getUserPosts()) {
				userPostDao.insert(userPost);
			}
		}
	}

	@Override
	public void importUserRole(String info) {
		if (info != null && !"".equals(info) && !"null".equals(info)) {
			List<Map<String, Object>> parameters = new ArrayList<Map<String, Object>>();
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", null);
			map.put("roleid", null);
			map.put("userid", null);
			
			parameters.add(map);
			
			userRoleDao.delete(parameters);

			UserRoles userRoles = (UserRoles) new XMLBinding().xmlContent2bean(UserRoles.class, info);
			for (UserRole userRole : userRoles.getUserRoles()) {
				userRoleDao.insert(userRole);
			}
		}
	}
	
}
