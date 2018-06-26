package org.frame.business.controller.system.role;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.frame.business.model.system.Role;
import org.frame.business.service.system.dictionary.IDictionaryService;
import org.frame.business.service.system.role.IRoleService;
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
public class RoleController extends BaseController {
	
	private Log logger = LogFactory.getLog(RoleController.class);
	
	@Resource
	@Autowired
	private IDictionaryService dictionaryService;
	
	@Resource
	@Autowired
	IRoleService roleService;

	@RequestMapping(value = "/system/role/abandon.jspx")
	public String abandon(HttpServletRequest request, Model model) {
		logger.info("role controller abandon access...");
		
		String sign = request.getParameter("flag");
		
		try {
			String ids = request.getParameter("ids");
			Integer flag = Integer.parseInt(sign);
			if (!StringHelper.isNull(ids)) {
				model.addAttribute(ISymbolConstant.INFO_CODE, roleService.abandon(ids, flag));
			}
		} catch (NumberFormatException e) {
			logger.error("flag invalid: " + sign);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "/system/role/list.jspx";
	}
	
	@RequestMapping(value = "/system/role/detail.jspx")
	public String detail(HttpServletRequest request, Model model) {
		logger.info("role controller detail access...");
		
		String ids = request.getParameter("ids");
		
		try {
			Long id;
			Role role = null;
			
			if (!StringHelper.isNull(ids)) {
				id = Long.parseLong(ids.split(",")[0]);
				role = roleService.search(id);
			} else {
				role = new Role();
				role.setFlag(ISymbolConstant.FLAG_AVAILABLE);
			}
		
			model.addAttribute("role", role);
		} catch (NumberFormatException e) {
			logger.error("detail id invalid: " + ids);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "detail.jsp";
	}

	@RequestMapping(value = "/system/role/list.jspx")
	public String list(HttpServletRequest request, Model model) {
		logger.info("role controller list access...");
		
		Page page = (Page) request.getAttribute(IWebConstant.PAGE_CODE);

		String level = request.getParameter("query_rank");
		String sign = request.getParameter("query_flag");
		String title = request.getParameter("query_title");
		
		try {
			Integer flag;
			Long rank;
			
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
			
			if (StringHelper.isNull(title)) {
				title = null;
			}
			
			model.addAttribute("query_flag", flag);
			model.addAttribute("query_rank", rank);
			model.addAttribute("query_title", title);
			model.addAttribute("flag", dictionaryService.query("FLAG"));
			model.addAttribute(IWebConstant.PAGE_CODE, roleService.pagination(title, rank, flag, page));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "/system/role/list.jsp";
	}

	@RequestMapping(value = "/system/role/load.jspx")
	public @ResponseBody String load(HttpServletRequest request) {
		logger.info("role controller load access...");
		
		String result = "[]";
		
		String level = request.getParameter("query_rank");
		String sign = request.getParameter("query_flag");
		
		String title = request.getParameter("query_title");
		
		try {
			Integer flag;
			Long rank;
			
			if (!StringHelper.isNull(sign)) {
				flag = Integer.parseInt(sign);
				if (flag == -1) flag = null;
			} else {
				flag = null;
			}
			
			if (!StringHelper.isNull(level)) {
				rank = Long.parseLong(level);
			} else {
				rank = null;
			}
			
			if (StringHelper.isNull(title)) {
				title = null;
			}
			
			List<Role> data = roleService.query(title, rank, ISymbolConstant.FLAG_AVAILABLE);
			result = new JSON().toJsonString(data);
		} catch (NumberFormatException e) {
			logger.error("rank or flag invalid. rank: " + level + ", flag: " + sign);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@RequestMapping(value = "/system/role/modify.jspx")
	public String modify(HttpServletRequest request, Model model) {
		logger.info("role controller modify access...");
		
		try {
			Role role = (Role) super.request2bean(request, Role.class);
			model.addAttribute(ISymbolConstant.INFO_CODE, roleService.modify(role));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "/system/role/list.jspx";
	}
	
	@RequestMapping(value = "/system/role/remove.jspx")
	public String remove(HttpServletRequest request, Model model) {
		logger.info("role controller remove access...");
		
		try {
			String ids = request.getParameter("ids");
			if (!StringHelper.isNull(ids)) {
				model.addAttribute(ISymbolConstant.INFO_CODE, roleService.remove(ids));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "/system/role/list.jspx";
	}

	@RequestMapping(value = "/system/role/synchronization.jspx")
	public @ResponseBody String synchronization(HttpServletRequest request, Model model) {
		logger.info("role controller synchronization access...");
		
		boolean result = false; 
		
		try {
			result = roleService.synchronization();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return String.valueOf(result);
	}
	
}
