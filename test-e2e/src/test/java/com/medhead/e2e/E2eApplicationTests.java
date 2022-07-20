package com.medhead.e2e;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class E2eApplicationTests {

	@Test
	@DisplayName("Accès Admin de la gateway (token management)") // (4)
	void gatewayAccessAdmin() {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();

		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("gatewayKey", "424C-481A-DA17-4908");
		String url = "http://ers-gateway:80/routes";

		HttpEntity<?> entity = new HttpEntity<>(headers);
		HttpEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

		System.out.println("-------------- Accès Admin pour gestion de Token");
		System.out.println(response.getBody());
		System.out.println("-------------------------------");

		Assertions.assertEquals(((ResponseEntity<String>) response).getStatusCode(), HttpStatus.OK);
	}

}
