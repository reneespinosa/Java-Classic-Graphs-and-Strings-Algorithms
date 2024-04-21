package Matriz_de_Adyacencia.DFS;

import Matriz_de_Adyacencia.BFS.GrafoMatriz_Ady;
import java.util.ArrayList;
import java.util.Scanner;

/*
LINK: https://cses.fi/problemset/task/1666/
LINK OF SUBMISSION : https://cses.fi/problemset/result/9063372/


    Byteland has n cities, and m roads between them. The goal is to construct new roads so that there is a route between any two cities.
Your task is to find out the minimum number of roads required, and also determine which roads should be built.
Input
The first input line has two integers n and m: the number of cities and roads. The cities are numbered 1,2,\dots,n.
After that, there are m lines describing the roads. Each line has two integers a and b: there is a road between those cities.
A road always connects two different cities, and there is at most one road between any two cities.
Output
First print an integer k: the number of required roads.
Then, print k lines that describe the new roads. You can print any valid solution.
Constraints

1 <= n <= 10^5
1 <= m <= 2*10^5
1 <= a,b <= n

Example
Input:
4 2
1 2
3 4

Output:
1
2 3

*/


public class Main {
    public static void main(String[] args) {

        // NOTE : THERE IS A MODIFICATION OF DFS IMPLEMENTATION USED SO FAR

        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        int m = sc.nextInt();

        GrafoMatriz_Ady g = new GrafoMatriz_Ady();
        for (int i = 1; i <= n; i++)
            g.insVertice(Integer.toString(i));

        for (int i = 0; i < m; i++) {
            int origen = sc.nextInt();
            int destino = sc.nextInt();
            g.insArista(Integer.toString(origen), Integer.toString(destino));
            g.insArista(Integer.toString(destino), Integer.toString(origen));
        }

        ArrayList<Integer> componentesConexas = new ArrayList<>();
    
        for (int i = 1; i <= n; i++) {
            
            if (g.getTabla()[g.buscar(Integer.toString(i))].getExtra() == 0) {
                
                g.recorrido_profundidad(Integer.toString(i)); 
                if(i>1)componentesConexas.add(i); 
            }
        }

       
        System.out.println(componentesConexas.size());

        for (int i = 0; i < componentesConexas.size(); i++) {
            System.out.println(1 + " " + componentesConexas.get(i));
        }

    }
}

/*
4 2
1 2
3 4
*/