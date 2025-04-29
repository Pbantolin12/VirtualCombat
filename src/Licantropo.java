import java.util.HashMap;
import java.util.List;

public class Licantropo extends Personaje{
    //Atributos
    private int rabia;
    private String dones;

    //Constructor
    public Licantropo(String nombre) {
        super(nombre);
        this.rabia = rabia;
        this.dones = dones;
    }


    //Métodos
    public int getRabia() {
        return rabia;
    }
    public void setRabia(int rabia) {
        this.rabia = rabia;
    }
    public String getDones() {
        return dones;
    }
    public void setDones(String dones) {
        this.dones = dones;
    }
}
