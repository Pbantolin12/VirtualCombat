import java.io.Serializable;

public class Equipo implements Serializable {
    //Atributos
    private String nombre;
    private int modificadorAtaque;
    private int modificadorDefensa;

    //Constructor
    public Equipo(String nombre, int modificadorDefensa, int modificadorAtaque) {
        this.nombre = nombre;
        this.modificadorAtaque = modificadorDefensa;
        this.modificadorDefensa = modificadorAtaque;
    }

    //Métodos
    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getModificadorDefensa() {
        return modificadorDefensa;
    }

    public void setModificadorDefensa(int modificadorDefensa) {
        this.modificadorDefensa = modificadorDefensa;
    }

    public int getModificadorAtaque() {
        return modificadorAtaque;
    }

    public void setModificadorAtaque(int modificadorAtaque) {
        this.modificadorAtaque = modificadorAtaque;
    }
}
