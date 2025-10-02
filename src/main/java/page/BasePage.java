package page;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import locators.Locators;
import page.openEntities.OpenEntitiesPage;

import java.time.Duration;
import java.util.Arrays;

import static arrays.ArraysEntities.*;
import static com.codeborne.selenide.Condition.attribute;
import static java.lang.Thread.sleep;
import static supportPage.ColorButtons.convertRgbaToHex;
import static com.codeborne.selenide.Condition.visible;
import static locators.Locators.UNIVERSAL_TABLE;
import static web.MainPage.SERVICES;

public class BasePage {

    private OpenEntitiesPage openEntitiesPage = new OpenEntitiesPage();

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
        // преобразование селенид элемента в строку
        String pageElementLocator = pageElement.getSearchCriteria();

        // проходит по циклу и возвращает булевое значение при совпадении с сущностью из сервиса
        boolean isService = Arrays.stream(serviceEntities)
                .anyMatch(serviceElement ->
                        serviceElement.getSearchCriteria().equals(pageElementLocator)
                );

        if (isService) {
            if (!openEntitiesPage.isButtonExpanded(SERVICES)) {
                System.out.println("Сервисы открыты - " + openEntitiesPage.isButtonExpanded(SERVICES));
                // если это сервис, то открывается меню "Сервисы"
                SERVICES.shouldBe(visible, Duration.ofSeconds(3)).click();
                System.out.println("Открыли меню 'Сервисы'");
                // Ждем пока меню раскроется (проверяем атрибут)
                SERVICES.shouldHave(attribute("aria-expanded", "true"), Duration.ofSeconds(2));
                pageElement.shouldBe(visible).click();
            } else {
                // выбор сущности
                pageElement.shouldBe(visible).click();
            }
        }

        pageElement.shouldBe(visible).click();
    }

    @Step("Проверка, что выбранная сущность выделена")
    public static String getColorTextEntity(SelenideElement locators) {
        String textColorRGBA = locators.shouldBe(visible, Duration.ofSeconds(3)).getCssValue("color");
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
        if (title == null || title.isBlank()) {
            return "";
        }

        for (int i = 0; i < entitiesWithTabs.length; i++) {
            if (title.equals(entitiesWithTabs[i])) {
                return entitiesWithTabs[i];
            }
        }

        // поиск через локатор с таймаутом
        try {
            return Locators.textTitle(title)
                    .shouldBe(visible, Duration.ofSeconds(5))
                    .getText();
        } catch (Exception e) {
            System.out.println("Элемент не найден: " + e.getMessage());
            return "";
        }
    }

    @Step("Возвращает наличие таблицы")
    public static boolean tableIsVisible() {
        try {
            UNIVERSAL_TABLE.shouldBe(visible, Duration.ofSeconds(5));
            return true;
        } catch (Throwable e) {
            return false;
        }
    }

    @Step("Возвращает открытие блоков в сущности")
    public boolean tabsIsVisible(String title) {
        if (title == null || title.isBlank()) {
            System.out.println("Заголовок пустой или null");
            return false;
        }

        for (int i = 0; i < entitiesWithTabs.length - 1; i++) {
            if (title.equals(entitiesWithTabs[i])) {
                return openEntitiesPage.openTabs(tabsSettlements);
            }
        }

        if (title.equals("Условия реализации")) {
            System.out.println("=== Начало проверки вкладок '" + title + "' ===");
            // проверка основной вкладки
            boolean mainBlocksWork = openEntitiesPage.openBlocks(tabsImplTerms);
            System.out.println("Основные вкладки работают: " + mainBlocksWork);

            if (!mainBlocksWork) {
                System.err.println("Основные вкладки не работают!");
                return false;
            }

            // переключение в архив
            System.out.println("=== Переключение в Архив ===");
            try {
                SelenideElement archiveTab = Locators.blocksTitle("Архив");
                archiveTab.shouldBe(visible, Duration.ofSeconds(5)).click();
                System.out.println("Успешно переключились в Архив");
                // ожидание загрузки архива
                sleep(2000);
            } catch (Exception e) {
                System.err.println("Ошибка переключения в архив: " + e.getMessage());
                return false;
            }
            // проверяка блока "Архив"
            System.out.println("=== Проверка вкладок в Архиве ===");
            boolean archiveBlocksWork = openEntitiesPage.openBlocks(tabsImplTerms);
            System.out.println("Вкладки в архиве работают: " + archiveBlocksWork);
            return archiveBlocksWork;
        }
        System.out.println("Заголовок '" + title + "' не поддерживается");
        return false;
    }
}
