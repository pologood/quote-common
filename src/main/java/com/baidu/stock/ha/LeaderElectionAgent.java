package com.baidu.stock.ha;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.leader.LeaderLatch;
import org.apache.curator.framework.recipes.leader.LeaderLatchListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class LeaderElectionAgent implements LeaderLatchListener {
	
	private final static Logger logger = LoggerFactory.getLogger(LeaderLatchListener.class);
	
	private static volatile LeaderElectionAgent leaderElectionAgent = null ;
	
	private List<LeaderChangeListener> list = new LinkedList<LeaderChangeListener>() ;
	private LeaderLatch leaderLatch ;
	private CuratorFramework client ;
	private String info = null;
	
	private boolean isActive = false;
	
	private LeaderElectionAgent() {
		
	}
	
	public static LeaderElectionAgent getInstance(){
		if(null!=leaderElectionAgent){
			return leaderElectionAgent;
		}else{
			synchronized (LeaderElectionAgent.class) {
				if(null==leaderElectionAgent){
					leaderElectionAgent = new LeaderElectionAgent() ;
				}
			}
			return leaderElectionAgent ;
		}
	}
	
	public void start(){
		if(isActive){
			logger.info("monitor is running.");
			return ;
		}
		InputStream is = null;
		try{
			is = LeaderElectionAgent.class.getResourceAsStream("/zkmonitor.properties");
			Properties p=new Properties();
			p.load(is);  
			String connectString = p.getProperty("monitor_connect");
			String namespace = p.getProperty("monitor_namespace");
			String path = p.getProperty("monitor_path");
			info = p.getProperty("monitor_info");
			RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);  
	        client = CuratorFrameworkFactory.builder()
	        		.connectString(connectString)
	        		.sessionTimeoutMs(2000)
	        		.connectionTimeoutMs(10000)
	        		.retryPolicy(retryPolicy)
	        		.namespace(namespace).build(); 
	        client.start();
	        leaderLatch = new LeaderLatch(client,path,info); 
	        leaderLatch.addListener(this);  
	        leaderLatch.start();  
	        leaderLatch.await(3, TimeUnit.SECONDS) ;
	        isActive = true;
		}catch(Exception e){
			logger.error("leader election start.", e);
		}finally{
			if(null!=is){
				try {
					is.close();
				} catch (IOException e) {
					logger.error("zkmonitor.properties inputstream close is error.", e);
				}
			}
		}
	}
	
	public void close(){
		if(!isActive){
			logger.info("monitor is closed.");
			return ;
		}
		try{
			if(null!=leaderLatch){
				leaderLatch.close();
			}
			if(null!=client){
				client.close();
			}
			isActive = false;
		}catch(Exception e){
			logger.error("leader election close.", e);
		}
	}
	
	public void isLeader() {
		synchronized(LeaderElectionAgent.class) {  
		    if (!leaderLatch.hasLeadership()) {  
		      return  ;
		    }  
		    for(LeaderChangeListener listener:list){
		    	listener.update(true);
		    }
		    logger.info(info+" is master.");
		}  
	}

	public void notLeader() {
		synchronized(LeaderElectionAgent.class) {  
		    if (leaderLatch.hasLeadership()) {  
		      return  ;
		    }  
		    for(LeaderChangeListener listener:list){
		    	listener.update(false);
		    }
		    logger.info(info+" is slave.");
		}
	}

	public boolean isMaster() throws Exception  {
		if(!isActive){
			throw new Exception("Leader Agent is not started.");
		}
		return leaderLatch.hasLeadership();
	}
	
	public void addListener(LeaderChangeListener litener) {
		this.list.add(litener) ;
	}

}
