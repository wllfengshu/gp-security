package app.wllfengshu.service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import app.wllfengshu.dao.LoginDao;
import app.wllfengshu.entity.Login;
import app.wllfengshu.entity.User;
import app.wllfengshu.exception.NotAcceptableException;
import app.wllfengshu.util.RedisUtils;
import net.sf.json.JSONObject;

@Service
public class LoginServiceImpl implements LoginService {
	private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
	
	@Autowired
	private LoginDao loginDao;

	@Override
	public String login(String user) throws NotAcceptableException {
		Map<String,Object> responseMap = new HashMap<String,Object>();
		String sessionId = UUID.randomUUID().toString();
		if (null==user || "".equals(user)) {
			throw new NotAcceptableException("没有数据");
		}
		JSONObject userJson = null;
		String domain=null;
		String username=null;
		String password=null;
		String token=null;//用于判断对应的角色登录才能登录对应的系统
		try{
			userJson=JSONObject.fromObject(user);
			token=userJson.getString("token");
			domain=userJson.getString("domain");
			username=userJson.getString("username");
			password=userJson.getString("password");
			if (null==username || null==password) {
				throw new NotAcceptableException("用户名或者密码为空");
			}
		}catch(Exception e){
			throw new NotAcceptableException("数据格式错误");
		}
		//1去mysql中获取用户信息、角色信息、权限信息、部门信息（如果查询数据为空，直接返回登陆失败）
		User userEntity =null;
		if (token.equals("crm")) {
			if (null==domain || "".equals(domain)) {
				throw new NotAcceptableException("域名不能为空");
			}
			userEntity=loginDao.login(username,password,domain);
		}else if (token.equals("tm")) {
			userEntity=loginDao.login(username,password,"");
		}else if (token.equals("manage")) {
			userEntity=loginDao.login(username,password,"");
		}else {
			throw new NotAcceptableException("凭证错误");
		}
		if (userEntity!=null) {
			Login loginEntity=new Login();
			loginEntity.setId(sessionId);//login的id就是sessionId
			loginEntity.setPastTime(System.currentTimeMillis());
			loginEntity.setUser(userEntity);
			//1.1对登录者角色进行控制
			if (!token.equals(loginEntity.getUser().getSys())) {
				throw new NotAcceptableException("权限异常");
			}
			//4、把上述数据都存放在Redis中
			try{
				RedisUtils.putLogin(loginEntity);
			}catch(Exception e){
				System.out.println("Redis异常");
				throw new NotAcceptableException("Redis异常");
			}
			//5、返回登陆成功，并把user相关信息都返回
			responseMap.put("loginEntity", loginEntity);
		}else{
			//5、直接返回失败
			System.out.println("登录失败");
			throw new NotAcceptableException("登录失败");
		}
		responseMap.put("timestamp", String.valueOf(System.currentTimeMillis()));
		System.out.println(gson.toJson(responseMap));
		return gson.toJson(responseMap);
	}

}
