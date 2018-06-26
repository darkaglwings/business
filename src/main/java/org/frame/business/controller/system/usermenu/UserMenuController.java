package org.frame.business.controller.system.usermenu;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.frame.business.constant.IBusinessConstant;
import org.frame.business.model.system.UserMenu;
import org.frame.business.service.system.dictionary.IDictionaryService;
import org.frame.business.service.system.user.IUserService;
import org.frame.business.service.system.usermenu.IUserMenuService;
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
public class UserMenuController extends BaseController {
	
	private Log logger = LogFactory.getLog(UserMenuController.class);
	
	@Resource
	@Autowired
	IDictionaryService dictionaryService;
	
	@Resource
	@Autowired
	IUserService userService;
	
	@Resource
	@Autowired
	IUserMenuService userMenuService;

	@RequestMapping(value = "/system/usermenu/abandon.jspx")
	public String abandon(HttpServletRequest request, Model model) {
		logger.info("userMenu controller abandon access...");
		
		String sign = request.getParameter("flag");
		
		try {
			String ids = request.getParameter("ids");
			Integer flag = Integer.parseInt(sign);
			if (!StringHelper.isNull(ids)) {
				model.addAttribute(ISymbolConstant.INFO_CODE, userMenuService.abandon(ids, flag));
			}
		} catch (NumberFormatException e) {
			logger.error("flag invalid: " + sign);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "/system/usermenu/list.jspx";
	}
	
	@RequestMapping(value = "/system/usermenu/assign.jspx")
	public @ResponseBody String assign(HttpServletRequest request, Model model) {
		logger.info("userMenu controller assign access...");
		
		String result = "";
		
		String user = request.getParameter("userid");
		String menus = request.getParameter("menu");
		
		String menu = request.getParameter("menuid");
		String users = request.getParameter("user");
		
		Long userid, menuid;
		try {
			if (!StringHelper.isNull(user)) {
				userid = Long.parseLong(user);
				
				result = userMenuService.assign(userid, menus, null, null);
			} else {
				logger.info("userid invalid: " + user);
			}
			
			if (!StringHelper.isNull(menu)) {
				menuid = Long.parseLong(menu);
				
				result = userMenuService.assign(null, null, menuid, users);
			} else {
				logger.info("menuid invalid: " + menu);
			}
		} catch (NumberFormatException e) {
			logger.info("user id or menu id invalid, user id: " + user + ", menu id: " + menu);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@RequestMapping(value = "/system/usermenu/detail.jspx")
	public String detail(HttpServletRequest request, Model model) {
		logger.info("userMenu controller detail access...");
		
		String ids = request.getParameter("ids");
		
		try {
			Long id = null;
			UserMenu userMenu = null;
			
			if (!StringHelper.isNull(ids)) {
				id = Long.parseLong(ids.split(",")[0]);
				userMenu = userMenuService.search(id);
			} else {
				userMenu = new UserMenu();
				userMenu.setFlag(ISymbolConstant.FLAG_AVAILABLE);
			}
			
			String related = new Config(IBusinessConstant.DEFAULT_CONFIG_PROPERTIES).get(IBusinessConstant.USER_RELATED).toLowerCase();
			if (related == null) related = "";
			if (!ISymbolConstant.FLAG_ALL.equals(related) && !related.contains("department")) {
				model.addAttribute("user", userService.query(null, null, null, ISymbolConstant.FLAG_AVAILABLE));
			}
			
			model.addAttribute("userMenu", userMenu);
		} catch (NumberFormatException e) {
			logger.error("detail id invalid: " + ids);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "detail.jsp";
	}

	@RequestMapping(value = "/system/usermenu/list.jspx")
	public String list(HttpServletRequest request, Model model) {
		logger.info("userMenu controller list access...");

		Page page = (Page) request.getAttribute(IWebConstant.PAGE_CODE);
		
		String menu = request.getParameter("query_menuid");
		String user = request.getParameter("query_userid");
		String sign = request.getParameter("query_flag");
		
		String menuname = request.getParameter("query_menu");
		String username = request.getParameter("query_user");
		
		try {
			Integer flag;
			Long userid, menuid;

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
			model.addAttribute("query_menu", menu);
			model.addAttribute("query_menuid", menuid);
			model.addAttribute("query_user",user);
			model.addAttribute("query_userid",userid);
			model.addAttribute("flag", dictionaryService.query("FLAG"));
			model.addAttribute(IWebConstant.PAGE_CODE, userMenuService.pagination(userid, username, menuid, menuname, flag, page));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "/system/usermenu/list.jsp";
	}

	@RequestMapping(value = "/system/usermenu/load.jspx")
	public @ResponseBody String load(HttpServletRequest request, Model model) {
		logger.info("usermenu controller load access...");
		
		String result = "[]";
		
		String menu = request.getParameter("menuid");
		String user = request.getParameter("id");
		
		try {
			Long menuid, userid;
			
			if (!StringHelper.isNull(menu)) {
				menuid = Long.parseLong(menu);
				if (menuid == -1) menuid = null;
			} else {
				menuid = null;
			}
			
			if (!StringHelper.isNull(user)) {
				userid = Long.parseLong(user);
				if (userid == -1) userid = null;
			} else {
				userid = null;
			}
			
			List<UserMenu> data = userMenuService.query(userid, null, menuid, null, ISymbolConstant.FLAG_AVAILABLE);
			result = new JSON().toJsonString(data);
		}  catch (NumberFormatException e) {
			logger.error("user id or menu id invalid, user id: " + user + ", menu id: " + menu);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@RequestMapping(value = "/system/usermenu/modify.jspx")
	public String modify(HttpServletRequest request, Model model) {
		logger.info("userMenu controller modify access...");
		
		try {
			UserMenu userMenu = (UserMenu) super.request2bean(request, UserMenu.class);
			model.addAttribute(ISymbolConstant.INFO_CODE, userMenuService.modify(userMenu));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "/system/usermenu/list.jspx";
	}
	
	@RequestMapping(value = "/system/usermenu/remove.jspx")
	public String remove(HttpServletRequest request, Model model) {
		logger.info("userMenu controller remove access...");
		
		try {
			String ids = request.getParameter("ids");
			if (!StringHelper.isNull(ids)) {
				model.addAttribute(ISymbolConstant.INFO_CODE, userMenuService.remove(ids));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "/system/usermenu/list.jspx";
	}

	@RequestMapping(value = "/system/usermenu/synchronization.jspx")
	public @ResponseBody String synchronization(HttpServletRequest request, Model model) {
		logger.info("department controller synchronization access...");
		
		boolean result = false; 
		
		try {
			result = userMenuService.synchronization();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return String.valueOf(result);
	}
	
}