package Clients.Daftra.Tests;

import Engine.Base.BaseTest;
import Clients.Daftra.Pages.LoginPage;
import Engine.Listeners.TestListeners;
import Engine.Listeners.TestSummaryListener;
import Engine.Utils.ConfigManager;
import Engine.Utils.DataFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners({TestListeners.class, TestSummaryListener.class})
public class LoginTest extends BaseTest {
    private static final Logger log = LoggerFactory.getLogger(LoginTest.class);
    LoginPage loginPage;

    String username = ConfigManager.get("credentials.username");
    String password = ConfigManager.get("credentials.password");

    @Test(groups = {"login","smoke","regression"})
    public void validLoginTest(){
        loginPage = new LoginPage(driver);

        loginPage.navigateToLoginPage();

        loginPage.fillCredentials(username , password);

        Assert.assertTrue(loginPage.checkDashboardRedirection());
    }

    @Test
    public void invalidLoginTest(){
        loginPage = new LoginPage(driver);

        loginPage.navigateToLoginPage();

        Assert.assertTrue(loginPage.verifyEmptyCredentialsValidation());

        Assert.assertTrue(loginPage.verifyInvalidCredentialsValidation(DataFactory.getEmail() , faker.internet().password()));

        Assert.assertTrue(loginPage.verifyRemainingOnLoginPage());
    }

    @Test
    public void invalidSecurityCodeValidation(){
        loginPage = new LoginPage(driver);

        loginPage.navigateToForgetPasswordPage();

        loginPage.fillForgetPageCredentials(DataFactory.getEmail() , "abcdefg");

        Assert.assertTrue(loginPage.checkInvalidCodeValidation());

        Assert.assertTrue(loginPage.verifyPasswordResetRequestNotSubmitted());
    }
}
