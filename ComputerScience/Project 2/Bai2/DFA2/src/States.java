import java.util.ArrayList;
import java.util.List;

public class States {
    //thuộc tính dùng để ghi vào file xml
    private int id;

    //một trang thái bây giờ là sự kết hợp của 2 trạng thái
    private State p, q;

    //kiểm tra đây có phải là trạng thái chấp nhận (tùy thuộc là hợp hay giao)
    private boolean des;

    private List<Link2> links;

    /**
     * @param id  id của trạng thái hiện tại
     * @param ids mảng hai chiều chứa id của tất cả các trạng thái
     * @param p
     * @param q
     */
    public States(int id, int ids[][], State p, State q) {
        this.id = id;
        this.p = p;
        this.q = q;
        links = new ArrayList<>();

        //thêm các liên kết
        p.getLinks().forEach(link -> {
            q.getLinks().forEach(link1 -> {
                ArrayList<String> reads = new ArrayList<>();
                link.getReads().forEach(read -> {
                    if (link1.getReads().contains(read)) {
                        reads.add(read);
                    }
                });
                if (reads.size() > 0) {
                    addLink(new Link2(ids[link.getTo().getId()][link1.getTo().getId()], link.getTo(), link1.getTo(), reads));
                }
            });
        });
    }

    public State getP() {
        return p;
    }

    public State getQ() {
        return q;
    }

    public boolean isDes() {
        return des;
    }

    public void setDes(boolean des) {
        this.des = des;
    }

    public int getId() {
        return id;
    }

    private boolean addLink(Link2 l) {
        return links.add(l);
    }

    public List<Link2> getLinks() {
        return links;
    }

    @Override
    public String toString() {
        return p.getName() + q.getName();
    }
}

class Link2 extends Link {
    //thuộc tính dùng để ghi vào file xml
    private int id;

    private State to2;

    public Link2(int id, State to, State to2, ArrayList<String> reads) {
        super(to, reads);
        this.to2 = to2;
        this.id = id;
    }

    public int getId() {
        return id;
    }
}