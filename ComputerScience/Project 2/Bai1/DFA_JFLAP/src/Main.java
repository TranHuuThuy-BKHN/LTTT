import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

    //phương thức đọc file jff sử dung module jdom2-2.0.6
    public static void readFileJFF(File file, ArrayList<State> states) throws IOException, JDOMException {
        SAXBuilder builder = new SAXBuilder();
        Document document = (Document) builder.build(file);
        Element rootNode = document.getRootElement().getChild("automaton");

        rootNode.getChildren("state").forEach(e -> {
            states.add(new State(e.getAttributeValue("name") + "", (e.getChildText("final") != null)));
        });
        rootNode.getChildren("transition").forEach(e -> {
            int from = Integer.parseInt(e.getChildText("from"));
            int to = Integer.parseInt(e.getChildText("to"));
            String[] reads = e.getChildText("read").split("\\s+");
            String temp = "";
            for (String read : reads) {
                temp += read;
            }
            Link link = new Link(states.get(to), new ArrayList<>(Arrays.asList(temp.split(","))));
            states.get(from).addLink(link);
        });
    }

    public static void main(String[] args) {
        System.out.println("Cảnh báo : File .jff phải có các trạng thái với id bắt đầu là 0 rồi tăng dần, nếu không chương trình sẽ lỗi !");

        ArrayList<State> states = new ArrayList<>();
        try {
            readFileJFF(new File("file.jff"), states); //thay tên file ở đây
            DFA dfa = new DFA(states);
            String w;
            Scanner sc = new Scanner(System.in);
            System.out.print("Nhập xâu cần kiển tra : ");
            w = sc.nextLine();
            if (dfa.isYes(w)) System.out.println("YES");
            else System.out.println("NO");
        } catch (Exception e) {
            System.out.println("Cảnh báo lỗi xảy ra !");
        }
    }
}