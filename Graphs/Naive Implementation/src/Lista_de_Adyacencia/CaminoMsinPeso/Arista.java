package Lista_de_Adyacencia.CaminoMsinPeso;


public class Arista {
    private int destino;
    private float costo;

    public Arista(int d, float c) {
        destino = d;
        costo = c;
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
