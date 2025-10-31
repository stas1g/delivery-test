package tests;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import data.DataGenerator;
import data.UserInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.*;

public class DeliveryTest {

    @BeforeEach
    void setUp() {
        Configuration.browserSize = "1280x800";
        open("http://localhost:9999");
    }

    @Test
    void shouldReplanMeeting() {
        UserInfo user = DataGenerator.generateUser();
        String firstDate = DataGenerator.generateDate(4);
        String secondDate = DataGenerator.generateDate(7);

        // Заполняем форму первый раз
        $("[data-test-id=city] input").setValue(user.getCity());
        $("[data-test-id=date] input").doubleClick().sendKeys(firstDate);
        $("[data-test-id=name] input").setValue(user.getName());
        $("[data-test-id=phone] input").setValue(user.getPhone());
        $("[data-test-id=agreement]").click();

        // Используем правильный селектор для кнопки
        $$("button").find(Condition.exactText("Запланировать")).click();

        // Проверяем успешное планирование
        $("[data-test-id=success-notification]")
                .shouldBe(Condition.visible)
                .shouldHave(Condition.text("Встреча успешно запланирована на " + firstDate));

        // Меняем дату
        $("[data-test-id=date] input").doubleClick().sendKeys(secondDate);
        $$("button").find(Condition.exactText("Запланировать")).click();

        // Проверяем предложение перепланировать
        $("[data-test-id=replan-notification]")
                .shouldBe(Condition.visible)
                .shouldHave(Condition.text("У вас уже запланирована встреча на другую дату. Перепланировать?"));

        // Подтверждаем перепланирование
        $("[data-test-id=replan-notification] .button").click();

        // Проверяем успешное перепланирование
        $("[data-test-id=success-notification]")
                .shouldBe(Condition.visible)
                .shouldHave(Condition.text("Встреча успешно запланирована на " + secondDate));
    }
}