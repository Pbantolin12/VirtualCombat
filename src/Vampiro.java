import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Vampiro extends Personaje{
    //Atributos
    private int puntosSangre;
    private int edad;
    private TerminalTexto terminalTexto = TerminalTexto.getInstance();

    //Constructor
    public Vampiro(String nombre) {
        super(nombre);
        this.setPuntosSangre(puntosSangre);
        this.setHabilidad("Disciplinas");
        terminalTexto.askInfo("Introduce la  edad del vampiro: ");
        this.setEdad(terminalTexto.readInt());
    }

    //Métodos
    public int getPuntosSangre(){
        return puntosSangre;
    }

    public void setPuntosSangre(int puntosSangre){
        if (puntosSangre < 0){
            this.puntosSangre = 0;
        } else if (puntosSangre > 10){
            this.puntosSangre = 10;
        } else {
            this.puntosSangre = puntosSangre;
        }
    }

    public int getEdad(){
        return edad;
    }

    public void setEdad(int edad){
        if (edad < 0) {
            terminalTexto.error("La edad no puede ser negativa");
        } else  {
            this.edad = edad;
        }
    }
}
