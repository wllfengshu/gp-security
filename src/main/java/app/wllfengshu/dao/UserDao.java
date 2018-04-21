package app.wllfengshu.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import app.wllfengshu.entity.User;

@Repository
public interface UserDao {
	public List<User> getUsers(@Param("domain")String domain);

	public void addUser(@Param("user")User user);

	public User getUser(@Param("id")String id);

	public void updateUser(@Param("User")User user);

	public void deleteUser(@Param("id")String id);
	
}
