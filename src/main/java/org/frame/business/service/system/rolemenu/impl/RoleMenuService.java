package org.frame.business.service.system.rolemenu.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.frame.business.constant.IBusinessConstant;
import org.frame.business.dao.system.menu.IMenuDao;
import org.frame.business.dao.system.role.IRoleDao;
import org.frame.business.dao.system.rolemenu.IRoleMenuDao;
import org.frame.business.model.system.RoleMenu;
import org.frame.business.service.synchronization.impl.SynchronizationService;
import org.frame.business.service.system.rolemenu.IRoleMenuService;
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


@Repository("roleMenuService")
@Scope("singleton")
public class RoleMenuService extends BaseService implements IRoleMenuService {
	
	private Log logger = LogFactory.getLog(RoleMenuService.class);
	
	@Resource
	@Autowired
	IMenuDao menuDao;
	
	@Resource
	@Autowired
	IRoleDao roleDao;
	
	@Resource
	@Autowired
	IRoleMenuDao roleMenuDao;
	
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
					map.put("menuid", null);
					map.put("roleid", null);
					map.put("flag", flag);

					parameters.add(map);
				} catch (NumberFormatException e) {
					logger.error("id invalid: " + ids);
				}
			}

			result = new StringHelper().join(roleMenuDao.abandon(parameters).toArray());
		}

		return result;
	}

	@Override
	public String abandon(Long id, Long roleid, Long menuid, Integer flag) {
		List<Map<String, Object>> parameters = new ArrayList<Map<String, Object>>();

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("menuid", menuid);
		map.put("roleid", roleid);
		map.put("flag", flag);
		
		return new StringHelper().join(roleMenuDao.abandon(parameters).toArray());
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor = {Exception.class})
	public String assign(Long roleid, String menus, Long menuid, String roles) {
		String result = null;
		
		List<Map<String, Object>> parameters = new ArrayList<Map<String, Object>>();

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", null);
		map.put("menuid", menuid);
		map.put("roleid", roleid);
		
		result = new StringHelper().join(roleMenuDao.delete(parameters).toArray());

		if (roleid != null && !"".equals(roleid) && !"null".equals(roleid)) {
			String[] menu;
			if (!StringHelper.isNull(menus)) {
				menu = menus.split(",");
			} else {
				menu = new String[]{};
			}
			RoleMenu roleMenu = new RoleMenu();
			roleMenu.setFlag(roleDao.find(roleid).getFlag());
			for (String id : menu) {
				roleMenu.setRole(roleid);
				roleMenu.setMenu(Long.parseLong(id));
				result += "," + roleMenuDao.insert(roleMenu);
			}
		}

		if (menuid != null && !"".equals(menuid) && !"null".equals(menuid)) {
			String[] role;
			if (!StringHelper.isNull(roles)) {
				role = roles.split(",");
			} else {
				role = new String[]{};
			}
			RoleMenu roleMenu = new RoleMenu();
			roleMenu.setFlag(menuDao.find(menuid).getFlag());
			for (String id : role) {
				roleMenu.setRole(Long.parseLong(id));
				roleMenu.setMenu(menuid);
				result += "," + roleMenuDao.insert(roleMenu);
			}
		}
		
		return result;
	}
	
	@Override
	public Long create(RoleMenu roleMenu) {
		return roleMenuDao.insert(roleMenu);
	}
	
	@Override
	public Long edit(RoleMenu roleMenu) {
		Integer result = roleMenuDao.update(roleMenu);
		return result > 0 ? roleMenu.getId() : result.longValue();
	}
	
	@Override
	public Long modify(RoleMenu roleMenu) {
		if (StringHelper.isNull(String.valueOf(roleMenu.getId())))
			return this.create(roleMenu);
		else
			return this.edit(roleMenu);
	}
	
	@Override
	public Page pagination(Long roleid, String role, Long menuid, String menu, Integer flag, Page page) {
		return roleMenuDao.pagination(roleid, role, menuid, menu, flag, page);
	}

	@Override
	public List<RoleMenu> query(Long roleid, String role, Long menuid, String menu, Integer flag) {
		return roleMenuDao.select(roleid, role, menuid, menu, flag);
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
					map.put("menuid", null);
					map.put("roleid", null);

					parameters.add(map);
				} catch (NumberFormatException e) {
					logger.error("id invalid: " + ids);
				}
			}

			result = new StringHelper().join(roleMenuDao.delete(parameters).toArray());
		}

		return result;
	}
	
	@Override
	public String remove(Long id, Long roleid, Long menuid) {
		List<Map<String, Object>> parameters = new ArrayList<Map<String, Object>>();

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("menuid", menuid);
		map.put("roleid", roleid);

		return new StringHelper().join(roleMenuDao.delete(parameters).toArray());
	}
	
	@Override
	public RoleMenu search(Long id) {
		return roleMenuDao.find(id);
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
						xmlBinding.xml2file(out.getAbsolutePath() + "/export_rolemenu" + suffix + ".xml", "<?xml version=\"1.0\" encoding=\"UTF-8\"?><export/>");
					}
				}
			}
		}
		
		if (passage != null && passage.contains(IBusinessConstant.SYNCHRONIZATION_PASSAGE_WEBSERVICE)) {
			String url = config.get(IBusinessConstant.SYNCHRONIZATION_URL);
			if (url != null && !"".equals(url) && !"null".equals(url)) {
				String namespace = "http://impl.synchronization.server.webservice.frame.org";
				
				Client client = new Client();
				Object[] info = client.invoke(url, namespace, "exportRoleMenu", new Class<?>[]{}, new Object[]{});
				if (info != null && info.length > 0) {
					new SynchronizationService().importRoleMenu(String.valueOf(info[0]));
				} else {
					System.err.println("no rolemenu info found.");
				}
			}
		}
		
		result = true;
		
		return result;
	}
	
}
