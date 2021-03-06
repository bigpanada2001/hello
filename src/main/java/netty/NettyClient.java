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
        //NioEventLoopGroup可以看做是一个线程池,客户端只需要用来处理发送数据任务的NioEventLoopGroup即可
        NioEventLoopGroup group = new NioEventLoopGroup();
        Bootstrap b = new Bootstrap();
        b.group(group)
            .channel(NioSocketChannel.class)//指定Nio模式为Client模式
            .handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel sc) throws Exception {
                    sc.pipeline().addLast(new ClientHandler());//添加自定义的客户端消息处理Handler
                }
            });

        ChannelFuture cf = b.connect("127.0.0.1",9999);         //连接指定host:ip
        cf.channel().write(Unpooled.copiedBuffer("缓冲数据Hello I am Client ".getBytes()));//write是写入缓冲区,
        cf.channel().flush();             //flush缓冲数据,必须flush!! 或者使用writeAndFlush方法发送数据
        cf.channel().closeFuture().sync();//异步监听,传输完毕才执行此代码,然后向下执行关闭操作
        group.shutdownGracefully();       //关闭应用,断开和server连接
    }
}
