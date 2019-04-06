import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author TranHuuThuy
 * Lớp chưa tất cả các lyndon có cùng độ dài
 */
public class LyndonLength {
    private static final String[] lyndon1 = new String[]{"0", "1"};

    private int length = 1;

    private HashMap<String, Lyndon> lyndonLength = new HashMap<>();

    public LyndonLength(int length) {
        if (length > 0) this.length = length;

        if (this.length == 1) {
            add(new Lyndon(lyndon1[0]));
            add(new Lyndon(lyndon1[1]));
        }

    }

    public int getLength() {
        return length;
    }

    public HashMap<String, Lyndon> getLyndonLength() {
        return lyndonLength;
    }

    //phương thức thêm một lyndon
    public boolean add(Lyndon l) {
        if (l.getLyndon().length() != length || lyndonLength.get(l.getLyndon()) != null) return false;
        else {
            lyndonLength.put(l.getLyndon(), l);
            return true;
        }
    }
}
