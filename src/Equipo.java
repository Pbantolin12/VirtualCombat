public class Equipo {
    //Atributos
    private String nombre;
    private int modificadorAtaque;
    private int modificadorDefensa;

    //Constructor
    public Equipo(){}

    //Métodos
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

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
}
