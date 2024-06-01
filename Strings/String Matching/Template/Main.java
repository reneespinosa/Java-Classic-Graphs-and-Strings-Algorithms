import java.util.*;
import java.io.*;


public class Main {

    public static void main(String args[]) throws IOException {
        _Scanner reader = new _Scanner(System.in);
        FastWriter w = new FastWriter();

        String s = reader.nextString();
        String p = reader.nextString();


        w.flush();
        w.close();
    }
}
 
class Hash implements Comparable<Hash> {
    public static final long MOD = 1000000007;

    private long val;

    public Hash(long val) {
        this.val = val;
    }

    public Hash(char c) {
        this.val = c - 'a' + 1;
    }

    public long getVal() {
        return val;
    }

    public Hash add(Hash other) {
        return new Hash((this.val + other.val) % MOD);
    }

    public Hash sub(Hash other) {
        return new Hash((this.val + MOD - other.val) % MOD);
    }

    public Hash prod(Hash other) {
        return new Hash((this.val * other.val) % MOD);
    }

    public boolean equals(Hash other) {
        return this.val == other.val;
    }

    public int compareTo(Hash other) {
        long cmp = this.val - other.val;
        return cmp == 0 ? 0 : (cmp < 0 ? -1 : 1);
    }

    public String toString() {
        return "[" + this.val + "]";
    }
}

class Hasher {
    public static final int MAX_LENGTH = 1000005;
    public static final Hash BASE = new Hash(887);

    private static Hash[] P;
    private Hash[] H;

    public Hasher(String S) {
        if (P == null) {
            P = new Hash[MAX_LENGTH];

            P[0] = new Hash(1);
            for (int i = 1; i < MAX_LENGTH; i++)
                P[i] = P[i - 1].prod(BASE);

        }

        H = new Hash[S.length()];

        // calcular el hashing de cada prefijo
        for (int i = 0; i < S.length(); i++) {
            H[i] = P[i].prod(new Hash(S.charAt(i)));
            H[i] = H[i].add(i > 0 ? H[i - 1] : new Hash(0));
        }
    }

    public Hash query(int a, int b) {
        if (a > b)
            return new Hash(0);

        Hash beg = a > 0 ? H[a - 1] : new Hash(0);
        Hash end = H[b];

        return end.sub(beg).prod(P[MAX_LENGTH - a - 1]);
    }
}

class KMP {
    private String pattern;
    private int[] fail;

    public KMP(String pattern) {
        this.pattern = pattern;
        this.fail = new int[pattern.length()];
        computeFailFunction();
    }

    private void computeFailFunction() {
        fail[0] = 0;
        for (int i = 1, st; i < pattern.length(); i++) {
            st = fail[i - 1];
            while (st > 0 && pattern.charAt(st) != pattern.charAt(i))
                st = fail[st - 1];
            if (pattern.charAt(st) == pattern.charAt(i))
                fail[i] = st + 1;
        }
    }

    public List<Integer> search(String text) {
        List<Integer> matches = new ArrayList<>();
        for (int i = 0, st = 0; i < text.length(); i++) {
            while (st > 0 && pattern.charAt(st) != text.charAt(i))
                st = fail[st - 1];
            if (pattern.charAt(st) == text.charAt(i))
                st++;
            if (st == pattern.length()) {
                matches.add(i - pattern.length() + 1);
                st = fail[st - 1];
            }
        }
        return matches;
    }
}

class _Scanner {
    InputStream is;

    _Scanner(InputStream is) {
        this.is = is;
    }

    byte[] bb = new byte[1 << 15];
    int k, l;

    byte getc() throws IOException {
        if (k >= l) {
            k = 0;
            l = is.read(bb);
            if (l < 0) return -1;
        }
        return bb[k++];
    }

    byte skip() throws IOException {
        byte b;
        while ((b = getc()) <= 32) ;
        return b;
    }

    int nextInt() throws IOException {
        int n = 0;
        for (byte b = skip(); b > 32; b = getc()) n = n * 10 + b - '0';
        return n;
    }

    float nextFloat() throws IOException {
        int integerPart = 0;
        float decimalPart = 0.0f;
        int divisor = 1;
        boolean isNegative = false;

        byte b = skip();
        if (b == '-') {
            isNegative = true;
            b = getc();
        }

        while ('0' <= b && b <= '9') {
            integerPart = integerPart * 10 + b - '0';
            b = getc();
        }

        if (b == '.') {
            b = getc();
            while ('0' <= b && b <= '9') {
                decimalPart = decimalPart * 10 + b - '0';
                divisor *= 10;
                b = getc();
            }
        }

        float result = integerPart + decimalPart / divisor;
        return isNegative ? -result : result;
    }

    String nextString() throws IOException {
        StringBuilder sb = new StringBuilder();
        byte b = skip();
        while (b > 32) {
            sb.append((char) b);
            b = getc();
        }
        return sb.toString();
    }
}

class FastWriter {
    BufferedWriter bw;

    public FastWriter() {
        bw = new BufferedWriter(new OutputStreamWriter(System.out));
    }

    public void print(Object o) throws IOException {
        bw.write(o.toString());
    }

    public void println(Object o) throws IOException {
        print(o);
        bw.newLine();
    }

    public void flush() throws IOException {
        bw.flush();
    }

    public void close() throws IOException {
        bw.close();
    }
}