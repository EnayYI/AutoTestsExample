package org.example;
import org.example.firstTests.AddingProductToCartTest;
import org.example.firstTests.AuthorizationTest;
import org.example.firstTests.ChangeOfCityTest;
import org.example.firstTests.LogOutTest;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;




@ExtendWith(CustomTestExtension.class)
@DisplayName("Тест-кейсы - магазин")
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class EnterData {
    @BeforeEach
    public void startPage() {
        driver = Function.initializingChrome("https://rs24.ru/home.htm");
        function = new Function(driver);
    }

    private WebDriver driver;
    private Function function;

    @Test
    @DisplayName("Ввод данных в поля")
    public void EnterData(){

    }
}

