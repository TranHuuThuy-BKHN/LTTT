import org.jdom2.JDOMException;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws JDOMException, IOException {
        Scanner sc = new Scanner(System.in);
        String filename = "chomskey";

        Grammar g = new Grammar();
        g.readFile(new File("CFL.jff"));
        //văn pham ban đầu
        g.getGrammar().forEach(production -> {
            System.out.print(production.getLeft() + "-->");
            production.getRights().forEach(s -> System.out.print(s + "|"));
            System.out.println();
        });
        Chomsky c = new Chomsky(g);
        c.setChomskey();

        System.out.println("Enter filename output (.jff) : ");
        filename = sc.nextLine();
        if (filename.length() == 0) filename = "chomskey";
        g.writeFile(filename + ".jff");

        //văn phạm sau khi chuyển về dạng chuẩn Chomskey
        System.out.println("---------------Chomskey------------------");
        g.getGrammar().forEach(production -> {
            System.out.print(production.getLeft() + "-->");
            production.getRights().forEach(s -> System.out.print(s + "|"));
            System.out.println();
        });
    }
}
