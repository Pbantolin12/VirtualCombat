import java.util.List;

public class Jugador extends Usuario implements Observador{
    //Atributos
    private String numeroRegistro;
    private Personaje personaje;
    private List<Desafio> desafiosCompletados;

    //Constructor
    public Jugador(){}

    //Métodos
    private String generarNumeroRegistro(){
        return null;
    }

    private Personaje crearPersonaje(){
        return null;
    }

    private void eliminarPersonaje(Personaje personaje ){}

    public void desafiarJugador(Jugador jugador ){}

    public void consultarRanking(){}

    private void apostarOro(int cantidad ){}

    public boolean aceptarDesafio(){
        return false;
    }

    public boolean rechazarDesafio(){
        return false;
    }

    private void bloquearAcciones(){}

    public void pagarApuestas(int apuesta){}

    private int calcularPorcentajeApuestas(int apuesta){
        return 0;
    }

    @Override
    public void actualizar(){}

    public String getNumeroRegistro() {
        return numeroRegistro;
    }

    public void setNumeroRegistro(String numeroRegistro) {
        this.numeroRegistro = numeroRegistro;
    }

    public Personaje getPersonaje() {
        return personaje;
    }

    public void setPersonaje(Personaje personaje) {
        this.personaje = personaje;
    }

    public List<Desafio> getDesafiosCompletados() {
        return desafiosCompletados;
    }

    public void setDesafiosCompletados(List<Desafio> desafiosCompletados) {
        this.desafiosCompletados = desafiosCompletados;
    }
}