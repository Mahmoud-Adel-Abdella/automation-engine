package Clients.Daftra.Tests;

import Engine.Base.BaseTest;
import Engine.Listeners.TestListeners;
import Engine.Listeners.TestSummaryListener;
import Clients.Daftra.Pages.ClientsPage;
import Clients.Daftra.Pages.LoginPage;
import Engine.Utils.ConfigManager;
import Engine.Utils.DataFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners({TestListeners.class, TestSummaryListener.class})
public class ClientsTest extends BaseTest {
    private static final Logger log = LoggerFactory.getLogger(ClientsTest.class);
    ClientsPage clientsPage;
    LoginPage loginPage;

    String email = ConfigManager.get("credentials.username");
    String password = ConfigManager.get("credentials.password");

    @Test(groups = {"smoke"})
    public void validAddNewClient(){
        clientsPage = new ClientsPage(driver);
        loginPage = new LoginPage(driver);

        loginPage.navigateToLoginPage();
        loginPage.fillCredentials(email , password);

        clientsPage.navigateToDashboard();
        clientsPage.navigateToAddClientPage();
        clientsPage.fillValidCredentials(DataFactory.getFullName() , DataFactory.getEmail());

        Assert.assertTrue(clientsPage.verifySuccessAddition());

        clientsPage.navigateToClientPage();

        Assert.assertTrue(clientsPage.verifyClientValidData(DataFactory.getFullName(), DataFactory.getEmail())[0]);
        Assert.assertTrue(clientsPage.verifyClientValidData(DataFactory.getFullName(), DataFactory.getEmail())[1]);
    }

    @Test
    public void invalidAddNewClient(){
        clientsPage = new ClientsPage(driver);
        loginPage = new LoginPage(driver);

        loginPage.navigateToLoginPage();
        loginPage.fillCredentials(email , password);

        clientsPage.navigateToDashboard();
        clientsPage.navigateToAddClientPage();

        clientsPage.fillInvalidCredentials();
        Assert.assertTrue(clientsPage.verifyValidationAndRemaining()[0]);
        Assert.assertTrue(clientsPage.verifyValidationAndRemaining()[1]);
    }

    @Test
    public void editClientTest(){
        clientsPage = new ClientsPage(driver);
        loginPage = new LoginPage(driver);

        loginPage.navigateToLoginPage();
        loginPage.fillCredentials(email , password);

        clientsPage.navigateToDashboard();
        clientsPage.navigateToAddClientPage();
        clientsPage.fillValidCredentials(DataFactory.getFullName() , DataFactory.getEmail());

        clientsPage.navigateToClientEditPage();
        clientsPage.editClientCredentials(DataFactory.getPhoneNumber() , DataFactory.getCity());
        Assert.assertTrue(clientsPage.verifySuccessEdit(DataFactory.getPhoneNumber() , DataFactory.getCity())[0]);
        Assert.assertTrue(clientsPage.verifySuccessEdit(DataFactory.getPhoneNumber() , DataFactory.getCity())[1]);
        Assert.assertTrue(clientsPage.verifySuccessEdit(DataFactory.getPhoneNumber() , DataFactory.getCity())[2]);
    }
}
