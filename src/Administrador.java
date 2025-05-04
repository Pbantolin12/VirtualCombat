import java.io.Serializable;
import java.util.List;

public class Administrador extends Usuario implements Observador {

    //Atributos
    private transient TerminalTexto terminalTexto = TerminalTexto.getInstance();
    private Personaje personajeModificar;
    private GestorUsuarios gestorUsuarios = GestorUsuarios.getInstance();

    //Constructor
    public Administrador(String nombre, String nick, String contrasena){
        super(nombre, nick, contrasena);
    }

    //Factory Method
    public static Administrador registrar(String nombre, String nick, String contrasena){
        if (nombre.isEmpty()){
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }
        if (nick.isEmpty()){
            throw new IllegalArgumentException("El nick no puede estar vacío");
        }
        if (contrasena.length() < 8 || contrasena.length() > 12) {
            throw new IllegalArgumentException("La contraseña debe tener entre 8 y 12 caracteres");
        }
        return new Administrador(nombre, nick, contrasena);
    }

    //Métodos
    public void modificarPersonaje(Personaje personaje) {
        this.personajeModificar = personaje;
        int opt;
        do {
            opt = menuModificarPersonaje();
            switch (opt) {
                case 1 -> {
                    terminalTexto.showln("Nombre actual: " + this.personajeModificar.getNombre());
                    terminalTexto.askInfo("Introduce el nuevo nombre: ");
                    String nuevoNombre = terminalTexto.readStr();
                    if (nuevoNombre != null) {
                        this.personajeModificar.setNombre(nuevoNombre);
                    } else {
                        terminalTexto.error("El nombre no puede estar vacío");
                    }
                }
                case 2 -> this.personajeModificar.modificarEsbirros();
                case 3 -> this.personajeModificar.modificarDebilidades();
                case 4 -> this.personajeModificar.modificarFortalezas();
                case 5 -> modificarEquipo();
                case 6 -> {
                    terminalTexto.askInfo("Introduce la nueva cantidad de oro del persoanje: ");
                    int oro = terminalTexto.readInt();
                    if (oro < 0) {
                        terminalTexto.error("El oro no puede ser negativo");
                    } else {
                        this.personajeModificar.setOro(oro);
                    }
                }
                case 7 -> {
                    terminalTexto.askInfo("Introduce la nueva salud del personaje: ");
                    int salud = terminalTexto.readInt();
                    if (salud < 0 || salud > 5) {
                        terminalTexto.error("La salud debe estar entre 0 y 5");
                    } else {
                        this.personajeModificar.setSalud(salud);
                    }
                }
                default -> terminalTexto.error("Opción incorrecta");
            }
        } while (opt != 8);
    }

    public int menuModificarPersonaje(){
        terminalTexto.showln(" _________________________________");
        terminalTexto.showln("|_____Menu_Modificar_Personaje____|");
        terminalTexto.showln("| 1. Cambiar nombre               |");
        terminalTexto.showln("| 2. Cambiar esbirros             |");
        terminalTexto.showln("| 3. Cambiar debilidades          |");
        terminalTexto.showln("| 4. Cambiar fortalezas           |");
        terminalTexto.showln("| 5. Cambiar equipo               |");
        terminalTexto.showln("| 6. Cambiar oro                  |");
        terminalTexto.showln("| 7. Cambiar salud                |");
        terminalTexto.showln("| 8. Volver                       |");
        terminalTexto.showln("|_________________________________|");
        terminalTexto.show("Introduce una opción: ");
        return terminalTexto.readInt();
    }

    public void validarDesafio(List<Desafio> desafios){
        boolean salir = false;
        while(!desafios.isEmpty() || !salir){
            Desafio desafio = desafios.get(0);
            desafio.mostrarDesafio();
            int opt;
            do {
                opt = menuValidarDesafio();
                switch (opt) {
                    case 1 -> {
                        desafio.getJugadorDesafiante().getPersonaje().setfortalezasActivas();
                        desafio.getJugadorDesafiante().getPersonaje().setdebilidadesActivas();
                    }
                    case 2 -> {
                        desafio.getJugadorDesafiado().getPersonaje().setfortalezasActivas();
                        desafio.getJugadorDesafiado().getPersonaje().setdebilidadesActivas();
                    }
                    case 3 -> {
                        desafio.setValidado(true);
                        terminalTexto.info("Desafío validado");
                        desafios.remove(desafio);
                    }
                    default -> terminalTexto.error("Opción incorrecta");
                }
            } while (opt != 3);
            this.gestorUsuarios.getGestorJuego().setDesafiosPendientes(desafio);
        }
    }

    public int menuValidarDesafio(){
        terminalTexto.showln(" _________________________________");
        terminalTexto.showln("|_____Menu_Validar_Desafio________|");
        terminalTexto.showln("| 1. Gestionar jugador desafiante |");
        terminalTexto.showln("| 2. Gestionar jugador desafiado  |");
        terminalTexto.showln("| 3. Confirmar                    |");
        terminalTexto.showln("|_________________________________|");
        terminalTexto.show("Introduce una opción: ");
        return terminalTexto.readInt();
    }

    public void bloquearUsuario(Usuario usuario){
        if (usuario.getBloqueado()){
            terminalTexto.error("El usuario " + usuario.getNombre() + "ya está bloqueado");
        } else {
            usuario.setBloqueado(true);
            terminalTexto.showln("Usuario " + usuario.getNombre() + " bloqueado");
        }
    }

    public void desbloquearUsuario(Usuario usuario){
        if (!usuario.getBloqueado()){
            terminalTexto.error("El usuario " + usuario.getNombre() + " no está bloqueado");
        } else {
            usuario.setBloqueado(false);
            terminalTexto.showln("Usuario " + usuario.getNombre() + " desbloqueado");
        }
    }

    public void eliminarPersonaje(Jugador jugador){
        if (jugador.getPersonaje() == null){
            terminalTexto.error("El personaje no existe");
        } else {
            terminalTexto.showln("Personaje " + jugador.getPersonaje().getNombre() + " eliminado");
            jugador.setPersonaje(null);
        }
    }

    public void eliminarArma(){
        int delArma = this.personajeModificar.mostrarArmas();
        List<Arma> conjunto = this.personajeModificar.getConjuntoArmas();
        conjunto.remove(delArma - 1);
        terminalTexto.info("Arma eliminada");
    }

    public void eliminarArmadura(){
        int delArmadura = this.personajeModificar.mostrarArmaduras();
        List<Armadura> conjunto = this.personajeModificar.getConjuntoArmaduras();
        conjunto.remove(delArmadura - 1);
        terminalTexto.info("Armadura eliminada");
    }

    public void anadirArma(){
        String nombreArma;
        int modificadorAtaque;
        int modificadorDefensa;
        TipoArma tipoArma = null;
        do {
            terminalTexto.askInfo("Introduce el nombre del arma: ");
            nombreArma = terminalTexto.readStr();
            if (nombreArma.isEmpty()){
                terminalTexto.error("El nombre no puede estar vacío");
            }
        } while (nombreArma.isEmpty());
        do {
            terminalTexto.askInfo("Introduce el modificador de defensa: ");
            modificadorDefensa = terminalTexto.readInt();
            if (modificadorDefensa < 0 || modificadorDefensa > 3){
                terminalTexto.error("El modificador de defensa debe estar entre 1 y 3");
            }
        } while (modificadorDefensa < 0 || modificadorDefensa > 3);
        do{
            terminalTexto.askInfo("Introduce el modificador de ataque: ");
            modificadorAtaque = terminalTexto.readInt();
            if (modificadorAtaque < 0 || modificadorAtaque > 3){
                terminalTexto.error("El modificador de ataque debe estar entre 1 y 3");
            }
        } while (modificadorAtaque < 0 || modificadorAtaque > 3);
        do {
            terminalTexto.askInfo("Introduce el tipo de arma (UNA_MANO/DOS_MANOS): ");
            String tipoArmaStr = terminalTexto.readStr();
            if (tipoArmaStr.equalsIgnoreCase("UNA_MANO")){
                tipoArma = TipoArma.UNA_MANO;
            } else if (tipoArmaStr.equalsIgnoreCase("DOS_MANOS")){
                tipoArma = TipoArma.DOS_MANOS;
            } else {
                terminalTexto.error("El tipo de arma no es válido");
            }
        } while (tipoArma == null);
        this.personajeModificar.setArma(new Arma(nombreArma, modificadorDefensa, modificadorAtaque, tipoArma));
    }

    public void anadirArmadura(){
        String nombreArmadura;
        int modificadorAtaque;
        int modificadorDefensa;
        do {
            terminalTexto.askInfo("Introduce el nombre de la armadura: ");
            nombreArmadura = terminalTexto.readStr();
            if (nombreArmadura.isEmpty()){
                terminalTexto.error("El nombre no puede estar vacío");
            }
        } while (nombreArmadura.isEmpty());
        do {
            terminalTexto.askInfo("Introduce el modificador de defensa: ");
            modificadorDefensa = terminalTexto.readInt();
            if (modificadorDefensa < 0 || modificadorDefensa > 3){
                terminalTexto.error("El modificador de defensa debe estar entre 1 y 3");
            }
        } while (modificadorDefensa < 0 || modificadorDefensa > 3);
        do{
            terminalTexto.askInfo("Introduce el modificador de ataque: ");
            modificadorAtaque = terminalTexto.readInt();
            if (modificadorAtaque < 0 || modificadorAtaque > 3){
                terminalTexto.error("El modificador de ataque debe estar entre 1 y 3");
            }
        } while (modificadorAtaque < 0 || modificadorAtaque > 3);
        this.personajeModificar.setArmadura(new Armadura(nombreArmadura, modificadorDefensa, modificadorAtaque));
    }

    public void modificarEquipo(){
        int optEquipo;
        do {
            optEquipo = menuModificarEquipo();
            switch (optEquipo) {
                case 1 -> anadirArma();
                case 2 -> anadirArmadura();
                case 3 -> eliminarArma();
                case 4 -> eliminarArmadura();
                default -> terminalTexto.error("Opción incorrecta");
            }
        } while (optEquipo != 5);
    }

    public int menuModificarEquipo(){
        terminalTexto.showln(" _________________________________");
        terminalTexto.showln("|_____Menu_Modificar_Equipo_______|");
        terminalTexto.showln("| 1. Añadir arma                  |");
        terminalTexto.showln("| 2. Añadir armadura              |");
        terminalTexto.showln("| 3. Eliminar arma                |");
        terminalTexto.showln("| 4. Eliminar armadura            |");
        terminalTexto.showln("| 5. Volver                       |");
        terminalTexto.showln("|_________________________________|");
        terminalTexto.show("Introduce una opción: ");
        return terminalTexto.readInt();
    }

    @Override
    public void actualizar(Desafio desafio) {
        if (!desafio.getValidado() && this.gestorUsuarios.getGestorJuego().getUsuarioLogeado().equals(this)) {
            terminalTexto.info("Nuevo desafio a validar");
            this.gestorUsuarios.getGestorJuego().setDesafiosPendientes(desafio);
        }
    }
}