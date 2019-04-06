import java.math.BigInteger;
import java.util.HashMap;

/**
 * @author TranHuuThuy
 * lớp đếm số xâu nguyên thủy nhị phân có n bits
 */
public class Counter {
    private int n = 10;

    private BigInteger exc = BigInteger.ZERO;


    public Counter(int n) {
        this.n = n;
    }

    public void setN(int n) {
        this.n = n;
    }

    /**
     * phương thức trả về số xâu nguyên mẫu so n bits
     *
     * @return
     */
    public BigInteger getAmount() {
        HashMap<Integer, Integer> pair = new Number(n).get();
        //nếu n là số nguyên tố thì số xâu nhị phân nguyên mẫu là 2^n - 2
        if (pair == null) return BigInteger.TWO.pow(n).subtract(BigInteger.TWO);

            //ngược lại ta phân tích thành tích các thùa số nguyên tố
        else if (pair.size() == 1) {
            int e = (Integer) pair.keySet().toArray()[0];
            return BigInteger.TWO.pow(n).subtract(BigInteger.TWO.pow(e));
        } else {
            exc = BigInteger.ZERO;
            pair.forEach((num, e) -> {
                exc = exc.add(BigInteger.TWO.pow((int) Math.pow(num, e)));
            });
            return BigInteger.TWO.pow(n).subtract(exc).add(new BigInteger(2* (pair.size() - 1) + ""));
        }
    }

}
