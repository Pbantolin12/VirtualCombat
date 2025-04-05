import java.util.List;

public class Desafio {
    //Atributos
    private int id;
    private int oroApostado;
    private Jugador jugadorDesafiado;
    private Jugador jugadorDesafiante;
    private List<Observador> observadores;

    //Constructor

    //Constructor
    public Desafio(int id, int oroApostado, Jugador jugadorDesafiado, Jugador jugadorDesafiante, List<Observador> observadores) {
        this.id = id;
        this.oroApostado = oroApostado;
        this.jugadorDesafiado = jugadorDesafiado;
        this.jugadorDesafiante = jugadorDesafiante;
        this.observadores = observadores;
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
}
