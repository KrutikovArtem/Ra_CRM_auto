package locators;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import static com.codeborne.selenide.Selenide.$x;

public class Locators {

    // локатор кнопки действия в сущности
    public static SelenideElement actionButton(String locator) {
        return $x(".//span[text() = '" + locator + "']");
    }

    // локатор заголовка сущности
    public static SelenideElement textTitle(String locator) {
        return $x(".//span[text() = '" + locator + "']");
    }

    // локатор заголовка сущности
    public static SelenideElement nameEntity(String locator) {
        return $x(".//a[text() = '" + locator + "']");
    }


    // локатор универсальной таблицы
    public static SelenideElement UNIVERSAL_TABLE = $x(".//table[@class = 'table-pQ5hnL table-sticky-header-ITpuV0']");
}
