import java.util.HashMap;
import java.util.List;

public class Licantropo extends Personaje{
    //Atributos
    private int rabia;

    //Constructor
    public Licantropo(String nombre) {
        super(nombre);
        this.setRabia(0);
        this.setHabilidad("Dones");
    }

    //Métodos
    public int getRabia() {
        return rabia;
    }

    public void setRabia(int rabia) {
        if (rabia < 0){
            this.rabia = 0;
        } else if (rabia > 3){
            this.rabia = 3;
        } else {
            this.rabia = rabia;
        }
    }

}
