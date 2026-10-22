package practice03;

import java.util.Locale;
import java.util.Scanner;

public class task4 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== ПАНЕЛЬ КЛІМАТ-КОНТРОЛЮ SMART HOME ===");

        System.out.print("Поточна температура (°C): ");
        double currentTemp = scanner.nextDouble();

        System.out.print("Бажана температура (°C): ");
        double desiredTemp = scanner.nextDouble();

        System.out.print("Вологість повітря (%): ");
        int humidity = scanner.nextInt();
        scanner.nextLine(); // очищення буфера після числового введення

        System.out.print("Сезон (зима/літо/міжсезоння): ");
        String season = scanner.nextLine().trim();

        System.out.print("Режим будинку (1-Standby, 2-Eco, 3-Party, 4-Night, 5-Vacation): ");
        int mode = scanner.nextInt();

        System.out.print("Чи відчинені вікна? (true/false): ");
        boolean isWindowOpen = scanner.nextBoolean();

        // Назва режиму (switch expression зі стрілками)
        String modeName = switch (mode) {
            case 1 -> "Standby (Звичайний)";
            case 2 -> "Eco (Енергозбереження)";
            case 3 -> "Party (Гості)";
            case 4 -> "Night (Ніч)";
            case 5 -> "Vacation (Відпустка)";
            default -> "Невідомий режим (використано Standby)";
        };

        // Short-circuit перевірка безпеки
        if (isWindowOpen && (mode != 5)) {
            System.out.println();
            System.out.println("ПОПЕРЕДЖЕННЯ: Відчинені вікна! Обігрів та кондиціонування примусово заблоковано!");
            System.out.println();
            System.out.println("------------- СТАТУС СИСТЕМИ -------------");
            System.out.printf("Режим роботи:              %s%n", modeName);
            System.out.printf("Поточна температура:       %.2f°C%n", currentTemp);
            System.out.println("Стан терморегуляції:       Кліматичні установки ВИМКНЕНО (аварійне блокування)");
            System.out.println("Стан мікроклімату:         Зволожувач/осушувач ВИМКНЕНО (аварійне блокування)");
            System.out.println("Енергоспоживання:          Мінімальне (системи заблоковано)");
            System.out.println("==========================================");
            scanner.close();
            return;
        }

        // Modern switch expression з yield
        double effectiveTargetTemp = switch (mode) {
            case 1 -> desiredTemp;
            case 2 -> {
                double shift;
                if (season.equalsIgnoreCase("зима")) {
                    shift = -2.0;
                } else if (season.equalsIgnoreCase("літо")) {
                    shift = 2.0;
                } else {
                    shift = 0.0;
                }
                yield desiredTemp + shift;
            }
            case 3 -> desiredTemp - 1.5;
            case 4 -> 19.0;
            case 5 -> {
                if (season.equalsIgnoreCase("зима")) {
                    yield 12.0; // захист труб від замерзання
                }
                yield 28.0;
            }
            default -> desiredTemp;
        };

        // Терморегуляція (if-else-if ladder)
        String thermoState;
        if (currentTemp - effectiveTargetTemp > 0.5) {
            thermoState = "Кондиціонер УВІМКНЕНО (Охолодження)";
        } else if (effectiveTargetTemp - currentTemp > 0.5) {
            thermoState = "Опалення УВІМКНЕНО (Обігрів)";
        } else {
            thermoState = "Температура в зоні комфорту (Кліматичні прилади в очікуванні)";
        }

        // Керування вологістю (тернарний оператор)
        String humidityState = (humidity < 40)
                ? "Зволожувач УВІМКНЕНО (вологість " + humidity + "% < 40%)"
                : (humidity > 65)
                ? "Осушувач УВІМКНЕНО (вологість " + humidity + "% > 65%)"
                : "Вологість оптимальна (" + humidity + "%)";

        // Пояснення корекції цільової температури
        boolean isAdjusted = Math.abs(effectiveTargetTemp - desiredTemp) > 1e-9;

        String seasonReason = switch (season.toLowerCase()) {
            case "зима" -> "для зими";
            case "літо" -> "для літа";
            default -> "для міжсезоння";
        };

        String targetNote = isAdjusted
                ? String.format(" (скориговано з %.1f°C %s)", desiredTemp, seasonReason)
                : "";

        String energy = switch (mode) {
            case 2, 5 -> "Оптимізоване";
            case 3 -> "Підвищене";
            default -> "Стандартне";
        };

        System.out.println();
        System.out.println("------------- СТАТУС СИСТЕМИ -------------");
        System.out.printf("Режим роботи:              %s%n", modeName);
        System.out.printf("Цільова температура:       %.2f°C%s%n", effectiveTargetTemp, targetNote);
        System.out.printf("Поточна температура:       %.2f°C%n", currentTemp);
        System.out.printf("Стан терморегуляції:       %s%n", thermoState);
        System.out.printf("Стан мікроклімату:         %s%n", humidityState);
        System.out.printf("Енергоспоживання:          %s%n", energy);
        System.out.println("==========================================");

        scanner.close();
    }
}
