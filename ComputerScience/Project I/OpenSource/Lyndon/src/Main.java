import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Lyndons lyndons = new Lyndons();
        int n;
        Scanner s = new Scanner(System.in);

        while (true) {
            System.out.print("Positive Integer : ");
            n = s.nextInt();
            lyndons.setLyndons(n);
            lyndons.show(n);
        }
    }
}
