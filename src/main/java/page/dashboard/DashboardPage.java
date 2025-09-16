package page.dashboard;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Step;
import page.BasePage;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;

public class DashboardPage extends BasePage {

    private static final SelenideElement CREATE_DASHBOARD = $x(".//span[text() = 'Создать дашборд']");

    private static final SelenideElement DASHBOARDS = $x(".//a[text() = 'Дашборды']");

    public String getCreateDashboardText() {
        return CREATE_DASHBOARD.shouldBe(visible).getText();
    }

    @Step("Проверка, что сущность 'Дашборды' выделена")
    public String getColorTextEntity() {
        String textColorRGBA = DASHBOARDS.shouldBe(visible).getCssValue("color");
        return convertRgbaToHex(textColorRGBA);
    }

    // конвертер цвета из Hex в rgba
    public String convertRgbaToHex(String rgba) {
        if (rgba.startsWith("rgba")) {
            String[] values = rgba.replace("rgba(", "").replace(")", "").split(",");
            int r = Integer.parseInt(values[0].trim());
            int g = Integer.parseInt(values[1].trim());
            int b = Integer.parseInt(values[2].trim());
            return String.format("#%02x%02x%02x", r, g, b);
        }
        return rgba;
    }
}
