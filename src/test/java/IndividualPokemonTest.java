import entity.Ability;
import entity.Abilitys;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.stream.Collectors;

import static io.restassured.RestAssured.filters;
import static io.restassured.RestAssured.given;
import static utils.CustomApiListener.withCustomTemplates;

public class IndividualPokemonTest {

    private static final String GET_REQUST = "https://pokeapi.co/api/v2/pokemon/";
    private static final String NAME_RATTATA = "rattata";
    private static final String NAME_PIDGEOTTO = "pidgeotto";

    @Test
    @Description("Проверяет наличие способности у покемона")
    public void availabilityAbilityTest() {
        filters(withCustomTemplates());
    Assertions.assertTrue(given()
                .when()
                    .contentType(ContentType.JSON)
                    .get(GET_REQUST + NAME_RATTATA)
                .then()
                    .statusCode(200)
                    .extract()
                    .body()
                    .jsonPath()
                    .getList("abilities", Abilitys.class)
                .stream()
                    .map(Abilitys::getAbility)
                    .map(Ability::getName)
                    .collect(Collectors.toList())
                    .contains("run-away"));
    }

    @Test
    @Description("Проверяет отсутствие способности у покемона")
    public void lackAbilityTest() {
        filters(withCustomTemplates());
        Assertions.assertFalse(given()
                .when()
                    .contentType(ContentType.JSON)
                    .get(GET_REQUST + NAME_PIDGEOTTO)
                .then()
                    .statusCode(200)
                    .extract()
                    .body()
                    .jsonPath()
                    .getList("abilities", Abilitys.class)
                .stream()
                    .map(Abilitys::getAbility)
                    .map(Ability::getName)
                    .collect(Collectors.toList())
                    .contains("run-away"));
    }

    @Test
    @Description("Проверяет что вес первого покемона меньше чем у второго")
    public void disparitySizeTest(){
        filters(withCustomTemplates());
    Assertions.assertTrue(getWeightPokemon(NAME_RATTATA) < getWeightPokemon(NAME_PIDGEOTTO));
    }

    @Step("Получает вес покемона {name}")
    public int getWeightPokemon(String name){
        return given()
                .when()
                    .contentType(ContentType.JSON)
                    .get(GET_REQUST + name)
                .then()
                    .statusCode(200)
                    .extract()
                    .body()
                    .jsonPath()
                    .get("weight");
    }
}
