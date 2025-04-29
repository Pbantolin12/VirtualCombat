import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Jugador extends Usuario implements Observador {
    //Atributos
    private String numeroRegistro;
    private Personaje personaje;
    private List<Desafio> desafiosCompletados;
    private TerminalTexto terminalTexto = TerminalTexto.getInstance();
    private Boolean desafioPendiente;
    private Desafio desafio;
    private int partidasGanadas;
    private GestorUsuarios gestorUsuarios;

    public Jugador(String nombre, String nick, String contrasena, GestorUsuarios gestorUsuarios) {
        super(nombre, nick, contrasena);
        setNumeroRegistro(generarNumeroRegistro());
        crearPersonaje();
        this.desafiosCompletados = new ArrayList<Desafio>();
        this.desafioPendiente = false;
        this.desafio = null;
        this.partidasGanadas = 0;
        this.gestorUsuarios = gestorUsuarios;
    }

    //Factory Method
    public static Jugador registrar(String nombre, String nick, String contrasena, GestorUsuarios gestorUsuarios) {
        if (nombre.isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }
        if (nick.isEmpty()) {
            throw new IllegalArgumentException("El nick no puede estar vacío");
        }
        if (contrasena.length() < 8 || contrasena.length() > 12) {
            throw new IllegalArgumentException("La contraseña debe tener entre 8 y 12 caracteres");
        }
        return new Jugador(nombre, nick, contrasena, gestorUsuarios);
    }

    //Métodos
    private String generarNumeroRegistro() {
        Random random = new Random();
        String codigo;

        char letra1 = (char) (random.nextInt(26) + 'A');
        int numero1 = random.nextInt(10);
        int numero2 = random.nextInt(10);
        char letra2 = (char) (random.nextInt(26) + 'A');
        char letra3 = (char) (random.nextInt(26) + 'A');
        codigo = "" + letra1 + numero1 + numero2 + letra2 + letra3;
        return codigo;
    }

    public void crearPersonaje() {
        terminalTexto.askInfo("Introduce el nombre del personaje: ");
        String nombre = terminalTexto.readStr();
        int opt = menuPersonaje();
        switch (opt) {
            case 1 -> {
                terminalTexto.askInfo("Introduce el número de debilidades que tendrá del personaje: ");
                int numDebilidades = terminalTexto.readInt();
                int j = 0;
                for (int i = 0; i < numDebilidades; i++) {
                    terminalTexto.askInfo("Introduce el nombre de la debilidad " + ++j + ": ");
                    String nombreDebilidad = terminalTexto.readStr();
                    terminalTexto.askInfo("Introduce la descripción de la debilidad: ");
                    String descripcionDebilidad = terminalTexto.readStr();
                    personaje.setDebilidad(nombreDebilidad, descripcionDebilidad);
                }

                terminalTexto.askInfo("Introduce el número de fortalezas que tendrá del personaje: ");
                int numFortalezas = terminalTexto.readInt();
                j = 0;
                for (int i = 0; i < numFortalezas; i++) {
                    this.personaje = new Vampiro(nombre);
                    terminalTexto.askInfo("Introduce el nombre de la fortaleza " + ++j + ": ");
                    String nombreFortaleza = terminalTexto.readStr();
                    terminalTexto.askInfo("Introduce la descripción de la fortaleza: ");
                    String descripcionFortaleza = terminalTexto.readStr();
                    personaje.setFortaleza(nombreFortaleza, descripcionFortaleza);
                }
            }
            case 2 -> {
                terminalTexto.askInfo("Introduce el número de debilidades que tendrá del personaje: ");
                int numDebilidades = terminalTexto.readInt();
                int j = 0;
                for (int i = 0; i < numDebilidades; i++) {
                    terminalTexto.askInfo("Introduce el nombre de la debilidad " + ++j + ": ");
                    String nombreDebilidad = terminalTexto.readStr();
                    terminalTexto.askInfo("Introduce la descripción de la debilidad: ");
                    String descripcionDebilidad = terminalTexto.readStr();
                    personaje.setDebilidad(nombreDebilidad, descripcionDebilidad);
                }

                terminalTexto.askInfo("Introduce el número de fortalezas que tendrá del personaje: ");
                int numFortalezas = terminalTexto.readInt();
                j = 0;
                for (int i = 0; i < numFortalezas; i++) {
                    this.personaje = new Licantropo(nombre);
                    terminalTexto.askInfo("Introduce el nombre de la fortaleza " + ++j + ": ");
                    String nombreFortaleza = terminalTexto.readStr();
                    terminalTexto.askInfo("Introduce la descripción de la fortaleza: ");
                    String descripcionFortaleza = terminalTexto.readStr();
                    personaje.setFortaleza(nombreFortaleza, descripcionFortaleza);
                }
            }
            case 3 -> {
                terminalTexto.askInfo("Introduce el número de debilidades que tendrá del personaje: ");
                int numDebilidades = terminalTexto.readInt();
                int j = 0;
                for (int i = 0; i < numDebilidades; i++) {
                    terminalTexto.askInfo("Introduce el nombre de la debilidad " + ++j + ": ");
                    String nombreDebilidad = terminalTexto.readStr();
                    terminalTexto.askInfo("Introduce la descripción de la debilidad: ");
                    String descripcionDebilidad = terminalTexto.readStr();
                    personaje.setDebilidad(nombreDebilidad, descripcionDebilidad);
                }

                terminalTexto.askInfo("Introduce el número de fortalezas que tendrá del personaje: ");
                int numFortalezas = terminalTexto.readInt();
                j = 0;
                for (int i = 0; i < numFortalezas; i++) {
                    this.personaje = new Cazador(nombre);
                    terminalTexto.askInfo("Introduce el nombre de la fortaleza " + ++j + ": ");
                    String nombreFortaleza = terminalTexto.readStr();
                    terminalTexto.askInfo("Introduce la descripción de la fortaleza: ");
                    String descripcionFortaleza = terminalTexto.readStr();
                    personaje.setFortaleza(nombreFortaleza, descripcionFortaleza);
                }
            }
            default -> {
                terminalTexto.error("Opción incorrecta");
            }
        }

    }

    private int menuPersonaje(){
        terminalTexto.show(" ________________________________");
        terminalTexto.show("|_____Personajes_disponibles_____|");
        terminalTexto.show("| 1. Vampiro                     |");
        terminalTexto.show("| 2. Licántropo                  |");
        terminalTexto.show("| 3. Cazador                     |");
        terminalTexto.show("|________________________________|");
        terminalTexto.askInfo("Introduce una opción: ");
        return terminalTexto.readInt();
    }

    public void eliminarPersonaje(Personaje personaje) {
        if (personaje != null) {
            terminalTexto.showln("El personaje " + personaje.getNombre() + " ha sido eliminado");
            this.personaje = null;
        } else {
            terminalTexto.error("No hay ningún personaje para eliminar");
        }
    }

    public void desafiarJugador() {
        String nombreDesafiado;

        terminalTexto.askInfo("Introduce el nombre del jugador a desafiar: ");
        nombreDesafiado = terminalTexto.readStr();
        Jugador jugadorDesafiado = gestorUsuarios.getJugador(nombreDesafiado);
        if (jugadorDesafiado != null) {
            if (jugadorDesafiado.getDesafioPendiente()) {
                terminalTexto.error("El jugador ya tiene un desafío pendiente");
            } else {
                terminalTexto.askInfo("Introduce la cantidad de oro a apostar: ");
                int oroApostado = terminalTexto.readInt();

                if (this.apostarOro(oroApostado)) {
                    Desafio desafio = new Desafio(oroApostado, jugadorDesafiado, this, null); //CAMBIAR NULL
                    this.desafio = desafio;
                    this.desafioPendiente = false;
                    jugadorDesafiado.setDesafioPendiente(true);
                    jugadorDesafiado.setDesafio(desafio);
                    terminalTexto.showln("|---Desafio_enviado---|");
                    terminalTexto.showln("| - Jugador desafiado: " + jugadorDesafiado.getNombre());
                    terminalTexto.showln("| - Oro apostado: " + desafio.getOroApostado());
                }
            }
        } else {
            terminalTexto.error("El jugador no existe");
        }
    }

    public void consultarRanking() {
        Ranking ranking = Ranking.getInstance();
        ranking.mostrarRanking();
    }

    private boolean apostarOro(int cantidad) {
        if (cantidad > 0 && cantidad <= personaje.getOro()) {
            personaje.setOro(personaje.getOro() - cantidad);
            terminalTexto.showln("Apuesta de " + cantidad + " oro realizada");
            return true;
        } else {
            terminalTexto.error("No tienes suficiente oro para realizar la apuesta");
            return false;
        }
    }

    public boolean aceptarDesafio() {
        return false;
    }

    public void rechazarDesafio() {
        this.desafioPendiente = false;
        this.desafio.setDesafioAceptado(false);
        this.personaje.setOro(personaje.getOro() - this.calcularPorcentajeApuestas(this.desafio.getOroApostado()));
    }

    private int calcularPorcentajeApuestas(int apuesta) {
        return (int) (apuesta * 0.1);
    }

    public void consultarOro() {
        terminalTexto.showln("Oro disponible: " + personaje.getOro());
    }

    public void consultarHistorialPartidas() {
        terminalTexto.showln("Historial de partidas:");
        for (Desafio desafio : desafiosCompletados) {
            terminalTexto.showln(desafio.toString() + " - Ganador: " + desafio.getGanador() +
                    " - Apostado: " + desafio.getOroApostado());
        }
    }

    public void modificarEquipo() {}

    public void modificarPersonaje() {
        int opt;
        do {
            opt = menuModificarEquipo();
            switch (opt) {
                case 1 -> terminalTexto.showln("Arma activa: " + getPersonaje().getArmaActiva().toString());
                case 2 -> terminalTexto.showln("Armadura activa: " + getPersonaje().getArmaduraActiva().toString());
                case 3 -> cambiarArma();
                case 4 -> cambiarArmadura();
            }
        } while (opt != 5);
    }

    private void cambiarArma(){
        int i = 0;
        terminalTexto.showln("Arma activa: " + getPersonaje().getArmaActiva().toString());
        terminalTexto.show("Armas disponibles: ");
        for (Arma arma : getPersonaje().getConjuntoArmas()) {
            terminalTexto.show(i++ + ". " + arma.toString());
        }
        terminalTexto.askInfo("Introduce el número del arma que quieres equipar: ");
        int armaEquipar = terminalTexto.readInt();
        personaje.setArmaActiva(personaje.getConjuntoArmas().get(armaEquipar));
    }

    private void cambiarArmadura(){
        int i = 0;
        terminalTexto.showln("Armadura activa: " + getPersonaje().getArmaduraActiva().toString());
        terminalTexto.show("Armaduras disponibles: ");
        for (Armadura armadura : getPersonaje().getConjuntoArmaduras()) {
            terminalTexto.show(i++ + ". " + armadura.toString());
        }
        terminalTexto.askInfo("Introduce el número de la armadura que quieres equipar: ");
        int armaduraEquipar = terminalTexto.readInt();
        personaje.setArmaduraActiva(personaje.getConjuntoArmaduras().get(armaduraEquipar));
    }

    public int menuModificarEquipo(){
        terminalTexto.showln(" _________________________________");
        terminalTexto.showln("|_____Menu_Modificar_Equipo_______|");
        terminalTexto.showln("| 1. Ver arma activa              |");
        terminalTexto.showln("| 2. Ver armadura activa          |");
        terminalTexto.showln("| 3. Cambiar arma activa          |");
        terminalTexto.showln("| 4. Cambiar armadura activa      |");
        terminalTexto.showln("| 5. Volver                       |");
        terminalTexto.showln("|_________________________________|");
        terminalTexto.show("Introduce una opción: ");
        return terminalTexto.readInt();
    }

    @Override
    public void actualizar() {
    }

    public String getNumeroRegistro() {
        return numeroRegistro;
    }

    public void setNumeroRegistro(String numeroRegistro) {
        this.numeroRegistro = numeroRegistro;
    }

    public Personaje getPersonaje() {
        return personaje;
    }

    public void setPersonaje(Personaje personaje) {
        this.personaje = personaje;
    }

    public List<Desafio> getDesafiosCompletados() {
        return desafiosCompletados;
    }

    public void setDesafiosCompletados(List<Desafio> desafiosCompletados) {
        this.desafiosCompletados = desafiosCompletados;
    }

    public void setDesafioPendiente(Boolean desafioPendiente) {
        this.desafioPendiente = desafioPendiente;
    }

    public Boolean getDesafioPendiente() {
        return desafioPendiente;
    }

    public Desafio getDesafio() {
        return desafio;
    }

    public void setDesafio(Desafio desafio) {
        this.desafio = desafio;
    }

    public int getPartidasGanadas() {
        return partidasGanadas;
    }

    public void setPartidasGanadas(int partidasGanadas) {
        this.partidasGanadas = partidasGanadas;
    }
}