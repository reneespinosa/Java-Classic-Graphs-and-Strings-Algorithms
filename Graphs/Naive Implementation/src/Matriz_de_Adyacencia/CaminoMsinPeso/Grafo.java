package Matriz_de_Adyacencia.CaminoMsinPeso;


import Lista_de_Adyacencia.CaminosMaciclicos.*;
import java.util.LinkedList;

public interface Grafo {

    int insVertice(String nombre);

    void insArista(String origen, String destino, float costo);

    void insArista(String origen, String destino);

    void elimArista(String origen, String destino);

    void elimVertice(String nombre);

    //operaciones basicas
    int buscar(String nombre);

    boolean esAdyacente(String vertice1, String vertice2);

    //recorridos sobre grafos
    LinkedList<String> recorrido_amplitud(String VerticeOrigen);

    LinkedList<String> recorrido_profundidad(String VerticeOrig);

    LinkedList<String> ordenamientoTopologico();
    
    //caminos minimos
    void caminoMSinPesos(String VerticeOrigen);

    boolean caminoMConPesosPositivos(String VerticeOrigen);
    
    boolean caminoMConPesosNegativos(String VerticeOrigen);
    
    boolean caminoMAciclico(String verticeO);

}
