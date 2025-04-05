import java.util.HashMap;
import java.util.List;

public class Licantropo extends Personaje{
    //Atributos
    private int rabia;
    private String dones;

    //Constructor
    public Licantropo(int rabia, String dones, String nombre, String habilidad, List<Armas> conjuntoArmas,
                      List<Armadura> conjuntoArmaduras, Esbirros conjuntoEsbirros, int oro, int salud, int poder,
                      HashMap<String, String> fortalezas, HashMap<String, String> debilidades) {
        super(nombre, habilidad, conjuntoArmas, conjuntoArmaduras, conjuntoEsbirros, oro, salud, poder, fortalezas,
                debilidades);
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
