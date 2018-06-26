package org.frame.business.service.system.user.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.frame.business.constant.IBusinessConstant;
import org.frame.business.dao.system.user.IUserDao;
import org.frame.business.dao.system.userdepartment.IUserDepartmentDao;
import org.frame.business.dao.system.usermenu.IUserMenuDao;
import org.frame.business.dao.system.userpost.IUserPostDao;
import org.frame.business.dao.system.userrole.IUserRoleDao;
import org.frame.business.model.system.User;
import org.frame.business.model.system.UserDepartment;
import org.frame.business.model.system.UserPost;
import org.frame.business.service.synchronization.impl.SynchronizationService;
import org.frame.business.service.system.user.IUserService;
import org.frame.business.system.Config;
import org.frame.common.constant.ISymbolConstant;
import org.frame.common.lang.StringHelper;
import org.frame.common.util.Date;
import org.frame.common.webservice.client.Client;
import org.frame.common.xml.XMLBinding;
import org.frame.repository.sql.model.Page;
import org.frame.web.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Repository("userService")
@Scope("singleton")
public class UserService extends BaseService implements IUserService{

	@SuppressWarnings("unused")
	private Log logger = LogFactory.getLog(UserService.class);
	
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
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor = {Exception.class})
	public String abandon(String ids, Integer flag) {
		String result = "";
		if (!StringHelper.isNull(ids)) {
			String related = new Config(IBusinessConstant.DEFAULT_CONFIG_PROPERTIES).get(IBusinessConstant.USER_RELATED).toLowerCase();
			if (related == null) related = "";
			
			List<Map<String, Object>> parameters = new ArrayList<Map<String, Object>>();
			Map<String, Object> map;

			if ("all".equals(related) || related.contains("department")) {
				parameters.clear();
				for (String id : ids.split(",")) {
					map = new HashMap<String, Object>();
					map.put("id", null);
					map.put("departmentid", null);
					map.put("userid", Long.parseLong(id));
					map.put("flag", flag);

					parameters.add(map);
				}

				userDepartmentDao.abandon(parameters);
			}

			if ("all".equals(related) || related.contains("menu")) {
				parameters.clear();
				for (String id : ids.split(",")) {
					map = new HashMap<String, Object>();
					map.put("id", null);
					map.put("menuid", null);
					map.put("userid", Long.parseLong(id));
					map.put("flag", flag);

					parameters.add(map);
				}

				userMenuDao.abandon(parameters);
			}

			if ("all".equals(related) || related.contains("post")) {
				parameters.clear();
				for (String id : ids.split(",")) {
					map = new HashMap<String, Object>();
					map.put("id", null);
					map.put("postid", null);
					map.put("userid", Long.parseLong(id));
					map.put("flag", flag);

					parameters.add(map);
				}

				userPostDao.abandon(parameters);
			}

			if ("all".equals(related) || related.contains("role")) {
				parameters.clear();
				for (String id : ids.split(",")) {
					map = new HashMap<String, Object>();
					map.put("id", null);
					map.put("roleid", null);
					map.put("userid", Long.parseLong(id));
					map.put("flag", flag);

					parameters.add(map);
				}

				userRoleDao.abandon(parameters);
			}

			parameters.clear();
			for (String id : ids.split(",")) {
				map = new HashMap<String, Object>();
				map.put("id", Long.parseLong(id));
				map.put("flag", flag);

				parameters.add(map);
			}

			result = new StringHelper().join(userDao.abandon(parameters).toArray());
		}

		return result;
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor = {Exception.class})
	public Long create(User user) {
		Long result = userDao.insert(user);

		Config config = new Config(IBusinessConstant.DEFAULT_CONFIG_PROPERTIES);
		String related = config.get(IBusinessConstant.USER_RELATED).toLowerCase();
		if (related == null) related = "";
		String departmentPattern = config.get(IBusinessConstant.RELATED_TYPE_DEPARTMENT).toLowerCase();
		String postPattern = config.get(IBusinessConstant.RELATED_TYPE_POST).toLowerCase();
		
		if (!StringHelper.isNull(related)) {
			Long id;
			List<User> lstUser = this.query(user.getTitle(), user.getUsername(), user.getSex(), user.getDepartment(), user.getPost(), user.getFlag());
			if (lstUser != null && lstUser.size() > 0) {
				id = lstUser.get(lstUser.size() - 1).getId();
			} else {
				id = null;
			}

			if (id != null) {
				if (ISymbolConstant.FLAG_ALL.equals(related) || related.contains("department")) {
					if (ISymbolConstant.FLAG_MULTI.equals(departmentPattern)) {
						if (!StringHelper.isNull(user.getDepartment())) {
							//userDepartmentDao.delete(null, id, null);
							for (String departmentid : user.getDepartment().split(",")) {
								UserDepartment userDepartment = new UserDepartment();
								userDepartment.setUser(id);
								userDepartment.setDepartment(Long.parseLong(departmentid));
								userDepartment.setFlag(user.getFlag());

								userDepartmentDao.insert(userDepartment);
							}
						}
					}
				}

				if (ISymbolConstant.FLAG_ALL.equals(related) || related.contains("post")) {
					if (ISymbolConstant.FLAG_MULTI.equals(postPattern)) {
						if (!StringHelper.isNull(user.getPost())) {
							//userPostDao.delete(null, id, null);
							for (String postid : user.getPost().split(",")) {
								UserPost userPost = new UserPost();
								userPost.setUser(id);
								userPost.setPost(Long.parseLong(postid));
								userPost.setFlag(user.getFlag());

								userPostDao.insert(userPost);
							}
						}
					}
				}
			}
		}
		
		return result;
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor = {Exception.class})
	public Long edit(User user) {
		Integer result = userDao.update(user);

		Config config = new Config(IBusinessConstant.DEFAULT_CONFIG_PROPERTIES);
		String related = config.get(IBusinessConstant.USER_RELATED).toLowerCase();
		if (related == null) related = "";
		String departmentPattern = config.get(IBusinessConstant.RELATED_TYPE_DEPARTMENT).toLowerCase();
		String postPattern = config.get(IBusinessConstant.RELATED_TYPE_POST).toLowerCase();

		if (!StringHelper.isNull(related)) {
			Long id = user.getId();

			List<Map<String, Object>> parameters = new ArrayList<Map<String, Object>>();
			Map<String, Object> map;
			
			if (id != null) {
				if ("all".equals(related) || related.contains("department")) {
					if (ISymbolConstant.FLAG_MULTI.equals(departmentPattern)) {
						if (!StringHelper.isNull(user.getDepartment())) {
							parameters.clear();
							
							map = new HashMap<String, Object>();
							map.put("id", null);
							map.put("departmentid", null);
							map.put("userid", id);
							
							parameters.add(map);
							
							userDepartmentDao.delete(parameters);
							
							for (String departmentid : user.getDepartment().split(",")) {
								UserDepartment userDepartment = new UserDepartment();
								userDepartment.setUser(id);
								userDepartment.setDepartment(Long.parseLong(departmentid));
								userDepartment.setFlag(user.getFlag());

								userDepartmentDao.insert(userDepartment);
							}
						}
					}
				}

				if ("all".equals(related) || related.contains("post")) {
					if (ISymbolConstant.FLAG_MULTI.equals(postPattern)) {
						if (!StringHelper.isNull(user.getPost())) {
							parameters.clear();
							
							map = new HashMap<String, Object>();
							map.put("id", null);
							map.put("postid", null);
							map.put("userid", id);
							
							parameters.add(map);
							
							userPostDao.delete(parameters);
							for (String postid : user.getPost().split(",")) {
								UserPost userPost = new UserPost();
								userPost.setUser(id);
								userPost.setPost(Long.parseLong(postid));
								userPost.setFlag(user.getFlag());

								userPostDao.insert(userPost);
							}
						}
					}
				}
			}
		}
		
		return result > 0 ? user.getId() : result.longValue();
	}
	
	@Override
	public Long modify(User user) {
		if (StringHelper.isNull(String.valueOf(user.getId())))
			return this.create(user);
		else
			return this.edit(user);
	}
	
	@Override
	public Page pagination(String title, String username, String sex, String department, String post, Integer flag, Page page) {
		return userDao.pagination(title, username, sex, department, post, flag, page);
	}

	@Override
	public List<User> query(Long id, String department, String post, Integer flag) {
		return userDao.select(id, department, post, flag);
	}
	
	@Override
	public List<User> query(String title, String username, String sex, String department, String post, Integer flag) {
		return userDao.select(title, username, sex, department, post, flag);
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor = {Exception.class})
	public String remove(String ids) {
		String result = "";
		if (!StringHelper.isNull(ids)) {
			Config config = new Config(IBusinessConstant.DEFAULT_CONFIG_PROPERTIES);
			String related = config.get(IBusinessConstant.USER_RELATED).toLowerCase();
			if (related == null) related = "";
			String departmentPattern = config.get(IBusinessConstant.RELATED_TYPE_DEPARTMENT).toLowerCase();
			String postPattern = config.get(IBusinessConstant.RELATED_TYPE_POST).toLowerCase();
			
			List<Map<String, Object>> parameters = new ArrayList<Map<String, Object>>();
			Map<String, Object> map;
			
				if (!StringHelper.isNull(related)) {
					if ((ISymbolConstant.FLAG_ALL.equals(related) || related.contains("department")) && ISymbolConstant.FLAG_MULTI.equals(departmentPattern)) {
						parameters.clear();
						for (String id : ids.split(",")) {
							map = new HashMap<String, Object>();
							map.put("id", null);
							map.put("departmentid", null);
							map.put("userid", Long.parseLong(id));

							parameters.add(map);
						}
						
						userDepartmentDao.delete(parameters);
					}
					
					if (ISymbolConstant.FLAG_ALL.equals(related) || related.contains("menu")) {
						parameters.clear();
						for (String id : ids.split(",")) {
							map = new HashMap<String, Object>();
							map.put("id", null);
							map.put("menuid", null);
							map.put("userid", Long.parseLong(id));

							parameters.add(map);
						}
						
						userMenuDao.delete(parameters);
					}
					
					if ((ISymbolConstant.FLAG_ALL.equals(related) || related.contains("post")) && ISymbolConstant.FLAG_MULTI.equals(postPattern)) {
						parameters.clear();
						for (String id : ids.split(",")) {
							map = new HashMap<String, Object>();
							map.put("id", null);
							map.put("postid", null);
							map.put("userid", Long.parseLong(id));

							parameters.add(map);
						}
						
						userPostDao.delete(parameters);
					}
					
					if (ISymbolConstant.FLAG_ALL.equals(related) || related.contains("role")) {
						parameters.clear();
						for (String id : ids.split(",")) {
							map = new HashMap<String, Object>();
							map.put("id", null);
							map.put("roleid", null);
							map.put("userid", Long.parseLong(id));

							parameters.add(map);
						}
						
						userRoleDao.delete(parameters);
					}

				}

				parameters.clear();
				for (String id : ids.split(",")) {
					map = new HashMap<String, Object>();
					map.put("id", Long.parseLong(id));

					parameters.add(map);
				}
				
				result = new StringHelper().join(userDao.delete(parameters).toArray());
		}
		
		return result;
	}
	
	@Override
	public User search(Long id) {
		return userDao.find(id);
	}
	
	@Override
	public boolean synchronization() {
		boolean result = false;
		
		Config config = new Config(IBusinessConstant.DEFAULT_CONFIG_PROPERTIES);
		String passage = config.get(IBusinessConstant.SYNCHRONIZATION_PASSAGE);
		if (passage != null && passage.contains(IBusinessConstant.SYNCHRONIZATION_PASSAGE_FILE)) {
			File out;
			String paths = config.get(IBusinessConstant.SYNCHRONIZATION_OUT);
			if (paths != null && !"".equals(paths)) {
				XMLBinding xmlBinding = new XMLBinding();
				String suffix = "_" + new Date().date2String("yyyyMMddHHmmss");
				for(String path : paths.split(",")) {
					if (path != null && !"".equals(path)) {
						out = new File(path);
						if (!out.exists()) out.mkdirs();
						xmlBinding.xml2file(out.getAbsolutePath() + "/export_user" + suffix + ".xml", "<?xml version=\"1.0\" encoding=\"UTF-8\"?><export/>");
					}
				}
			}
		}
		
		if (passage != null && passage.contains(IBusinessConstant.SYNCHRONIZATION_PASSAGE_WEBSERVICE)) {
			String url = config.get(IBusinessConstant.SYNCHRONIZATION_URL);
			if (url != null && !"".equals(url) && !"null".equals(url)) {
				String namespace = "http://impl.synchronization.server.webservice.frame.org";
				
				Client client = new Client();
				Object[] info = client.invoke(url, namespace, "exportUser", new Class<?>[]{}, new Object[]{});
				if (info != null && info.length > 0) {
					new SynchronizationService().importUser(String.valueOf(info[0]));
				} else {
					System.err.println("no user info found.");
				}
			}
		}
		
		result = true;
		
		return result;
	}
	
}
