import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Personaje implements Serializable {
    //Atributos
    private String nombre;
    private int habilidad;
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
    private Map<String, Boolean> debilidadesActivas;
    private Map<String, Boolean> fortalezasActivas;
    private int potencialAtaque;
    private int potencialDefensa;
    private transient TerminalTexto terminalTexto = TerminalTexto.getInstance();
    private int potencialFortalezas;
    private int potencialDebilidades;
    
    //Constructor
    public Personaje(String nombre){
        this.nombre = nombre;
        this.setConjuntoArmas(this.conjuntoArmasInicial());
        this.setConjuntoArmaduras(this.conjuntoArmadurasInicial());
        this.armaActiva = this.elegirArmaActiva();
        this.armaduraActiva = this.elegirArmaduraActiva();
        this.oro = 1000;
        this.salud = 5;
        this.poder = 2;
        this.debilidades = new HashMap<>();
        this.fortalezas = new HashMap<>();
        this.debilidadesActivas = new HashMap<>();
        this.fortalezasActivas = new HashMap<>();
        anadirDebilidad();
        anadirFortaleza();
        anadirEsbirros();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getHabilidad() {
        return habilidad;
    }

    public void setHabilidad(int habilidad) {
        this.habilidad = habilidad;
    }

    public List<Arma> getConjuntoArmas() {
        return conjuntoArmas;
    }

    public void setConjuntoArmas(List<Arma> conjuntoArmas) {
        this.conjuntoArmas = conjuntoArmas;
    }

    public void setArma(Arma arma){
        this.conjuntoArmas.add(arma);
    }
    public Arma getArma(String name){
        for (Arma arma : conjuntoArmas) {
            if (arma.getNombre().equals(name)){
                return arma;
            }
        }
        return null;
    }

    public List<Armadura> getConjuntoArmaduras() {
        return conjuntoArmaduras;
    }

    public void setArmadura(Armadura armadura){
        this.conjuntoArmaduras.add(armadura);
    }

    public Armadura getArmadura(String name){
        for (Armadura armadura : conjuntoArmaduras) {
            if (armadura.getNombre().equals(name)){
                return armadura;
            }
        }
        return null;
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
        return conjuntoArmas.get(this.mostrarArmas() - 1);
    }

    public Armadura elegirArmaduraActiva(){
        return conjuntoArmaduras.get(this.mostrarArmaduras() - 1);
    }

    public int mostrarArmas(){
        int opt = 0;
        while (opt < 1 || opt > conjuntoArmas.size() + 1){
            terminalTexto.showln("|---Armas disponibles---|");
            int j= 0;
            for (int i = 0; i < conjuntoArmas.size(); i++) {
                terminalTexto.showln(++j + ". " + conjuntoArmas.get(i).getNombre());
            }
            terminalTexto.showln("-------------------------");
            terminalTexto.askInfo("Elige un arma: ");
            opt = terminalTexto.readInt();
            if (opt < 1 || opt > conjuntoArmas.size() + 1){
                terminalTexto.error("Opción incorrecta");
            }
        }
        return opt;
    }

    public int mostrarArmaduras(){
        int opt = 0;
        while (opt < 1 || opt > conjuntoArmaduras.size() + 1){
            terminalTexto.showln("|---Armaduras disponibles---|");
            int j= 0;
            for (int i = 0; i < conjuntoArmaduras.size(); i++) {
                terminalTexto.showln(++j + ". " + conjuntoArmaduras.get(i).getNombre());
            }
            terminalTexto.showln("-------------------------------");
            terminalTexto.askInfo("Elige una armadura: ");
            opt = terminalTexto.readInt();
            if (opt < 1 || opt > conjuntoArmaduras.size() + 1){
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

    private void anadirDebilidad(){
        String descripcionDebilidad;
        do {
            terminalTexto.askInfo("Introduce la descripción de la debilidad: ");
            descripcionDebilidad = terminalTexto.readStr();
        } while (descripcionDebilidad.isEmpty());
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

    private void anadirFortaleza(){
        String descripcionFortaleza;
        do {
            terminalTexto.askInfo("Introduce la descripción de la fortaleza: ");
            descripcionFortaleza = terminalTexto.readStr();
        } while (descripcionFortaleza.isEmpty());
        int valorFortaleza = 0;
        while (valorFortaleza > 5 || valorFortaleza < 1){
            terminalTexto.askInfo("Introduce el valor de la fortaleza (De 1 a 5): ");
            valorFortaleza = terminalTexto.readInt();
            if (valorFortaleza > 5 || valorFortaleza < 1){
                terminalTexto.error("El valor debe estar entre 1 y 5");
            }
        }
        this.setFortaleza(descripcionFortaleza, valorFortaleza);
    }

    public void modificarEsbirros() {
        int opt;
        do {
            opt = this.menuModificarEsbirros();
            switch (opt) {
                case 1 -> {
                    if (this.getConjuntoEsbirros() != null) {
                        terminalTexto.error("El personaje ya tiene esbirros");
                    } else {
                        this.anadirEsbirros();
                    }
                }
                case 2 -> this.eliminarEsbirro();
                case 3 -> this.mostrarEsbirros();
                default -> terminalTexto.error("Opción incorrecta");
            }
        } while (opt != 4);
    }

    private int menuModificarEsbirros(){
        terminalTexto.showln(" __________________________");
        terminalTexto.showln("|____Menú de esbirros______|");
        terminalTexto.showln("| 1. Añadir esbirro        |");
        terminalTexto.showln("| 2. Eliminar esbirro      |");
        terminalTexto.showln("| 3. Mostrar esbirros      |");
        terminalTexto.showln("| 4. Confirmar             |");
        terminalTexto.showln("|--------------------------|");
        terminalTexto.askInfo("Elige una opción: ");
        return terminalTexto.readInt();
    }

    private void eliminarEsbirro(){
        if (this.conjuntoEsbirros != null){
            terminalTexto.showln("Esbirro: [" + this.conjuntoEsbirros.getNombre() + "] eliminado");
            if(this.conjuntoEsbirros instanceof Demonio){
                if (((Demonio) this.conjuntoEsbirros).getConjuntoEsbirros() != null){
                    ((Demonio) this.conjuntoEsbirros).setConjuntoEsbirros(null);
                }
            }
            this.conjuntoEsbirros = null;
        } else {
            terminalTexto.error("El personaje no tiene esbirros");
        }
    }

    public void mostrarEsbirros(){
        if (this.conjuntoEsbirros != null){
            terminalTexto.showln("Nombre esbirro: " + this.conjuntoEsbirros.getNombre());
            terminalTexto.showln("Tipo esbirro: " + this.conjuntoEsbirros.getClass().getSimpleName());
        } else {
            terminalTexto.error("El personaje no tiene esbirros");
        }
    }

    public void modificarDebilidades(){
        int opt;
        do {
            opt = menuDebilidades();
            switch (opt){
                case 1 -> this.anadirDebilidad();
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
                case 1 -> this.anadirFortaleza();
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
        terminalTexto.showln("| 1. Añadir fortaleza       |");
        terminalTexto.showln("| 2. Eliminar fortaleza     |");
        terminalTexto.showln("| 3. Mostrar fortalezas     |");
        terminalTexto.showln("| 4. Confirmar              |");
        terminalTexto.showln("|---------------------------|");
        terminalTexto.askInfo("Elige una opción: ");
        return terminalTexto.readInt();
    }

    public void setdebilidadesActivas(){
        int opt;
        do {
            int i = 1;
            terminalTexto.showln("|--------Seleccionar debilidades " + this.getNombre() + "-------|");
            for (Map.Entry<String, Integer> debilidad : this.debilidades.entrySet()) {
                String status = debilidadesActivas.containsKey(debilidad.getKey()) ? "[activa]" : "[inactiva]";
                terminalTexto.showln(i++ + ". " +  debilidad.getKey() + " - Valor: " + debilidad.getValue() + " --> " + status);
            }
            opt = menuActivarDesactivarDebFort("debilidad");
            if (opt >= 1 && opt <= this.debilidades.size()){
                String debilidad = this.debilidades.keySet().toArray()[opt - 1].toString();
                if (this.debilidadesActivas.containsKey(debilidad)){
                    this.debilidadesActivas.remove(debilidad);
                    terminalTexto.info("Debilidad [" + debilidad + "] desactivada");
                } else {
                    this.debilidadesActivas.put(debilidad, true);
                    terminalTexto.info("Debilidad [" + debilidad + "] activada");
                }
            } else if (opt != 0){
                terminalTexto.error("Opción incorrecta");
            }
        } while (opt != 0);
    }

    public void setfortalezasActivas(){
        int opt;
        do {
            int i = 1;
            terminalTexto.showln("|--------Seleccionar fortalezas " + this.getNombre() + "-------|");
            for (Map.Entry<String, Integer> fortaleza : this.fortalezas.entrySet()) {
                String status = fortalezasActivas.containsKey(fortaleza.getKey()) ? "[activa]" : "[inactiva]";
                terminalTexto.showln(i++ + ". " +  fortaleza.getKey() + " - Valor: " + fortaleza.getValue() + " --> " + status);
            }
            opt = menuActivarDesactivarDebFort("fortaleza");
            if (opt >= 1 && opt <= this.fortalezas.size()){
                String fortaleza = this.fortalezas.keySet().toArray()[opt - 1].toString();
                if (this.fortalezasActivas.containsKey(fortaleza)){
                    this.fortalezasActivas.remove(fortaleza);
                    terminalTexto.info("Fortaleza [" + fortaleza + "] desactivada");
                } else {
                    this.fortalezasActivas.put(fortaleza, true);
                    terminalTexto.info("Fortaleza [" + fortaleza + "] activada");
                }
            } else if (opt != 0){
                terminalTexto.error("Opción incorrecta");
            }
        } while (opt != 0);
    }

    private int menuActivarDesactivarDebFort(String tipo){
        terminalTexto.showln("Opciones");
        terminalTexto.showln("- Introduce el número de una " + tipo + " debilidad para activarla/desactivarla");
        terminalTexto.showln("- Introduce 0 para confirmar");
        terminalTexto.askInfo("Elige una opción: ");
        return terminalTexto.readInt();
    }

    public void anadirEsbirros(){
        int opt;
        do {
            opt = menuEsbirros();
            if (opt == 4 && this.conjuntoEsbirros == null){
                terminalTexto.error("No has añadido ningun esbirro");
            }
            switch (opt){
                case 1 -> {
                    do {
                        terminalTexto.askInfo("Introduce el nombre del esbirro: ");
                        String nombre = terminalTexto.readStr();
                        if (nombre.isEmpty()){
                            terminalTexto.error("El nombre no puede ser vacío");
                        }
                    } while (nombre.isEmpty());
                    int dependencia;
                    do {
                        terminalTexto.askInfo("Introduce la dependencia del esbirro (entre 1 y 5): ");
                        dependencia = terminalTexto.readInt();
                        if (dependencia < 1 || dependencia > 5){
                            terminalTexto.error("Valor incorrecto");
                        }
                    } while (opt < 1 || opt > 3);
                    this.conjuntoEsbirros = new Ghoul(nombre, dependencia);
                }
                case 2 -> {
                    do {
                        terminalTexto.askInfo("Introduce el nombre del esbirro: ");
                        String nombre = terminalTexto.readStr();
                        if (nombre.isEmpty()){
                            terminalTexto.error("El nombre no puede estar vacío");
                        }
                    } while (nombre.isEmpty());
                    String pacto;
                    do {
                        terminalTexto.askInfo("Introduce la descripción el pacto del demonio con el amo: ");
                        pacto = terminalTexto.readStr();
                        if (pacto.isEmpty()){
                            terminalTexto.error("El pacto no puede estar vacío");
                        }
                    } while (pacto.isEmpty());
                    this.conjuntoEsbirros = new Demonio(nombre, pacto);
                }
                case 3 -> {
                    if (this instanceof Vampiro){
                        terminalTexto.error("Los vampiros no pueden tener esbirros humanos");
                    } else {
                        String nombre;
                        do {
                            terminalTexto.askInfo("Introduce el nombre del esbirro: ");
                            nombre = terminalTexto.readStr();
                            if (nombre.isEmpty()){
                                terminalTexto.error("El nombre no puede estar vacío");
                            }
                        } while (nombre.isEmpty());
                        for (Grado grado : Grado.values()) {
                            terminalTexto.showln(grado.toString());
                        }
                        Grado gradoSeleccionado = null;
                        Boolean gradoAux = false;
                        do {
                            terminalTexto.askInfo("Introduce la lealtad del esbirro: ");
                            String grado = terminalTexto.readStr().toUpperCase();
                            if (grado.isEmpty()){
                                terminalTexto.error("El grado no puede estar vacío");
                            } else{
                                for (Grado g : Grado.values()) {
                                    if (g.name().equals(grado)){
                                        gradoAux = true;
                                        gradoSeleccionado = g;
                                        break;
                                    }
                                }
                                if (Boolean.FALSE.equals(gradoAux)){
                                    terminalTexto.error("El grado introducido no es correcto");
                                }
                            }
                        } while (Boolean.FALSE.equals(gradoAux));
                        this.conjuntoEsbirros = new Humano(nombre, gradoSeleccionado);
                    }
                }
                default -> terminalTexto.error("Opción incorrecta");
            }
        } while (this.conjuntoEsbirros == null);
    }

    private int menuEsbirros(){
        terminalTexto.showln(" __________________________");
        terminalTexto.showln("|____Escoger esbirros______|");
        terminalTexto.showln("| 1. Ghoul                 |");
        terminalTexto.showln("| 2. Demonio               |");
        terminalTexto.showln("| 3. Humano                |");
        terminalTexto.showln("|--------------------------|");
        terminalTexto.askInfo("Elige una opción: ");
        return terminalTexto.readInt();
    }

    public int getPotencialFortalezas() {
        return potencialFortalezas;
    }

    public void setPotencialFortalezas(int potencialFortalezas) {
        this.potencialFortalezas = potencialFortalezas;
    }

    private void calcularPotencialFortalezas(){
        this.potencialFortalezas = 0;
        for (Map.Entry<String, Integer> fortaleza : this.fortalezas.entrySet()) {
            if (this.fortalezasActivas.containsKey(fortaleza.getKey())){
                this.potencialFortalezas += fortaleza.getValue();
            }
        }
    }

    public int getPotencialDebilidades() {
        return potencialDebilidades;
    }

    public void setPotencialDebilidades(int potencialDebilidades) {
        this.potencialDebilidades = potencialDebilidades;
    }

    private void calcularPotencialDebilidades(){
        this.potencialDebilidades = 0;
        for (Map.Entry<String, Integer> debilidad : this.debilidades.entrySet()) {
            if (this.debilidadesActivas.containsKey(debilidad.getKey())){
                this.potencialDebilidades += debilidad.getValue();
            }
        }
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        this.terminalTexto = TerminalTexto.getInstance();
    }
}
