package org.frame.business.controller.system.dictionary;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.frame.business.model.system.Dictionary;
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
public class DictionaryController extends BaseController {
	
	private Log logger = LogFactory.getLog(DictionaryController.class);
	
	@Resource
	@Autowired
	IDictionaryService dictionaryService;

	@RequestMapping(value = "/system/dictionary/abandon.jspx")
	public String abandon(HttpServletRequest request, Model model) {
		logger.info("dictionaryService controller abandon access...");
		
		String sign = request.getParameter("flag");
		
		try {
			String ids = request.getParameter("ids");
			Integer flag = Integer.parseInt(sign);
			if (!StringHelper.isNull(ids)) {
				model.addAttribute(ISymbolConstant.INFO_CODE, dictionaryService.abandon(ids, flag));
			}
		} catch (NumberFormatException e) {
			logger.error("flag invalid: " + sign);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "/system/dictionary/list.jspx";
	}
	
	@RequestMapping(value = "/system/dictionary/detail.jspx")
	public String detail(HttpServletRequest request, Model model) {
		logger.info("dictionary controller detail access...");
		
		String ids = request.getParameter("ids");
		String sort = request.getParameter("parent_sort");
		
		try {
			Long id = null;
			Dictionary dictionary = null;
			Dictionary parent = null;
			
			if (!StringHelper.isNull(ids)) {
				id = Long.parseLong(ids.split(",")[0]);
				dictionary = dictionaryService.search(id);
				
				if (dictionary.getParentid() != null) {
					parent = dictionaryService.search(dictionary.getParentid());
					if (parent != null) sort = parent.getSort();
				}
			} else {
				dictionary = new Dictionary();
				dictionary.setFlag(ISymbolConstant.FLAG_AVAILABLE);
			}
			
			if (StringHelper.isNull(sort)) {
				model.addAttribute("type", null);
			} else {
				model.addAttribute("type", dictionaryService.query(sort));
			}
			
			model.addAttribute("sort", dictionaryService.group());
			model.addAttribute("parent_sort", sort);
			model.addAttribute("dictionary", dictionary);
		} catch (NumberFormatException e) {
			logger.error("detail id invalid: " + ids);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "detail.jsp";
	}

	@RequestMapping(value = "/system/dictionary/list.jspx")
	public String list(HttpServletRequest request, Model model) {
		logger.info("dictionary controller list access...");
		
		Page page = (Page) request.getAttribute(IWebConstant.PAGE_CODE);

		String sign = request.getParameter("query_flag");
		
		try {
			Integer flag;
			String parentname, sort;
			
			if (!StringHelper.isNull(sign)) {
				try {
					flag = Integer.parseInt(sign);
				} catch (NumberFormatException e) {
					flag = null;
					logger.error("flag invalid: " + sign);
				}
				
				if (flag != null && flag == -1) flag = null;
			} else {
				flag = null;
			}
			
			if (!StringHelper.isNull(request.getParameter("query_parentname"))) {
				parentname = request.getParameter("query_parentname");
			} else {
				parentname = null;
			}
			
			if (!StringHelper.isNull(request.getParameter("query_sort"))) {
				sort = request.getParameter("query_sort");
			} else {
				sort = null;
			}
			
			model.addAttribute("query_flag", flag);
			model.addAttribute("query_parentname", parentname);
			model.addAttribute("query_sort", sort);
			model.addAttribute("flag", dictionaryService.query("FLAG"));
			model.addAttribute(IWebConstant.PAGE_CODE, dictionaryService.pagination(parentname, sort, flag, page));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "/system/dictionary/list.jsp";
	}

	@RequestMapping(value = "/system/dictionary/load.jspx")
	public @ResponseBody String load(HttpServletRequest request, Model model) {
		logger.info("dictionary controller load access...");
		
		String result = "[]";
		
		String parent = request.getParameter("parent_id");
		Long parentid;
		try {
			parentid = Long.parseLong(parent);
		} catch (NumberFormatException e) {
			parentid = null;
			//logger.error("parent id invalid: " + parent);
		}
		
		String sort = request.getParameter("parent_sort");
		
		List<Dictionary> data = dictionaryService.query(parentid, sort);
		result = new JSON().toJsonString(data);
		
		return result;
	}
	
	@RequestMapping(value = "/system/dictionary/modify.jspx")
	public String modify(HttpServletRequest request, Model model) {
		logger.info("dictionary controller modify access...");
		
		try {
			Dictionary dictionary = (Dictionary) super.request2bean(request, Dictionary.class);
			if (dictionary.getParentid() != null && dictionary.getParentid() == -1) dictionary.setParentid(null);
			model.addAttribute(ISymbolConstant.INFO_CODE, dictionaryService.modify(dictionary));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "/system/dictionary/list.jspx";
	}
	
	@RequestMapping(value = "/system/dictionary/remove.jspx")
	public String remove(HttpServletRequest request, Model model) {
		logger.info("dictionary controller remove access...");
		
		try {
			String ids = request.getParameter("ids");
			if (!StringHelper.isNull(ids)) {
				model.addAttribute(ISymbolConstant.INFO_CODE, dictionaryService.remove(ids));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "/system/dictionary/list.jspx";
	}

	@RequestMapping(value = "/system/dictionary/synchronization.jspx")
	public @ResponseBody String synchronization(HttpServletRequest request, Model model) {
		logger.info("dictionary controller synchronization access...");
		
		boolean result = false; 
		
		try {
			result = dictionaryService.synchronization();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return String.valueOf(result);
	}
	
}
