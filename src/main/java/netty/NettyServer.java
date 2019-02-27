package netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class NettyServer {

    public static void main(String[] args) throws InterruptedException {
        //NioEventLoopGroup���Կ�����һ���̳߳�,parentGroup����������������,childGroup�����������IO����
        NioEventLoopGroup parentGroup = new NioEventLoopGroup();//��������������˽��ܿͻ�����
        NioEventLoopGroup childGroup = new NioEventLoopGroup(); //������������ͨ��(�����д)
        ServerBootstrap bootstrap = new ServerBootstrap();      //����������ͨ�����õĸ���������
        bootstrap.group(parentGroup,childGroup)                 //����ÿ��NioEventLoopGroup����;
                .channel(NioServerSocketChannel.class)          //ָ��NioģʽΪServerģʽ
                .option(ChannelOption.SO_BACKLOG,1024)    //ָ��tcp������
                .option(ChannelOption.SO_SNDBUF,10*1024)  //ָ�����ͻ�������С
                .option(ChannelOption.SO_RCVBUF,10*1024)  //ָ�����ջ�������С
                .option(ChannelOption.SO_KEEPALIVE,Boolean.TRUE)//�Ƿ񱣳�����,Ĭ��true
                .childHandler(new ChannelInitializer<SocketChannel>() {//��������ݽ��շ���
                    @Override
                    protected void initChannel(SocketChannel sc) throws Exception {     //���ChannelHandler,handler�����Զ�����Ϣ�����߼�
                        sc.pipeline().addLast(new ServerHandler());//��ʵ������Ӷ��Handlerʵ������
                    }
                });

        ChannelFuture cfuture = bootstrap.bind(9999).sync();//�첽�󶨶˿�
        cfuture.channel().closeFuture().sync();//��������,�ȴ��ر�
        parentGroup.shutdownGracefully();//�ر�Ӧ��
        childGroup.shutdownGracefully();
    }
}

