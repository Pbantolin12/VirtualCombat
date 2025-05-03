import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GestorUsuarios implements Serializable {
    //Atributos
    private List<Jugador> jugadores;
    private List<Administrador> administradores;
    private transient TerminalTexto terminal = TerminalTexto.getInstance();
    private GestorJuego gestorJuego;
    private TerminalTexto terminalTexto = TerminalTexto.getInstance();

    //Constructor
    public GestorUsuarios() {
        this.jugadores = new ArrayList<>();
        this.administradores = new ArrayList<>();
        this.gestorJuego = new GestorJuego(GestorUsuarios.this);
    }

    //Métodos

    public void iniciar() {
        int opcion = 0;
        while (opcion < 1 || opcion > 3) {
            opcion = menuPrincipal();
            switch (opcion) {
                case 1 -> {
                    int opcionIni;
                    do {
                        opcionIni = menuRegistro();
                        switch (opcionIni) {
                            case 1 -> {
                                setAdministradores(registrarAdministrador());
                                opcion = 0;
                            }
                            case 2 -> {
                                setJugadores(registrarJugador());
                                opcion = 0;
                            }
                            case 3 -> opcion = 0;
                            default -> terminalTexto.error("Opción incorrecta");
                        }
                    } while (opcionIni != 3);
                }
                case 2 -> {
                    int opcionReg = 0;
                    while (opcionReg != 3) {
                        opcionReg = menuInicioSesion();
                        switch (opcionReg) {
                            case 1 -> iniciarSesionAdministrador();
                            case 2 -> iniciarSesionJugador();
                            case 3 -> opcion = 0;
                            default -> terminalTexto.error("Opción incorrecta");
                        }
                    }
                }
                case 3 -> {
                    terminal.showln("Saliendo...");
                    System.exit(0);
                }
                default -> terminalTexto.error("Opción incorrecta");
            }
        }
    }

    //Método para registrar un jugador o administrador
    private Jugador registrarJugador() {
        String nombre = "";
        String nick = "";
        String contrasena = "";

        terminal.nextLine();
        while (nombre.isEmpty()) {
            terminal.askInfo("Introduzca su nombre: ");
            nombre = terminal.readStr();
            if (nombre.isEmpty()) {
                terminal.error("El nombre no puede estar vacío");
            }
        }
        while (nick.isEmpty()) {
            terminal.askInfo("Introduzca su nick: ");
            nick = terminal.readStr();
            if (nick.isEmpty()) {
                terminal.error("El nick no puede estar vacío");
            }
        }
        while (contrasena.length() < 8 || contrasena.length() > 12) {
            terminal.askInfo("Introduzca su contraseña: ");
            contrasena = terminal.readStr();
            if (contrasena.length() < 8 || contrasena.length() > 12) {
                terminal.error("La contraseña debe tener entre 8 y 12 caracteres");
            }
        }
        return Jugador.registrar(nombre, nick, contrasena, GestorUsuarios.this);
    }

    //Método para iniciar sesión como jugador
    private void iniciarSesionJugador() {
        String nombre = "";
        String contrasena = "";

        terminal.askInfo("Introduzca su nombre: ");
        nombre = terminal.readStr();
        terminal.askInfo("Introduzca su contraseña: ");
        contrasena = terminal.readStr();

        if (jugadores.isEmpty()) {
            terminal.error("No hay jugadores registrados");
            return;
        }

        for (Jugador jugador : jugadores) {
            if (jugador.getNombre().equals(nombre)) {
                if (jugador.getContrasena().equals(contrasena)) {
                    if (jugador.getBloqueado()) {
                        terminal.error("El usuario ha sido bloqueado");
                        return;
                    } else {
                        terminal.info("Iniciando sesión...");
                        gestorJuego.modoJugador(jugador);
                    }
                } else {
                    terminal.error("Contraseña incorrecta: ");
                }
            } else {
                terminal.error("Usuario no encontrado");
            }
        }
    }

    //Método para iniciar sesión como administrador
    private void iniciarSesionAdministrador() {
        String nombre = "";
        String contrasena = "";

        terminal.askInfo("Introduzca su nombre: ");
        nombre = terminal.readStr();
        terminal.askInfo("Introduzca su contraseña: ");
        contrasena = terminal.readStr();
        if (administradores.isEmpty()) {
            terminal.error("No hay administradores registrados");
            return;
        }
        for (Administrador admin : administradores) {
            if (admin.getNombre().equals(nombre)) {
                if (admin.getContrasena().equals(contrasena)) {
                    terminal.info("Iniciando sesión...");
                    gestorJuego.modoAdmin(admin);
                } else {
                    terminal.error("Contraseña incorrecta");
                }
            } else {
                terminal.error("Usuario no encontrado");
            }
        }
    }

    //Método para registrar un administrador
    private Administrador registrarAdministrador() {
        String nombre = "";
        String nick = "";
        String contrasena = "";

        terminal.nextLine();
        while (nombre.isEmpty()) {
            terminal.askInfo("Introduzca su nombre: ");
            nombre = terminal.readStr();
            if (nombre.isEmpty()) {
                terminal.error("El nombre no puede estar vacío");
            }
        }
        while (nick.isEmpty()) {
            terminal.askInfo("Introduzca su nick: ");
            nick = terminal.readStr();
            if (nick.isEmpty()) {
                terminal.error("El nick no puede estar vacío");
            }
        }
        while (contrasena.length() < 8 || contrasena.length() > 12) {
            terminal.askInfo("Introduzca su contraseña: ");
            contrasena = terminal.readStr();
            if (contrasena.length() < 8 || contrasena.length() > 12) {
                terminal.error("La contraseña debe tener entre 8 y 12 caracteres");
            }
        }
        return Administrador.registrar(nombre, nick, contrasena);
    }

    //Método para dar de baja a un jugador
    public void darBajaJugador(Jugador jugador) {
        if (jugadores.isEmpty()) {
            terminal.error("No hay jugadores registrados");
            return;
        }
        for (Jugador j : jugadores) {
            if (j.getNumeroRegistro().equals(jugador.getNumeroRegistro())) {
                terminal.info("Jugador" + jugador.getNombre() + "dado de baja");
                jugadores.remove(j);
            } else {
                terminal.error("Jugador no encontrado");
            }
        }
    }

    //Método para dar de baja a un administrador
    public void darBajaAdministrador(Administrador admin) {
        if (administradores.isEmpty()) {
            terminal.error("No hay administradores registrados");
            return;
        }
        for (Administrador a : administradores) {
            if (a.getNombre().equals(admin.getNombre())) {
                terminal.info("Administrador" + admin.getNombre() + "dado de baja");
                administradores.remove(a);
            } else {
                terminal.error("Administrador no encontrado");
            }
        }
    }

    public List<Jugador> getJugadores() {
        return jugadores;
    }

    public Jugador getJugador(String nombre) {
        for (Jugador jugador : jugadores) {
            if (jugador.getNombre().equals(nombre)) {
                return jugador;
            }
        }
        return null;
    }

    public void setJugadores(Jugador jugador) {
        this.jugadores.add(jugador);
    }

    public List<Administrador> getAdministradores() {
        return administradores;
    }

    public void setAdministradores(Administrador admin) {
        this.administradores.add(admin);
    }

    //Método para mostrar el menú principal
    private int menuPrincipal() {
        terminal.showln(" _____________________________");
        terminal.showln("|_Bienvenido_a_Virtual_Combat_|");
        terminal.showln("| 1.Crear una cuenta          |");
        terminal.showln("| 2.Iniciar sesión            |");
        terminal.showln("| 3.Salir                     |");
        terminal.showln("|_____________________________|");
        terminal.askInfo("Introduzca una opción: ");
        return terminal.readInt();
    }

    //Método para mostrar el menú de registro
    private int menuRegistro() {
        terminal.showln(" _____________________________________");
        terminal.showln("|____________Crear_cuenta_____________|");
        terminal.showln("| 1. Crear cuenta como administrador  |");
        terminal.showln("| 2. Crear cuenta como jugador        |");
        terminal.showln("| 3. Volver                           |");
        terminal.showln("|_____________________________________|");
        terminal.askInfo("Introduzca una opción: ");
        return terminal.readInt();
    }

    //Método para mostrar el menú de inicio de sesión
    private int menuInicioSesion() {
        terminal.showln(" _____________________________________");
        terminal.showln("|___________Iniciar_sesion____________|");
        terminal.showln("| 1. Inciar sesion como administrador |");
        terminal.showln("| 2. Inciar sesion como jugador       |");
        terminal.showln("| 3. Volver                           |");
        terminal.showln("|_____________________________________|");
        terminal.askInfo("Introduzca una opción: ");
        return terminal.readInt();
    }
}