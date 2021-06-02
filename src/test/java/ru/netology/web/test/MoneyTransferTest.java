package ru.netology.web.test;

import com.codeborne.selenide.Condition;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.web.page.LoginPage;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.web.data.DataHelper.*;

class MoneyTransferTest {

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
    }

    @Test
    void shouldTransferMoneyFirstToSecondCards() {
        val change = 10;
        val dashboardPageBeforeTransfer = new LoginPage()
                .validLogin(getAuthInfo())
                .validVerify(getVerificationCodeFor(getAuthInfo()));
        int actualAmountFirst = dashboardPageBeforeTransfer.getCardBalance(getFirstCardId()) + change;
        if(change > dashboardPageBeforeTransfer.getCardBalance(getFirstCardId())){
            actualAmountFirst = dashboardPageBeforeTransfer.getCardBalance(getFirstCardId());
        }
        int actualAmountSecond = dashboardPageBeforeTransfer.getCardBalance(getSecondCardId()) - change;
        if(change > dashboardPageBeforeTransfer.getCardBalance(getSecondCardId())){
            actualAmountSecond = dashboardPageBeforeTransfer.getCardBalance(getSecondCardId());
        }
        val dashboardPageAfterTransfer = dashboardPageBeforeTransfer
                .transferTo(getFirstCardId())
                .transferFrom(change, getSecondCard());
        val balanceFirst = dashboardPageAfterTransfer.getCardBalance(getFirstCardId());
        val balanceSecond = dashboardPageAfterTransfer.getCardBalance(getSecondCardId());
        assertEquals(actualAmountFirst, balanceFirst);
        assertEquals(actualAmountSecond, balanceSecond);
    }

    @Test
    void shouldTransferMoneySecondToFirstCards() {
        val change = 10;
        val dashboardPageBeforeTransfer = new LoginPage()
                .validLogin(getAuthInfo())
                .validVerify(getVerificationCodeFor(getAuthInfo()));
        int actualAmountFirst = dashboardPageBeforeTransfer.getCardBalance(getFirstCardId()) - change;
        if(change > dashboardPageBeforeTransfer.getCardBalance(getFirstCardId())){
            actualAmountFirst = dashboardPageBeforeTransfer.getCardBalance(getFirstCardId());
        }
        int actualAmountSecond = dashboardPageBeforeTransfer.getCardBalance(getSecondCardId()) + change;
        if(change > dashboardPageBeforeTransfer.getCardBalance(getSecondCardId())){
            actualAmountSecond = dashboardPageBeforeTransfer.getCardBalance(getSecondCardId());
        }
        val dashboardPageAfterTransfer = dashboardPageBeforeTransfer
                .transferTo(getSecondCardId())
                .transferFrom(change, getFirstCard());
        val balanceFirst = dashboardPageAfterTransfer.getCardBalance(getFirstCardId());
        val balanceSecond = dashboardPageAfterTransfer.getCardBalance(getSecondCardId());
        assertEquals(actualAmountFirst, balanceFirst);
        assertEquals(actualAmountSecond, balanceSecond);
    }

    @Test
    void shouldLoginWithInvalidUser(){
        val loginPage = new LoginPage();
        loginPage.invalidLogin(getOtherAuthInfo(getAuthInfo()));
        $("[data-test-id=\"error-notification\"] .notification__content").shouldBe(Condition.visible)
                .shouldHave(Condition.text("Неверно указан логин или пароль"));
    }

    @Test
    void shouldTransferMoreMoneyThenTheyHave() {
        val change = 20001;
        val dashboardPageBeforeTransfer = new LoginPage()
                .validLogin(getAuthInfo())
                .validVerify(getVerificationCodeFor(getAuthInfo()));
        int actualAmountFirst = dashboardPageBeforeTransfer.getCardBalance(getFirstCardId()) + change;
        if(change > dashboardPageBeforeTransfer.getCardBalance(getFirstCardId())){
            actualAmountFirst = dashboardPageBeforeTransfer.getCardBalance(getFirstCardId());
        }
        int actualAmountSecond = dashboardPageBeforeTransfer.getCardBalance(getSecondCardId()) - change;
        if(change > dashboardPageBeforeTransfer.getCardBalance(getSecondCardId())){
            actualAmountSecond = dashboardPageBeforeTransfer.getCardBalance(getSecondCardId());
        }
        val dashboardPageAfterTransfer = dashboardPageBeforeTransfer
                .transferTo(getFirstCardId())
                .transferFrom(change, getSecondCard());
        val balanceFirst = dashboardPageAfterTransfer.getCardBalance(getFirstCardId());
        val balanceSecond = dashboardPageAfterTransfer.getCardBalance(getSecondCardId());
        assertEquals(actualAmountFirst, balanceFirst);
        assertEquals(actualAmountSecond, balanceSecond);
    }
}

