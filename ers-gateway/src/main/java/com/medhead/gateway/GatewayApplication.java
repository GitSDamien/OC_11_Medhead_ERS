package com.medhead.gateway;

import com.medhead.gateway.configuration.AppConstant;
import com.medhead.gateway.model.ApiKey;
import com.medhead.gateway.dao.ApiKeyDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

import javax.annotation.PostConstruct;
import java.util.stream.StreamSupport;

@SpringBootApplication
public class GatewayApplication {

	@Autowired
	ApiKeyDao apiKeyDao;

	public static void main(String[] args) {
		SpringApplication.run(GatewayApplication.class, args);
	}

	@Bean
	public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
		return builder.routes()
			.route(AppConstant.SERVICE_A_KEY, r ->
				r.path(AppConstant.SERVICE_A_PATH)
				.filters(f -> f.stripPrefix(2)).uri(AppConstant.SERVICE_A_URI))

//			.route(AppConstant.SERVICE_B_KEY,
//				r -> r.path(AppConstant.SERVICE_B_PATH)
//				.filters(f -> f.stripPrefix(2)).uri(AppConstant.SERVICE_B_URI))

			.build();
	}


	@PostConstruct
	public void initData() {
		Iterable<ApiKey> listApiKey = apiKeyDao.findAll();
		if(StreamSupport.stream(listApiKey.spliterator(), false).count() <= 0){
			AppConstant.apiKeys.forEach(k -> apiKeyDao.save(k));
		}
	}

}
