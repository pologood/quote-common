package com.baidu.stock.jms;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import javax.jms.BytesMessage;
import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baidu.stock.jms.model.QuoteBytesMessage;
import com.baidu.stock.jms.model.QuoteTextMessage;

public class Consumer implements MessageListener {
	
	private final static Logger logger = LoggerFactory.getLogger(Consumer.class);

	private List<QuoteMessageListener> list = new LinkedList<QuoteMessageListener>();
	
	private String user = null;
	private String password = null;
	private String url = null;
	private String subject = null;
	private Destination destination = null;
	private Connection connection = null;
	private Session session = null;
	private MessageConsumer consumer = null;
	private boolean topic;
	private boolean transacted;
	private boolean persistent;
	private String clientid;
	private String clientName = "";
	private boolean isActive = false;
	private boolean isStart=false;
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
			if(StringUtils.isBlank(destination)){
				subject = p.getProperty("message_subject");
			}else{
				subject = p.getProperty(destination);
			}
			clientid = p.getProperty("message_client_id");
			clientName = p.getProperty("message_client_name");
		}catch(Exception e){
			logger.error("consumer init.", e);
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
	
	public void start()  throws Exception {
		if(isActive){
			throw new Exception("consumer is already active") ;
		}
		try{
			logger.info("准备构造连接工厂,username:"+user+ " password:"+password+" brokerUrl:"+url);
			ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
					user, password, url);
			connection = connectionFactory.createConnection();
			if (topic){
				connection.setClientID(clientid);
				session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE); 
				destination = session.createTopic(subject);
			}else{
				session = connection.createSession(transacted, Session.AUTO_ACKNOWLEDGE); 
				destination = session.createQueue(subject);
			}
			if (persistent && topic) {
				consumer = session.createDurableSubscriber(
						(javax.jms.Topic) destination, clientName);
			} else {
				consumer = session.createConsumer(destination);
			}
			consumer.setMessageListener(this);
			connection.start();
			isActive = true;
			logger.info("启动consumer成功.");
		}catch(Exception e){
			logger.error("consumer start.", e);
			throw e;
		}
	}
	
	
	public void startByPoll()  throws Exception {
		if(!isStart()){
			return;
		}
		try{
			logger.info("准备构造连接工厂,username:"+user+ " password:"+password+" brokerUrl:"+url);
			ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
					user, password, url);
			connection = connectionFactory.createConnection();
			if (topic){
				connection.setClientID(clientid);
				session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE); 
				destination = session.createTopic(subject);
			}else{
				session = connection.createSession(transacted, Session.AUTO_ACKNOWLEDGE); 
				destination = session.createQueue(subject);
			}
			if (persistent && topic) {
				consumer = session.createDurableSubscriber(
						(javax.jms.Topic) destination, clientName);
			} else {
				consumer = session.createConsumer(destination);
			}
			connection.start();
			logger.info("启动consumer成功.");
		}catch(Exception e){
			logger.error("consumer start.", e);
			throw e;
		}
	}
	
	public void close()  throws Exception {
		if(null != consumer){
			consumer.close();
		}
		if(null != session){
			session.close();
		}
		if(null != connection){
			connection.close();
		}
		isActive = false;
	}
	
	public void onMessage(Message message) {
		if (message instanceof BytesMessage) {
			try{
				BytesMessage bytesMessage = (BytesMessage) message;
				byte[] bytes = new byte[(int) bytesMessage.getBodyLength()];
				bytesMessage.readBytes(bytes);
				bytesMessage.clearBody();
				QuoteBytesMessage quoteMessage = new 
						QuoteBytesMessage(bytesMessage.getStringProperty(QuoteBytesMessage.GROUP),bytesMessage.getJMSType(),bytes) ;
				for(QuoteMessageListener listener : list){
					listener.receive(quoteMessage);
				}
			}catch(Exception e){
				logger.error("receive bytes message error.",e);
			}
		}else if (message instanceof TextMessage) {
			try{
				TextMessage textMessage = (TextMessage) message;
				QuoteTextMessage quoteMessage = new 
						QuoteTextMessage(textMessage.getStringProperty(QuoteBytesMessage.GROUP),textMessage.getJMSType(),textMessage.getText()) ;
				for(QuoteMessageListener listener : list){
					listener.receive(quoteMessage);
				}
			}catch(Exception e){
				logger.error("receive text message error.",e);
			}
		}
	}
	
	public void addMessageListener(QuoteMessageListener quoteMessageListener){
		this.list.add(quoteMessageListener) ;
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
	public Connection getConnection() {
		return connection;
	}
	public void setConnection(Connection connection) {
		this.connection = connection;
	}
	public Session getSession() {
		return session;
	}
	public void setSession(Session session) {
		this.session = session;
	}
	public MessageConsumer getConsumer() {
		return consumer;
	}
	public void setConsumer(MessageConsumer consumer) {
		this.consumer = consumer;
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
	public String getClientid() {
		return clientid;
	}
	public void setClientid(String clientid) {
		this.clientid = clientid;
	}
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public boolean isStart() {
		return isStart;
	}
	public void setStart(boolean isStart) {
		this.isStart = isStart;
	}
	
}
