package org.example.firstTests;
import io.qameta.allure.Step;
import org.example.Function;

import static org.assertj.core.api.Assertions.assertThat; 

public class ChangeOfCityTest { 

    private Function function;

    public ChangeOfCityTest(Function function){ 
        this.function = function; 
    }

    public void cityChange(){ 
        selectCityButton(); 
        regionSelect(); 
        citySelect(); 
        acceptChangeOfCity(); 
    }
    @Step("Нажать кнопку выбора города") 
    public void selectCityButton(){ 
        function.clickElement("body > div.main-wrapper > header > div > div > div > div.col-xs-12.header-top-part > div > div > div.col-xs-12.col-sm-12.col-md-9.col-lg-8.header-actions-wrap > div > div.select-city > a"); 
    }

    @Step("Выбрать Алтайский край") 
    public void regionSelect(){ 
        function.clickElement("#cities-by-regions-guest-block > div > div > div > div > div > div > div.region-list-column > div.region-list-column-content > div:nth-child(1) > div.regions-by-letter > div:nth-child(1)"); 
    }

    @Step("Выбрать Бийск") 
    public void citySelect(){ 
        function.clickElement("#cities-by-regions-guest-block > div > div > div > div > div > div > div.region-city-list-column > div.region-city-list-column-content > div:nth-child(2) > a"); 
    }

    @Step("Проверить что город сменился") 
    public void acceptChangeOfCity(){ 
        String text = function.getTextFromElement("body > div.main-wrapper > header > div > div > div > div.col-xs-12.header-top-part > div > div > div.col-xs-12.col-sm-12.col-md-9.col-lg-8.header-actions-wrap > div > div.select-city > a > span"); 
        assertThat(text).as("Проверяем, что отображается название выбранного города 'Бийск'").isEqualTo("Бйск");
    }
}
