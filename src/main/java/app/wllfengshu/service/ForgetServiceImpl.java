package app.wllfengshu.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import app.wllfengshu.dao.ForgetDao;
import app.wllfengshu.entity.User;
import app.wllfengshu.exception.NotAcceptableException;
import app.wllfengshu.util.MailUtils;

@Service
public class ForgetServiceImpl implements ForgetService {
	private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
	private String activeUrl = "http://localhost:9001/security/forget/forget";
	
	@Autowired
	private ForgetDao forgetDao;
	
	@Override
	public String forget(String username) throws NotAcceptableException {
		Map<String,Object> responseMap = new HashMap<String,Object>();
		forgetDao.forget(username);
		responseMap.put("timestamp", String.valueOf(System.currentTimeMillis()));
		return gson.toJson(responseMap);
	}

	@Override
	public String sendEmail(String email) throws NotAcceptableException {
		Map<String,Object> responseMap = new HashMap<String,Object>();
		//1检查系统中是否存在该邮箱地址
		User user=forgetDao.getUserByEamil(email);
		if (user==null || "".equals(user)) {
			throw new NotAcceptableException("邮件不存在");
		}
		//2向该邮箱发送邮件，注意，邮箱中的链接上必须携带username
		String emailContent="";
		emailContent+="<h1>密码重置<//h1>";
		emailContent+="<h2>您正在进行密码重置操作，如果是您本人操作，请点击文中链接重置密码，点击后您的密码将会被重置为初始密码；如果不是您本人操作，请忽略本邮件。<//h2>";
		emailContent+="<a href=\"";
		emailContent+=(activeUrl+"?username="+user.getUsername());
		emailContent+="\">点我重置密码</a>";
		MailUtils.sendMail("密码重置", emailContent, email);
		responseMap.put("timestamp", String.valueOf(System.currentTimeMillis()));
		return gson.toJson(responseMap);
	}

}
