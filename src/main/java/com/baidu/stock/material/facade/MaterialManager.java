package com.baidu.stock.material.facade;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baidu.stock.log.LogConstant;
import com.baidu.stock.log.LogUtils;
import com.baidu.stock.material.domain.IndexComponent;
import com.baidu.stock.material.domain.StockConcept;
import com.baidu.stock.material.domain.StockExchangeRate;
import com.baidu.stock.material.domain.StockFinance;
import com.baidu.stock.material.domain.StockIndustry;
import com.baidu.stock.material.domain.StockPlate;
import com.baidu.stock.material.domain.StockStopInfo;
import com.baidu.stock.material.service.IAFinanceFacade;
import com.baidu.stock.material.service.IHolidayFacade;
import com.caucho.hessian.client.HessianProxyFactory;

/**
 * 获取基本数据
 * 
 * 第一种方式
 * 该类封装了访问获取基础数据的方法
 * 基础数据的获取方式采用主备的方式，目前通过urls获取主备的url，创建相应的代理类，
 * 来提供服务，提供简单的重试和failover
 * 
 * 第二种方式，url通过配置成BNS的方式，这样只提供一个url，通过BNS提供软负载
 * 
 * 第三种方式，url通过注册中心注册，代理类通过注册中心寻找url服务
 * 
 * @author baidu
 *
 */
public class MaterialManager {
	
	private final static Logger logger = LoggerFactory.getLogger(MaterialManager.class);
	
	private String[] urls = null ;
	private static String SEMICOLON = ";" ;
	
	@SuppressWarnings("all")
	private Map<String,Map<Class,Object>> serviceMap = new HashMap<String,Map<Class,Object>>();
	private HessianProxyFactory factory = new HessianProxyFactory();
	private int tryTimes = 3;
	
	/**
	 * urls
	 * @param urls
	 */
	@SuppressWarnings("all")
	public  MaterialManager(String urls) {
		if(StringUtils.isBlank(urls)){
			logger.error(LogUtils.getLogInfo(LogConstant.MATERIAL_MODULE,LogConstant.DEFAULT_ERROR_KEY
					, LogConstant.DEFAULT_ERROR_VALUE, "urls is not null."));
		} else {
			factory.setConnectTimeout(10000);
			factory.setReadTimeout(120000);
			this.urls = urls.split(SEMICOLON);
			Map<Class,Object>  classMap = null;
			IHolidayFacade hoidayService = null;
			IAFinanceFacade afinanceFacade =null;
			String tmpUrl = null;
			for(String url:this.urls){
				try{
					tmpUrl = url;
					if(!tmpUrl.endsWith("/")){
						tmpUrl += "/";
					}
					hoidayService = (IHolidayFacade) factory.create(IHolidayFacade.class,tmpUrl+IHolidayFacade.class.getSimpleName());
					afinanceFacade = (IAFinanceFacade) factory.create(IAFinanceFacade.class,tmpUrl+IAFinanceFacade.class.getSimpleName());
					classMap = serviceMap.get(url);
					if(null == classMap){
						classMap = new HashMap<Class,Object>();
						serviceMap.put(url, classMap) ;
					}
					classMap.put(IHolidayFacade.class, hoidayService) ;
					classMap.put(IAFinanceFacade.class, afinanceFacade) ;
				}catch(Exception e){
					logger.error(LogUtils.getLogInfo(LogConstant.MATERIAL_MODULE,LogConstant.DEFAULT_ERROR_KEY
							, LogConstant.DEFAULT_ERROR_VALUE, "实例化代理类发生异常，service url :"+url));
				}
			}
		}
	}
	
	/**
	 * 获取最近一年的节假日
	 * String  yyyyMMdd
	 * @return
	 */
	@SuppressWarnings("all")
	public Set<String> getHolidays(Long updateTime) {
		IHolidayFacade hoidayService = null;
		for(String url:urls){
			Map<Class,Object> map = serviceMap.get(url) ;
			for(int i=0;i<this.tryTimes;i++){
				try{
					hoidayService = (IHolidayFacade)map.get(IHolidayFacade.class);
					return hoidayService.getHolidays(updateTime);
				}catch(Exception e){
					logger.error(LogUtils.getLogInfo(LogConstant.MATERIAL_MODULE,LogConstant.DEFAULT_ERROR_KEY
							, LogConstant.DEFAULT_ERROR_VALUE, "获取节假日异常，service url :"+url),e);
				}
			}
		}
		return null;
	}
	
	/**
	 * 获取交易日信息
	 * String  yyyyMMdd
	 * @return
	 */
	@SuppressWarnings("all")
	public Set<String> getTradedays(Long updateTime)  {
		IHolidayFacade hoidayService = null;
		for(String url:urls){
			Map<Class,Object> map = serviceMap.get(url) ;
			for(int i=0;i<this.tryTimes;i++){
				try{
					hoidayService = (IHolidayFacade)map.get(IHolidayFacade.class);
					return hoidayService.getTradedays(updateTime);
				}catch(Exception e){
					logger.error(LogUtils.getLogInfo(LogConstant.MATERIAL_MODULE,LogConstant.DEFAULT_ERROR_KEY
							, LogConstant.DEFAULT_ERROR_VALUE, "获取交易日异常，service url :"+url),e);
				}
			}
		}
		return null;
	}
	
	/**
	 * 获取指数的成份股
	 * key 市场_指数编码  sh_000001
	 * @return
	 */
	@SuppressWarnings("all")
	public Map<String,IndexComponent> getIndexComponents(Long updateTime) {
		IAFinanceFacade afinanceFacade = null;
		for(String url:urls){
			Map<Class,Object> map = serviceMap.get(url) ;
			for(int i=0;i<this.tryTimes;i++){
				try{
					afinanceFacade = (IAFinanceFacade)map.get(IAFinanceFacade.class);
					return afinanceFacade.getIndexComponents(updateTime);
				}catch(Exception e){
					logger.error(LogUtils.getLogInfo(LogConstant.MATERIAL_MODULE,LogConstant.DEFAULT_ERROR_KEY
							, LogConstant.DEFAULT_ERROR_VALUE, "获取指数成份股异常，service url :"+url),e);
				}
			}
		}
		return null;
	}
	
	/**
	 * 获取概念股
	 * key 概念编码
	 * @return
	 */
	@SuppressWarnings("all")
	public Map<String,StockConcept> getStockConcepts(Long updateTime) {
		IAFinanceFacade afinanceFacade = null;
		for(String url:urls){
			Map<Class,Object> map = serviceMap.get(url) ;
			for(int i=0;i<this.tryTimes;i++){
				try{
					afinanceFacade = (IAFinanceFacade)map.get(IAFinanceFacade.class);
					return afinanceFacade.getStockConcept(updateTime);
				}catch(Exception e){
					logger.error(LogUtils.getLogInfo(LogConstant.MATERIAL_MODULE,LogConstant.DEFAULT_ERROR_KEY
							, LogConstant.DEFAULT_ERROR_VALUE, "获取概念分类异常，service url :"+url),e);
				}
			}
		}
		return null;
	}
	
	/**
	 * 获取股票的财务信息
	 * key 市场_股票编码 sh_600000
	 * @return
	 */
	@SuppressWarnings("all")
	public Map<String,StockFinance> getStockFinances(Long updateTime) {
		IAFinanceFacade afinanceFacade = null;
		for(String url:urls){
			Map<Class,Object> map = serviceMap.get(url) ;
			for(int i=0;i<this.tryTimes;i++){
				try{
					afinanceFacade = (IAFinanceFacade)map.get(IAFinanceFacade.class);
					return afinanceFacade.getStockFinances(updateTime);
				}catch(Exception e){
					logger.error(LogUtils.getLogInfo(LogConstant.MATERIAL_MODULE,LogConstant.DEFAULT_ERROR_KEY
							, LogConstant.DEFAULT_ERROR_VALUE, "获取财务信息异常，service url :"+url),e);
				}
			}
		}
		return null;
	}
	
	/**
	 * 获取次新股列表
	 * String 市场_股票编码 sh_600000
	 * @return
	 */
	@SuppressWarnings("all")
	public Set<String> getSecondStocks(Long updateTime) {
		IAFinanceFacade afinanceFacade = null;
		for(String url:urls){
			Map<Class,Object> map = serviceMap.get(url) ;
			for(int i=0;i<this.tryTimes;i++){
				try{
					afinanceFacade = (IAFinanceFacade)map.get(IAFinanceFacade.class);
					return afinanceFacade.getSecondStocks(updateTime);
				}catch(Exception e){
					logger.error(LogUtils.getLogInfo(LogConstant.MATERIAL_MODULE,LogConstant.DEFAULT_ERROR_KEY
							, LogConstant.DEFAULT_ERROR_VALUE, "获取次新股异常，service url :"+url),e);
				}
			}
		}
		return null;
	}

	/**
	 * 获取行业分类
	 * key 为行业编码
	 * @return
	 */
	@SuppressWarnings("all")
	public Map<String,StockIndustry> getStockIndustry(Long updateTime) {
		IAFinanceFacade afinanceFacade = null;
		for(String url:urls){
			Map<Class,Object> map = serviceMap.get(url) ;
			for(int i=0;i<this.tryTimes;i++){
				try{
					afinanceFacade = (IAFinanceFacade)map.get(IAFinanceFacade.class);
					return afinanceFacade.getStockIndustry(updateTime);
				}catch(Exception e){
					logger.error(LogUtils.getLogInfo(LogConstant.MATERIAL_MODULE,LogConstant.DEFAULT_ERROR_KEY
							, LogConstant.DEFAULT_ERROR_VALUE, "获取行业分类异常，service url :"+url),e);
				}
			}
		}
		return null;
	}
	
	/**
	 * 获取板块分类
	 * @return
	 */
	@SuppressWarnings("all")
	public Map<String,StockPlate> getStockPlate(Long updateTime) {
		return null;
	}
	
	
	/**
	 * 获取停牌股票的信息
	 * String 市场_股票编码 sh_600000
	 * @return
	 */
	@SuppressWarnings("all")
	public List<StockStopInfo> getStockStopInfos(Long updateTime)  {
		IAFinanceFacade afinanceFacade = null;
		for(String url:urls){
			Map<Class,Object> map = serviceMap.get(url) ;
			for(int i=0;i<this.tryTimes;i++){
				try{
					afinanceFacade = (IAFinanceFacade)map.get(IAFinanceFacade.class);
					return afinanceFacade.getStockStopInfos(updateTime);
				}catch(Exception e){
					logger.error(LogUtils.getLogInfo(LogConstant.MATERIAL_MODULE,LogConstant.DEFAULT_ERROR_KEY
							, LogConstant.DEFAULT_ERROR_VALUE, "获取停牌股票异常，service url :"+url),e);
				}
			}
		}
		return null;
	}
	
	/**
	 * 获取IPO股票的信息
	 * String 市场_股票编码 sh_600000
	 * @return
	 */
	@SuppressWarnings("all")
	public Set<String> getIpoStock(Long updateTime)  {
		IAFinanceFacade afinanceFacade = null;
		for(String url:urls){
			Map<Class,Object> map = serviceMap.get(url) ;
			for(int i=0;i<this.tryTimes;i++){
				try{
					afinanceFacade = (IAFinanceFacade)map.get(IAFinanceFacade.class);
					return afinanceFacade.getIpoStock(updateTime);
				}catch(Exception e){
					logger.error(LogUtils.getLogInfo(LogConstant.MATERIAL_MODULE,LogConstant.DEFAULT_ERROR_KEY
							, LogConstant.DEFAULT_ERROR_VALUE, "获取IPO股票异常，service url :"+url),e);
				}
			}
		}
		return null;
	}
	
	/**
	 * 获取退市股票的信息
	 * String 市场_股票编码 sh_600000
	 * @return
	 */
	@SuppressWarnings("all")
	public Set<String> getDelistStock(Long updateTime)  {
		IAFinanceFacade afinanceFacade = null;
		for(String url:urls){
			Map<Class,Object> map = serviceMap.get(url) ;
			for(int i=0;i<this.tryTimes;i++){
				try{
					afinanceFacade = (IAFinanceFacade)map.get(IAFinanceFacade.class);
					return afinanceFacade.getDelistStock(updateTime);
				}catch(Exception e){
					logger.error(LogUtils.getLogInfo(LogConstant.MATERIAL_MODULE,LogConstant.DEFAULT_ERROR_KEY
							, LogConstant.DEFAULT_ERROR_VALUE, "获取退市股票异常，service url :"+url),e);
				}
			}
		}
		return null;
	}
	
	/**
	 * 获取汇率
	 * key    币种
	 * value  汇率值
	 * @return
	 */
	@SuppressWarnings("all")
	public StockExchangeRate getForeignExchange(Long updateTime) {
		IAFinanceFacade afinanceFacade = null;
		for(String url:urls){
			Map<Class,Object> map = serviceMap.get(url) ;
			for(int i=0;i<this.tryTimes;i++){
				try{
					afinanceFacade = (IAFinanceFacade)map.get(IAFinanceFacade.class);
					return afinanceFacade.getForeignExchange(updateTime);
				}catch(Exception e){
					logger.error(LogUtils.getLogInfo(LogConstant.MATERIAL_MODULE,LogConstant.DEFAULT_ERROR_KEY
							, LogConstant.DEFAULT_ERROR_VALUE, "获取汇率异常，service url :"+url),e);
				}
			}
		}
		return null;
	}
	
	/**
	 * 获取5日均量
	 * key sh_600000 市场 + _ + 股票编码
	 * @return
	 */
	@SuppressWarnings("all")
	public Map<String, Long>  getStockMaFiveVolume(Long updateTime) {
		IAFinanceFacade afinanceFacade = null;
		for(String url:urls){
			Map<Class,Object> map = serviceMap.get(url) ;
			for(int i=0;i<this.tryTimes;i++){
				try{
					afinanceFacade = (IAFinanceFacade)map.get(IAFinanceFacade.class);
					return afinanceFacade.getStockMaFiveVolume(updateTime);
				}catch(Exception e){
					logger.error(LogUtils.getLogInfo(LogConstant.MATERIAL_MODULE,LogConstant.DEFAULT_ERROR_KEY
							, LogConstant.DEFAULT_ERROR_VALUE, "获取5日均量异常，service url :"+url),e);
				}
			}
		}
		return null;
	}
	
	/**
	 * 获取沪股通信息
	 * String 市场_股票编码 sh_600000
	 * @return
	 */
	@SuppressWarnings("all")
	public Set<String> getHgtStock(Long updateTime)  {
		IAFinanceFacade afinanceFacade = null;
		for(String url:urls){
			Map<Class,Object> map = serviceMap.get(url) ;
			for(int i=0;i<this.tryTimes;i++){
				try{
					afinanceFacade = (IAFinanceFacade)map.get(IAFinanceFacade.class);
					return afinanceFacade.getHgtStocks(updateTime);
				}catch(Exception e){
					logger.error(LogUtils.getLogInfo(LogConstant.MATERIAL_MODULE,LogConstant.DEFAULT_ERROR_KEY
							, LogConstant.DEFAULT_ERROR_VALUE, "获取沪股通异常，service url :"+url),e);
				}
			}
		}
		return null;
	}
	
	public static void main(String[] args) {
		System.out.println(IAFinanceFacade.class.getSimpleName());
	}
}
