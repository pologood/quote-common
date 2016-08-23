package com.baidu.stock.quote.asyn;

import java.net.URL;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *  异步写出配置项
 * @author dengjianli
 *
 */
public class AsynConfig{
	private static final Log Logger = LogFactory.getLog(AsynConfig.class);
	private static AsynConfig config;
	public static final String ASYN_CONFIG = "asynConfig.properties";
	/**
	 * 写入器(桶)个数
	 */
	public  int writerCount=5;
	/**
	 * flush的间隔时间，单位毫秒
	 */
	public  int flushInterval =500;
	
	/**
	 * 消费者(桶)数量
	 */
	public  int consumerCount =1;
	
	/**
	 * 当记录数达到多少的时候写入数据库
	 */
	public  int bundleMaxCount =500;
	/**
	 * 队列缓存大小
	 */
	public  int queueBuffSize=10240;
	/**
	 * 实现方输出类路径
	 */
	public  String scheduleOutImplClassPath;
	
	
	public static AsynConfig getInstance(){
		return getInstance(ASYN_CONFIG);
	}
	
	public synchronized static AsynConfig getInstance(String conf){
		if (config == null)
			config = new AsynConfig(conf);
			
		return config;
	}
	
	private AsynConfig(String conf){
		Properties prop;
		try{
			prop = new Properties();
			URL url = Thread.currentThread().getContextClassLoader().getResource(conf);
			if (url != null)
				prop.load(url.openStream());
			 writerCount=Integer.parseInt(prop.getProperty("asyn.writerCount","3"));
			 flushInterval=Integer.parseInt(prop.getProperty("asyn.flushInterval","3"));	
			 consumerCount=Integer.parseInt(prop.getProperty("asyn.consumerCount","1"));
			 bundleMaxCount=Integer.parseInt(prop.getProperty("asyn.bundleMaxCount","10"));
			 queueBuffSize=Integer.parseInt(prop.getProperty("asyn.queueBuffSize","1024"));
			String implClassPath=prop.getProperty("asyn.scheduleOutImplClassPath");
			if(StringUtils.isBlank(implClassPath)){
				throw new RuntimeException("asynConfig.properties配置文件参数asyn.scheduleOutImplClassPath不能空,必须设置.");
			}else{
			 scheduleOutImplClassPath=implClassPath;
			}
		}
		catch(Exception ex){
			Logger.error("Load AsynWriter error!",ex);
			throw new java.lang.RuntimeException(ex);
		}
	}
	

	public int getWriterCount() {
		return writerCount;
	}

	public void setWriterCount(int writerCount) {
		this.writerCount = writerCount;
	}

	public int getFlushInterval() {
		return flushInterval;
	}

	public void setFlushInterval(int flushInterval) {
		this.flushInterval = flushInterval;
	}

	public int getConsumerCount() {
		return consumerCount;
	}

	public void setConsumerCount(int consumerCount) {
		this.consumerCount = consumerCount;
	}

	public int getBundleMaxCount() {
		return bundleMaxCount;
	}

	public void setBundleMaxCount(int bundleMaxCount) {
		this.bundleMaxCount = bundleMaxCount;
	}

	public int getQueueBuffSize() {
		return queueBuffSize;
	}

	public void setQueueBuffSize(int queueBuffSize) {
		this.queueBuffSize = queueBuffSize;
	}

	public String getScheduleOutImplClassPath() {
		return scheduleOutImplClassPath;
	}

	public void setScheduleOutImplClassPath(String scheduleOutImplClassPath) {
		this.scheduleOutImplClassPath = scheduleOutImplClassPath;
	}

	@Override
	public String toString(){
		StringBuffer result = new StringBuffer();
		result.append("flushInterval:").append(flushInterval)
			.append(",consumerCount:").append(consumerCount)
			.append(",writerCount:").append(writerCount)
			.append(",scheduleOutImplClassPath:").append(scheduleOutImplClassPath)
		.append(",bundleMaxCount:").append(bundleMaxCount);
		return result.toString();
	}
	
}
