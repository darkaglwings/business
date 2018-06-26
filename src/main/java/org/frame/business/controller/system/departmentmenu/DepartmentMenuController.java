package org.frame.business.controller.system.departmentmenu;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.frame.business.model.system.DepartmentMenu;
import org.frame.business.service.system.departmentmenu.IDepartmentMenuService;
import org.frame.business.service.system.dictionary.IDictionaryService;
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
public class DepartmentMenuController extends BaseController {
	
	private Log logger = LogFactory.getLog(DepartmentMenuController.class);
	
	@Resource
	@Autowired
	IDepartmentMenuService departmentMenuService;
	
	@Resource
	@Autowired
	IDictionaryService dictionaryService;
	
	@RequestMapping(value = "/system/departmentmenu/abandon.jspx")
	public String abandon(HttpServletRequest request, Model model) {
		logger.info("departmentMenu controller abandon access...");
		
		String sign = request.getParameter("flag");
		
		try {
			String ids = request.getParameter("ids");
			Integer flag = Integer.parseInt(sign);
			if (!StringHelper.isNull(ids)) {
				model.addAttribute(ISymbolConstant.INFO_CODE, departmentMenuService.abandon(ids, flag));
			}
		} catch (NumberFormatException e) {
			logger.error("flag invalid: " + sign);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "/system/departmentmenu/list.jspx";
	}
	
	@RequestMapping(value = "/system/departmentmenu/assign.jspx")
	public @ResponseBody String assign(HttpServletRequest request, Model model) {
		logger.info("departmentMenu controller assign access...");
		
		String result = "";
		
		String department = request.getParameter("departmentid");
		String menus = request.getParameter("menu");
		
		String menu = request.getParameter("menuid");
		String departments = request.getParameter("department");
		
		Long departmentid, menuid;
		try {
			if (!StringHelper.isNull(department)) {
				departmentid = Long.parseLong(department);
				
				result = departmentMenuService.assign(departmentid, menus, null, null);
			} else {
				logger.info("departmentid invalid: " + department);
			}
			
			if (!StringHelper.isNull(menu)) {
				menuid = Long.parseLong(menu);
				
				result = departmentMenuService.assign(null, null, menuid, departments);
			} else {
				logger.info("menuid invalid: " + menu);
			}
		} catch (NumberFormatException e) {
			logger.info("department id or menu id invalid, department id: " + department + ", menu id: " + menu);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@RequestMapping(value = "/system/departmentmenu/detail.jspx")
	public String detail(HttpServletRequest request, Model model) {
		logger.info("departmentMenu controller detail access...");
		
		String ids = request.getParameter("ids");
		
		try {
			Long id = null;
			DepartmentMenu departmentMenu = null;
			
			if (!StringHelper.isNull(ids)) {
				id = Long.parseLong(ids.split(",")[0]);
				departmentMenu = departmentMenuService.search(id);
			} else {
				departmentMenu = new DepartmentMenu();
				departmentMenu.setFlag(ISymbolConstant.FLAG_AVAILABLE);
			}
			
			model.addAttribute("departmentMenu", departmentMenu);
		} catch (NumberFormatException e) {
			logger.error("detail id invalid: " + ids);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "detail.jsp";
	}

	@RequestMapping(value = "/system/departmentmenu/list.jspx")
	public String list(HttpServletRequest request, Model model) {
		logger.info("departmentMenu controller list access...");
		
		Page page = (Page) request.getAttribute(IWebConstant.PAGE_CODE);

		String department =request.getParameter("query_departmentid");
		String menu = request.getParameter("query_menuid");
		String sign = request.getParameter("query_flag");
		
		String departmentname =request.getParameter("query_department");
		String menuname = request.getParameter("query_menu");
		
		try {
			Integer flag;
			Long departmentid, menuid;
			
			if (!StringHelper.isNull(department)) {
				try {
					departmentid = Long.parseLong(department);
				} catch (NumberFormatException e) {
					departmentid = null;
					logger.error("department id invalid. department id: " + department);
				}
			} else {
				departmentid = null;
			}
			
			if (StringHelper.isNull(departmentname)) {
				departmentname = null;
			}
			
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
			
			if (StringHelper.isNull(menuname)) {
				menuname = null;
			}
			
			model.addAttribute("query_department",departmentname);
			model.addAttribute("query_departmentid",departmentid);
			model.addAttribute("query_flag", flag);
			model.addAttribute("query_menu", menuname);
			model.addAttribute("query_menuid", menuid);
			model.addAttribute("flag", dictionaryService.query("FLAG"));
			model.addAttribute(IWebConstant.PAGE_CODE, departmentMenuService.pagination(departmentid, departmentname, menuid, menuname, flag, page));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "/system/departmentmenu/list.jsp";
	}

	@RequestMapping(value = "/system/departmentmenu/load.jspx")
	public @ResponseBody String load(HttpServletRequest request, Model model) {
		logger.info("departmentmenu controller load access...");
		
		String result = "[]";
		
		String department = request.getParameter("id");
		String menu = request.getParameter("menuid");
		
		try {
			Long departmentid, menuid;
			
			if (!StringHelper.isNull(department)) {
				departmentid = Long.parseLong(department);
				if (departmentid == -1) departmentid = null;
			} else {
				departmentid = null;
			}
			
			if (!StringHelper.isNull(menu)) {
				menuid = Long.parseLong(menu);
				if (menuid == -1) menuid = null;
			} else {
				menuid = null;
			}
			
			List<DepartmentMenu> data = departmentMenuService.query(departmentid, null, menuid, null, ISymbolConstant.FLAG_AVAILABLE);
			result = new JSON().toJsonString(data);
		} catch (NumberFormatException e) {
			result = "";
			logger.error("department id or menu id invalid. department id: " + department + ", menu id: " + menu);
		}  catch (Exception e) {
			result = "";
			e.printStackTrace();
		}
		
		return result;
	}
	
	@RequestMapping(value = "/system/departmentmenu/modify.jspx")
	public String modify(HttpServletRequest request, Model model) {
		logger.info("departmentMenu controller modify access...");
		try {
			DepartmentMenu departmentMenu = (DepartmentMenu) super.request2bean(request, DepartmentMenu.class);
			model.addAttribute(ISymbolConstant.INFO_CODE, departmentMenuService.modify(departmentMenu));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "/system/departmentmenu/list.jspx";
	}
	
	@RequestMapping(value = "/system/departmentmenu/remove.jspx")
	public String remove(HttpServletRequest request, Model model) {
		logger.info("departmentMenu controller remove access...");
		
		try {
			String ids = request.getParameter("ids");
			if (!StringHelper.isNull(ids)) {
				model.addAttribute(ISymbolConstant.INFO_CODE, departmentMenuService.remove(ids));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "/system/departmentmenu/list.jspx";
	}

	@RequestMapping(value = "/system/departmentmenu/synchronization.jspx")
	public @ResponseBody String synchronization(HttpServletRequest request, Model model) {
		logger.info("departmentmenu controller synchronization access...");
		
		boolean result = false; 
		
		try {
			result = departmentMenuService.synchronization();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return String.valueOf(result);
	}
	
}
