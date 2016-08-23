package com.baidu.stock.jms.model;

public class QuoteBytesMessage extends Message {
	
	private byte[] message ;
	
	public QuoteBytesMessage( byte[] message) {
		super();
		this.message = message;
	}
	
	public QuoteBytesMessage(String type, byte[] message) {
		super();
		this.type = type;
		this.message = message;
	}
	
	public QuoteBytesMessage(String groupId, String type, byte[] message) {
		super();
		this.groupId = groupId;
		this.type = type;
		this.message = message;
	}
	public byte[] getMessage() {
		return message;
	}
	public void setMessage(byte[] message) {
		this.message = message;
	}
	
	
}
