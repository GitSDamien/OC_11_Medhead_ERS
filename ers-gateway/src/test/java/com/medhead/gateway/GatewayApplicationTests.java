package com.medhead.gateway;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class GatewayApplicationTests {
    @LocalServerPort
    int port;
    private WebTestClient client;

    @BeforeEach
    public void setup() {
        client = WebTestClient.bindToServer()
            .baseUrl("http://localhost:" + port)
            .build();
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testGatewayControllerWithoutToken() {
        client.get().uri("/routes")
            .exchange()
            .expectStatus().is4xxClientError();
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testGatewayController() {
        client.get().uri("/routes")
            .header("gatewayKey", "424C-481A-DA17-4908")
            //.header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_UTF8_VALUE)
            //.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE)
            .exchange()
            .expectStatus().isOk();
            //.expectBody(Map.class)
            //.consumeWith(result -> {
            //    assertThat(result.getResponseBody()).isNotEmpty();
           // });
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testRouteAWithoutToken() {
        client.get().uri("/api/v1/")
            .exchange()
            .expectStatus().isUnauthorized();
    }

//    @Test
//    @SuppressWarnings("unchecked")
//    public void testRouteA() {
//        client.get().uri("/api/v1/")
//            .header("gatewayKey", "343C-ED0B-4137-B27E")
//            .exchange()
//            .expectStatus().isOk();
//    }

}
