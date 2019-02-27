package netty;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;

public class CommonTest {

	public static void main(String[] args) {
	    String str = "����,����һ����Ϣ";
	    Message msg = new Message((byte)0xAD,35,str);
	    ByteArrayOutputStream out = new ByteArrayOutputStream();
	    try {
	        ObjectOutputStream os = new ObjectOutputStream(out);
	        os.writeObject(msg);
	        os.flush();
	        byte[] b = out.toByteArray();
	        System.out.println("jdk���л���ĳ��ȣ� "+b.length);
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
	        System.out.println("ʹ�ö��������л��ĳ��ȣ�"+result.length);

	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}

}
