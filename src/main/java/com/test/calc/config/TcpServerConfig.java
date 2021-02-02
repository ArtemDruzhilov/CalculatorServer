package com.test.calc.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.ip.tcp.TcpInboundGateway;
import org.springframework.integration.ip.tcp.connection.AbstractServerConnectionFactory;
import org.springframework.integration.ip.tcp.connection.TcpNioServerConnectionFactory;
import org.springframework.messaging.MessageChannel;

/**
 * Configuration of Server
 */
@Configuration
public class TcpServerConfig {

    @Value("${tcp.server.port}")
    private int port;

    @Bean
    public AbstractServerConnectionFactory serverConnectionFactory() {
        return new TcpNioServerConnectionFactory(port);
    }

    @Bean
    public MessageChannel inboundChannel() {
        return new DirectChannel();
    }

    /**
     * Creating inbound gateway
     * Sets the input channel for the gateway, but does not set the output channel
     * Messages arriving at this gateway will be processed,
     * and the processing result will be returned to the sender
     * @return gateway with only input channel (no output channel)
     */
    @Bean
    public TcpInboundGateway inboundGateway(final AbstractServerConnectionFactory serverConnectionFactory,
                                            final MessageChannel inboundChannel) {
        final var tcpInboundGateway = new TcpInboundGateway();
        tcpInboundGateway.setConnectionFactory(serverConnectionFactory);
        tcpInboundGateway.setRequestChannel(inboundChannel);
        return tcpInboundGateway;
    }
}
