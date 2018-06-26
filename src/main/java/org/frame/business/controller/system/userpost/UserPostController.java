package org.frame.business.controller.system.userpost;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.frame.business.constant.IBusinessConstant;
import org.frame.business.model.system.UserPost;
import org.frame.business.service.system.dictionary.IDictionaryService;
import org.frame.business.service.system.user.IUserService;
import org.frame.business.service.system.userpost.IUserPostService;
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
public class UserPostController extends BaseController {
	
	private Log logger = LogFactory.getLog(UserPostController.class);
	
	@Resource
	@Autowired
	IDictionaryService dictionaryService;
	
	@Resource
	@Autowired
	IUserService userService;
	
	@Resource
	@Autowired
	IUserPostService userPostService;
	
	@RequestMapping(value = "/system/userpost/abandon.jspx")
	public String abandon(HttpServletRequest request, Model model) {
		logger.info("userPost controller abandon access...");
		
		String sign = request.getParameter("flag");
		
		try {
			String ids = request.getParameter("ids");
			Integer flag = Integer.parseInt(sign);
			if (!StringHelper.isNull(ids)) {
				model.addAttribute(ISymbolConstant.INFO_CODE, userPostService.abandon(ids, flag));
			}
		} catch (NumberFormatException e) {
			logger.error("flag invalid: " + sign);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "/system/userpost/list.jspx";
	}
	
	@RequestMapping(value = "/system/userpost/assign.jspx")
	public @ResponseBody String assign(HttpServletRequest request, Model model) {
		logger.info("userPost controller edit access...");
		
		String result = "";
		
		String user = request.getParameter("userid");
		String posts = request.getParameter("posts");
		
		String post = request.getParameter("postid");
		String users = request.getParameter("users");
		
		Long postid, userid;
		try {
			if (!StringHelper.isNull(user)) {
				userid = Long.parseLong(user);
				result = userPostService.assign(userid, posts, null, null);
			} else {
				logger.info("user id invalid: " + user);
			}
			
			if (!StringHelper.isNull(post)) {
				postid = Long.parseLong(post);
				result = userPostService.assign(null, null, postid, users);
			} else {
				logger.info("post id invalid: " + post);
			}
		} catch (NumberFormatException e) {
			logger.info("post id or user id invalid, post id: " + post + ", user id: " + user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@RequestMapping(value = "/system/userpost/detail.jspx")
	public String detail(HttpServletRequest request, Model model) {
		logger.info("userPost controller detail access...");
		
		String ids = request.getParameter("ids");
		
		try {
			Long id = null;
			UserPost userPost = null;
			
			if (!StringHelper.isNull(ids)) {
				id = Long.parseLong(ids.split(",")[0]);
				userPost = userPostService.search(id);
			} else {
				userPost = new UserPost();
				userPost.setFlag(ISymbolConstant.FLAG_AVAILABLE);
			}

			String related = new Config(IBusinessConstant.DEFAULT_CONFIG_PROPERTIES).get(IBusinessConstant.USER_RELATED).toLowerCase();
			if (related == null) related = "";
			if (!ISymbolConstant.FLAG_ALL.equals(related) && !related.contains("department")) {
				model.addAttribute("user", userService.query(null, null, null, ISymbolConstant.FLAG_AVAILABLE));
			}
			
			model.addAttribute("userPost", userPost);
		} catch (NumberFormatException e) {
			logger.error("detail id invalid: " + ids);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "detail.jsp";
	}

	@RequestMapping(value = "/system/userpost/list.jspx")
	public String list(HttpServletRequest request, Model model) {
		logger.info("userPost controller list access...");

		Page page = (Page) request.getAttribute(IWebConstant.PAGE_CODE);
		
		String post = request.getParameter("query_postid");
		String user = request.getParameter("query_userid");
		String sign = request.getParameter("query_flag");
		
		String postname = request.getParameter("query_post");
		String username = request.getParameter("query_user");
		
		try {
			Integer flag;
			Long postid, userid;

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
			model.addAttribute("query_post",post);
			model.addAttribute("query_postid",postid);
			model.addAttribute("query_user", user);
			model.addAttribute("query_userid", userid);
			model.addAttribute("flag", dictionaryService.query("FLAG"));
			model.addAttribute(IWebConstant.PAGE_CODE, userPostService.pagination(userid, username, postid, postname, flag, page));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "/system/userpost/list.jsp";
	}

	@RequestMapping(value = "/system/userpost/load.jspx")
	public @ResponseBody String load(HttpServletRequest request) {
		logger.info("userPost controller load access...");
		
		String result = "[]";
		
		String post = request.getParameter("postid");
		String user = request.getParameter("userid");
		
		try {
			Long postid, userid;
			
			if (!StringHelper.isNull(post)) {
				postid = Long.parseLong(post);
				if (postid == -1) postid = null;
			} else {
				postid = null;
			}
			
			if (!StringHelper.isNull(user)) {
				userid = Long.parseLong(user);
				if (userid == -1) userid = null;
			} else {
				userid = null;
			}
			
			List<UserPost> data = userPostService.query(userid, null, postid, null, ISymbolConstant.FLAG_AVAILABLE);
			result = new JSON().toJsonString(data);
		} catch (NumberFormatException e) {
			logger.error("user id or post id invalid, user id: " + user + ", post id: " + post);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	@RequestMapping(value = "/system/userpost/modify.jspx")
	public String modify(HttpServletRequest request, Model model) {
		logger.info("userPost controller modify access...");
		
		try {
			UserPost userPost = (UserPost) super.request2bean(request, UserPost.class);
			model.addAttribute(ISymbolConstant.INFO_CODE, userPostService.modify(userPost));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "/system/userpost/list.jspx";
	}
	
	@RequestMapping(value = "/system/userpost/remove.jspx")
	public String remove(HttpServletRequest request, Model model) {
		logger.info("userPost controller remove access...");
		
		try {
			String ids = request.getParameter("ids");
			if (!StringHelper.isNull(ids)) {
				model.addAttribute(ISymbolConstant.INFO_CODE, userPostService.remove(ids));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "/system/userpost/list.jspx";
	}

	@RequestMapping(value = "/system/userpost/synchronization.jspx")
	public @ResponseBody String synchronization(HttpServletRequest request, Model model) {
		logger.info("userpost controller synchronization access...");
		
		boolean result = false; 
		
		try {
			result = userPostService.synchronization();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return String.valueOf(result);
	}
	
}
