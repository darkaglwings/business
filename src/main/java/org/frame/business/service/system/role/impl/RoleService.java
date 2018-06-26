package org.frame.business.service.system.role.impl;

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
import org.frame.business.dao.system.rolemenu.IRoleMenuDao;
import org.frame.business.dao.system.userrole.IUserRoleDao;
import org.frame.business.model.system.Role;
import org.frame.business.service.synchronization.impl.SynchronizationService;
import org.frame.business.service.system.role.IRoleService;
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


@Repository("roleService")
@Scope("singleton")
public class RoleService extends BaseService implements IRoleService {
	
	@SuppressWarnings("unused")
	private Log logger = LogFactory.getLog(RoleService.class);
	
	@Resource
	@Autowired
	IRoleDao roleDao;
	
	@Resource
	@Autowired
	IRoleMenuDao roleMenuDao;
	
	@Resource
	@Autowired
	IUserRoleDao userRoleDao;

	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor = {Exception.class})
	public String abandon(String ids, Integer flag) {
		String result = "";
		if (!StringHelper.isNull(ids)) {
			boolean menu = false, user = false;

			Config config = new Config(IBusinessConstant.DEFAULT_CONFIG_PROPERTIES);
			String related = config.get(IBusinessConstant.MENU_RELATED).toLowerCase();
			if (related == null) related = "";
			if ("all".equals(related) || related.contains("role")) {
				menu = true;
			}

			related = config.get(IBusinessConstant.USER_RELATED).toLowerCase();
			if (related == null) related = "";
			if ("all".equals(related) || related.contains("role")) {
				user = true;
			}

			List<Map<String, Object>> parameters = new ArrayList<Map<String, Object>>();
			Map<String, Object> map;

			if (menu) {
				parameters.clear();
				for (String id : ids.split(",")) {
					map = new HashMap<String, Object>();
					map.put("id", null);
					map.put("menuid", null);
					map.put("roleid", Long.parseLong(id));
					map.put("flag", flag);

					parameters.add(map);
				}

				roleMenuDao.abandon(parameters);
			}

			if (user) {
				parameters.clear();
				for (String id : ids.split(",")) {
					map = new HashMap<String, Object>();
					map.put("id", null);
					map.put("roleid", Long.parseLong(id));
					map.put("userid", null);
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

			result = new StringHelper().join(roleDao.abandon(parameters).toArray());
		}
		
		return result;
	}
	
	@Override
	public Long create(Role role) {
		return roleDao.insert(role);
	}
	
	@Override
	public Long edit(Role role) {
		Integer result = roleDao.update(role);
		return result > 0 ? role.getId() : result.longValue();
	}
	
	@Override
	public Long modify(Role role) {
		if (StringHelper.isNull(String.valueOf(role.getId())))
			return this.create(role);
		else
			return this.edit(role);
	}
	
	@Override
	public Page pagination(String title, Long rank, Integer flag, Page page) {
		return roleDao.pagination(title, rank, flag, page);
	}

	@Override
	public List<Role> query(Long userid) {
		return roleDao.select(userid);
	}
	
	@Override
	public List<Role> query(String title, Long rank, Integer flag) {
		return roleDao.select(title, rank, flag);
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor = {Exception.class})
	public String remove(String ids) {
		String result = "";
		if (!StringHelper.isNull(ids)) {
			boolean menu = false, user = false;

			Config config = new Config(IBusinessConstant.DEFAULT_CONFIG_PROPERTIES);
			String related = config.get(IBusinessConstant.MENU_RELATED).toLowerCase();
			if (related == null) related = "";
			if ("all".equals(related) || related.contains("role")) {
				menu = true;
			}

			related = config.get(IBusinessConstant.USER_RELATED).toLowerCase();
			if (related == null) related = "";
			if ("all".equals(related) || related.contains("role")) {
				user = true;
			}

			List<Map<String, Object>> parameters = new ArrayList<Map<String, Object>>();
			Map<String, Object> map;

			if (menu) {
				parameters.clear();
				for (String id : ids.split(",")) {
					map = new HashMap<String, Object>();
					map.put("id", null);
					map.put("menuid", null);
					map.put("roleid", Long.parseLong(id));

					parameters.add(map);
				}

				roleMenuDao.delete(parameters);
			}

			if (user) {
				parameters.clear();
				for (String id : ids.split(",")) {
					map = new HashMap<String, Object>();
					map.put("id", null);
					map.put("roleid", Long.parseLong(id));
					map.put("userid", null);

					parameters.add(map);
				}

				userRoleDao.delete(parameters);
			}

			parameters.clear();
			for (String id : ids.split(",")) {
				map = new HashMap<String, Object>();
				map.put("id", Long.parseLong(id));

				parameters.add(map);
			}

			result = new StringHelper().join(roleDao.delete(parameters).toArray());
		}
		
		return result;
	}
	
	@Override
	public Role search(Long id) {
		return roleDao.find(id);
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
						xmlBinding.xml2file(out.getAbsolutePath() + "/export_role" + suffix + ".xml", "<?xml version=\"1.0\" encoding=\"UTF-8\"?><export/>");
					}
				}
			}
		}
		
		if (passage != null && passage.contains(IBusinessConstant.SYNCHRONIZATION_PASSAGE_WEBSERVICE)) {
			String url = config.get(IBusinessConstant.SYNCHRONIZATION_URL);
			if (url != null && !"".equals(url) && !"null".equals(url)) {
				String namespace = "http://impl.synchronization.server.webservice.frame.org";
				
				Client client = new Client();
				Object[] info = client.invoke(url, namespace, "exportRole", new Class<?>[]{}, new Object[]{});
				if (info != null && info.length > 0) {
					new SynchronizationService().importRole(String.valueOf(info[0]));
				} else {
					System.err.println("no role info found.");
				}
			}
		}
		
		result = true;
		
		return result;
	}
	
}
