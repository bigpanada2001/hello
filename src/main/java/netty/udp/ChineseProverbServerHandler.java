package netty.udp;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import io.netty.util.CharsetUtil;

public class ChineseProverbServerHandler extends SimpleChannelInboundHandler<DatagramPacket> {
	//�����б�
	private static final String[] DICTIONARY = { "ֻҪ���������ĥ���롣",
	    "��ʱ��л��ǰ��,����Ѱ�����ռҡ�", "�������������ʣ�һƬ�����������", "һ�����һ��𣬴������������",
	    "����������־��ǧ���ʿĺ�꣬׳�Ĳ���" };
	private String nextQuote(){
	//����0-DICTIONARY.length�е�һ��������
	int quoteId = ThreadLocalRandom.current().nextInt(DICTIONARY.length);
	return DICTIONARY[quoteId];//�������б��ж�Ӧ�����ﷵ��
	}
	/**
	* ����������У��β�packet�ͻ��˷�������DatagramPacket����
	* DatagramPacket �����
	* 1.��������ô˵�ģ�
	* The message container that is used for {@link DatagramChannel} to communicate with the remote peer.
	* ���룺DatagramPacket ����Ϣ�����������Ϣ������ DatagramChannelʹ�ã�������������Զ���豸����
	* 2.������Դ�����Ƿ���DatagramPacket��final�಻�ܱ��̳У�ֻ�ܱ�ʹ�á����ǻ�����DatagramChannel����ʵ����AddressedEnvelope�ӿڣ����������ǿ�һ��AddressedEnvelope�ӿڡ�
	* AddressedEnvelope�ӿڹ����������£�
	* A message that wraps another message with a sender address and a recipient address.
	* ���룺����һ����Ϣ,�����Ϣ���������ߺͽ�������Ϣ
	* 3.������֪����DatagramPacket�������˷����ߺͽ����ߵ���Ϣ��
	* ͨ��content()����ȡ��Ϣ����
	* ͨ��sender();����ȡ�����ߵ���Ϣ
	* ͨ��recipient();����ȡ�����ߵ���Ϣ��
	* 
	* 4.public DatagramPacket(ByteBuf data, InetSocketAddress recipient) {}
	*  ���DatagramPacket���е�һ�����췽����data �Ƿ�������;�Ƿ��Ͷ���Ϣ��
	*/
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket packet)
	    throws Exception {
	
	String req = packet.content().toString(CharsetUtil.UTF_8);//����˵�ˣ�ͨ��content()����ȡ��Ϣ����
	System.out.println(req);
	if("�����ֵ��ѯ��".equals(req)){//�����Ϣ�ǡ������ֵ��ѯ�������������ȡһ����Ϣ���ͳ�ȥ��
	    /**
	     * ���� new һ��DatagramPacket��������ͨ��packet.sender()����ȡ�����ߵ���Ϣ��
	     * ���·����ȥ��
	     */
	    ctx.writeAndFlush(new DatagramPacket(Unpooled.copiedBuffer("�����ѯ�����"+nextQuote(),CharsetUtil.UTF_8), packet.sender()));
	}
	}
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
	    throws Exception {
	ctx.close();
	cause.printStackTrace();
	}

}
