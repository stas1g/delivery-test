package data;

import lombok.Value;
import net.datafaker.Faker;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Random;

public class DataGenerator {
    private DataGenerator() {
    }

    private static final Faker faker = new Faker(new Locale("ru"));
    private static final Random random = new Random();

    public static String generateDate(int shift) {
        return LocalDate.now().plusDays(shift)
                .format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    public static String generateCity() {
        String[] cities = {
                "Москва", "Санкт-Петербург", "Новосибирск", "Екатеринбург",
                "Казань", "Нижний Новгород", "Челябинск", "Самара",
                "Омск", "Ростов-на-Дону", "Уфа", "Красноярск",
                "Воронеж", "Пермь", "Волгоград"
        };
        return cities[random.nextInt(cities.length)];
    }

    public static String generateName() {
        String name = faker.name().firstName().replace("ё", "е").replace("Ё", "Е")
                + " " + faker.name().lastName().replace("ё", "е").replace("Ё", "Е");
        return name;
    }

    public static String generatePhone() {
        // Генерируем 10 цифр для номера
        long phoneNumber = Math.abs(random.nextLong()) % 10000000000L;
        return String.format("+7%010d", phoneNumber);
    }

    public static class Registration {

        private Registration() {
        }

        public static UserInfo generateUser(String locale) {
            return new UserInfo(
                    generateCity(),
                    generateName(),
                    generatePhone());
        }
    }

    @Value
    public static class UserInfo {
        String city;
        String name;
        String phone;
    }
}