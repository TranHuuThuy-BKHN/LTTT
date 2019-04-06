import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;

/**
 * @author TranHuuThuy
 * lớp thể hiện các trạng thái E(R) , nhưng ở đây ta chỉ quan tâm nó chứa gì
 */
public class Extension {

    private int id;

    private HashSet<State> e;

    public Extension(HashSet<State> e) {
        this.e = e;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    /**
     * phương thức kiểm tra xem trạng thái mới Extension này có phải trạng thái kết thúc hay không
     *
     * @return
     */
    public boolean isDes() {
        for (State s : e) {
            if (s.isDes()) return true;
        }
        return false;
    }

    public HashSet<State> getE() {
        return e;
    }

    /**
     * phương thức chuyển E(R) -> E(R')
     *
     * @param read
     * @return
     */
    public Extension getTo(String read) {
        HashSet<State> temp = new HashSet<>();
        HashSet<State> res = new HashSet<>();

        e.forEach(state -> {
            temp.addAll(state.getTo(read));
        });

        res.addAll(temp);

        temp.forEach(state -> {
            res.addAll(state.getE());
        });

        return new Extension(res);
    }

    @Override
    public String toString() {
        String name = "";
        for (State s : e) {
            name = name + (s.getName() + ",");
        }
        return name.substring(0, name.length() - 1);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Extension == false || (e.size() != ((Extension) obj).getE().size())) return false;
        else {
            Object[] s1 = e.toArray();
            Object[] s2 = ((Extension) obj).getE().toArray();

            Comparator c = (o1, o2) -> {
                if (((State) o1).getId() > ((State) o2).getId()) return 1;
                else if (((State) o1).getId() < ((State) o2).getId()) return -1;
                else return 0;
            };

            Arrays.sort(s1, c);
            Arrays.sort(s2, c);

            for (int i = 0; i < s1.length; i++) {
                if (((State) s1[i]).getId() != ((State) s2[i]).getId()) return false;
            }
            return true;
        }
    }
}
