package org.frame.business.service.system.userdepartment.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.frame.business.constant.IBusinessConstant;
import org.frame.business.dao.system.department.IDepartmentDao;
import org.frame.business.dao.system.user.IUserDao;
import org.frame.business.dao.system.userdepartment.IUserDepartmentDao;
import org.frame.business.model.system.User;
import org.frame.business.model.system.UserDepartment;
import org.frame.business.service.synchronization.impl.SynchronizationService;
import org.frame.business.service.system.userdepartment.IUserDepartmentService;
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


@Repository("userDepartmentService")
@Scope("singleton")
public class UserDepartmentService extends BaseService implements IUserDepartmentService {
	
	private Log logger = LogFactory.getLog(UserDepartmentService.class);
	
	@Resource
	@Autowired
	IDepartmentDao departmentDao;
	
	@Resource
	@Autowired
	IUserDepartmentDao userDepartmentDao;
	
	@Resource
	@Autowired
	IUserDao userDao;

	@Override
	public String abandon(String ids, Integer flag) {
		String result = "";
		if (!StringHelper.isNull(ids)) {
			List<Map<String, Object>> parameters = new ArrayList<Map<String, Object>>();
			Map<String, Object> map;
			for (String id : ids.split(",")) {
				try {
					map = new HashMap<String, Object>();
					map.put("id", Long.parseLong(id));
					map.put("departmentid", null);
					map.put("userid", null);
					map.put("flag", flag);

					parameters.add(map);
				} catch (NumberFormatException e) {
					logger.error("id invalid: " + ids);
				}
			}

			result = new StringHelper().join(userDepartmentDao.abandon(parameters).toArray());
		}

		return result;
	}
	
	@Override
	public String abandon(Long id, Long userid, Long departmentid, Integer flag) {
		List<Map<String, Object>> parameters = new ArrayList<Map<String, Object>>();
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("departmentid", departmentid);
		map.put("userid", userid);
		map.put("flag", flag);

		parameters.add(map);
		
		return new StringHelper().join(userDepartmentDao.abandon(parameters).toArray());
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor = {Exception.class})
	public String assign(Long userid, String departments, Long departmentid, String users) {
		String result = null;
		
		String pattern = new Config(IBusinessConstant.DEFAULT_CONFIG_PROPERTIES).get(IBusinessConstant.RELATED_TYPE_DEPARTMENT).toLowerCase();
		
		if (userid != null && !"".equals(userid) && !"null".equals(userid)) {
			User user = userDao.find(userid);

			if (ISymbolConstant.FLAG_MULTI.equals(pattern)) {
				String[] department;
				if (!StringHelper.isNull(departments)) {
					department = departments.split(",");
				} else {
					department = new String[]{};
				}
				
				UserDepartment userDepartment = new UserDepartment();
				userDepartment.setFlag(user.getFlag());
				
				List<Map<String, Object>> parameters = new ArrayList<Map<String, Object>>();
				
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", null);
				map.put("departmentid", departmentid);
				map.put("userid", userid);

				parameters.add(map);
				
				result = new StringHelper().join(userDepartmentDao.delete(parameters).toArray());
				for (String id : department) {
					userDepartment.setUser(userid);
					userDepartment.setDepartment(Long.parseLong(id));

					result += "," + userDepartmentDao.insert(userDepartment);
				}
			}
			
			if (user != null) {
				user.setDepartment(departments);
				userDao.update(user);
			}
		}

		if (departmentid != null && !"".equals(departmentid) && !"null".equals(departmentid)) {
			String department = "";
			
			String[] user;
			if (!StringHelper.isNull(users)) {
				user = users.split(",");
			} else {
				user = new String[]{};
			}
			
			boolean effected = false;
			List<User> effectUsers = userDao.select(null, String.valueOf(departmentid), null, null);
			
			if (ISymbolConstant.FLAG_MULTI.equals(pattern)) {
				UserDepartment userDepartment = new UserDepartment();
				userDepartment.setFlag(departmentDao.find(departmentid).getFlag());
				
				List<Map<String, Object>> parameters = new ArrayList<Map<String, Object>>();
				
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", null);
				map.put("departmentid", departmentid);
				map.put("userid", userid);

				parameters.add(map);
				
				result = new StringHelper().join(userDepartmentDao.delete(parameters).toArray());
				
				parameters.clear();
				for (String id : user) {
					userDepartment.setUser(Long.parseLong(id));
					userDepartment.setDepartment(departmentid);

					if (ISymbolConstant.FLAG_SINGLE.equals(pattern)) {
						map = new HashMap<String, Object>();
						map.put("id", null);
						map.put("departmentid", null);
						map.put("userid", Long.parseLong(id));

						parameters.add(map);
						
						userDepartmentDao.delete(parameters);
					}

					result += "," + userDepartmentDao.insert(userDepartment);
					
					effected = false;
					
					for (User effectUser : effectUsers) {
						if (effectUser.getId() == Long.parseLong(id)) {
							effected = true;
							break;
						}
					}
					
					if (!effected) {
						User u = userDao.find(Long.parseLong(id));
						effectUsers.add(u);
					}
					
				}
			} else {
				for (String id : user) {
					for (User effectUser : effectUsers) {
						if (effectUser.getId() == Long.parseLong(id)) {
							effected = true;
							break;
						}
					}
					
					if (!effected) {
						User u = userDao.find(Long.parseLong(id));
						effectUsers.add(u);
					}
				}
			}
			
			for (User effectUser : effectUsers) {
				List<UserDepartment> data = this.query(effectUser.getId(), null, null, null, null);
				if (data != null) {
					department = "";
					for (UserDepartment ud : data) {
						if ("".equals(department)) {
							department = String.valueOf(ud.getDepartment());
						} else {
							department += "," + String.valueOf(ud.getDepartment());
						}
					}
				}

				if (StringHelper.isNull(department)) department = null;
				
				effectUser.setDepartment(department);
				userDao.update(effectUser);
			}
		}

		return result;
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor = {Exception.class})
	public Long create(UserDepartment userDepartment) {
		Long result = userDepartmentDao.insert(userDepartment);

		String department = "";
		List<UserDepartment> data = this.query(userDepartment.getUser(), null, null, null, null);
		if (data != null) {
			for (UserDepartment ud : data) {
				if ("".equals(department)) {
					department = String.valueOf(ud.getDepartment());
				} else {
					department += "," + String.valueOf(ud.getDepartment());
				}
			}

			User user = userDao.find(userDepartment.getUser());
			if (user != null) {
				user.setDepartment(department);
				userDao.update(user);
			}
		}
		
		return result;
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor = {Exception.class})
	public Long edit(UserDepartment userDepartment) {
		Integer result = userDepartmentDao.update(userDepartment);

		String department = "";
		List<UserDepartment> data = this.query(userDepartment.getUser(), null, null, null, null);
		if (data != null) {
			for (UserDepartment ud : data) {
				if ("".equals(department)) {
					department = String.valueOf(ud.getDepartment());
				} else {
					department += "," + String.valueOf(ud.getDepartment());
				}
			}

			User user = userDao.find(userDepartment.getUser());
			if (user != null) {
				user.setDepartment(department);
				userDao.update(user);
			}
		}
		
		return result > 0 ? userDepartment.getId() : result.longValue();
	}
	
	@Override
	public Long modify(UserDepartment userDepartment) {
		if (StringHelper.isNull(String.valueOf(userDepartment.getId())))
			return this.create(userDepartment);
		else
			return this.edit(userDepartment);
	}

	@Override
	public Page pagination(Long userid, String user, Long departmentid, String department, Integer flag, Page page) {
		return userDepartmentDao.pagination(userid, user, departmentid, department, flag, page);
	}
	
	@Override
	public List<UserDepartment> query(Long userid, String user, Long departmentid, String department, Integer flag) {
		return userDepartmentDao.select(userid, user, departmentid, department, flag);
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor = {Exception.class})
	public String remove(String ids) {
		String result = "";
		if (!StringHelper.isNull(ids)) {
			for (String id : ids.split(",")) {
				UserDepartment userDepartment = this.search(Long.parseLong(id));
				
				List<Map<String, Object>> parameters = new ArrayList<Map<String, Object>>();
				
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", Long.parseLong(id));
				map.put("departmentid", null);
				map.put("userid", null);

				parameters.add(map);
				
				result += new StringHelper().join(userDepartmentDao.delete(parameters).toArray()) + ",";

				String department = "";
				List<UserDepartment> data = this.query(userDepartment.getUser(), null, null, null, null);
				if (data != null) {
					for (UserDepartment ud : data) {
						if ("".equals(department)) {
							department = String.valueOf(ud.getDepartment());
						} else {
							department += "," + String.valueOf(ud.getDepartment());
						}
					}

					User user = userDao.find(userDepartment.getUser());
					if (user != null) {
						user.setDepartment(department);
						userDao.update(user);
					}
				}

				result = result.substring(0, result.length() - 1);
			}
		}
		
		return result;
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor = {Exception.class})
	public String remove(Long id, Long userid, Long departmentid) {
		String result = null;
		if (id != null) {
			UserDepartment userDepartment = this.search(id);
			if (userDepartment != null) {
				userid = userDepartment.getUser();
			}
		}

		List<UserDepartment> data = this.query(userid, null, departmentid, null, null);

		List<Map<String, Object>> parameters = new ArrayList<Map<String, Object>>();
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("departmentid", departmentid);
		map.put("userid", userid);

		parameters.add(map);
		
		result = new StringHelper().join(userDepartmentDao.delete(parameters).toArray());

		if (data != null) {
			String department = "";
			List<Long> lstUser = new ArrayList<Long>();
			if (userid == null) {
				for (UserDepartment userDepartment : data) {
					if (!lstUser.contains(userDepartment.getUser())) lstUser.add(userDepartment.getUser());
				}
			} else {
				if (!lstUser.contains(userid)) lstUser.add(userid);
			}

			for (Long uid : lstUser) {
				department = "";
				List<UserDepartment> lstData;

				if (userid == null) {
					lstData = this.query(uid, null, null, null, null);
				} else {
					lstData = data;
				}

				if (lstData != null) {
					for (UserDepartment userDepartment : lstData) {
						if ("".equals(department)) {
							department = String.valueOf(userDepartment.getDepartment());
						} else {
							department += "," + String.valueOf(userDepartment.getDepartment());
						}
					}

					User user = userDao.find(uid);
					if (user != null) {
						user.setDepartment(department);
						userDao.update(user);
					}
				}
			}
		}

		return result;
	}
	
	@Override
	public UserDepartment search(Long id) {
		return userDepartmentDao.find(id);
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
						xmlBinding.xml2file(out.getAbsolutePath() + "/export_userdepartment" + suffix + ".xml", "<?xml version=\"1.0\" encoding=\"UTF-8\"?><export/>");
					}
				}
			}
		}
		
		if (passage != null && passage.contains(IBusinessConstant.SYNCHRONIZATION_PASSAGE_WEBSERVICE)) {
			String url = config.get(IBusinessConstant.SYNCHRONIZATION_URL);
			if (url != null && !"".equals(url) && !"null".equals(url)) {
				String namespace = "http://impl.synchronization.server.webservice.frame.org";
				
				Client client = new Client();
				Object[] info = client.invoke(url, namespace, "exportUserDepartment", new Class<?>[]{}, new Object[]{});
				if (info != null && info.length > 0) {
					new SynchronizationService().importUserDepartment(String.valueOf(info[0]));
				} else {
					System.err.println("no userdepartment info found.");
				}
			}
		}
		
		result = true;
		
		return result;
	}
	
}
