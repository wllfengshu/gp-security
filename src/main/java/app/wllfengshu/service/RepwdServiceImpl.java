package app.wllfengshu.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import app.wllfengshu.dao.RepwdDao;
import app.wllfengshu.entity.Login;
import app.wllfengshu.exception.NotAcceptableException;
import app.wllfengshu.util.RedisUtils;
import net.sf.json.JSONObject;

@Service
public class RepwdServiceImpl implements RepwdService {
	private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
	
	@Autowired
	private RepwdDao repwdDao;
	
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
	
	@Override
	public String repwd(String upuser,String user_id,String sessionId) throws NotAcceptableException {
		Map<String,Object> responseMap = new HashMap<String,Object>();
		//1判断密码和确认密码是否相同
		JSONObject upuserJson = JSONObject.fromObject(upuser);
		String oldPwd=upuserJson.getString("oldPwd");
		String newPwd1=upuserJson.getString("newPwd1");
		String newPwd2=upuserJson.getString("newPwd2");
		if (oldPwd.equals("") || oldPwd==null || newPwd1.equals("") || newPwd1==null || newPwd2.equals("") || newPwd2==null) {
			throw new NotAcceptableException("密码存在空");
		}
		if (!newPwd1.equals(newPwd2)) {
			throw new NotAcceptableException("两次密码不一致");
		}
		//2使用旧密码去Redis中查询
		Login loginEntity =RedisUtils.getLogin(sessionId);
		String password = loginEntity.getUser().getPassword();
		if (!password.equals(oldPwd)) {
			throw new NotAcceptableException("旧密码输入错误");
		}
		//3检验sessionId
		checkSessionId(loginEntity);
		//4去Mysql数据库中修改密码（不用修改Redis，因为密码重置后需要重新登录，Redis中数据已经失效）
		repwdDao.repwd(user_id,newPwd1);
		responseMap.put("timestamp", String.valueOf(System.currentTimeMillis()));
		return gson.toJson(responseMap);
	}

}
