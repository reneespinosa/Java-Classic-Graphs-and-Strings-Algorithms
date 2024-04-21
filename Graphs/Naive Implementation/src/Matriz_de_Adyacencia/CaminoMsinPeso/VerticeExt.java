package Matriz_de_Adyacencia.CaminoMsinPeso;


import java.util.LinkedList;

public class VerticeExt extends Vertice {
    private LinkedList<Arista> lista;

    public VerticeExt(String nombre) {
        super(nombre);
        lista = new LinkedList<>();
    }

     public LinkedList<Arista> getLista() {
        return lista;
    }

    public void setLista(LinkedList<Arista> lista) {
        this.lista=lista;
    }

}
