//package com.baidu.stock.serializable;
//
//import java.io.InputStream;
//import java.io.OutputStream;
//import java.util.ArrayList;
//import java.util.List;
//import com.esotericsoftware.kryo.Kryo;
//import com.esotericsoftware.kryo.io.Input;
//import com.esotericsoftware.kryo.io.Output;
//
///**
// * 基于kryo序列化
// * @author dengjianli
// *
// */
//public class KryoManager {
//	
//	private static List<Class<?>> registerList = new ArrayList<Class<?>>();
//	public static ThreadLocal<Kryo> kryos = new ThreadLocal<Kryo>(){
//		@Override
//		protected Kryo initialValue() {
//			Kryo kryo = new Kryo();
//			kryo.setReferences(false);
//			for(int i=0;i<registerList.size();i++){
//				kryo.register(registerList.get(i));
//			}
//			return kryo;
//		}
//	};
//	
//	public static void registerMapperClass(Class<?> className){
//		registerList.add(className);
//	}
//	
//	
//	
//	public static void writeObjects(OutputStream os, Object object){
//		Output op = new Output(os); 
//		try{
//		kryos.get().writeClassAndObject(op, object);
//		}catch(Exception e){
//			e.printStackTrace();
//		}finally{
//			op.close();
//		}
//	}
//	
//	public static Object readObjects(InputStream is){
//		Input input = new Input(is);
//		try {
//			Object objects = kryos.get().readClassAndObject(input);
//			return objects;
//		} catch (Exception e) {
//			e.printStackTrace();
//		}finally{
//			input.close();
//		}
//		return null;
//	}
//}
