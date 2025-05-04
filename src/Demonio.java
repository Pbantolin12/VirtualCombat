public class Demonio extends Esbirros {
    //Atributos
    private String pacto;
    private Esbirros conjuntoEsbirros;
    private TerminalTexto terminalTexto = TerminalTexto.getInstance();

    //Constructor
    public Demonio(String nombre, String pacto) {
        super(nombre);
        this.pacto = pacto;
        terminalTexto.showln("¿ Quieres añadir otro conjunto de esbirros ? ");
        terminalTexto.showln("1. Si");
        terminalTexto.showln("2. No");
        terminalTexto.askInfo("Elige una opción: ");
        int opcion = terminalTexto.readInt();
        if (opcion == 1) {
           this.anadirEsbirros();
        } else {
            this.conjuntoEsbirros = null;
        }

    }

    //Métodos
    public String getPacto() {
        return pacto;
    }

    public void setPacto(String pacto) {
        this.pacto = pacto;
    }

    public Esbirros getConjuntoEsbirros() {
        return conjuntoEsbirros;
    }

    public void setConjuntoEsbirros(Esbirros conjuntoEsbirros) {
        this.conjuntoEsbirros = conjuntoEsbirros;
    }

    public void anadirEsbirros(){
        int opt;
        String nombre;
        do {
            opt = menuEsbirros();
            if (opt == 4 && this.conjuntoEsbirros == null){
                terminalTexto.error("No has añadido ningun esbirro");
            }
            switch (opt){
                case 1 -> {
                    do {
                        terminalTexto.askInfo("Introduce el nombre del esbirro: ");
                        nombre = terminalTexto.readStr();
                        if (nombre.isEmpty()){
                            terminalTexto.error("El nombre no puede ser vacío");
                        }
                    } while (nombre.isEmpty());
                    int dependencia;
                    do {
                        terminalTexto.askInfo("Introduce la dependencia del esbirro (entre 1 y 5): ");
                        dependencia = terminalTexto.readInt();
                        if (dependencia < 1 || dependencia > 3){
                            terminalTexto.error("Valor incorrecto");
                        }
                    } while (opt < 1 || opt > 3);
                    this.conjuntoEsbirros = new Ghoul(nombre, dependencia);
                }
                case 2 -> {
                    do {
                        terminalTexto.askInfo("Introduce el nombre del esbirro: ");
                        nombre = terminalTexto.readStr();
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
                    String grado;
                    Boolean gradoAux = false;
                    do {
                        terminalTexto.askInfo("Introduce la lealtad del esbirro: ");
                        grado = terminalTexto.readStr();
                        if (grado.isEmpty()){
                            terminalTexto.error("El grado no puede estar vacío");
                        }
                        for (Grado g : Grado.values()) {
                            if (g.name().equals(grado)){
                                gradoAux = true;
                            }
                        }
                        if (!gradoAux){
                            terminalTexto.error("El grado introducido no es correcto");
                        }
                    } while (grado.isEmpty() && !gradoAux);
                    this.conjuntoEsbirros = new Humano(nombre, Grado.valueOf(grado));
                }
                default -> terminalTexto.error("Opción incorrecta");
            }
        } while (this.conjuntoEsbirros != null);
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
}
