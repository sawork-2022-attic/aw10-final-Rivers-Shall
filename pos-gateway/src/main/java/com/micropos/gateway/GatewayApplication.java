package com.micropos.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.channel.DirectChannel;

@SpringBootApplication
@EnableDiscoveryClient
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("carts-service-route", r -> r.path("/carts", "/carts/*", "/carts/*/*")
                        .uri("lb://pos-carts-service"))
                .route("products-service-route", r -> r.path("/products", "/products/*")
                        .uri("lb://pos-products-service"))
                .route("products-service-route", r -> r.path("/orders", "/orders/*", "/delivery/*")
                        .uri("lb://pos-order-service"))
                .build();
    }
}
