import java.io.Serializable;

public class Esbirros implements Serializable {
    //Atributos
    private String nombre;
    private int salud;
    private int ataque;

    //Constructor
    public Esbirros(String nombre) {
        this.nombre = nombre;
        this.salud = 3;
    }

    //Métodos
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getSalud() {
        return salud;
    }

    public void setSalud(int salud) {
        this.salud = salud;
    }

    public int getAtaque() {
        return ataque;
    }

    public void setAtaque(int ataque) {
        this.ataque = ataque;
    }
}
