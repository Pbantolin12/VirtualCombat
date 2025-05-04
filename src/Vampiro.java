import java.io.IOException;
import java.io.ObjectInputStream;

public class Vampiro extends Personaje {
    //Atributos
    private int puntosSangre;
    private int edad;
    private transient TerminalTexto terminalTexto = TerminalTexto.getInstance();

    //Constructor
    public Vampiro(String nombre) {
        super(nombre);
        this.setPuntosSangre(puntosSangre);
        this.setHabilidad(2);
        terminalTexto.askInfo("Introduce la  edad del vampiro: ");
        this.setEdad(terminalTexto.readInt());
    }

    //Métodos
    public int getPuntosSangre() {
        return puntosSangre;
    }

    public void setPuntosSangre(int puntosSangre) {
        if (puntosSangre < 0) {
            this.puntosSangre = 0;
        } else if (puntosSangre > 10) {
            this.puntosSangre = 10;
        } else {
            this.puntosSangre = puntosSangre;
        }
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        if (edad < 0) {
            terminalTexto.error("La edad no puede ser negativa");
        } else {
            this.edad = edad;
        }
    }

    public int calcularAtaque() {
        if (this.puntosSangre >= 5) {
            if (this.puntosSangre > this.getHabilidad()) {
                this.puntosSangre -= this.getHabilidad();
                return this.getPoder() + this.getArmaActiva().getModificadorAtaque() +
                        this.getArmaduraActiva().getModificadorAtaque() + this.getHabilidad() + 2 +
                        this.getPotencialFortalezas() - this.getPotencialDebilidades();
            } else {
                return this.getPoder() + this.getArmaActiva().getModificadorAtaque() +
                        this.getArmaduraActiva().getModificadorAtaque() + 2 + this.getPotencialFortalezas() -
                        this.getPotencialDebilidades();
            }
        }
        if (this.puntosSangre > this.getHabilidad()) {
            return this.getPoder() + this.getArmaActiva().getModificadorAtaque() +
                    this.getArmaduraActiva().getModificadorAtaque() + this.getHabilidad() +
                    this.getPotencialFortalezas() - this.getPotencialDebilidades();
        } else {
            return this.getPoder() + this.getArmaActiva().getModificadorAtaque() +
                    this.getArmaduraActiva().getModificadorAtaque() + this.getPotencialFortalezas()
                    - this.getPotencialDebilidades();
        }
    }

    public int calcularDefensa() {
        if (this.puntosSangre >= 5) {
            if (this.puntosSangre > this.getHabilidad()) {
                this.puntosSangre -= this.getHabilidad();
                return this.getPoder() + this.getArmaActiva().getModificadorDefensa() +
                        this.getArmaduraActiva().getModificadorDefensa() + 2 + this.getPotencialFortalezas() -
                        this.getPotencialDebilidades();
            } else {
                return this.getPoder() + this.getArmaActiva().getModificadorDefensa() +
                        this.getArmaduraActiva().getModificadorDefensa() + 2 + this.getPotencialFortalezas() -
                        this.getPotencialDebilidades();
            }
        }
        if (this.puntosSangre > this.getHabilidad()) {
            return this.getPoder() + this.getArmaActiva().getModificadorDefensa() +
                    this.getArmaduraActiva().getModificadorDefensa() +
                    this.getHabilidad() + this.getPotencialFortalezas() - this.getPotencialDebilidades();
        } else {
            return this.getPoder() + this.getArmaActiva().getModificadorDefensa() +
                    this.getArmaduraActiva().getModificadorDefensa() + this.getPotencialFortalezas() -
                    this.getPotencialDebilidades();
        }
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        this.terminalTexto = TerminalTexto.getInstance();
    }
}


