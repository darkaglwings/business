package org.frame.business.controller.system.post;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.frame.business.model.system.Post;
import org.frame.business.service.system.dictionary.IDictionaryService;
import org.frame.business.service.system.post.IPostService;
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
public class PostController extends BaseController {
	
	private Log logger = LogFactory.getLog(PostController.class);
	
	@Resource
	@Autowired
	private IDictionaryService dictionaryService;
	
	@Resource
	@Autowired
	IPostService postService;
	
	@RequestMapping(value = "/system/post/abandon.jspx")
	public String abandon(HttpServletRequest request, Model model) {
		logger.info("post controller abandon access...");
		
		String sign = request.getParameter("flag");
		
		try {
			String ids = request.getParameter("ids");
			Integer flag = Integer.parseInt(sign);
			if (!StringHelper.isNull(ids)) {
				model.addAttribute(ISymbolConstant.INFO_CODE, postService.abandon(ids, flag));
			}
		} catch (NumberFormatException e) {
			logger.error("flag invalid: " + sign);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "/system/post/list.jspx";
	}
	
	@RequestMapping(value = "/system/post/detail.jspx")
	public String detail(HttpServletRequest request, Model model) {
		logger.info("post controller detail access...");
		
		String ids = request.getParameter("ids");
		
		try {
			Long id = null;
			Post post = null;
			
			if (!StringHelper.isNull(ids)) {
				id = Long.parseLong(ids.split(",")[0]);
				post = postService.search(id);
			} else {
				post = new Post();
				post.setFlag(ISymbolConstant.FLAG_AVAILABLE);
			}
			
			model.addAttribute("post", post);
		} catch (NumberFormatException e) {
			logger.error("detail id invalid: " + ids);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "detail.jsp";
	}

	@RequestMapping(value = "/system/post/list.jspx")
	public String list(HttpServletRequest request, Model model) {
		logger.info("post controller list access...");
		
		Page page = (Page) request.getAttribute(IWebConstant.PAGE_CODE);

		String parent = request.getParameter("query_parentid");
		String sign = request.getParameter("query_flag");
		
		String parentname = request.getParameter("query_parentname");
		String title = request.getParameter("query_title");
		try {
			Integer flag;
			Long parentid;
			
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
					logger.error("parent id invalid. parent id: " + sign);
				}
			} else {
				parentid = null;
			}
			
			if (StringHelper.isNull(parentname)) {
				parentname = null;
			}
			
			if (StringHelper.isNull(title)) {
				title = null;
			}
			
			model.addAttribute("query_flag", flag);
			model.addAttribute("query_parentid", parentid);
			model.addAttribute("query_parentname", parentname);
			model.addAttribute("query_title", title);
			model.addAttribute("flag", dictionaryService.query("FLAG"));
			model.addAttribute(IWebConstant.PAGE_CODE, postService.pagination(parentid, parentname, title, flag, page));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "/system/post/list.jsp";
	}

	@RequestMapping(value = "/system/post/load.jspx")
	public @ResponseBody String load(HttpServletRequest request, Model model) {
		logger.info("post controller load access...");
		
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
			
			List<Post> data = postService.query(parentid, null, null, ISymbolConstant.FLAG_AVAILABLE);
			result = new TreeDataFormat().format(data);
		} catch (NumberFormatException e) {
			logger.error("parent id invalid: " + parent);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@RequestMapping(value = "/system/post/modify.jspx")
	public String modify(HttpServletRequest request, Model model) {
		logger.info("post controller modify access...");
		
		try {
			Post post = (Post) super.request2bean(request, Post.class);
			model.addAttribute(ISymbolConstant.INFO_CODE, postService.modify(post));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "/system/post/list.jspx";
	}
	
	@RequestMapping(value = "/system/post/remove.jspx")
	public String remove(HttpServletRequest request, Model model) {
		logger.info("post controller remove access...");
		
		try {
			String ids = request.getParameter("ids");
			if (!StringHelper.isNull(ids)) {
				model.addAttribute(ISymbolConstant.INFO_CODE, postService.remove(ids));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "/system/post/list.jspx";
	}
	
	@RequestMapping(value = "/system/post/synchronization.jspx")
	public @ResponseBody String synchronization(HttpServletRequest request, Model model) {
		logger.info("post controller synchronization access...");
		
		boolean result = false; 
		
		try {
			result = postService.synchronization();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return String.valueOf(result);
	}

}
