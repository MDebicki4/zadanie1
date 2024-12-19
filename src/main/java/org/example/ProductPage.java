package org.example;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.Condition;

import static com.codeborne.selenide.Selenide.*;

public class ProductPage {
    private final SelenideElement productScene = $("#binkies-scene");
    private final ElementsCollection buttons = $$("button");
    private final ElementsCollection priceElements = $$(".dt_price_change");

    public void verifyProductPageVisible() {
        productScene.should(Condition.visible);
    }

    public int[] addProductToCart() {
        String startText = priceElements.get(2).text();
        String rataText = priceElements.get(3).text();

        String startNumber = startText.replaceAll("[^0-9]", "");
        String rataNumber = rataText.replaceAll("[^0-9]", "");

        int start = Integer.parseInt(startNumber);
        int rata = Integer.parseInt(rataNumber);

        buttons.filterBy(Condition.text("Dodaj do koszyka")).first().click();

        return new int[]{start, rata};
    }
}