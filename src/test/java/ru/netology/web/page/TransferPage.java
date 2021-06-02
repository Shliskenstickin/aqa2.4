package ru.netology.web.page;

import com.codeborne.selenide.SelenideElement;
import ru.netology.web.data.DataHelper;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;

public class TransferPage {
    private SelenideElement heading = $(withText("Пополнение карты"));
    private SelenideElement amountInput = $("[data-test-id=\"amount\"] input");
    private SelenideElement cardInput = $("[data-test-id=\"from\"] input");
    private SelenideElement transfer = $("[data-test-id=\"action-transfer\"]");

    public TransferPage() {
        heading.shouldBe(visible);
    }

    public DashboardPage transferFrom(int amount, DataHelper.Card card) {
        $(amountInput).setValue(Integer.toString(amount));
        $(cardInput).setValue(card.getNumber());
        $(transfer).click();
        return new DashboardPage();
    }
}
