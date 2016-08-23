package com.baidu.stock.material.inform;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.baidu.stock.jms.QuoteMessageListener;
import com.baidu.stock.jms.model.Message;
import com.baidu.stock.jms.model.QuoteTextMessage;
import com.baidu.stock.material.constant.InformConstant;
import com.baidu.stock.material.domain.StockInform;

/**
 * 更新通知的模块可以继承该类 实现业务通知更新
 * @author baidu
 *
 */
public abstract class InformListener implements QuoteMessageListener {

	private final static Logger logger = LoggerFactory.getLogger(InformListener.class);
	
	public void receive(Message message) {
		try{
			if(message instanceof QuoteTextMessage){
				String text = ((QuoteTextMessage)message).getText() ;
				if(StringUtils.isNotBlank(text)){
					StockInform stockInform = JSON.parseObject(text, StockInform.class) ;
					process(stockInform.getInform(),stockInform.getUpdatetime());
				}else{
					logger.info("material data inform update is null.");
				}
			}
		}catch(Exception e){
			logger.error("material data inform update error.", e);
		}
	}

	private void process(String inform,long updateTime) {
		if(InformConstant.CONCEPT.endsWith(inform)){
			doConcept(updateTime) ;
		}else if(InformConstant.DELISTSTOCK.endsWith(inform)){
			doDelistStock(updateTime) ;
		}else if(InformConstant.FINANCE.endsWith(inform)){
			doFinance(updateTime) ;
		}else if(InformConstant.FOREIGN.endsWith(inform)){
			doForeign(updateTime) ;
		}else if(InformConstant.HOLIDAY.endsWith(inform)){
			doHoliday(updateTime) ;
		}else if(InformConstant.INDEXCOMONENT.endsWith(inform)){
			doIndexComonent(updateTime) ;
		}else if(InformConstant.INDUSTRY.endsWith(inform)){
			doIndustry(updateTime) ;
		}else if(InformConstant.IPOSTOCK.endsWith(inform)){
			doIpoStock(updateTime) ;
		}else if(InformConstant.MAFIVEVOLUME.endsWith(inform)){
			doMafiveVolume(updateTime) ;
		}else if(InformConstant.SECONDSTOCK.endsWith(inform)){
			doSecondStock(updateTime) ;
		}else if(InformConstant.STOPSTOCK.endsWith(inform)){
			doStopStock(updateTime) ;
		}else if(InformConstant.TRADEDAY.endsWith(inform)){
			doTradeday(updateTime) ;
		}else if(InformConstant.HGT.endsWith(inform)){
			doHgt(updateTime) ;
		}
		
	}
	
	protected abstract void doConcept(long updateTime) ;
	protected abstract void doDelistStock(long updateTime) ;
	protected abstract void doFinance(long updateTime) ;
	protected abstract void doForeign(long updateTime) ;
	protected abstract void doHoliday(long updateTime) ;
	protected abstract void doIndexComonent(long updateTime) ;
	protected abstract void doIndustry(long updateTime) ;
	protected abstract void doIpoStock(long updateTime) ;
	protected abstract void doMafiveVolume(long updateTime) ;
	protected abstract void doSecondStock(long updateTime) ;
	protected abstract void doStopStock(long updateTime) ;
	protected abstract void doTradeday(long updateTime) ;
	protected abstract void doHgt(long updateTime) ;
}
