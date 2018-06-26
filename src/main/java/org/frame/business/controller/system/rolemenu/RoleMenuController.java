package org.frame.business.controller.system.rolemenu;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.frame.business.model.system.RoleMenu;
import org.frame.business.service.system.dictionary.IDictionaryService;
import org.frame.business.service.system.role.IRoleService;
import org.frame.business.service.system.rolemenu.IRoleMenuService;
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
public class RoleMenuController extends BaseController {
	
	private Log logger = LogFactory.getLog(RoleMenuController.class);
	
	@Resource
	@Autowired
	IDictionaryService dictionaryService;
	
	@Resource
	@Autowired
	IRoleMenuService roleMenuService;
	
	@Resource
	@Autowired
	IRoleService roleService;

	@RequestMapping(value = "/system/rolemenu/abandon.jspx")
	public String abandon(HttpServletRequest request, Model model) {
		logger.info("roleMenu controller abandon access...");
		
		String sign = request.getParameter("flag");
		
		try {
			String ids = request.getParameter("ids");
			Integer flag = Integer.parseInt(sign);
			if (!StringHelper.isNull(ids)) {
				model.addAttribute(ISymbolConstant.INFO_CODE, roleMenuService.abandon(ids, flag));
			}
		} catch (NumberFormatException e) {
			logger.error("flag invalid: " + sign);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "/system/rolemenu/list.jspx";
	}
	
	@RequestMapping(value = "/system/rolemenu/assign.jspx")
	public @ResponseBody String assign(HttpServletRequest request, Model model) {
		logger.info("roleMenu controller assign access...");
		
		String result = "";
		
		String role = request.getParameter("roleid");
		String menus = request.getParameter("menu");
		
		String menu = request.getParameter("menuid");
		String roles = request.getParameter("role");
		
		Long roleid, menuid;
		try {
			if (!StringHelper.isNull(role)) {
				roleid = Long.parseLong(role);
				
				result = roleMenuService.assign(roleid, menus, null, null);
			} else {
				logger.info("roleid invalid: " + role);
			}
			
			if (!StringHelper.isNull(menu)) {
				menuid = Long.parseLong(menu);
				
				result = roleMenuService.assign(null, null, menuid, roles);
			} else {
				logger.info("menuid invalid: " + menu);
			}
		} catch (NumberFormatException e) {
			logger.info("role id or menu id invalid, role id: " + role + ", menu id: " + menu);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@RequestMapping(value = "/system/rolemenu/detail.jspx")
	public String detail(HttpServletRequest request, Model model) {
		logger.info("roleMenu controller detail access...");
		
		String ids = request.getParameter("ids");
		
		try {
			Long id = null;
			RoleMenu roleMenu = null;
			
			if (!StringHelper.isNull(ids)) {
				id = Long.parseLong(ids.split(",")[0]);
				roleMenu = roleMenuService.search(id);
			} else {
				roleMenu = new RoleMenu();
				roleMenu.setFlag(ISymbolConstant.FLAG_AVAILABLE);
			}
			
			model.addAttribute("roles", roleService.query(null, null, ISymbolConstant.FLAG_AVAILABLE));
			model.addAttribute("roleMenu", roleMenu);
		} catch (NumberFormatException e) {
			logger.error("detail id invalid: " + ids);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "detail.jsp";
	}

	@RequestMapping(value = "/system/rolemenu/list.jspx")
	public String list(HttpServletRequest request, Model model) {
		logger.info("roleMenu controller list access...");
		
		Page page = (Page) request.getAttribute(IWebConstant.PAGE_CODE);

		String menu = request.getParameter("query_menuid");
		String role = request.getParameter("query_roleid");
		String sign = request.getParameter("query_flag");
		
		String menuname = request.getParameter("query_menu");
		String rolename = request.getParameter("query_role");
		
		try {
			Integer flag;
			Long roleid, menuid;
			
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
			
			if (!StringHelper.isNull(menu)) {
				try {
					menuid = Long.parseLong(menu);
				} catch (NumberFormatException e) {
					menuid = null;
					logger.error("menu id invalid. menu id: " + menu);
				}
			} else {
				menuid = null;
			}
			
			if (!StringHelper.isNull(menuname)) {
				menuname = null;
			}
			
			model.addAttribute("query_flag", flag);
			model.addAttribute("query_role",role);
			model.addAttribute("query_roleid",roleid);
			model.addAttribute("query_menu", menu);
			model.addAttribute("query_menuid", menuid);
			model.addAttribute("flag", dictionaryService.query("FLAG"));
			model.addAttribute(IWebConstant.PAGE_CODE, roleMenuService.pagination(roleid, rolename, menuid, menuname, flag, page));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "/system/rolemenu/list.jsp";
	}

	@RequestMapping(value = "/system/rolemenu/load.jspx")
	public @ResponseBody String load(HttpServletRequest request, Model model) {
		logger.info("rolemenu controller load access...");
		
		String result = "[]";
		
		String menu = request.getParameter("menuid");
		String role = request.getParameter("id");
		try {	
			Long menuid, roleid;
			
			if (!StringHelper.isNull(menu)) {
				menuid = Long.parseLong(menu);
				if (menuid == -1) menuid = null;
			} else {
				menuid = null;
			}
			
			if (!StringHelper.isNull(role)) {
				roleid = Long.parseLong(role);
				if (roleid == -1) roleid = null;
			} else {
				roleid = null;
			}
			
			List<RoleMenu> data = roleMenuService.query(roleid, null, menuid, null, ISymbolConstant.FLAG_AVAILABLE);
			result = new JSON().toJsonString(data);
		} catch (NumberFormatException e) {
			logger.error("role id or menu id invalid, role id: " + role + ", menu id: " + menu);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@RequestMapping(value = "/system/rolemenu/modify.jspx")
	public String modify(HttpServletRequest request, Model model) {
		logger.info("roleMenu controller modify access...");
		
		try {
			RoleMenu roleMenu = (RoleMenu) super.request2bean(request, RoleMenu.class);
			model.addAttribute(ISymbolConstant.INFO_CODE, roleMenuService.modify(roleMenu));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "/system/rolemenu/list.jspx";
	}
	
	@RequestMapping(value = "/system/rolemenu/remove.jspx")
	public String remove(HttpServletRequest request, Model model) {
		logger.info("roleMenu controller remove access...");
		
		try {
			String ids = request.getParameter("ids");
			if (!StringHelper.isNull(ids)) {
				model.addAttribute(ISymbolConstant.INFO_CODE, roleMenuService.remove(ids));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "/system/rolemenu/list.jspx";
	}

	@RequestMapping(value = "/system/rolemenu/synchronization.jspx")
	public @ResponseBody String synchronization(HttpServletRequest request, Model model) {
		logger.info("rolemenu controller synchronization access...");
		
		boolean result = false; 
		
		try {
			result = roleMenuService.synchronization();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return String.valueOf(result);
	}
	
}
