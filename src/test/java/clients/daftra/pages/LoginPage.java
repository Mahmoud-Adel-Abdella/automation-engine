package clients.daftra.pages;

import engine.base.BasePage;
import engine.utils.ClickUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {
    public LoginPage(WebDriver driver) {
        super(driver);
    }

    String loginPageURL = "https://www.daftra.com/users/login";
    String forgetPasswordPageURL = "https://www.daftra.com/users/forget_password";

    private final By emailLocator = By.xpath("//*[@id=\"UserEmail\"]");
    private final By passwordLocator = By.xpath("//*[@id=\"UserPassword\"]");
    private final By signInBtnLocator = By.xpath("/html/body/div[1]/div[3]/section/div[3]/div/form/div[4]/div[2]/button");
    private final By signInBtn2Locator = By.xpath("/html/body/div[2]/div[3]/section/div[3]/div/form/div[4]/div[2]/button");
    private final By dashBoardElementLocator = By.xpath("/html/body/div[6]/div/div/div[2]/div/ul/li[1]/a");

    private final By credentialsEmptyErrorMSGLocator = By.xpath("//*[@id=\"flashMessage\"]");
    private final By credentialsInvalidErrorMSGLocator = By.xpath("//*[@id=\"flashMessage\"]");

    public final By securityCodeLocator = By.xpath("//*[@id=\"UserSecurityCode\"]");
    public final By continueBtnLocator = By.xpath("/html/body/div[1]/section/div[3]/div/form/div[4]/div/button");
    public final By securityCodeErrorLocator = By.xpath("//*[@id=\"security_code_error\"]/div");


    public void navigateToLoginPage() {
        navigateToAndVerify(loginPageURL);
    }

    public void fillCredentials(String email, String password) {
        write(emailLocator, email);
        write(passwordLocator, password);
        ClickUtils.guaranteedClick(signInBtnLocator);
    }

    public boolean checkDashboardRedirection() {
        return driver.findElement(dashBoardElementLocator).isDisplayed();
    }


    //Case 2
    public boolean verifyEmptyCredentialsValidation() {
        ClickUtils.guaranteedClick(signInBtnLocator);

        return driver.findElement(credentialsEmptyErrorMSGLocator).isDisplayed();
    }

    public boolean verifyInvalidCredentialsValidation(String email, String password) {
        write(emailLocator, email);
        write(passwordLocator, password);
        ClickUtils.guaranteedClick(signInBtn2Locator);

        return driver.findElement(credentialsInvalidErrorMSGLocator).isDisplayed();
    }

    public boolean verifyRemainingOnLoginPage(){
        return driver.getCurrentUrl().contains("https://www.daftra.com/users/login");
    }

    //Case 3
    public void navigateToForgetPasswordPage(){
        navigateToAndVerify(forgetPasswordPageURL);
    }

    public void fillForgetPageCredentials(String email , String securityCode){
        write(emailLocator , email);
        write(securityCodeLocator , securityCode);
        ClickUtils.guaranteedClick(continueBtnLocator);
    }

    public boolean checkInvalidCodeValidation(){
        return driver.findElement(securityCodeErrorLocator).isDisplayed();
    }

    public boolean verifyPasswordResetRequestNotSubmitted(){
        return driver.getCurrentUrl().contains(forgetPasswordPageURL);
    }
}
