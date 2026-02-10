package Tests;

import Pages.RegisterPage;
import Utilities.DataFactory;
import Utilities.TestSummaryListener;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import Utilities.TestListeners;

@Listeners({TestListeners.class, TestSummaryListener.class})
public class RegisterTest extends BaseTest{
    RegisterPage registerPage;

    @Test
    public void registerTest() throws InterruptedException {
        checkValidation1();
        fillCredentials1();
        Thread.sleep(2000);
        checkValidation2();
        fillCredentials2();
        checkValidation3();
        fillCredentials3();
        skipIndustryAndSaveInfo();
        verifyDashboardRedirect();
    }

    public void checkValidation1(){
        registerPage = new RegisterPage(driver);

        registerPage.navigateToSignUpPage();
        Assert.assertTrue(registerPage.validateErrorPage1()[0]);
        Assert.assertTrue(registerPage.validateErrorPage1()[1]);
        Assert.assertTrue(registerPage.validateErrorPage1()[2]);
    }

    public void fillCredentials1() {
        registerPage = new RegisterPage(driver);

        registerPage.fillCredentials1(faker.company().name() , DataFactory.getEmail(),
                DataFactory.getPhoneNumber() , DataFactory.getPassword());
    }

    public void checkValidation2(){
        registerPage = new RegisterPage(driver);

        Assert.assertTrue(registerPage.validateErrorPage2()[0]);
        Assert.assertTrue(registerPage.validateErrorPage2()[1]);
        Assert.assertTrue(registerPage.validateErrorPage2()[2]);
    }

    public void fillCredentials2(){
        registerPage = new RegisterPage(driver);

        registerPage.fillCredentials2(DataFactory.getFirstName(), DataFactory.getLastName());
    }

    public void checkValidation3(){
        registerPage = new RegisterPage(driver);

        Assert.assertTrue(registerPage.validateErrorPage3()[0]);
        Assert.assertTrue(registerPage.validateErrorPage3()[1]);
    }

    public void fillCredentials3(){
        registerPage = new RegisterPage(driver);

        registerPage.fillCredentials3(DataFactory.getAddress() , DataFactory.getCity() , DataFactory.getState());
    }

    public void skipIndustryAndSaveInfo(){
        registerPage = new RegisterPage(driver);

        registerPage.skipIndustrySelectionAndSaveInfo();
    }

    public void verifyDashboardRedirect(){
        registerPage = new RegisterPage(driver);

        registerPage.verifyDashboardRedirect();
    }
}
