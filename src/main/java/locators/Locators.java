package locators;

import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Selenide.$x;

public class Locators {

    // локатор id записи в таблице
    public static final SelenideElement ID_ON_TABLE = $x(".//tr[@class = 'clickable-JNIQAI']//td[@data-cell-name='id']/div");

    // локатор наименования записи в таблице
    public static final SelenideElement NAME_ON_TABLE = $x(".//tr[@class = 'clickable-JNIQAI']//td[@data-cell-name='name']/div");

    // локатор универсальной таблицы
    public static final SelenideElement UNIVERSAL_TABLE = $x(".//table[@class = 'table-pQ5hnL table-sticky-header-ITpuV0']");

    // локатор кнопки действия в сущности
    public static SelenideElement actionButton(String locator) {
        return $x(".//span[text() = '" + locator + "']");
    }

    // локатор заголовка сущности
    public static SelenideElement textTitle(String locator) {
        return $x(".//span[text() = '" + locator + "']");
    }

    // локатор сущности слева СРМ
    public static SelenideElement nameEntity(String locator) {
        return $x(".//a[text() = '" + locator + "']");
    }

    // локатор блоков в сущности
    public static SelenideElement blocksTitle(String locator) {
        return $x(".//span[text() = '" + locator + "']");
    }

    // локатор вкладки в сущности
    public static SelenideElement tabsInEntity(String locator) {
        return $x(".//div[text() = '" + locator + "']");
    }
}
