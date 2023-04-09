import dto.AbilityInfo;
import io.qameta.allure.Description;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import steps.RequestMethods;
import utils.MyStringUtils;

import java.util.List;

import static io.restassured.RestAssured.filters;
import static utils.CustomApiListener.withCustomTemplates;

public class IndividualPokemonTest {

    @Test
    @Description("Проверяет наличие способности у покемона")
    public void availabilityAbilityTest() {
        filters(withCustomTemplates());

        List<AbilityInfo> abilityInfoList = RequestMethods.getListAbilityInfo(MyStringUtils.NAME_RATTATA);

        boolean result = RequestMethods.containsAbility(abilityInfoList, MyStringUtils.ABILITY_RUN_AWAY);

        Assertions.assertTrue(result);
    }

    @Test
    @Description("Проверяет отсутствие способности у покемона")
    public void lackAbilityTest() {
        filters(withCustomTemplates());

        List<AbilityInfo> abilityInfoList = RequestMethods.getListAbilityInfo(MyStringUtils.NAME_PIDGEOTTO);

        boolean result = RequestMethods.containsAbility(abilityInfoList, MyStringUtils.ABILITY_RUN_AWAY);

        Assertions.assertFalse(result);
    }

    @Test
    @Description("Проверяет что вес первого покемона меньше чем у второго")
    public void disparitySizeTest() {
        filters(withCustomTemplates());

        boolean result = RequestMethods.getWeightPokemon(MyStringUtils.NAME_RATTATA) < RequestMethods.getWeightPokemon(MyStringUtils.NAME_PIDGEOTTO);

        Assertions.assertTrue(result);
    }
}
