import java.util.HashMap;
import java.util.List;

public class Personaje {
    //Atributos
    private String nombre;
    private String habilidad;
    private List<Arma> conjuntoArmas;
    private List<Armadura> conjuntoArmaduras;
    private Arma armaActiva;
    private Armadura armaduraActiva;
    private Esbirros conjuntoEsbirros;
    private int oro;
    private int salud;
    private int poder;
    private HashMap<String, String> fortalezas;
    private HashMap<String, String> debilidades;
    private int potencialAtaque;
    private int potencialDefensa;
    
    //Constructor
    public Personaje(String nombre, String habilidad, List<Arma> conjuntoArmas, List<Armadura> conjuntoArmaduras,
                     Esbirros conjuntoEsbirros, int oro, int salud, int poder, HashMap<String, String> fortalezas,
                     HashMap<String, String> debilidades){
        this.nombre = nombre;
        this.habilidad = habilidad;
        this.conjuntoArmas = conjuntoArmas;
        this.conjuntoArmaduras = conjuntoArmaduras;
        this.conjuntoEsbirros = conjuntoEsbirros;
        this.oro = oro;
        this.salud = salud;
        this.poder = poder;
        this.fortalezas = fortalezas;
        this.debilidades = debilidades;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getHabilidad() {
        return habilidad;
    }

    public void setHabilidad(String habilidad) {
        this.habilidad = habilidad;
    }

    public List<Arma> getConjuntoArmas() {
        return conjuntoArmas;
    }

    public void setConjuntoArmas(List<Arma> conjuntoArmas) {
        this.conjuntoArmas = conjuntoArmas;
    }

    public List<Armadura> getConjuntoArmaduras() {
        return conjuntoArmaduras;
    }

    public void setConjuntoArmaduras(List<Armadura> conjuntoArmaduras) {
        this.conjuntoArmaduras = conjuntoArmaduras;
    }

    public Arma getArmaActiva() {
        return armaActiva;
    }

    public void setArmaActiva(Arma armaActiva) {
        this.armaActiva = armaActiva;
    }

    public Armadura getArmaduraActiva() {
        return armaduraActiva;
    }

    public void setArmaduraActiva(Armadura armaduraActiva) {
        this.armaduraActiva = armaduraActiva;
    }

    public Esbirros getConjuntoEsbirros() {
        return conjuntoEsbirros;
    }

    public void setConjuntoEsbirros(Esbirros conjuntoEsbirros) {
        this.conjuntoEsbirros = conjuntoEsbirros;
    }

    public int getOro() {
        return oro;
    }

    public void setOro(int oro) {
        this.oro = oro;
    }

    public int getSalud() {
        return salud;
    }

    public void setSalud(int salud) {
        this.salud = salud;
    }

    public int getPoder() {
        return poder;
    }

    public void setPoder(int poder) {
        this.poder = poder;
    }

    public HashMap<String, String> getFortalezas() {
        return fortalezas;
    }

    public void setFortalezas(HashMap<String, String> fortalezas) {
        this.fortalezas = fortalezas;
    }

    public HashMap<String, String> getDebilidades() {
        return debilidades;
    }

    public void setDebilidades(HashMap<String, String> debilidades) {
        this.debilidades = debilidades;
    }

    public int getPotencialAtaque() {
        return potencialAtaque;
    }

    public void setPotencialAtaque(int potencialAtaque) {
        this.potencialAtaque = potencialAtaque;
    }

    public int getPotencialDefensa() {
        return potencialDefensa;
    }

    public void setPotencialDefensa(int potencialDefensa) {
        this.potencialDefensa = potencialDefensa;
    }
}
