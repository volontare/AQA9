package ru.netology;

import com.github.javafaker.Faker;
import lombok.Data;
import lombok.Value;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Data

public class DataGenerator {

    private DataGenerator() {
    }

    public static String generateDate(int days) {
        String date = LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        return date;
    }

    public static String generateCity(String locale) {
        Faker faker = new Faker(new Locale(locale));
        String city = faker.options().option("Москва", "Санкт-Петербург", "Самара", "Екатеринбург", "Казань", "Орёл", "Хабаровск");
        return city;
    }

    public static String generateInvalidCity(String locale) {
        Faker faker = new Faker(new Locale(locale));
        String city = faker.address().city();
        return city;
    }

    public static String generateName(String locale) {
        Faker faker = new Faker(new Locale(locale));
        String name = faker.name().fullName();
        return name;
    }

    public static String generateNameWithLetterYo(String locale) {
        Faker faker = new Faker(new Locale(locale));
        String name = faker.name().fullName().concat("ё");
        return name;
    }

    public static String generateNameWithInvalidSymbol(String locale) {
        Faker faker = new Faker(new Locale(locale));
        String name = faker.name().fullName().concat("$");
        return name;
    }

    public static String generateNameWithDash(String locale) {
        Faker faker = new Faker(new Locale(locale));
        String name = faker.name().fullName().concat("-");
        return name;
    }

    public static String generatePhone(String locale) {
        Faker faker = new Faker(new Locale(locale));
        String phone = faker.numerify("+79#########");
        return phone;
    }

    public static String generateInvalidPhoneWithIncorrectStartNumber(String locale) {
        Faker faker = new Faker(new Locale(locale));
        String phone = faker.numerify("###########");
        return phone;
    }

    public static String generateInvalidPhoneWhenDeficiencyNumbers(String locale) {
        Faker faker = new Faker(new Locale(locale));
        String phone = faker.numerify("+79#####");
        return phone;
    }


    public static class Registration {

        private Registration() {
        }

        public static UserInfo generateUser(String locale) {
            UserInfo user = new UserInfo(generateCity(locale), generateName(locale), generatePhone(locale));
            return (UserInfo) user;
        }
    }

    public static class ValidNameRegistrationWithDash {

        private ValidNameRegistrationWithDash() {
        }

        public static UserInfo generateValidUser(String locale) {
            UserInfo user = new UserInfo(generateCity(locale), generateNameWithDash(locale), generatePhone(locale));
            return (UserInfo) user;
        }
    }

    public static class ValidNameRegistrationWithLetterYo {

        private ValidNameRegistrationWithLetterYo() {
        }

        public static UserInfo generateInvalidUser(String locale) {
            UserInfo user = new UserInfo(generateCity(locale), generateNameWithLetterYo(locale), generatePhone(locale));
            return (UserInfo) user;
        }
    }

    public static class InvalidRegistrationName {

        private InvalidRegistrationName() {
        }

        public static UserInfo generateInvalidUser(String locale) {
            UserInfo user = new UserInfo(generateCity(locale), generateName("us"), generatePhone(locale));
            return (UserInfo) user;
        }
    }

    public static class InvalidNameRegistrationWithInvalidSymbol {

        private InvalidNameRegistrationWithInvalidSymbol() {
        }

        public static UserInfo generateInvalidUser(String locale) {
            UserInfo user = new UserInfo(generateCity(locale), generateNameWithInvalidSymbol(locale), generatePhone(locale));
            return (UserInfo) user;
        }
    }

    public static class InvalidRegistrationCity {

        private InvalidRegistrationCity() {
        }

        public static UserInfo generateInvalidUser(String locale) {
            UserInfo user = new UserInfo(generateInvalidCity("ua"), generateName(locale), generatePhone(locale));
            return (UserInfo) user;
        }
    }

    public static class InvalidRegistrationPhoneWithIncorrectStartNumber {

        private InvalidRegistrationPhoneWithIncorrectStartNumber() {
        }

        public static UserInfo generateInvalidUser(String locale) {
            UserInfo user = new UserInfo(generateCity(locale), generateName(locale), generateInvalidPhoneWithIncorrectStartNumber(locale));
            return (UserInfo) user;
        }
    }

    public static class InvalidRegistrationPhoneWhenDeficiencyNumbers {

        private InvalidRegistrationPhoneWhenDeficiencyNumbers() {
        }

        public static UserInfo generateInvalidUser(String locale) {
            UserInfo user = new UserInfo(generateCity(locale), generateName(locale), generateInvalidPhoneWhenDeficiencyNumbers(locale));
            return (UserInfo) user;
        }
    }

    @Value
    public static class UserInfo {
        private final String city;
        private final String name;
        private final String phone;
    }
}