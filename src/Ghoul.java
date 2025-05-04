public class Ghoul extends Esbirros {
    //Atributos
    private int dependencia;

    //Constructor
    public Ghoul(String nombre, int dependencia) {
        super(nombre);
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
