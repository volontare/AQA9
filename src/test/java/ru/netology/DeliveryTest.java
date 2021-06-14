package ru.netology;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import lombok.val;
import org.junit.jupiter.api.*;


import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.openqa.selenium.By.cssSelector;
import static org.openqa.selenium.Keys.DELETE;
import static ru.netology.DataGenerator.generateDate;

public class DeliveryTest {

    private final String date = generateDate(3);
    private final String anotherDate = generateDate(4);
    private final String dateUnderDueDate = generateDate(2);

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
        $(cssSelector("[data-test-id=date] .input__control")).doubleClick().sendKeys(DELETE);
    }

    @AfterEach
    void tearDown() {
        closeWindow();
    }

    @Test
    void shouldTestOrderWithValidData() {
        val validUser = DataGenerator.Registration.generateUser("ru");
        $(cssSelector("[data-test-id=city] .input__control")).setValue(validUser.getCity());
        $(cssSelector("[data-test-id=date] .input__control")).sendKeys(date);
        $(cssSelector("[data-test-id=name] .input__control")).setValue(validUser.getName());
        $(cssSelector("[data-test-id=phone] .input__control")).setValue(validUser.getPhone());
        $("[data-test-id=agreement] .checkbox__box").click();
        $$("button").find(Condition.exactText("Запланировать")).click();
        $(".notification__content").waitUntil(Condition.visible,
                1500).shouldHave(exactText("Встреча успешно запланирована на " + date));
    }

    @Test
    void shouldTestOrderWithValidDataAndAnotherMeetingDate() {
        val validUser = DataGenerator.Registration.generateUser("ru");
        $(cssSelector("[data-test-id=city] .input__control")).setValue(validUser.getCity());
        $(cssSelector("[data-test-id=date] .input__control")).sendKeys(date);
        $(cssSelector("[data-test-id=name] .input__control")).setValue(validUser.getName());
        $(cssSelector("[data-test-id=phone] .input__control")).setValue(validUser.getPhone());
        $("[data-test-id=agreement] .checkbox__box").click();
        $$("button").find(Condition.exactText("Запланировать")).click();
        $(".notification__content").waitUntil(Condition.visible,
                1500).shouldHave(exactText("Встреча успешно запланирована на " + date));
        $(cssSelector("[data-test-id=date] .input__control")).doubleClick().sendKeys(DELETE, anotherDate);
        $$("button").find(Condition.exactText("Запланировать")).click();
        $$(".button__text").find(exactText("Перепланировать")).click();
        $("[data-test-id=success-notification]").waitUntil(Condition.visible,
                1500).shouldHave(exactText("Успешно! Встреча успешно запланирована на " + anotherDate));
    }

    @Test
    void shouldTestOrderWhenSameMeetingDate() {
        val validUser = DataGenerator.Registration.generateUser("ru");
        $(cssSelector("[data-test-id=city] .input__control")).setValue(validUser.getCity());
        $(cssSelector("[data-test-id=date] .input__control")).sendKeys(date);
        $(cssSelector("[data-test-id=name] .input__control")).setValue(validUser.getName());
        $(cssSelector("[data-test-id=phone] .input__control")).setValue(validUser.getPhone());
        $("[data-test-id=agreement] .checkbox__box").click();
        $$("button").find(Condition.exactText("Запланировать")).click();
        $(".notification__content").waitUntil(Condition.visible,
                1500).shouldHave(exactText("Встреча успешно запланирована на " + date));
        $(cssSelector("[data-test-id=date] .input__control")).doubleClick().sendKeys(DELETE, date);
        $$("button").find(Condition.exactText("Запланировать")).click();
        $$(".button__text").find(exactText("Перепланировать")).click();
        $(withText("Перепланирование на указанную дату не возможно")).shouldHave(Condition.visible);
    }

    @Test
    void shouldTestOrderWhenInvalidCity() {
        val invalidUser = DataGenerator.InvalidRegistrationCity.generateInvalidUser("ru");
        $(cssSelector("[data-test-id=city] .input__control")).setValue(invalidUser.getCity());
        $(cssSelector("[data-test-id=date] .input__control")).sendKeys(date);
        $(cssSelector("[data-test-id=name] .input__control")).setValue(invalidUser.getName());
        $(cssSelector("[data-test-id=phone] .input__control")).setValue(invalidUser.getPhone());
        $("[data-test-id=agreement] .checkbox__box").click();
        $$("button").find(Condition.exactText("Запланировать")).click();
        $(withText("Доставка в выбранный город недоступна")).shouldHave(Condition.visible);
    }

    @Test
    void shouldTestOrderWhenNoCity() {
        val validUser = DataGenerator.Registration.generateUser("ru");
        $(cssSelector("[data-test-id=date] .input__control")).sendKeys(date);
        $(cssSelector("[data-test-id=name] .input__control")).setValue(validUser.getName());
        $(cssSelector("[data-test-id=phone] .input__control")).setValue(validUser.getPhone());
        $("[data-test-id=agreement] .checkbox__box").click();
        $$("button").find(Condition.exactText("Запланировать")).click();
        $(withText("Поле обязательно для заполнения")).shouldHave(Condition.visible);
    }

    @Test
    void shouldTestOrderValidNameWithDash() {
        val validUser = DataGenerator.ValidNameRegistrationWithDash.generateValidUser("ru");
        $(cssSelector("[data-test-id=city] .input__control")).setValue(validUser.getCity());
        $(cssSelector("[data-test-id=date] .input__control")).sendKeys(date);
        $(cssSelector("[data-test-id=name] .input__control")).setValue(validUser.getName());
        $(cssSelector("[data-test-id=phone] .input__control")).setValue(validUser.getPhone());
        $("[data-test-id=agreement] .checkbox__box").click();
        $$("button").find(Condition.exactText("Запланировать")).click();
        $(".notification__content").waitUntil(Condition.visible,
                1500).shouldHave(exactText("Встреча успешно запланирована на " + date));
    }

    @Test
    void shouldTestOrderWhenInvalidName() {
        val invalidUser = DataGenerator.InvalidRegistrationName.generateInvalidUser("ru");
        $(cssSelector("[data-test-id=city] .input__control")).setValue(invalidUser.getCity());
        $(cssSelector("[data-test-id=date] .input__control")).sendKeys(date);
        $(cssSelector("[data-test-id=name] .input__control")).setValue(invalidUser.getName());
        $(cssSelector("[data-test-id=phone] .input__control")).setValue(invalidUser.getPhone());
        $("[data-test-id=agreement] .checkbox__box").click();
        $$("button").find(Condition.exactText("Запланировать")).click();
        $(withText("Имя и Фамилия указаны неверно")).shouldHave(Condition.visible);
    }

    @Test
    void shouldTestOrderWhenNoName() {
        val validUser = DataGenerator.Registration.generateUser("ru");
        $(cssSelector("[data-test-id=city] .input__control")).setValue(validUser.getCity());
        $(cssSelector("[data-test-id=date] .input__control")).sendKeys(date);
        $(cssSelector("[data-test-id=phone] .input__control")).setValue(validUser.getPhone());
        $("[data-test-id=agreement] .checkbox__box").click();
        $$("button").find(Condition.exactText("Запланировать")).click();
        $(withText("Поле обязательно для заполнения")).shouldHave(Condition.visible);
    }

    @Test
    void shouldTestOrderWhenNameWithLetterYo() {
        val invalidUser = DataGenerator.ValidNameRegistrationWithLetterYo.generateInvalidUser("ru");
        $(cssSelector("[data-test-id=city] .input__control")).setValue(invalidUser.getCity());
        $(cssSelector("[data-test-id=date] .input__control")).sendKeys(date);
        $(cssSelector("[data-test-id=name] .input__control")).setValue(invalidUser.getName());
        $(cssSelector("[data-test-id=phone] .input__control")).setValue(invalidUser.getPhone());
        $("[data-test-id=agreement] .checkbox__box").click();
        $$("button").find(Condition.exactText("Запланировать")).click();
        $(".notification__content").waitUntil(Condition.visible,
                1500).shouldHave(exactText("Встреча успешно запланирована на " + date));
    }

    @Test
    void shouldTestOrderWhenNameWithInvalidSymbol() {
        val invalidUser = DataGenerator.InvalidNameRegistrationWithInvalidSymbol.generateInvalidUser("ru");
        $(cssSelector("[data-test-id=city] .input__control")).setValue(invalidUser.getCity());
        $(cssSelector("[data-test-id=date] .input__control")).sendKeys(date);
        $(cssSelector("[data-test-id=name] .input__control")).setValue(invalidUser.getName());
        $(cssSelector("[data-test-id=phone] .input__control")).setValue(invalidUser.getPhone());
        $("[data-test-id=agreement] .checkbox__box").click();
        $$("button").find(Condition.exactText("Запланировать")).click();
        $(withText("Имя и Фамилия указаны неверно. Допустимы только русские буквы, пробелы и дефисы")).shouldHave(Condition.visible);
    }

    @Test
    void shouldTestOrderWhenNameWhenInvalidStartNumber() {
        val invalidUser = DataGenerator.InvalidRegistrationPhoneWithIncorrectStartNumber.generateInvalidUser("ru");
        $(cssSelector("[data-test-id=city] .input__control")).setValue(invalidUser.getCity());
        $(cssSelector("[data-test-id=date] .input__control")).sendKeys(date);
        $(cssSelector("[data-test-id=name] .input__control")).setValue(invalidUser.getName());
        $(cssSelector("[data-test-id=phone] .input__control")).setValue(invalidUser.getPhone());
        $("[data-test-id=agreement] .checkbox__box").click();
        $$("button").find(Condition.exactText("Запланировать")).click();
        $(withText("Номер телефона должен начинаться с +7")).shouldHave(Condition.visible);
    }

    @Test
    void shouldTestOrderWhenNameWhenDeficiencyNumbers() {
        val invalidUser = DataGenerator.InvalidRegistrationPhoneWhenDeficiencyNumbers.generateInvalidUser("ru");
        $(cssSelector("[data-test-id=city] .input__control")).setValue(invalidUser.getCity());
        $(cssSelector("[data-test-id=date] .input__control")).sendKeys(date);
        $(cssSelector("[data-test-id=name] .input__control")).setValue(invalidUser.getName());
        $(cssSelector("[data-test-id=phone] .input__control")).setValue(invalidUser.getPhone());
        $("[data-test-id=agreement] .checkbox__box").click();
        $$("button").find(Condition.exactText("Запланировать")).click();
        $(withText("Поле должно содержать 11 цифр")).shouldHave(Condition.visible);
    }

    @Test
    void shouldTestOrderWhenNoTel() {
        val validUser = DataGenerator.Registration.generateUser("ru");
        $(cssSelector("[data-test-id=city] .input__control")).setValue(validUser.getCity());
        $(cssSelector("[data-test-id=date] .input__control")).sendKeys(date);
        $(cssSelector("[data-test-id=phone] .input__control")).setValue(validUser.getPhone());
        $("[data-test-id=agreement] .checkbox__box").click();
        $$("button").find(Condition.exactText("Запланировать")).click();
        $(withText("Поле обязательно для заполнения")).shouldHave(Condition.visible);
    }

    @Test
    void shouldTestOrderWhenNoAgreement() {
        val validUser = DataGenerator.Registration.generateUser("ru");
        $(cssSelector("[data-test-id=city] .input__control")).setValue(validUser.getCity());
        $(cssSelector("[data-test-id=date] .input__control")).sendKeys(date);
        $(cssSelector("[data-test-id=name] .input__control")).setValue(validUser.getName());
        $(cssSelector("[data-test-id=phone] .input__control")).setValue(validUser.getPhone());
        $("[data-test-id=agreement] .checkbox__box").click();
        $$("button").find(Condition.exactText("Запланировать")).click();
        String text = $(".checkbox__text").getCssValue("color");
        assertEquals("rgba(11, 31, 53, 0.95)", text);
    }

    @Test
    void shouldTestOrderWhenMeetingDateUnder3() {
        val validUser = DataGenerator.Registration.generateUser("ru");
        $(cssSelector("[data-test-id=city] .input__control")).setValue(validUser.getCity());
        $(cssSelector("[data-test-id=date] .input__control")).sendKeys(dateUnderDueDate);
        $(cssSelector("[data-test-id=name] .input__control")).setValue(validUser.getName());
        $(cssSelector("[data-test-id=phone] .input__control")).setValue(validUser.getPhone());
        $("[data-test-id=agreement] .checkbox__box").click();
        $$("button").find(Condition.exactText("Запланировать")).click();
        $(withText("Заказ на выбранную дату невозможен")).shouldHave(Condition.visible);
    }
}
