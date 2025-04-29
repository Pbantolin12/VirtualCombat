import java.util.ArrayList;
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
    private TerminalTexto terminalTexto= TerminalTexto.getInstance();
    
    //Constructor
    public Personaje(String nombre){
        this.nombre = nombre;
        this.setConjuntoArmas(this.conjuntoArmasInicial());
        this.setConjuntoArmaduras(this.conjuntoArmadurasInicial());
        this.elegirArmaActiva();
        this.elegirArmaduraActiva();
        this.oro = 1000;
        this.salud = 5;
        this.poder = 2;
        inicializarDebilidades();
        inicializarFortalezas();
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

    public void setFortaleza(String fortaleza, String descripcion) {
        this.fortalezas.put(fortaleza, descripcion);
    }

    public HashMap<String, String> getDebilidades() {
        return debilidades;
    }

    public void setDebilidades(HashMap<String, String> debilidades) {
        this.debilidades = debilidades;
    }

    public void setDebilidad(String debilidad, String descripcion) {
        this.debilidades.put(debilidad, descripcion);
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

    public Arma elegirArmaActiva(){
        this.mostrarArmas();
        terminalTexto.askInfo("Elige el arma activa: ");
        return conjuntoArmas.get(terminalTexto.readInt()-1);
    }

    public Armadura elegirArmaduraActiva(){
        this.mostrarArmaduras();
        terminalTexto.askInfo("Elige la armadura activa: ");
        return conjuntoArmaduras.get(terminalTexto.readInt()-1);
    }

    public int mostrarArmas(){
        int opt = 0;
        while (opt < 1 || opt > conjuntoArmas.size()){
            terminalTexto.show("|---Armas disponibles---|");
            int j= 0;
            for (int i = 0; i < conjuntoArmas.size(); i++) {
                terminalTexto.show(++j + ". " + conjuntoArmas.get(i).getNombre());
            }
            terminalTexto.show("-------------------------");
            terminalTexto.askInfo("Elige un arma: ");
            opt = terminalTexto.readInt();
            if (opt < 1 || opt > conjuntoArmaduras.size()){
                terminalTexto.error("Opción incorrecta");
            }
        }
        return opt;
    }

    public int mostrarArmaduras(){
        int opt = 0;
        while (opt < 1 || opt > conjuntoArmaduras.size()){
            terminalTexto.show("|---Armaduras disponibles---|");
            int j= 0;
            for (int i = 0; i < conjuntoArmaduras.size(); i++) {
                terminalTexto.show(++j + ". " + conjuntoArmaduras.get(i).getNombre());
            }
            terminalTexto.show("-------------------------------");
            terminalTexto.askInfo("Elige una armadura: ");
            opt = terminalTexto.readInt();
            if (opt < 1 || opt > conjuntoArmaduras.size()){
                terminalTexto.error("Opción incorrecta");
            }
        }
        return opt;
    }

    private ArrayList<Arma> conjuntoArmasInicial(){
        ArrayList<Arma> conjuntoArmasIni = new ArrayList<Arma>();
        conjuntoArmasIni.add(new Arma("Espada",TipoArma.UNA_MANO, 1, 1 ));
        conjuntoArmasIni.add(new Arma("Hacha", TipoArma.DOS_MANOS, 2, 2));
        conjuntoArmasIni.add(new Arma("Lanza", TipoArma.DOS_MANOS, 0, 3));
        conjuntoArmasIni.add(new Arma("Daga", TipoArma.UNA_MANO, 1, 2));
        conjuntoArmasIni.add(new Arma("Maza", TipoArma.DOS_MANOS, 0, 3));
        conjuntoArmasIni.add(new Arma("Espada larga", TipoArma.DOS_MANOS, 0, 3));
        return conjuntoArmasIni;
    }

    private ArrayList<Armadura> conjuntoArmadurasInicial(){
        ArrayList<Armadura> conjuntoArmadurasIni = new ArrayList<Armadura>();
        conjuntoArmadurasIni.add(new Armadura("Cota de malla", 1, 2));
        conjuntoArmadurasIni.add(new Armadura("Cuero", 2, 1));
        conjuntoArmadurasIni.add(new Armadura("Piel", 3, 1));
        conjuntoArmadurasIni.add(new Armadura("Metal", 0, 3));
        return conjuntoArmadurasIni;
    }

    private void inicializarDebilidades(){
        this.debilidades = new HashMap<String, String>();
        terminalTexto.askInfo("Introduce el número de debilidades que tendrá del personaje: ");
        int numDebilidades = terminalTexto.readInt();
        int j = 0;
        for (int i = 0; i < numDebilidades; i++) {
            terminalTexto.askInfo("Introduce el nombre de la debilidad " + ++j + ": ");
            String nombreDebilidad = terminalTexto.readStr();
            terminalTexto.askInfo("Introduce la descripción de la debilidad: ");
            String descripcionDebilidad = terminalTexto.readStr();
            this.setDebilidad(nombreDebilidad, descripcionDebilidad);
        }
    }

    private void inicializarFortalezas(){
        this.fortalezas = new HashMap<String, String>();
        terminalTexto.askInfo("Introduce el número de fortalezas que tendrá del personaje: ");
        int numFortalezas = terminalTexto.readInt();
        int j = 0;
        for (int i = 0; i < numFortalezas; i++) {
            terminalTexto.askInfo("Introduce el nombre de la fortaleza " + ++j + ": ");
            String nombreFortaleza = terminalTexto.readStr();
            terminalTexto.askInfo("Introduce la descripción de la fortaleza: ");
            String descripcionFortaleza = terminalTexto.readStr();
            this.setFortaleza(nombreFortaleza, descripcionFortaleza);
        }
    }
}
