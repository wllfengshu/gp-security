package app.wllfengshu.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RepwdDao {

	void repwd(@Param("user_id")String user_id,@Param("password")String password);

}
