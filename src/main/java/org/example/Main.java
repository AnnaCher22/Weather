package org.example;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        double longitude = 0;
        double latitude = 0;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        userInputLatitude();
        userInputLongitude();
        ;
        userInputDays();
        try {
            Weather weather = new Weather(latitude, longitude);
            weather.fetchAndProcessWeatherData();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void userInputLatitude() {
        System.out.println("Введите широту: ");

        if (scanner.hasNextDouble()) {
            double latitude = scanner.nextDouble();
            if (0 <= latitude && latitude <= 90) {

            } else {
                System.out.println("Ошибка");
                scanner.next();
                userInputLatitude();
            }
        } else {
            System.out.println("Ошибка");
            scanner.next();
            userInputLatitude();
        }


    }


    public static void userInputLongitude() {
        System.out.println("Введите долготу: ");
        if (scanner.hasNextDouble()) {
            double longitude = scanner.nextDouble();
            if (0 <= longitude && longitude <= 180) {

            } else {
                System.out.println("Ошибка");
                scanner.next();
                userInputLatitude();

            }
        } else {
            System.out.println("Ошибка");
            scanner.next();
            userInputLatitude();
        }
    }

    public static void userInputDays() {
        System.out.println("Введите количество дней для расчета средней температуры: ");

        if (scanner.hasNextInt()) {
            int days = scanner.nextInt();
            if (0 < days) {

            } else {
                System.out.println("Ошибка");
                scanner.next();
                userInputLatitude();
            }
        } else {
            System.out.println("Ошибка");
            scanner.next();
            userInputLatitude();
        }


    }
}
