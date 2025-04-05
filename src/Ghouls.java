public class Ghouls extends Esbirros{
    //Atributos
    private int dependencia;
    
    //Constructor
    public Ghouls(int dependencia, String nombre, int salud, int ataque, int defensa) {
        super(nombre, salud, ataque, defensa);
        this.dependencia = dependencia;
    }

    //Métodos
    public int getDependencia() {
        return dependencia;
    }

    public void setDependencia(int dependencia) {
        this.dependencia = dependencia;
    }
}
