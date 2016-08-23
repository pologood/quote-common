package com.baidu.stock.jms;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.jms.BytesMessage;
import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baidu.stock.jms.model.QuoteBytesMessage;

public class NativeProducer {

	private final static Logger logger = LoggerFactory.getLogger(NativeProducer.class);

	private String user = null;             
    private String password = null;            
    private String url = null;      
    private String subject = null;     
    private Destination destination = null;            
    private Connection connection = null;            
    private Session session = null;             
    private MessageProducer producer = null;   
    private boolean topic;   
    private boolean transacted;   
    private boolean persistent;
    private long timeToLive;
    
    public NativeProducer(){
    	
    }
    
    
    /**
	 * 默认配置文件的名称为message.properties
	 */
	public void init() throws Exception {
		init("message.properties","message_subject");
	}
	/**
	 * 默认配置文件的名称为message.properties
	 * @param fileName message配置文件的名称
	 * @param destination queue or topic name
	 * @throws Exception 
	 */
	public void init(String fileName,String destination) throws Exception{
		InputStream is = null;
		try{
			is = Consumer.class.getResourceAsStream("/"+fileName);
			Properties p=new Properties();
			p.load(is);  
			url = p.getProperty("message_url");
			user = p.getProperty("message_user");
			password = p.getProperty("message_password"); 
			topic = Boolean.parseBoolean(p.getProperty("message_is_topic"));
			transacted = Boolean.parseBoolean(p.getProperty("message_transacted"));
			persistent = Boolean.parseBoolean(p.getProperty("message_persistent"));
			timeToLive = Long.parseLong(p.getProperty("message_time_to_live"));
			if(StringUtils.isBlank(destination)){
				subject = p.getProperty("message_subject");
			}else{
				subject = p.getProperty(destination);
			}
		}catch(Exception e){
			logger.error("NativeProducer init.", e);
			throw e;
		}finally{
			if(null!=is){
				try {
					is.close();
				} catch (IOException e) {
					logger.error("message.properties inputstream close is error.", e);
				}
			}
		}
	}
    
    public void start() throws JMSException, Exception {       
    	ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(user, password, url);  
    	connectionFactory.setCopyMessageOnSend(false);
        connection = connectionFactory.createConnection();     
        session = connection.createSession(transacted, Session.AUTO_ACKNOWLEDGE);       
        if(topic){
        	destination = session.createTopic(subject);
        }else{
        	destination = session.createQueue(subject);
        }
        producer = session.createProducer(destination); 
       
        if(persistent){
        	producer.setDeliveryMode(DeliveryMode.PERSISTENT);
        }else{
        	producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);  
        }
        producer.setTimeToLive(timeToLive);
        connection.start();
        logger.info("NativeProducer start.");
    }       
     
    /**
	 * 发送消息
	 * @param groupId 分组   sh_600000  市场_股票编码
	 * @param type    消息类型
	 * @param message 发送的消息
     * @throws JMSException 
	 */
	public void send(QuoteBytesMessage quoteMessage) throws JMSException{
		BytesMessage bytesMessage = session.createBytesMessage() ;
		bytesMessage.setStringProperty(QuoteBytesMessage.GROUP, quoteMessage.getGroupId());
		bytesMessage.setJMSType(quoteMessage.getType());
		bytesMessage.writeBytes(quoteMessage.getMessage());
		producer.send(bytesMessage);       
        if(transacted){
        	session.commit();
        }
	}
	
	/**
	 * 发送消息
	 * @param groupId 分组   sh_600000  市场_股票编码
	 * @param type    消息类型
	 * @param message 发送的消息
	 * @throws JMSException  
	 */
	public void send(final String groupId,final String type ,final byte[] message) throws JMSException {
		BytesMessage bytesMessage = session.createBytesMessage() ;
		bytesMessage.setStringProperty(QuoteBytesMessage.GROUP, groupId);
		bytesMessage.setJMSType(type);
		bytesMessage.writeBytes(message);
		producer.send(bytesMessage);       
        if(transacted){
        	session.commit();
        }
	}
	
	/**
	 * 发送消息
	 * @param type    消息类型
	 * @param message 发送的消息
	 * @throws JMSException 
	 */
	public void send(final String type ,final byte[] message) throws JMSException{
		BytesMessage bytesMessage = session.createBytesMessage() ;
		bytesMessage.setJMSType(type);
		bytesMessage.writeBytes(message);
		producer.send(bytesMessage);       
        if(transacted){
        	session.commit();
        }
	}
	
	/**
	 * 发送消息
	 * @param message 发送的消息
	 * @throws JMSException 
	 */
	public void send(final byte[] message) throws JMSException{
		BytesMessage bytesMessage = session.createBytesMessage() ;
		bytesMessage.writeBytes(message);
		producer.send(bytesMessage);       
        if(transacted){
        	session.commit();
        }
	}
	
	/**
	 * 发送消息
	 * @param groupId 分组   sh_600000  市场_股票编码
	 * @param type    消息类型
	 * @param message 发送的消息
	 * @throws JMSException 
	 */
	public void send(final String groupId,final String type ,final String message) throws JMSException{
		TextMessage textMessage = session.createTextMessage(message);
		textMessage.setStringProperty(QuoteBytesMessage.GROUP, groupId);
		textMessage.setJMSType(type);
		producer.send(textMessage);       
        if(transacted){
        	session.commit();
        }
	}
	
	/**
	 * 发送消息
	 * @param type    消息类型
	 * @param message 发送的消息
	 * @throws JMSException 
	 */
	public void send(final String type ,final String message) throws JMSException{
		TextMessage textMessage = session.createTextMessage(message);
		textMessage.setJMSType(type);
		producer.send(textMessage);       
        if(transacted){
        	session.commit();
        }
	}
	
	/**
	 * 发送消息
	 * @param message 发送的消息
	 * @throws JMSException 
	 */
	public void send(final String message) throws JMSException{
		TextMessage textMessage = session.createTextMessage(message);
		producer.send(textMessage);       
        if(transacted){
        	session.commit();
        }
	}
    
    public void close() {  
    	try{
    		if (producer != null) {       
                producer.close();  
    		}
            if (session != null)  {
            	 session.close();  
            }
            if (connection != null){
            	connection.close();   
            }
            logger.info("NativeProducer close.");
    	}catch(Exception e){
    		logger.error("NativeProducer close error.",e);
    	}
    }


	public String getUser() {
		return user;
	}


	public void setUser(String user) {
		this.user = user;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getUrl() {
		return url;
	}


	public void setUrl(String url) {
		this.url = url;
	}


	public String getSubject() {
		return subject;
	}


	public void setSubject(String subject) {
		this.subject = subject;
	}


	public Destination getDestination() {
		return destination;
	}


	public void setDestination(Destination destination) {
		this.destination = destination;
	}


	public Session getSession() {
		return session;
	}


	public void setSession(Session session) {
		this.session = session;
	}


	public MessageProducer getProducer() {
		return producer;
	}


	public void setProducer(MessageProducer producer) {
		this.producer = producer;
	}


	public boolean isTopic() {
		return topic;
	}


	public void setTopic(boolean topic) {
		this.topic = topic;
	}


	public boolean isTransacted() {
		return transacted;
	}


	public void setTransacted(boolean transacted) {
		this.transacted = transacted;
	}


	public boolean isPersistent() {
		return persistent;
	}


	public void setPersistent(boolean persistent) {
		this.persistent = persistent;
	}


	public long getTimeToLive() {
		return timeToLive;
	}


	public void setTimeToLive(long timeToLive) {
		this.timeToLive = timeToLive;
	}    
    
    
    //setter
    
}
