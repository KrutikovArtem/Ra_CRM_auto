package page;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import locators.Locators;

import java.time.Duration;

import static SupportPage.ColorButtons.convertRgbaToHex;
import static com.codeborne.selenide.Condition.visible;
import static locators.Locators.UNIVERSAL_TABLE;

public class BasePage {

    // универсальный метод, который проверяет отображение кнопки, выбирает её и возвращает необходимый класс
    public <T extends BasePage> T openPage(SelenideElement pageElement, Class<T> pageClass) {
        tapToEntity(pageElement);
        try {
            return pageClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Ошибка создания page object", e);
        }
    }

    // поиск элемента и тап по нему
    public void tapToEntity(SelenideElement pageElement) {
        pageElement.shouldBe(visible).click();
    }

    @Step("Проверка, что сущность 'Дашборды' выделена")
    public String getColorTextEntity(SelenideElement locators) {
        String textColorRGBA = locators.shouldBe(visible).getCssValue("color");
        return convertRgbaToHex(textColorRGBA);
    }

    @Step("Получение текста кнопки создания")
    public String getCreateButtonText(String createButton) {
        if (createButton != null && !createButton.isBlank()) {
            return Locators.actionButton(createButton).shouldBe(visible).getText();
        }
        return "";
    }

    @Step("Получение заголовка выбранной сущности")
    public String getTitle(String title) {
        if (title != null && !title.isBlank()) {
            return Locators.textTitle(title).shouldBe(visible).getText();
        }
        return "";
    }

    @Step("Возвращает наличие таблицы")
    public boolean tableIsVisible() {
        try {
            UNIVERSAL_TABLE.shouldBe(visible, Duration.ofSeconds(2));
            return true;
        } catch (Throwable e) {
            return false;
        }
    }

}
