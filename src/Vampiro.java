import java.util.HashMap;
import java.util.List;

public class Vampiro extends Personaje{
    //Atributos
    private int puntosSangre;
    private int edad;
    private TerminalTexto terminalTexto= TerminalTexto.getInstance();

    //Constructor
    public Vampiro(String nombre) {
        super(nombre);
        this.puntosSangre = 10;
        this.setHabilidad("Disciplinas");
        terminalTexto.askInfo("Introduce la  edad del vampiro: ");
        this.edad = terminalTexto.readInt();

    }

    //Métodos
    public int getPuntosSangre(){
        return puntosSangre;
    }

    public void setPuntosSangre(){
        this.puntosSangre = puntosSangre;
    }

    public String getDisciplinas(){
        return disciplinas;
    }

    public int getEdad(){
        return edad;
    }

    public void setEdad(int edad){
        this.edad = edad;
    }
}
