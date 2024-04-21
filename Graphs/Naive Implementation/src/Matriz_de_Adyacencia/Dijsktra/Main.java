package Matriz_de_Adyacencia.Dijsktra;
import java.util.Scanner;


/*

LINK OF PROBLEM :https://cses.fi/problemset/task/1671
LINK OF SUBMISSION : https://cses.fi/problemset/result/9044891/

There are n cities and m flight connections between them. Your task is to determine the length of the shortest route from Syrjälä to every city.
Input
The first input line has two integers n and m: the number of cities and flight connections. The cities are numbered 1,2,\dots,n, and city 1 is Syrjälä.
After that, there are m lines describing the flight connections. Each line has three integers a, b and c: a flight begins at city a, ends at city b, and its length is c. Each flight is a one-way flight.
You can assume that it is possible to travel from Syrjälä to all other cities.
Output
Print n integers: the shortest route lengths from Syrjälä to cities 1,2,\dots,n.
Constraints

1 <= n <= 10^5
1 <= m <= 2 * 10^5
1 <= a,b <= n
1 <= c  10^9

Example
Input:
3 4
1 2 6
1 3 2
3 2 3
1 3 4

Output:
0 5 2


*/


public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Leer el número de ciudades y conexiones
        int numCiudades = scanner.nextInt();
        int numRutas = scanner.nextInt();

        // Crear una instancia de GrafoMatriz_Ady
        GrafoMatriz_Ady grafo = new GrafoMatriz_Ady();

        // Leer las rutas y agregarlas al grafo
        for (int i = 0; i < numRutas; i++) {
            int origen = scanner.nextInt();
            int destino = scanner.nextInt();
            float longitud = scanner.nextFloat();
              if (grafo.getMatriz()[grafo.insVertice(Integer.toString(origen))][grafo.insVertice(Integer.toString(destino))] == 0 || longitud < grafo.getMatriz()[grafo.insVertice(Integer.toString(origen))][grafo.insVertice(Integer.toString(destino))]) {
                // Si no hay arista o la nueva arista tiene un peso menor, actualizar la matriz
                grafo.insArista(Integer.toString(origen), Integer.toString(destino), longitud);
            }
        }

        scanner.close();

        // Calcular el camino más corto desde la ciudad 1 (Syrjälä) a todas las ciudades
        grafo.caminoMConPesosPositivos("1");

        // Imprimir las distancias más cortas desde Syrjälä a todas las ciudades
        for (int i = 1; i <= numCiudades; i++) {
            System.out.print((int) grafo.getTabla()[grafo.insVertice(Integer.toString(i))].getDist() + " ");
        }
    }
}

/*
3 4 
1 2 6 
1 3 2 
3 2 3 
1 3 4

*/

