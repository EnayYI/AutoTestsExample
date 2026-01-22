package org.example.firstTests;

import io.qameta.allure.Step;
import org.example.Function;
import org.example.ProductData;

import static org.assertj.core.api.Assertions.assertThat; 

public class AddingProductToCartTest { 
    private Function function;
    private ProductData productPage;
    private ProductData productCart; 

    public AddingProductToCartTest(Function function){ 
        this.function = function; 
    }

    public void addingToCart(){ 
        clickOnSearchField(); 
        enterSearchRequest(); 
        clickSearchButton(); 
        openFirstProductInTheList(); 
        getDataFromProduct(); 
        addProductToCart(); 
        openCart(); 
        getDataFromProductInTheCart(); 
        equalOfCollectedData(); 
    }

    @Step("Кликнуть по полю поиска") 
    public void clickOnSearchField() { 
        function.clickElement("#js-search-input"); 
    }
    @Step("Ввести поисковый запрос в поиск") 
    public void enterSearchRequest() { 
        function.enterData("#js-search-input", "10100"); 
    }
    @Step("Нажать кнопку поиска") 
    public void clickSearchButton() { 
        function.clickElement("#search__button");
    }
    @Step("Открыть карточку найденного товара") 
    public void openFirstProductInTheList() { 
        function.clickElement("#search > div > div > div > div:nth-child(3) > div > div.search-page > div > div.catalog-section-right-part > div.catalog-section > div:nth-child(2) > div > div.single-item-name > div.item-name > a"); 
    }
    @Step("Записать данные карточки товара: Код, Цена, Имя товара") 
    public void getDataFromProduct() { 
        function.switchToOpenedTab(); 
        String product_code = function.getTextFromElement("body > div.main-wrapper > div.product-info-container > div.product-info__main-block > div.product-main__main-chars-block > div:nth-child(1) > div.main-chars__value"); 
        String product_name = function.getTextFromElement("body > div.main-wrapper > div.product-info-container > div.product-info__header-block > div.product-header__title-block > div.product-header__title"); 
        double cost_of_product = Double.parseDouble(function.getTextFromElement("body > div.main-wrapper > div.product-info-container > div.product-info__main-block > div.product-main__main-info-block.js-product > div.main-info__top-block > div.main-info__price-block > div.main-info__price-value.js-product-price")); 
        productPage = new ProductData(product_code, product_name, cost_of_product); 
    }
    @Step("Добавить товар в корзину") 
    public void addProductToCart() { 
        function.clickElement("ody > div.main-wrapper > div.product-info-container > div.product-info__main-block > div.product-main__main-info-block.js-product > div.main-info__top-block > div.main-info__add-to-cart-block > button");
    }
    @Step("Открыть корзину")
    public void openCart() {
        function.clickElement("body > div.main-wrapper > div.product-info-container > div.product-info__main-block > div.product-main__main-info-block.js-product > div.main-info__top-block > div.main-info__add-to-cart-block > div.main-info__product-sum-block.js-product-sum-block > span.main-info__product-sum.js-product-sum"); 
        function.clickElement("body > div.main-wrapper > header > button");
    }
    @Step("Записать данные товара из корзины: Код, Цена, Имя товара")
    public void getDataFromProductInTheCart() {
        String product_code_in_the_cart = function.getTextFromElement(".basket-table-col-value.js-cart-code");
        String product_name_in_the_cart = function.getTextFromElement(".basket-table-col.descr-position-col > div.basket-table-col-value");
        double cost_of_product_in_the_cart = Double.parseDouble(function.getTextFromElement(".basket-table-col-value.js-cart-price-nds"));
        productCart = new ProductData(product_code_in_the_cart, product_name_in_the_cart, cost_of_product_in_the_cart);
    }
    @Step("Сравнить данные карточки товара и товара в корзине")
    public void equalOfCollectedData() {
        assertThat(productPage) //Вызывет переменную с объектом класса ProductData в котором сохранены данные о товаре со страницы товара
                .usingRecursiveComparison() //Сравнивает все поля
                .withComparatorForFields(
                        (p1, p2) -> Math.abs((double)p1 - (double)p2) <= 0.2 ? 0 : 1, "price"  //Компаратор поля price для сравнения цены с расхождением в 20 коп.
                )
                .ignoringActualNullFields()//Игнорирует поля со значением null
                .isEqualTo(productCart); //Сравнивает с объектом класса ProductData в котором сохранены данные о товаре из корзины
    }

}
