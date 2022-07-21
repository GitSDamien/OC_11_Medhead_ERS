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
	void gatewayAccessAdmin4TokenManagement() {
		System.out.println("Accès Admin de la gateway (token management)");
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();

		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("gatewayKey", "424C-481A-DA17-4908");
		String url = "http://ers-gateway:80/routes";

		HttpEntity<?> entity = new HttpEntity<>(headers);
		HttpEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

		System.out.println(response.getBody());

		Assertions.assertEquals(((ResponseEntity<String>) response).getStatusCode(), HttpStatus.OK);
	}

	@Test
	void ersApiListHospital() {
		System.out.println("Demande la liste des hopitaux");
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();

		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("gatewayKey", "343C-ED0B-4137-B27E");
		String url = "http://ers-gateway:80/api/v1/Hospital";

		HttpEntity<?> entity = new HttpEntity<>(headers);
		HttpEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);

		System.out.println(response.getBody());

		Assertions.assertEquals(((ResponseEntity<String>) response).getStatusCode(), HttpStatus.OK);
	}

	@Test
	void bedAvailabilityRequest() {
		System.out.println("Effectue la tache principal avec un token approprie");
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("gatewayKey", "343C-ED0B-4137-B27E");
		String url = "http://ers-gateway:80/api/v1/BedAvailability";
		String requestJson = "{\"latitude\":43.657554510261534, \"longitude\": 7.049383456590217, \"specs\": [21, 54] }";

		HttpEntity<?> entity = new HttpEntity<>(requestJson, headers);
		HttpEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

		System.out.println(response.getBody());

		Assertions.assertEquals(((ResponseEntity<String>) response).getStatusCode(), HttpStatus.OK);
	}

}
