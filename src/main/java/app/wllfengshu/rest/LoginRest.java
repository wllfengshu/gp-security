package app.wllfengshu.rest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import app.wllfengshu.exception.NotAcceptableException;
import app.wllfengshu.service.LoginService;
import app.wllfengshu.util.LogUtils;

@Controller
@Path("/security")
public class LoginRest {
	
	@Autowired
	private LoginService loginService ;
    
	/**
	 * @title 用户登陆
	 * @param user 用户信息-账号和密码
	 * @param request
	 * @param response
	 * @return
	 */
	@Path("/login")
    @POST
    public Response login(String user,
    		@Context HttpServletRequest request,@Context HttpServletResponse response) {
		response.setHeader("Access-Control-Allow-Origin","*");
        response.setHeader("Access-Control-Allow-Methods","GET,POST,PUT,OPTIONS");
        response.setHeader("Access-Control-Allow-Credentials","true");
		String responseStr = null;
		try{
			responseStr=loginService.login(user);
		}catch (NotAcceptableException e) {
			System.out.println(e);
			return Response.serverError().entity("{\"message\":\""+e.getMessage()+"\",\"timestamp\":\""+System.currentTimeMillis()+"\"}").status(406).build();
		}catch (Exception e) {
			System.out.println(e);
			LogUtils.error(this, e, "# UserRest-getUsers,has exception #");
			return Response.serverError().entity("{\"message\":\"has exception\",\"timestamp\":\""+System.currentTimeMillis()+"\"}").status(500).build();
		}
		return Response.ok(responseStr, MediaType.APPLICATION_JSON)
        		.status(200).build();
    }
    
}
