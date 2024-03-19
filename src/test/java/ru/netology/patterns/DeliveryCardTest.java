package ru.netology.patterns;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selectors;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;


import java.time.Duration;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.SetValueOptions.withText;
import static ru.netology.patterns.DataGenerator.generateDate;

public class DeliveryCardTest {

    @Test
    void shouldChangeDateAndOrderCard() {
        open("http://localhost:9999");

        DataGenerator.UserInfo validUser = DataGenerator.Registration.generateUser("ru");
        $("[data-test-id=city] input").setValue(validUser.getCity());

        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        String planningDate = generateDate(3);
        $("[data-test-id=date] input").setValue(planningDate);
        $("[data-test-id=name] input").setValue(validUser.getName());
        $("[data-test-id=phone] input").setValue(validUser.getPhone());
        $("[data-test-id=agreement]").click();
        $("button.button").click();
        $("[data-test-id=success-notification] .notification__content")
                .shouldBe(Condition.visible, Duration.ofSeconds(15))
                .shouldHave(Condition.exactText("Встреча успешно запланирована на " + planningDate));
        $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        String newPlanningDate = generateDate(5);
        $("[data-test-id=date] input").setValue(newPlanningDate);
        $("button.button").click();
        $(Selectors.withText("Перепланировать")).shouldBe(Condition.visible);
        $("[data-test-id=replan-notification] button").click();
        $("[data-test-id=success-notification] .notification__content")
                .shouldBe(Condition.visible)
                .shouldHave(Condition.exactText("Встреча успешно запланирована на " + newPlanningDate));
    }
}