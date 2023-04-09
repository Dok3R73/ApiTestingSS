package steps;

import dto.AbilityDto;
import dto.AbilityInfo;
import dto.ResultListInstanceDto;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import utils.MyStringUtils;

import java.util.List;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;

public class RequestMethods {

    @Step("Получает вес покемона {name}")
    public static int getWeightPokemon(String name) {
        return given()
                .when()
                .contentType(ContentType.JSON)
                .get(MyStringUtils.GET_REQUST + name)
                .then()
                .statusCode(200)
                .extract()
                .body()
                .jsonPath()
                .get("weight");
    }

    @Step("Получает список способностей покемона {name}")
    public static List<AbilityInfo> getListAbilityInfo(String name) {
        return given()
                .when()
                .contentType(ContentType.JSON)
                .get(MyStringUtils.GET_REQUST + name)
                .then()
                .statusCode(200)
                .extract()
                .body()
                .jsonPath()
                .getList("abilities", AbilityInfo.class);
    }

    @Step("Возвращает результат проверки наличия спосоности")
    public static Boolean containsAbility(List<AbilityInfo> list, String ability) {
        return list.stream()
                .map(AbilityInfo::getAbility)
                .map(AbilityDto::getName)
                .collect(Collectors.toList())
                .contains(ability);
    }

    @Step("Получает список покемонов")
    public static List<ResultListInstanceDto> getResultListDto() {
        return given()
                .when()
                .contentType(ContentType.JSON)
                .get(MyStringUtils.GET_REQUST)
                .then()
                .statusCode(200)
                .extract().body().jsonPath().getList("results", ResultListInstanceDto.class);
    }

    @Step("Получает ограниченный список покемонов")
    public static List<ResultListInstanceDto> getResultListLimitDto(Integer limit) {
        return given()
                .when()
                .contentType(ContentType.JSON)
                .get(MyStringUtils.GET_REQUST_LIST_LIMIT + limit)
                .then()
                .statusCode(200)
                .extract().body().jsonPath().getList("results", ResultListInstanceDto.class);
    }
}
