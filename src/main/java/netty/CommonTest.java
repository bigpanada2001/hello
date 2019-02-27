package netty;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;

public class CommonTest {

	public static void main(String[] args) {
	    String str = "哈哈,我是一条消息";
	    Message msg = new Message((byte)0xAD,35,str);
	    ByteArrayOutputStream out = new ByteArrayOutputStream();
	    try {
	        ObjectOutputStream os = new ObjectOutputStream(out);
	        os.writeObject(msg);
	        os.flush();
	        byte[] b = out.toByteArray();
	        System.out.println("jdk序列化后的长度： "+b.length);
	        os.close();
	        out.close();


	        ByteBuffer buffer = ByteBuffer.allocate(1024);
	        byte[] bt = msg.getMsgBody().getBytes();
	        buffer.put(msg.getType());
	        buffer.putInt(msg.getLength());
	        buffer.put(bt);
	        buffer.flip();

	        byte[] result = new byte[buffer.remaining()];
	        buffer.get(result);
	        System.out.println("使用二进制序列化的长度："+result.length);

	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}

}
