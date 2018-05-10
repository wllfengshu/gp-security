package app.wllfengshu.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import app.wllfengshu.dao.UserDao;
import app.wllfengshu.entity.Login;
import app.wllfengshu.entity.User;
import app.wllfengshu.exception.NotAcceptableException;
import app.wllfengshu.util.RedisUtils;
import net.sf.json.JSONObject;

@Service
public class UserServiceImpl implements UserService {
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
	
	@Autowired
	private UserDao userDao;
	
	/**
	 * @title 检查sessionId是否过期
	 * @param loginEntity 登陆的bean
	 * @return
	 * @throws NotAcceptableException 
	 */
	private void checkSessionId(Login loginEntity) throws NotAcceptableException{
		if (null==loginEntity) {
			throw new NotAcceptableException("未登陆");
		}
		long currentTimeMillis = System.currentTimeMillis();
		long pastTime = loginEntity.getPastTime();
		if (currentTimeMillis-pastTime>1800000) {//30分钟  30*60*1000
			throw new NotAcceptableException("Session过期");
		}
	}
	
	/**
	 * @title 检查角色权限
	 * @param loginEntity
	 * @param roleToken
	 * @throws NotAcceptableException
	 */
	private void checkRole(Login loginEntity,String ... roleToken) throws NotAcceptableException{
		User user = loginEntity.getUser();
		if ("manage".equals(user.getSys())) {
			return;
		}
		String role_name = user.getRoles().get(0).getRole_name();//一个用户只有一个角色
		boolean success=false;//是否拥有权限的标志，默认没有权限
		for (String strTemp : roleToken) {
			if (strTemp.equals(role_name)) {
				success=true;
				break;
			}
		}
		if (!success) {
			throw new NotAcceptableException("没有权限");
		}
	}
	
	@Override
	public String getUsers(String sessionId,String tenant_id,String username,int pageNo,int pageSize) throws NotAcceptableException {
		Map<String,Object> responseMap = new HashMap<String,Object>();
		//1、使用sessionId去redis中查询Login的bean
		Login loginEntity =RedisUtils.getLogin(sessionId);
		//1.1判断角色，该操作只能由租户管理员操作
		checkRole(loginEntity, "tm");
		//2、检查登陆
		checkSessionId(loginEntity);
		//3、获取所有user信息
		List<User> users = userDao.getUsers(tenant_id,username,(pageNo-1)*pageSize,pageSize);
		responseMap.put("data", users);
		responseMap.put("count", userDao.getUsersCount(tenant_id,username));
		responseMap.put("timestamp", String.valueOf(System.currentTimeMillis()));
		return gson.toJson(responseMap);
	}
	
	@Override
	public String addUser(String user,String sessionId,String token) throws NotAcceptableException {
		Map<String,Object> responseMap = new HashMap<String,Object>();
		//1、使用sessionId去redis中查询Login的bean
		Login loginEntity =RedisUtils.getLogin(sessionId);
		//1.1判断角色，该操作只能由租户管理员操作
		checkRole(loginEntity, "tm");
		//2、检查登陆
		checkSessionId(loginEntity);
		JSONObject userJson = null;
		User userTemp=null;
		if (null!=token && ""!=token && "tm".equals(token)) {
			//3、准备user数据
			String id = UUID.randomUUID().toString();
			String domain=loginEntity.getUser().getDomain();
			String tenant_id = loginEntity.getUser().getTenant_id();
			userJson=JSONObject.fromObject(user);
			String roles=userJson.getString("roles");
			System.out.println("添加的角色是"+roles);
			userJson.remove("roles");
			//整理数据
			userTemp=(User) JSONObject.toBean(userJson,User.class);
			userTemp.setId(id);
			userTemp.setLast_activity_time(sdf.format(new Date(System.currentTimeMillis())));
			userTemp.setDomain(domain);
			userTemp.setStatus(1);
			userTemp.setSex("男");
			userTemp.setTenant_id(tenant_id);
			userTemp.setOrg_id("1");
			userTemp.setPassword("Aa123456");
			userTemp.setSys("crm");
			System.out.println("添加的user信息是"+userTemp.toString());
		}else{
			userJson=JSONObject.fromObject(user);
			userTemp=(User) JSONObject.toBean(userJson,User.class);
			System.out.println("添加的user信息是"+userTemp.toString());
		}
		userDao.addUser(userTemp);
		responseMap.put("timestamp", String.valueOf(System.currentTimeMillis()));
		return gson.toJson(responseMap);
	}
	
	@Override
	public String getUser(String user_id,String sessionId,String token) throws NotAcceptableException {
		System.out.println("正在获取user信息user_id="+user_id+" sessionId="+sessionId);
		Map<String,Object> responseMap = new HashMap<String,Object>();
		//1、使用sessionId去redis中查询Login的bean
		Login loginEntity =RedisUtils.getLogin(sessionId);
		//1.1判断角色，该操作租户管理员、坐席、质检员操作
		checkRole(loginEntity, new String[]{"tm","agent","qc"});
		//2、检查登陆
		checkSessionId(loginEntity);
		//3、获取数据
		User user = loginEntity.getUser();
		System.out.println("security认证通过，已经发送user信息"+user.toString());
		responseMap.put("data", user);
		responseMap.put("timestamp", String.valueOf(System.currentTimeMillis()));
		return gson.toJson(responseMap);
	}
	
	@Override
	public String updateUser(String user,String sessionId) throws NotAcceptableException {
		Map<String,Object> responseMap = new HashMap<String,Object>();
		//1、使用sessionId去redis中查询Login的bean
		Login loginEntity =RedisUtils.getLogin(sessionId);
		//1.1判断角色，该操作租户管理员、坐席操作
		checkRole(loginEntity, new String[]{"tm","agent","qc"});
		//2、检查登陆
		checkSessionId(loginEntity);
		//3、准备数据
		JSONObject userJson = null;
		User userTemp=null;
		try{
			userJson=JSONObject.fromObject(user);
			userTemp=(User) JSONObject.toBean(userJson,User.class);
		}catch(Exception e){
			throw new NotAcceptableException("数据格式错误");
		}
		userDao.updateUser(userTemp);
		responseMap.put("timestamp", String.valueOf(System.currentTimeMillis()));
		return gson.toJson(responseMap);
	}
	
	@Override
	public String deleteUser(String user_id,String sessionId) throws NotAcceptableException {
		Map<String,Object> responseMap = new HashMap<String,Object>();
		//1、使用sessionId去redis中查询Login的bean
		Login loginEntity =RedisUtils.getLogin(sessionId);
		//1.1判断角色，该操作只能由租户管理员操作
		checkRole(loginEntity, "tm");
		//2、检查登陆
		checkSessionId(loginEntity);
		//3、删除数据
		userDao.deleteUser(user_id);
		responseMap.put("timestamp", String.valueOf(System.currentTimeMillis()));
		return gson.toJson(responseMap);
	}

}
