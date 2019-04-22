package com.galaxis.wcs.yanfeng.config;

import com.galaxis.wcs.yanfeng.connection.netty.NettyClient;
import com.galaxis.wcs.yanfeng.database.oes.domain.Line;
import com.galaxis.wcs.yanfeng.database.oes.domain.LineExample;
import com.galaxis.wcs.yanfeng.database.oes.service.LineService;
import com.galaxis.wcs.yanfeng.device.line.YanFengKtInboundHandler;
import com.galaxis.wcs.yanfeng.device.line.YanFengLineInboundHandler;
import com.galaxis.wcs.yanfeng.util.Config;
import com.galaxis.wcs.yanfeng.util.SpringBeanFactoryTool;
import com.galaxis.wcs.yanfeng.util.ThreadUtil;
import com.galaxis.wcs.yanfeng.work.manager.ConfigManager;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.ReadTimeoutHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Configuration
public class InitFinishConfig implements ApplicationListener<ContextRefreshedEvent> {
    private static final Logger log = LoggerFactory.getLogger(InitFinishConfig.class);

    @Autowired
    private ConfigManager configManager;
    @Autowired
    private LineService lineService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        log.info("spring容器加载完毕....");

        // 初始化PLC连接
        initPlcConnection();

    }

    private void initPlcConnection() {
        List<Line> lines = lineService.selectByExample(new LineExample());
        if (lines.isEmpty()) {
            log.info("没有设备需要初始化...");
            return;
        }

        long lineReaderIdleTime = configManager.getNumber(Config.KEY_TIME_OUT_LINE_READ, 20000).longValue();
        long ktReaderIdleTime = configManager.getNumber(Config.KEY_TIME_OUT_KT_READ, 20000).longValue();

        LineChannelInitializer lineChannelInitializer = new LineChannelInitializer(lineReaderIdleTime);
        KtChannelInitializer ktChannelInitializer = new KtChannelInitializer(ktReaderIdleTime);

        for (Line line : lines) {
            Integer id = line.getId();
            Integer status = line.getStatus();
            if (status == 0) {
                log.info("该设备尚未启用, id: {}, status: {}", id, status);
                continue;
            }

            String ip = line.getIp();
            Integer port = line.getPort();

            String type = line.getType();
            log.info("正在初始化 {}, id: {}, status: {}, {}:{}", type, id, status, ip, port);
            if ("lineYanFeng".equalsIgnoreCase(type)) {
                // 初始化输送线的连接
                NettyClient client = new NettyClient(ip, port, lineChannelInitializer, true, ThreadUtil.SLEEP_MIDDLE);
                ThreadUtil.createThread("line-conn-" + id, client::init).start();

            } else if ("ktYanFeng".equalsIgnoreCase(type)) {
                // 初始化KT的连接
                NettyClient client = new NettyClient(ip, port, ktChannelInitializer, true, ThreadUtil.SLEEP_MIDDLE);
                ThreadUtil.createThread("kt-conn-" + id, client::init).start();

            } else {
                log.warn("未知的设备类型, id: {}, type: {}", id, type);
            }

        }

    }

    private class LineChannelInitializer extends io.netty.channel.ChannelInitializer<SocketChannel> {

        private long readerIdleTime;

        public LineChannelInitializer(long readerIdleTime) {
            this.readerIdleTime = readerIdleTime;
        }

        @Override
        protected void initChannel(SocketChannel socketChannel) throws Exception {
            socketChannel.pipeline()
                    // .addLast("IdleStateHandler", new IdleStateHandler(readerIdleTime, writerIdleTime, allIdleTime, TimeUnit.MILLISECONDS))
                    .addLast("IdleStateHandler", new ReadTimeoutHandler(readerIdleTime, TimeUnit.MILLISECONDS))
                    .addLast("LineHandler", SpringBeanFactoryTool.getBean("yanFengLineInboundHandler", YanFengLineInboundHandler.class));
        }
    }

    private class KtChannelInitializer extends ChannelInitializer<SocketChannel> {

        private long readerIdleTime;

        public KtChannelInitializer(long readerIdleTime) {
            this.readerIdleTime = readerIdleTime;
        }

        @Override
        protected void initChannel(SocketChannel socketChannel) throws Exception {
            socketChannel.pipeline()
                    // .addLast("IdleStateHandler", new IdleStateHandler(readerIdleTime, writerIdleTime, allIdleTime, TimeUnit.MILLISECONDS))
                    .addLast("timeoutHandler", new ReadTimeoutHandler(readerIdleTime, TimeUnit.MILLISECONDS))
                    .addLast("ktHandler", new YanFengKtInboundHandler());
        }
    }
}
