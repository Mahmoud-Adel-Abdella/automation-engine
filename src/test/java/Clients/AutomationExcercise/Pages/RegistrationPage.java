package Clients.AutomationExcercise.Pages;

import Engine.Base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class RegistrationPage extends BasePage {
    public RegistrationPage(WebDriver driver) {
        super(driver);
    }

    //By locators
    private final By nameInputLocator = By.cssSelector("[data-qa=\"signup-name\"]");
    private final By emailInputLocator = By.cssSelector("[data-qa=\"signup-email\"]");
    private final By signUpBtnLocator = By.cssSelector("[data-qa=\"signup-button\"]");

    private final By genderInputLocator = By.cssSelector("[class=\"clearfix\"] [class=\"radio-inline\"] label");
    private final By passInputLocator = By.xpath("//*[@data-qa=\"password\"]");

    private final By daysLocator = By.xpath("//*[@data-qa=\"days\"]");
    private final By monthsLocator = By.xpath("//*[@data-qa=\"months\"]");
    private final By yearsLocator = By.xpath("//*[@data-qa=\"years\"]");

    private final By fNameLocator = By.xpath("//*[@data-qa=\"first_name\"]");
    private final By lNameLocator = By.xpath("//*[@data-qa=\"last_name\"]");

    private final By addressLocator = By.xpath("//*[@data-qa=\"address\"]");
    private final By countryLocator = By.xpath("//*[@data-qa=\"country\"]");
    private final By stateLocator = By.xpath("//*[@data-qa=\"state\"]");
    private final By cityLocator = By.xpath("//*[@data-qa=\"city\"]");

    private final By zipCodeLocator = By.xpath("//*[@data-qa=\"zipcode\"]");
    private final By mobileNumberLocator = By.xpath("//*[@data-qa=\"mobile_number\"]");

    private final By submitBtnLocator = By.xpath("//*[@dataa-qa=\"create-account\"]");

    public void fillRegister(String email, String name) {
        write(nameInputLocator, name);
        write(emailInputLocator, email);
        click(signUpBtnLocator);
    }

    public void fillGender() {
//        myClick(genderInputLocator);

        radioRandomSelect(genderInputLocator);
    }

    public void fillPass(String password) {
        write(passInputLocator, password);
    }

    public void selectDateOfBirth() {
        dropdownRandomSelect(daysLocator);
        dropdownRandomSelect(monthsLocator);
        dropdownRandomSelect(yearsLocator);
    }

    public void fillFullName(String firstName, String lastName) {
        write(fNameLocator, firstName);
        write(lNameLocator, lastName);
    }

    public void fillAddress(String address) {
        write(addressLocator, address);
    }

    public void selectCountry() {
        dropdownRandomSelect(countryLocator);
    }

    public void fillState(String state) {
        write(stateLocator, state);
    }

    public void fillCity(String city) {
        write(cityLocator, city);
    }

    public void fillZipCode(String zipCode) {
        write(zipCodeLocator, zipCode);
    }

    public void fillMobileNumber(String mobileNumber) {
        write(mobileNumberLocator, mobileNumber);
    }

    public void submitRegister(){
        click(submitBtnLocator);
    }

}
