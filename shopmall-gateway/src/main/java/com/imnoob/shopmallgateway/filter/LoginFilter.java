package com.imnoob.shopmallgateway.filter;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.imnoob.shopmallcommon.utils.AjaxResult;
import com.imnoob.shopmallcommon.utils.R;
import com.imnoob.shopmallgateway.fegin.AuthFegin;
import com.imnoob.shopmallgateway.properties.WhiteURL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;



import javax.annotation.Resource;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
public class LoginFilter implements GlobalFilter, Ordered {

    @Resource
    AuthFegin authFegin;

    @Autowired
    WhiteURL whiteURL;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        String path = exchange.getRequest().getURI().getPath();
        System.out.println(path);
        String token = exchange.getRequest().getQueryParams().getFirst("access-token");


        if (true) return chain.filter(exchange); //继续向下执行

        //2.判断是否存在
        if(token == null) {
            //3.如果不存在 : 认证失败 response.writeWith(Mono.just(buffer));
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            String res = JSONObject.toJSONString(R.error("登陆失败"));
            byte[] bits = res.getBytes(StandardCharsets.UTF_8);
            DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bits);
            return exchange.getResponse().writeWith(Mono.just(buffer)); //请求结束
        }
        //4.如果存在,继续执行
        AjaxResult res = authFegin.checkToken(token);
        if (res.getCode() == HttpStatus.OK.value())
            return chain.filter(exchange); //继续向下执行
        else
           return exchange.getResponse().setComplete(); //请求结束
    }

    @Override
    public int getOrder() {
        return -1;
    }

    private Boolean containpath(List<String> paths,String target){
        for (String path : paths) {
            System.out.println(path);
            if (target.equals(path)) return true;
        }
        return false;
    }
}
