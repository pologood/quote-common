package com.baidu.stock.material.service;

import java.util.Set;

import com.baidu.stock.material.exception.ServiceException;

public interface IHolidayFacade {

	/**
	 * 获取节假日信息
	 * @return
	 */
	public Set<String> getHolidays(Long updateTime)  throws ServiceException ;
	
	/**
	 * 获取交易日信息
	 * @return
	 */
	public Set<String> getTradedays(Long updateTime)  throws ServiceException ;
}
