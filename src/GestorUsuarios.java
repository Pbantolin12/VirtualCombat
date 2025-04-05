import java.util.List;

public class GestorUsuarios {
    //Atributos
    private List<Jugador> jugadores;
    private List<Administrador> administradores;

    //Constructor
    public GestorUsuarios(){}

    //Métodos
    public Jugador registrarJugador(){
        return null;
    }
    public List<Jugador> getJugadores() {
        return jugadores;
    }

    public void setJugadores(List<Jugador> jugadores) {
        this.jugadores = jugadores;
    }

    public List<Administrador> getAdministradores() {
        return administradores;
    }

    public void setAdministradores(List<Administrador> administradores) {
        this.administradores = administradores;
    }
}