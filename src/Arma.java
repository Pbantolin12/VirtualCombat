public class Arma extends Equipo{
    //Atributos
    private TipoArma tipo;
    private int modificadorDefensa;
    private int modificadorAtaque;
    private String nombre;

    //Constructor
    public Arma(String nombre, TipoArma tipo, int modificadorDefensa, int modificadorAtaque) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.modificadorDefensa = modificadorDefensa;
        this.modificadorAtaque = modificadorAtaque;
    }

    //Métodos
    public TipoArma getTipo() {
        return tipo;
    }

    public void setTipo(TipoArma tipo){
        this.tipo = tipo;
    }

    public int getModificadorDefensa() {
        return modificadorDefensa;
    }

    public void setModificadorDefensa(int modificadorDefensa){
        this.modificadorDefensa = modificadorDefensa;
    }

    public int getModificadorAtaque() {
        return modificadorAtaque;
    }

    public void setModificadorAtaque(int modificadorAtaque) {
        this.modificadorAtaque = modificadorAtaque;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
