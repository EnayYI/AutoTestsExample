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
public class ExecuteOfTests {
    private WebDriver driver;
    private Function function;
    private AuthorizationTest authorizationTest;
    private AddingProductToCartTest addingProductToCartTest;
    private ChangeOfCityTest changeOfCityTest;
    private LogOutTest logOutTest;

    @Test
    @DisplayName("Проверка авторизации")
    public void Authorization() {
        authorizationTest.authTest();
    }

    @Test
    @DisplayName("Проверка смены города")
    public void City() {
        changeOfCityTest.cityChange();
    }

    @Test
    @DisplayName("Проверка добавления товара в корзину")
    public void addToCart() {
        addingProductToCartTest.addingToCart();
    }

    @Test
    @DisplayName("Проверка выхода из аккаунта")
    public void LogOut() {
        logOutTest.logOut();
    }

    @BeforeEach
    public void startPage() {
        driver = Function.initializingChrome("https://rs24.ru/home.htm");
        function = new Function(driver);
        authorizationTest = new AuthorizationTest(function);
        addingProductToCartTest = new AddingProductToCartTest(function);
        changeOfCityTest = new ChangeOfCityTest(function);
        logOutTest = new LogOutTest(function);
    }
}
