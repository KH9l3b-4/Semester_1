package practice06;

class MatrixSolver {

    private double[][] data;

    /** Зберігає копію переданого 2D-масиву (захист внутрішнього стану від зовнішніх змін). */
    public void setMatrix(double[][] data) {
        if (data == null || data.length == 0 || data[0] == null) {
            System.out.println("Помилка: матриця не може бути порожньою!");
            this.data = new double[][]{{0}};
            return;
        }
        this.data = new double[data.length][];
        for (int i = 0; i < data.length; i++) {
            this.data[i] = data[i].clone();
        }
    }

    /** Форматоване виведення матриці. */
    public void printMatrix() {
        for (double[] row : data) {
            StringBuilder sb = new StringBuilder("[");
            for (int j = 0; j < row.length; j++) {
                sb.append(String.format("%5.2f", row[j]));
                if (j < row.length - 1) {
                    sb.append("  ");
                }
            }
            sb.append(" ]");
            System.out.println(sb);
        }
    }

    /** Визначник матриці 2x2. */
    public double getDeterminant2x2() {
        if (data.length != 2 || data[0].length != 2) {
            System.out.println("Помилка: матриця не має розміру 2x2!");
            return Double.NaN;
        }
        return data[0][0] * data[1][1] - data[0][1] * data[1][0];
    }

    /** Визначник матриці 3x3 за правилом Саррюса. */
    public double getDeterminant3x3() {
        if (data.length != 3 || data[0].length != 3) {
            System.out.println("Помилка: матриця не має розміру 3x3!");
            return Double.NaN;
        }
        return data[0][0] * data[1][1] * data[2][2]
                + data[0][1] * data[1][2] * data[2][0]
                + data[0][2] * data[1][0] * data[2][1]
                - data[0][2] * data[1][1] * data[2][0]
                - data[0][0] * data[1][2] * data[2][1]
                - data[0][1] * data[1][0] * data[2][2];
    }

    /** Транспонування: T[j][i] = data[i][j]. Результат записується назад у поле data. */
    public void transpose() {
        int rows = data.length;
        int cols = data[0].length;
        double[][] t = new double[cols][rows];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                t[j][i] = data[i][j];
            }
        }
        data = t;
    }

    /** Розв'язання системи 2x2 методом Крамера. */
    public void solveSystem2x2(double c1, double c2) {
        if (data.length != 2 || data[0].length != 2) {
            System.out.println("Помилка: для методу Крамера потрібна матриця 2x2!");
            return;
        }
        double a1 = data[0][0], b1 = data[0][1];
        double a2 = data[1][0], b2 = data[1][1];

        System.out.println("--- РОЗВ'ЯЗАННЯ СИСТЕМИ ЛІНІЙНИХ РІВНЯНЬ (МЕТОД КРАМЕРА) ---");
        System.out.println("Рівняння:");
        System.out.println("  " + equation(a1, b1, c1));
        System.out.println("  " + equation(a2, b2, c2));
        System.out.println();

        double delta = getDeterminant2x2();
        double deltaX = c1 * data[1][1] - c2 * data[0][1];
        double deltaY = data[0][0] * c2 - data[1][0] * c1;

        System.out.printf("Головний визначник Delta  = %.2f%n", delta);
        System.out.printf("Визначник Delta_x        = %.2f%n", deltaX);
        System.out.printf("Визначник Delta_y        = %.2f%n", deltaY);
        System.out.println();

        if (Math.abs(delta) > 1e-9) {
            double x = deltaX / delta;
            double y = deltaY / delta;
            System.out.println("Знайдено єдиний розв'язок:");
            System.out.printf("  x = Delta_x / Delta = %.2f%n", x);
            System.out.printf("  y = Delta_y / Delta = %.2f%n", y);
            double left1 = a1 * x + b1 * y;
            boolean ok = Math.abs(left1 - c1) < 1e-9;
            System.out.printf("Перевірка: %.0f*(%.0f) + %.0f*(%.0f) = %.0f (%s)%n",
                    a1, x, b1, y, left1, ok ? "ВІРНО!" : "ПОМИЛКА!");
        } else if (Math.abs(deltaX) < 1e-9 && Math.abs(deltaY) < 1e-9) {
            System.out.println("Система має безліч розв'язків");
        } else {
            System.out.println("Система несумісна (не має розв'язків)");
        }
    }

    /** Допоміжний метод: рядок виду "2.00*x + 3.00*y = 8.00". */
    private String equation(double a, double b, double c) {
        String sign = b < 0 ? "-" : "+";
        return String.format("%.2f*x %s %.2f*y = %.2f", a, sign, Math.abs(b), c);
    }
}

public class task4 {

    public static void main(String[] args) {
        System.out.println("================ МАТРИЧНИЙ КАЛЬКУЛЯТОР ТА СЛАР ================");

        // 1. Матриця 2x2: виведення, визначник, транспонування
        MatrixSolver m = new MatrixSolver();
        m.setMatrix(new double[][]{{2, 3}, {4, -1}});

        System.out.println("Початкова матриця 2x2 коефіцієнтів A:");
        m.printMatrix();
        System.out.printf("%nВизначник det(A) = %.2f%n", m.getDeterminant2x2());

        System.out.println("\nТранспонована матриця A^T:");
        m.transpose();
        m.printMatrix();
        m.transpose(); // повертаємо A для розв'язання системи
        System.out.println();

        // 2. СЛАР методом Крамера: 2x + 3y = 8; 4x - y = 2
        m.solveSystem2x2(8, 2);


        // 4. Матриця 3x3: визначник за правилом Саррюса
        System.out.println("\n--- МАТРИЦЯ 3x3 ---");
        MatrixSolver m3 = new MatrixSolver();
        m3.setMatrix(new double[][]{{2, -1, 0}, {1, 3, 4}, {0, 5, -2}});
        m3.printMatrix();
        System.out.printf("Визначник det = %.2f%n", m3.getDeterminant3x3());

        System.out.println("===============================================================");
    }
}
