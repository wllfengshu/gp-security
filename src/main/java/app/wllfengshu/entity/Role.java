package app.wllfengshu.entity;

import java.io.Serializable;

public class Role implements Serializable{
	private static final long serialVersionUID = 1L;
	private String id;
	private String role_explain;
	private String role_name;
	private Integer status;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getRole_explain() {
		return role_explain;
	}
	public void setRole_explain(String role_explain) {
		this.role_explain = role_explain;
	}
	public String getRole_name() {
		return role_name;
	}
	public void setRole_name(String role_name) {
		this.role_name = role_name;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
}
