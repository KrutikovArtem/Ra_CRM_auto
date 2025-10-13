package page.openEntities;

import com.codeborne.selenide.SelenideElement;
import constants.Constants;
import locators.Locators;
import java.time.Duration;
import static com.codeborne.selenide.Condition.visible;
import static constants.Constants.TAP_SELECTED_FLAG;
import static java.lang.Thread.sleep;
import static page.BasePage.getColorTextEntity;
import static page.BasePage.tableIsVisible;

public class OpenEntitiesPage {

    // открытие блоков в сущности
    public boolean openBlocks(SelenideElement[] tabs) {
        try {
            for (int i = 0; i < tabs.length; i++) {
                // проверяем текущий блок
                tabs[i].shouldBe(visible, Duration.ofSeconds(5)).click(); // клик по первому блоку

                boolean isActive = blockIsVisible(tabs[i]);
                // если блок активен и загружается таблица, переключаемся на следующую
                if (isActive) {
                    System.out.println("Вкладка " + tabs[i] + " активна");

                    if (i < tabs.length - 1) {
                        SelenideElement nextLocator = tabs[i + 1];
                        nextLocator.shouldBe(visible, Duration.ofSeconds(5)).click();
                        System.out.println("Успешно переключено в блок: " + tabs[i + 1]);
                        sleep(500);
                    } else {
                        // это последний блок - возвращает результат
                        boolean lastTabActive = blockIsVisible(tabs[i]);
                        System.out.println("Последний блок активен: " + lastTabActive);
                        return lastTabActive;
                    }
                } else {
                    System.out.println("Блок " + tabs[i] + " не активен");
                    return false;
                }
            }
        } catch (Exception e) {
            System.err.println("Ошибка при переключении блоков: " + e.getMessage());
        }
        return true;
    }

    // открытие вкладок сущности
    public boolean openTabs(String[] tabs) {
        try {
            for (int i = 0; i < tabs.length; i++) {
                // проверка текущей вкладки
                SelenideElement currentTab = Locators.tabsInEntity(tabs[i]).shouldBe(visible);
                // если вкладка активна, переключение на следующую
                if (Boolean.TRUE.equals(tabSelected(currentTab, TAP_SELECTED_FLAG))) {
                    if (i < tabs.length - 1) {
                        SelenideElement nextLocator = Locators.tabsInEntity(tabs[i + 1]);

                        /// === ДОБАВИТЬ ПРОВЕРКУ НАЛИЧИЯ ТАБЛИЦЫ ИЛИ ДРУГОГО ПРИЗНАКА !!! ===

                        nextLocator.shouldBe(visible).click();
                        System.out.println("Успешно переключено на вкладку: " + tabs[i + 1]);
                    }
                    // проверка, достигли ли последней вкладки
                    if (i == tabs.length - 1) {
                        System.out.println(tabSelected(currentTab, TAP_SELECTED_FLAG));
                        return tabSelected(currentTab, TAP_SELECTED_FLAG);
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Критическая ошибка: " + e.getMessage());
        }
        return false;
    }

    // метод, который проверяет выделен ли выбранный блок и загружена ли таблица
    private Boolean blockIsVisible(SelenideElement locators) {
        String currentColor = getColorTextEntity(locators);
        System.out.println(currentColor);
        if (Constants.TEXT_COLOR_BLOCK.equals(currentColor)) {
            return tableIsVisible();
        }
        return false;
    }

    // проверка, что блок выбран (присутствует нижнее подчёркивание выбранного значения)
    public static Boolean tabSelected(SelenideElement element, String selectName) {
        return Boolean.parseBoolean(element.getAttribute(selectName));
    }

    // метод возвращает значение выбранной или нет сущности по признаку aria-expanded (например Сервисы)
    public boolean isButtonExpanded(SelenideElement button) {
        return "true".equals(button.getAttribute("aria-expanded"));
    }

    // метод проверки сущности "Условия реализации"
    public boolean checkInstallment(SelenideElement[] tabsArray) {
        // проверка основной вкладки
        boolean mainBlocksWork = openBlocks(tabsArray);
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
            System.err.println("Ошибка переключения в архиве: " + e.getMessage());
            return false;
        }
        // проверка блока "Архив"
        boolean archiveBlocksWork = openBlocks(tabsArray);
        System.out.println("Вкладки в архиве работают: " + archiveBlocksWork);
        return archiveBlocksWork;
    }
}
