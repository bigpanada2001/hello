package netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class NettyClient {
    public static void main(String[] args) throws InterruptedException {
        //NioEventLoopGroup���Կ�����һ���̳߳�,�ͻ���ֻ��Ҫ�������������������NioEventLoopGroup����
        NioEventLoopGroup group = new NioEventLoopGroup();
        Bootstrap b = new Bootstrap();
        b.group(group)
            .channel(NioSocketChannel.class)//ָ��NioģʽΪClientģʽ
            .handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel sc) throws Exception {
                    sc.pipeline().addLast(new ClientHandler());//����Զ���Ŀͻ�����Ϣ����Handler
                }
            });

        ChannelFuture cf = b.connect("127.0.0.1",9999);         //����ָ��host:ip
        cf.channel().write(Unpooled.copiedBuffer("��������Hello I am Client ".getBytes()));//write��д�뻺����,
        cf.channel().flush();             //flush��������,����flush!! ����ʹ��writeAndFlush������������
        cf.channel().closeFuture().sync();//�첽����,������ϲ�ִ�д˴���,Ȼ������ִ�йرղ���
        group.shutdownGracefully();       //�ر�Ӧ��,�Ͽ���server����
    }
}
