package org.example;

import com.codeborne.selenide.Selenide;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static com.codeborne.selenide.Selenide.*;
import static org.junit.Assert.*;


public class StepDefinitions {
    private int start;
    private int rata;
    private final HomePage homePage = new HomePage();
    private final ProductPage productPage = new ProductPage();
    private final CartPage cartPage = new CartPage();


    private void closeBrowserAndReportError(Exception e) {
        try {
            Selenide.closeWebDriver();
            LoggerUtil.logInfo("Przeglądarka została zamknięta z powodu błędu.");
        } catch (Exception ex) {
            LoggerUtil.logError("Błąd przy zamykaniu przeglądarki po wystąpieniu błędu: ", ex);
        }
        LoggerUtil.logError("Wystąpił błąd: ", e);
    }

    @Before
    public void beforeTest() {
        LoggerUtil.logInfo("Rozpoczynam test...");
    }

    @After
    public void afterTest() {
        try {
            webdriver();
            Selenide.closeWebDriver();
            LoggerUtil.logInfo("Przeglądarka została zamknięta.");
        } catch (Exception e) {
            LoggerUtil.logError("Wystąpił błąd podczas zamykania przeglądarki.", e);
        }
    }

    @Given("Otworz przegladarke")
    public void otworzPrzegladarke() {
        try {
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--headless");
            options.addArguments("--window-size=1600x800");

            LoggerUtil.logInfo("Otwieram przeglądarkę...");
            Selenide.open("about:blank");
            WebDriver driver = Selenide.webdriver().driver().getWebDriver();
            LoggerUtil.logInfo("Przeglądarka została otworzona w trybie headless z rozmiarem 1600x800");
        } catch (Exception e) {
            LoggerUtil.logError("Błąd podczas otwierania przeglądarki.", e);
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
        LoggerUtil.logInfo("Przechodzę na stronę: {}", url);
        open(url);
        Selenide.screenshot("test_screenshot");
    }

    @Then("Strona glowna jest widoczna")
    public void stronaGlownaJestWidoczna() {
        String title = com.codeborne.selenide.Selenide.title();
        LoggerUtil.logInfo("Sprawdzam tytuł strony głównej: {}", title);
        assertTrue("Strona główna niewidoczna", title != null && !title.isEmpty());

        homePage.acceptCookies();
        String expectedText = "T‑Mobile Polska S.A. Wszystkie prawa zastrzeżone";
        boolean isTextPresent = homePage.isTextPresentInFooter(expectedText);

        if (isTextPresent) {
            LoggerUtil.logInfo("Tekst '{}' znaleziony w stopce strony", expectedText);
        } else {
            LoggerUtil.logError("Tekst '{}' nie został znaleziony w stopce strony", expectedText);
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
            LoggerUtil.logInfo("Dropdown został rozwinięty.");
        } else {
            LoggerUtil.logError("Dropdown nie został rozwinięty.");
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
        boolean isTextPresent = homePage.isSmartphoneListVisible(expectedText);
        assertTrue("Tekst '" + expectedText + "' nie został znaleziony w headerze strony!", isTextPresent);
    }

    @When("Kliknij w pierwszy element z listy")
    public void kliknijWPierwszyElement() {
        homePage.clickFirstProduct();
    }

    @Then("Widoczna strona produktu")
    public void widocznaStronaProduktu() {
        productPage.verifyProductPageVisible();
    }

    @When("Dodaj produkt do koszyka")
    public void dodajProduktDoKoszyka() {
        LoggerUtil.logInfo("Dodaję produkt do koszyka...");

        int[] prices = productPage.addProductToCart();
        start = prices[0];
        rata = prices[1];

        LoggerUtil.logInfo("Kwota początkowa: {}, Kwota miesięczna: {}", start, rata);
    }

    @Then("Widoczna strona Twoj koszyk")
    public void widocznaStronaTwojKoszyk() {
        cartPage.verifyCartPageVisible();
    }

    @Then("Kwoty Cena na start oraz Rata miesieczna zgadzaja sie z kwotami z poprzedniej strony")
    public void kwotyZgadzaSie() {
        try {
            LoggerUtil.logInfo("Sprawdzam, czy kwoty Cena na start oraz Rata miesięczna zgadzają się...");
            cartPage.verifyPricesMatch(start, rata);
            LoggerUtil.logInfo("Kwoty na stronie koszyka zgadzają się z kwotami na poprzedniej stronie.");
        } catch (Exception e) {
            LoggerUtil.logError("Błąd przy sprawdzaniu kwot na stronie koszyka.", e);
            closeBrowserAndReportError(e);
            throw e;
        }
    }

    @When("Przejdz na strone glowna {string}")
    public void przejdzNaStroneGlowna(String url) {
        LoggerUtil.logInfo("Przechodzę na stronę: {}", url);
        open(url);
    }

    @Then("Widoczna strona glowna")
    public void widocznaStronaGlowna() {
        String title = com.codeborne.selenide.Selenide.title();
        LoggerUtil.logInfo("Sprawdzam tytuł strony głównej: {}", title);
        assertTrue("Strona główna niewidoczna", title != null && !title.isEmpty());

        String expectedText = "T‑Mobile Polska S.A. Wszystkie prawa zastrzeżone";
        boolean isTextPresent = homePage.isTextPresentInFooter(expectedText);

        if (isTextPresent) {
            LoggerUtil.logInfo("Tekst '{}' znaleziony w stopce strony", expectedText);
        } else {
            LoggerUtil.logError("Tekst '{}' nie został znaleziony w stopce strony", expectedText);
        }

        assertTrue("Tekst '" + expectedText + "' nie został znaleziony w stopce strony!", isTextPresent);
    }

    @Then("W prawym gornej rogu widoczna jest ikonka koszyka z liczba produktow w koszyku")
    public void ikonkaKoszykaWRoGu() {
        String tekstKoszyk = $("a[title='Koszyk']").text();
        String liczbaTekst = tekstKoszyk.replaceAll("[^0-9]", "");
        int liczbaKoszyk = Integer.parseInt(liczbaTekst);
        LoggerUtil.logInfo("Liczba produktów w koszyku: {}", liczbaKoszyk);
        assertEquals("Liczba w koszyku nie wynosi 1!", 1, liczbaKoszyk);
    }
}