package com.baidu.stock.quote.asyn;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 类加载帮助类
 * @author dengjianli
 *
 */
public class AsynUtil{
	private static final Log Logger = LogFactory.getLog(AsynUtil.class);
	private static Map<String,Object> localConfig = new HashMap<String,Object>();
	
	/**
	 * 根据传入的类型按照策略去搜索classpath下的实现，动态装载实现类并且返回
	 * @param <I>
	 * @param 接口描述
	 * @param classLoader
	 * @param 默认的实现类
	 * @return 返回的接口实现实例
	 */
	public static <I> I getInstanceByInterface(Class<I> interfaceDefinition,ClassLoader classLoader,String defaultImplClass,boolean needCache){
		if (needCache && localConfig.get(interfaceDefinition.getName()) != null){
			return newInstance(interfaceDefinition,localConfig.get(interfaceDefinition.getName()).toString(),classLoader);
		}
		String errorStr = new StringBuilder(interfaceDefinition.getName()).append(" Instance load error!").toString();
		try{
			String systemProp = System.getProperty(interfaceDefinition.getName());
            if( systemProp!=null) {
            	if (Logger.isInfoEnabled()){
            		Logger.info("found system property" + systemProp);
            	}
            	if (needCache){
            		localConfig.put(interfaceDefinition.getName(), systemProp);
            	}
                return newInstance(interfaceDefinition,systemProp, classLoader);
            }
		}catch(Exception ex){
			Logger.error(errorStr,ex);
		}
		String serviceId = new StringBuilder("META-INF/services/").append(interfaceDefinition.getName()).toString();
		InputStream in = null;
		try {
            if (classLoader == null){
	            in = ClassLoader.getSystemResourceAsStream(serviceId);
            }
            else{
            	in = classLoader.getResourceAsStream(serviceId);
            }
            if( in != null) {
                BufferedReader rd =new BufferedReader(new InputStreamReader(in, "UTF-8"));
                String className = rd.readLine();
                rd.close();
                if (className != null && ! "".equals(className))  {
                	if (Logger.isInfoEnabled())
                		Logger.info("loaded from services: " + className);
                	if (needCache)
                		localConfig.put(interfaceDefinition.getName(), className);
                    return newInstance(interfaceDefinition,className, classLoader);
                }
            }else{
            	if (defaultImplClass != null && !defaultImplClass.equals("")){
            		if (Logger.isInfoEnabled()){
            			Logger.info("loaded from services: " + defaultImplClass);
            		}
            		if (needCache){
                		localConfig.put(interfaceDefinition.getName(), defaultImplClass);
            		}
                    return newInstance(interfaceDefinition,defaultImplClass, classLoader);
            	}
            }
        } catch( Exception ex ) {
			Logger.error(errorStr,ex);
        }finally{
			try{
				if (in != null)
					in.close();
				    in = null;
			}catch(Exception ex){
				Logger.error(errorStr,ex);
			}
		}		
		throw new java.lang.RuntimeException(errorStr);
	}

	/**
	 * 创建实例
	 * @param <I>
	 * @param interfaceDefinition
	 * @param className
	 * @param classLoader
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private static <I> I newInstance(Class<I> interfaceDefinition,String className, ClassLoader classLoader){
		try{
			Class<I> spiClass;
            if (classLoader == null) {
                spiClass = (Class<I>) Class.forName(className);
            } else {
                spiClass = (Class<I>) classLoader.loadClass(className);
            }
            return spiClass.newInstance();
		}
		catch(ClassNotFoundException x){
			throw new java.lang.RuntimeException( "Provider " + className + " not found", x);
		}
		catch(Exception ex){
			throw new java.lang.RuntimeException("Provider " + className + " could not be instantiated: " + ex,ex);
		}
	}
	
}
