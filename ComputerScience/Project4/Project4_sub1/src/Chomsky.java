import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

public class Chomsky {
    private static int i = 69;

    private Grammar g;

    public Chomsky(Grammar grammar) {
        this.g = grammar;
    }

    public void setGrammar(Grammar grammar) {
        this.g = grammar;
    }

    //==========================================================================================
    public void setChomskey() {
        step1(); //bước 1, thêm kí kí hiệu bắt đầu mới
        step2(); //bước 2, loại bỏ các sản suất A --> epsilon
        step3(); //bước 3, loại bỏ quy tắc đơn A --> B
        step4(); //bước 4, loại bỏ các quy tắc có vế phải độ dài > 2
    }

    private void step1() {
        Production p = new Production((char) (i++) + "");
        p.addRight(g.getGrammar().get(0).getLeft());
        g.getGrammar().add(0, p);
    }

    private void step2() {
        Production p = null;
        while ((p = eProduction()) != null) {
            //thay thế kí tự epsilon để tạo thêm các sản suất mới
            for (Production production : g.getGrammar()) {
                LinkedList<String> queue = new LinkedList<>();

                for (String s : production.getRights()) {
                    if (s.contains(p.getLeft())) {
                        queue.push(s);
                    }
                }

                while (!queue.isEmpty()) {
                    String temp = queue.pop();
                    for (int i = 0; i < temp.length(); i++) {
                        if (temp.charAt(i) == p.getLeft().charAt(0)) {
                            String temp2 = temp.substring(0, i) + temp.substring(i + 1);

                            if (temp2.equals("")) {
                                temp2 = Grammar.epsilon;
                            }
                            //ghi nhận sản xuất mới
                            production.addRight(temp2);
                            queue.push(temp2);
                        }
                    }
                }
            }
        }
        //Loại bỏ các sản suất ko có vế phải
        Iterator<Production> ps = g.getGrammar().iterator();
        while (ps.hasNext()) {
            if (ps.next().getRights().isEmpty()) ps.remove();
        }
    }

    private void step3() {
        Pair<Integer, Integer> pair = null;
        while ((pair = singleRule()) != null) {
            //xóa quy tắc đơn này
            g.getGrammar().get(pair.getKey()).getRights().remove(g.getGrammar().get(pair.getValue()).getLeft());
            //thêm các sản xuất mới
            for (String right : g.getGrammar().get(pair.getValue()).getRights()) {
                g.getGrammar().get(pair.getKey()).addRight(right);
            }
        }
    }

    private void step4() {
        //tập lưu chuỗi thay thế tương ứng
        HashMap<String, String> hm = new HashMap<>();
        Object pros[] = g.getGrammar().toArray();

        for (Object ps0 : pros) {
            Production p = (Production) ps0;
            //tập các vế phải cần xóa vì không thoản mãn chuẩn Chomskey
            ArrayList<String> delete = new ArrayList<>();
            Object rights[] = p.getRights().toArray();
            for (Object r : rights) {
                String right = (String) r;
                //tập các tập sản xuất thay thế vế phải có 2 kí tự
                ArrayList<Production> ps = new ArrayList<>();

                //tập các tập sản xuất thay thế ở dạng chuẩn Chomskey
                ArrayList<Production> ps2 = new ArrayList<>();

                if (right.length() > 2) {
                    LinkedList<String> queue = new LinkedList<>();
                    queue.push(right);
                    String left = p.getLeft();
                    while (!queue.isEmpty()) {
                        String str = queue.pop();
                        String A1 = str.substring(1);

                        //thêm sản xuất A --> uA1
                        if (hm.get(A1) == null) {
                            hm.put(A1, (char) (i++) + "");
                        }

                        ps.add(new Production(left, str.charAt(0) + hm.get(A1))); //thêm vào tập sản xuất thay thế
                        left = hm.get(A1);
                        if (A1.length() > 2) queue.push(A1);
                        else ps.add(new Production(left, A1));
                    }

                    //sau khi có tập ps, ta cần thay thế các kí tự kết thúc bằng kí tự không kết thúc
                    for (int k = 0; k < ps.size(); k++) {
                        sovle(ps.get(k).getLeft(), (String) ps.get(k).getRights().toArray()[0], ps2, hm);
                    }

                    delete.add(right);

                } else if (right.length() == 2) {
                    if (!right.equals(right.toUpperCase())) {
                        sovle(p.getLeft(), right, ps2, hm);
                        delete.add(right);
                    }
                }
                ps2.forEach(production -> g.addProduction(production));
            }
            delete.forEach(s -> p.getRights().remove(s));
        }
    }

    //==========================================================================================

    //phương thức hỗ trợ step2, tìm ra sản xuất A--> epsilon đầu tiên của văn phạm, đồng thời xóa sản xuất này
    private Production eProduction() {
        for (Production p : g.getGrammar()) {
            if (p.getRights().contains(Grammar.epsilon)) {
                p.getRights().remove(Grammar.epsilon);
                return p;
            }
        }
        return null;
    }

    //phương thức hỗ trợ step 3, kiểm tra một sản xuất chứa quay tắc đơn
    //trả về vị trị hiện tại và vị trí cần thêm các vế phải
    // ví dụ tập sx
    //0, S₀-->S
    //1, S-->aB|a|AS|SA|ASA
    //2, A-->B|S
    //3, B-->b
    // Phát hiện quy tắc đơn đầu tiên A-->B thì trả về (2,3)
    private Pair<Integer, Integer> singleRule() {
        for (int i = 0; i < g.getGrammar().size(); i++) {
            for (String s : g.getGrammar().get(i).getRights()) {
                for (int j = 0; j < g.getGrammar().size(); j++)
                    if (s.equals(g.getGrammar().get(j).getLeft()))
                        return new Pair<>(i, j);
            }
        }
        return null;
    }

    //phương thức hỗ trợ step4, khi gặp sản xuất dạng A --> X, trong đo X có độ dài 2 thì cần chuyển về Chomskey như nào
    private void sovle(String left, String right, ArrayList<Production> p, HashMap<String, String> hm) {
        if (!right.equals(right.toUpperCase())) {
            boolean b1 = Character.isLowerCase(right.charAt(0)),
                    b2 = Character.isLowerCase(right.charAt(1));

            if (b1 && b2) {
                if (hm.get(right.charAt(0) + "") == null) {
                    hm.put(right.charAt(0) + "", (char) (i++) + "");
                }

                if (hm.get(right.charAt(1) + "") == null) {
                    hm.put(right.charAt(1) + "", (char) (i++) + "");
                }

                p.add(new Production(left, hm.get(right.charAt(0) + "") + "" + hm.get(right.charAt(1) + "")));
                p.add(new Production(hm.get(right.charAt(0) + ""), right.charAt(0) + ""));
                p.add(new Production(hm.get(right.charAt(1) + ""), right.charAt(1) + ""));
            } else if (b1) {
                if (hm.get(right.charAt(0) + "") == null) {
                    hm.put(right.charAt(0) + "", (char) (i++) + "");
                }
                p.add(new Production(left, hm.get(right.charAt(0) + "") + "" + right.charAt(1)));
                p.add(new Production(hm.get(right.charAt(0) + ""), right.charAt(0) + ""));
            } else if (b2) {
                if (hm.get(right.charAt(1) + "") == null) {
                    hm.put(right.charAt(1) + "", (char) (i++) + "");
                }
                p.add(new Production(left, right.charAt(0) + "" + hm.get(right.charAt(1) + "")));
                p.add(new Production(hm.get(right.charAt(1) + ""), right.charAt(1) + ""));
            }
        } else p.add(new Production(left, right.toUpperCase()));
    }

}