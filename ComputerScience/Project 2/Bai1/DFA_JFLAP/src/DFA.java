import java.util.ArrayList;

public class DFA {
    //một Otomat hữu hạn đơn định chứa các trạng thái, các trạng thái đã chứa sắn các liên kết
    private ArrayList<State> states;

    public DFA(ArrayList<State> states) {
        this.states = states;
    }

    public boolean isYes(String w) {
        int i = 0, n = w.length();
        State current = states.get(0);

        //nếu xâu kiểm tra là xâu rỗng và trạng thái ban đầu là trạng thái chấp nhận được thì ok
        if (current.isDes() && (w == null || w.length() == 0))
            return true;

        while (i < n) {
            //kiểm tra các liên kết với trạng thái hiện tại để tìm trạng thái tiếp theo
            label:
            for (int j = 0; j < current.getLinks().size(); j++) {
                ArrayList<String> reads = current.getLinks().get(j).getReads();
                for (int k = 0; k < reads.size(); k++) {
                    if (reads.get(k).equals(w.charAt(i) + "")) {
                        current = current.getLinks().get(j).getTo();
                        break label;
                    }
                }
            }
            i++;
        }
        return current.isDes();
    }
}
