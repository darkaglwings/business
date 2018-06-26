package org.frame.business.service.system.userpost.impl;

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
import org.frame.business.dao.system.user.IUserDao;
import org.frame.business.dao.system.userpost.IUserPostDao;
import org.frame.business.model.system.User;
import org.frame.business.model.system.UserPost;
import org.frame.business.service.synchronization.impl.SynchronizationService;
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


@Repository("userPostService")
@Scope("singleton")
public class UserPostService extends BaseService implements IUserPostService {

	private Log logger = LogFactory.getLog(UserPostService.class);
	
	@Resource
	@Autowired
	IPostDao postDao;
	
	@Resource
	@Autowired
	IUserPostDao userPostDao;
	
	@Resource
	@Autowired
	IUserDao userDao;
	
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
					map.put("postid", null);
					map.put("userid", null);
					map.put("flag", flag);

					parameters.add(map);
				} catch (NumberFormatException e) {
					logger.error("id invalid: " + ids);
				}
			}

			result = new StringHelper().join(userPostDao.abandon(parameters).toArray());
		}

		return result;
	}
	
	@Override
	public String abandon(Long id, Long userid, Long postid, Integer flag) {
		List<Map<String, Object>> parameters = new ArrayList<Map<String, Object>>();
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("postid", postid);
		map.put("userid", userid);
		map.put("flag", flag);

		parameters.add(map);
		
		return new StringHelper().join(userPostDao.abandon(parameters).toArray());
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor = {Exception.class})
	public String assign(Long userid, String posts, Long postid, String users) {
		String result = null;
		
		String pattern = new Config(IBusinessConstant.DEFAULT_CONFIG_PROPERTIES).get(IBusinessConstant.RELATED_TYPE_POST).toLowerCase();
		
		if (userid != null && !"".equals(userid) && !"null".equals(userid)) {
			User user = userDao.find(userid);

			if (ISymbolConstant.FLAG_MULTI.equals(pattern)) {
				String[] post;
				if (!StringHelper.isNull(posts)) {
					post = posts.split(",");
				} else {
					post = new String[]{};
				}
				UserPost userPost = new UserPost();
				userPost.setFlag(user.getFlag());
				
				List<Map<String, Object>> parameters = new ArrayList<Map<String, Object>>();
				
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", null);
				map.put("postid", postid);
				map.put("userid", userid);

				parameters.add(map);
				
				result = new StringHelper().join(userPostDao.delete(parameters).toArray());
				for (String id : post) {
					userPost.setUser(userid);
					userPost.setPost(Long.parseLong(id));

					result += "," + userPostDao.insert(userPost);
				}
			}

			if (user != null) {
				user.setPost(posts);
				userDao.update(user);
			}
		}

		if (postid != null && !"".equals(postid) && !"null".equals(postid)) {
			String post = "";
			
			String[] user;
			if (!StringHelper.isNull(users)) {
				user = users.split(",");
			} else {
				user = new String[]{};
			}
			
			boolean effected = false;
			List<User> effectUsers = userDao.select(null, null, String.valueOf(postid), null);
			
			if (ISymbolConstant.FLAG_MULTI.equals(pattern)) {
				UserPost userPost = new UserPost();
				userPost.setFlag(postDao.find(postid).getFlag());
				
				List<Map<String, Object>> parameters = new ArrayList<Map<String, Object>>();
				
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", null);
				map.put("postid", postid);
				map.put("userid", userid);

				parameters.add(map);
				
				result = new StringHelper().join(userPostDao.delete(parameters).toArray());
				
				parameters.clear();
				for (String id : user) {
					userPost.setUser(Long.parseLong(id));
					userPost.setPost(postid);

					if (ISymbolConstant.FLAG_SINGLE.equals(pattern)) {
						map = new HashMap<String, Object>();
						map.put("id", null);
						map.put("postid", null);
						map.put("userid", Long.parseLong(id));

						parameters.add(map);
						
						userPostDao.delete(parameters);
					}

					result += "," + userPostDao.insert(userPost);
					
					effected = false;
					
					for (User effectUser : effectUsers) {
						if (effectUser.getId() == Long.parseLong(id)) {
							effected = true;
							break;
						}
					}
					
					if (!effected) {
						User u = userDao.find(Long.parseLong(id));
						effectUsers.add(u);
					}
					
				}
			} else {
				for (String id : user) {
					for (User effectUser : effectUsers) {
						if (effectUser.getId() == Long.parseLong(id)) {
							effected = true;
							break;
						}
					}
					
					if (!effected) {
						User u = userDao.find(Long.parseLong(id));
						effectUsers.add(u);
					}
				}
			}
			
			for (User effectUser : effectUsers) {
				List<UserPost> data = this.query(effectUser.getId(), null, null, null, null);
				if (data != null) {
					post = "";
					for (UserPost up : data) {
						if ("".equals(post)) {
							post = String.valueOf(up.getPost());
						} else {
							post += "," + String.valueOf(up.getPost());
						}
					}
				}

				if (StringHelper.isNull(post)) post = null;
				
				effectUser.setPost(post);
				userDao.update(effectUser);
			}
		}
		
		return result;
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor = {Exception.class})
	public Long create(UserPost userPost) {
		Long result = userPostDao.insert(userPost);

		String post = "";
		List<UserPost> data = this.query(userPost.getUser(), null, null, null, null);
		if (data != null) {
			for (UserPost up : data) {
				if ("".equals(post)) {
					post = String.valueOf(up.getPost());
				} else {
					post += "," + String.valueOf(up.getPost());
				}
			}

			User user = userDao.find(userPost.getUser());
			if (user != null) {
				user.setPost(post);
				userDao.update(user);
			}
		}
		
		return result;
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor = {Exception.class})
	public Long edit(UserPost userPost) {
		Integer result = userPostDao.update(userPost);

		String post = "";
		List<UserPost> data = this.query(userPost.getUser(), null, null, null, null);
		if (data != null) {
			for (UserPost up : data) {
				if ("".equals(post)) {
					post = String.valueOf(up.getPost());
				} else {
					post += "," + String.valueOf(up.getPost());
				}
			}

			User user = userDao.find(userPost.getUser());
			if (user != null) {
				user.setPost(post);
				userDao.update(user);
			}
		}
		
		return result > 0 ? userPost.getId() : result.longValue();
	}
	
	@Override
	public Long modify(UserPost userPost) {
		if (StringHelper.isNull(String.valueOf(userPost.getId())))
			return this.create(userPost);
		else
			return this.edit(userPost);
	}
	
	@Override
	public Page pagination(Long userid, String user, Long postid, String post, Integer flag, Page page) {
		return userPostDao.pagination(userid, user, postid, post, flag, page);
	}

	@Override
	public List<UserPost> query(Long userid, String user, Long postid, String post, Integer flag) {
		return userPostDao.select(userid, user, postid, post, flag);
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor = {Exception.class})
	public String remove(String ids) {
		String result = "";
		if (!StringHelper.isNull(ids)) {
			for (String id : ids.split(",")) {
				UserPost userPost = this.search(Long.parseLong(id));
				
				List<Map<String, Object>> parameters = new ArrayList<Map<String, Object>>();
				
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", Long.parseLong(id));
				map.put("postid", null);
				map.put("userid", null);

				parameters.add(map);
				
				result += new StringHelper().join(userPostDao.delete(parameters).toArray()) + ",";

				try {
					String post = "";
					List<UserPost> data = this.query(userPost.getUser(), null, null, null, null);
					if (data != null) {
						for (UserPost up : data) {
							if ("".equals(post)) {
								post = String.valueOf(up.getPost());
							} else {
								post += "," + String.valueOf(up.getPost());
							}
						}

						User user = userDao.find(userPost.getUser());
						if (user != null) {
							user.setPost(post);
							userDao.update(user);
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			result = result.substring(0, result.length() - 1);
		}
		
		return result;
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor = {Exception.class})
	public String remove(Long id, Long userid, Long postid) {
		String result = null;
		if (id != null) {
			UserPost userPost = this.search(id);
			if (userPost != null) {
				userid = userPost.getUser();
			}
		}

		List<UserPost> data = this.query(userid, null, postid, null, null);

		List<Map<String, Object>> parameters = new ArrayList<Map<String, Object>>();
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("postid", postid);
		map.put("userid", userid);

		parameters.add(map);
		
		result = new StringHelper().join(userPostDao.delete(parameters).toArray());
		
		if (data != null) {
			String post = "";
			List<Long> lstUser = new ArrayList<Long>();
			if (userid == null) {
				for (UserPost userPost : data) {
					if (!lstUser.contains(userPost.getUser())) lstUser.add(userPost.getUser());
				}
			} else {
				if (!lstUser.contains(userid)) lstUser.add(userid);
			}

			for (Long uid : lstUser) {
				post = "";
				List<UserPost> lstData;

				if (userid == null) {
					lstData = this.query(uid, null, null, null, null);
				} else {
					lstData = data;
				}

				if (lstData != null) {
					for (UserPost userPost : lstData) {
						if ("".equals(post)) {
							post = String.valueOf(userPost.getPost());
						} else {
							post += "," + String.valueOf(userPost.getPost());
						}
					}

					User user = userDao.find(uid);
					if (user != null) {
						user.setPost(post);
						userDao.update(user);
					}
				}
			}
		}

		return result;
	}
	
	@Override
	public UserPost search(Long id) {
		return userPostDao.find(id);
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
						xmlBinding.xml2file(out.getAbsolutePath() + "/export_userpost" + suffix + ".xml", "<?xml version=\"1.0\" encoding=\"UTF-8\"?><export/>");
					}
				}
			}
		}
		
		if (passage != null && passage.contains(IBusinessConstant.SYNCHRONIZATION_PASSAGE_WEBSERVICE)) {
			String url = config.get(IBusinessConstant.SYNCHRONIZATION_URL);
			if (url != null && !"".equals(url) && !"null".equals(url)) {
				String namespace = "http://impl.synchronization.server.webservice.frame.org";
				
				Client client = new Client();
				Object[] info = client.invoke(url, namespace, "exportUserPost", new Class<?>[]{}, new Object[]{});
				if (info != null && info.length > 0) {
					new SynchronizationService().importUserPost(String.valueOf(info[0]));
				} else {
					System.err.println("no userpost info found.");
				}
			}
		}
		
		result = true;
		
		return result;
	}
	
}
