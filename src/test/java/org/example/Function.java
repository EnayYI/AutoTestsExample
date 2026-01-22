package org.example; 
import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver; 
import org.openqa.selenium.chrome.ChromeOptions; 
import org.openqa.selenium.interactions.Actions; 
import org.openqa.selenium.support.ui.ExpectedConditions; 
import java.time.Duration; 
import java.util.*; 

import static org.assertj.core.api.Assertions.assertThat; 

public class Function { 
    private WebDriver driver; 

    public Function(WebDriver driver){ 
        this.driver = driver; 
    }

    public static WebDriver initializingChrome(String webAddress) { 
        ChromeOptions options = new ChromeOptions(); 
        
        options.addArguments("start-maximized"); 
        WebDriver driver = new ChromeDriver(options); 
        driver.get(webAddress); 
        return driver; 
    }

    @Step("Кликаю по элементу")
    public void clickElement(String selector){ 
        
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10)); 
        WebElement element = driver.findElement(By.cssSelector(selector)); 
        element.click(); 
    }

    @Step("Ввожу данные в поле")
    public void enterData(String selector, String data){ 
        
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10)); 
        WebElement field = driver.findElement(By.cssSelector(selector)); 
        field.sendKeys(data); 
    }

    @Step("Получаю текст элемента")
    public String getTextFromElement(String selector){ 
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10)); 
        WebElement text_from_element = driver.findElement(By.cssSelector(selector)); 
        return text_from_element.getText(); 
    }

    @Step("Навожусь на элемент")
    public void hoverOnElement(String selector){ 
        Actions action = new Actions(driver); 
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10)); 
        WebElement element_to_hover = driver.findElement(By.cssSelector(selector)); 
        action.moveToElement(element_to_hover).perform(); 
    }

    @Step("Поверяю результат теста")
    public void acceptanceOfTest(String selector, String description){
        try { 
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10)); 
            boolean isVisible = ExpectedConditions.visibilityOfElementLocated(By.cssSelector(selector))
                    .apply(driver) != null; 
            assertThat(isVisible).as(description).isTrue(); 
        }
        catch (org.openqa.selenium.TimeoutException e){ 
            System.out.println("The element was not found within 10 seconds"); 
            
        }
    }

    @Step("Переключаюсь на другую вкладку")
    public void switchToOpenedTab(){ 
        Set<String> windowHandles = driver.getWindowHandles(); //Возвращает уникальные идентификаторы всех открытых вкладок
        List<String> tabs = new ArrayList<>(windowHandles); //Преобразует set в string, чтобы можно было обращаться по индексу
        driver.switchTo().window(tabs.getLast()); //Берет последний элемент из списка и переключает вкладку
    }
}
