package Lista_de_Adyacencia.DFS;


public class Vertice implements Comparable<Vertice> {

    private String nombre;
    private int extra;
    private int ant;
    private float dist;

    public Vertice(String nom) {
        nombre = nom;
        extra = 0;
        ant = -1;
        dist = Float.MAX_VALUE;
    }

    public Vertice() {
        dist = -1;
    }

    public Vertice(String nom, float distancia) {
        nombre = nom;
        extra = 0;
        ant = -1;
        dist = distancia;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getExtra() {
        return extra;
    }

    public void setExtra(int extra) {
        this.extra = extra;
    }

    public int getAnt() {
        return ant;
    }

    public void setAnt(int ant) {
        this.ant = ant;
    }

    public float getDist() {
        return dist;
    }

    public void setDist(float dist) {
        this.dist = dist;
    }

    @Override
    public int compareTo(Vertice otroVertice) {
        if (this.dist < otroVertice.dist) {
            return -1;
        } else if (this.dist > otroVertice.dist) {
            return 1;
        } else {
            return 0;
        }
    }
}
