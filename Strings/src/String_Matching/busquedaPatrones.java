package String_Matching;

public class busquedaPatrones {

    public static int fuerzaBruta(String T, String P) {
        if (P.length() > T.length()) {
            return -1;
        }
        int n = T.length();
        int m = P.length();
        int j;
        for (int i = 0; i <= n - m; i++) {
            j = 0;
            while (j < m && T.charAt(i + j) == P.charAt(j)) {
                j++;
            }
            if (j == m) {
                return i;
            }
        }
        return -1;
    }

    public static int fuerzaBrutaUltimaCoincidencia(String T, String P) {
        if (P.length() > T.length()) {
            return -1;
        }
        int n = T.length();
        int m = P.length();
        int j;
        int lastMatch = -1;
        for (int i = 0; i <= n - m; i++) {
            j = 0;
            while (j < m && T.charAt(i + j) == P.charAt(j)) {
                j++;
            }
            if (j == m) {
                lastMatch = i;
            }
        }
        return lastMatch;
    }

    public static int[] lastfuncion(String P, String alfabeto) {
        int[] last = new int[alfabeto.length()];
        for (int i = 0; i < last.length; i++) {
            last[i] = -1;
        }
        int m = P.length();
        for (int i = m - 1; i >= 0; i--) {
            for (int j = 0; j < alfabeto.length(); j++) {
                if (P.charAt(i) == alfabeto.charAt(j) && last[j] == -1) {
                    last[j] = i;
                }
            }
        }
        return last;
    }

    public static int[] lastfuncion2(String P, String alfabeto) {
        int[] last = new int[alfabeto.length()];
        for (int i = 0; i < last.length; i++) {
            last[i] = P.lastIndexOf(alfabeto.charAt(i));
        }
        return last;
    }

    public static int boyerMoore(String T, String P, String alfabeto) {
        if (P.length() > T.length()) {
            return -1;
        }
        int[] last = lastfuncion(P, alfabeto);
        int n = T.length();
        int m = P.length();
        int j = m - 1;
        int i = m - 1;
        do {
            if (P.charAt(j) == T.charAt(i)) {
                if (j == 0) {
                    return i;
                } else {
                    i--;
                    j--;
                }
            } else {
                int pos = alfabeto.indexOf(T.charAt(i));
                int min = Math.min(j, 1 + last[pos]);
                i = i + m - min;
                j = m - 1;

            }
        } while (i <= n - 1);
        return -1;
    }

    public static int boyerMooreUltimaCoincidencia(String T, String P, String alfabeto) { //Boyer-Moore modificado para que encuentre la última coincidencia del patrón en la cadena
        if (P.length() > T.length()) {
            return -1;
        }
        int[] last = lastfuncion(P, alfabeto);
        int n = T.length();
        int m = P.length();
        int j = m - 1;
        int i = m - 1;
        int lastMatch = -1;
        do {
            if (P.charAt(j) == T.charAt(i)) {
                if (j == 0) {
                    lastMatch = i;
                    i = i + m;
                    j = m - 1;
                } else {
                    i--;
                    j--;
                }
            } else {
                int pos = alfabeto.indexOf(T.charAt(i));
                int min = Math.min(j, 1 + last[pos]);
                i = i + m - min;
                j = m - 1;

            }
        } while (i <= n - 1);
        return lastMatch;
    }

    public static int[] fallafuncion(String P) {
        int[] falla = new int[P.length()];
        falla[0] = 0;
        int m = P.length();
        int j = 0;
        int i = 1;
        while (i < m) {
            if (P.charAt(j) == P.charAt(i)) {
                falla[i] = j + 1;
                i++;
                j++;
            } else if (j > 0) {
                j = falla[j - 1];
            } else {
                falla[i] = 0;
                i++;
            }
        }
        return falla;
    }

    public static int kMP(String T, String P) {
        if (P.length() > T.length()) {
            return -1;
        }
        int[] fail = fallafuncion(P);
        int n = T.length();
        int m = P.length();
        int i = 0;
        int j = 0;
        while (i < n) {
            if (P.charAt(j) == T.charAt(i)) {
                if (j == m - 1) {
                    return i - m + 1;
                }
                i++;
                j++;
            } else if (j > 0) {
                j = fail[j - 1];
            } else {
                i++;
            }
        }
        return -1;
    }

    public static int kMPUltimaCoincidencia(String T, String P) {
        if (P.length() > T.length()) {
            return -1;
        }
        int[] fail = fallafuncion(P);
        int n = T.length();
        int m = P.length();
        int i = 0;
        int j = 0;
        int lastMatch = -1;
        while (i < n) {
            if (P.charAt(j) == T.charAt(i)) {
                if (j == m - 1) {
                    lastMatch = i - m + 1;
                    j = -1;
                    i = i - m + 1;
                }
                i++;
                j++;
            } else if (j > 0) {
                j = fail[j - 1];
            } else {
                i++;
            }
        }
        return lastMatch;
    }

    //aquí esta la programación del algoritmo de autómata finito   
    public static int smAF(String P, String T, Automatafinito a) {
        int n = T.length();
        int m = P.length();
        if (m > n) {
            return -1;
        }
        int q = 0;
        for (int i = 0; i < n; i++) {
            q = a.gettransition(q, T.charAt(i));
            if (q == m) {
                return i - m + 1;
            }
        }
        return -1;
    }

    public static int smAFUltimaOcurr(String P, String T, Automatafinito a) {
        int n = T.length();
        int m = P.length();
        if (m > n) {
            return -1;
        }
        int q = 0;
        int lastMatch = -1;
        int i = 0;
        while (i < n) {
            q = a.gettransition(q, T.charAt(i));
            if (q == m) {
                lastMatch = i - m + 1;
                i = lastMatch + 1;
                q = a.gettransition(0, T.charAt(i));
            }
            i++;
        }
        return lastMatch;
    }
}

