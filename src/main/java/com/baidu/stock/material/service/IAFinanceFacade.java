package com.baidu.stock.material.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.baidu.stock.material.domain.IndexComponent;
import com.baidu.stock.material.domain.StockConcept;
import com.baidu.stock.material.domain.StockExchangeRate;
import com.baidu.stock.material.domain.StockFinance;
import com.baidu.stock.material.domain.StockIndustry;
import com.baidu.stock.material.domain.StockStopInfo;
import com.baidu.stock.material.exception.ServiceException;

public interface IAFinanceFacade {
	
	/**
	 * 获取沪股通列表
	 * @param updateTime
	 * @return
	 * @throws ServiceException
	 */
	public Set<String> getHgtStocks(Long updateTime) throws ServiceException;
	
	/**
	 * 获取行业分类
	 * 首先从文件获取  如果文件没有从mysql获取 
	 * 如果文件不是最新的 则从mysql获取
	 * updateTime yyyyMMddhhmmss
	 * @return
	 * @throws ServiceException
	 */
	public Map<String,StockIndustry> getStockIndustry(Long updateTime) throws ServiceException ;
	
	/**
	 * 获取基本财务数据
	 * @param updateTime
	 * @return
	 * @throws ServiceException
	 */
	public Map<String,StockFinance> getStockFinances(Long updateTime) throws ServiceException ;
	
	
	/**
	 * 获取概念分类
	 * @param updateTime
	 * @return
	 * @throws ServiceException
	 */
	public Map<String,StockConcept> getStockConcept(Long updateTime) throws ServiceException ;
	
	/**
	 * 获取次新股
	 * @return
	 */
	public Set<String> getSecondStocks(Long updateTime)  throws ServiceException ;
	
	
	/**
	 * 获取停牌股票的信息
	 * @return
	 */
	public List<StockStopInfo> getStockStopInfos(Long updateTime)  throws ServiceException ;
	
	/**
	 * 获取IPO股票的信息
	 * @return
	 */
	public Set<String> getIpoStock(Long updateTime)  throws ServiceException ;
	
	/**
	 * 获取退市股票的信息
	 * @return
	 */
	public Set<String> getDelistStock(Long updateTime)  throws ServiceException ;
	
	/**
	 * 获取汇率信息
	 * @return
	 */
	public StockExchangeRate getForeignExchange(Long updateTime) throws ServiceException ;
	
	/**
	 * 获取指数成分股
	 * @return
	 * @throws ServiceException
	 */
	public Map<String,IndexComponent> getIndexComponents(Long updateTime) throws ServiceException  ;
	
	/**
	 * 获取5日均量
	 * @param updateTime
	 * @return
	 * @throws ServiceException
	 */
	public Map<String, Long> getStockMaFiveVolume(Long updateTime) throws ServiceException ;
}
