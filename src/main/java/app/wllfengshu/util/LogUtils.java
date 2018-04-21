package app.wllfengshu.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Liuyg
 * @mail lyg210@msn.cn
 * @version
 * @time Oct 18, 2014
 *
 */
public class LogUtils {
	
	public static void main(String[] args) {
		LogUtils.trace(LogUtils.class, "test,%s,%s","string1","string2");
		LogUtils.error(LogUtils.class, new Exception(), "aaaaaaa", "asd");
	}
	
	private static Logger logger =null; 
	
	public static void trace(Object obj,String msg,Object...params) {
		if(obj==null){
			logger = LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
		}else{
			logger = LoggerFactory.getLogger(obj.getClass());
		}
		logger.trace(String.format("-->> %s",String.format(msg, params)));
	}

	public static void debug(Object obj,String msg,Object...params) {
		if(obj==null){
			logger = LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
		}else{
			logger = LoggerFactory.getLogger(obj.getClass());
		}
		logger.debug(String.format("-->> %s",String.format(msg, params)));
	}

	public static void info(Object obj,String msg,Object...params) {
		if(obj==null){
			logger = LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
		}else{
			logger = LoggerFactory.getLogger(obj.getClass());
		}
		logger.info(String.format("-->> %s",String.format(msg, params)));
	}

	public static void warn(Object obj,String msg,Object...params) {
		if(obj==null){
			logger = LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
		}else{
			logger = LoggerFactory.getLogger(obj.getClass());
		}
		logger.warn(String.format("-->> %s",String.format(msg, params)));
	}

	public static void error(Object obj,Throwable e,String msg,Object...params) {
		if(obj==null){
			logger = LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
		}else{
			logger = LoggerFactory.getLogger(obj.getClass());
		}
		logger.error(String.format("-->> %s",String.format(msg, params)),e);
	}
}
