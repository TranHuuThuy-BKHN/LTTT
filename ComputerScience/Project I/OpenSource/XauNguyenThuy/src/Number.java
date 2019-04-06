import java.util.HashMap;

public class Number {
    private int number = 1;

    public Number(int number) {
        this.number = number;
    }

    //kiểm tra số nguyên tố
    private boolean isPrime() {
        if (number < 2) return false;
        int sqrt = (int) Math.sqrt(number);
        for (int i = 2; i <= sqrt; i++) {
            if (number % i == 0) return false;
        }
        return true;
    }

    //phân tích một số ra thừa số nguyên tố, nếu nó số nguyên tố thì trả về null
    public HashMap<Integer, Integer> get() {
        if (isPrime()) return null;
        HashMap<Integer, Integer> res = new HashMap<>();
        int val = number, i = 2;
        while (i <= val) {
            if (val % i == 0) {
                if (res.get(i) == null) {
                    res.put(i, 1);
                } else res.put(i, res.get(i) + 1);
                val = val / i;
            } else i++;
        }
        return res;
    }

}
