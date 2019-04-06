public class Lyndon {
    private String lyndon;

    public Lyndon(String lyndon) {
        this.lyndon = lyndon;
    }

    public String getLyndon() {
        return lyndon;
    }

    /**
     * phương thức so sánh hai chuỗi lyndon theo thứ tự anpha-beta
     *
     * @param l
     * @return
     */
    public int compareTo(Lyndon l) {
        int c = lyndon.compareTo(l.lyndon);
        if (c > 0) return 1;
        else if (c == 0) return 0;
        else return -1;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Lyndon == false) return false;
        return lyndon.equals(((Lyndon) obj).lyndon);
    }

    /**
     * phương thức cộng hai Lyndon để tạo ra một lyndon khác
     * phương thức sử dụng tính chất : nếu u, v là Lyndon và u < v thì uv cũng là Lyndon
     *
     * @param l
     * @return
     */
    public Lyndon plus(Lyndon l) {
        int c = compareTo(l);
        if (c == 1) return new Lyndon(l.lyndon + lyndon);
        else if (c == -1) return new Lyndon(lyndon + l.lyndon);
        return null;
    }

}
