import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GestorUsuarios implements Serializable {
    //Atributos
    private static GestorUsuarios instance;
    private List<Jugador> jugadores;
    private List<Administrador> administradores;
    private transient TerminalTexto terminalTexto = TerminalTexto.getInstance();
    private GestorJuego gestorJuego = GestorJuego.getInstance();

    //Constructor
    private GestorUsuarios() {
        this.jugadores = new ArrayList<>();
        this.administradores = new ArrayList<>();
    }

    public static GestorUsuarios getInstance() {
        if (instance == null) {
            instance = new GestorUsuarios();
        }
        return instance;
    }

    //Métodos

    public void iniciar() {
        int opcion;
        do {
            opcion = menuPrincipal();
            switch (opcion) {
                case 1 -> {
                    int opcionReg;
                    do {
                        opcionReg = menuRegistro();
                        switch (opcionReg) {
                            case 1 -> {
                                setAdministradores(registrarAdministrador());
                                GestorPersistencia.guardarObjeto( "gestorUsuarios.dat", this);
                                opcionReg = 3;
                            }
                            case 2 -> {
                                setJugadores(registrarJugador());
                                GestorPersistencia.guardarObjeto( "gestorUsuarios.dat", this);                                opcionReg = 3;
                            }
                            case 3 -> {
                                opcion = 0;
                                GestorPersistencia.guardarObjeto( "gestorUsuarios.dat", this);                            }
                            default -> terminalTexto.error("Opción incorrecta");
                        }
                    } while (opcionReg != 3);
                }
                case 2 -> {
                    int opcionIni;
                    do {
                        opcionIni = menuInicioSesion();
                        switch (opcionIni) {
                            case 1 -> {
                                iniciarSesionAdministrador();
                                GestorPersistencia.guardarObjeto( "gestorUsuarios.dat", this);                                opcionIni = 3;
                            }
                            case 2 ->{
                                iniciarSesionJugador();
                                GestorPersistencia.guardarObjeto( "gestorUsuarios.dat", this);                                opcionIni = 3;
                            }
                            default -> terminalTexto.error("Opción incorrecta");
                        }
                    } while (opcionIni != 3);
                }
                case 3 -> {
                    GestorPersistencia.guardarObjeto( "gestorUsuarios.dat", this);                    terminalTexto.showln("Saliendo...");
                    System.exit(0);
                }
                default -> terminalTexto.error("Opción incorrecta");
            }
        } while (opcion != 3);
    }

    //Método para registrar un jugador o administrador
    private Jugador registrarJugador() {
        String nombre = "";
        String nick = "";
        String contrasena = "";

        terminalTexto.nextLine();
        while (nombre.isEmpty()) {
            terminalTexto.askInfo("Introduzca su nombre: ");
            nombre = terminalTexto.readStr();
            if (nombre.isEmpty()) {
                terminalTexto.error("El nombre no puede estar vacío");
            }
        }
        while (nick.isEmpty()) {
            terminalTexto.askInfo("Introduzca su nick: ");
            nick = terminalTexto.readStr();
            if (nick.isEmpty()) {
                terminalTexto.error("El nick no puede estar vacío");
            }
        }
        while (contrasena.length() < 8 || contrasena.length() > 12) {
            terminalTexto.askInfo("Introduzca su contraseña: ");
            contrasena = terminalTexto.readStr();
            if (contrasena.length() < 8 || contrasena.length() > 12) {
                terminalTexto.error("La contraseña debe tener entre 8 y 12 caracteres");
            }
        }
        return Jugador.registrar(nombre, nick, contrasena, GestorUsuarios.this);
    }

    //Método para iniciar sesión como jugador
    private void iniciarSesionJugador() {
        String nombre = "";
        String contrasena = "";

        terminalTexto.askInfo("Introduzca su nombre: ");
        nombre = terminalTexto.readStr();
        terminalTexto.askInfo("Introduzca su contraseña: ");
        contrasena = terminalTexto.readStr();

        if (jugadores.isEmpty()) {
            terminalTexto.error("No hay jugadores registrados");
            return;
        }

        for (Jugador jugador : jugadores) {
            if (jugador.getNombre().equals(nombre)) {
                if (jugador.getContrasena().equals(contrasena)) {
                    if (jugador.getBloqueado()) {
                        terminalTexto.error("El usuario ha sido bloqueado");
                        return;
                    } else {
                        terminalTexto.info("Iniciando sesión...");
                        this.gestorJuego.modoJugador(jugador);
                        this.getGestorJuego().setUsuarioLogeado(null);
                    }
                } else {
                    terminalTexto.error("Contraseña incorrecta: ");
                }
            } else {
                terminalTexto.error("Usuario no encontrado");
            }
        }
    }

    //Método para iniciar sesión como administrador
    private void iniciarSesionAdministrador() {
        String nombre = "";
        String contrasena = "";

        terminalTexto.askInfo("Introduzca su nombre: ");
        nombre = terminalTexto.readStr();
        terminalTexto.askInfo("Introduzca su contraseña: ");
        contrasena = terminalTexto.readStr();
        if (administradores.isEmpty()) {
            terminalTexto.error("No hay administradores registrados");
            return;
        }
        for (Administrador admin : administradores) {
            if (admin.getNombre().equals(nombre)) {
                if (admin.getContrasena().equals(contrasena)) {
                    terminalTexto.info("Iniciando sesión...");
                    this.gestorJuego.modoAdmin(admin);
                    this.getGestorJuego().setUsuarioLogeado(null);
                } else {
                    terminalTexto.error("Contraseña incorrecta");
                }
            } else {
                terminalTexto.error("Usuario no encontrado");
            }
        }
    }

    //Método para registrar un administrador
    private Administrador registrarAdministrador() {
        String nombre = "";
        String nick = "";
        String contrasena = "";

        terminalTexto.nextLine();
        while (nombre.isEmpty()) {
            terminalTexto.askInfo("Introduzca su nombre: ");
            nombre = terminalTexto.readStr();
            if (nombre.isEmpty()) {
                terminalTexto.error("El nombre no puede estar vacío");
            }
        }
        while (nick.isEmpty()) {
            terminalTexto.askInfo("Introduzca su nick: ");
            nick = terminalTexto.readStr();
            if (nick.isEmpty()) {
                terminalTexto.error("El nick no puede estar vacío");
            }
        }
        while (contrasena.length() < 8 || contrasena.length() > 12) {
            terminalTexto.askInfo("Introduzca su contraseña: ");
            contrasena = terminalTexto.readStr();
            if (contrasena.length() < 8 || contrasena.length() > 12) {
                terminalTexto.error("La contraseña debe tener entre 8 y 12 caracteres");
            }
        }
        return Administrador.registrar(nombre, nick, contrasena);
    }

    //Método para dar de baja a un jugador
    public void darBajaJugador(Jugador jugador) {
        if (jugadores.isEmpty()) {
            terminalTexto.error("No hay jugadores registrados");
            return;
        }
        for (Jugador j : jugadores) {
            if (j.getNumeroRegistro().equals(jugador.getNumeroRegistro())) {
                terminalTexto.info("Jugador" + jugador.getNombre() + "dado de baja");
                jugadores.remove(j);
            } else {
                terminalTexto.error("Jugador no encontrado");
            }
        }
    }

    //Método para dar de baja a un administrador
    public void darBajaAdministrador(Administrador admin) {
        if (administradores.isEmpty()) {
            terminalTexto.error("No hay administradores registrados");
            return;
        }
        for (Administrador a : administradores) {
            if (a.getNombre().equals(admin.getNombre())) {
                terminalTexto.info("Administrador" + admin.getNombre() + "dado de baja");
                administradores.remove(a);
            } else {
                terminalTexto.error("Administrador no encontrado");
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
        terminalTexto.showln(" _____________________________");
        terminalTexto.showln("|_Bienvenido_a_Virtual_Combat_|");
        terminalTexto.showln("| 1.Crear una cuenta          |");
        terminalTexto.showln("| 2.Iniciar sesión            |");
        terminalTexto.showln("| 3.Salir                     |");
        terminalTexto.showln("|_____________________________|");
        terminalTexto.askInfo("Introduzca una opción: ");
        return terminalTexto.readInt();
    }

    //Método para mostrar el menú de registro
    private int menuRegistro() {
        terminalTexto.showln(" _____________________________________");
        terminalTexto.showln("|____________Crear_cuenta_____________|");
        terminalTexto.showln("| 1. Crear cuenta como administrador  |");
        terminalTexto.showln("| 2. Crear cuenta como jugador        |");
        terminalTexto.showln("| 3. Volver                           |");
        terminalTexto.showln("|_____________________________________|");
        terminalTexto.askInfo("Introduzca una opción: ");
        return terminalTexto.readInt();
    }

    //Método para mostrar el menú de inicio de sesión
    private int menuInicioSesion() {
        terminalTexto.showln(" _____________________________________");
        terminalTexto.showln("|___________Iniciar_sesion____________|");
        terminalTexto.showln("| 1. Inciar sesion como administrador |");
        terminalTexto.showln("| 2. Inciar sesion como jugador       |");
        terminalTexto.showln("| 3. Volver                           |");
        terminalTexto.showln("|_____________________________________|");
        terminalTexto.askInfo("Introduzca una opción: ");
        return terminalTexto.readInt();
    }

    public GestorJuego getGestorJuego() {
        return gestorJuego;
    }

    public void setGestorJuego(GestorJuego gestorJuego) {
        this.gestorJuego = gestorJuego;
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        this.terminalTexto = TerminalTexto.getInstance();
    }

    private Object readResolve() {
        instance = this;
        return instance;
    }
}