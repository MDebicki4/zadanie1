package org.example;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;

import static com.codeborne.selenide.Selenide.*;
import static org.junit.Assert.*;

public class StepDefinitions {
    private int start;
    private int rata;

    @Given("Otworz przegladarke")
    public void otworzPrzegladarke() {
        Selenide.open("about:blank");
        WebDriver driver = Selenide.webdriver().driver().getWebDriver();
        driver.manage().window().setSize(new Dimension(1600, 800));
    }

    @Then("Przegladarka otworzona")
    public void przegladarkaOtworzona() {
        assertNotNull("Przeglądarka nie jest uruchomiona", webdriver());
    }

    @Given("Przejdz na strone {string}")
    public void przejdzNaStrone(String url) {
        open(url);
    }

    @Then("Strona glowna jest widoczna")
    public void stronaGlownaJestWidoczna() {
        String title = title();
        assertTrue("Strona główna niewidoczna", title != null && !title.isEmpty());
        $("#didomi-notice-agree-button").should(Condition.visible).click();
        $("footer").scrollTo();
        String expectedText = "T‑Mobile Polska S.A. Wszystkie prawa zastrzeżone";
        boolean isTextPresent = $("footer").text().contains(expectedText);
        assertTrue("Tekst '" + expectedText + "' nie został znaleziony w stopce strony!", isTextPresent);
    }

    @Given("Z gornej belki wybierz {string}")
    public void zGornejBelkiWybierz(String opcja) {
        $(By.xpath("//button[contains(.,'" + opcja + "')]")).click();
    }

    @Then("Widoczna rozwijana lista")
    public void widocznaRozwijanaLista() {
        SelenideElement element = $$("div.menu-dropdown-submenu").first();
        String displayStyle = element.getCssValue("display");
        assertEquals("Pierwszy dropdown się nie rozwinął", "flex", displayStyle);
    }

    @When("Kliknij {string} z kolumny Bez abonamentu")
    public void kliknijZKolumny(String opcja) {
        $(By.xpath("(//span[contains(text(), '" + opcja + "')])[2]")).click();
    }

    @Then("Widoczna lista smartfonow")
    public void widocznaListaSmartfonow() {
        String expectedText = "Urządzenia bez abonamentu";
        boolean isTextPresent = $("h1").text().contains(expectedText);
        assertTrue("Tekst '" + expectedText + "' nie został znaleziony w headerze strony!", isTextPresent);
    }

    @When("Kliknij w pierwszy element z listy")
    public void kliknijWPierwszyElement() {
        $(By.xpath("//*[@data-qa='LST_ProductCard0']")).click();
    }

    @Then("Widoczna strona produktu")
    public void widocznaStronaProduktu() {
        $("#binkies-scene").should(Condition.visible);
    }

    @When("Dodaj produkt do koszyka")
    public void dodajProduktDoKoszyka() {
        ElementsCollection buttons = $$("button");  // Zbiera wszystkie przyciski na stronie

        ElementsCollection priceElements = $$(".dt_price_change");

        String startText = priceElements.get(2).text();  // 3. element (indeks 2)
        String rataText = priceElements.get(3).text();   // 4. element (indeks 3)

        String startNumber = startText.replaceAll("[^0-9]", ""); // Usuń wszystko, co nie jest liczbą
        String rataNumber = rataText.replaceAll("[^0-9]", "");  // Usuń wszystko, co nie jest liczbą

        start = Integer.parseInt(startNumber);  // Zamień tekst na liczbę
        rata = Integer.parseInt(rataNumber);    // Zamień tekst na liczbę

        buttons.filterBy(Condition.text("Dodaj do koszyka")).first().click();
    }

    @Then("Widoczna strona Twoj koszyk")
    public void widocznaStronaTwojKoszyk() {
        String expectedText = "Twój koszyk";
        SelenideElement basketHeader = $("h1.basketHeaderText");
        basketHeader.shouldBe(Condition.visible);
        boolean isTextPresent = basketHeader.text().contains(expectedText);
        assertTrue("Tekst '" + expectedText + "' nie został znaleziony w headerze strony!", isTextPresent);
    }

    @Then("Kwoty Cena na start oraz Rata miesieczna zgadzaja sie z kwotami z poprzedniej strony")
    public void kwotyZgadzaSie() {

        String totalMonthlyText = $("span[data-qa='BKT_TotalMonthly']").text();
        String totalUpFrontText = $("span[data-qa='BKT_TotalupFront']").text();

        int totalMonthly = Integer.parseInt(totalMonthlyText.replaceAll("[^0-9]", ""));
        int totalUpFront = Integer.parseInt(totalUpFrontText.replaceAll("[^0-9]", ""));

        assertEquals("Kwota miesięczna się nie zgadza!", totalMonthly, rata);
        assertEquals("Kwota upfront się nie zgadza!", totalUpFront, start);
    }

    @When("Przejdz na strone glowna {string}")
    public void przejdzNaStroneGlowna(String url) {
        open(url);
    }

    @Then("Widoczna strona glowna")
    public void widocznaStronaGlowna() {
        String title = title();
        assertTrue("Strona główna niewidoczna", title != null && !title.isEmpty());
        String expectedText = "T‑Mobile Polska S.A. Wszystkie prawa zastrzeżone";
        boolean isTextPresent = $("footer").text().contains(expectedText);
        assertTrue("Tekst '" + expectedText + "' nie został znaleziony w stopce strony!", isTextPresent);
    }

    @Then("W prawym gornej rogu widoczna jest ikonka koszyka z liczba produktow w koszyku")
    public void ikonkaKoszykaWRoGu() {
        String tekstKoszyk = $("a[title='Koszyk']").text();
        String liczbaTekst = tekstKoszyk.replaceAll("[^0-9]", ""); // Usuwamy wszystko, co nie jest cyfrą
        int liczbaKoszyk = Integer.parseInt(liczbaTekst);
        assertEquals("Liczba w koszyku nie wynosi 1!", 1, liczbaKoszyk);
    }
}