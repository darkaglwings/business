package org.frame.business.service.system.departmentmenu.impl;

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
import org.frame.business.dao.system.departmentmenu.IDepartmentMenuDao;
import org.frame.business.dao.system.menu.IMenuDao;
import org.frame.business.model.system.DepartmentMenu;
import org.frame.business.service.synchronization.impl.SynchronizationService;
import org.frame.business.service.system.departmentmenu.IDepartmentMenuService;
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


@Repository("departmentMenuService")
@Scope("singleton")
public class DepartmentMenuService extends BaseService implements IDepartmentMenuService {
	
	private Log logger = LogFactory.getLog(DepartmentMenuService.class);
	
	@Resource
	@Autowired
	IDepartmentDao departmentDao;
	
	@Resource
	@Autowired
	IDepartmentMenuDao departmentMenuDao;
	
	@Resource
	@Autowired
	IMenuDao menuDao;
	
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
					map.put("menuid", null);
					map.put("flag", flag);

					parameters.add(map);
				} catch (NumberFormatException e) {
					logger.error("id invalid: " + ids);
				}
			}

			result = new StringHelper().join(departmentMenuDao.abandon(parameters).toArray());
		}

		return result;
	}
	
	@Override
	public String abandon(Long id, Long departmentid, Long menuid, Integer flag) {
		List<Map<String, Object>> parameters = new ArrayList<Map<String, Object>>();
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("departmentid", departmentid);
		map.put("menuid", menuid);
		map.put("flag", flag);
		
		parameters.add(map);
		
		return new StringHelper().join(departmentMenuDao.abandon(parameters).toArray());
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor = {Exception.class})
	public String assign(Long departmentid, String menus, Long menuid, String departments) {
		String result = null;

		List<Map<String, Object>> parameters = new ArrayList<Map<String, Object>>();
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", null);
		map.put("departmentid", departmentid);
		map.put("menuid", menuid);
		
		parameters.add(map);
		
		result = new StringHelper().join(departmentMenuDao.delete(parameters).toArray());

		if (departmentid != null && !"".equals(departmentid) && !"null".equals(departmentid)) {
			String[] menu;
			if (!StringHelper.isNull(menus)) {
				menu = menus.split(",");
			} else {
				menu = new String[]{};
			}
			DepartmentMenu departmentMenu = new DepartmentMenu();
			departmentMenu.setFlag(departmentDao.find(departmentid).getFlag());
			for (String id : menu) {
				departmentMenu.setDepartment(departmentid);
				departmentMenu.setMenu(Long.parseLong(id));

				result += "," + departmentMenuDao.insert(departmentMenu);
			}
		}

		if (menuid != null && !"".equals(menuid) && !"null".equals(menuid)) {
			String[] department;
			if (!StringHelper.isNull(departments)) {
				department = departments.split(",");
			} else {
				department = new String[]{};
			}
			DepartmentMenu departmentMenu = new DepartmentMenu();
			departmentMenu.setFlag(menuDao.find(menuid).getFlag());
			for (String id : department) {
				departmentMenu.setDepartment(Long.parseLong(id));
				departmentMenu.setMenu(menuid);
				result += "," + departmentMenuDao.insert(departmentMenu);
			}
		}

		return result;
	}
	
	@Override
	public Long create(DepartmentMenu departmentMenu) {
		return departmentMenuDao.insert(departmentMenu);
	}
	
	@Override
	public Long edit(DepartmentMenu departmentMenu) {
		Integer result = departmentMenuDao.update(departmentMenu);
		return result > 0 ? departmentMenu.getId() : result.longValue();
	}
	
	@Override
	public Long modify(DepartmentMenu departmentMenu) {
		if (StringHelper.isNull(String.valueOf(departmentMenu.getId())))
			return this.create(departmentMenu);
		else
			return this.edit(departmentMenu);
	}

	@Override
	public Page pagination(Long departmentid, String department, Long menuid, String menu, Integer flag, Page page) {
		return departmentMenuDao.pagination(departmentid, department, menuid, menu, flag, page);
	}
	
	@Override
	public List<DepartmentMenu> query(Long departmentid, String department, Long menuid, String menu, Integer flag) {
		return departmentMenuDao.select(departmentid, department, menuid, menu, flag);
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

					parameters.add(map);
				} catch (NumberFormatException e) {
					logger.error("id invalid: " + ids);
				}
			}

			result = new StringHelper().join(departmentMenuDao.delete(parameters).toArray());
		}

		return result;
	}
	
	@Override
	public String remove(Long id, Long departmentid, Long menuid) {
		List<Map<String, Object>> parameters = new ArrayList<Map<String, Object>>();
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("departmentid", departmentid);
		map.put("menuid", menuid);
		
		parameters.add(map);
		
		return new StringHelper().join(departmentMenuDao.delete(parameters).toArray());
	}
	
	@Override
	public DepartmentMenu search(Long id) {
		return departmentMenuDao.find(id);
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
						xmlBinding.xml2file(out.getAbsolutePath() + "/export_departmentmenu" + suffix + ".xml", "<?xml version=\"1.0\" encoding=\"UTF-8\"?><export/>");
					}
				}
			}
		}
		
		if (passage != null && passage.contains(IBusinessConstant.SYNCHRONIZATION_PASSAGE_WEBSERVICE)) {
			String url = config.get(IBusinessConstant.SYNCHRONIZATION_URL);
			if (url != null && !"".equals(url) && !"null".equals(url)) {
				String namespace = "http://impl.synchronization.server.webservice.frame.org";
				
				Client client = new Client();
				Object[] info = client.invoke(url, namespace, "exportDepartmentMenu", new Class<?>[]{}, new Object[]{});
				if (info != null && info.length > 0) {
					new SynchronizationService().importDepartmentMenu(String.valueOf(info[0]));
				} else {
					System.err.println("no departmentmenu info found.");
				}
			}
		}
		
		result = true;
		
		return result;
	}
	
}
