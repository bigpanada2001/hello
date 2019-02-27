package netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/*
ChannelInboundHandlerAdapter ���кܶ෽�����Ը���,��Щ����������һ���������������������,
һ����˵����ֻ��Ҫ����channelRead��exceptionCaught ����
*/
public class ServerHandler extends ChannelInboundHandlerAdapter {

    //���ݶ�ȡ�߼�
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //ByteBuf���ֻ���������ݶ�û��writeAndFlushд������ʹ�������ʹ�õ���release()����,�ͷ��ڴ�
        ByteBuf buf = (ByteBuf) msg;
        byte[] bytes = new byte[buf.readableBytes()];
        buf.readBytes(bytes);
        String rev = new String(bytes,"utf-8");
        System.out.println("server�յ�����:"+ rev);
        //���ͻ�����Ӧһ������
        ctx.writeAndFlush(Unpooled.copiedBuffer("���,����Server".getBytes()))
                //��Ӽ�����,д�����ݺ�ر�ͨ��,ԭ����ֻҪ�õ�Futrue����server�˺�client�˶����������ر�,һ����server�˹رսϺ�
                .addListener(ChannelFutureListener.CLOSE);
        buf.release();//�ͷ�ByteBuf
    }

    //�׳��쳣ʱ�����߼�
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        System.out.println("exceptionCaught");
    }
}
