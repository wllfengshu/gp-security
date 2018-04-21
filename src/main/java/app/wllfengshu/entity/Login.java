package app.wllfengshu.entity;

import java.io.Serializable;

/**
 * @title 这是一个登陆的实体类，包含用户信息、角色信息、权限信息、部门信息
 */
public class Login  implements Serializable{
	private static final long serialVersionUID = 1L;
	private String id;//是sessionId
	private long pastTime;//用来判断sessionId是否过期
	private User user;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public long getPastTime() {
		return pastTime;
	}
	public void setPastTime(long pastTime) {
		this.pastTime = pastTime;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
}
