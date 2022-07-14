package com.medhead.gateway.filter;

import com.medhead.gateway.model.ApiKey;
import com.medhead.gateway.dao.ApiKeyDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class AuthFilter implements GlobalFilter, Ordered {
    @Autowired
    ApiKeyDao apiKeyDao;

    Logger log = LoggerFactory.getLogger(AuthFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        List<String> apiKeyHeader = exchange.getRequest().getHeaders().get("gatewayKey");
        log.info("Api key: {}", apiKeyHeader);

        Route route = exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR);
        String routeId = route != null ? route.getId() : null;
        if (routeId == null || CollectionUtils.isEmpty(apiKeyHeader) || !isAuthorized(routeId, apiKeyHeader.get(0))) {
            log.error("Api Key not valid");
            // log.error(routeId);
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You cannot consume this service. Please check your api key.");
        }

        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }

    /**
     * @param routeId id of route
     * @param apikey client header api key
     * @return true if is authorized, false otherwise
     */
    private boolean isAuthorized(String routeId, String apikey) {
        Iterable<ApiKey> listApiKey = apiKeyDao.findAllByRoute(routeId);
        for (ApiKey elem : listApiKey) {
            if(apikey.equals(elem.getToken())){
                return true;
            }
        }
        return false;
    }
}
