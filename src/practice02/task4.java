package practice02;

public class task4 {
    public static void main(String[] args){
        double a = 7.0, b = 8.0, c = 9.0;

        double P = a + b + c;

        double p = P/2;

        double S = Math.sqrt(p  * (p - a) * (p - b) * (p - c));


        double r = S / p;
        double Sr = Math.PI * Math.pow(r, 2);


        double R = (a * b * c) / (4.0 * S);
        double SR = Math.PI * Math.pow(R, 2);

        System.out.println("================ ГЕОМЕТРИЧНИЙ ЗВІТ ===============");
        System.out.println("Сторони: a = " + a + ", b = " + b + ", c = " + c);
        System.out.println("Периметр (P):               " + P);
        System.out.println("Напівпериметр (p):          " + p);
        System.out.println("Площа трикутника (S):       " + S + " кв. од.");
        System.out.println("Радіус вписаного кола (r):  " + r);
        System.out.println("Площа вписаного круга:      " + Sr + " кв. од.");
        System.out.println("Радіус описаного кола (R):  " + R);
        System.out.println("Площа описаного круга:      " + SR + " кв. од.");
        System.out.println("==================================================");

    }
}
