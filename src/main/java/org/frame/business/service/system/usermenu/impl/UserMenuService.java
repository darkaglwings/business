package org.frame.business.service.system.usermenu.impl;

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
import org.frame.business.dao.system.user.IUserDao;
import org.frame.business.dao.system.usermenu.IUserMenuDao;
import org.frame.business.model.system.UserMenu;
import org.frame.business.service.synchronization.impl.SynchronizationService;
import org.frame.business.service.system.usermenu.IUserMenuService;
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


@Repository("userMenuService")
@Scope("singleton")
public class UserMenuService extends BaseService implements IUserMenuService {
	
	private Log logger = LogFactory.getLog(UserMenuService.class);
	
	@Resource
	@Autowired
	IMenuDao menuDao;
	
	@Resource
	@Autowired
	IUserDao userDao;
	
	@Resource
	@Autowired
	IUserMenuDao userMenuDao;
	
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
					map.put("userid", null);
					map.put("flag", flag);

					parameters.add(map);
				} catch (NumberFormatException e) {
					logger.error("id invalid: " + ids);
				}
			}

			result = new StringHelper().join(userMenuDao.abandon(parameters).toArray());
		}

		return result;
	}
	
	@Override
	public String abandon(Long id, Long menuid, Long userid, Integer flag) {
		List<Map<String, Object>> parameters = new ArrayList<Map<String, Object>>();
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("menuid", menuid);
		map.put("userid", userid);
		map.put("flag", flag);
		
		return new StringHelper().join(userMenuDao.abandon(parameters).toArray());
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor = {Exception.class})
	public String assign(Long userid, String menus, Long menuid, String users) {
		String result = null;
		
		List<Map<String, Object>> parameters = new ArrayList<Map<String, Object>>();
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", null);
		map.put("menuid", menuid);
		map.put("userid", userid);
		
		result = new StringHelper().join(userMenuDao.delete(parameters).toArray());

		if (userid != null && !"".equals(userid) && !"null".equals(userid)) {
			String[] menu;
			if (!StringHelper.isNull(menus)) {
				menu = menus.split(",");
			} else {
				menu = new String[]{};
			}
			UserMenu userMenu = new UserMenu();
			userMenu.setFlag(userDao.find(userid).getFlag());
			for (String id : menu) {
				userMenu.setUser(userid);
				userMenu.setMenu(Long.parseLong(id));

				result += "," + userMenuDao.insert(userMenu);
			}
		}

		if (menuid != null && !"".equals(menuid) && !"null".equals(menuid)) {
			String[] user;
			if (!StringHelper.isNull(users)) {
				user = users.split(",");
			} else {
				user = new String[]{};
			}
			UserMenu userMenu = new UserMenu();
			userMenu.setFlag(menuDao.find(menuid).getFlag());
			for (String id : user) {
				userMenu.setUser(Long.parseLong(id));
				userMenu.setMenu(menuid);

				result += "," + userMenuDao.insert(userMenu);
			}
		}

		return result;
	}
	
	@Override
	public Long create(UserMenu userMenu) {
		return userMenuDao.insert(userMenu);
	}
	
	@Override
	public Long edit(UserMenu userMenu) {
		Integer result = userMenuDao.update(userMenu);
		return result > 0 ? userMenu.getId() : result.longValue();
	}
	
	@Override
	public Long modify(UserMenu userMenu) {
		if (StringHelper.isNull(String.valueOf(userMenu.getId())))
			return this.create(userMenu);
		else
			return this.edit(userMenu);
	}
	
	@Override
	public Page pagination(Long userid, String user, Long menuid, String menu, Integer flag, Page page) {
		return userMenuDao.pagination(userid, user, menuid, menu, flag, page);
	}

	@Override
	public List<UserMenu> query(Long userid, String user, Long menuid, String menu, Integer flag) {
		return userMenuDao.select(userid, user, menuid, menu, flag);
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
					map.put("userid", null);

					parameters.add(map);
				} catch (NumberFormatException e) {
					logger.error("id invalid: " + ids);
				}
			}

			result = new StringHelper().join(userMenuDao.delete(parameters).toArray());
		}

		return result;
	}
	
	@Override
	public String remove(Long id, Long userid, Long menuid) {
		List<Map<String, Object>> parameters = new ArrayList<Map<String, Object>>();
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("menuid", menuid);
		map.put("userid", userid);
		
		return new StringHelper().join(userMenuDao.delete(parameters).toArray());
	}
	
	@Override
	public UserMenu search(Long id) {
		return userMenuDao.find(id);
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
						xmlBinding.xml2file(out.getAbsolutePath() + "/export_usermenu" + suffix + ".xml", "<?xml version=\"1.0\" encoding=\"UTF-8\"?><export/>");
					}
				}
			}
		}
		
		if (passage != null && passage.contains(IBusinessConstant.SYNCHRONIZATION_PASSAGE_WEBSERVICE)) {
			String url = config.get(IBusinessConstant.SYNCHRONIZATION_URL);
			if (url != null && !"".equals(url) && !"null".equals(url)) {
				String namespace = "http://impl.synchronization.server.webservice.frame.org";
				
				Client client = new Client();
				Object[] info = client.invoke(url, namespace, "exportUserMenu", new Class<?>[]{}, new Object[]{});
				if (info != null && info.length > 0) {
					new SynchronizationService().importUserMenu(String.valueOf(info[0]));
				} else {
					System.err.println("no usermenu info found.");
				}
			}
		}
		
		result = true;
		
		return result;
	}
	
}
