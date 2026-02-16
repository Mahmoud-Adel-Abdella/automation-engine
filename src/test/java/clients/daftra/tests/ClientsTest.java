package clients.daftra.tests;

import engine.base.BaseTest;
import engine.listeners.TestListeners;
import engine.listeners.TestSummaryListener;
import clients.daftra.pages.ClientsPage;
import clients.daftra.pages.LoginPage;
import engine.utils.ConfigManager;
import engine.utils.DataFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners({TestListeners.class, TestSummaryListener.class})
public class ClientsTest extends BaseTest {

    ClientsPage clientsPage;
    LoginPage loginPage;

    String email ;
    String password ;

    @BeforeClass
    public void classSetup(){
        String email = ConfigManager.getUsername();
        String password = ConfigManager.getPassword();
    }

    @Test()
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
