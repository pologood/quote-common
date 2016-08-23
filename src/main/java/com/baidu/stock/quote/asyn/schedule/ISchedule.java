package com.baidu.stock.quote.asyn.schedule;

/**
 * 任务接口定义,被用于ExecutorService定时执行
 * 或者周期性执行，内嵌入相关的业务逻辑
 * @author dengjianli
 *
 */
public interface ISchedule extends Runnable
{
}
