public class Esbirros {
    //Atributos
    private String nombre;
    private int salud;
    private int ataque;
    private int defensa;

    //Constructor
    public Esbirros(String nombre){
        this.nombre = nombre;
        this.salud = 3;
        this.ataque = ataque;
        this.defensa = defensa;
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

    public int getDefensa() {
        return defensa;
    }

    public void setDefensa(int defensa) {
        this.defensa = defensa;
    }
}
