import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * @author TranHuuThuy
 * lớp thể hiện một trạng thái của Otomat hữu hạn đa định
 */
public class State {
    private int id;
    //tên của trạng thái, các trạng thái sẽ có tên khác nhau, ví dụ q0, q1, ...
    private String name;

    //biến thể hiện đây có phải là trạng thái chấp nhận hay không
    private boolean des;

    //biến thể hiện đây có phải là trạng thái bắt đầu hay không
    private boolean initial;

    //Các liên kết từ trạng thái này có thể đến các trạng thái khác
    private List<Link> links = new ArrayList<>();

    public State(int id, String name, boolean des, boolean initial) {
        this.id = id;
        this.name = name;
        this.des = des;
        this.initial = initial;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isDes() {
        return des;
    }

    public boolean isInitial() {
        return initial;
    }

    public List<Link> getLinks() {
        return links;
    }

    /**
     * phương thức thêm một liên kết vào trạng thái hiện tại
     *
     * @param link
     * @return kết quả trả về xem có thêm được liên kết đó không, nếu được thì thêm vào
     */
    public boolean addLink(Link link) {
        return links.add(link);
    }


    /**
     * phương thức trả về hết các trạng thái Extension của trạng thái hiện tai
     *
     * @return
     */
    public HashSet<State> getE() {
        HashSet<State> ex = new HashSet<>();
        ex.add(this);
        //trang thai ex được xác định bởi các chuyển epsilon
        links.forEach(link -> {
            if (link.getReads().contains(Main.EPSILON)) {
                ex.addAll(link.getTo().getE());
            }
        });
        return ex;
    }

    /**
     * phương thức trả về tập trạng thái tiếp theo của trạng thái hiện tại với chuyển được truyền vào
     *
     * @param read : tham số chuyển
     * @return
     */
    public HashSet<State> getTo(String read) {
        HashSet<State> s = new HashSet<>();
        links.forEach(link -> {
            if (link.getReads().contains(read)) {
                s.add(link.to);
            }
        });
        return s;
    }
}

class Link {
    //Liên kết này có đích đến là trạng thái nào
    protected State to;

    //tập các cách để đến trạng thái "to"
    protected ArrayList<String> reads;

    public Link(State to, ArrayList<String> reads) {
        this.to = to;
        this.reads = reads;
    }

    public State getTo() {
        return to;
    }

    public ArrayList<String> getReads() {
        return reads;
    }
}
