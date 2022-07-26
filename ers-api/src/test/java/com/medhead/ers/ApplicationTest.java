package com.medhead.ers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.text.MessageFormat;
import java.time.Duration;
import java.time.Instant;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.medhead.ers.service.MainService;

@SpringBootTest
@AutoConfigureMockMvc
public class ApplicationTest {
    @Autowired
    private MockMvc mockMvc;

    private static Instant startedAt;

    // Testez la vitesse de vos traitements

    @BeforeAll
	static public void initStartingTime() {
		System.out.println("Appel avant tous les tests");
		startedAt = Instant.now();
	}

	@AfterAll
	static public void showTestDuration() {
		System.out.println("Appel après tous les tests");
		Instant endedAt = Instant.now();
		long duration = Duration.between(startedAt, endedAt).toMillis(); 
		System.out.print("Durée des tests (ms) : ");
		System.out.println(duration);
	}

    // Tests unitaires

    // https://assertj.github.io/doc/
    
    @Test
    // @CsvSource({ "48.862725,2.287592,43.2961743,5.3699525,662914" })
    void testDistanceLatLong(){
        // GIVEN
        final double lat1 = 48.862725; // Paris
        final double lon1 = 2.287592;
        final double lat2 = 43.2961743; // Marseille
        final double lon2 = 5.3699525;
        final long result = 662914;
 
        // WHEN
        final double distance = MainService.distance(lat1, lat2, lon1, lon2, 0, 0);
 
        // THEN
        assertThat(result).isEqualTo(Math.round(distance));
    }


    // Tests d'intégration

    @Test
    public void shouldReturnDefaultMessage() throws Exception {
        this.mockMvc.perform(get("/NHSSpec")).andDo(print())
                .andExpect(status().isOk());
                //.andExpect(content().string(containsString("Hello, World !")));
    }
    
    
    @Test
    public void shouldReturnListOfHospitals() throws Exception {
        this.mockMvc.perform(get("/Hospital")).andDo(print())
                .andExpect(status().isOk());
    }

}
