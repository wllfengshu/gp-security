package app.wllfengshu.service;

import app.wllfengshu.exception.NotAcceptableException;

public interface RepwdService {

	public String repwd(String upuser, String user_id, String sessionId) throws NotAcceptableException;
	
}
