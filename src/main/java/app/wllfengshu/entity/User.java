package app.wllfengshu.entity;

import java.io.Serializable;
import java.util.List;

public class User implements Serializable{
	private static final long serialVersionUID = 1L;
	private String id;
	private String email;
	private String sex;
	private String last_activity_time;
	private String login_name;
	private String domain;
	private String phone;
	private Integer status;
	private String tenant_id;
	private String username;
	private String password;
	private List<Role> roles;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getLast_activity_time() {
		return last_activity_time;
	}
	public void setLast_activity_time(String last_activity_time) {
		this.last_activity_time = last_activity_time;
	}
	public String getLogin_name() {
		return login_name;
	}
	public void setLogin_name(String login_name) {
		this.login_name = login_name;
	}
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getTenant_id() {
		return tenant_id;
	}
	public void setTenant_id(String tenant_id) {
		this.tenant_id = tenant_id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public List<Role> getRoles() {
		return roles;
	}
	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	
}
