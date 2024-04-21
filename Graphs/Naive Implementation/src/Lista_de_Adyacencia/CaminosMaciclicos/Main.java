package Lista_de_Adyacencia.CaminosMaciclicos;

import java.util.Scanner;

/*
    LINK OF PROBLEM : https://www.geeksforgeeks.org/shortest-path-for-directed-acyclic-graphs/
    
    NOTE:TLE

    Consider a directed graph whose vertices are numbered from 1 to n. 
    There is an edge from a vertex i to a vertex j if and only if either j = i + 1 
    or j = 3 * i. The task is to find the minimum number of edges in a path from vertex 
    1 to vertex n.

*/


public class Main {
    public static void main(String[] args) {
        
        Scanner sc = new Scanner(System.in);
        
        int n = sc.nextInt();
        
        
        GrafoLista_Adyacencia grafo = new GrafoLista_Adyacencia();

        // Construyendo el grafo según las reglas del problema
        for (int i = 1; i <= n; i++) {
            grafo.insArista(Integer.toString(i), Integer.toString(i + 1),1);
            grafo.insArista(Integer.toString(i), Integer.toString(3 * i),1);
        }
        
        grafo.caminoMAciclico("1");

        System.out.println((int)grafo.getTabla()[grafo.insVertice(String.valueOf(n))].getDist());
        //grafo.imprimirCamino("9");
    }
}

// 4
// 2


// 9
// 2
