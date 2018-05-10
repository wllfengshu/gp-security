package app.wllfengshu.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import app.wllfengshu.entity.User;

@Repository
public interface UserDao {
	public List<User> getUsers(@Param("tenant_id")String tenant_id,
			@Param("username")String username,
			@Param("pageStart")int pageStart,
			@Param("pageEnd")int pageEnd);
	
	public int getUsersCount(@Param("tenant_id")String tenant_id,
			@Param("username")String username);
	
	public void addUser(@Param("user")User user);

	public User getUser(@Param("id")String id);

	public void updateUser(@Param("User")User user);

	public void deleteUser(@Param("id")String id);
	
}
