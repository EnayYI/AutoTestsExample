package org.example;

import io.qameta.allure.Allure;
import io.qameta.allure.model.Status;
import org.junit.jupiter.api.extension.*;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import java.io.ByteArrayInputStream;
import java.lang.reflect.Field;


public class CustomTestExtension implements TestExecutionExceptionHandler, TestWatcher {

    // Получение WebDriver из тестового класса
    private WebDriver getDriver(ExtensionContext context) { //Принимает на вход контекст и информацию о тесте
        return context.getTestInstance() //Получает объект текущего теста
                .map(instance -> { //Если тест существует, выполняется код ниже
                    try {
                        Field driverField = instance.getClass().getDeclaredField("driver"); //Ищет поле с именем driver внутри тестового класса. private WebDriver driver в ExecuteOfTests
                        driverField.setAccessible(true); //Делает приватное поле доступным
                        Object driverObject = driverField.get(instance); //Получает объект WebDriver
                        return (driverObject instanceof WebDriver) ? (WebDriver) driverObject : null; //Проверяет действительно ли driver — это WebDriver. Если да, приводит к WebDriver, если нет - null
                    } catch (NoSuchFieldException | IllegalAccessException e) { //Обработка исключений, если driver не найден или к нему нельзя получить доступ
                        return null;
                    }
                }).orElse(null); //Если что-то пошло не так, возвращает null
    }

    // Добавление скриншота в Allure
    private void takeScreenshot(ExtensionContext context) {
        WebDriver driver = getDriver(context);
        //Проверяет можно ли сделать скриншот
        if (driver instanceof TakesScreenshot) {
            try {
                //Делает скриншот и добавляет его к отчету
                byte[] screenshotBytes = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
                Allure.addAttachment("Screenshot - " + context.getDisplayName(), "image/png",
                        new ByteArrayInputStream(screenshotBytes), ".png");
                //Если что-то не так - ошибка игнорируется
            } catch (Exception ignored) {

            }
        }
    }

    // Принудительно изменяет статус теста в Allure на FAILED, если он падает по какой-либо причине
    private void markTestAsFailed(ExtensionContext context) {
        Allure.getLifecycle().updateTestCase(tc -> tc.setStatus(Status.FAILED));
    }

    // Закрывает браузер после окончания теста
    private void closeDriver(ExtensionContext context) {
        WebDriver driver = getDriver(context);
        if (driver != null) {
            driver.quit();
        }
    }

    // Если тест упал, выводится текст, делается скриншот, тест помечается как Failed и закрывается браузер
    @Override
    public void handleTestExecutionException(ExtensionContext context, Throwable throwable) throws Throwable {
        System.out.println("Ошибка в тесте: " + context.getDisplayName());

        takeScreenshot(context);
        markTestAsFailed(context);
        closeDriver(context);

        // Заменяет оригинальную ошибку на AssertionError
        throw new AssertionError("Тест провален из-за ошибки: " + throwable.getMessage(), throwable);
    }

    // TestWatcher: тест успешно выполнен, закрывает браузер
    @Override
    public void testSuccessful(ExtensionContext context) {
        closeDriver(context);
    }

    // TestWatcher: тест прерван если, например, не было достаточно данных. Делает скриншот, помечает тест как failed и закрывает браузер
    @Override
    public void testAborted(ExtensionContext context, Throwable cause) {
        takeScreenshot(context);
        markTestAsFailed(context);
        closeDriver(context);
    }

    // TestWatcher: тест провален по assertj. Делает скриншот, помечает тест как failed и закрывает браузер
    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        takeScreenshot(context);
        markTestAsFailed(context);
        closeDriver(context);
    }
}
