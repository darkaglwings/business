package org.frame.business.service.system.department.impl;

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
import org.frame.business.dao.system.userdepartment.IUserDepartmentDao;
import org.frame.business.model.system.Department;
import org.frame.business.service.synchronization.impl.SynchronizationService;
import org.frame.business.service.system.department.IDepartmentService;
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


@Repository("departmentService")
@Scope("singleton")
public class DepartmentService extends BaseService implements IDepartmentService {
	
	@SuppressWarnings("unused")
	private Log logger = LogFactory.getLog(DepartmentService.class);
	
	@Resource
	@Autowired
	IDepartmentDao departmentDao;
	
	@Resource
	@Autowired
	IDepartmentMenuDao departmentMenuDao;
	
	@Resource
	@Autowired
	IUserDepartmentDao userDepartmentDao;
	
	@Resource
	@Autowired
	IUserDepartmentService userDepartmentService;

	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor = {Exception.class})
	public String abandon(String ids, Integer flag) {
		String result = "";
		if (!StringHelper.isNull(ids)) {
			boolean menu = false, user = false;

			Config config = new Config(IBusinessConstant.DEFAULT_CONFIG_PROPERTIES);
			String related = config.get(IBusinessConstant.MENU_RELATED).toLowerCase();
			if (related == null) related = "";
			if (ISymbolConstant.FLAG_ALL.equals(related) || related.contains("department")) {
				menu = true;
			}

			related = config.get(IBusinessConstant.USER_RELATED).toLowerCase();
			if (related == null) related = "";
			if (ISymbolConstant.FLAG_ALL.equals(related) || related.contains("department")) {
				user = true;
			}

			List<Map<String, Object>> parameters = new ArrayList<Map<String, Object>>();
			Map<String, Object> map;
			
			if (menu) {
				parameters.clear();
				for (String id : ids.split(",")) {
					map = new HashMap<String, Object>();
					map.put("id", null);
					map.put("departmentid", Long.parseLong(id));
					map.put("menuid", null);
					map.put("flag", flag);

					parameters.add(map);
				}

				departmentMenuDao.abandon(parameters);
			}

			if (user) {
				parameters.clear();
				for (String id : ids.split(",")) {
					map = new HashMap<String, Object>();
					map.put("id", null);
					map.put("departmentid", Long.parseLong(id));
					map.put("userid", null);
					map.put("flag", flag);

					parameters.add(map);

				}
				
				userDepartmentDao.abandon(parameters);
			}

			parameters.clear();
			for (String id : ids.split(",")) {
				map = new HashMap<String, Object>();
				map.put("id", Long.parseLong(id));
				map.put("flag", flag);

				parameters.add(map);
			}

			result = new StringHelper().join(departmentDao.abandon(parameters).toArray());
		}
		
		return result;
	}
	
	@Override
	public Long create(Department department) {
		return departmentDao.insert(department);
	}
	
	@Override
	public Long edit(Department department) {
		Integer result = departmentDao.update(department);
		return result > 0 ? department.getId() : result.longValue();
	}
	
	@Override
	public Long modify(Department department) {
		if (department.getParentid() == null) {
			department.setFullname(department.getTitle());
		} else {
			Department parent = this.search(department.getParentid());
			if (parent != null) {
				if (parent.getFullname() != null && !"".equals(parent.getFullname())) {
					department.setFullname(parent.getFullname() + "/" + department.getTitle());
				} else {
					department.setFullname(department.getTitle());
				}
			} else {
				department.setFullname(department.getTitle());
			}
		}
		
		if (StringHelper.isNull(String.valueOf(department.getId())))
			return this.create(department);
		else
			return this.edit(department);
	}
	
	@Override
	public Page pagination(Long parentid, String parentname, String title, Integer flag, Page page) {
		return departmentDao.pagination(parentid, parentname, title, flag, page);
	}
	
	@Override
	public List<Department> query(Long userid) {
		return departmentDao.select(userid);
	}
	
	@Override
	public List<Department> query(Long parentid, String parentname, String title, Integer flag) {
		return departmentDao.select(parentid, parentname, title, flag);
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
			if (ISymbolConstant.FLAG_ALL.equals(related) || related.contains("department")) {
				menu = true;
			}

			related = config.get(IBusinessConstant.USER_RELATED).toLowerCase();
			if (related == null) related = "";
			if (ISymbolConstant.FLAG_ALL.equals(related) || related.contains("department")) {
				user = true;
			}
			
			String pattern = config.get(IBusinessConstant.RELATED_TYPE_DEPARTMENT).toLowerCase();
			
			List<Map<String, Object>> parameters = new ArrayList<Map<String, Object>>();
			Map<String, Object> map;
			if (menu) {
				parameters.clear();
				for (String id : ids.split(",")) {
					map = new HashMap<String, Object>();
					map.put("id", null);
					map.put("departmentid", Long.parseLong(id));
					map.put("menuid", null);

					parameters.add(map);
				}

				departmentMenuDao.delete(parameters);
			}

			if (user && ISymbolConstant.FLAG_MULTI.equals(pattern)) {
				for (String id : ids.split(",")) {
					userDepartmentService.remove(null, null, Long.parseLong(id));
				}
			}

			parameters.clear();
			for (String id : ids.split(",")) {
				map = new HashMap<String, Object>();
				map.put("id", Long.parseLong(id));

				parameters.add(map);
			}

			result = new StringHelper().join(departmentDao.delete(parameters).toArray());
		}
		
		return result;
	}
	
	@Override
	public Department search(Long id) {
		return departmentDao.find(id);
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
						xmlBinding.xml2file(out.getAbsolutePath() + "/export_department" + suffix + ".xml", "<?xml version=\"1.0\" encoding=\"UTF-8\"?><export/>");
					}
				}
			}
		}
		
		if (passage != null && passage.contains(IBusinessConstant.SYNCHRONIZATION_PASSAGE_WEBSERVICE)) {
			String url = config.get(IBusinessConstant.SYNCHRONIZATION_URL);
			if (url != null && !"".equals(url) && !"null".equals(url)) {
				String namespace = "http://impl.synchronization.server.webservice.frame.org";
				
				Client client = new Client();
				Object[] info = client.invoke(url, namespace, "exportDepartment", new Class<?>[]{}, new Object[]{});
				if (info != null && info.length > 0) {
					new SynchronizationService().importDepartment(String.valueOf(info[0]));
				} else {
					System.err.println("no department info found.");
				}
			}
		}
		
		result = true;
		
		return result;
	}

}
