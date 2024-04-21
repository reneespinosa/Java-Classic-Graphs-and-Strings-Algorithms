
package String_Matching;


public class Automatafinito {

    private int[][] T;
    private String P;
    private String alfabeto;

    public Automatafinito(String Patron, String alfabeto) {
        this.alfabeto = alfabeto;
        this.P = Patron;
        T = new int[P.length() + 1][alfabeto.length()];
    }

    public void addtransition(int estadoinicial, char simbolo, int estadodestino) { //permite al ususario adicionar una transición de manera directa
        int pos = alfabeto.indexOf(simbolo);
        T[estadoinicial][pos] = estadodestino;
//        System.out.println("T["+estadoinicial+"]"+"["+pos+"]"+" = "+estadodestino);
    }

    public int gettransition(int q, char c) {//devuelve el estado al que se llega mediante la transición desde el estado q con el símbolo c
        int pos = alfabeto.indexOf(c);
        return T[q][pos];
    }

    public void functionTransition(String P) {//implementar utilizando seudocódigo de la conferencia

    }
}