import dto.ResultListInstanceDto;
import io.qameta.allure.Description;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import steps.RequestMethods;
import utils.MyStringUtils;

import java.util.List;

import static io.restassured.RestAssured.filters;
import static utils.CustomApiListener.withCustomTemplates;

public class ListPokemonsTest {

    @Test
    @Description("Проверяет ограничивается ли список")
    public void testExpectedListSize() {
        filters(withCustomTemplates());

        List<ResultListInstanceDto> resultList = RequestMethods.getResultListLimitDto(MyStringUtils.NUMBER_LIST_ITEMS);

        boolean result = (resultList.size() == MyStringUtils.NUMBER_LIST_ITEMS);

        Assertions.assertTrue(result);
    }

    @Test
    @Description("Проверяет наличие имени у каждого покемона из списка")
    public void testCheckPokeHaveName() {
        filters(withCustomTemplates());

        List<ResultListInstanceDto> resultListInstanceDto = RequestMethods.getResultListDto();

        boolean result = resultListInstanceDto.stream()
                .map(ResultListInstanceDto::getName)
                .allMatch(StringUtils::isNotEmpty);

        Assertions.assertTrue(result);
    }
}
