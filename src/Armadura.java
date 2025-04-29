public class Armadura extends Equipo{
    //Atributos
    private int modificadorAtaque;
    private int modificadorDefensa;
    private String nombre;

    //Constructor
    public Armadura(String nombre, int modificadorAtaque, int modificadorDefensa) {
        this.nombre = nombre;
        this.modificadorAtaque = modificadorAtaque;
        this.modificadorDefensa = modificadorDefensa;
    }

    //Métodos
    public int getModificadorAtaque() {
        return modificadorAtaque;
    }

    public void setModificadorAtaque(int modificadorAtaque) {
        this.modificadorAtaque = modificadorAtaque;
    }

    public int getModificadorDefensa() {
        return modificadorDefensa;
    }

    public void setModificadorDefensa(int modificadorDefensa) {
        this.modificadorDefensa = modificadorDefensa;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
