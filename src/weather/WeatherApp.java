package weather;

import java.util.Scanner;

class WeatherApp {
    private Scanner scanner = new Scanner(System.in);

    public void start() {
        while (true) {
            System.out.println("Weather App Menu:");
            System.out.println("1. Search weather by city name");
            System.out.println("2. Exit");
            System.out.print("Please enter your choice: ");

            // Kiểm tra đầu vào
            if (scanner.hasNextInt()) {
                int choice = scanner.nextInt();
                scanner.nextLine(); // consume the newline

                switch (choice) {
                    case 1:
                        searchWeatherByCity();
                        break;
                    case 2:
                        System.out.println("Exiting the application.");
                        System.exit(0);
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } else {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // consume the invalid input
            }
        }
    }

    private void searchWeatherByCity() {
        System.out.print("Enter city name: ");
        String cityName = scanner.nextLine();
        OpenWeatherMapAPI.getCurrentWeather(cityName);
    }
}
