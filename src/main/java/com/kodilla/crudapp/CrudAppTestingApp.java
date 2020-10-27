package com.kodilla.crudapp;

import com.kodilla.config.WebDriverConfig;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class CrudAppTestingApp {
    // XPath-Absolute
    public static final String XPATH_INPUT = "//html/body/main/section/form/fieldset/input";
    public static final String XPATH_TEXTAREA = "//html/body/main/section/form/fieldset[2]/textarea";
    // XPath-Position
    public static final String XPATH_WAIT_FOR = "//select[1]";
    // XPath-Relative
    public static final String XPATH_SELECT = "//div[contains(@class, \"tasks-container\")]/form/div/fieldset/select[1]";

    public static void main(String[] args) {
        WebDriver driver = WebDriverConfig.getDriver(WebDriverConfig.CHROME);
        driver.get("https://zagpatryk.github.io/");

        WebElement taskNameField = driver.findElement(By.xpath(XPATH_INPUT));
        taskNameField.sendKeys("Test auto task");

        WebElement textAreaField = driver.findElement(By.xpath(XPATH_TEXTAREA));
        textAreaField.sendKeys("Test auto text");

        while (!driver.findElement(By.xpath(XPATH_WAIT_FOR)).isDisplayed());

        WebElement selectCombo = driver.findElement(By.xpath(XPATH_SELECT));
        Select selectBoard = new Select(selectCombo);
        selectBoard.selectByIndex(1);
    }
}
