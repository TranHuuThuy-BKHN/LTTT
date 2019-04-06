import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int n = 0;
        Scanner s = new Scanner(System.in);
        while (true) {
            do {
                System.out.print("n = ");
                n = s.nextInt();
            } while (n < 1);
            Counter c = new Counter(n);
            if (n == 1) System.out.println(2);
            else System.out.println(c.getAmount());
        }
    }
}
