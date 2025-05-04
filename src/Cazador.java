public class Cazador extends Personaje {
    //Atributos
    private int voluntad;

    //Constructor
    public Cazador(String nombre) {
        super(nombre);
        this.setVoluntad(3);
        this.setHabilidad(2);
    }

    //Métodos
    public int getVoluntad() {
        return voluntad;
    }

    public void setVoluntad(int voluntad) {
        if (voluntad < 0) {
            this.voluntad = 0;
        } else if (voluntad > 3) {
            this.voluntad = 3;
        } else {
            this.voluntad = voluntad;
        }
    }

    public int calcularAtaque() {
        return this.getPoder() + this.getArmaActiva().getModificadorAtaque() +
                this.getArmaActiva().getModificadorAtaque() + this.getHabilidad() + this.voluntad +
                this.getPotencialFortalezas() - this.getPotencialDebilidades();
    }

    public int calcularDefensa() {
        return this.getPoder() + this.getArmaActiva().getModificadorDefensa() +
                this.getArmaduraActiva().getModificadorDefensa() + this.getHabilidad() + this.voluntad +
                this.getPotencialFortalezas() - this.getPotencialDebilidades();
    }
}
