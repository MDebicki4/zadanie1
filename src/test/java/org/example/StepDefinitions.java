package org.example;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.codeborne.selenide.Selenide.*;
import static org.junit.Assert.*;

public class StepDefinitions {
    private int start;
    private int rata;
    HomePage homePage = new HomePage();

    private static final Logger logger = LoggerFactory.getLogger(StepDefinitions.class);

    private void closeBrowserAndReportError(Exception e) {
        try {
            Selenide.closeWebDriver();
            logger.info("Przeglądarka została zamknięta z powodu błędu.");
        } catch (Exception ex) {
            logger.error("Błąd przy zamykaniu przeglądarki po wystąpieniu błędu: ", ex);
        }
        logger.error("Wystąpił błąd: ", e);
    }

    @Before
    public void beforeTest() {
        logger.info("Rozpoczynam test...");
    }

    @After
    public void afterTest() {
        try {
            webdriver();
            Selenide.closeWebDriver();
            logger.info("Przeglądarka została zamknięta.");
        } catch (Exception e) {
            logger.error("Wystąpił błąd podczas zamykania przeglądarki.", e);
        }
    }

    @Given("Otworz przegladarke")
    public void otworzPrzegladarke() {
        try {
            logger.info("Otwieram przeglądarkę...");
            Selenide.open("about:blank");
            WebDriver driver = Selenide.webdriver().driver().getWebDriver();
            driver.manage().window().setSize(new Dimension(1600, 800));
            logger.info("Przeglądarka została otworzona i zmieniono jej rozmiar na 1600x800");
        } catch (Exception e) {
            logger.error("Błąd podczas otwierania przeglądarki.", e);
            closeBrowserAndReportError(e);
            throw e;
        }
    }

    @Then("Przegladarka otworzona")
    public void przegladarkaOtworzona() {
        assertNotNull("Przeglądarka nie jest uruchomiona", webdriver());
    }

    @Given("Przejdz na strone {string}")
    public void przejdzNaStrone(String url) {
        logger.info("Przechodzę na stronę: {}", url);
        open(url);
    }

    @Then("Strona glowna jest widoczna")
    public void stronaGlownaJestWidoczna() {
        String title = com.codeborne.selenide.Selenide.title();
        logger.info("Sprawdzam tytuł strony głównej: {}", title);
        assertTrue("Strona główna niewidoczna", title != null && !title.isEmpty());

        homePage.acceptCookies();
        String expectedText = "T‑Mobile";
        boolean isTextPresent = homePage.isTextPresentInFooter(expectedText);

        if (isTextPresent) {
            logger.info("Tekst '{}' znaleziony w stopce strony", expectedText);
        } else {
            logger.error("Tekst '{}' nie został znaleziony w stopce strony", expectedText);
        }

        assertTrue("Tekst '" + expectedText + "' nie został znaleziony w stopce strony!", isTextPresent);
    }

    @Given("Z gornej belki wybierz {string}")
    public void zGornejBelkiWybierz(String opcja) {
        homePage.selectOptionFromTopBar(opcja);
    }

    @Then("Widoczna rozwijana lista")
    public void widocznaRozwijanaLista() {
        boolean isDropdownVisible = homePage.isDropdownVisible();
        if (isDropdownVisible) {
            logger.info("Dropdown został rozwinięty.");
        } else {
            logger.error("Dropdown nie został rozwinięty.");
        }
        assertTrue("Pierwszy dropdown się nie rozwinął", isDropdownVisible);
    }

    @When("Kliknij {string} z kolumny Bez abonamentu")
    public void kliknijZKolumny(String opcja) {
        homePage.clickOptionFromColumn(opcja);
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
        logger.info("Dodaję produkt do koszyka...");
        ElementsCollection buttons = $$("button");
        ElementsCollection priceElements = $$(".dt_price_change");

        String startText = priceElements.get(2).text();
        String rataText = priceElements.get(3).text();

        String startNumber = startText.replaceAll("[^0-9]", "");
        String rataNumber = rataText.replaceAll("[^0-9]", "");

        start = Integer.parseInt(startNumber);
        rata = Integer.parseInt(rataNumber);

        logger.info("Kwota początkowa: {}, Kwota miesięczna: {}", start, rata);

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

        try {
            logger.info("Sprawdzam, czy kwoty Cena na start oraz Rata miesięczna zgadzają się...");
            String totalMonthlyText = $("span[data-qa='BKT_TotalMonthly']").text();
            String totalUpFrontText = $("span[data-qa='BKT_TotalupFront']").text();

            int totalMonthly = Integer.parseInt(totalMonthlyText.replaceAll("[^0-9]", ""));
            int totalUpFront = Integer.parseInt(totalUpFrontText.replaceAll("[^0-9]", ""));

            logger.info("Kwota miesięczna na stronie: {}, Kwota upfront na stronie: {}", totalMonthly, totalUpFront);
            logger.info("Kwota miesięczna w poprzedniej stronie: {}, Kwota upfront w poprzedniej stronie: {}", rata, start);

            assertEquals("Kwota miesięczna się nie zgadza!", totalMonthly, rata);
            assertEquals("Kwota upfront się nie zgadza!", totalUpFront, start);
        } catch (Exception e) {
            logger.error("Błąd przy sprawdzaniu kwot na stronie koszyka.", e);
            closeBrowserAndReportError(e);
            throw e;
        }
    }

    @When("Przejdz na strone glowna {string}")
    public void przejdzNaStroneGlowna(String url) {
        open(url);
    }

    @Then("Widoczna strona glowna")
    public void widocznaStronaGlowna() {
        String title = title();
        assertTrue("Strona główna niewidoczna", title != null && !title.isEmpty());
        String expectedText = "T‑Mobile Polska";
        boolean isTextPresent = $("footer").text().contains(expectedText);
        assertTrue("Tekst '" + expectedText + "' nie został znaleziony w stopce strony!", isTextPresent);
    }

    @Then("W prawym gornej rogu widoczna jest ikonka koszyka z liczba produktow w koszyku")
    public void ikonkaKoszykaWRoGu() {
        String tekstKoszyk = $("a[title='Koszyk']").text();
        String liczbaTekst = tekstKoszyk.replaceAll("[^0-9]", "");
        int liczbaKoszyk = Integer.parseInt(liczbaTekst);
        logger.info("Liczba produktów w koszyku: {}", liczbaKoszyk);
        assertEquals("Liczba w koszyku nie wynosi 1!", 1, liczbaKoszyk);
    }
}