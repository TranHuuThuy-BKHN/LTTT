import java.util.ArrayList;
import java.util.List;

public class DFA2 {

    private List<States> states;

    //mảng chưa id các trạng thái để giúp set id các Link2 dễ dàng hơn
    private int ids[][];

    private int id = 0;

    public DFA2(ArrayList<State> p, ArrayList<State> q) {

        states = new ArrayList<>();
        ids = new int[p.size()][q.size()];

        for (int i = 0; i < ids.length; i++) {
            for (int j = 0; j < ids[i].length; j++) {
                ids[i][j] = id++;
            }
        }
        id = 0;
        //khởi tạo các trạng thái
        p.forEach(s -> {
            q.forEach(s1 -> {
                states.add(new States(id++, ids, s, s1));
            });
        });

    }

    public List<States> getStates() {
        return states;
    }
}
