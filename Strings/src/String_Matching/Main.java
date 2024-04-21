package String_Matching;

public class Main {

    public static void main(String[] args) {
        String alfabeto0 = "abd";
        String texto0 = "aaabaadaabaaa";
        String patron0 = "aabaaa";

        System.out.println("P: " + patron0 + "    T: " + texto0 + "    alfabeto: " + alfabeto0);
        System.out.println("Fuerza Bruta: " + busquedaPatrones.fuerzaBruta(texto0, patron0));
        System.out.println("Boyer-Moore: " + busquedaPatrones.boyerMoore(texto0, patron0, alfabeto0));
        System.out.println("KMP: " + busquedaPatrones.kMP(texto0, patron0));

        System.out.println("Función last:");
        int[] last = busquedaPatrones.lastfuncion(patron0, alfabeto0);
        for (int i = 0; i < last.length; i++) {
            System.out.print(alfabeto0.charAt(i) + " -> " + last[i] + "   ");
        }
        System.out.println("");

        System.out.println("Función falla:");
        int[] fail = busquedaPatrones.fallafuncion(patron0);
        for (int i = 0; i < fail.length; i++) {
            System.out.print(patron0.charAt(i) + " -> " + fail[i] + "   ");
        }
        System.out.println();
        String temp = "cgtacgttcgtac";
        System.out.println("Función falla del string " + temp + ":");
        int[] fail2 = busquedaPatrones.fallafuncion(temp);
        for (int i = 0; i < fail2.length; i++) {
            System.out.print(temp.charAt(i) + " -> " + fail2[i] + "   ");
        }
        System.out.println("");
        String alfabeto = "a";
        String texto = "aaaaaa";
        String patron = "aa";
        int lastMatch1 = busquedaPatrones.fuerzaBrutaUltimaCoincidencia(texto, patron);
        System.out.println("\nFuerza Bruta Modificado:\nLa última coincidencia del patrón " + patron + " en la cadena " + texto + " es " + lastMatch1);

        int lastMatch2 = busquedaPatrones.boyerMooreUltimaCoincidencia(texto, patron, alfabeto);
        System.out.println("\nBoyer Moore Modificado:\nLa última coincidencia del patrón " + patron + " en la cadena " + texto + " es " + lastMatch2);

        int lastMatch3 = busquedaPatrones.kMPUltimaCoincidencia(texto, patron);
        System.out.println("\nKMP Modificado:\nLa última coincidencia del patrón " + patron + " en la cadena " + texto + " es " + lastMatch3);

        //para crear el autámata finito manualmente sería de la siguiente manera,
        //como en este caso el usuario es quien llena manualmente la matriz con
        //la función de transición, de esta manera pudieran existir errores del usuario.
        System.out.println();
        Automatafinito a = new Automatafinito("babab", "ab");
        a.addtransition(0, 'a', 0);
        a.addtransition(0, 'b', 1);
        a.addtransition(1, 'a', 2);
        a.addtransition(1, 'b', 1);
        a.addtransition(2, 'a', 0);
        a.addtransition(2, 'b', 3);
        a.addtransition(3, 'a', 4);
        a.addtransition(3, 'b', 1);
        a.addtransition(4, 'a', 0);
        a.addtransition(4, 'b', 5);
        a.addtransition(5, 'a', 4);
        a.addtransition(5, 'b', 1);
        int lastMatch4 = busquedaPatrones.smAFUltimaOcurr("babab", "aabbabbbababaabababbb", a);
        System.out.println("Autómata Finito Modificado:\nLa última coincidencia del patrón \"bab\" en la cadena \"babababababababab\" es " + lastMatch4);

        System.out.println();
        Automatafinito a2 = new Automatafinito("aba", "abababa");
        a2.addtransition(0, 'a', 1);
        a2.addtransition(0, 'b', 0);
        a2.addtransition(1, 'a', 1);
        a2.addtransition(1, 'b', 2);
        a2.addtransition(2, 'a', 3);
        a2.addtransition(2, 'b', 0);
        a2.addtransition(3, 'a', 1);
        a2.addtransition(3, 'b', 2);
        int lastMatch5 = busquedaPatrones.smAFUltimaOcurr("aba", "abababa", a2);
        System.out.println("Autómata Finito Modificado:\nLa última coincidencia del patrón \"aba\" en la cadena \"abababa\" es " + lastMatch5);

        System.out.println();
        Automatafinito a3 = new Automatafinito("aa", "aaaaaaaa");
        a3.addtransition(0, 'a', 1);
        a3.addtransition(1, 'a', 2);
        a3.addtransition(2, 'a', 2);

        int lastMatch6 = busquedaPatrones.smAFUltimaOcurr("aa", "aaaaaaaa", a3);
        System.out.println("Autómata Finito Modificado:\nLa última coincidencia del patrón \"aa\" en la cadena \"aaaaaaaa\" es " + lastMatch6);
    }

}