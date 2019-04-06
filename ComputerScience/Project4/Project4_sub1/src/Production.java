import java.util.HashSet;

public class Production {
    private String left;
    private HashSet<String> rights;

    public Production(String left) {
        this.left = left;
        rights = new HashSet<>();
    }

    public Production(String left, String right){
        this(left);
        addRight(right);
    }

    public String getLeft() {
        return left;
    }

    public HashSet<String> getRights() {
        return rights;
    }

    //phương thức thêm một vế phải vào sản xuất
    boolean addRight(String right) {
        return right.equals(left) == false && rights.add(right);
    }

    //phương thức xóa một vế phải khỏi sản suất
    boolean remove(String right) {
        return rights.remove(right);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Production == false) return false;
        return left.equals(((Production) obj).left);
    }
}
