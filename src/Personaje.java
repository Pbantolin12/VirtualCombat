import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private Map<String, Integer> fortalezas;
    private Map<String, Integer> debilidades;
    private int potencialAtaque;
    private int potencialDefensa;
    private TerminalTexto terminalTexto = TerminalTexto.getInstance();
    
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
        this.debilidades = new HashMap<>();
        this.fortalezas = new HashMap<>();
        añadirDebilidad();
        añadirFortaleza();
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

    public Map<String, Integer> getFortalezas() {
        return fortalezas;
    }

    public void setFortaleza(String descripcion, int valor) {
        if (valor < 1 || valor > 5){
            terminalTexto.error("El valor de la fortaleza debe ser entre 1 y 5");
        } else {
            this.fortalezas.put(descripcion, valor);
        }
    }

    public Map<String, Integer> getDebilidades() {
        return debilidades;
    }

    public void setDebilidad(String descripcion, int valor) {
        if (valor < 1 || valor > 5){
            terminalTexto.error("El valor de la debilidad debe ser entre 1 y 5");
        } else {
            this.debilidades.put(descripcion, valor);
        }
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
        return conjuntoArmas.get(terminalTexto.readInt() - 1);
    }

    public Armadura elegirArmaduraActiva(){
        this.mostrarArmaduras();
        terminalTexto.askInfo("Elige la armadura activa: ");
        return conjuntoArmaduras.get(terminalTexto.readInt() - 1);
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
        ArrayList<Arma> conjuntoArmasIni = new ArrayList<>();
        conjuntoArmasIni.add(new Arma("Espada", 1, 1, TipoArma.UNA_MANO ));
        conjuntoArmasIni.add(new Arma("Hacha", 2, 2, TipoArma.DOS_MANOS));
        conjuntoArmasIni.add(new Arma("Lanza", 0, 3, TipoArma.DOS_MANOS));
        conjuntoArmasIni.add(new Arma("Daga", 1, 2, TipoArma.UNA_MANO));
        conjuntoArmasIni.add(new Arma("Maza",  0, 3, TipoArma.DOS_MANOS));
        conjuntoArmasIni.add(new Arma("Espada larga", 0, 3, TipoArma.DOS_MANOS));
        return conjuntoArmasIni;
    }

    private ArrayList<Armadura> conjuntoArmadurasInicial(){
        ArrayList<Armadura> conjuntoArmadurasIni = new ArrayList<>();
        conjuntoArmadurasIni.add(new Armadura("Cota de malla", 1, 2));
        conjuntoArmadurasIni.add(new Armadura("Cuero", 2, 1));
        conjuntoArmadurasIni.add(new Armadura("Piel", 3, 1));
        conjuntoArmadurasIni.add(new Armadura("Metal", 0, 3));
        return conjuntoArmadurasIni;
    }

    private void añadirDebilidad(){
        terminalTexto.askInfo("Introduce la descripción de la debilidad: ");
        String descripcionDebilidad = terminalTexto.readStr();
        int valorDebilidad = 0;
        while (valorDebilidad > 5 || valorDebilidad < 1){
            terminalTexto.askInfo("Introduce el valor de la debilidad (De 1 a 5): ");
            valorDebilidad = terminalTexto.readInt();
            if (valorDebilidad > 5 || valorDebilidad < 1){
                terminalTexto.error("El valor debe estar entre 1 y 5");
            }
        }
        this.setDebilidad(descripcionDebilidad, valorDebilidad);
    }

    private void añadirFortaleza(){
        terminalTexto.askInfo("Introduce la descripción de la fortaleza: ");
        String descripcionFortaleza = terminalTexto.readStr();
        int valorFortaleza = 0;
        while (valorFortaleza > 5 || valorFortaleza < 1){
            terminalTexto.askInfo("Introduce el valor de la debilidad (De 1 a 5): ");
            valorFortaleza = terminalTexto.readInt();
            if (valorFortaleza > 5 || valorFortaleza < 1){
                terminalTexto.error("El valor debe estar entre 1 y 5");
            }
        }
        this.setFortaleza(descripcionFortaleza, valorFortaleza);
    }

    public void modificarEsbirros(){} //TODO: Implementar

    public void modificarDebilidades(){
        int opt;
        do {
            opt = menuDebilidades();
            switch (opt){
                case 1 -> this.añadirDebilidad();
                case 2 -> this.eliminarDebilidad();
                case 3 -> this.mostrarDebilidades();
                default -> terminalTexto.error("Opción incorrecta");
            }
        } while (opt != 4);
    }

    private void eliminarDebilidad(){
        this.mostrarDebilidades();
        terminalTexto.askInfo("Introduce el nombre de la debilidad a eliminar: ");
        String delDebilidad = terminalTexto.readStr();
        if (this.debilidades.containsKey(delDebilidad)){
            this.debilidades.remove(delDebilidad);
            terminalTexto.info("Debilidad [" + delDebilidad + "] eliminada");
        } else {
            terminalTexto.error("La debilidad no existe");
        }
    }

    private void mostrarDebilidades(){
        terminalTexto.showln("|--------Debilidades-------|");
        this.getDebilidades().forEach((descripcion, valor) -> terminalTexto.showln("| - " +
                descripcion + ": " + valor));
    }

    private int menuDebilidades(){
        terminalTexto.showln(" ___________________________");
        terminalTexto.showln("|____Menú de debilidades____|");
        terminalTexto.showln("| 1. Añadir debilidad       |");
        terminalTexto.showln("| 2. Eliminar debilidad     |");
        terminalTexto.showln("| 3. Mostrar debilidades    |");
        terminalTexto.showln("| 4. Confirmar              |");
        terminalTexto.showln("|---------------------------|");
        terminalTexto.askInfo("Elige una opción: ");
        return terminalTexto.readInt();
    }

    public void modificarFortalezas(){
        int opt;
        do {
            opt = menuFortalezas();
            switch (opt){
                case 1 -> this.añadirFortaleza();
                case 2 -> this.eliminarFortaleza();
                case 3 -> this.mostrarFortalezas();
                default -> terminalTexto.error("Opción incorrecta");
            }
        } while (opt != 4);
    }

    private void eliminarFortaleza(){
        this.mostrarFortalezas();
        terminalTexto.askInfo("Introduce el nombre de la fortaleza a eliminar: ");
        String delFortaleza = terminalTexto.readStr();
        if (this.fortalezas.containsKey(delFortaleza)){
            this.fortalezas.remove(delFortaleza);
            terminalTexto.info("Fortaleza [" + delFortaleza + "] eliminada");
        } else {
            terminalTexto.error("La fortaleza no existe");
        }
    }

    private void mostrarFortalezas(){
        terminalTexto.showln("|--------Fortalezas--------|");
        this.getFortalezas().forEach((descripcion, valor) -> terminalTexto.showln("| - " +
                descripcion + ": " + valor));
    }

    private int menuFortalezas(){
        terminalTexto.showln(" ___________________________");
        terminalTexto.showln("|____Menú de fortalezas_____|");
        terminalTexto.showln("| 1. Añadir fortaleza      |");
        terminalTexto.showln("| 2. Eliminar fortaleza    |");
        terminalTexto.showln("| 3. Mostrar fortalezas     |");
        terminalTexto.showln("| 4. Confirmar             |");
        terminalTexto.showln("|---------------------------|");
        terminalTexto.askInfo("Elige una opción: ");
        return terminalTexto.readInt();
    }
}
