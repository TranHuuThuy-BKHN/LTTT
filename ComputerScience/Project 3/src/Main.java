import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import java.util.HashSet;

public class Main {
    //kí hiệu của epsilon trong nfa
    public static final String EPSILON = "epsilon";
    //tập các trạng thái
    private ArrayList<State> states = new ArrayList<>();
    //bảng chữ cái không có epsilon
    private HashSet<String> alphabet = new HashSet<>();

    public ArrayList<State> getStates() {
        return states;
    }

    public HashSet<String> getAlphabet() {
        return alphabet;
    }

    //phương thức đọc file các trạng thái, đọc vào cả bảng chữ cái
    public void readFileJFF(File file) throws IOException, JDOMException {
        SAXBuilder builder = new SAXBuilder();
        Document document = (Document) builder.build(file);
        Element rootNode = document.getRootElement().getChild("automaton");

        rootNode.getChildren("state").forEach(e -> {
            states.add(new State(Integer.parseInt(e.getAttributeValue("id")), e.getAttributeValue("name"), (e.getChildText("final") != null), (e.getChildText("initial") != null)));
        });
        rootNode.getChildren("transition").forEach(e -> {
            int from = Integer.parseInt(e.getChildText("from"));
            int to = Integer.parseInt(e.getChildText("to"));
            String[] reads = e.getChildText("read").split("\\s+");
            String temp = "";
            for (String read : reads) {
                temp += read;
            }
            String[] temp2 = temp.split(",");
            //đọc vào bảng chữ cái, lúc này, bảng chữ cái có cả epsilon, ta cần lạo bỏ nó đi
            alphabet.addAll(Arrays.asList(temp2));

            Link link = new Link(states.get(to), new ArrayList<>(Arrays.asList(temp2)));
            states.get(from).addLink(link);
        });
        alphabet.remove(EPSILON);
    }

    public static void main(String[] args) throws IOException, JDOMException {

        File file = new File("nfa.jff"); // thay đổi tên file ở đây
        Main m = new Main();
        //đọc các trạng thái và bảng chữ, lúc này, bảng chữ có cả epsilon
        m.readFileJFF(file);

        new Algorithm(new File("dfa.jff"), m.getAlphabet(), m.getStates());
        System.out.println("Sucessful...");
    }
}
