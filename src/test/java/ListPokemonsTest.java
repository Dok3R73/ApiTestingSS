import entity.ResultListInstance;
import io.qameta.allure.Description;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class ListPokemonsTest {

    private static final String GET_REQUST = "https://pokeapi.co/api/v2/pokemon";

    @Test
    @Description("Проверяет ограничивается ли список")
    public void testExpectedListSize() {
        Assertions.assertEquals(5, given()
                .when()
                .contentType(ContentType.JSON)
                .get(GET_REQUST + "?limit=5")
                .then()
                .extract()
                .body()
                .jsonPath()
                .getList("results", ResultListInstance.class)
                .size());
    }

    @Test
    @Description("Проверяет наличие имени у каждого покемона из списка")
    public void testCheckPokeHaveName() {
        given()
                .when()
                .contentType(ContentType.JSON)
                .get(GET_REQUST)
                .then()
                .extract().body().jsonPath().getList("results", ResultListInstance.class)
                .stream()
                .map(ResultListInstance::getName)
                .forEach(x -> Assertions.assertFalse(x.isEmpty()));
    }
}
