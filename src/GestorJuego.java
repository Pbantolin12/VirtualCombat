public class GestorJuego {
    //Atributos
    private TerminalTexto terminalTexto;
    private GestorUsuarios gestorUsuarios;

    //Constructor
    public GestorJuego(GestorUsuarios gestorUsuarios) {
        this.terminalTexto = TerminalTexto.getInstance();
        this.gestorUsuarios = gestorUsuarios;
    }

    //Métodos
    public void modoAdmin(Administrador administrador) {
        int opt;
        String nombreJugador;

        terminalTexto.askInfo("Introduce el nombre del jugador para reaizar operaciones: ");
        nombreJugador = terminalTexto.readStr();
        if (gestorUsuarios.getJugador(nombreJugador) == null) {
            terminalTexto.error("El jugador no existe");
            return;
        }
        do {
            opt = menuAdmin();
            switch (opt) {
                case 1 -> administrador.modificarPersonaje(gestorUsuarios.getJugador(nombreJugador).getPersonaje());
                case 2 -> administrador.eliminarPersonaje(gestorUsuarios.getJugador(nombreJugador).getPersonaje());
                case 3 -> administrador.modificarEquipo();
                case 4 -> administrador.añadirEsbirros();
                case 5 -> administrador.validarDesafio();
                case 6 -> administrador.bloquearUsuario(gestorUsuarios.getJugador(nombreJugador));
                case 7 -> administrador.desbloquearUsuario(gestorUsuarios.getJugador(nombreJugador));
                default -> terminalTexto.error("Opción incorrecta");
            }
        } while (opt !=9);
    }

    public void modoJugador(Jugador jugador) {
        int opt;
        int optDesafio;

        do {
            if (jugador.getDesafioPendiente()){
                optDesafio = menuDesafio(jugador);
                switch (optDesafio){
                    case 1 -> jugador.aceptarDesafio();
                    case 2 -> jugador.rechazarDesafio();
                    default -> terminalTexto.error("Opción incorrecta");
                }
            }
        } while (jugador.getDesafioPendiente());

        do {
            opt = menuJugador();
            switch (opt){
                case 1 -> jugador.crearPersonaje();
                case 2 -> jugador.modificarPersonaje();
                case 3 -> jugador.eliminarPersonaje(jugador.getPersonaje());
                case 4 -> jugador.modificarEquipo();
                case 5 -> jugador.desafiarJugador();
                case 6 -> jugador.consultarOro();
                case 7 -> jugador.consultarRanking();
                case 8 -> jugador.consultarHistorialPartidas();
                default -> terminalTexto.error("Opción incorrecta");
            }
        } while (opt != 9);
    }

    public int menuAdmin() {
        terminalTexto.showln(" __________________________");
        terminalTexto.showln("|____Menu_administrador____|");
        terminalTexto.showln("| 1. Modificar personaje   |");
        terminalTexto.showln("| 2. Eliminar personaje    |");
        terminalTexto.showln("| 3. Modificar equipo      |");
        terminalTexto.showln("| 4. Añadir esbirros       |");
        terminalTexto.showln("| 5. Validar desafio       |");
        terminalTexto.showln("| 6. Bloquear usuario      |");
        terminalTexto.showln("| 7. Desbloquear usuario   |");
        terminalTexto.showln("| 8. Volver                |");
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
        terminalTexto.showln("| 9. Volver                       |");
        terminalTexto.showln("|_________________________________|");
        terminalTexto.show("Introduce una opción: ");
        return terminalTexto.readInt();
    }

    public int menuDesafio(Jugador jugador){
        terminalTexto.show("|---Desafio_Pendiente---|");
        terminalTexto.show("| - Desafiado por: " + jugador.getDesafio().getJugadorDesafiante());
        terminalTexto.show("| - Oro apostado: " + jugador.getDesafio().getOroApostado());
        terminalTexto.show("| - Penalización por no aceptar: " + jugador.getDesafio().getOroApostado() * 0.1);
        terminalTexto.show("|------------------------|");
        terminalTexto.show("| 1. Aceptar desafio     |");
        terminalTexto.show("| 2. Rechazar desafio    |");
        terminalTexto.show("|------------------------|");
        terminalTexto.show("Introduce una opción: ");
        return terminalTexto.readInt();
    }
}
