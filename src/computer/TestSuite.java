package computer;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utilities.Utility;

import java.time.Duration;
import java.util.List;

public class TestSuite extends Utility {

    String baseUrl = "https://demo.nopcommerce.com/";

    @Before
    public void setUp() {

        openBrowser(baseUrl);
    }

    @Test
    public void verifyProductArrangeInAlphabeticalOrder() {
        //click on computer menu
        clickOnElement(By.xpath("//ul[@class='top-menu notmobile']//a[normalize-space() = 'Computers']"));
        //click on desktop
        clickOnElement(By.xpath("//div[@class = 'sub-category-item']/h2/a[normalize-space() = 'Desktops']"));
        selectByValueFromDropDown(By.xpath("//select[@id = 'products-orderby']"), "6");
        List<WebElement> items = driver.findElements(By.xpath("//div[@class = 'details']//a"));

        //verify descending order
        boolean isDescending = true;
        for (int i = 0; i < items.size() - 1; i++) {
            String currentElement = items.get(i).getText();
            String nextElement = items.get(i + 1).getText();
            if (currentElement.compareTo(nextElement) > 0) {
                isDescending = false;
                break;
            }
        }

        if (isDescending) {
            System.out.println("Elements are in descending order.");
        } else {
            System.out.println("Elements are not in descending order.");
        }
    }

    @Test
    public void verifyProductAddedToShoppingCartSuccessfully() {

        //click on computer menu
        clickOnElement(By.xpath("//ul[@class='top-menu notmobile']//a[normalize-space() = 'Computers']"));
        //click on desktop
        clickOnElement(By.xpath("//div[@class = 'sub-category-item']/h2/a[normalize-space() = 'Desktops']"));
        //select A to Z
        selectByValueFromDropDown(By.xpath("//select[@id = 'products-orderby']"), "5");

        try {
            WebElement button = driver.findElement(By.xpath("//div[@class='item-grid']//div[1]//div[1]//div[2]//div[3]//div[2]//button[1]"));
            button.click();
        } catch (StaleElementReferenceException e) {
            WebElement button = driver.findElement(By.xpath("//div[@class='item-grid']//div[1]//div[1]//div[2]//div[3]//div[2]//button[1]"));
            button.click();
        }

        String expected = "Build your own computer";
        String actual = getTextFromElement(By.xpath("//h1[contains(text(),'Build your own computer')]"));
        Assert.assertEquals(expected, actual);
        //select processor
        selectByValueFromDropDown(By.id("product_attribute_1"), "1");
        selectByValueFromDropDown(By.id("product_attribute_2"), "5");
        clickOnElement(By.id("product_attribute_3_7"));
        clickOnElement(By.id("product_attribute_4_9"));
        clickOnElement(By.id("product_attribute_5_12"));
        String eprice = "$1,475.00";
        String aprice = driver.findElement(By.xpath("//span[normalize-space() = '$1,475.00']")).getText();
        Assert.assertEquals(eprice, aprice);
        clickOnElement(By.id("add-to-cart-button-1"));
        String expectedMessage1 = "The product has been added to your shopping cart";
        String actualMessage1 = getTextFromElement(By.xpath("//p[@class = 'content']"));
        Assert.assertEquals(expectedMessage1,actualMessage1);
        clickOnElement(By.xpath("//span[@class = 'close']"));

        try {
            WebElement shoppingCart = driver.findElement(By.xpath("//span[contains(text(),'Shopping cart')]"));
            WebElement goToCart = driver.findElement(By.xpath("//button[contains(text(),'Go to cart')]"));
            Actions actions = new Actions(driver);
            actions.moveToElement(shoppingCart).moveToElement(goToCart).click().build().perform();
        }catch (ElementNotInteractableException e){
            WebElement shoppingCart = driver.findElement(By.xpath("//span[contains(text(),'Shopping cart')]"));
            Actions actions = new Actions(driver);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement goToCart = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(),'Go to cart')]")));
            actions.moveToElement(shoppingCart).moveToElement(goToCart).click().build().perform();
        }

        String expectedMessage2 = "Shopping cart";
        String actualMessage2 = getTextFromElement(By.xpath("//h1[contains(text(),'Shopping cart')]"));
        Assert.assertEquals(expectedMessage2,actualMessage2);
        driver.findElement(By.xpath("//input[@class = 'qty-input']")).clear();
        sendTextToElement(By.xpath("//input[@class = 'qty-input']"),"2");
        clickOnElement(By.id("updatecart"));
        String eTotal = "$2,950.00";
        String aTotal = driver.findElement(By.xpath("//span[text()='$2,950.00'][@class = 'product-subtotal']")).getText();
        Assert.assertEquals(eTotal,aTotal);
        clickOnElement(By.id("termsofservice"));
        clickOnElement(By.id("checkout"));
        String eText = "Welcome, Please Sign In!";
        String aText = getTextFromElement(By.xpath("//h1[contains(text(),'Welcome, Please Sign In!')]"));
        Assert.assertEquals(eText,aText);
        clickOnElement(By.xpath("//button[contains(text(),'Checkout as Guest')]"));
        sendTextToElement(By.id("BillingNewAddress_FirstName"),"YK");
        sendTextToElement(By.id("BillingNewAddress_LastName"),"Modi");
        sendTextToElement(By.id("BillingNewAddress_Email"),"prime123@gmail.com");
        selectByVisibleTextFromDropDown(By.id("BillingNewAddress_CountryId"),"United Kingdom");
        sendTextToElement(By.id("BillingNewAddress_City"),"London");
        sendTextToElement(By.id("BillingNewAddress_Address1"),"abc");
        sendTextToElement(By.id("BillingNewAddress_ZipPostalCode"),"HA39HL");
        sendTextToElement(By.id("BillingNewAddress_PhoneNumber"),"09898245856");
        clickOnElement(By.xpath("//button[@name = 'save']"));
        clickOnElement(By.id("shippingoption_1"));
        clickOnElement(By.xpath("//button[text() = 'Continue'][@class = 'button-1 shipping-method-next-step-button']"));
        clickOnElement(By.id("paymentmethod_1"));
        clickOnElement(By.xpath("//div[@id='payment-method-buttons-container']//button[text()='Continue']"));
        selectByValueFromDropDown(By.id("CreditCardType"),"MasterCard");
        sendTextToElement(By.id("CardholderName"),"YK");
        sendTextToElement(By.id("CardNumber"),"1111222233334444");
        selectByValueFromDropDown(By.id("ExpireMonth"),"5");
        selectByValueFromDropDown(By.id("ExpireYear"),"2030");
        sendTextToElement(By.id("CardCode"),"123");
        clickOnElement(By.xpath("//div[@id='payment-info-buttons-container']//button[text()='Continue']"));
        String aPaymentMethod = getTextFromElement(By.xpath("//li[@class='payment-method']"));
        String ePaymentMethod = "Payment Method: Credit Card";
        Assert.assertEquals(ePaymentMethod,aPaymentMethod);
        String aShippingMethod = getTextFromElement(By.xpath("//li[@class='shipping-method']"));
        String eShippingMethod = "Shipping Method: Next Day Air";
        Assert.assertEquals(eShippingMethod,aShippingMethod);
        String eTotal1 = "$2,950.00";
        String aTotal1 = driver.findElement(By.xpath("//span[text()='$2,950.00'][@class = 'product-subtotal']")).getText();
        Assert.assertEquals(eTotal1,aTotal1);
        clickOnElement(By.xpath("//button[contains(text(),'Confirm')]"));
        String atext = getTextFromElement(By.xpath("//h1[contains(text(),'Thank you')]"));
        String etext = "Thank you";
        Assert.assertEquals(etext,atext);
        String aOrder = getTextFromElement(By.xpath("//strong[contains(text(),'Your order has been successfully processed!')]"));
        String eOrder = "Your order has been successfully processed!";
        Assert.assertEquals(eOrder,aOrder);
        clickOnElement(By.xpath("//button[contains(text(),'Continue')]"));
        String eWelcome = "Welcome to our store";
        String aWelcome = getTextFromElement(By.xpath("//h2[contains(text(),'Welcome to our store')]"));
        Assert.assertEquals(eWelcome,aWelcome);
    }

    @After
    public void tearDown() {

        closeBrowser();
    }
}
