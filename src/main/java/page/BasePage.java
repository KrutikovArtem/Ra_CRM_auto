package page;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import locators.Locators;
import page.openEntities.OpenEntitiesPage;

import java.time.Duration;
import java.util.Arrays;

import static arrays.ArraysEntities.*;
import static java.lang.Thread.sleep;
import static locators.Locators.nameEntity;
import static supportPage.ColorButtons.convertRgbaToHex;
import static com.codeborne.selenide.Condition.visible;
import static locators.Locators.UNIVERSAL_TABLE;
import static web.MainPage.SERVICES;

public class BasePage {

    private final OpenEntitiesPage openEntitiesPage = new OpenEntitiesPage();

    // универсальный метод, который проверяет отображение кнопки, выбирает её и возвращает необходимый класс
    public <T extends BasePage> T openPage(SelenideElement pageElement, Class<T> pageClass) {
        tapToEntity(pageElement);
        try {
            return pageClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Ошибка создания page object", e);
        }
    }

    @Step("Поиск элемента сущности слева и тап по нему")
    public void tapToEntity(SelenideElement pageElement) {
        // преобразование селенид элемента в строку
        String pageElementLocator = pageElement.getSearchCriteria();
        // проходит по циклу и возвращает булевое значение при совпадении с сущностью из сервиса
        boolean isService = Arrays.stream(serviceEntities)
                .anyMatch(serviceElement ->
                        serviceElement.getSearchCriteria().equals(pageElementLocator)
                );
        System.out.println("Это сервис - " + isService);

        // если это сервис
        if (isService) {
            // получаем значение открты ли сервисы
            boolean statusService = openEntitiesPage.isButtonExpanded(SERVICES);
            System.out.println("Сервисы открыты - " + statusService);

            if (!statusService) {
                // если это сервис, то открывается меню "Сервисы"
                SERVICES.shouldBe(visible, Duration.ofSeconds(3)).click();
                System.out.println("Открыли меню 'Сервисы'");
            }
            // выбор сущности
            pageElement.shouldBe(visible).click();
        } else {
            pageElement.shouldBe(visible).click();
        }
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

        System.out.println("Название вкладки: " + title);

        // есть ли сущность в entitiesWithTabs (сущности с вкладками)
        boolean isEntityWithTab = Arrays.stream(entitiesWithTabs)
                .anyMatch(entity -> entity.trim().equalsIgnoreCase(title.trim())
                );

        System.out.println("Сущность имеет вкладки: " + isEntityWithTab);

        try {
            if (isEntityWithTab) {
                System.out.println("Изменяемая сущность: " + title);
                // для сущностей с табами используем variableTitle
                System.out.println(Locators.variableTitle(title)
                        .shouldBe(visible, Duration.ofSeconds(3))
                        .getText());
                return Locators.variableTitle(title)
                        .shouldBe(visible, Duration.ofSeconds(3))
                        .getText();
            } else {
                System.out.println("Не изменяемая сущность: " + title);
                // для остальных сущностей используется textTitle
                return Locators.textTitle(title)
                        .shouldBe(visible, Duration.ofSeconds(5))
                        .getText();
            }
        } catch (Exception e) {
            System.out.println("Элемент '" + title + "' не найден: " + e.getMessage());
            return "";
        }
    }

    @Step("Возвращает наличие таблицы")
    public static boolean tableIsVisible() {
        try {
            UNIVERSAL_TABLE.shouldBe(visible, Duration.ofSeconds(10));
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

        for (String entity : entitiesWithTabs) {
            if (title.equals(entity)) {
                if (title.equals("Заселение")) {
                    return openEntitiesPage.openTabs(tabsSettlements);
                } else if (title.equals("CSI Ипотека")) {
                    return openEntitiesPage.openTabs(tabsInfometrics);
                } else if (title.equals("Импорт ОН")) {
                    return openEntitiesPage.openTabs(tabsImport);
                } else if (title.equals("Календарь")) {
                    return openEntitiesPage.openTabs(tabsCalendar);
                } else if (title.equals("Уведомления")) {
                    return openEntitiesPage.openTabs(tabsNotifications);
                }
            }
        }

        System.out.println("=== Начало проверки вкладок '" + title + "' ===");

        if (title.equals("Условия реализации")) {
            return openEntitiesPage.checkInstallment(tabsImplTerms);
        }
        System.out.println("Заголовок '" + title + "' не поддерживается");
        return false;
    }
}
