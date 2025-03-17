package viosmash.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import viosmash.LoginUser;
import viosmash.utils.SecurityFrameworkUtils;

@Component
public class AuthenticationTokenFilter implements GlobalFilter {

    @Autowired
    private WebClient webClient;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String token = SecurityFrameworkUtils.obtainTokenFromHeader(exchange);
        if(token == null || token.isEmpty()) {
            return chain.filter(exchange);
        }
        return webClient.get().uri(String.format("/rpc/auth/token/check/%s", token))
                .retrieve().bodyToMono(LoginUser.class)
                .flatMap(loginUser -> {
                   if(loginUser.isExpired()) {
                       return chain.filter(exchange);
                   }
                   ServerWebExchange newExchange = exchange.mutate()
                           .request(builder -> SecurityFrameworkUtils.setLoginUserHeader(builder, loginUser))
                           .build();
                   return chain.filter(newExchange);
                });
    }
}
