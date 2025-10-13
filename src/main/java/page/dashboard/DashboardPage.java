package page.dashboard;

import com.codeborne.selenide.SelenideElement;
import page.BasePage;
import static com.codeborne.selenide.Selenide.$x;

public class DashboardPage extends BasePage {

    public static final SelenideElement CREATE_DASHBOARD = $x(".//span[text() = 'Создать дашборд']");

    @Override
    public String getCreateButtonText(String createButton) {
        return super.getCreateButtonText(createButton);
    }
}
