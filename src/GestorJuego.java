import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GestorJuego implements Serializable {
    //Atributos
    private static GestorJuego instancia;
    private transient TerminalTexto terminalTexto;
    private GestorUsuarios gestorUsuarios;
    private List<Desafio> desafiosPendientes;
    private Usuario usuarioLogeado;


    //Constructor
    private GestorJuego() {
        this.terminalTexto = TerminalTexto.getInstance();
        this.desafiosPendientes = new ArrayList<>();
        this.usuarioLogeado = null;
    }

    public static GestorJuego getInstance() {
        if (instancia == null) {
            instancia = new GestorJuego();
        }
        return instancia;
    }

    //Métodos
    public void modoAdmin(Administrador administrador) {
        this.usuarioLogeado = administrador;
        this.gestorUsuarios = GestorUsuarios.getInstance();
        int opt;
        String nombreJugador;

        terminalTexto.showln("Bienvenido " + administrador.getNombre());
        terminalTexto.askInfo("Introduce el nombre del jugador para realizar operaciones: ");
        nombreJugador = terminalTexto.readStr();
        if (gestorUsuarios.getJugador(nombreJugador) == null) {
            terminalTexto.error("El jugador no existe");
            return;
        }
        do {
            opt = menuAdmin();
            switch (opt) {
                case 1 -> administrador.modificarPersonaje(gestorUsuarios.getJugador(nombreJugador).getPersonaje());
                case 2 -> administrador.eliminarPersonaje(gestorUsuarios.getJugador(nombreJugador));
                case 3 -> administrador.validarDesafio(this.desafiosPendientes);
                case 4 -> administrador.bloquearUsuario(gestorUsuarios.getJugador(nombreJugador));
                case 5 -> administrador.desbloquearUsuario(gestorUsuarios.getJugador(nombreJugador));
                case 6 -> terminalTexto.info("Cerrando sesión...");
                default -> terminalTexto.error("Opción incorrecta");
            }
        } while (opt !=6);
    }

    public void modoJugador(Jugador jugador) {
        this.usuarioLogeado = jugador;
        this.gestorUsuarios = GestorUsuarios.getInstance();
        int opt;
        int optDesafio;

        terminalTexto.showln("Bienvenido " + jugador.getNombre());
        do {
            do {
                if (Boolean.TRUE.equals(jugador.getDesafioPendiente())){
                    optDesafio = menuDesafio(jugador);
                    switch (optDesafio){
                        case 1 -> jugador.aceptarDesafio();
                        case 2 -> jugador.rechazarDesafio();
                        default -> terminalTexto.error("Opción incorrecta");
                    }
                }
            } while (Boolean.TRUE.equals(jugador.getDesafioPendiente()));
            opt = menuJugador();
            if (jugador.getPersonaje() == null && opt != 1 && opt != 9) {
                // Si no tiene personaje y no eligió crear uno o salir
                terminalTexto.error("Primero debes crear un personaje (opción 1)");
            } else {
                switch (opt) {
                    case 1 -> {
                        if (jugador.getPersonaje() != null) {
                            terminalTexto.error("Ya tienes un personaje creado");
                        } else {
                            jugador.crearPersonaje();
                        }
                    }
                    case 2 -> jugador.modificarPersonaje();
                    case 3 -> jugador.eliminarPersonaje(jugador.getPersonaje());
                    case 4 -> jugador.modificarEquipo();
                    case 5 -> jugador.desafiarJugador();
                    case 6 -> jugador.consultarOro();
                    case 7 -> jugador.consultarRanking();
                    case 8 -> jugador.consultarHistorialPartidas();
                    case 9 -> terminalTexto.info("Cerrando sesión...");
                    default -> terminalTexto.error("Opción incorrecta");
                }
            }
        } while (opt != 9);
    }

    public int menuAdmin() {
        terminalTexto.showln(" __________________________");
        terminalTexto.showln("|____Menu_administrador____|");
        terminalTexto.showln("| 1. Modificar personaje   |");
        terminalTexto.showln("| 2. Eliminar personaje    |");
        terminalTexto.showln("| 3. Validar desafio       |");
        terminalTexto.showln("| 4. Bloquear usuario      |");
        terminalTexto.showln("| 5. Desbloquear usuario   |");
        terminalTexto.showln("| 6. Cerrar sesión         |");
        terminalTexto.showln("|__________________________");
        terminalTexto.show("Introduce una opción: ");
        return terminalTexto.readInt();
    }

    public int menuJugador() {
        terminalTexto.showln(" _________________________________");
        terminalTexto.showln("|__________Menu_jugador___________|");
        terminalTexto.showln("| 1. Crear personaje              |");
        terminalTexto.showln("| 2. Modificar personaje          |");
        terminalTexto.showln("| 3. Eliminar personaje           |");
        terminalTexto.showln("| 4. Modificar equipo             |");
        terminalTexto.showln("| 5. Desafiar jugador             |");
        terminalTexto.showln("| 6. Consultar oro                |");
        terminalTexto.showln("| 7. Consultar ranking            |");
        terminalTexto.showln("| 8. Consultar historial partidas |");
        terminalTexto.showln("| 9. Cerrar sesión                |");
        terminalTexto.showln("|_________________________________|");
        terminalTexto.show("Introduce una opción: ");
        return terminalTexto.readInt();
    }

    public int menuDesafio(Jugador jugador){
        terminalTexto.showln("|---Desafio_Pendiente---|");
        terminalTexto.showln("| - Desafiado por: " + jugador.getDesafio().getJugadorDesafiante());
        terminalTexto.showln("| - Oro apostado: " + jugador.getDesafio().getOroApostado());
        terminalTexto.showln("| - Penalización por no aceptar: " + jugador.getDesafio().getOroApostado() * 0.1);
        terminalTexto.showln("|------------------------|");
        terminalTexto.showln("| 1. Aceptar desafio     |");
        terminalTexto.showln("| 2. Rechazar desafio    |");
        terminalTexto.showln("|------------------------|");
        terminalTexto.showln("Introduce una opción: ");
        return terminalTexto.readInt();
    }

    public List<Desafio> getDesafiosPendientes() {
        return desafiosPendientes;
    }

    public void setDesafiosPendientes(Desafio desafio) {
        this.desafiosPendientes.add(desafio);
    }

    public Usuario getUsuarioLogeado() {
        return usuarioLogeado;
    }

    public void setUsuarioLogeado(Usuario usuarioLogeado) {
        this.usuarioLogeado = usuarioLogeado;
    }
}
