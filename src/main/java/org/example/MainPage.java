package org.example;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;

public class MainPage {

    // Znalezienie elementów na stronie
    public SelenideElement devicesMenu = $(By.id("devicesMenu"));
    public SelenideElement noSubscriptionLink = $(By.linkText("Bez abonamentu"));

    // Przejdź na stronę urządzeń
    public void goToDevicesPage() {
        devicesMenu.hover();  // Przechodzenie po menu
        noSubscriptionLink.click();
    }
}
