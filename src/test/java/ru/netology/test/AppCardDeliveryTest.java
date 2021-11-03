package ru.netology.test;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import ru.netology.data.DataGenerator;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.open;
import static ru.netology.data.DataGenerator.generateDate;

public class AppCardDeliveryTest {

        @BeforeEach
        void setup() {
                open("http://localhost:9999");
        }

        @Test
        void shouldReplanMeeting() {
                $("[placeholder='Город']").setValue(DataGenerator.generateCity());
                $("[placeholder='Дата встречи']").doubleClick().sendKeys(Keys.BACK_SPACE);
                $("[placeholder='Дата встречи']").setValue(generateDate(4));
                $("[name='name']").setValue(DataGenerator.generateName());
                $("[name='phone']").setValue(DataGenerator.generatePhone());
                $("[data-test-id=agreement]").click();
                $$("button").find(exactText("Запланировать")).click();
                $(byText("Успешно!")).shouldBe(visible, Duration.ofSeconds(15));
                $(withText("Встреча успешно запланирована")).shouldHave(text(generateDate(4)));
                $("[placeholder='Дата встречи']").doubleClick().sendKeys(Keys.BACK_SPACE);
                $("[placeholder='Дата встречи']").setValue(generateDate(5));
                $$("button").find(exactText("Запланировать")).click();
                $$("button").find(exactText("Перепланировать")).click();
                $(withText("Встреча успешно запланирована")).shouldHave(text(generateDate(5)));
        }
}