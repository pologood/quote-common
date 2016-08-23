package com.baidu.stock.jms.model;

public class QuoteTextMessage extends Message {

	private String text;
	
	public QuoteTextMessage( String text) {
		super();
		this.text = text;
	}
	
	public QuoteTextMessage(String type, String text) {
		super();
		this.type = type;
		this.text = text;
	}
	
	public QuoteTextMessage(String groupId, String type, String text) {
		super();
		this.groupId = groupId;
		this.type = type;
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	
}
