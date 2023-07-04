package electronics;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utilities.Utility;

import java.time.Duration;

public class ElectronicsTest extends Utility {

    String baseUrl = "https://demo.nopcommerce.com/";

    @Before
    public void setUp() {

        openBrowser(baseUrl);
    }

    @Test
    public void verifyUserShouldNavigateToCellPhonesPageSuccessfully() {

        WebElement electronics = driver.findElement(By.xpath("//ul[@class='top-menu notmobile']//a[normalize-space() = 'Electronics']"));
        WebElement cellphones = driver.findElement(By.xpath("//a[text() = 'Cell phones ']"));
        Actions actions = new Actions(driver);
        actions.moveToElement(electronics).moveToElement(cellphones).click().build().perform();
        String expected = "Cell phones";
        String actual = getTextFromElement(By.xpath("//h1[contains(text(),'Cell phones')]"));
        Assert.assertEquals(expected, actual);

    }

    @Test
    public void verifyThatTheProductAddedSuccessfullyAndPlaceOrderSuccessfully() {


        //Click on cellphones and verify text
        WebElement electronics = driver.findElement(By.xpath("//ul[@class='top-menu notmobile']//a[normalize-space() = 'Electronics']"));
        WebElement cellphones = driver.findElement(By.xpath("//a[text() = 'Cell phones ']"));
        Actions actions = new Actions(driver);
        actions.moveToElement(electronics).moveToElement(cellphones).click().build().perform();
        String expected = "Cell phones";
        String actual = getTextFromElement(By.xpath("//h1[contains(text(),'Cell phones')]"));
        Assert.assertEquals(expected, actual);

        //click on list view tab
        clickOnElement(By.xpath("//a[contains(text(),'List')]"));

        //click on nokia lumia 1020
        try {
            try {
                clickOnElement(By.xpath("//a[@title = 'Show details for Nokia Lumia 1020']"));
                //WebElement nokia = driver.findElement(By.linkText("Nokia Lumia 1020"));
                //nokia.click();
            } catch (StaleElementReferenceException e) {
                clickOnElement(By.xpath("//a[@title = 'Show details for Nokia Lumia 1020']"));
                //WebElement nokia = driver.findElement(By.linkText("Nokia Lumia 1020"));
                //nokia.click();
            }
        } catch (ElementClickInterceptedException e) {
            clickOnElement(By.xpath("//a[@title = 'Show details for Nokia Lumia 1020']"));
            //WebElement nokia = driver.findElement(By.linkText("Nokia Lumia 1020"));
            //nokia.click();
        }
        //verify text and price
        String enokia = "Nokia Lumia 1020";
        String anokia = getTextFromElement(By.xpath("//h1[contains(text(),'Nokia Lumia 1020')]"));
        Assert.assertEquals(enokia, anokia);

        String eprice = "$349.00";
        String aprice = getTextFromElement(By.xpath("//span[@id='price-value-20']"));
        Assert.assertEquals(eprice, aprice);

        //updating quantity
        WebElement quantity = driver.findElement(By.xpath("//input[@id='product_enteredQuantity_20']"));
        quantity.clear();
        sendTextToElement(By.xpath("//input[@id='product_enteredQuantity_20']"), "2");

        //add to cart
        clickOnElement(By.xpath("//button[@id='add-to-cart-button-20']"));

        //verify message
        String expectedMessage1 = "The product has been added to your shopping cart";
        String actualMessage1 = getTextFromElement(By.xpath("//p[@class = 'content']"));
        Assert.assertEquals(expectedMessage1, actualMessage1);

        //close green bar
        clickOnElement(By.xpath("//span[@class = 'close']"));

        try {
            WebElement shoppingCart = driver.findElement(By.xpath("//span[contains(text(),'Shopping cart')]"));
            WebElement goToCart = driver.findElement(By.xpath("//button[contains(text(),'Go to cart')]"));
            actions.moveToElement(shoppingCart).moveToElement(goToCart).click().build().perform();
        } catch (ElementNotInteractableException e) {
            WebElement shoppingCart = driver.findElement(By.xpath("//span[contains(text(),'Shopping cart')]"));
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement goToCart = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(),'Go to cart')]")));
            actions.moveToElement(shoppingCart).moveToElement(goToCart).click().build().perform();
        }

        String expectedMessage2 = "Shopping cart";
        String actualMessage2 = getTextFromElement(By.xpath("//h1[contains(text(),'Shopping cart')]"));
        Assert.assertEquals(expectedMessage2, actualMessage2);

        String eQuantity = "2";
        String aQuantity = driver.findElement(By.xpath("//input[@class = 'qty-input']")).getAttribute("value");
        Assert.assertEquals(eQuantity, aQuantity);

        String eTotal = "$698.00";
        String aTotal = driver.findElement(By.xpath("//span[text()='$698.00'][@class = 'product-subtotal']")).getText();
        Assert.assertEquals(eTotal, aTotal);

        clickOnElement(By.id("termsofservice"));
        clickOnElement(By.id("checkout"));

        //verify welcome message
        String eText = "Welcome, Please Sign In!";
        String aText = getTextFromElement(By.xpath("//h1[contains(text(),'Welcome, Please Sign In!')]"));
        Assert.assertEquals(eText, aText);

        clickOnElement(By.xpath("//button[contains(text(),'Register')]"));
        String eRegister = "Register";
        String aRegister = getTextFromElement(By.xpath("//h1[contains(text(),'Register')]"));
        Assert.assertEquals(eRegister, aRegister);

        //fill form
        clickOnElement(By.id("gender-male"));
        sendTextToElement(By.id("FirstName"), "YK");
        sendTextToElement(By.id("LastName"), "Modi");
        sendTextToElement(By.id("Email"), "rk23@gmail.com");
        sendTextToElement(By.id("Password"), "Prime@123");
        sendTextToElement(By.id("ConfirmPassword"), "Prime@123");
        clickOnElement(By.id("register-button"));

        //verify register completion text
        String eCompletion = "Your registration completed";
        String aCompletion = getTextFromElement(By.xpath("//div[contains(text(),'Your registration completed')]"));
        Assert.assertEquals(eCompletion, aCompletion);
        clickOnElement(By.xpath("//a[contains(text(),'Continue')]"));

        clickOnElement(By.xpath("//a[contains(text(),'Log in')]"));
        sendTextToElement(By.id("Email"),"rk23@gmail.com");
        sendTextToElement(By.id("Password"),"Prime@123");
        clickOnElement(By.xpath("//button[contains(text(),'Log in')]"));

        String eCart = "Shopping cart";
        String aCart = getTextFromElement(By.xpath("//h1[contains(text(),'Shopping cart')]"));
        Assert.assertEquals(eCart, aCart);
        clickOnElement(By.id("termsofservice"));
        clickOnElement(By.xpath("//button[@id='checkout']"));

        sendTextToElement(By.id("BillingNewAddress_FirstName"), "YK");
        sendTextToElement(By.id("BillingNewAddress_LastName"), "Modi");
        sendTextToElement(By.id("BillingNewAddress_Email"), "prime123");
        selectByVisibleTextFromDropDown(By.id("BillingNewAddress_CountryId"), "United Kingdom");
        sendTextToElement(By.id("BillingNewAddress_City"), "London");
        sendTextToElement(By.id("BillingNewAddress_Address1"), "abc");
        sendTextToElement(By.id("BillingNewAddress_ZipPostalCode"), "HA39HL");
        sendTextToElement(By.id("BillingNewAddress_PhoneNumber"), "09898245856");
        clickOnElement(By.xpath("//button[@name = 'save']"));

        clickOnElement(By.id("shippingoption_2"));
        clickOnElement(By.xpath("//button[text() = 'Continue'][@class = 'button-1 shipping-method-next-step-button']"));

        clickOnElement(By.id("paymentmethod_1"));
        clickOnElement(By.xpath("//div[@id='payment-method-buttons-container']//button[text()='Continue']"));

        selectByValueFromDropDown(By.id("CreditCardType"), "MasterCard");
        sendTextToElement(By.id("CardholderName"), "YK");
        sendTextToElement(By.id("CardNumber"), "1111222233334444");
        selectByValueFromDropDown(By.id("ExpireMonth"), "5");
        selectByValueFromDropDown(By.id("ExpireYear"), "2030");
        sendTextToElement(By.id("CardCode"), "123");
        clickOnElement(By.xpath("//div[@id='payment-info-buttons-container']//button[text()='Continue']"));

        String aPaymentMethod = getTextFromElement(By.xpath("//li[@class='payment-method']"));
        String ePaymentMethod = "Payment Method: Credit Card";
        Assert.assertEquals(ePaymentMethod, aPaymentMethod);

        String aShippingMethod = getTextFromElement(By.xpath("//li[@class='shipping-method']"));
        String eShippingMethod = "Shipping Method: 2nd Day Air";
        Assert.assertEquals(eShippingMethod, aShippingMethod);

        String eTotal1 = "$698.00";
        String aTotal1 = driver.findElement(By.xpath("//span[text()='$698.00'][@class = 'product-subtotal']")).getText();
        Assert.assertEquals(eTotal1, aTotal1);

        clickOnElement(By.xpath("//button[contains(text(),'Confirm')]"));

        String atext = getTextFromElement(By.xpath("//h1[contains(text(),'Thank you')]"));
        String etext = "Thank you";
        Assert.assertEquals(etext, atext);
        String aOrder = getTextFromElement(By.xpath("//strong[contains(text(),'Your order has been successfully processed!')]"));
        String eOrder = "Your order has been successfully processed!";
        Assert.assertEquals(eOrder, aOrder);

        clickOnElement(By.xpath("//button[contains(text(),'Continue')]"));

        String eWelcome = "Welcome to our store";
        String aWelcome = getTextFromElement(By.xpath("//h2[contains(text(),'Welcome to our store')]"));
        Assert.assertEquals(eWelcome, aWelcome);

        clickOnElement(By.xpath("//a[contains(text(),'Log out')]"));
        System.out.println(driver.getCurrentUrl());
        String eurl = "https://demo.nopcommerce.com/";
        String aurl = driver.getCurrentUrl();
        Assert.assertEquals(eurl, aurl);

    }

    @After
    public void tearDown() {

        closeBrowser();
    }

}
