package netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/*
ChannelInboundHandlerAdapter 中有很多方法可以覆盖,这些方法覆盖了一个请求处理的整个生命周期,
一般来说我们只需要关心channelRead和exceptionCaught 方法
*/
public class ServerHandler extends ChannelInboundHandlerAdapter {

    //数据读取逻辑
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //ByteBuf如果只用来读数据而没有writeAndFlush写数据则使用完必须使用调用release()方法,释放内存
        ByteBuf buf = (ByteBuf) msg;
        byte[] bytes = new byte[buf.readableBytes()];
        buf.readBytes(bytes);
        String rev = new String(bytes,"utf-8");
        System.out.println("server收到数据:"+ rev);
        //给客户端响应一条数据
        ctx.writeAndFlush(Unpooled.copiedBuffer("你好,我是Server".getBytes()))
                //添加监听器,写出数据后关闭通道,原理上只要拿到Futrue对象server端和client端都可以主动关闭,一般在server端关闭较好
                .addListener(ChannelFutureListener.CLOSE);
        buf.release();//释放ByteBuf
    }

    //抛出异常时处理逻辑
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        System.out.println("exceptionCaught");
    }
}
