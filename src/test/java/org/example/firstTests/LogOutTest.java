package org.example.firstTests;

import io.qameta.allure.Step;
import org.example.Function;

public class LogOutTest { 

    private Function function;

    public LogOutTest(Function function){ 
        this.function = function; 
    }

    public void logOut(){ 
        clickAuthHeader(); 
        enterLogin(); 
        enterPassword(); 
        clickAuthLogin(); 
        hoverOnPersonalAccountElement(); 
        clickOnLogOutButton(); 
        logOutAfterCheck(); 
    }
    @Step("Нажать кнопку 'Вход' в хедере") 
    public void clickAuthHeader(){ 
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
    @Step("Навести курсор мыши на 'Личный кабинет' в хедере") 
    public void hoverOnPersonalAccountElement(){
        function.hoverOnElement("body > div.main-wrapper > header > div > div > div > div.col-xs-12.header-top-part > div > div > div.col-xs-5.col-sm-4.col-md-9.col-lg-8 > div.top-menu > div > div > div > div > a"); 
    }
    @Step("Нажать кнопку 'Выход'")
    public void clickOnLogOutButton(){
        function.clickElement("body > div.main-wrapper > header > div > div > div > div.col-xs-12.header-top-part > div > div > div.col-xs-5.col-sm-4.col-md-9.col-lg-8 > div.top-menu > div > div > div > div > div > div:nth-child(3) > a");
    }
    @Step("Проверить что выход из аккаунта прошел успешно")
    public void logOutAfterCheck(){
        function.acceptanceOfTest("body > div.main-wrapper > header > div > div > div > div.col-xs-12.header-top-part > div > div > div.col-xs-5.col-sm-4.col-md-9.col-lg-8 > div.top-menu > div > div > div > ul > li:nth-child(8) > a",
                "Должна отображаться кнопка 'Вход'"); 
    }

}
