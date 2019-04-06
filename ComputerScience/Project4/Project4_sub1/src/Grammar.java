import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Grammar {
    public static final String epsilon = "ε";

    private ArrayList<Production> grammar;

    public Grammar() {
        this.grammar = new ArrayList<>();
    }

    public ArrayList<Production> getGrammar() {
        return grammar;
    }

    //phương thức thêm một sản suất vào tập văn phạm
    public void addProduction(Production p) {
        int i = grammar.indexOf(p);
        if (i < 0) grammar.add(p);
            //nếu như vế trái của sản suất đã tồn tại trong văn phạm
        else {
            Production pr = grammar.get(i);

            Iterator<String> iterator = p.getRights().iterator();
            while (iterator.hasNext()) {
                pr.addRight(iterator.next());
            }
        }
    }

    //phương thức đọc một file jff văn phạm
    public void readFile(File file) throws JDOMException, IOException {
        grammar.clear();
        SAXBuilder builder = new SAXBuilder();
        Document document = (Document) builder.build(file);
        List<Element> rootNode = document.getRootElement().getChildren("production");

        rootNode.forEach(e -> {
            Production p = new Production(e.getChildText("left"));
            String right = e.getChildText("right");
            if (right.equals("") == false) p.addRight(right);
            else p.addRight(epsilon);
            addProduction(p);
        });
    }

    //phương thức ghi văn phạm vào file jff
    public void writeFile(String filename) throws IOException {
        File file = new File(filename);

        Document document = new Document();
        document.setRootElement(new Element("structure"));
        Element type = new Element("type");
        type.setText("grammar");
        document.getRootElement().addContent(type);

        grammar.forEach(p -> {
            Iterator<String> iterator = p.getRights().iterator();
            while (iterator.hasNext()){
                Element production = new Element("production");
                Element left = new Element("left");
                Element right = new Element("right");

                left.setText(p.getLeft());
                right.setText(iterator.next());
                if (right.getValue().equals(epsilon)) right.setText("");
                production.addContent(left);
                production.addContent(right);
                document.getRootElement().addContent(production);
            }
        });
        XMLOutputter xml = new XMLOutputter();
        xml.setFormat(Format.getPrettyFormat());
        xml.output(document, new FileOutputStream(file));
    }
}