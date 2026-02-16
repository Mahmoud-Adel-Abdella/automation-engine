package clients.daftra.tests;

import engine.base.BaseTest;
import clients.daftra.pages.LoginPage;
import engine.utils.ConfigManager;
import engine.utils.DataFactory;
import engine.listeners.TestListeners;
import engine.listeners.TestSummaryListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners({engine.listeners.TestListeners.class, engine.listeners.TestSummaryListener.class})
public class LoginTest extends BaseTest {

    LoginPage loginPage;

    @Test()
    public void validLoginTest(){
        String username = ConfigManager.getUsername();
        String password = ConfigManager.getPassword();

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
