package engine.utils;

import com.github.javafaker.Faker;

public final class DataFactory {
    private static final Faker faker = new Faker();

    private static final String fName = faker.name().firstName();
    private static final String lName = faker.name().lastName();
    private static final String phoneNumber = faker.phoneNumber().subscriberNumber(8);
    private static final String email = faker.internet().emailAddress();
    private static final String fullName = faker.name().fullName();
    private static final String password = "Pa$$w0rd22w";
    private static final String address = faker.address().fullAddress();
    private static final String extraAddress = faker.address().buildingNumber();
    private static final String city = faker.address().city();
    private static final String state = faker.address().state();


    public static String getFirstName() {
        return fName;
    }

    public static String getLastName() {
        return lName;
    }

    public static String getFullName() {
        return fullName;
    }

    public static String getPhoneNumber() {
        return "10" + phoneNumber;
    }

    public static String getEmail() {
        return email;
    }

    public static String getPassword() {
        return password;
    }

    public static String getAddress() {
        return address;
    }

    public static String getExtraAddress() {
        return extraAddress;
    }

    public static String getCity (){return city;}

    public static String getState (){return state;}
}
