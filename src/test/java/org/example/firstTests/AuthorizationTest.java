package org.example.firstTests;

import io.qameta.allure.Step;
import org.example.Function;
import org.junit.jupiter.api.*;

@DisplayName("Авторизация") 
public class AuthorizationTest { 
    private Function function;

    public AuthorizationTest(Function function){ 
        this.function = function; 
    }

    public void authTest(){ 
        ckickAuthHeader(); 
        enterLogin(); 
        enterPassword(); 
        clickAuthLogin(); 
        loginAcceptance(); 
    }

    @Step("Нажать кнопку 'Вход' в хедере") 
    public void ckickAuthHeader(){ 
        function.clickElement("a.auth.header-text.new-menu-text-regular"); 
    }
    @Step("Ввести данные в поле Логин") 
    public void enterLogin(){ 
        function.enterData("input#usernameB2B", "GOROD_SVETA_MONITORING"); 
    }
    @Step("Ввести данные в поле Пароль") 
    public void enterPassword(){ 
        function.enterData("#passwordB2B", "Qwerty123"); 
    }
    @Step("Нажать кнопку 'Вход'") 
    public void clickAuthLogin(){ 
        function.clickElement("#mainFormB2B > div:nth-child(7) > button"); 
    }
    @Step("Проверить, что авторизация прошла успешно") 
    public void loginAcceptance(){ 
        function.acceptanceOfTest("body > div.main-wrapper > header > div > div > div > div.col-xs-12.header-top-part > div > div > div.col-xs-5.col-sm-4.col-md-9.col-lg-8 > div.top-menu > div > div > div > div > a",
                "Должна отображаться кнопка 'Личный кабинет'"); 
    }
}
