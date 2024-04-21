package Matriz_de_Adyacencia.BFS;




import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;



public class GrafoMatriz_Ady implements Grafo {

    private static final int TAMANHO_TABLA_INI = 10;
    private int numVertices;
    private float[][] matriz;
    private Vertice[] tabla;
    private final float INFINITO = Float.MAX_VALUE;

    public GrafoMatriz_Ady() {
        numVertices = 0;
        matriz = new float[TAMANHO_TABLA_INI][TAMANHO_TABLA_INI];
        tabla = new Vertice[TAMANHO_TABLA_INI];
    }

    @Override
    public int insVertice(String nombre) {
        int i = 0;
        while (i < numVertices) {
            if (tabla[i].getNombre().equals(nombre)) {
                return i;
            } else {
                i++;
            }
        }
        Vertice v1 = new Vertice(nombre);
        if (tabla.length == numVertices) {
            duplicarVectorTabla();
        }
        tabla[numVertices] = v1;
        return numVertices++;
    }

    private void duplicarVectorTabla() {
        Vertice[] nuevaTabla = new Vertice[tabla.length * 2];
        System.arraycopy(tabla, 0, nuevaTabla, 0, tabla.length);
        tabla = nuevaTabla;
        float[][] nuevamatriz = new float[matriz.length * 2][matriz.length * 2];
        for (int j = 0; j < matriz.length; j++) {
            System.arraycopy(matriz[j], 0, nuevamatriz[j], 0, matriz.length);
        }
        matriz = nuevamatriz;
    }

    @Override
    public int buscar(String nombre) {
        int i = 0;
        while (i < numVertices) {
            if (tabla[i].getNombre().equals(nombre)) {
                return i;
            } else {
                i++;
            }
        }
        return -1;
    }

    @Override
    public void insArista(String origen, String destino, float costo) {
        int origen2 = insVertice(origen);
        int destino2 = insVertice(destino);
        matriz[origen2][destino2] = costo;

    }

    @Override
    public void insArista(String origen, String destino) {
        int origen2 = insVertice(origen);
        int destino2 = insVertice(destino);
        matriz[origen2][destino2] = 1;
    }

    @Override
    public void elimArista(String origen, String destino) {
        int i = buscar(origen);
        int j = buscar(destino);
        if (i != -1 && j != -1) {
            matriz[i][j] = 0;
        } else {
            System.out.println("la arista no existe");
        }
    }

    @Override
    public void elimVertice(String nombre) {
        int i = buscar(nombre);
        if (i != -1) {
            if (i == numVertices - 1) //si es el ultimo vertice lo unico que tengo que hacer es decrementar la variable
            {
                numVertices--;
            } else {
                tabla[i] = tabla[numVertices - 1];
                numVertices--;
                for (int j = 0; j <= numVertices; j++) {
                    matriz[i][j] = matriz[numVertices][j]; // se copia la fila
                }
                for (int k = 0; k <= numVertices; k++) {
                    matriz[k][i] = matriz[k][numVertices];  //se copia la columna     
                }
            }
            for (int l = 0; l <= numVertices; l++) { // se ponen 0 en la fila y columna que se duplic�
                matriz[numVertices][l] = 0;
                matriz[l][numVertices] = 0;

            }
        } else {
            System.out.println("el vertice no existe");
        }
    }

    @Override
    public boolean esAdyacente(String vertice1, String vertice2) {
        int i = buscar(vertice1);
        int j = buscar(vertice2);
        if (i != -1 && j != -1) {
            return matriz[i][j] != 0;
        } else {
            return false;
        }
    }

    @Override
    public LinkedList recorrido_amplitud(String VerticeOrigen) {
        //limpiarDatos();
        int verticeOrig = insVertice(VerticeOrigen);
        Queue<Integer> q = new LinkedList<>();
        LinkedList<String> L = new LinkedList<>();
       // boolean repetir = true;
        //while (repetir) {
            tabla[verticeOrig].setExtra(1);
            L.add(tabla[verticeOrig].getNombre());
            q.add(verticeOrig);
            while (!q.isEmpty()) {
                int verticeActual = q.remove();
                for (int i = 0; i < numVertices; i++) {
                    if (matriz[verticeActual][i] != 0 && tabla[i].getExtra() != 1) {
                        L.add(tabla[i].getNombre());
                        tabla[i].setExtra(1);
                        q.add(i);
                    }
                }
            }
           // repetir = false;
           // for (int i = 0; i < numVertices; i++) {
           //     if (tabla[i].getExtra() != 1) {
           //         verticeOrig = i;
           //         repetir = true;
           //         break;
           //     }
           // }
        //}
        return L;
    }

    private LinkedList recorrido_profundidadR(String VerticeOrigen, LinkedList L) {
        int VerticeOrig = insVertice(VerticeOrigen);
        tabla[VerticeOrig].setExtra(1);
        L.add(tabla[VerticeOrig].getNombre());

        for (int i = 0; i < numVertices; i++) {
            if (matriz[VerticeOrig][i] != 0 && tabla[i].getExtra() != 1) {
                recorrido_profundidadR(tabla[i].getNombre(), L);
            }
        }
        return L;
    }

    @Override
    public LinkedList recorrido_profundidad(String VerticeOrig) {
        //limpiarDatos();
        LinkedList<String> L = new LinkedList<>();
        recorrido_profundidadR(VerticeOrig, L);
        //for (int j = 0; j < numVertices; j++) {//este for se hace para los vertices aislados del grafo no se queden fuera del recorrido como es el caso del vertice E en el grafo del main
        //    if (tabla[j].getExtra() != 1) {
        //        recorrido_profundidadR(tabla[j].getNombre(), L);
        //    }
        //}
        return L;
    }

    private void limpiarDatos() {
        for (int i = 0; i < numVertices; i++) {
            tabla[i].setExtra(0);
            tabla[i].setAnt(-1);
            tabla[i].setDist(INFINITO);
        }
    }

    public void imprimirGrafo() {
        for (int h = 0; h < numVertices; h++) {
            System.out.print("\t" + tabla[h].getNombre());
        }
        System.out.println("");
        for (int i = 0; i < numVertices; i++) {
            System.out.print(tabla[i].getNombre());
            for (int j = 0; j < numVertices; j++) {
                System.out.print("\t" + matriz[i][j]);
            }
            System.out.println("");
        }
    }

//    public void imprimirGrafo2() {
//        String[][] matriz2 = new String[numVertices + 1][numVertices + 1];
//        matriz2[0][0] = "";
//        for (int i = 0; i < numVertices; i++) {
//            matriz2[0][i + 1] = tabla[i].getNombre();
//            matriz2[i + 1][0] = tabla[i].getNombre();
//        }
//        for (int i = 1; i < matriz2.length; i++) {
//            for (int j = 1; j < matriz2.length; j++) {
//                matriz2[i][j] = String.valueOf(matriz[i - 1][j - 1]);
//            }
//        }
//        for (int i = 0; i < matriz2.length; i++) {
//            for (int j = 0; j < matriz2.length; j++) {
//                System.out.print(matriz2[i][j] + "\t");
//            }
//            System.out.println("");
//        }
//        System.out.println("");
//    }
    @Override
    public void caminoMSinPesos(String VerticeOrigen) {
        int verticeOrig = buscar(VerticeOrigen);
        if (verticeOrig == -1) {
          //  System.out.println("El vertice " + VerticeOrigen + " no existe");
        } else {
            limpiarDatos();
            Queue<Integer> q = new LinkedList<>();
            tabla[verticeOrig].setDist(0);
            q.add(verticeOrig);
            while (!q.isEmpty()) {
                int actual = q.remove();
                for (int i = 0; i < numVertices; i++) {
                    if (matriz[actual][i] != 0 && tabla[i].getDist() == INFINITO) {
                        tabla[i].setDist(tabla[actual].getDist() + 1);
                        tabla[i].setAnt(actual);
                        q.add(i);
                    }
                }
            }
        }
    }

    @Override
    public boolean caminoMConPesosPositivos(String verticeOrig) {
    limpiarDatos(); // Inicializar todos los atributos

    int verticeOrigen = buscar(verticeOrig); // Buscar el vértice origen para saber si existe y su posición
    if (verticeOrigen == -1) { // Si el vértice origen no existe, devuelve falso
        return false;
    }

    PriorityQueue<Vertice> cp = new PriorityQueue<>(); // Crear una cola de prioridad para almacenar los vértices a visitar
    tabla[verticeOrigen].setDist(0); // Establecer la distancia del vértice origen a 0
    cp.add(new Vertice(verticeOrig, 0)); // Agregar el vértice origen a la cola de prioridad

    while (!cp.isEmpty()) { // Mientras la cola de prioridad no esté vacía
        Vertice ver = cp.remove(); // Eliminar el vértice de la cola de prioridad
        int v = buscar(ver.getNombre()); // Obtener la posición del vértice eliminado en la matriz de adyacencia

        if (tabla[v].getExtra() == 0) { // Si el vértice no ha sido visitado
            tabla[v].setExtra(1); // Marcar el vértice como visitado

            for (int w = 0; w < numVertices; w++) { // Iterar sobre los vértices adyacentes
                if (matriz[v][w] != 0) { // Si hay una arista entre los vértices v y w
                    float cvw = matriz[v][w]; // Obtener el costo de la arista

                    if (cvw < 0) { // Si el costo es negativo, retornar falso
                        return false;
                    }

                    if (tabla[w].getDist() > tabla[v].getDist() + cvw) { // Si la distancia en el vértice w es mayor que la distancia en el vértice v más el costo de la arista
                        tabla[w].setDist(tabla[v].getDist() + cvw); // Actualizar la distancia en el vértice w
                        tabla[w].setAnt(v); // Establecer el vértice anterior en el camino más corto

                        // Agregar el vértice w a la cola de prioridad con su nueva distancia
                        cp.add(new Vertice(tabla[w].getNombre(), tabla[w].getDist()));
                    }
                }
            }
        }
    }
    return true; // Devolver verdadero indicando que se completó el algoritmo correctamente
}


    //implementar
    
    @Override
    public boolean caminoMConPesosNegativos(String VerticeOrigen) {
        int verticeOrig = buscar(VerticeOrigen);
        if (verticeOrig == -1) {
             // El vértice de origen no existe
            return false;
        } 
        else {
             limpiarDatos();
             Queue<Integer> q = new LinkedList<>();
             tabla[verticeOrig].setDist(0);
             q.add(verticeOrig);
             tabla[verticeOrig].setExtra(tabla[verticeOrig].getExtra() + 1);

             while (!q.isEmpty()) {
                 int v = q.remove();
                 tabla[v].setExtra(tabla[v].getExtra() + 1);

                 if (tabla[v].getExtra() > 2 * numVertices) {
                     // Ciclo de costo negativo
                     System.out.println("Ciclo de costo negativo!");
                     return false;
                }

                 for (int w = 0; w < numVertices; w++) {
                     if (matriz[v][w] != 0) {
                         float cvw = matriz[v][w];
                         if (tabla[w].getDist() > tabla[v].getDist() + cvw) {
                             tabla[w].setDist(tabla[v].getDist() + cvw);
                             tabla[w].setAnt(v);
                             if (!q.contains(w)) {
                                 q.add(w);
                                 tabla[w].setExtra(tabla[w].getExtra() + 1);
                            } 
                            else{
                                 tabla[w].setExtra(tabla[w].getExtra() + 2);
                            }
                        }
                    }
                }
            }
        }
        
        return true;
     }


    @Override
    public LinkedList<String> ordenamientoTopologico() {

    int[] gradosEntrada = new int[numVertices];
    Queue<Integer> cola = new LinkedList<>();
    LinkedList<String> ordenTopologico = new LinkedList<>();

    // Calcular grados de entrada de todos los vértices
    for (int i = 0; i < numVertices; i++) {
        int gradoEntrada = 0;
        for (int j = 0; j < numVertices; j++) {
            if (matriz[j][i] != 0) { // Hay una arista de j a i
                gradoEntrada++;
            }
        }
        gradosEntrada[i] = gradoEntrada;
        if (gradoEntrada == 0) { // Si el grado de entrada es cero, agregar a la cola
            cola.add(i);
        }
    }

    // Mientras la cola no esté vacía
    while (!cola.isEmpty()) {
        int vertice = cola.poll(); // Eliminar el primer elemento de la cola (v)
        ordenTopologico.add(tabla[vertice].getNombre()); // Agregar el vértice a la lista de orden topológico

        // Para cada vértice adyacente a v
            for (int j = 0; j < numVertices; j++) {
                if (matriz[vertice][j] != 0) { // Si hay una arista de v a j
                    gradosEntrada[j]--; // Decrementar el grado de entrada de j
                    if (gradosEntrada[j] == 0) { // Si el grado de entrada de j es cero, agregar a la cola
                        cola.add(j);
                    }
                }
            }
        }

    // Si el tamaño de la lista de orden topológico es igual al número de vértices, retornar la lista
        if (ordenTopologico.size() == numVertices) {
            return ordenTopologico;
        } 

        else {
            return null; // No se pudo completar el ordenamiento topológico (hay un ciclo)
        }
    }


    @Override
    public boolean caminoMAciclico(String verticeO) {
        LinkedList<String> ordenTopologico = new LinkedList<>();
        limpiarDatos();
        int verticeOrig = buscar(verticeO);
        if (verticeOrig == -1) {
            // El vértice de origen no existe
            return false;
        }
        tabla[verticeOrig].setDist(0);
        ordenTopologico = ordenamientoTopologico();
        if (ordenTopologico == null) {
            // El grafo tiene ciclos
            return false;
        }
        while (!ordenTopologico.isEmpty()) {
            String v2 = ordenTopologico.remove(); // Elimina y devuelve el primer elemento de la lista
            int v = buscar(v2);
            for (int i = 0; i < numVertices; i++) {
                if (matriz[v][i] != 0) {
                    float cvw = matriz[v][i];
                    if (tabla[v].getDist() == INFINITO) {
                        continue; // Si el vértice actual v es inalcanzable desde el origen, no se calculan las distancias
                    }
                    if (tabla[i].getDist() > tabla[v].getDist() + cvw) {
                        tabla[i].setDist(tabla[v].getDist() + cvw);
                        tabla[i].setAnt(v); // Establecer el vértice anterior en el camino más corto
                    }
                }
            }
        }
        return true;
    }


    public void imprimirCamino(String verticeDestino) {//para imprimir el camino minimo desde el v�rtice origen hasta un v�rtice destino especificado
        int verticeDest = buscar(verticeDestino);
        if (verticeDest == -1) {
            System.out.println("El vertice " + verticeDestino + " no existe");
        }
        if (tabla[verticeDest].getDist() == INFINITO) {
            System.out.println(verticeDestino + " es inalcanzable");
        } else {
            imprimirCaminoRec(verticeDest);
           // System.out.println(" (el costo es " + tabla[verticeDest].getDist() + ")");
        }
//        System.out.println();
    }

    private void imprimirCaminoRec(int verticeDest) {
        if (tabla[verticeDest].getAnt() != -1 && tabla[verticeDest].getDist() != 0) {
            imprimirCaminoRec(tabla[verticeDest].getAnt());
            //System.out.print(" -> ");
        }
        System.out.print(tabla[verticeDest].getNombre()+" ");
    }
    
    public int getNumVertices() {
        return numVertices;
    }

    // Setter para numVertices
    public void setNumVertices(int numVertices) {
        this.numVertices = numVertices;
    }

    // Getter para la matriz de adyacencia
    public float[][] getMatriz() {
        return matriz;
    }

    // Setter para la matriz de adyacencia
    public void setMatriz(float[][] matriz) {
        this.matriz = matriz;
    }
    
    
    // Getter para la tabla de vértices
    public Vertice[] getTabla() {
        return tabla;
    }

    // Setter para la tabla de vértices
    public void setTabla(Vertice[] tabla) {
        this.tabla = tabla;
    }

    public float getINFINITO() {
    return INFINITO;
    }

    
}
