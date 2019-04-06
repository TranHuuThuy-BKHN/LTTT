import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public static void readFileJFF(File file, ArrayList<State> states) throws IOException, JDOMException {
        SAXBuilder builder = new SAXBuilder();
        Document document = (Document) builder.build(file);
        Element rootNode = document.getRootElement().getChild("automaton");

        rootNode.getChildren("state").forEach(e -> {
            states.add(new State(Integer.parseInt(e.getAttributeValue("id")), e.getAttributeValue("name"), (e.getChildText("final") != null)));
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

    //file tạo giao của hai Otomat hữu hạn đơn định
    private static void GiaoJFF(DFA2 dfa2, String filename) throws IOException {
        //set lại trạng thái đích
        dfa2.getStates().forEach(states -> {
            if (states.getP().isDes() && states.getQ().isDes()) {
                states.setDes(true);
            } else states.setDes(false);
        });
        createFileJFF(dfa2, filename);
    }

    //file tạo hợp của hai Otomat hữu hạn đơn định
    private static void HopJFF(DFA2 dfa2, String filename) throws IOException {
        //set lại trạng thái đích
        dfa2.getStates().forEach(states -> {
            if (states.getP().isDes() || states.getQ().isDes()) {
                states.setDes(true);
            } else states.setDes(false);
        });
        createFileJFF(dfa2, filename);
    }

    //thầy đừng đọc file này làm gì, thầy cứ hiểu file này tạo file xml
    private static void createFileJFF(DFA2 dfa2, String filename) throws IOException {
        Document document = new Document();
        document.setRootElement(new Element("structure"));

        Element type = new Element("type");
        type.setText("fa");
        Element automaton = new Element("automaton");
        //thêm các thành phần trạng thái vào file
        final int[] x = {100};
        String y = "100";
        dfa2.getStates().forEach(s -> {
            Element state = new Element("state");
            //thêm các thuộc tính
            state.setAttribute("id", s.getId() + "");
            state.setAttribute("name", s.toString());
            //thêm thành phần tọa độ x, y
            Element X = new Element("x");
            X.setText(x[0] + "");
            Element Y = new Element("y");
            Y.setText(y);
            x[0] += 30;
            state.addContent(X);
            state.addContent(Y);
            //thêm thành phần final nếu đây là trạng thái chấp nhận được
            if (s.isDes()) {
                Element fin = new Element("final");
                state.addContent(fin);
            }
            automaton.addContent(state);
        });
        //thêm các thành phần liên kết vào file
        dfa2.getStates().forEach(s -> {
            s.getLinks().forEach(link -> {
                Element transition = new Element("transition");
                //thêm thành phần from, to
                Element from = new Element("from");
                from.setText(s.getId() + "");
                Element to = new Element("to");
                to.setText(link.getId() + "");
                //thêm thành phần read
                final String[] reads = {""};
                link.getReads().forEach(s1 -> reads[0] += s1);
                Element read = new Element("read");
                read.setText(reads[0]);
                transition.addContent(from);
                transition.addContent(to);
                transition.addContent(read);
                automaton.addContent(transition);
            });
        });

        document.getRootElement().addContent(type);
        document.getRootElement().addContent(automaton);

        XMLOutputter xml = new XMLOutputter();
        xml.setFormat(Format.getPrettyFormat());
        xml.output(document, new FileOutputStream(new File(filename)));
    }

    public static void main(String[] args) throws IOException {
        System.out.println("Cảnh báo : File .jff phải có các trạng thái với id bắt đầu là 0 rồi tăng dần, nếu không chương trình sẽ lỗi !");
        ArrayList<State> p = new ArrayList<>();
        ArrayList<State> q = new ArrayList<>();
        System.out.println("Reading 2 files ...");
        try {
            readFileJFF(new File("file1.jff"), p); //thay tên file 1
            readFileJFF(new File("file2.jff"), q); //thay tên file 2
            DFA2 dfa2 = new DFA2(p, q);

            GiaoJFF(dfa2, "giao.jff");
            HopJFF(dfa2, "hop.jff");
            System.out.println("Sucessful...");
        } catch (Exception e) {
            System.out.println("Cảnh báo lỗi xẩy ra !");
        }
        System.out.println("End...");
    }
}