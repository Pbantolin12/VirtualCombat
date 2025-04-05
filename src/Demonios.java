public class Demonios extends Esbirros {
    //Atributos
    private String pacto;

    //Constructor
    public Demonios(String pacto, String nombre, int salud, int ataque, int defensa) {
        super(nombre, salud, ataque, defensa);
        this.pacto = pacto;
    }

    //Métodos
    public String getPacto() {
        return pacto;
    }

    public void setPacto(String pacto) {
        this.pacto = pacto;
    }
}
