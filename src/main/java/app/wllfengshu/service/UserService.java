package app.wllfengshu.service;

import app.wllfengshu.exception.NotAcceptableException;

public interface UserService {
	
	public String getUsers(String sessionId,String tenant_id,String username,int pageNo,int pageSize) throws NotAcceptableException;

	public String addUser(String user,String sessionId,String token) throws NotAcceptableException;

	public String getUser(String user_id,String sessionId,String token) throws NotAcceptableException;

	public String updateUser(String user,String sessionId) throws NotAcceptableException;

	public String deleteUser(String user_id,String sessionId) throws NotAcceptableException;
	
}
