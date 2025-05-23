import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Ranking implements Observador, Serializable {
    //Atributos
    private List<Jugador> jugadores;
    private static Ranking instancia = null;
    private transient TerminalTexto terminalTexto = TerminalTexto.getInstance();

    //Constructor
    private Ranking() {
        this.jugadores = new ArrayList<>();
    }

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
    public void actualizar(Desafio desafio) {
        this.setJugadores(desafio.getGanador());
        jugadores.sort(Comparator.comparingInt(Jugador::getPartidasGanadas).reversed());
        terminalTexto.info("Ranking actualizado");
    }

    public void mostrarRanking() {
        int pos;
        terminalTexto.showln("Ranking de jugadores:");
        for (int i = 0; i < this.jugadores.size() && i < 10; i++) {
            pos = i;
            Jugador jugador = this.jugadores.get(i);
            terminalTexto.showln(++pos + ". " + jugador.getNombre() + " - Partidas ganadas: " + jugador.getPartidasGanadas());
        }
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        this.terminalTexto = TerminalTexto.getInstance();
    }
}
