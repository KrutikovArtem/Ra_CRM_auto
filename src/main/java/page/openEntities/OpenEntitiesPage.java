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

    public boolean openBlocks(String[] tabs) {
            try {
                for (int i = 0; i < tabs.length; i++) {
                    // проверяем текущую вкладку
                    SelenideElement currentTab = Locators.blocksTitle(tabs[i]).shouldBe(visible, Duration.ofSeconds(5));
                    currentTab.click(); // клик по первому блоку

                    boolean isActive = blockIsVisible(currentTab);
                    // если вкладка активна и загружается таблица, переключаемся на следующую
                    if (isActive) {
                        System.out.println("Вкладка " + tabs[i] + " активна");

                        if (i < tabs.length - 1) {
                            SelenideElement nextLocator = Locators.blocksTitle(tabs[i + 1]);
                            nextLocator.shouldBe(visible, Duration.ofSeconds(5)).click();
                            System.out.println("Успешно переключено на вкладку: " + tabs[i + 1]);
                            sleep(1000);
                        } else {
                            // это последняя вкладка - возвращает результат
                            boolean lastTabActive = blockIsVisible(currentTab);
                            System.out.println("Последняя вкладка активна: " + lastTabActive);
                            return lastTabActive;
                        }
                    } else {
                        System.out.println("Вкладка " + tabs[i] + " не активна");
                        return false;
                    }
                }
            } catch (Exception e) {
                System.err.println("Ошибка при переключении блоков: " + e.getMessage());
            }
        return true;
    }

    public boolean openTabs(String[] tabs, String tabSelection) {
        if (tabSelection.equals("Условия реализации")) {
            try {
                for (int i = 0; i < tabs.length; i++) {
                    // проверяем текущую вкладку
                    SelenideElement currentTab = Locators.blocksTitle(tabs[i]).shouldBe(visible);
                    // если вкладка активна, переключаемся на следующую
                    if (Boolean.TRUE.equals(tabSelected(currentTab, TAP_SELECTED_FLAG))) {
                        if (i < tabs.length - 1) {
                            SelenideElement nextLocator = Locators.blocksTitle(tabs[i + 1]);
                            nextLocator.shouldBe(visible).click();
                            System.out.println("Успешно переключено на вкладку: " + tabs[i + 1]);
                        }
                        // проверяем, достигли ли последней вкладки
                        if (i == tabs.length - 1) {
                            System.out.println(tabSelected(currentTab, TAP_SELECTED_FLAG));
                            return tabSelected(currentTab, TAP_SELECTED_FLAG);
                        }
                    }
                }
            } catch (Exception e) {
                System.err.println("Критическая ошибка: " + e.getMessage());
            }
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
}
