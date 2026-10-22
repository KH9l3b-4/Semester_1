package practice04;

import java.util.Scanner;

public class task04 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== ДЕТЕКТОР ПРОСТИХ ЧИСЕЛ ===");

        int n;
        do {
            System.out.print("Введіть верхню межу N: ");
            while (!scanner.hasNextInt()) {
                System.out.println("Помилка: введіть ціле число!");
                scanner.next();
                System.out.print("Введіть верхню межу N: ");
            }
            n = scanner.nextInt();
            if (n < 2) {
                System.out.println("Помилка: N має бути не меншим за 2!");
            }
        } while (n < 2);

        System.out.println();
        System.out.println("Знайдені прості числа у проміжку від 2 до " + n + ":");

        int primeCount = 0;
        long primeSum = 0;
        StringBuilder primesLine = new StringBuilder();

        for (int number = 2; number <= n; number++) {
            boolean isPrime = true;

            for (int d = 2; d * d <= number; d++) {
                if (number % d == 0) {
                    isPrime = false;
                    break;
                }
            }

            if (isPrime) {
                if (primeCount > 0) {
                    primesLine.append(", ");
                }
                primesLine.append(number);
                primeCount++;
                primeSum += number;
            }
        }

        System.out.println(primesLine);

        int totalNumbers = n - 1;
        double percent = (double) primeCount / totalNumbers * 100;

        System.out.println();
        System.out.println("--- СТАТИСТИКА ---");
        System.out.println("Усього чисел у діапазоні:    " + totalNumbers);
        System.out.println("Знайдено простих чисел:      " + primeCount + " шт.");
        System.out.println("Сума всіх простих чисел:     " + primeSum);
        System.out.printf("Частка простих чисел:        %.2f%%%n", percent);
        System.out.println("==============================");

        scanner.close();
    }
}
