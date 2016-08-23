package com.baidu.stock.serializable;

import com.baidu.stock.quote.dto.HQMessage;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.jms.BytesMessage;


/**
 *  使用 Kryo 实现序列化
 * @author dengjianli
 *
 */
public class KryoSerializable{

	private final static Kryo kryo = new Kryo();
	static{
	kryo.setReferences(false);
	kryo.register(HQMessage.class);
	}

	public String name() {
		return "kryo";
	}

	public static byte[] toBytes(Object obj) throws IOException {
		Output output = null;
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			output = new Output(baos);
			output.toBytes();
			kryo.writeClassAndObject(output, obj);
			output.flush();
			return baos.toByteArray();
		}finally{
			if(output != null)
				output.close();
		}
	}

	@SuppressWarnings("rawtypes")
	public static HQMessage bytesMessage2Object(BytesMessage msg) throws IOException {
		HQMessage hqMessage=null;
		Input ois = null;
		try {
			byte[] bytes = new byte[(int) msg.getBodyLength()];
			msg.readBytes(bytes);
			ois = new Input(new ByteArrayInputStream(bytes));
			hqMessage=(HQMessage) kryo.readClassAndObject(ois);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(ois != null)
				ois.close();
		}
		return hqMessage;
	}
	
	public static Object toObject(byte[] bits) throws IOException {
		if(bits == null || bits.length == 0)
			return null;
		Input ois = null;
		try {
			ois = new Input(new ByteArrayInputStream(bits));
			return kryo.readClassAndObject(ois);
		} finally {
			if(ois != null)
				ois.close();
		}
	}
}
