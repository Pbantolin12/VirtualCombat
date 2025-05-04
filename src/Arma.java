import java.io.Serializable;

public class Arma extends Equipo implements Serializable {
    //Atributos
    private TipoArma tipo;
    private TerminalTexto terminalTexto = TerminalTexto.getInstance();

    //Constructor
    public Arma(String nombre, int modificadorDefensa, int modificadorAtaque, TipoArma tipo) {
        super(nombre, modificadorDefensa, modificadorAtaque);
        this.tipo = tipo;
    }

    //Métodos
    public TipoArma getTipo() {
        return tipo;
    }

    public void setTipo(TipoArma tipo){
        this.tipo = tipo;
    }

    public void mostrarCaracteristicas(){
        terminalTexto.showln("|---Características del arma---|");
        terminalTexto.showln("| Arma: " + this.getNombre());
        terminalTexto.showln("| Tipo: " + this.getTipo());
        terminalTexto.showln("| Modificador de defensa: " + this.getModificadorDefensa());
        terminalTexto.showln("| Modificador de ataque: " + this.getModificadorAtaque());
        terminalTexto.showln("|-------------------------------|");
    }
}
