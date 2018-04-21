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
		User userTemp=null;
		String username=null;
		String password=null;
		try{
			userJson=JSONObject.fromObject(user);
			userTemp=(User) JSONObject.toBean(userJson,User.class);
			username=userTemp.getUsername();
			password=userTemp.getPassword();
			if (null==username || null==password) {
				throw new NotAcceptableException("用户名或者密码为空");
			}
		}catch(Exception e){
			throw new NotAcceptableException("数据格式错误");
		}
		//1去mysql中获取用户信息、角色信息、权限信息、部门信息（如果查询数据为空，直接返回登陆失败）
		User userEntity = loginDao.login(username,password);
		if (userEntity!=null) {
			Login loginEntity=new Login();
			loginEntity.setId(sessionId);//login的id就是sessionId
			loginEntity.setPastTime(System.currentTimeMillis());
			loginEntity.setUser(userEntity);
			//4、把上述数据都存放在Redis中
			try{
				RedisUtils.putLogin(loginEntity);
			}catch(Exception e){
				System.out.println("Redis异常");
			}
			//5、返回登陆成功，并把sessionId
			responseMap.put("sessionId", sessionId);
		}else{
			//5、直接返回失败
			responseMap.put("sessionId", "");
		}
		responseMap.put("timestamp", String.valueOf(System.currentTimeMillis()));
		return gson.toJson(responseMap);
	}

}
