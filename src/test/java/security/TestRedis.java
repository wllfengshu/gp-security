package security;

import org.junit.Test;

import app.wllfengshu.util.RedisUtils;

public class TestRedis {
	
	@Test
	public void testPut(){
		RedisUtils.putTest("liang", "testLiang");
		System.out.println("成功存入");
		System.out.println("成功取回："+RedisUtils.getTest("liang"));
	}
}
