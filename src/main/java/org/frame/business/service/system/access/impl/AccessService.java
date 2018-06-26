package org.frame.business.service.system.access.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.frame.business.constant.IBusinessConstant;
import org.frame.business.dao.system.access.IAccessDao;
import org.frame.business.model.system.access.Account;
import org.frame.business.service.system.access.IAccessService;
import org.frame.business.service.system.department.IDepartmentService;
import org.frame.business.service.system.menu.IMenuService;
import org.frame.business.service.system.post.IPostService;
import org.frame.business.service.system.role.IRoleService;
import org.frame.business.service.system.user.IUserService;
import org.frame.business.system.Config;
import org.frame.common.constant.ISymbolConstant;
import org.frame.repository.constant.IRepositoryConstant;
import org.frame.web.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;


@Service("accessService")
@Scope("singleton")
public class AccessService extends BaseService implements IAccessService{
	
	private Log logger = LogFactory.getLog(AccessService.class);
	
	@Resource
	@Autowired
	IAccessDao accessDao;
	
	@Resource
	@Autowired
	IUserService userService;
	
	@Resource
	@Autowired
	IDepartmentService departmentService;
	
	@Resource
	@Autowired
	IMenuService menuService;
	
	@Resource
	@Autowired
	IPostService postService;
	
	@Resource
	@Autowired
	IRoleService roleService;
	
	@Override
	public Map<String, Object> login(String username, String password) {
		logger.info("access service login access...");
		Map<String, Object> result = new HashMap<String, Object>();
		Account account = null;
		Long id = accessDao.login(username, password);
		if (id < 0) {
			logger.info("login failure: " + id);
		} else {
			Config config = new Config(IBusinessConstant.DEFAULT_CONFIG_PROPERTIES);
			String related = config.get(IBusinessConstant.USER_RELATED).toLowerCase();
			if (related == null) related = "";
			
			account = new Account();
			account.setUser(userService.search(id));
			
			if (related.contains("department") || ISymbolConstant.FLAG_ALL.equals(related)) {
				account.setDepartments(departmentService.query(id));
			} else {
				account.setDepartments(null);
			}
			
			if (related.contains("post") || ISymbolConstant.FLAG_ALL.equals(related)) {
				account.setPosts(postService.query(id));
			} else {
				account.setPosts(null);
			}
			
			if (related.contains("role") || ISymbolConstant.FLAG_ALL.equals(related)) {
				account.setRoles(roleService.query(id));
			} else {
				account.setRoles(null);
			}
			
			account.setMenus(menuService.query(id, config.get(IRepositoryConstant.SYSTEM_NAME).toLowerCase()));
		}
		
		result.put("code", id);
		result.put("account", account);
		
		return result;
	}
	
	@Override
	public Long logout(Account account) {
		logger.info("access service logout access...");
		Integer result = accessDao.logout(account.getUser().getId());
		return result > 0 ? account.getUser().getId() : result.longValue();
	}

}
