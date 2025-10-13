package browser;

import com.codeborne.selenide.Configuration;
import io.restassured.RestAssured;

// конфигурация браузера
public class ConfigurationBrowser {

    private final String authURL;

    public ConfigurationBrowser(String authURL) {
        this.authURL = authURL;
    }

    public void configuration() {
        // отключение сертификата
        RestAssured.config = RestAssured.config()
                .sslConfig(io.restassured.config.SSLConfig.sslConfig().relaxedHTTPSValidation());
        RestAssured.baseURI = this.authURL;
        Configuration.browser = "chrome";
        //Configuration.driverManagerEnabled = true;
        Configuration.browserSize = "1920x1080";
        Configuration.headless = false;
        Configuration.timeout = 10000;
        Configuration.pageLoadStrategy = "normal";
    }
}
