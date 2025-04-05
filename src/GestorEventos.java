import java.util.List;

public class GestorEventos {
    //Atributos
    private List<Observador> observadores;

    //Constructor
    public GestorEventos(){}

    //Métodos
    public void añadirObservador(Observador o){
        observadores.add(o);
    }

    public void eliminarObservador(Observador o){
        observadores.remove(o);
    }

    public void notificarObservadores(){
        for(Observador o: observadores){
            o.actualizar();
        }
    }
}
