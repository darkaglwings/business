package org.frame.business.service.system.menu.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.frame.business.constant.IBusinessConstant;
import org.frame.business.dao.system.departmentmenu.IDepartmentMenuDao;
import org.frame.business.dao.system.menu.IMenuDao;
import org.frame.business.dao.system.postmenu.IPostMenuDao;
import org.frame.business.dao.system.rolemenu.IRoleMenuDao;
import org.frame.business.dao.system.usermenu.IUserMenuDao;
import org.frame.business.model.system.Menu;
import org.frame.business.service.synchronization.impl.SynchronizationService;
import org.frame.business.service.system.menu.IMenuService;
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


@Repository("menuService")
@Scope("singleton")
public class MenuService extends BaseService implements IMenuService{
	
	@SuppressWarnings("unused")
	private Log logger = LogFactory.getLog(MenuService.class);
	
	@Resource
	@Autowired
	IMenuDao menuDao;
	
	@Resource
	@Autowired
	IDepartmentMenuDao departmentMenuDao;
	
	@Resource
	@Autowired
	IPostMenuDao postMenuDao;
	
	@Resource
	@Autowired
	IRoleMenuDao roleMenuDao;
	
	@Resource
	@Autowired
	IUserMenuDao userMenuDao;

	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor = {Exception.class})
	public String abandon(String ids, Integer flag) {
		String result = "";
		if (!StringHelper.isNull(ids)) {
			String related = new Config(IBusinessConstant.DEFAULT_CONFIG_PROPERTIES).get(IBusinessConstant.MENU_RELATED).toLowerCase();
			if (related == null) related = "";
			
			List<Map<String, Object>> parameters = new ArrayList<Map<String, Object>>();
			Map<String, Object> map;
			
			if ("all".equals(related) || related.contains("department")) {
				parameters.clear();
				for (String id : ids.split(",")) {
					map = new HashMap<String, Object>();
					map.put("id", null);
					map.put("departmentid", null);
					map.put("menuid", Long.parseLong(id));
					map.put("flag", flag);

					parameters.add(map);
				}

				departmentMenuDao.abandon(parameters);
			}
				
			if ("all".equals(related) || related.contains("post")) {
				parameters.clear();
				for (String id : ids.split(",")) {
					map = new HashMap<String, Object>();
					map.put("id", null);
					map.put("postid", null);
					map.put("menuid", Long.parseLong(id));
					map.put("flag", flag);

					parameters.add(map);
				}

				postMenuDao.abandon(parameters);
			}
				
			if ("all".equals(related) || related.contains("role")) {
				parameters.clear();
				for (String id : ids.split(",")) {
					map = new HashMap<String, Object>();
					map.put("id", null);
					map.put("menuid", Long.parseLong(id));
					map.put("roleid", null);
					map.put("flag", flag);

					parameters.add(map);
				}

				roleMenuDao.abandon(parameters);
			}

			if ("all".equals(related) || related.contains("user")) {
				parameters.clear();
				for (String id : ids.split(",")) {
					map = new HashMap<String, Object>();
					map.put("id", null);
					map.put("menuid", Long.parseLong(id));
					map.put("userid", null);
					map.put("flag", flag);

					parameters.add(map);
				}

				userMenuDao.abandon(parameters);
			}

			parameters.clear();
			for (String id : ids.split(",")) {
				map = new HashMap<String, Object>();
				map.put("id", id);
				map.put("flag", flag);

				parameters.add(map);
			}
			
			result = new StringHelper().join(menuDao.abandon(parameters).toArray());
		}
		
		return result;
	}
	
	@Override
	public Long create(Menu menu) {
		return menuDao.insert(menu);
	}
	
	@Override
	public Long edit(Menu menu) {
		Integer result = menuDao.update(menu);
		return result > 0 ? menu.getId() : result.longValue();
	}
	
	@Override
	public Long modify(Menu menu) {
		if (StringHelper.isNull(String.valueOf(menu.getId())))
			return this.create(menu);
		else
			return this.edit(menu);
	}
	
	@Override
	public Page pagination(Long parentid, String parentname, String system, String title, Long rank, Integer flag, Page page) {
		return menuDao.pagination(parentid, parentname, system, title, rank, flag, page);
	}
	
	@Override
	public List<Menu> query(Long userid, String system) {
		return menuDao.select(userid, system);
	}

	@Override
	public List<Menu> query(Long parentid, String parentname, String system, String title, Long rank, Integer flag) {
		return menuDao.select(parentid, parentname, system, title, rank, flag);
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor = {Exception.class})
	public String remove(String ids) {
		String result = "";
		if (!StringHelper.isNull(ids)) {
			String related = new Config(IBusinessConstant.DEFAULT_CONFIG_PROPERTIES).get(IBusinessConstant.MENU_RELATED).toLowerCase();
			if (related == null) related = "";
			
			List<Map<String, Object>> parameters = new ArrayList<Map<String, Object>>();
			Map<String, Object> map;
			
			if ("all".equals(related) || related.contains("department")) {
				parameters.clear();
				for (String id : ids.split(",")) {
					map = new HashMap<String, Object>();
					map.put("id", null);
					map.put("departmentid", null);
					map.put("menuid", Long.parseLong(id));

					parameters.add(map);
				}

				departmentMenuDao.delete(parameters);
			}
				
			if ("all".equals(related) || related.contains("post")) {
				parameters.clear();
				for (String id : ids.split(",")) {
					map = new HashMap<String, Object>();
					map.put("id", null);
					map.put("postid", null);
					map.put("menuid", Long.parseLong(id));

					parameters.add(map);
				}

				postMenuDao.delete(parameters);
			}
				
			if ("all".equals(related) || related.contains("role")) {
				parameters.clear();
				for (String id : ids.split(",")) {
					map = new HashMap<String, Object>();
					map.put("id", null);
					map.put("menuid", Long.parseLong(id));
					map.put("roleid", null);

					parameters.add(map);
				}

				roleMenuDao.delete(parameters);
			}

			if ("all".equals(related) || related.contains("user")) {
				parameters.clear();
				for (String id : ids.split(",")) {
					map = new HashMap<String, Object>();
					map.put("id", null);
					map.put("menuid", Long.parseLong(id));
					map.put("userid", null);

					parameters.add(map);
				}

				userMenuDao.delete(parameters);
			}

			parameters.clear();
			for (String id : ids.split(",")) {
				map = new HashMap<String, Object>();
				map.put("id", id);

				parameters.add(map);
			}
			
			result = new StringHelper().join(menuDao.delete(parameters).toArray());
		}
		
		return result;
	}
	
	@Override
	public Menu search(Long id) {
		return menuDao.find(id);
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
						xmlBinding.xml2file(out.getAbsolutePath() + "/export_menu" + suffix + ".xml", "<?xml version=\"1.0\" encoding=\"UTF-8\"?><export/>");
					}
				}
			}
		}
		
		if (passage != null && passage.contains(IBusinessConstant.SYNCHRONIZATION_PASSAGE_WEBSERVICE)) {
			String url = config.get(IBusinessConstant.SYNCHRONIZATION_URL);
			if (url != null && !"".equals(url) && !"null".equals(url)) {
				String namespace = "http://impl.synchronization.server.webservice.frame.org";
				
				Client client = new Client();
				Object[] info = client.invoke(url, namespace, "exportMenu", new Class<?>[]{}, new Object[]{});
				if (info != null && info.length > 0) {
					new SynchronizationService().importMenu(String.valueOf(info[0]));
				} else {
					System.err.println("no menu info found.");
				}
			}
		}
		
		result = true;
		
		return result;
	}
	
}
