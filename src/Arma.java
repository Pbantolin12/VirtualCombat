public class Arma extends Equipo{
    //Atributos
    private TipoArma tipo;
    private int modificadorDefensa;

    //Constructor
    public Arma(){}

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
}
