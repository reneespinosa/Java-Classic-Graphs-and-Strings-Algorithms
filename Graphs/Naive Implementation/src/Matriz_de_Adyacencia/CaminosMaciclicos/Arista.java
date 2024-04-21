package Matriz_de_Adyacencia.CaminosMaciclicos;



public class Arista {
    private int destino;
    private float costo;

    public Arista(int d, float c) {
        this.destino = d;
        this.costo = c;
    }

    public int getDestino() {
        return destino;
    }

    public void setDestino(int destino) {
        this.destino = destino;
    }

    public float getCosto() {
        return costo;
    }

    public void setCosto(float costo) {
        this.costo = costo;
    }
}
