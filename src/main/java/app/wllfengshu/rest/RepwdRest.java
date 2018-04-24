package app.wllfengshu.rest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import app.wllfengshu.exception.NotAcceptableException;
import app.wllfengshu.service.RepwdService;
import app.wllfengshu.util.LogUtils;

@Controller
@Path("/security/repwd")
public class RepwdRest {
	
	@Autowired
	private RepwdService repwdService ;
    
	/**
	 * @title 修改密码
	 * @param upuser 旧密码-新密码-确认密码
	 * @param request
	 * @param response
	 * @return
	 */
	@Path("/")
    @POST
    public Response repwd(String upuser,
    		@HeaderParam(value="user_id")String user_id,
    		@HeaderParam(value="sessionId") String sessionId,
    		@Context HttpServletRequest request,@Context HttpServletResponse response) {
		String responseStr = null;
		try{
			responseStr=repwdService.repwd(upuser,user_id,sessionId);
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