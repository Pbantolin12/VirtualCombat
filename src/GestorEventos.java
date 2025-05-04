import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GestorEventos implements Serializable {
    //Atributos
    private Map<String, List<Observador>> observadores;

    //Constructor
    public GestorEventos() {
        this.observadores = new HashMap<>();
    }

    //Métodos
    public void anadirObservador(String tipoEvento, Observador observador) {
        this.observadores.putIfAbsent(tipoEvento, new ArrayList<>());
        this.observadores.get(tipoEvento).add(observador);
    }

    public void eliminarObservador(String tipoEvento, Observador observador) {
        if (this.observadores.containsKey(tipoEvento)) {
            this.observadores.get(tipoEvento).remove(observador);
        }
    }

    public void notificar(String tipoEvento, Desafio desafio) {
        if (this.observadores.containsKey(tipoEvento)) {
            for (Observador observador : this.observadores.get(tipoEvento)) {
                observador.actualizar(desafio);
            }
        }
    }
}
