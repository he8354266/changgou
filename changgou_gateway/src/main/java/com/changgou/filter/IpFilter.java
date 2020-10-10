package com.changgou.filter;

import org.omg.CORBA.ServerRequest;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.InetSocketAddress;

/**
 * @Title: project
 * @Package * @Description:     * @author CodingSir
 * @date 2020/9/2418:02
 */
@Component
public class IpFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        System.out.println("经过第一个过滤器=========");
        ServerHttpRequest serverHttpRequest = exchange.getRequest();
        InetSocketAddress inetSocketAddress = serverHttpRequest.getRemoteAddress();
        System.out.println("ip=="+inetSocketAddress.getHostName());
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 2;
    }
}
