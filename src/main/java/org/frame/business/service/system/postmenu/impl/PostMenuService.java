package org.frame.business.service.system.postmenu.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.frame.business.constant.IBusinessConstant;
import org.frame.business.dao.system.menu.IMenuDao;
import org.frame.business.dao.system.post.IPostDao;
import org.frame.business.dao.system.postmenu.IPostMenuDao;
import org.frame.business.model.system.PostMenu;
import org.frame.business.service.synchronization.impl.SynchronizationService;
import org.frame.business.service.system.postmenu.IPostMenuService;
import org.frame.business.system.Config;
import org.frame.common.lang.StringHelper;
import org.frame.common.util.Date;
import org.frame.common.webservice.client.Client;
import org.frame.common.xml.XMLBinding;
import org.frame.repository.sql.model.Page;
import org.frame.web.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Repository("postMenuService")
@Scope("singleton")
public class PostMenuService extends BaseService implements IPostMenuService {
	
	private Log logger = LogFactory.getLog(PostMenuService.class);
	
	@Resource
	@Autowired
	IMenuDao menuDao;
	
	@Resource
	@Autowired
	IPostDao postDao;
	
	@Resource
	@Autowired
	IPostMenuDao postMenuDao;

	@Override
	public String abandon(String ids, Integer flag) {
		String result = "";
		if (!StringHelper.isNull(ids)) {
			List<Map<String, Object>> parameters = new ArrayList<Map<String, Object>>();
			Map<String, Object> map;
			for (String id : ids.split(",")) {
				try {
					map = new HashMap<String, Object>();
					map.put("id", Long.parseLong(id));
					map.put("menuid", null);
					map.put("postid", null);
					map.put("flag", flag);

					parameters.add(map);
				} catch (NumberFormatException e) {
					logger.error("id invalid: " + ids);
				}
			}

			result = new StringHelper().join(postMenuDao.abandon(parameters).toArray());
		}

		return result;
	}
	
	@Override
	public String abandon(Long id, Long postid, Long menuid, Integer flag) {
		List<Map<String, Object>> parameters = new ArrayList<Map<String, Object>>();
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("menuid", menuid);
		map.put("postid", postid);
		map.put("flag", flag);
		
		return new StringHelper().join(postMenuDao.abandon(parameters).toArray());
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor = {Exception.class})
	public String assign(Long postid, String menus, Long menuid, String posts) {
		String result = null;
		
		List<Map<String, Object>> parameters = new ArrayList<Map<String, Object>>();
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", null);
		map.put("menuid", menuid);
		map.put("postid", postid);
		
		result = new StringHelper().join(postMenuDao.delete(parameters).toArray());

		if (postid != null && !"".equals(postid) && !"null".equals(postid)) {
			String[] menu;
			if (!StringHelper.isNull(menus)) {
				menu = menus.split(",");
			} else {
				menu = new String[]{};
			}
			PostMenu postMenu = new PostMenu();
			postMenu.setFlag(postDao.find(postid).getFlag());
			for (String id : menu) {
				postMenu.setPost(postid);
				postMenu.setMenu(Long.parseLong(id));

				result += "," + postMenuDao.insert(postMenu);
			}
		}

		if (menuid != null && !"".equals(menuid) && !"null".equals(menuid)) {
			String[] post;
			if (!StringHelper.isNull(posts)) {
				post = posts.split(",");
			} else {
				post = new String[]{};
			}
			PostMenu postMenu = new PostMenu();
			postMenu.setFlag(menuDao.find(postid).getFlag());
			for (String id : post) {
				postMenu.setPost(Long.parseLong(id));
				postMenu.setMenu(menuid);
				result += "," + postMenuDao.insert(postMenu);
			}
		}
		
		return result;
	}
	
	@Override
	public Long create(PostMenu postMenu) {
		return postMenuDao.insert(postMenu);
	}
	
	@Override
	public Long edit(PostMenu postMenu) {
		Integer result = postMenuDao.update(postMenu);
		return result > 0 ? postMenu.getId() : result.longValue();
	}
	
	@Override
	public Long modify(PostMenu postMenu) {
		if (StringHelper.isNull(String.valueOf(postMenu.getId())))
			return this.create(postMenu);
		else
			return this.edit(postMenu);
	}

	@Override
	public Page pagination(Long postid, String post, Long menuid, String menu, Integer flag, Page page) {
		return postMenuDao.pagination(postid, post, menuid, menu, flag, page);
	}
	
	@Override
	public List<PostMenu> query(Long postid, String post, Long menuid, String menu, Integer flag) {
		return postMenuDao.select(postid, post, menuid, menu, flag);
	}
	
	@Override
	public String remove(String ids) {
		String result = "";
		if (!StringHelper.isNull(ids)) {
			List<Map<String, Object>> parameters = new ArrayList<Map<String, Object>>();
			Map<String, Object> map;
			for (String id : ids.split(",")) {
				try {
					map = new HashMap<String, Object>();
					map.put("id", Long.parseLong(id));
					map.put("menuid", null);
					map.put("postid", null);

					parameters.add(map);
				} catch (NumberFormatException e) {
					logger.error("id invalid: " + ids);
				}
			}

			result = new StringHelper().join(postMenuDao.delete(parameters).toArray());
		}

		return result;
	}
	
	@Override
	public String remove(Long id, Long postid, Long menuid) {
		List<Map<String, Object>> parameters = new ArrayList<Map<String, Object>>();
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("menuid", menuid);
		map.put("postid", postid);
		
		return new StringHelper().join(postMenuDao.delete(parameters).toArray());
	}
	
	@Override
	public PostMenu search(Long id) {
		return postMenuDao.find(id);
	}

	@Override
	public boolean synchronization() {
		boolean result = false;
		
		Config config = new Config(IBusinessConstant.DEFAULT_CONFIG_PROPERTIES);
		String passage = config.get(IBusinessConstant.SYNCHRONIZATION_PASSAGE);
		if (passage != null && passage.contains(IBusinessConstant.SYNCHRONIZATION_PASSAGE_FILE)) {
			File out;
			String paths = config.get(IBusinessConstant.SYNCHRONIZATION_OUT);
			if (paths != null && !"".equals(paths)) {
				XMLBinding xmlBinding = new XMLBinding();
				String suffix = "_" + new Date().date2String("yyyyMMddHHmmss");
				for(String path : paths.split(",")) {
					if (path != null && !"".equals(path)) {
						out = new File(path);
						if (!out.exists()) out.mkdirs();
						xmlBinding.xml2file(out.getAbsolutePath() + "/export_postmenu" + suffix + ".xml", "<?xml version=\"1.0\" encoding=\"UTF-8\"?><export/>");
					}
				}
			}
		}
		
		if (passage != null && passage.contains(IBusinessConstant.SYNCHRONIZATION_PASSAGE_WEBSERVICE)) {
			String url = config.get(IBusinessConstant.SYNCHRONIZATION_URL);
			if (url != null && !"".equals(url) && !"null".equals(url)) {
				String namespace = "http://impl.synchronization.server.webservice.frame.org";
				
				Client client = new Client();
				Object[] info = client.invoke(url, namespace, "exportPostMenu", new Class<?>[]{}, new Object[]{});
				if (info != null && info.length > 0) {
					new SynchronizationService().importPostMenu(String.valueOf(info[0]));
				} else {
					System.err.println("no postmenu info found.");
				}
			}
		}
		
		result = true;
		
		return result;
	}
	
}
