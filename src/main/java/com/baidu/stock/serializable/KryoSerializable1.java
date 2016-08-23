//package com.baidu.stock.serializable;
//
//import java.io.ByteArrayInputStream;
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//import javax.jms.BytesMessage;
//import com.baidu.stock.quote.dto.HQMessage;
//
///**
// * 
// * @author dengjianli
// *
// */
//public class KryoSerializable1 {
//	
////	static{
////		KryoManager.registerMapperClass(HQMessage.class);
////	    }
//	
//	public static byte[] toBytes(HQMessage obj) throws Exception{
//		ByteArrayOutputStream bos = new ByteArrayOutputStream();
//		try{
//			//转化成字节
//			KryoManager.writeObjects(bos, obj);
//			byte[]bytes=bos.toByteArray();
//			return bytes;
//		}catch(Exception e){
//			e.printStackTrace();
//		}finally{
//			bos.flush();
//			bos.close();	
//		}
//		return null;
//	}
//	
//	
//	public static  HQMessage bytesMessage2Object(BytesMessage msg){
//		HQMessage hqMessage=null;
//		ByteArrayInputStream in=null;
//		try{
//		byte[] bytes = new byte[(int) ((BytesMessage)msg).getBodyLength()];
//		((BytesMessage)msg).readBytes(bytes);
//		in=new ByteArrayInputStream(bytes);
//		hqMessage= (HQMessage) KryoManager.readObjects(in);
//		}catch(Exception ex){
//			ex.printStackTrace();
//		}finally{
//			if(in!=null){
//				try {
//					in.close();
//				} catch (IOException e) {
//				}
//			}
//		}
//		return hqMessage;
//	}
//		
//}
