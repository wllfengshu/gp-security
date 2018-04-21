package app.wllfengshu.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import app.wllfengshu.entity.User;

@Repository
public interface LoginDao {
	public User login(@Param("username")String username,@Param("password")String password);

}
