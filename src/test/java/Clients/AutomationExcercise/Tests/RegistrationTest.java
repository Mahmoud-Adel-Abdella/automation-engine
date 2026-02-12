package Clients.AutomationExcercise.Tests;

import Clients.AutomationExcercise.Pages.RegistrationPage;
import Engine.Base.BaseTest;
import Engine.Listeners.TestListeners;
import Engine.Listeners.TestSummaryListener;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners({TestListeners.class, TestSummaryListener.class})
public class RegistrationTest extends BaseTest {
    RegistrationPage register;

    @Test
    public void RegisterTest(){
        register = new RegistrationPage(driver);

        register.fillRegister(faker.internet().emailAddress() , faker.name().name());

        register.fillGender();
        register.fillPass(faker.internet().password());

        register.selectDateOfBirth();

        register.fillFullName(faker.name().firstName() , faker.name().lastName());

        register.fillAddress(faker.address().fullAddress());
        register.selectCountry();
        register.fillState(faker.address().state());
        register.fillCity(faker.address().city());
        register.fillZipCode(faker.address().zipCode());

        register.fillMobileNumber(faker.phoneNumber().phoneNumber());

        register.submitRegister();
    }
}
