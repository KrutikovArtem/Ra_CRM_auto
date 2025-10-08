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
    public <T extends BasePage> T openPage(String pageElement, Class<T> pageClass) {
        tapToEntity(pageElement);
        try {
            return pageClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Ошибка создания page object", e);
        }
    }

    @Step("Поиск элемента сущности слева и тап по нему")
    public void tapToEntity(String pageElement) {
        // преобразование селенид элемента в строку
        String pageElementLocator = nameEntity(pageElement).getSearchCriteria();
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
            nameEntity(pageElement).shouldBe(visible).click();
        } else {
            nameEntity(pageElement).shouldBe(visible).click();
        }
    }

    @Step("Проверка, что выбранная сущность выделена")
    public static String getColorTextEntity(String locators) {
        String textColorRGBA = nameEntity(locators).shouldBe(visible, Duration.ofSeconds(3)).getCssValue("color");
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
        boolean isEntityWithTab = Arrays.stream(notStaticEntityTabs)
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
                // для остальных сущностей используем textTitle
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
                    System.out.println(tabsInfometrics[0]);
                    return openEntitiesPage.openTabs(tabsInfometrics);
                } else if (title.equals("Импорт ОН")) {
                    return openEntitiesPage.openTabs(tabsImport);
                } else if (title.equals("Календарь")) {
                    return openEntitiesPage.openTabs(tabsCalendar);
                }
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
