package homepage;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import utilities.Utility;

public class TopMenuTest extends Utility {

    String baseUrl = "https://demo.nopcommerce.com/";

    @Before
    public void setUp() {

        openBrowser(baseUrl);
    }

    public void selectMenu(String menu){

        //click on menu
        clickOnElement(By.xpath("//ul[@class='top-menu notmobile']//a[normalize-space() = '" + menu + "']"));
        //verify navigation of page
        String expected = menu;
        String actual = driver.findElement(By.xpath("//h1[contains(text(),'" + menu + "')]")).getText();
        Assert.assertEquals(expected,actual);

    }

    @Test
    public void verifyPageNavigation(){

        selectMenu("Computers");
        selectMenu("Electronics");
        selectMenu("Apparel");
        selectMenu("Digital downloads");
        selectMenu("Books");
        selectMenu("Jewelry");
        selectMenu("Gift Cards");

    }

    @After
    public void tearDown() {

       closeBrowser();
    }

}
