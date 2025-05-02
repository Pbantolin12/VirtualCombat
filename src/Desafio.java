import java.util.List;
import java.util.Random;

public class Desafio {
    //Atributos
    private int id;
    private int oroApostado;
    private Jugador jugadorDesafiado;
    private Jugador jugadorDesafiante;
    private List<Observador> observadores;
    private Jugador ganador;
    private boolean desafioAceptado;
    private static final Random random = new Random();
    private TerminalTexto terminalTexto = TerminalTexto.getInstance();
    private boolean validado;

    //Constructor
    public Desafio(int oroApostado, Jugador jugadorDesafiado, Jugador jugadorDesafiante, List<Observador> observadores) {
        this.id = generarId();
        this.oroApostado = oroApostado;
        this.jugadorDesafiado = jugadorDesafiado;
        this.jugadorDesafiante = jugadorDesafiante;
        this.observadores = observadores;
        this.ganador = null;
        this.desafioAceptado = false;
        this.validado = false;
    }

    //Métodos
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOroApostado() {
        return oroApostado;
    }

    public void setOroApostado(int oroApostado) {
        this.oroApostado = oroApostado;
    }

    public Jugador getJugadorDesafiado() {
        return jugadorDesafiado;
    }

    public void setJugadorDesafiado(Jugador jugadorDesafiado) {
        this.jugadorDesafiado = jugadorDesafiado;
    }

    public Jugador getJugadorDesafiante() {
        return jugadorDesafiante;
    }

    public void setJugadorDesafiante(Jugador jugadorDesafiante) {
        this.jugadorDesafiante = jugadorDesafiante;
    }

    public List<Observador> getObservadores() {
        return observadores;
    }

    public void setObservadores(List<Observador> observadores) {
        this.observadores = observadores;
    }

    public Jugador getGanador() {
        return ganador;
    }

    public void setGanador(Jugador ganador) {
        this.ganador = ganador;
    }

    public boolean getDesafioAceptado() {
        return desafioAceptado;
    }

    public void setDesafioAceptado(boolean estado) {
        this.desafioAceptado = estado;
    }

    private int generarId() {
        return random.nextInt(1000); // Genera un ID aleatorio entre 0 y 999
    }

    public void setValidado(boolean validado) {
        this.validado = validado;
    }

    public boolean getValidado() {
        return validado;
    }

    public void iniciarDesafio() { //TODO
        // Lógica para iniciar el desafío
        // Aquí puedes implementar la lógica del desafío entre los jugadores
        // y notificar a los observadores sobre el resultado.
    }

    public void mostrarDesafio(){
        terminalTexto.info("Desafío: " + this.id);
        terminalTexto.info("Oro Apostado: " + this.oroApostado);
        terminalTexto.info("Jugador Desafiado: " + this.jugadorDesafiado.getNombre());
        terminalTexto.info("Jugador Desafiante: " + this.jugadorDesafiante.getNombre());
    }
}
