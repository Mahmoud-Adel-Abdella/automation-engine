package clients.daftra.pages;

import engine.base.BasePage;
import engine.utils.ClickUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class RegisterPage extends BasePage {
    public RegisterPage(WebDriver driver) {
        super(driver);
    }

    private final By signUpLocator = By.xpath("/html/body/div[1]/header/div/ul/li[15]/a");
    private final By businessNameLocator = By.xpath("//*[@id=\"SiteBusinessName\"]");
    private final By emailLocator = By.xpath("//*[@id=\"SiteEmail\"]");
    private final By phoneNumberLocator = By.xpath("//*[@id=\"SitePhone2\"]");
    private final By passwordLocator = By.xpath("//*[@id=\"SitePassword\"]");
    private final By getStartedBtnLocator = By.xpath("//*[@id=\"SiteAddForm\"]/div[8]/div[2]/button");

    private final By errorMSG1Locator =By.xpath("//*[@id=\"business_error\"]/div");
    private final By errorMSG2Locator =By.xpath("//*[@id=\"email_error\"]/div");
    private final By errorMSG3Locator =By.xpath("//*[@id=\"sub_error\"]/div");

    private final By continueBtnLocator = By.xpath("//*[@id=\"go-to-step-2\"]");
    private final By fNameLocator = By.xpath("//*[@id=\"SiteFirstName\"]");
    private final By lNameLocator = By.xpath("//*[@id=\"SiteLastName\"]");
    private final By jobLocator = By.xpath("//*[@id=\"SitePosition-selectized\"]");
    private final By companySizeLocator = By.xpath("//*[@id=\"SiteCompanySize-selectized\"]");

    private final By errorMSG4Locator =By.xpath("//*[@id=\"stepper-1\"]/div/div[1]/div[2]/div");
    private final By errorMSG5Locator =By.xpath("//*[@id=\"stepper-1\"]/div/div[1]/div[3]/div[2]/div[2]");
    private final By errorMSG6Locator =By.xpath("//*[@id=\"stepper-1\"]/div/div[1]/div[3]/div[1]/div[2]");

    private final By addressLocator = By.xpath("//*[@id=\"SiteAddress1\"]");
    private final By cityLocator = By.xpath("//*[@id=\"SiteCity\"]");
    private final By stateLocator = By.xpath("//*[@id=\"SiteState\"]");
    private final By continueBtn2Locator = By.xpath("//*[@id=\"stepper-2\"]/div[2]/div[2]/button");

    private final By errorMSG7Locator =By.xpath("//*[@id=\"stepper-2\"]/div[2]/div[1]/div/div[2]/div");
    private final By errorMSG8Locator =By.xpath("//*[@id=\"stepper-2\"]/div[2]/div[1]/div/div[3]/div");

    private final By skipIndustryBtnLocator = By.xpath("/html/body/div[1]/div/div[5]/div[2]/div/div/div[1]/div[2]/div/a");
    private final By saveInfoBtnLocator = By.xpath("//*[@id=\"main-content\"]/div[3]/div/form/div[1]/div/div/div[2]/div/button");
    private final By welcomeMessageLocator = By.xpath("//*[@id=\"message-block\"]");
    private final By dashboardElementLocator = By.xpath("//*[@id=\"main-nav\"]/div[2]/div[1]/div[2]/div/div/div/div/ul/li[1]/a");


    public void navigateToSignUpPage(){
        ClickUtils.guaranteedClick(signUpLocator);
    }

    public boolean[] validateErrorPage1 (){
        ClickUtils.guaranteedClick(getStartedBtnLocator);

        return new boolean[]{
            driver.findElement(errorMSG1Locator).isDisplayed(),
            driver.findElement(errorMSG2Locator).isDisplayed(),
            driver.findElement(errorMSG3Locator).isDisplayed()
        };
    }

    public void fillCredentials1(String BN , String email, String phoneNumber , String password){
        write(businessNameLocator , BN);
        write(emailLocator , email);
        write(phoneNumberLocator , phoneNumber);
        write(passwordLocator , password);
        ClickUtils.guaranteedClick(getStartedBtnLocator);
    }

    public boolean[] validateErrorPage2 (){
        ClickUtils.guaranteedClick(continueBtnLocator);

        return new boolean[]{
                driver.findElement(errorMSG4Locator).isDisplayed(),
                driver.findElement(errorMSG5Locator).isDisplayed(),
                driver.findElement(errorMSG6Locator).isDisplayed()
        };
    }

    public void fillCredentials2(String fName , String lName){
        write(fNameLocator , fName);
        write(lNameLocator , lName);
        selectCustomDropdown(jobLocator, "مسؤول الحسابات");
        selectCustomDropdown(companySizeLocator , "عامل حر");
        ClickUtils.guaranteedClick(continueBtnLocator);
    }

    public boolean[] validateErrorPage3 (){
        ClickUtils.guaranteedClick(continueBtn2Locator);

        return new boolean[]{
                driver.findElement(errorMSG7Locator).isDisplayed(),
                driver.findElement(errorMSG8Locator).isDisplayed(),
        };
    }

    public void fillCredentials3(String address, String city , String state){
        write(addressLocator, address);
        write(cityLocator , city);
        write(stateLocator , state);
        ClickUtils.guaranteedClick(continueBtn2Locator);
    }

    public void skipIndustrySelectionAndSaveInfo (){
        ClickUtils.guaranteedClick(skipIndustryBtnLocator);
        ClickUtils.guaranteedClick(saveInfoBtnLocator);
    }

    public boolean[] verifyDashboardRedirect(){
        return new boolean[]{
            driver.findElement(welcomeMessageLocator).isDisplayed(),
            driver.findElement(dashboardElementLocator).isDisplayed()
        };
    }
}
