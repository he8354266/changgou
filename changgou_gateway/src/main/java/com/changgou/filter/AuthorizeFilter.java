package com.changgou.filter;


import com.changgou.util.JwtUtil;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @Title: project
 * @Package * @Description:     * @author CodingSir
 * @date 2020/9/259:52
 */
@Component
public class AuthorizeFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//        ServerHttpRequest serverHttpRequest = exchange.getRequest();
//        ServerHttpResponse serverHttpResponse = exchange.getResponse();
//        String path = serverHttpRequest.getURI().getPath();
//        System.out.println(path);
//        if (path.contains("/system/admin/login")) {
//            return chain.filter(exchange);
//        }
//        //获取当前请求头信息
//        HttpHeaders headers = serverHttpRequest.getHeaders();
//        //获取token
//        String jwtToken = headers.getFirst("token");
//        if (StringUtils.isEmpty(jwtToken)) {
//            serverHttpResponse.setStatusCode(HttpStatus.UNAUTHORIZED);
//            return serverHttpResponse.setComplete();
//
//        }
//        try {
//            JwtUtil.parseJWT(jwtToken);
//        } catch (Exception e) {
//            e.printStackTrace();
//            //令牌解析失败
//            serverHttpResponse.setStatusCode(HttpStatus.UNAUTHORIZED);
//            return serverHttpResponse.setComplete();
//        }
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
