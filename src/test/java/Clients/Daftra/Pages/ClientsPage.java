package Clients.Daftra.Pages;

import Engine.Base.BasePage;
import Engine.Utils.ClickUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.util.ArrayList;
import java.util.Objects;

public class ClientsPage extends BasePage {
    public ClientsPage(WebDriver driver) {
        super(driver);
    }

    String URL = "https://mahaammoud224.daftra.com/";

    private final By enterToDashboardLocator = By.xpath("/html/body/div[6]/div/div/div[1]/div[1]/div[1]/div[1]/a[1]");
    private final By clientsListBtnLocator = By.xpath("//*[@id=\"main-nav\"]/div[2]/div[1]/div[2]/div/div/div/div/ul/li[3]/a");
    private final By clientsMenagementBtnLocator = By.xpath("//*[@id=\"main-nav\"]/div[2]/div[1]/div[2]/div/div/div/div/ul/li[3]/div/ul/li[1]/a");
    private final By addClientBtnLocator = By.xpath("//*[@id=\"main-nav\"]/div[2]/div[1]/div[2]/div/div/div/div/ul/li[3]/div/ul/li[2]/a");

    private final By fullNameInputLocator = By.xpath("//*[@id=\"ClientBusinessName\"]");
    private final By fullNameInputErrorLocator = By.xpath("//div[@class='error-message']");
    private final By emailInputLocator = By.xpath("//*[@id=\"ClientEmail\"]");

    private final By saveBtnLocator = By.xpath("//*[@id=\"submitBtn\"]");
    private final By successMSGLocator = By.xpath("//*[@id=\"flashMessage\"]");

    private final By randomClientPageLocator = By.xpath("//table[@data-lt='true']//td[1]");
    private final By editClientBtnLocator = By.xpath("(//a[@class='btn btn-default btn-sm btn-5 '])[1]");

    private final By editPagePhoneLocator = By.xpath("//*[@id=\"ClientPhone1\"]");
    private final By editPageCityLocator = By.xpath("//input[@id='ClientCity']");

    public void navigateToDashboard() {
        ClickUtils.guaranteedClick(enterToDashboardLocator);
    }

    public void navigateToAddClientPage() {
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));

        ClickUtils.guaranteedClick(clientsListBtnLocator);
        ClickUtils.guaranteedClick(addClientBtnLocator);
    }

    public void fillValidCredentials(String fullName, String email) {
        write(fullNameInputLocator, fullName);
        write(emailInputLocator, email);
        ClickUtils.guaranteedClick(saveBtnLocator);
    }

    public boolean verifySuccessAddition() {
        return driver.findElement(successMSGLocator).isDisplayed();
    }

    public void navigateToClientPage() {
        ClickUtils.guaranteedClick(clientsMenagementBtnLocator);
    }

    public boolean[] verifyClientValidData(String fullName, String email) {
        return new boolean[]{
                checkPageContains(fullName),
                checkPageContains(email)
        };
    }

    public void fillInvalidCredentials() {
        ClickUtils.guaranteedClick(saveBtnLocator);
    }

    public boolean[] verifyValidationAndRemaining() {
        return new boolean[]{
                driver.findElement(fullNameInputErrorLocator).isDisplayed(),
                Objects.requireNonNull(driver.getCurrentUrl()).contains("https://mahaammoud224.daftra.com/owner/clients/add")
        };
    }

    public void navigateToClientEditPage(){
        ClickUtils.guaranteedClick(clientsMenagementBtnLocator);
        ClickUtils.guaranteedClick(randomClientPageLocator);
        ClickUtils.guaranteedClick(editClientBtnLocator);
    }

    public void editClientCredentials(String phoneNumber , String city){
        write(editPagePhoneLocator , phoneNumber);
        write(editPageCityLocator , city);
        ClickUtils.guaranteedClick(saveBtnLocator);
    }

    public boolean[] verifySuccessEdit(String phoneNumber , String city){
        return  new boolean[]{
                checkPageContains(phoneNumber),
                checkPageContains(city),
                checkDisplayed(successMSGLocator)
        };
    }
}
