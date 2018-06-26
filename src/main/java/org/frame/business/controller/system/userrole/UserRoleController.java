package org.frame.business.controller.system.userrole;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.frame.business.constant.IBusinessConstant;
import org.frame.business.model.system.UserRole;
import org.frame.business.service.system.dictionary.IDictionaryService;
import org.frame.business.service.system.role.IRoleService;
import org.frame.business.service.system.user.IUserService;
import org.frame.business.service.system.userrole.IUserRoleService;
import org.frame.business.system.Config;
import org.frame.common.constant.ISymbolConstant;
import org.frame.common.json.JSON;
import org.frame.common.lang.StringHelper;
import org.frame.repository.sql.model.Page;
import org.frame.web.constant.IWebConstant;
import org.frame.web.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class UserRoleController extends BaseController {
	
	private Log logger = LogFactory.getLog(UserRoleController.class);
	
	@Resource
	@Autowired
	IDictionaryService dictionaryService;
	
	@Resource
	@Autowired
	IRoleService roleService;
	
	@Resource
	@Autowired
	IUserService userService;
	
	@Resource
	@Autowired
	IUserRoleService userRoleService;

	@RequestMapping(value = "/system/userrole/abandon.jspx")
	public String abandon(HttpServletRequest request, Model model) {
		logger.info("userRole controller abandon access...");
		
		String sign = request.getParameter("flag");
		
		try {
			String ids = request.getParameter("ids");
			Integer flag = Integer.parseInt(sign);
			if (!StringHelper.isNull(ids)) {
				model.addAttribute(ISymbolConstant.INFO_CODE, userRoleService.abandon(ids, flag));
			}
		} catch (NumberFormatException e) {
			logger.error("flag invalid: " + sign);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "/system/userrole/list.jspx";
	}
	
	@RequestMapping(value = "/system/userrole/assign.jspx")
	public @ResponseBody String assign(HttpServletRequest request, Model model) {
		logger.info("userRole controller edit access...");
		
		String result = "";
		
		String user = request.getParameter("userid");
		String roles = request.getParameter("role");
		
		String role = request.getParameter("roleid");
		String users = request.getParameter("user");
		
		try {
			Long roleid, userid;
			
			if (!StringHelper.isNull(user)) {
				userid = Long.parseLong(user);
				result = userRoleService.assign(userid, roles, null, null);
			} else {
				logger.info("user id invalid: " + user);
			}
			
			if (!StringHelper.isNull(role)) {
				roleid = Long.parseLong(role);
				result = userRoleService.assign(null, null, roleid, users);
			} else {
				logger.info("role id invalid: " + role);
			}
		} catch (NumberFormatException e) {
			logger.info("user id or role id invalid, user id: " + user + ", role id: " + role);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@RequestMapping(value = "/system/userrole/detail.jspx")
	public String detail(HttpServletRequest request, Model model) {
		logger.info("userRole controller detail access...");
		
		String ids = request.getParameter("ids");
		
		try {
			Long id = null;
			UserRole userRole = null;
			
			if (!StringHelper.isNull(ids)) {
				id = Long.parseLong(ids.split(",")[0]);
				userRole = userRoleService.search(id);
			} else {
				userRole = new UserRole();
				userRole.setFlag(ISymbolConstant.FLAG_AVAILABLE);
			}
			
			String related = new Config(IBusinessConstant.DEFAULT_CONFIG_PROPERTIES).get(IBusinessConstant.USER_RELATED).toLowerCase();
			if (related == null) related = "";
			if (!ISymbolConstant.FLAG_ALL.equals(related) && !related.contains("department")) {
				model.addAttribute("user", userService.query(null, null, null, ISymbolConstant.FLAG_AVAILABLE));
			}
			
			model.addAttribute("roles", roleService.query(null, null, ISymbolConstant.FLAG_AVAILABLE));
			model.addAttribute("userRole", userRole);
		} catch (NumberFormatException e) {
			logger.error("detail id invalid: " + ids);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "detail.jsp";
	}

	@RequestMapping(value = "/system/userrole/list.jspx")
	public String list(HttpServletRequest request, Model model) {
		logger.info("userRole controller list access...");

		Page page = (Page) request.getAttribute(IWebConstant.PAGE_CODE);
		
		String role = request.getParameter("query_roleid");
		String user = request.getParameter("query_userid");
		String sign = request.getParameter("query_flag");
		
		String rolename = request.getParameter("query_role");
		String username = request.getParameter("query_user");
		
		try {
			Integer flag;
			Long roleid, userid;

			if (!StringHelper.isNull(sign)) {
				try {
					flag = Integer.parseInt(sign);
				} catch (NumberFormatException e) {
					flag = null;
					logger.error("flag invalid. flag: " + sign);
				}
				if (flag != null && flag == -1) flag = null;
			} else {
				flag = null;
			}

			if (!StringHelper.isNull(role)) {
				try {
					roleid = Long.parseLong(role);
				} catch (NumberFormatException e) {
					roleid = null;
					logger.error("role id invalid. role id: " + role);
				}
			} else {
				roleid = null;
			}
			
			if (StringHelper.isNull(rolename)) {
				rolename = null;
			}

			if (!StringHelper.isNull(user)) {
				try {
					userid = Long.parseLong(user);
				} catch (NumberFormatException e) {
					userid = null;
					logger.error("user id invalid. user id: " + user);
				}
			} else {
				userid = null;
			}
			
			if (!StringHelper.isNull(username)) {
				username = null;
			}

			model.addAttribute("query_flag", flag);
			model.addAttribute("query_role", role);
			model.addAttribute("query_roleid", roleid);
			model.addAttribute("query_user", user);
			model.addAttribute("query_userid", userid);
			model.addAttribute("flag", dictionaryService.query("FLAG"));
			model.addAttribute(IWebConstant.PAGE_CODE, userRoleService.pagination(userid, username, roleid, rolename, flag, page));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "/system/userrole/list.jsp";
	}

	@RequestMapping(value = "/system/userrole/load.jspx")
	public @ResponseBody String load(HttpServletRequest request) {
		logger.info("userRole controller load access...");
		
		String result = "[]";
		
		String role = request.getParameter("roleid");
		String user = request.getParameter("userid");
		
		try {
			Long roleid, userid;

			if (!StringHelper.isNull(role)) {
				roleid = Long.parseLong(role);
				if (roleid != null && roleid == -1) roleid = null;
			} else {
				roleid = null;
			}

			if (!StringHelper.isNull(user)) {
				userid = Long.parseLong(user);
				if (userid != null && userid == -1) userid = null;
			} else {
				userid = null;
			}
			
			List<UserRole> data = userRoleService.query(userid, null, roleid, null, ISymbolConstant.FLAG_AVAILABLE);
			result = new JSON().toJsonString(data);
		} catch (NumberFormatException e) {
			logger.error("role id or user id invalid, role id: "+ role +" user id: " + user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@RequestMapping(value = "/system/userrole/modify.jspx")
	public String modify(HttpServletRequest request, Model model) {
		logger.info("userRole controller modify access...");
		
		try {
			UserRole userRole = (UserRole) super.request2bean(request, UserRole.class);
			model.addAttribute(ISymbolConstant.INFO_CODE, userRoleService.modify(userRole));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "/system/userrole/list.jspx";
	}
	
	@RequestMapping(value = "/system/userrole/remove.jspx")
	public String remove(HttpServletRequest request, Model model) {
		logger.info("userRole controller remove access...");
		
		try {
			String ids = request.getParameter("ids");
			if (!StringHelper.isNull(ids)) {
				model.addAttribute(ISymbolConstant.INFO_CODE, userRoleService.remove(ids));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "/system/userrole/list.jspx";
	}
	
	@RequestMapping(value = "/system/userrole/synchronization.jspx")
	public @ResponseBody String synchronization(HttpServletRequest request, Model model) {
		logger.info("userrole controller synchronization access...");
		
		boolean result = false; 
		
		try {
			result = userRoleService.synchronization();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return String.valueOf(result);
	}

}
