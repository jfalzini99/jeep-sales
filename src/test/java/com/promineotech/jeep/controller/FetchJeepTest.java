/*
 * Week 13+ Coding Assignment FetchJeepTest.java JUnit Test Case
 * Edited By: Joseph Falzini
 */


package com.promineotech.jeep.controller;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.jdbc.JdbcTestUtils;

import com.promineotech.jeep.entity.Jeep;
import com.promineotech.jeep.entity.JeepModel;

import lombok.Getter;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Sql(scripts = {
		"classpath:flyway/migrations/V1.0_Jeep_Schema.sql",
		"classpath:flyway/migrations/V1.1_Jeep_Data.sql"},
		config = @SqlConfig(encoding = "utf-8"))
class FetchJeepTest {
	@LocalServerPort
	private int serverPort;
	
	@Autowired
	@Getter
	private TestRestTemplate restTemplate;

	@Test
	void testThatJeepsAreReturnedWhenAValidModelAndTrimAreSupplied() {
		// Given a valid model, trim and URI...
		JeepModel model = JeepModel.WRANGLER;
		String trim = "Sport";
		String uri = String.format("http://localhost:%d/jeeps?model=%s&trim=%s",
				serverPort, model, trim);
		
		// When a connection is made to the URI...
		ResponseEntity<List<Jeep>> response = 
				restTemplate.exchange(uri, HttpMethod.GET, null,
				new ParameterizedTypeReference<>() {});
	
		
		// Then a success (OK - 200) status code is returned.
		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
		
		// And: the actual list returned is the same as the expected list
		List<Jeep> expected = buildExpected();
		assertThat(response.getBody()).isEqualTo(expected);
	}

	protected List<Jeep> buildExpected() {
		List<Jeep> list = new LinkedList<Jeep>();
		
		// @formatter:off
		list.add(Jeep.builder()
			.modelId(JeepModel.WRANGLER)
			.trimLevel("Sport")
			.numDoors(2)
			.wheelSize(17)
			.basePrice(new BigDecimal("28475.00"))
			.build());
		
		list.add(Jeep.builder()
			.modelId(JeepModel.WRANGLER)
			.trimLevel("Sport").numDoors(4)
			.wheelSize(17)
			.basePrice(new BigDecimal("31975.00"))
			.build());
		// @formatter: on
		
		return list;
	}
	
}

/**
 * This MySQL code is used as reference for the buildExpected() method.
 * 	INSERT INTO models (model_id, trim_level, num_doors, wheel_size, base_price) VALUES('WRANGLER', 'Sport', 2, 17, 28475.00);
 * 	INSERT INTO models (model_id, trim_level, num_doors, wheel_size, base_price) VALUES('WRANGLER', 'Sport', 4, 17, 31975.00);
 */


	
