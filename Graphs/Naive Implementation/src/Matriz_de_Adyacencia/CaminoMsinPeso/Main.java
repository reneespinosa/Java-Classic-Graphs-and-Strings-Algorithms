package Matriz_de_Adyacencia.CaminoMsinPeso;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        
        /*
        
        LINK del Problema: https://cses.fi/problemset/task/1667/
        LINK de Envio: https://cses.fi/problemset/result/9044649/
        
            Syrjälä's network has n computers and m connections. Your task is to find out if Uolevi can send a message to Maija, and if it is possible, what is the minimum number of computers on such a route.
        Input
            The first input line has two integers n and m: the number of computers and connections. The computers are numbered 1,2,\dots,n. Uolevi's computer is 1 and Maija's computer is n.
            Then, there are m lines describing the connections. Each line has two integers a and b: there is a connection between those computers.
            Every connection is between two different computers, and there is at most one connection between any two computers.
            Output
            If it is possible to send a message, first print k: the minimum number of computers on a valid route. After this, print an example of such a route. You can print any valid solution.
            If there are no routes, print "IMPOSSIBLE".
            Constraints

            2 <= n <= 10^5
            1 <= m <= 2*10^5
            1 <= a,b <= n

            Example
        
            Input:
            5 5
            1 2
            1 3
            1 4
            2 3
            5 4

            Output:
            3
            1 4 5
        */
        
        
        Scanner scanner = new Scanner(System.in);

        int n = scanner.nextInt(); // número de computadoras
        int m = scanner.nextInt(); // número de conexiones

        GrafoMatriz_Ady grafo = new GrafoMatriz_Ady();

        // Insertar vértices y aristas en el grafo
        for (int i = 0; i < m; i++) {
            int origen = scanner.nextInt();
            int destino = scanner.nextInt();
            grafo.insArista(Integer.toString(origen), Integer.toString(destino));
            grafo.insArista(Integer.toString(destino), Integer.toString(origen));
            
        }

        // Encontrar la ruta más corta usando el método de caminos mínimos sin pesos
        grafo.caminoMSinPesos("1");

        // Obtener el número mínimo de computadoras en la ruta
        Vertice[] tabla = grafo.getTabla();
        int minComputadoras = (int) tabla[grafo.buscar(Integer.toString(n))].getDist();

        if (minComputadoras == (int)grafo.getINFINITO()) {
            System.out.println("IMPOSSIBLE");
        } else {
            System.out.println(minComputadoras + 1); // se suma 1 para incluir el vértice de Uolevi (1)
            grafo.imprimirCamino(Integer.toString(n));
        }

        scanner.close();
    }
}
