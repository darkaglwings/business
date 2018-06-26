package org.frame.business.controller.system.postmenu;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.frame.business.model.system.PostMenu;
import org.frame.business.service.system.dictionary.IDictionaryService;
import org.frame.business.service.system.postmenu.IPostMenuService;
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
public class PostMenuController extends BaseController {
	
	private Log logger = LogFactory.getLog(PostMenuController.class);
	
	@Resource
	@Autowired
	IDictionaryService dictionaryService;
	
	@Resource
	@Autowired
	IPostMenuService postMenuService;
	
	@RequestMapping(value = "/system/postmenu/abandon.jspx")
	public String abandon(HttpServletRequest request, Model model) {
		logger.info("postMenu controller abandon access...");
		
		String sign = request.getParameter("flag");
		
		try {
			String ids = request.getParameter("ids");
			Integer flag = Integer.parseInt(sign);
			if (!StringHelper.isNull(ids)) {
				model.addAttribute(ISymbolConstant.INFO_CODE, postMenuService.abandon(ids, flag));
			}
		} catch (NumberFormatException e) {
			logger.error("flag invalid: " + request.getParameter("flag"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "/system/postmenu/list.jspx";
	}
	
	@RequestMapping(value = "/system/postmenu/assign.jspx")
	public @ResponseBody String assign(HttpServletRequest request, Model model) {
		logger.info("postMenu controller assign access...");
		
		String result = "";
		
		String post = request.getParameter("postid");
		String menus = request.getParameter("menu");
		
		String menu = request.getParameter("menuid");
		String posts = request.getParameter("post");
		
		Long postid, menuid;
		try {
			if (!StringHelper.isNull(post)) {
				postid = Long.parseLong(post);
				
				result = postMenuService.assign(postid, menus, null, null);
			} else {
				logger.info("post id invalid: " + post);
			}
			
			if (!StringHelper.isNull(menu)) {
				menuid = Long.parseLong(menu);
				
				result = postMenuService.assign(null, null, menuid, posts);
			} else {
				logger.info("menu id invalid: " + menu);
			}
		} catch (NumberFormatException e) {
			logger.info("post id or menu id invalid, post id: " + post + ", menu id: " + menu);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@RequestMapping(value = "/system/postmenu/detail.jspx")
	public String detail(HttpServletRequest request, Model model) {
		logger.info("postMenu controller detail access...");
		
		String ids = request.getParameter("ids");
		
		try {
			Long id = null;
			PostMenu postMenu = null;
			
			if (!StringHelper.isNull(ids)) {
				id = Long.parseLong(ids.split(",")[0]);
				postMenu = postMenuService.search(id);
			} else {
				postMenu = new PostMenu();
				postMenu.setFlag(ISymbolConstant.FLAG_AVAILABLE);
			}
			
			model.addAttribute("postMenu", postMenu);
		} catch (NumberFormatException e) {
			logger.error("detail id invalid: " + ids);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "detail.jsp";
	}

	@RequestMapping(value = "/system/postmenu/list.jspx")
	public String list(HttpServletRequest request, Model model) {
		logger.info("postMenu controller list access...");
		
		Page page = (Page) request.getAttribute(IWebConstant.PAGE_CODE);

		String menu = request.getParameter("query_menuid");
		String post = request.getParameter("query_postid");
		String sign = request.getParameter("query_flag");
		
		String menuname = request.getParameter("query_menu");
		String postname = request.getParameter("query_post");
		
		try {
			Integer flag;
			Long postid, menuid;
			
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
			
			if (!StringHelper.isNull(post)) {
				try {
					postid = Long.parseLong(post);
				} catch (NumberFormatException e) {
					postid = null;
					logger.error("post id invalid. post id: " + post);
				}
			} else {
				postid = null;
			}
			
			if (StringHelper.isNull(postname)) {
				postname = null;
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
			model.addAttribute("query_post",post);
			model.addAttribute("query_postid",postid);
			model.addAttribute("query_menu", menu);
			model.addAttribute("query_menuid", menuid);
			model.addAttribute("flag", dictionaryService.query("FLAG"));
			model.addAttribute(IWebConstant.PAGE_CODE, postMenuService.pagination(postid, postname, menuid, menuname, flag, page));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "/system/postmenu/list.jsp";
	}

	@RequestMapping(value = "/system/postmenu/load.jspx")
	public @ResponseBody String load(HttpServletRequest request, Model model) {
		logger.info("postmenu controller load access...");
		
		String result = "[]";
		
		String post = request.getParameter("id");
		String menu = request.getParameter("menuid");
		
		try {
			Long menuid, postid;
			
			if (!StringHelper.isNull(post)) {
				postid = Long.parseLong(post);
				if (postid == -1) postid = null;
			} else {
				postid = null;
			}
			
			if (!StringHelper.isNull(menu)) {
				menuid = Long.parseLong(menu);
				if (menuid == -1) menuid = null;
			} else {
				menuid = null;
			}
			
			List<PostMenu> data = postMenuService.query(postid, null, menuid, null, ISymbolConstant.FLAG_AVAILABLE);
			result = new JSON().toJsonString(data);
		} catch (NumberFormatException e) {
			logger.error("post id or menu id invalid. post id: " + post + ", menu id: " + menu);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@RequestMapping(value = "/system/postmenu/modify.jspx")
	public String modify(HttpServletRequest request, Model model) {
		logger.info("postMenu controller modify access...");
		
		try {
			PostMenu postMenu = (PostMenu) super.request2bean(request, PostMenu.class);
			model.addAttribute(ISymbolConstant.INFO_CODE, postMenuService.modify(postMenu));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "/system/postmenu/list.jspx";
	}
	
	@RequestMapping(value = "/system/postmenu/remove.jspx")
	public String remove(HttpServletRequest request, Model model) {
		logger.info("postMenu controller remove access...");
		
		try {
			String ids = request.getParameter("ids");
			if (!StringHelper.isNull(ids)) {
				model.addAttribute(ISymbolConstant.INFO_CODE, postMenuService.remove(ids));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "/system/postmenu/list.jspx";
	}

	@RequestMapping(value = "/system/postmenu/synchronization.jspx")
	public @ResponseBody String synchronization(HttpServletRequest request, Model model) {
		logger.info("postmenu controller synchronization access...");
		
		boolean result = false; 
		
		try {
			result = postMenuService.synchronization();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return String.valueOf(result);
	}
	
}
