package app.wllfengshu.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import app.wllfengshu.entity.User;

@Repository
public interface ForgetDao {

	void forget(@Param("username")String username);

	User getUserByEamil(@Param("email")String email);

}
