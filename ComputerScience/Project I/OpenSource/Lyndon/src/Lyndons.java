import java.util.ArrayList;
import java.util.List;

public class Lyndons {
    private int n = 100;

    private List<LyndonLength> lyndons = new ArrayList<>();


    public void setLyndons(int n) {
        this.n = n;
        lyndons.clear();
        for (int i = 1; i <= n; i++) {
            LyndonLength l = new LyndonLength(i);
            create(l);
            add(l);
        }
    }

    //phương thức hiển thị tất cả các lyndon có đố dài từ 1->n
    public void show() {
        System.out.println("-----------------------Lyndons-----------------------");
        for (int i = 1; i <= n; i++) {
            System.out.print(i + "-" + lyndons.get(i - 1).getLyndonLength().size() + " :  ");
            lyndons.get(i - 1).getLyndonLength().values().forEach(lyndon -> {
                System.out.print(lyndon.getLyndon() + ",");
            });
            System.out.println();
        }
    }

    //phương thức hiển thị lyndon có độ dài pos
    public void show(int pos) {
        lyndons.get(pos - 1).getLyndonLength().values().forEach(lyndon -> {
            System.out.println(lyndon.getLyndon() + ",");
        });
        System.out.println(pos + " bit(s) - " + lyndons.get(pos - 1).getLyndonLength().size());
    }

    private boolean add(LyndonLength l) {
        if (lyndons.contains(l)) return false;
        lyndons.add(l.getLength() - 1, l);
        return true;
    }

    /**
     * phương thức tìm ra tất cả các lyndon có độ dài length ở trên
     * phương thức sử dụng tính chất : nếu u, v là Lyndon và u < v thì uv cũng là Lyndon
     */
    private void create(LyndonLength l) {
        for (int i = 1; i <= l.getLength() / 2; i++) {
            int j = l.getLength() - i - 1;
            lyndons.get(i - 1).getLyndonLength().values().forEach(u -> {
                lyndons.get(j).getLyndonLength().values().forEach(v -> {
                    Lyndon uv = u.plus(v);
                    if (uv != null && l.getLyndonLength().get(uv.getLyndon()) == null) l.add(uv);
                });
            });
        }
    }
}
