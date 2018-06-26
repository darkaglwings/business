package org.frame.business.service.system.userrole.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.frame.business.constant.IBusinessConstant;
import org.frame.business.dao.system.role.IRoleDao;
import org.frame.business.dao.system.user.IUserDao;
import org.frame.business.dao.system.userrole.IUserRoleDao;
import org.frame.business.model.system.UserRole;
import org.frame.business.service.synchronization.impl.SynchronizationService;
import org.frame.business.service.system.userrole.IUserRoleService;
import org.frame.business.system.Config;
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


@Repository("userRoleService")
@Scope("singleton")
public class UserRoleService extends BaseService implements IUserRoleService {
	
	private Log logger = LogFactory.getLog(UserRoleService.class);
	
	@Resource
	@Autowired
	IRoleDao roleDao;
	
	@Resource
	@Autowired
	IUserDao userDao;
	
	@Resource
	@Autowired
	IUserRoleDao userRoleDao;

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
					map.put("roleid", null);
					map.put("userid", null);
					map.put("flag", flag);

					parameters.add(map);
				} catch (NumberFormatException e) {
					logger.error("id invalid: " + ids);
				}
			}

			result = new StringHelper().join(userRoleDao.abandon(parameters).toArray());
		}

		return result;
	}
	
	@Override
	public String abandon(Long id, Long userid, Long roleid, Integer flag) {
		List<Map<String, Object>> parameters = new ArrayList<Map<String, Object>>();
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("roleid", roleid);
		map.put("userid", userid);
		map.put("flag", flag);
		
		return new StringHelper().join(userRoleDao.abandon(parameters).toArray());
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor = {Exception.class})
	public String assign(Long userid, String roles, Long roleid, String users) {
		String result = null;
		
		List<Map<String, Object>> parameters = new ArrayList<Map<String, Object>>();
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", null);
		map.put("roleid", roleid);
		map.put("userid", userid);
		
		result = new StringHelper().join(userRoleDao.delete(parameters).toArray());
		
		if (userid != null && !"".equals(userid) && !"null".equals(userid)) {
			String[] role;
			if (!StringHelper.isNull(roles)) {
				role = roles.split(",");
			} else {
				role = new String[]{};
			}
			UserRole userRole = new UserRole();
			userRole.setFlag(userDao.find(userid).getFlag());
			for (String id : role) {
				userRole.setUser(userid);
				userRole.setRole(Long.parseLong(id));

				result += "," + userRoleDao.insert(userRole);
			}
		}

		if (roleid != null && !"".equals(roleid) && !"null".equals(roleid)) {
			String[] user;
			if (!StringHelper.isNull(users)) {
				user = users.split(",");
			} else {
				user = new String[]{};
			}
			UserRole userRole = new UserRole();
			userRole.setFlag(roleDao.find(roleid).getFlag());
			for (String id : user) {
				userRole.setUser(Long.parseLong(id));
				userRole.setRole(roleid);

				result += "," + userRoleDao.insert(userRole);
			}
		}
		
		return result;
	}
	
	@Override
	public Long create(UserRole userRole) {
		return userRoleDao.insert(userRole);
	}
	
	@Override
	public Long edit(UserRole userRole) {
		Integer result = userRoleDao.update(userRole);
		return result > 0 ? userRole.getId() : result.longValue();
	}
	
	@Override
	public Long modify(UserRole userRole) {
		if (StringHelper.isNull(String.valueOf(userRole.getId())))
			return this.create(userRole);
		else
			return this.edit(userRole);
	}

	@Override
	public Page pagination(Long userid, String user, Long roleid, String role, Integer flag, Page page) {
		return userRoleDao.pagination(userid, user, roleid, role, flag, page);
	}
	
	@Override
	public List<UserRole> query(Long userid, String user, Long roleid, String role, Integer flag) {
		return userRoleDao.select(userid, user, roleid, role, flag);
	}
	
	@Override
	public String remove(String ids) {
		String result = "";
		if (!StringHelper.isNull(ids)) {
			List<Map<String, Object>> parameters = new ArrayList<Map<String, Object>>();
			Map<String, Object> map;
			for (String id : ids.split(",")) {
				try {
					map = new HashMap<String, Object>();
					map.put("id", Long.parseLong(id));
					map.put("roleid", null);
					map.put("userid", null);

					parameters.add(map);
				} catch (NumberFormatException e) {
					logger.error("id invalid: " + ids);
				}
			}

			result = new StringHelper().join(userRoleDao.delete(parameters).toArray());
		}

		return result;
	}
	
	@Override
	public String remove(Long id, Long userid, Long roleid) {
		List<Map<String, Object>> parameters = new ArrayList<Map<String, Object>>();
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("roleid", roleid);
		map.put("userid", userid);
		
		return new StringHelper().join(userRoleDao.delete(parameters).toArray());
	}
	
	@Override
	public UserRole search(Long id) {
		return userRoleDao.find(id);
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
						xmlBinding.xml2file(out.getAbsolutePath() + "/export_userrole" + suffix + ".xml", "<?xml version=\"1.0\" encoding=\"UTF-8\"?><export/>");
					}
				}
			}
		}
		
		if (passage != null && passage.contains(IBusinessConstant.SYNCHRONIZATION_PASSAGE_WEBSERVICE)) {
			String url = config.get(IBusinessConstant.SYNCHRONIZATION_URL);
			if (url != null && !"".equals(url) && !"null".equals(url)) {
				String namespace = "http://impl.synchronization.server.webservice.frame.org";
				
				Client client = new Client();
				Object[] info = client.invoke(url, namespace, "exportUserRole", new Class<?>[]{}, new Object[]{});
				if (info != null && info.length > 0) {
					new SynchronizationService().importUserRole(String.valueOf(info[0]));
				} else {
					System.err.println("no userrole info found.");
				}
			}
		}
		
		result = true;
		
		return result;
	}
	
}
