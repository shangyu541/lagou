package com.lagou.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

/**
 * 〈全局过滤器〉
 *
 * @author 商玉
 * @create 2022/1/19
 * @since 1.0.0
 */
@Slf4j
//@Component
public class BlackListFilter implements GlobalFilter, Ordered {


    //莫伊黑名单
    private static List<String> list=new ArrayList<>();

    static {
        list.add("0:0:0:0:0:0:0:1");
    }


    /**
     * 过滤器核心方法
     * @param exchange  封装了request和response对象的上下文
     * @param chain 网关过滤器链（包含全局过滤器和单路由过滤器）
     * @return
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //思路： 获取客户端ip，判断是否在和名单中，在的话就拒绝访问，不在就放行
        //从上下文取出request和response
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();

        //从request对象中获取客户端ip
        String clientIp = request.getRemoteAddress().getHostString();

        //拿着clientIp去和名单中查询，存在的话就拒绝访问
        if (list.contains(clientIp)) {
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            log.debug("黑名单，拒绝访问");
            String data="request be denied!";
            DataBuffer wrap = response.bufferFactory().wrap(data.getBytes());
            return response.writeWith(Mono.just(wrap));
        }
        //合法请求，放行，执行后续的过滤器
        return chain.filter(exchange);
    }


    @Override
    public int getOrder() {
        return 0;
    }
}
