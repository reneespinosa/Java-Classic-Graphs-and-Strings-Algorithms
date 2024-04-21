package Matriz_de_Adyacencia.CaminosMaciclicos;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        
        Scanner sc = new Scanner(System.in);
        
        int n = sc.nextInt();
        
        GrafoMatriz_Ady grafo = new GrafoMatriz_Ady();

        // Construyendo el grafo según las reglas del problema
        for (int i = 1; i <= n; i++) {
            if (i + 1 <= n) {
                grafo.insArista(Integer.toString(i), Integer.toString(i + 1), 1);
            }
            if (3 * i <= n) {
                grafo.insArista(Integer.toString(i), Integer.toString(3 * i), 1);
            }
        }
        
        grafo.caminoMAciclico("1");

        System.out.println((int) grafo.getTabla()[grafo.insVertice(Integer.toString(n))].getDist());
    }
}
