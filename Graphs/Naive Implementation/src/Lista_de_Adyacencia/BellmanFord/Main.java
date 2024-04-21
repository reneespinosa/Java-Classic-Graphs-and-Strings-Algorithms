
package Lista_de_Adyacencia.BellmanFord;

import java.util.Scanner;

/*
 
    LINK OF PROBLEM : https://basecamp.eolymp.com/en/problems/1453

    IMPORTANT : WA , pass 74% of test cases , need to check this 
    NOTE : (the judge isn't well know)

*/

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int numCiudades = sc.nextInt();
        int numRutas = sc.nextInt();

        GrafoLista_Adyacencia g = new GrafoLista_Adyacencia();

        // Leer las rutas y agregarlas al grafo
        for (int i = 0; i < numRutas; i++) {
            int origen = sc.nextInt();
            int destino = sc.nextInt();
            float longitud = sc.nextFloat();
            g.insArista(Integer.toString(origen), Integer.toString(destino),  longitud);
            
        }

        // g.imprimirGrafo();
        sc.close();
        g.caminoMConPesosNegativos("1");
        
        for(int i=1;i<=numCiudades;i++)
        {
            if((int)g.getTabla()[g.insVertice(String.valueOf(i))].getDist()==(int)g.getINFINITO())
            {
                System.out.print(30000+" ");
            }
            else{
                System.out.print((int)g.getTabla()[g.insVertice(String.valueOf(i))].getDist() + " ");
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