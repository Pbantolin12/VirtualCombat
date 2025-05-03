import java.io.Serializable;
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
    private Random random = new Random();

    public Jugador(String nombre, String nick, String contrasena, GestorUsuarios gestorUsuarios) {
        super(nombre, nick, contrasena);
        setNumeroRegistro(generarNumeroRegistro());
        crearPersonaje();
        this.desafiosCompletados = new ArrayList<>();
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
        String codigo;
        char letra1 = (char) (this.random.nextInt(26) + 'A');
        int numero1 = this.random.nextInt(10);
        int numero2 = this.random.nextInt(10);
        char letra2 = (char) (this.random.nextInt(26) + 'A');
        char letra3 = (char) (this.random.nextInt(26) + 'A');
        codigo = "" + letra1 + numero1 + numero2 + letra2 + letra3;
        return codigo;
    }

    public void crearPersonaje() {
        terminalTexto.askInfo("Introduce el nombre del personaje: ");
        String nombre = terminalTexto.readStr();
        if (nombre == null) {
            terminalTexto.error("El nombre no puede estar vacío");
            return;
        }
        int opt = menuPersonaje();
        switch (opt) {
            case 1 -> this.personaje = new Vampiro(nombre);
            case 2 -> this.personaje = new Licantropo(nombre);
            case 3 -> this.personaje = new Cazador(nombre);
            default -> terminalTexto.error("Opción incorrecta");
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
                    this.desafio = new Desafio(oroApostado, jugadorDesafiado, this, this.gestorUsuarios.getAdministradores());
                    this.desafioPendiente = false;
                    jugadorDesafiado.setDesafioPendiente(true);
                    jugadorDesafiado.setDesafio(desafio);
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

    public void aceptarDesafio() {
        this.desafioPendiente = false;
        int opt;
        do {
            opt = menuDesafio();
            switch (opt) {
                case 1 -> {
                    this.personaje.getArmaActiva().mostrarCaracteristicas();
                    this.personaje.getArmaduraActiva().mostrarCaracteristicas();
                }
                case 2 -> this.modificarEquipo();
                case 3 -> this.desafio.setDesafioAceptado(true);
                default -> terminalTexto.error("Opción incorrecta");
            }
        } while (!this.desafio.getDesafioAceptado());
        this.desafio.iniciarDesafio();
        //Agregar el desafío a la lista de desafíos completados
        this.desafiosCompletados.add(this.desafio);
        if (this.desafio.getJugadorDesafiante() != this) {
            this.desafio.getJugadorDesafiante().getDesafiosCompletados().add(this.desafio);
        }
    }

    private int menuDesafio(){
        terminalTexto.showln(" __________________________");
        terminalTexto.showln("|_______Menu_Desafio_______|");
        terminalTexto.showln("| 1. Mostrar equipo activo |");
        terminalTexto.showln("| 2. Cambiar equipo        |");
        terminalTexto.showln("| 3. Confirmar             |");
        terminalTexto.showln("|__________________________|");
        terminalTexto.show("Introduce una opción: ");
        return terminalTexto.readInt();
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
        for (Desafio desafioC : this.desafiosCompletados) {
            terminalTexto.showln(desafioC.toString() + " - Ganador: " + desafioC.getGanador() +
                    " - Apostado: " + desafioC.getOroApostado());
        }
    }

    public void modificarEquipo() {
        int opt;
        do {
            opt = menuModificarEquipo();
            switch (opt) {
                case 1 -> {
                    terminalTexto.showln("Arma activa: " + getPersonaje().getArmaActiva().getNombre());
                    getPersonaje().getArmaActiva().mostrarCaracteristicas();
                }
                case 2 -> {
                    terminalTexto.showln("Armadura activa: " + getPersonaje().getArmaduraActiva().getNombre());
                    getPersonaje().getArmaduraActiva().mostrarCaracteristicas();
                }
                case 3 -> cambiarArma();
                case 4 -> cambiarArmadura();
                default -> terminalTexto.error("Opción incorrecta");
            }
        } while (opt != 5);
    }

    public void modificarPersonaje() {
        int opt;
        do {
            opt = menuModificarPersonaje();
            switch (opt) {
                case 1 -> {
                    terminalTexto.showln("Nombre actual: " + getPersonaje().getNombre());
                    terminalTexto.askInfo("Introduce el nuevo nombre: ");
                    String nuevoNombre = terminalTexto.readStr();
                    if (nuevoNombre != null) {
                        getPersonaje().setNombre(nuevoNombre);
                    } else {
                        terminalTexto.error("El nombre no puede estar vacío");
                    }
                }
                case 2 -> this.personaje.modificarEsbirros();
                case 3 -> this.personaje.modificarDebilidades();
                case 4 -> this.personaje.modificarFortalezas();
                default -> terminalTexto.error("Opción incorrecta");
            }
        } while (opt != 5);
    }

    private void cambiarArma(){
        int i = 0;
        terminalTexto.showln("Arma activa: " + getPersonaje().getArmaActiva().getNombre());
        terminalTexto.show("Armas disponibles: ");
        for (Arma arma : getPersonaje().getConjuntoArmas()) {
            terminalTexto.show(i++ + ". " + arma.getNombre());
            arma.mostrarCaracteristicas();
            terminalTexto.nextLine();
        }
        terminalTexto.askInfo("Introduce el número del arma que quieres equipar: ");
        int armaEquipar = terminalTexto.readInt();
        personaje.setArmaActiva(personaje.getConjuntoArmas().get(armaEquipar));
    }

    private void cambiarArmadura(){
        int i = 0;
        terminalTexto.showln("Armadura activa: " + getPersonaje().getArmaduraActiva().getNombre());
        terminalTexto.show("Armaduras disponibles: ");
        for (Armadura armadura : getPersonaje().getConjuntoArmaduras()) {
            terminalTexto.show(i++ + ". " + armadura.getNombre());
            armadura.mostrarCaracteristicas();
            terminalTexto.nextLine();
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
    public void actualizar(Desafio desafio) {
        if (desafio.getJugadorDesafiado() == this) {
            terminalTexto.info("Has sido desafiado por " + desafio.getJugadorDesafiante().getNombre());
        } else if (desafio.getJugadorDesafiante() == this) {
            terminalTexto.info("Tu desafío a " + desafio.getJugadorDesafiado().getNombre() + " ha sido " +
                    (desafio.getDesafioAceptado() ? "aceptado" : "enviado"));
        } else if(desafio.getDesafioAceptado() && desafio.getGanador() != null) {
            terminalTexto.info("El desafío finalizado --> Ganador: " + desafio.getGanador().getNombre());
        }
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

    public int menuModificarPersonaje(){
        terminalTexto.showln(" _________________________________");
        terminalTexto.showln("|_____Menu_Modificar_Personaje____|");
        terminalTexto.showln("| 1. Cambiar nombre               |");
        terminalTexto.showln("| 2. Cambiar esbirros             |");
        terminalTexto.showln("| 3. Cambiar debilidades          |");
        terminalTexto.showln("| 4. Cambiar fortalezas           |");
        terminalTexto.showln("| 5. Volver                       |");
        terminalTexto.showln("|_________________________________|");
        terminalTexto.show("Introduce una opción: ");
        return terminalTexto.readInt();
    }
}