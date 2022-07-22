package com.mck.cloudparking.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;

import com.mck.cloudparking.dto.ParkingCreateDTO;

import io.restassured.RestAssured;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ParkingControllerTest{

    @LocalServerPort
    private int randomPort;

    @BeforeEach
    public void setUpTest(){
        RestAssured.port = randomPort;
    }

    @Test
    void whenCreateThenCheckIsCreated() {

        var createDTO = new ParkingCreateDTO();
        createDTO.setColor("azul");
        createDTO.setLicense("AAA-1234");
        createDTO.setModel("camaro");
        createDTO.setState("MA");

        RestAssured.given()
            .auth()
            .basic("melck", "12345")
            .when()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .body(createDTO)
            .post("/parkings")
            .then()
            .statusCode(201);
    }

    @Test
    void whenFindAllThenCheckResult() {
        RestAssured.given()
        .auth()
        .basic("melck", "12345")
        .when()
        .get("/parkings")
        .then()
        .statusCode(200);
        
    }
}
