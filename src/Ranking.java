import java.util.List;

public class Ranking implements Observador {
    //Atributos
    private List<Jugador> jugadores;
    private static Ranking instancia = null;

    //Métodos
    public static Ranking getInstance() {
        if (instancia == null) {
            instancia = new Ranking();
        }
        return instancia;
    }

    public List<Jugador> getJugadores() {
        return jugadores;
    }

    public void setJugadores(List<Jugador> jugadores) {
        this.jugadores = jugadores;
    }

    @Override
    public void actualizar() {

    }
}
