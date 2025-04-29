import java.util.HashMap;
import java.util.List;

public class Cazador extends Personaje{
    //Atributos
    private int voluntad;

    //Constructor
    public Cazador(String nombre) {
        super(nombre);
        this.setVoluntad(3);
        this.setHabilidad("Talentos");
    }

    //Métodos
    public int getVoluntad(){
        return voluntad;
    }

    public void setVoluntad(int voluntad){
        if (voluntad < 0){
            this.voluntad = 0;
        } else if (voluntad > 3){
            this.voluntad = 3;
        } else {
            this.voluntad = voluntad;
        }
    }
}
