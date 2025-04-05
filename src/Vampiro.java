import java.util.HashMap;
import java.util.List;

public class Vampiro extends Personaje{
    //Atributos
    private int puntosSangre;
    private String disciplinas;
    private int edad;

    //Constructor
    public Vampiro(int puntosSangre, String disciplinas, int edad, String nombre, String habilidad,
                      List<Armas> conjuntoArmas, List<Armadura> conjuntoArmaduras, Esbirros conjuntoEsbirros, int oro,
                      int salud, int poder, HashMap<String, String> fortalezas, HashMap<String, String> debilidades) {
        super(nombre, habilidad, conjuntoArmas, conjuntoArmaduras, conjuntoEsbirros, oro, salud, poder, fortalezas,
                debilidades);
        this.puntosSangre = puntosSangre;
        this.disciplinas = disciplinas;
        this.edad = edad;
    }

    //Métodos
    public int getPuntosSangre(){
        return puntosSangre;
    }

    public void setPuntosSangre(){
        this.puntosSangre = puntosSangre;
    }

    public String getDisciplinas(){
        return disciplinas;
    }

    public int getEdad(){
        return edad;
    }

    public void setEdad(int edad){
        this.edad = edad;
    }
}
