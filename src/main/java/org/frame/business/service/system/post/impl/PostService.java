package org.frame.business.service.system.post.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.frame.business.constant.IBusinessConstant;
import org.frame.business.dao.system.post.IPostDao;
import org.frame.business.dao.system.postmenu.IPostMenuDao;
import org.frame.business.dao.system.userpost.IUserPostDao;
import org.frame.business.model.system.Post;
import org.frame.business.service.synchronization.impl.SynchronizationService;
import org.frame.business.service.system.post.IPostService;
import org.frame.business.service.system.userpost.IUserPostService;
import org.frame.business.system.Config;
import org.frame.common.constant.ISymbolConstant;
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


@Repository("postService")
@Scope("singleton")
public class PostService extends BaseService implements IPostService {
	
	@SuppressWarnings("unused")
	private Log logger = LogFactory.getLog(PostService.class);
	
	@Resource
	@Autowired
	IPostDao postDao;
	
	@Resource
	@Autowired
	IPostMenuDao postMenuDao;
	
	@Resource
	@Autowired
	IUserPostDao userPostDao;
	
	@Resource
	@Autowired
	IUserPostService userPostService;
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor = {Exception.class})
	public String abandon(String ids, Integer flag) {
		String result = "";
		if (!StringHelper.isNull(ids)) {
			boolean menu = false, user = false;

			Config config = new Config(IBusinessConstant.DEFAULT_CONFIG_PROPERTIES);
			String related = config.get(IBusinessConstant.MENU_RELATED).toLowerCase();
			if (related == null) related = "";
			if ("all".equals(related) || related.contains("post")) {
				menu = true;
			}

			related = config.get(IBusinessConstant.USER_RELATED).toLowerCase();
			if (related == null) related = "";
			if (!StringHelper.isNull(related)) {
				if ("all".equals(related) || related.contains("post")) {
					user = true;
				}
			}

			List<Map<String, Object>> parameters = new ArrayList<Map<String, Object>>();
			Map<String, Object> map;

			if (menu) {
				parameters.clear();
				for (String id : ids.split(",")) {
					map = new HashMap<String, Object>();
					map.put("id", null);
					map.put("postid", Long.parseLong(id));
					map.put("menuid", null);
					map.put("flag", flag);

					parameters.add(map);
				}

				postMenuDao.abandon(parameters);
			}

			if (user) {
				parameters.clear();
				for (String id : ids.split(",")) {
					map = new HashMap<String, Object>();
					map.put("id", null);
					map.put("postid", Long.parseLong(id));
					map.put("userid", null);
					map.put("flag", flag);

					parameters.add(map);
				}

				userPostDao.abandon(parameters);
			}

			parameters.clear();
			for (String id : ids.split(",")) {
				map = new HashMap<String, Object>();
				map.put("id", Long.parseLong(id));
				map.put("flag", flag);

				parameters.add(map);
			}

			result = new StringHelper().join(postDao.abandon(parameters).toArray());
		}

		return result;
	}
	
	@Override
	public Long create(Post post) {
		return postDao.insert(post);
	}
	
	@Override
	public Long edit(Post post) {
		Integer result = postDao.update(post);
		return result > 0 ? post.getId() : result.longValue();
	}
	
	@Override
	public Long modify(Post post) {
		if (post.getParentid() == null) {
			post.setFullname(post.getTitle());
		} else {
			Post parent = this.search(post.getParentid());
			if (parent != null) {
				if (parent.getFullname() != null && !"".equals(parent.getFullname())) {
					post.setFullname(parent.getFullname() + "/" + post.getTitle());
				} else {
					post.setFullname(post.getTitle());
				}
			} else {
				post.setFullname(post.getTitle());
			}
		}
		
		if (StringHelper.isNull(String.valueOf(post.getId())))
			return this.create(post);
		else
			return this.edit(post);
	}
	
	@Override
	public Page pagination(Long parentid, String parentname, String title, Integer flag, Page page) {
		return postDao.pagination(parentid, parentname, title, flag, page);
	}

	@Override
	public List<Post> query(Long userid) {
		return postDao.select(userid);
	}
	
	@Override
	public List<Post> query(Long parentid, String parentname, String title, Integer flag) {
		return postDao.select(parentid, parentname, title, flag);
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor = {Exception.class})
	public String remove(String ids) {
		String result = "";
		if (!StringHelper.isNull(ids)) {
			boolean menu = false, user = false;

			Config config = new Config(IBusinessConstant.DEFAULT_CONFIG_PROPERTIES);
			String related = config.get(IBusinessConstant.MENU_RELATED).toLowerCase();
			if (related == null) related = "";
			if (ISymbolConstant.FLAG_ALL.equals(related) || related.contains("post")) {
				menu = true;
			}

			related = config.get(IBusinessConstant.USER_RELATED).toLowerCase();
			if (related == null) related = "";
			if (ISymbolConstant.FLAG_ALL.equals(related) || related.contains("post")) {
				user = true;
			}
			
			String pattern = config.get(IBusinessConstant.RELATED_TYPE_POST).toLowerCase();
			
			List<Map<String, Object>> parameters = new ArrayList<Map<String, Object>>();
			Map<String, Object> map;

			if (menu) {
				parameters.clear();
				for (String id : ids.split(",")) {
					map = new HashMap<String, Object>();
					map.put("id", null);
					map.put("postid", Long.parseLong(id));
					map.put("menuid", null);

					parameters.add(map);
				}

				postMenuDao.delete(parameters);
			}

			if (user && ISymbolConstant.FLAG_MULTI.equals(pattern)) {
				parameters.clear();
				for (String id : ids.split(",")) {
					map = new HashMap<String, Object>();
					map.put("id", null);
					map.put("postid", Long.parseLong(id));
					map.put("userid", null);

					parameters.add(map);
				}

				userPostDao.delete(parameters);
			}

			parameters.clear();
			for (String id : ids.split(",")) {
				map = new HashMap<String, Object>();
				map.put("id", Long.parseLong(id));

				parameters.add(map);
			}

			result = new StringHelper().join(postDao.delete(parameters).toArray());
		}
		
		return result;
	}
	
	@Override
	public Post search(Long id) {
		return postDao.find(id);
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
						xmlBinding.xml2file(out.getAbsolutePath() + "/export_post" + suffix + ".xml", "<?xml version=\"1.0\" encoding=\"UTF-8\"?><export/>");
					}
				}
			}
		}
		
		if (passage != null && passage.contains(IBusinessConstant.SYNCHRONIZATION_PASSAGE_WEBSERVICE)) {
			String url = config.get(IBusinessConstant.SYNCHRONIZATION_URL);
			if (url != null && !"".equals(url) && !"null".equals(url)) {
				String namespace = "http://impl.synchronization.server.webservice.frame.org";
				
				Client client = new Client();
				Object[] info = client.invoke(url, namespace, "exportPost", new Class<?>[]{}, new Object[]{});
				if (info != null && info.length > 0) {
					new SynchronizationService().importPost(String.valueOf(info[0]));
				} else {
					System.err.println("no post info found.");
				}
			}
		}
		
		result = true;
		
		return result;
	}
	
}
