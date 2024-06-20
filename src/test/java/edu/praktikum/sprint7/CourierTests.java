package edu.praktikum.sprint7;

import edu.praktikum.sprint7.courier.CourierClient;
import edu.praktikum.sprint7.models.Courier;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static edu.praktikum.sprint7.courier.CourierGenerator.randomCourier;
import static edu.praktikum.sprint7.models.CourierCreds.credsFromCourier;
import static org.apache.http.HttpStatus.SC_CREATED;
import static org.apache.http.HttpStatus.SC_OK;
import static org.junit.Assert.assertEquals;

public class CourierTests {

    private static final String BASE_URL = "https://qa-scooter.praktikum-services.ru";

    private CourierClient courierClient;
    private int id;

    @Before
    public void setUp() {
        RestAssured.baseURI = BASE_URL;
    }

    @Test
    @DisplayName("Создание курьера")
    @Description("Создание курьера со случайными данными")
    public void createCourier() {
        Courier courier = randomCourier();
        courierClient = new CourierClient();

        Response response = courierClient.create(courier);

        assertEquals("Неверный статус код", SC_CREATED, response.statusCode());

        Response loginResponse = courierClient.login(credsFromCourier(courier));
        id = loginResponse.path("id");

        assertEquals("Неверный статус код", SC_OK, loginResponse.statusCode());
    }

    @After
    public void tearDown() {
        // Нужно реализовать ;)
        // courierClient.delete(id);
    }
}
