import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Jugador extends Usuario implements Observador {
    //Atributos
    private String numeroRegistro;
    private Personaje personaje;
    private List<Desafio> desafiosCompletados;
    private TerminalTexto terminalTexto = TerminalTexto.getInstance();

    public Jugador(String nombre, String nick, String contrasena) {
        super(nombre, nick, contrasena);
        setNumeroRegistro(generarNumeroRegistro());
        this.personaje = crearPersonaje();
        this.desafiosCompletados = new ArrayList<Desafio>();
    }

    //Factory Method
    public static Jugador registrar(String nombre, String nick, String contrasena) {
        if (nombre.isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }
        if (nick.isEmpty()) {
            throw new IllegalArgumentException("El nick no puede estar vacío");
        }
        if (contrasena.length() < 8 || contrasena.length() > 12) {
            throw new IllegalArgumentException("La contraseña debe tener entre 8 y 12 caracteres");
        }
        return new Jugador(nombre, nick, contrasena);
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

    public Personaje crearPersonaje() {
        return null;
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
    }

    public void consultarRanking() {
    }

    private void apostarOro(int cantidad) {
        if (cantidad > 0 && cantidad <= personaje.getOro()) {
            personaje.setOro(personaje.getOro() - cantidad);
            terminalTexto.showln("Apuesta de " + cantidad + " oro realizada");
        } else {
            terminalTexto.error("No tienes suficiente oro para realizar la apuesta");
        }
    }

    public boolean aceptarDesafio() {
        return false;
    }

    public boolean rechazarDesafio() {
        return false;
    }

    private void bloquearAcciones() { }

    private int calcularPorcentajeApuestas(int apuesta) {
        return 0;
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
}