package org.example;

import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.Condition;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.*;

public class HomePage {

    private final SelenideElement footer = $("footer");
    private final SelenideElement noticeButton = $("#didomi-notice-agree-button");
    private final SelenideElement submenuDropdown = $$("div.menu-dropdown-submenu").first();

    public void acceptCookies() {
        noticeButton.should(Condition.visible).click();
    }

    public boolean isTextPresentInFooter(String expectedText) {
        footer.scrollTo();
        return footer.text().contains(expectedText);
    }

    public void selectOptionFromTopBar(String option) {
        $(By.xpath("//button[contains(.,'" + option + "')]")).click();
    }

    public boolean isDropdownVisible() {
        String displayStyle = submenuDropdown.getCssValue("display");
        return "flex".equals(displayStyle);
    }

    public void clickOptionFromColumn(String option) {
        $(By.xpath("(//span[contains(text(), '" + option + "')])[2]")).click();
    }
}
