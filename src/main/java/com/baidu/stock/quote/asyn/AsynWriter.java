package com.baidu.stock.quote.asyn;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.channel.ExecutorChannel;
import com.baidu.stock.quote.asyn.schedule.ConsumeScheduleImpl;
import com.baidu.stock.quote.asyn.schedule.IConsumeSchedule;


/**
 * 异步输出数据接口默认实现类
 * @author dengjianli
 *
 */
public class AsynWriter implements IAsynWriter{
	private static final Logger Logger = LoggerFactory.getLogger(AsynWriter.class);
	
	/**
	 * 数据队列
	 */
	private LinkedBlockingQueue<Object> dataQueue;	
	
	/**
	 * 创建ConsumeSchedule的服务执行器，根据配置决定创建多少个Consumer，
	 * 由Consumer来检测dataQueue获取数据
	 */
	private ExecutorService createConsumerService;
	
	/**
	 * 内部创建的Consumer
	 */
	private IConsumeSchedule[] consumers;
	
	/**
	 * 消费线程控制句柄
	 */
	private Future<?>[] consumersHandler;
	
	/**
	 * Writer的名称
	 */
	private String name;
	
	public AsynWriter(DirectChannel directChannel,ExecutorChannel executorChannel,int index){
		dataQueue = new LinkedBlockingQueue<Object>(AsynConfig.getInstance().getQueueBuffSize());
		createConsumerService = Executors.newFixedThreadPool(AsynConfig.getInstance().getConsumerCount());
		createConsumer(directChannel,executorChannel,index);
	}
	
	public void createConsumer(DirectChannel directChannel,ExecutorChannel executorChannel,int index){
		int consumerCount =AsynConfig.getInstance().getConsumerCount();
		consumers = new IConsumeSchedule[consumerCount];
		consumersHandler = new Future[consumerCount];
		for(int i = 0 ; i < consumerCount; i++){
			consumers[i] =new ConsumeScheduleImpl(directChannel,executorChannel);
			consumers[i].setName(String.valueOf(index));
			consumers[i].setQueue(dataQueue);
			consumersHandler[i] = createConsumerService.submit(consumers[i]);
		}
	}
	
	
	
	/**
	 * 写入队列
	 */
	public void write(Object content){
		try {
			//阻塞写入数据,直到空闲
			dataQueue.put(content);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			Logger.error("数据写入Queue阻塞线程中断.",e);
		}
	}
	

	public String getName(){
		return name;
	}

	public void setName(String name){
		this.name = name;
	}

	
	public LinkedBlockingQueue<Object> getDataQueue() {
		return dataQueue;
	}

	/**
	 * 不建议使用
	 */
	@Deprecated
	public void flush() {
		try{
			for(int i = 0 ; i < consumers.length; i++)
				consumers[i].flush();
		}
		catch (InterruptedException e){
			Logger.error("flush cause InterruptedException !");
		} 
		catch(Exception ex)
		{
			Logger.error("flush error !",ex);
		}
	}
	
	/**
	 * 不建议使用
	 */
	@Deprecated
	public void stop(){
		flush();
		for(int i = 0 ; i < consumers.length; i++){
			consumersHandler[i].cancel(true);
		}
		if (createConsumerService != null)
			createConsumerService.shutdown();
	}

}
