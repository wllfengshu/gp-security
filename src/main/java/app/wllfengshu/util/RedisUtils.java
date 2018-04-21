package app.wllfengshu.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import app.wllfengshu.entity.Login;
import redis.clients.jedis.Jedis;


public class RedisUtils {
	//连接到redis服务  
    private static Jedis jedis = null;  
	
    static{
    	jedis=new Jedis("127.0.0.1"); 
    	System.out.println("Redis连接成功"+jedis.ping());  
        System.out.println("--------------------------");  
    }
    
	/**
	 * @title 把登陆的bean存入redis中
	 * @param loginEntity
	 */
	public static void putLogin(Login loginEntity) {
		jedis.set(loginEntity.getId().getBytes(), serialize(loginEntity));
	}

	/**
	 * @title 通过sessionId获取登陆的bean
	 * @param sessionId
	 * @return
	 */
	public static Login getLogin(String sessionId) {
		byte[] byt=jedis.get(sessionId.getBytes());
        Object obj=unserizlize(byt);
        if(obj instanceof Login){
        	return (Login) obj;
        }
		return null;
	}
	
	
	//序列化 
    public static byte [] serialize(Object obj){
        ObjectOutputStream obi=null;
        ByteArrayOutputStream bai=null;
        try {
            bai=new ByteArrayOutputStream();
            obi=new ObjectOutputStream(bai);
            obi.writeObject(obj);
            byte[] byt=bai.toByteArray();
            return byt;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    //反序列化
    public static Object unserizlize(byte[] byt){
        ObjectInputStream oii=null;
        ByteArrayInputStream bis=null;
        bis=new ByteArrayInputStream(byt);
        try {
            oii=new ObjectInputStream(bis);
            Object obj=oii.readObject();
            return obj;
        } catch (Exception e) {
            
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @title 测试取数据
     */
    public static String getTest(String testKey){
    	return jedis.get(testKey);
    }
    /**
     * @title 测试存数据
     */
    public static void  putTest(String testKey,String testValue) {
		jedis.set(testKey,testValue);
	}
    
}
