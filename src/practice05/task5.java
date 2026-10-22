package practice05;

import java.util.Scanner;

public class task5 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // ---------- Каталог вихованців (паралельні масиви) ----------
        String[] petNames = {"Dog", "Cat", "Dragon", "Unicorn", "Huge Titanic Cat"};
        String[] petIcons = {"🐶", "🐱", "🐉", "🦄", "💎"};
        int[] petPowers = {15, 25, 120, 350, 2000};
        int[] dropChances = {45, 30, 15, 8, 2}; // у сумі 100%

        // ----------  Рюкзак-інвентар на 8 слотів ----------
        String[] inventoryNames = new String[8];
        int[] inventoryPowers = new int[8];
        int petCount = 0; // скільки слотів зараз зайнято

        // ----------  Економіка ----------
        int coins = 300;
        final int EGG_PRICE = 100;

        boolean running = true;

        while (running) {
            System.out.println("======================================================");
            System.out.println("           🐾 ROBLOX: PET SIMULATOR 🐾");
            System.out.println("======================================================");
            System.out.println("Баланс: " + coins + " монет | Вихованців у рюкзаку: "
                    + petCount + "/" + inventoryNames.length);
            System.out.println();
            System.out.println("1 - Відкрити чарівне яйце (" + EGG_PRICE + " монет)");
            System.out.println("2 - Відкрити рюкзак-інвентар");
            System.out.println("3 - Порахувати силу команди");
            System.out.println("4 - Фармити монети (+50..150 монет)");
            System.out.println("0 - Вийти з гри");
            System.out.print("Ваш вибір: ");

            if (!scanner.hasNextInt()) {
                scanner.next(); // пропускаємо некоректне слово, щоб не зациклитись
                System.out.println("❌ Невірне введення! Введіть число від 0 до 4.\n");
                continue;
            }

            int choice = scanner.nextInt();

            switch (choice) {
                case 1: {
                    // Перевірка 1: чи вистачає монет
                    if (coins < EGG_PRICE) {
                        System.out.println("❌ Недостатньо монет! Потрібно " + EGG_PRICE
                                + ", а у вас " + coins + ". Пофармте монети (пункт 4).");
                        break;
                    }
                    // Перевірка 2: чи є вільне місце в рюкзаку
                    if (petCount >= inventoryNames.length) {
                        System.out.println("🎒 Рюкзак переповнений! Продайте або видаліть зайвих петів");
                        break;
                    }

                    System.out.println("🥚 Трісь! Яйце розколюється...");

                    // Визначаємо пета за шансами (кумулятивна сума)
                    int roll = (int) (Math.random() * 100) + 1; // 1..100
                    int dropped = 0;
                    int cumulative = 0;
                    for (int i = 0; i < petNames.length; i++) {
                        cumulative += dropChances[i];
                        if (roll <= cumulative) {
                            dropped = i;
                            break;
                        }
                    }

                    // Додаємо в наступний вільний слот і списуємо монети
                    inventoryNames[petCount] = petNames[dropped];
                    inventoryPowers[petCount] = petPowers[dropped];
                    petCount++;
                    coins -= EGG_PRICE;

                    if (dropped == petNames.length - 1) {
                        System.out.println("⭐⭐⭐ НЕЙМОВІРНО! СУПЕР-ДЖЕКПОТ! ⭐⭐⭐");
                        System.out.println(petIcons[dropped] + " " + petNames[dropped]
                                + "! (Сила: " + petPowers[dropped] + ")");
                        System.out.println("Залишок монет: " + coins);
                    } else {
                        System.out.println("✨ ВАМ ВИПАВ: " + petIcons[dropped] + " "
                                + petNames[dropped] + "! (Сила: " + petPowers[dropped] + ")");
                        System.out.println("Вихованця додано в інвентар! Залишок монет: " + coins);
                    }
                    break;
                }

                case 2: {
                    System.out.println("--- ВАШ РЮКЗАК (" + petCount + "/" + inventoryNames.length + ") ---");
                    if (petCount == 0) {
                        System.out.println("Рюкзак порожній. Відкрийте яйце!");
                    } else {
                        for (int i = 0; i < petCount; i++) {
                            System.out.println("[Слот " + (i + 1) + "] " + inventoryNames[i]
                                    + " (Сила: " + inventoryPowers[i] + ")");
                        }
                    }
                    break;
                }

                case 3: {
                    if (petCount == 0) {
                        System.out.println("У вас ще немає вихованців. Відкрийте яйце!");
                        break;
                    }
                    int totalPower = 0;
                    int bestIndex = 0;
                    for (int i = 0; i < petCount; i++) {
                        totalPower += inventoryPowers[i];
                        if (inventoryPowers[i] > inventoryPowers[bestIndex]) {
                            bestIndex = i;
                        }
                    }
                    System.out.println("--- СТАТИСТИКА ВАШОЇ КОМАНДИ ---");
                    System.out.println("Кількість вихованців: " + petCount + " шт.");
                    System.out.println("Загальна сила команди: " + totalPower + " очок шкоди");
                    System.out.println("🔥 Найсильніший улюбленець: " + inventoryNames[bestIndex]
                            + " (Сила: " + inventoryPowers[bestIndex] + ")");
                    break;
                }

                case 4: {
                    int earned = (int) (Math.random() * 101) + 50; // 50..150
                    coins += earned;
                    System.out.println("⛏️ Ви розбили кілька скринь на локації і отримали +"
                            + earned + " монет! Баланс: " + coins);
                    break;
                }

                case 0:
                    running = false;
                    System.out.println("👋 Дякуємо за гру! Ваш фінальний баланс: " + coins + " монет.");
                    break;

                default:
                    System.out.println("❌ Невідома команда! Оберіть пункт від 0 до 4.");
            }
            System.out.println();
        }

        scanner.close();
    }
}
