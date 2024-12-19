package org.example;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;

public class MainPage {

    public SelenideElement devicesMenu = $(By.id("devicesMenu"));
    public SelenideElement noSubscriptionLink = $(By.linkText("Bez abonamentu"));

    public void goToDevicesPage() {
        devicesMenu.hover();
        noSubscriptionLink.click();
    }
}
