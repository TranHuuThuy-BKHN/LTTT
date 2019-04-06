import org.jdom2.JDOMException;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws JDOMException, IOException {
        Grammar g = new Grammar();
        g.readFile(new File("chomskey.jff"));

        CYK cyk = new CYK(g);

        Scanner sc = new Scanner(System.in);
        System.out.print("Nhập xâu cần kiểm tra : ");
        System.out.println(cyk.check(sc.nextLine()));
    }
}
