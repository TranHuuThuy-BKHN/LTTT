
import java.util.LinkedList;
import java.util.Scanner;

public class Main {

    /**
     * phương thức sử sử dụng thuật toán duval tách một chuỗi thỏa mãn định lý Lyndon
     *
     * @param s
     * @return
     */
    public static LinkedList<String> duval(String s) {
        LinkedList<String> list = new LinkedList<>();

        int i = 0, j = 0, n = s.length();
        while (i < n) {
            j = i + 1;
            while (j < n && s.charAt(i) < s.charAt(j)) {
                j++;
            }
            list.add(s.substring(i, j));
            i = j;
            int k = 1, size = list.size();
            //từ các lyndon trên ta cần tạo các lyndon có thứ tự anpha-beta
            while (k++ < size) {
                String last = list.getLast(), last2 = list.get(list.size() - 2);
                if (last.compareTo(last2) > 0) {
                    list.removeLast();
                    list.removeLast();
                    list.add(last2 + last);
                }
            }
        }


        return list;
    }

    public static void main(String[] args) {
        String s = "";
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.print("Binary String : ");
            s = sc.nextLine();
            duval(s).forEach(str -> System.out.println(str + " "));
        }
    }
}