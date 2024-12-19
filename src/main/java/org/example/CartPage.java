package org.example;

import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.Condition;

import static com.codeborne.selenide.Selenide.$;

public class CartPage {
    private final SelenideElement basketHeader = $("h1.basketHeaderText");
    private final SelenideElement totalMonthlyElement = $("span[data-qa='BKT_TotalMonthly']");
    private final SelenideElement totalUpFrontElement = $("span[data-qa='BKT_TotalupFront']");

    public void verifyCartPageVisible() {
        String expectedText = "Twój koszyk";
        basketHeader.shouldBe(Condition.visible);
        boolean isTextPresent = basketHeader.text().contains(expectedText);
        if (!isTextPresent) {
            throw new AssertionError("Tekst '" + expectedText + "' nie został znaleziony w headerze strony!");
        }
    }

    public void verifyPricesMatch(int expectedStart, int expectedRata) {
        String totalMonthlyText = totalMonthlyElement.text();
        String totalUpFrontText = totalUpFrontElement.text();

        int totalMonthly = Integer.parseInt(totalMonthlyText.replaceAll("[^0-9]", ""));
        int totalUpFront = Integer.parseInt(totalUpFrontText.replaceAll("[^0-9]", ""));

        if (totalMonthly != expectedRata) {
            throw new AssertionError("Kwota miesięczna się nie zgadza! Oczekiwano: " + expectedRata + ", otrzymano: " + totalMonthly);
        }

        if (totalUpFront != expectedStart) {
            throw new AssertionError("Kwota upfront się nie zgadza! Oczekiwano: " + expectedStart + ", otrzymano: " + totalUpFront);
        }
    }
}