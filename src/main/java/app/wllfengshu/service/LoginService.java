package app.wllfengshu.service;

import app.wllfengshu.exception.NotAcceptableException;

public interface LoginService {
	
	public String login(String user) throws NotAcceptableException;

}
