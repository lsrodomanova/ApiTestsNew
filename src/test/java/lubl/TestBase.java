package lubl;


import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import config.demowebshop.AppConfig;
import config.demowebshop.RemoteOwner;
import helpers.AllureAttachments;
import io.qameta.allure.junit5.AllureJunit5;
import io.qameta.allure.selenide.AllureSelenide;
import io.restassured.RestAssured;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;

import static com.codeborne.selenide.Selenide.closeWebDriver;


public class TestBase {

    @BeforeAll
    static void beforeAll() {
        AppConfig appConfig = ConfigFactory.create(AppConfig.class);
        Configuration.baseUrl = appConfig.webUrl();
        RestAssured.baseURI = appConfig.apiUrl();
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());
        RemoteOwner confRemote = ConfigFactory.create(RemoteOwner.class);

        String propertyBrowserSize = System.getProperty("browserSize", "1980x1024"),
                propertyRemoteUrl = System.getProperty("remoteUrl", confRemote.url());


        Configuration.browserSize = propertyBrowserSize;
        Configuration.remote = propertyRemoteUrl;
    }


    @AfterEach
    public void afterEach() {

        AllureAttachments.screenshotAs("Final screenshot");;
        AllureAttachments.pageSource();
        AllureAttachments.browserConsoleLogs();
        AllureAttachments.addVideo();

        closeWebDriver();

    }
}
