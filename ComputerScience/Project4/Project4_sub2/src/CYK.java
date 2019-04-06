import java.util.ArrayList;

public class CYK {
    //văn pham đã ở dạng chuẩn Chomskey
    private Grammar g;

    public CYK(Grammar grammar) {
        this.g = grammar;
    }

    /**
     * phương thức kiểm tra xem một chuỗi kí tự có được sinh ra bởi văn phạm g hay không
     *
     * @param w : chuỗi cần kiểm tra
     * @return
     */
    public boolean check(String w) {
        int n = w.length();
        //nếu chuỗi đầu vào rỗng, ta chỉ cần kiểm tra xem có quy tắc startSymbol --> ε
        if (n == 0) return g.getGrammar().get(0).getRights().contains(Grammar.epsilon);

        ArrayList<String> table[][] = new ArrayList[n][n];

        //đối với các chuỗi con của w có độ dài 1
        for (int i = 0; i < n; i++) {
            table[0][i] = getLefts(w.charAt(i) + "");
        }
        //đối với các chuỗi con của w có độ dài từ 2 trở đi
        for (int l = 2; l <= n; l++) {                    // l < n tương ứng với độ dài chuỗi con
            for (int i = 0; i <= n - l; i++) {             // i tương ứng với vị trí chuỗi con trong w
                String subStr = w.substring(i, i + l);  // chuỗi con có đọ dài l tại vị trí i
                table[l - 1][i] = new ArrayList<>();
                //xem xét các khả năng chia subStr thành 2 chuỗi
                for (int j = 1; j < l; j++) {
                    String subStr1 = subStr.substring(0, j);
                    String subStr2 = subStr.substring(j);
                    //Tập các biến sinh ra 2 chuỗi con này, và nó đã có trong bảng table
                    ArrayList<String> lefts1 = table[j - 1][i];
                    ArrayList<String> lefts2 = table[l - j - 1][i + j];
                    for (String s1 : lefts1) {
                        for (String s2 : lefts2) {
                            table[l - 1][i].addAll(getLefts(s1 + s2));
                        }
                    }
                }
            }
        }

        return table[n - 1][0].contains(g.getGrammar().get(0).getLeft());
    }


    /**
     * phương thức trả về tập các biến sinh ra vế phải nào đó
     * VD : Văn phạm S --> RT, R --> TR|a, T --> TR|b
     * right = TR thì trả về (R, T)
     *
     * @param right : vế phải đang xét
     * @return
     */
    private ArrayList<String> getLefts(String right) {
        ArrayList<String> res = new ArrayList<>();
        g.getGrammar().forEach(production -> {
            if (production.getRights().contains(right))
                res.add(production.getLeft());
        });
        return res;
    }
}
