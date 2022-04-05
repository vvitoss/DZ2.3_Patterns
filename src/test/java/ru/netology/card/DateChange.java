package ru.netology.card;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import lombok.Data;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import ru.netology.card.utilits.DataGenerator;
import ru.netology.card.utilits.RegistrationInfo;

import java.time.Duration;


import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

@Data
public class DateChange {
    private int DAY_3 = 3;
    private int DAY_7 = 7;
    RegistrationInfo info = DataGenerator.getUser();
    DataGenerator data = new DataGenerator();


    @Test
    void happyPathResendWithChangedDate() {
        Configuration.holdBrowserOpen = true;
        Selenide.open("http://localhost:9999/");
        $("[data-test-id='city'] input").val(info.getCity());
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").val(data.generateDate(this.DAY_3));
        $("[data-test-id='name'] input").val(info.getName());
        $("[data-test-id='phone'] input").val(info.getPhone());
        $("[data-test-id='agreement']").click();
        $("div .button").click();
        $("[data-test-id='success-notification'] .notification__title").should(visible, Duration.ofSeconds(15));
        $("[data-test-id='success-notification'] .notification__content")
                .should(Condition.text("Встреча успешно запланирована на " + data.generateDate(this.DAY_3)), Duration.ofSeconds(15));

        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").val(data.generateDate(this.DAY_7));
        $("div .button").click();
        $("[data-test-id='replan-notification'] .notification__content")
                .should(Condition.text("У вас уже запланирована встреча на другую дату. Перепланировать?"));
        $("[data-test-id='replan-notification'] .button").click();
        $("[data-test-id='success-notification'] .notification__content")
                .should(Condition.text("Встреча успешно запланирована на " + data.generateDate(this.DAY_7)), Duration.ofSeconds(15));

    }


    @Test
    void nameWithYo() {
        DataGenerator yo = new DataGenerator();
        yo.searchNameYo();
        RegistrationInfo info2 = DataGenerator.getUserYo();
        Configuration.holdBrowserOpen = true;

        Selenide.open("http://localhost:9999/");
        $("[data-test-id='city'] input").val(info2.getCity());
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $("[data-test-id='date'] input").val(data.generateDate(this.DAY_3));
        $("[data-test-id='name'] input").val(info2.getName());
        $("[data-test-id='phone'] input").val(info2.getPhone());
        $("[data-test-id='agreement']").click();
        $("div .button").click();
        $("[data-test-id='success-notification'] .notification__title").should(visible, Duration.ofSeconds(15));
        $("[data-test-id='success-notification'] .notification__content")
                .should(Condition.text("Встреча успешно запланирована на " + data.generateDate(this.DAY_3)), Duration.ofSeconds(15));

    }


}
