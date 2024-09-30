import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.*;
import static com.codeborne.selenide.Selenide.*;

public class CardDeliveryTest {
    @BeforeAll
    static void setup() {
        // Настройки Selenide
        Configuration.baseUrl = "http://localhost:9999/";
        Configuration.headless = true; // Запускаем в headless режиме
        Configuration.browserSize = "1920x1080"; // Задаем размер окна для headless режима
        Configuration.timeout = 15000; // Таймаут для ожидания
    }

    @Test
    void shouldTestFormPositive() {
        // Открываем страницу
        open(Configuration.baseUrl);

        // Заполняем поля формы
        $("[data-test-id='city'] input").setValue("Санкт-Петербург");
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        String newDate = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id='date'] input").setValue(newDate);
        $("[data-test-id='name'] input").setValue("Татьянина Татьяна");
        $("[data-test-id='phone'] input").setValue("+79001234567");
        $("[data-test-id='agreement']").click();

        // Отправляем форму
        $(byText("Забронировать")).click();

        // Проверяем, что сообщение об успехе отображается
        $(withText("успешно")).shouldBe(visible, Duration.ofSeconds(15));
    }

    @Test
    void shouldTestFormComplexCity() {
        open(Configuration.baseUrl);
        $("[data-test-id='city'] input").setValue("Мо").shouldBe(visible, Duration.ofSeconds(3));
        $(byText("Москва")).click();
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        String newDate = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id='date'] input").setValue(newDate);
        $("[data-test-id='name'] input").setValue("Татьянина Татьяна");
        $("[data-test-id='phone'] input").setValue("+79001234567");
        $("[data-test-id='agreement']").click();
        $(byText("Забронировать")).click();
        $(withText("успешно")).shouldBe(visible, Duration.ofSeconds(15));
    }

    @Test
    void shouldTestFormComplexDate() {
        open(Configuration.baseUrl);
        $("[data-test-id='city'] input").setValue("Казань");
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        String newDate = LocalDate.now().plusDays(7).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $("[data-test-id='date'] input").setValue(newDate);
        $("[data-test-id='name'] input").setValue("Татьянина Татьяна");
        $("[data-test-id='phone'] input").setValue("+79001234567");
        $("[data-test-id='agreement']").click();
        $(byText("Забронировать")).click();
        $(withText("успешно")).shouldBe(visible, Duration.ofSeconds(15));
    }
}
