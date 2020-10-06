package com.bruce.domain;

import com.bruce.annotation.RpcService;
import com.bruce.registry.IRegistryCenter;
import com.bruce.registry.ZkRegistryCenter;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.StringUtils;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Bruce.Ren
 * @version 1.0
 * @description netty监听服务
 * @data 2020/10/6 19:39
 **/
public class RpcServer implements ApplicationContextAware, InitializingBean {
    // 对外暴露的端口号
    private int port;
    // 提供具体业务处理的服务
    private Map<String, Object> handlerMap = new HashMap();
    // 服务注册中心
    private IRegistryCenter registryCenter = new ZkRegistryCenter();

    public RpcServer(int port) {
        this.port = port;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        //接收客户端的链接
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        //处理已经被接收的链接
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class).
                    childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().
                                    addLast(new ObjectDecoder(Integer.MAX_VALUE, ClassResolvers.cacheDisabled(null))).
                                    addLast(new ObjectEncoder()).
                                    addLast(new ProcessorHandler(handlerMap));
                        }
                    });
            serverBootstrap.bind(port).sync();
        } finally {
           /* workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();*/
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, Object> serviceBeanMap = applicationContext.getBeansWithAnnotation(RpcService.class);
        if (serviceBeanMap.isEmpty()) {
            return;
        }
        for (Object serviceBean : serviceBeanMap.values()) {
            RpcService rpcService = serviceBean.getClass().getAnnotation(RpcService.class);
            String serviceName = rpcService.value().getName();// 拿到接口类定义
            String version = rpcService.version();// 拿到版本号
            if (!StringUtils.isEmpty(version)) {
                serviceName += "-" + version;
            }
            handlerMap.put(serviceName, serviceBean);
            registryCenter.registry(serviceName, getAddress() + ":" + port);
        }
    }

    /**
     * 获取本机的ip地址
     *
     * @return
     */
    private String getAddress() {
        String ip = null;
        InetAddress inetAddress = null;
        try {
            inetAddress = InetAddress.getLocalHost();
            ip = inetAddress.getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return ip;
    }
}
