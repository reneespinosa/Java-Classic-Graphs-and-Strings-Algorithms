package Matriz_de_Adyacencia.BellmanFord;
import java.util.Scanner;

/*
 
    LINK OF PROBLEM : https://basecamp.eolymp.com/en/problems/1453


*/

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int numCiudades = sc.nextInt();
        int numRutas = sc.nextInt();

        GrafoMatriz_Ady g = new GrafoMatriz_Ady();

        // Leer las rutas y agregarlas al grafo
        for (int i = 0; i < numRutas; i++) {
            int origen = sc.nextInt();
            int destino = sc.nextInt();
            float longitud = sc.nextFloat();
             if (g.getMatriz()[g.insVertice(Integer.toString(origen))][g.insVertice(Integer.toString(destino))] == 0 || longitud < g.getMatriz()[g.insVertice(Integer.toString(origen))][g.insVertice(Integer.toString(destino))]) {
                // Si no hay arista o la nueva arista tiene un peso menor, actualizar la matriz
                g.insArista(Integer.toString(origen), Integer.toString(destino), longitud);
            }
        }

        sc.close();
        g.caminoMConPesosNegativos("1");
        
        for (int i = 1; i <= numCiudades; i++) {
            if (g.getTabla()[g.insVertice(Integer.toString(i))] == null) {
                System.out.print(30000 + " ");
            } 
            else if((int)g.getTabla()[g.insVertice(Integer.toString(i))].getDist()==(int)g.getINFINITO())
            {
                 System.out.print(30000 + " ");
            }
            else {
                System.out.print((int) g.getTabla()[g.insVertice(Integer.toString(i))].getDist() + " ");
            }
        }
    }
}


/*
4 5
 1 2 10
 2 3 10
 1 3 100 
3 1 -10 
2 3 1


*/