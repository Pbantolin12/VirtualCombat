import java.util.HashMap;
import java.util.List;

public class Cazadores extends Personaje{
    //Atributos
    private int voluntad;
    private String talentos;

    //Constructor
    public Cazadores(int voluntad, String talentos, String nombre, String habilidad, List<Arma> conjuntoArmas,
                      List<Armadura> conjuntoArmaduras, Esbirros conjuntoEsbirros, int oro, int salud, int poder,
                      HashMap<String, String> fortalezas, HashMap<String, String> debilidades) {
        super(nombre, habilidad, conjuntoArmas, conjuntoArmaduras, conjuntoEsbirros, oro, salud, poder, fortalezas,
                debilidades);
        this.voluntad = voluntad;
        this.talentos = talentos;
    }

    //Métodos
    public int getVoluntad(){
        return voluntad;
    }

    public void setVoluntad(int voluntad){
        this.voluntad = voluntad;
    }

    public String getTalentos(){
        return talentos;
    }

    public void setTalentos(String talentos){
        this.talentos = talentos;
    }
}
