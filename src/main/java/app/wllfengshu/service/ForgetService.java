package app.wllfengshu.service;

import app.wllfengshu.exception.NotAcceptableException;

public interface ForgetService {

	public String forget(String forget) throws NotAcceptableException;

	public String sendEmail(String email) throws NotAcceptableException;
	
}
