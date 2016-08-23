package com.baidu.stock.quote.asyn;

import java.util.List;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.channel.ExecutorChannel;


/**
 * 异步输出的模板类,主要该组件仅仅局限于行情输出场景
 * @author dengjianli
 */
public abstract class AsynWriterTemplate<T>{
	private IAsynWriter[] writers;
	private int writerCount;
	
	/**
	 * 构造写入器个数,每个写入器就是一个桶,每个桶就包含一个queue
	 * @param writerCount
	 */
	public AsynWriterTemplate(int writerCount,DirectChannel directChannel,ExecutorChannel executorChannel){
		this.writerCount = writerCount;
		createWriters(directChannel,executorChannel);
	}
	
	/**
	 * 创建异步输出者
	 */
	private void createWriters(DirectChannel directChannel,ExecutorChannel executorChannel){
		writers = new IAsynWriter[writerCount];
		for(int i= 0 ; i< writerCount; i++){					
			writers[i] =new AsynWriter(directChannel,executorChannel,i);
			writers[i].setName(new StringBuffer("AsynWriter-").append(i).toString());
		}
	}
	
	/**
	 * 单个写入
	 * @param content
	 */
	public void write(T content){
		IAsynWriter writer = getWriter(content);
		writer.write(content);
	}
	
	/**
	 * 批量写入，用于批量原子抓取,末尾添加结束标志;批量写入的数据必须是原子的,反之不适用
	 * @param lstContent
	 */
	public void write(List<T> lstContent){
		for(T content:lstContent){
			IAsynWriter writer = getWriter(content);
			writer.write(content);
		}
		//分别给writer添加写入结束标志
		for(IAsynWriter writer:writers){
			writer.write(new Tail());
		}
		
	}
	/**
	 *  不建议不适用
	 */
	@Deprecated
	public void flush(){
		if (writers != null && writers.length > 0)
			for(int i = 0 ; i < writers.length; i++)
				writers[i].flush();
	}
	
	/**
	 *  不建议不适用stop方法关闭线程池
	 */
	@Deprecated
	public void stop(){
		if (writers != null && writers.length > 0)
			for(int i = 0 ; i < writers.length; i++)
				writers[i].stop();
	}
	
	/**
	 * 在多个Writer的情况下如何将T这个内容分配到某一个Writer，算法由使用者实现,负载均衡策略
	 * @param content
	 */
	public abstract IAsynWriter getWriter(Object content);

	protected IAsynWriter[] getWriters(){
		return writers;
	}

	
}
