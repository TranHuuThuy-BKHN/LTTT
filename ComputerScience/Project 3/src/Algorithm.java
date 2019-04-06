import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;


public class Algorithm {

    //file ghi ra kết quả
    private File file;

    //tập bảng chữ cái
    private HashSet<String> alphabet;

    //trạng thái bắt đầu
    private State startState;

    public Algorithm(File file, HashSet<String> alphabet, ArrayList<State> states) throws IOException {
        this.file = file;
        this.alphabet = alphabet;

        //xác định trạng thái bắt đầu
        for (State state : states) {
            if(state.isInitial()) {
                startState = state;
                break;
            }
        }
        run();
    }

    //phương thức chạy thuật toán sử dụng hàng đơi,đông thời viết vào file luôn
    private void run() throws IOException {
        //===========chuẩn bị ghi file==================
        Document document = new Document();
        document.setRootElement(new Element("structure"));

        Element type = new Element("type");
        type.setText("fa");
        Element automaton = new Element("automaton");

        //=====================================================


        LinkedList<Extension> queue = new LinkedList<>();
        LinkedList<Extension> queue2 = new LinkedList<>();
        final int[] id = {0};

        //bắt đâu là E(startState)
        Extension e = new Extension(startState.getE());
        e.setId(id[0]++);
        queue.push(e);
        queue2.push(e);

        while (!queue.isEmpty()) {
            Extension ex = queue.pop();
            alphabet.forEach(read -> {
                Extension ex1 = ex.getTo(read);
                if (ex1.getE().size() > 0) {

                    if (!queue2.contains(ex1)) {
                        ex1.setId(id[0]++);
                        queue.push(ex1);
                        queue2.push(ex1);
                    }

                    //ghi vào file cac liên kết
                    Element transition = new Element("transition");
                    Element from = new Element("from");
                    from.setText(ex.getId() + "");
                    Element to = new Element("to");
                    to.setText(queue2.get(queue2.indexOf(ex1)).getId() + "");
                    Element r = new Element("read");
                    r.setText(read);

                    transition.addContent(from);
                    transition.addContent(to);
                    transition.addContent(r);
                    automaton.addContent(transition);
                }
            });
        }
        //ghi vào file các trạng thái
        int x = 100, y = 100;
        while (!queue2.isEmpty()) {
            Extension temp = queue2.pop();
            Element state = new Element("state");
            state.setAttribute("id", temp.getId() + "");
            state.setAttribute("name", temp.toString());
            Element X = new Element("x");
            X.setText(x + "");
            x += 100;
            Element Y = new Element("y");
            Y.setText(y + "");
            state.addContent(X);
            state.addContent(Y);

            if (temp.isDes()) {
                state.addContent(new Element("final"));
            }

            if (temp.getId() == 0) {
                state.addContent(new Element("initial"));
            }
            automaton.addContent(state);
        }

        //kết thức ghi file
        document.getRootElement().addContent(type);
        document.getRootElement().addContent(automaton);

        XMLOutputter xml = new XMLOutputter();
        xml.setFormat(Format.getPrettyFormat());
        xml.output(document, new FileOutputStream(file));
    }
}
