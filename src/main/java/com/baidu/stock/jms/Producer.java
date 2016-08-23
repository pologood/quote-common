package com.baidu.stock.jms;

import javax.jms.BytesMessage;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MarshallingMessageConverter;

import com.baidu.stock.jms.model.QuoteBytesMessage;

public class Producer {

	private JmsTemplate jmsTemplate ;
	
	/**
	 * 发送消息
	 * @param groupId 分组   sh_600000  市场_股票编码
	 * @param type    消息类型
	 * @param message 发送的消息
	 */
	public void send(QuoteBytesMessage quoteMessage){
		final String groupId = quoteMessage.getGroupId() ;
		final String type = quoteMessage.getType() ;
		final byte[] message = quoteMessage.getMessage() ;
		jmsTemplate.send(new MessageCreator() {
			public Message createMessage(Session session) throws JMSException {
				BytesMessage bytesMessage = session.createBytesMessage() ;
				bytesMessage.setStringProperty(QuoteBytesMessage.GROUP, groupId);
				bytesMessage.setJMSType(type);
				bytesMessage.writeBytes(message);
                return bytesMessage;
			}
        });
	}
	
	
	/**
	 * 发送消息
	 * @param groupId 分组   sh_600000  市场_股票编码
	 * @param type    消息类型
	 * @param message 发送的消息
	 */
	public void send(final String groupId,final String type ,final byte[] message){
		jmsTemplate.send(new MessageCreator() {
			public Message createMessage(Session session) throws JMSException {
				BytesMessage bytesMessage = session.createBytesMessage() ;
				bytesMessage.setStringProperty(QuoteBytesMessage.GROUP, groupId);
				bytesMessage.setJMSType(type);
				bytesMessage.writeBytes(message);
                return bytesMessage;
			}
        });
	}
	
	
	/**
	 * 发送消息
	 * @param type    消息类型
	 * @param message 发送的消息
	 */
	public void send(final String type ,final byte[] message){
		jmsTemplate.send(new MessageCreator() {
			public Message createMessage(Session session) throws JMSException {
				BytesMessage bytesMessage = session.createBytesMessage() ;
				bytesMessage.setJMSType(type);
				bytesMessage.writeBytes(message);
                return bytesMessage;
			}
        });
	}
	
	/**
	 * 发送消息
	 * @param message 发送的消息
	 */
	public void send(final byte[] message){
		jmsTemplate.send(new MessageCreator() {
			public Message createMessage(Session session) throws JMSException {
				BytesMessage bytesMessage = session.createBytesMessage() ;
				bytesMessage.writeBytes(message);
                return bytesMessage;
			}
        });
	}
	
	/**
	 * 发送消息
	 * @param groupId 分组   sh_600000  市场_股票编码
	 * @param type    消息类型
	 * @param message 发送的消息
	 */
	public void send(final String groupId,final String type ,final String message){
		jmsTemplate.send(new MessageCreator() {
			public Message createMessage(Session session) throws JMSException {
				TextMessage textMessage = session.createTextMessage(message);
                return textMessage;
			}
        });
	}
	
	/**
	 * 发送消息
	 * @param type    消息类型
	 * @param message 发送的消息
	 */
	public void send(final String type ,final String message){
		jmsTemplate.send(new MessageCreator() {
			public Message createMessage(Session session) throws JMSException {
				TextMessage textMessage = session.createTextMessage(message);
                return textMessage;
			}
        });
	}
	
	/**
	 * 发送消息
	 * @param message 发送的消息
	 */
	public void send(final String message){
		jmsTemplate.send(new MessageCreator() {
			public Message createMessage(Session session) throws JMSException {
				TextMessage textMessage = session.createTextMessage(message);
                return textMessage;
			}
        });
	}
	
	
	/**
	 * 发送对象,默认采用jackson序列化
	 * @param obj
	 */
	public void send(final Object obj){
		jmsTemplate.setMessageConverter(new MappingJackson2MessageConverter());
		jmsTemplate.send(new MessageCreator() {
			public Message createMessage(Session session) throws JMSException {
				Message message=jmsTemplate.getMessageConverter().toMessage(obj, session);
				message.setStringProperty("messageTypeID","HQMessageId");
                return message;
			}
        });
	}
	

	public JmsTemplate getJmsTemplate() {
		return jmsTemplate;
	}

	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}
	
	
}
