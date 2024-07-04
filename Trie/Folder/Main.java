import java.util.*;
import java.io.*;

//LINK : https://dmoj.uclv.edu.cu/src/329090

public class Main {

    public static void main(String args[]) throws IOException {
        _Scanner reader = new _Scanner(System.in);
        FastWriter w = new FastWriter();

        int n = reader.nextInt();

        Trie<Integer> T = new Trie<>(52) {
            @Override
            protected int getCharCode(char c) {
                if (c >= 'a' && c <= 'z') {
                    return c - 'a';  // minúsculas: 0-25
                } else if (c >= 'A' && c <= 'Z') {
                    return c - 'A' + 26;  // mayúsculas: 26-51
                }
                throw new IllegalArgumentException("Invalid character: " + c);
            }
        };

        for (int i = 0; i < n; i++) {

            String S = reader.nextString();
            if(S.equals("add"))
            {
                S = reader.nextString();
                T.agregar(S,(i));
            }
            else{
              
                S = reader.nextString();
                w.println(T.buscar(S));
            }
        }

        

        w.flush();
        w.close();
    }
}


class Node<T> {
    private boolean terminal;
    private int[] next;
    private T value;
    private int words = 0;

    public Node(int alphabetSize, boolean terminal) {
        this.next = new int[alphabetSize];
        this.terminal = terminal;

        for (int i = 0; i < alphabetSize; i++)
            this.next[i] = -1;
    }

    public Node(int alphabetSize, boolean esTerminal, T value) {
        this(alphabetSize, esTerminal);
        this.value = value;
        
    }

    public int[] getNext() {
        return next;
    }

    public boolean isTerminal() {
        return terminal;
    }

    public void setTerminal(boolean terminal) {
        this.terminal = terminal;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    public void incWords()
    {
        this.words++;
    }

    public int getWords()
    {
        return this.words;
    }
}

abstract class Trie<T> {
    private int alphabetSize;
    private List<Node<T>> nodes;

    public Trie(int alphabetSize) {
        this.alphabetSize = alphabetSize;
        this.nodes = new ArrayList<>();
        crearNodo();
    }

    private int crearNodo() {
        nodes.add(new Node<>(this.alphabetSize, false));
        return nodes.size() - 1;
    }

    protected abstract int getCharCode(char c);

    public void agregar(String S, T value) {
        int nod = 0;

        for (int i = 0; i < S.length(); i++) {
            int c = getCharCode(S.charAt(i));
            
            int[] next = nodes.get(nod).getNext();

            if (next[c] == -1)
                next[c] = crearNodo();
            nod = next[c];
            nodes.get(nod).incWords();
        }
        nodes.get(nod).setTerminal(true);
        nodes.get(nod).setValue(value);
    }

    public int buscar(String S) {
        int nod = 0;

        for (int i = 0; i < S.length(); i++) {
            int c = getCharCode(S.charAt(i));
            int[] next = nodes.get(nod).getNext();

            if (next[c] == -1)
                return 0;
            nod = next[c];
        }
            return nodes.get(nod).getWords();
       
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