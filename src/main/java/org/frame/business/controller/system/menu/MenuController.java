package org.frame.business.controller.system.menu;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.frame.business.model.system.Menu;
import org.frame.business.service.system.dictionary.IDictionaryService;
import org.frame.business.service.system.menu.IMenuService;
import org.frame.common.constant.ISymbolConstant;
import org.frame.common.lang.StringHelper;
import org.frame.repository.sql.model.Page;
import org.frame.web.constant.IWebConstant;
import org.frame.web.controller.BaseController;
import org.frame.web.tree.TreeDataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class MenuController extends BaseController {
	
	private Log logger = LogFactory.getLog(MenuController.class);
	
	@Resource
	@Autowired
	IDictionaryService dictionaryService;
	
	@Resource
	@Autowired
	IMenuService menuService;

	@RequestMapping(value = "/system/menu/abandon.jspx")
	public String abandon(HttpServletRequest request, Model model) {
		logger.info("menu controller abandon access...");
		
		String sign = request.getParameter("flag");
		
		try {
			String ids = request.getParameter("ids");
			Integer flag = Integer.parseInt(sign);
			if (!StringHelper.isNull(ids)) {
				model.addAttribute(ISymbolConstant.INFO_CODE, menuService.abandon(ids, flag));
			}
		} catch (NumberFormatException e) {
			logger.error("flag invalid: " + sign);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "/system/menu/list.jspx";
	}
	
	@RequestMapping(value = "/system/menu/detail.jspx")
	public String detail(HttpServletRequest request, Model model) {
		logger.info("menu controller detail access...");
		
		String ids = request.getParameter("ids");
		
		try {
			Long id = null;
			Menu menu = null;
			
			if (!StringHelper.isNull(ids)) {
				id = Long.parseLong(ids.split(",")[0]);
				menu = menuService.search(id);
			} else {
				menu = new Menu();
				menu.setDisplay(0l);
				menu.setFlag(ISymbolConstant.FLAG_AVAILABLE);
			}
			
			model.addAttribute("system", dictionaryService.query("SYSTEM"));
			model.addAttribute("menu", menu);
		} catch (NumberFormatException e) {
			logger.error("detail id invalid: " + ids);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "detail.jsp";
	}

	@RequestMapping(value = "/system/menu/list.jspx")
	public String list(HttpServletRequest request, Model model) {
		logger.info("menu controller list access...");
		
		Page page = (Page) request.getAttribute(IWebConstant.PAGE_CODE);

		String parent = request.getParameter("query_parentid");
		String level = request.getParameter("query_rank");
		String sign = request.getParameter("query_flag");
		
		String parentname = request.getParameter("query_parentname");
		String system = request.getParameter("query_system");
		String title = request.getParameter("query_title");
		try {
			Integer flag;
			Long parentid, rank;
			
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
			
			if (!StringHelper.isNull(parent)) {
				try {
					parentid = Long.parseLong(parent);
				} catch (NumberFormatException e) {
					parentid = null;
					logger.error("parent id invalid. parent id: " + parent);
				}
			} else {
				parentid = null;
			}
			
			if (StringHelper.isNull(parentname)) {
				parentname = null;
			}
			
			if (!StringHelper.isNull(level)) {
				try {
					rank = Long.parseLong(level);
				} catch (NumberFormatException e) {
					rank = null;
					logger.error("rank invalid. rank: " + level);
				}
			} else {
				rank = null;
			}
			
			if (StringHelper.isNull(system)) {
				system = null;
			}
			
			if (StringHelper.isNull(title)) {
				title = null;
			}
			
			model.addAttribute("query_parentid", parentid);
			model.addAttribute("query_parentname", parentname);
			model.addAttribute("query_rank", rank);
			model.addAttribute("query_system", system);
			model.addAttribute("query_title", title);
			model.addAttribute("query_flag", flag);
			model.addAttribute("flag", dictionaryService.query("FLAG"));
			model.addAttribute(IWebConstant.PAGE_CODE, menuService.pagination(parentid, parentname, system, title, rank, flag, page));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "/system/menu/list.jsp";
	}
	
	@RequestMapping(value = "/system/menu/load.jspx")
	public @ResponseBody String load(HttpServletRequest request, Model model) {
		logger.info("menu controller load access...");
		
		String result = "[]";
		
		String parent = request.getParameter("id");
		
		try {
			Long parentid;
			
			if (!StringHelper.isNull(parent)) {
				parentid = Long.parseLong(parent);
				if (parentid == -1) parentid = null;
			} else {
				parentid = null;
			}
			
			List<Menu> data = menuService.query(parentid, null, null, null, null, ISymbolConstant.FLAG_AVAILABLE);
			result = new TreeDataFormat().format(data);
		} catch (NumberFormatException e) {
			logger.error("parent id invalid: " + parent);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	@RequestMapping(value = "/system/menu/modify.jspx")
	public String modify(HttpServletRequest request, Model model) {
		logger.info("menu controller modify access...");
		
		try {
			Menu menu = (Menu) super.request2bean(request, Menu.class);
			if ("-1".equals(menu.getSystem()) || StringHelper.isNull(menu.getSystem())) menu.setSystem(null);
			model.addAttribute(ISymbolConstant.INFO_CODE, menuService.modify(menu));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "/system/menu/list.jspx";
	}
	
	@RequestMapping(value = "/system/menu/remove.jspx")
	public String remove(HttpServletRequest request, Model model) {
		logger.info("menu controller remove access...");
		
		try {
			String ids = request.getParameter("ids");
			if (!StringHelper.isNull(ids)) {
				model.addAttribute(ISymbolConstant.INFO_CODE, menuService.remove(ids));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "/system/menu/list.jspx";
	}
	
	@RequestMapping(value = "/system/menu/synchronization.jspx")
	public @ResponseBody String synchronization(HttpServletRequest request, Model model) {
		logger.info("menu controller synchronization access...");
		
		boolean result = false; 
		
		try {
			result = menuService.synchronization();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return String.valueOf(result);
	}
	
}
