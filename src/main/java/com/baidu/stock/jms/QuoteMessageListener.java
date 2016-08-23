package com.baidu.stock.jms;

import com.baidu.stock.jms.model.Message;

public interface QuoteMessageListener {

	public void receive(Message message) ;
}
