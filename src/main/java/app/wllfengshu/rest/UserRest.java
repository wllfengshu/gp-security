package app.wllfengshu.rest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import app.wllfengshu.exception.NotAcceptableException;
import app.wllfengshu.service.UserService;
import app.wllfengshu.util.LogUtils;

/**
 * @title 这里的用户指的是租户管理员和坐席（包括质检员）,这些接口只能由管理员调用，即租户控制台调用
 */
@Controller
@Path("/security/user")
public class UserRest {
	
	@Autowired
	private UserService userService ;
    
	/**
	 * @title 查询所有用户信息
	 * @param sessionId
	 * @param request
	 * @param response
	 * @return
	 */
    @GET
    public Response getusers(@HeaderParam(value="sessionid") String sessionId,
    		@Context HttpServletRequest request,@Context HttpServletResponse response) {
		String responseStr = null;
		try{
			responseStr=userService.getUsers(sessionId);
		}catch (NotAcceptableException e) {
			System.out.println(e);
			return Response.serverError().entity("{\"message\":\""+e.getMessage()+"\",\"timestamp\":\""+System.currentTimeMillis()+"\"}").status(406).build();
		}catch (Exception e) {
			System.out.println(e);
			LogUtils.error(this, e, "# userRest-getusers,has exception #");
			return Response.serverError().entity("{\"message\":\"has exception\",\"timestamp\":\""+System.currentTimeMillis()+"\"}").status(500).build();
		}
		return Response.ok(responseStr, MediaType.APPLICATION_JSON)
        		.status(200).build();
    }
    
    /**
     * @title 添加用户信息
     * @param user 用户信息数据
     * @param sessionId
     * @param request
     * @param response
     * @return
     */
    @POST
    public Response adduser(String user,
    		@HeaderParam(value="sessionid") String sessionId,
    		@Context HttpServletRequest request,@Context HttpServletResponse response) {
		String responseStr = null;
		try{
			responseStr=userService.addUser(user,sessionId);
		}catch (NotAcceptableException e) {
			System.out.println(e);
			return Response.serverError().entity("{\"message\":\""+e.getMessage()+"\",\"timestamp\":\""+System.currentTimeMillis()+"\"}").status(406).build();
		}catch (Exception e) {
			System.out.println(e);
			LogUtils.error(this, e, "# userRest-adduser,has exception #");
			return Response.serverError().entity("{\"message\":\"has exception\",\"timestamp\":\""+System.currentTimeMillis()+"\"}").status(500).build();
		}
		return Response.ok(responseStr, MediaType.APPLICATION_JSON)
        		.status(200).build();
    }
    
    /**
     * @title 查询用户详情
     * @param user_id 用户ID
     * @param sessionId
     * @param request
     * @param response
     * @return
     */
    @GET
    @Path("/{user_id}/")
    public Response getuser(@PathParam("user_id")String user_id,
    		@HeaderParam(value="sessionid") String sessionId,
    		@Context HttpServletRequest request,@Context HttpServletResponse response) {
		String responseStr = null;
		try{
			responseStr=userService.getUser(user_id,sessionId);
		}catch (NotAcceptableException e) {
			System.out.println(e);
			return Response.serverError().entity("{\"message\":\""+e.getMessage()+"\",\"timestamp\":\""+System.currentTimeMillis()+"\"}").status(406).build();
		}catch (Exception e) {
			System.out.println(e);
			LogUtils.error(this, e, "# userRest-getuser,has exception #");
			return Response.serverError().entity("{\"message\":\"has exception\",\"timestamp\":\""+System.currentTimeMillis()+"\"}").status(500).build();
		}
		return Response.ok(responseStr, MediaType.APPLICATION_JSON)
        		.status(200).build();
    }
    
    /**
     * @title 修改用户信息
     * @param user 用户信息数据
     * @param sessionId
     * @param request
     * @param response
     * @return
     */
    @PUT
    @Path("/{user_id}/")
    public Response updateuser(String user,
    		@HeaderParam(value="sessionid") String sessionId,
    		@Context HttpServletRequest request,@Context HttpServletResponse response) {
		String responseStr = null;
		try{
			responseStr=userService.updateUser(user,sessionId);//前端的user中必须包含user_id
		}catch (NotAcceptableException e) {
			System.out.println(e);
			return Response.serverError().entity("{\"message\":\""+e.getMessage()+"\",\"timestamp\":\""+System.currentTimeMillis()+"\"}").status(406).build();
		}catch (Exception e) {
			System.out.println(e);
			LogUtils.error(this, e, "# userRest-updateuser,has exception #");
			return Response.serverError().entity("{\"message\":\"has exception\",\"timestamp\":\""+System.currentTimeMillis()+"\"}").status(500).build();
		}
		return Response.ok(responseStr, MediaType.APPLICATION_JSON)
        		.status(200).build();
    }
    
    /**
     * @title 删除用户信息
     * @param user_id 用户ID
     * @param sessionId
     * @param request
     * @param response
     * @return
     */
    @DELETE
    @Path("/{user_id}/")
    public Response deleteuser(@PathParam("user_id")String user_id,
    		@HeaderParam(value="sessionid") String sessionId,
    		@Context HttpServletRequest request,@Context HttpServletResponse response) {
		String responseStr = null;
		try{
			responseStr=userService.deleteUser(user_id,sessionId);
		}catch (NotAcceptableException e) {
			System.out.println(e);
			return Response.serverError().entity("{\"message\":\""+e.getMessage()+"\",\"timestamp\":\""+System.currentTimeMillis()+"\"}").status(406).build();
		}catch (Exception e) {
			System.out.println(e);
			LogUtils.error(this, e, "# userRest-deleteuser,has exception #");
			return Response.serverError().entity("{\"message\":\"has exception\",\"timestamp\":\""+System.currentTimeMillis()+"\"}").status(500).build();
		}
		return Response.ok(responseStr, MediaType.APPLICATION_JSON)
        		.status(200).build();
    }
    
}
