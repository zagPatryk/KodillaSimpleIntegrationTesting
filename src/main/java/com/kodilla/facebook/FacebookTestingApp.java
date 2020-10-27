package com.kodilla.facebook;

import com.kodilla.config.WebDriverConfig;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class FacebookTestingApp {
    public static final String XPATH_WAIT_COOKIES = "//*[@data-testid=\"cookie-policy-banner\"]";
    public static final String XPATH_ACCEPT_COOKIES = "//*[@data-testid=\"cookie-policy-banner\"]/div[2]/div/div/div/div/div[3]/button[2]";
    public static final String XPATH_CREATE_NEW_ACCOUNT = "//*[@data-testid=\"open-registration-form-button\"]";
    public static final String XPATH_WAIT_ACCOUNT = "//*[@id=\"reg_box\"]";
    public static final String XPATH_FNAME = "//div[contains(@id, \"fullname_field\")]/div[1]/div/div[1]/input";
    public static final String XPATH_DATE_D = "//*[@id=\"day\"]";
    public static final String XPATH_DATE_M = "//*[@id=\"month\"]";
    public static final String XPATH_DATE_Y = "//*[@id=\"year\"]";
    public static final String XPATH_SEX = "//input[@name=\"sex\"]";

    public static void main(String[] args) {
        WebDriver driver = WebDriverConfig.getDriver(WebDriverConfig.CHROME);
        driver.get("https://www.facebook.com/");

        WebDriverWait wait = new WebDriverWait(driver, 15);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(XPATH_WAIT_COOKIES)));

        WebElement acceptCookies = driver.findElement(By.xpath(XPATH_ACCEPT_COOKIES));
        acceptCookies.click();

        WebElement createNewAccount = driver.findElement(By.xpath(XPATH_CREATE_NEW_ACCOUNT));
        createNewAccount.click();

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(XPATH_WAIT_ACCOUNT)));

        WebElement firstNameField = driver.findElement(By.xpath(XPATH_FNAME));
        firstNameField.sendKeys("FNAM");

        WebElement selectDay = driver.findElement(By.xpath(XPATH_DATE_D));
        Select selectD = new Select(selectDay);
        selectD.selectByValue("2");

        WebElement selectMonth = driver.findElement(By.xpath(XPATH_DATE_M));
        Select selectM = new Select(selectMonth);
        selectM.selectByValue("7");

        WebElement selectYear = driver.findElement(By.xpath(XPATH_DATE_Y));
        Select selectY = new Select(selectYear);
        selectY.selectByValue("1999");

        WebElement selectSex = driver.findElement(By.xpath(XPATH_SEX));
        selectSex.click();
    }
}
