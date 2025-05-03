import java.util.HashMap;
import java.util.List;

public class Licantropo extends Personaje{
    //Atributos
    private int rabia;

    //Constructor
    public Licantropo(String nombre) {
        super(nombre);
        this.setRabia(0);
        this.setHabilidad(2);
    }

    //Métodos
    public int getRabia() {
        return rabia;
    }

    public void setRabia(int rabia) {
        if (rabia < 0){
            this.rabia = 0;
        } else if (rabia > 3){
            this.rabia = 3;
        } else {
            this.rabia = rabia;
        }
    }

    public int calcularAtaque() {
        if (this.rabia > this.getHabilidad()){
            return this.getPoder() + this.getArmaActiva().getModificadorAtaque() +
                    this.getArmaduraActiva().getModificadorAtaque() + this.getHabilidad() +
                    this.getPotencialFortalezas() - this.getPotencialDebilidades();
        } else {
            return this.getPoder() + this.getArmaActiva().getModificadorAtaque() +
                    this.getArmaduraActiva().getModificadorAtaque() + this.getPotencialFortalezas()
                    - this.getPotencialDebilidades();
        }
    } //TODO: al perder vida la rabia aumenta 1 punto

    public int calcularDefensa() {
        if (this.rabia > this.getHabilidad()) {
            return this.getPoder() + this.getArmaActiva().getModificadorDefensa() +
                    this.getArmaduraActiva().getModificadorDefensa() + this.getHabilidad() +
                    this.getPotencialFortalezas() - this.getPotencialDebilidades();
        } else {
            return this.getPoder() + this.getArmaActiva().getModificadorDefensa() +
                    this.getArmaduraActiva().getModificadorDefensa() + this.getPotencialFortalezas() - this.getPotencialDebilidades();
        }
    }
}
