import java.util.Comparator;
import java.util.List;

public class Ranking implements Observador {
    //Atributos
    private List<Jugador> jugadores;
    private static Ranking instancia = null;
    private TerminalTexto terminalTexto = TerminalTexto.getInstance();

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

    public void setJugadores(Jugador jugador) {
        this.jugadores.add(jugador);
        this.jugadores.sort(Comparator.comparingInt(Jugador::getPartidasGanadas).reversed());
    }

    @Override
    public void actualizar() {

    }

    public void mostrarRanking() {
        int pos;
        terminalTexto.showln("Ranking de jugadores:");
        for (int i = 0; i < jugadores.size() && i < 10; i++) {
            pos = i;
            Jugador jugador = jugadores.get(i);
            terminalTexto.showln(++pos + ". " + jugador.getNombre() + " - Partidas ganadas: " + jugador.getPartidasGanadas());
        }
    }
}
