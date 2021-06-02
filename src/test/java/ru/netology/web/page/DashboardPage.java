package ru.netology.web.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import lombok.val;
import ru.netology.web.data.DataHelper.*;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;

public class DashboardPage {
  private SelenideElement heading = $(withText("Ваши карты"));
  private SelenideElement buttonReload = $("[data-test-id=\"action-reload\"]");
  private ElementsCollection cards = $$(".list__item");
  private final String balanceStart = ", баланс: ";
  private final String balanceFinish = " р.";
  private final String cardIdStart = "[data-test-id=\"";
  private final String cardIdFinish = "\"]";

  public DashboardPage() {
    heading.shouldBe(visible);
  }

  public TransferPage transferTo(CardId cardId){
    String id = cardId.getId();
    $(cardIdStart + id + cardIdFinish + " button").click();
    return new TransferPage();
  }

  public int getCardBalance(CardId cardId) {
    String id = cardId.getId();
    String text2 = $(cardIdStart + id + cardIdFinish).text();
    val text = cards.find(text(text2)).text();
    return extractBalance(text);
  }

  private int extractBalance(String text) {
    val start = text.indexOf(balanceStart);
    val finish = text.indexOf(balanceFinish);
    val value = text.substring(start + balanceStart.length(), finish);
    return Integer.parseInt(value);
  }
}
