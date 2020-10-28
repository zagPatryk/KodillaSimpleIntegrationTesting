package com.kodilla.crudapp;

import com.kodilla.config.WebDriverConfig;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Random;

import static org.junit.Assert.*;

public class CrudAppTestingAppTestSuite {
    private static final String BASE_URL = "https://zagpatryk.github.io";
    private WebDriver driver;
    private Random generator;

    @Before
    public void initTests() {
        driver = WebDriverConfig.getDriver(WebDriverConfig.CHROME);
        driver.get(BASE_URL);
        generator = new Random();
    }

    private String createCrudAppTestTask() throws InterruptedException {
        final String XPATH_TASK_NAME = "//form[contains(@action, \"createTask\")]/fieldset[1]/input";
        final String XPATH_TASK_CONTENT = "//form[contains(@action, \"createTask\")]/fieldset[2]/textarea";
        final String XPATH_TASK_BUTTON = "//form[contains(@action, \"createTask\")]/fieldset[3]/button";
        String taskName = "Task number " + generator.nextInt(100000);
        String taskContent = taskName + " content";

        WebElement name = driver.findElement(By.xpath(XPATH_TASK_NAME));
        name.sendKeys(taskName);

        WebElement content = driver.findElement(By.xpath(XPATH_TASK_CONTENT));
        content.sendKeys(taskContent);

        WebElement adaButton = driver.findElement(By.xpath(XPATH_TASK_BUTTON));
        adaButton.click();
        Thread.sleep(2000);

        return taskName;
    }

    private void sendTaskTaskToTrello(String taskName) throws InterruptedException {
        driver.navigate().refresh();

        WebDriverWait wait = new WebDriverWait(driver, 150);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//select[1]")));

        driver.findElements(By.xpath("//form[@class=\"datatable__row\"]")).stream()
                .filter(anyForm ->
                        anyForm.findElement(By.xpath(".//p[@class=\"datatable__field-value\"]"))
                                .getText().equals(taskName))
                .forEach(theForm -> {
                    WebElement selectElement = theForm.findElement(By.xpath(".//select[1]"));
                    Select select = new Select(selectElement);
                    select.selectByIndex(1);

                    WebElement buttonCreateCard =
                            theForm.findElement(By.xpath(".//button[contains(@class, \"card-creation\")]"));
                    buttonCreateCard.click();
                });
        Thread.sleep(2000);

        Alert alert = driver.switchTo().alert();
        alert.accept();
    }

    private boolean checkTaskExistsInTrello(String taskName) throws InterruptedException {
        final String TRELLO_URL = "https://trello.com/login";
        boolean result = false;
        WebDriver driverTrello = WebDriverConfig.getDriver(WebDriverConfig.CHROME);
        driverTrello.get(TRELLO_URL);

        driverTrello.findElement(By.id("user")).sendKeys("zag.patryk@gmail.com");
        driverTrello.findElement(By.id("password")).sendKeys("kodilla2");
        WebElement webElement = driverTrello.findElement(By.id("login"));
        webElement.submit();

        Thread.sleep(4000);

        WebElement boardKodilla = driverTrello.findElement(
                By.xpath("//div[contains(@class, \"board-tile-details is-badged\")]/div[@title=\"Kodilla\"]"));
        boardKodilla.click();

        Thread.sleep(4000);

        result = driverTrello.findElements(By.xpath("//span")).stream()
                .anyMatch(theSpan -> theSpan.getText().equals(taskName));

        driverTrello.close();

        return result;
    }

    public void removeTaskAfterTest(String taskName) throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, 150);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//select[1]")));

        driver.findElements(By.xpath("//form[@class=\"datatable__row\"]")).stream()
                .filter(anyForm ->
                        anyForm.findElement(By.xpath(".//p[@class=\"datatable__field-value\"]"))
                                .getText().equals(taskName))
                .forEach(theForm -> {
                    WebElement deleteButton =
                            theForm.findElement(By.xpath(".//button[4]"));
                    deleteButton.click();
                });

        Thread.sleep(2000);
    }

    @Test
    public void shouldCreateTrelloCard() throws InterruptedException {
        String taskName = createCrudAppTestTask();
        sendTaskTaskToTrello(taskName);
        assertTrue(checkTaskExistsInTrello(taskName));
        removeTaskAfterTest(taskName);
    }

    @After
    public void closeDriverAfterTest() {
        driver.close();
    }
}
