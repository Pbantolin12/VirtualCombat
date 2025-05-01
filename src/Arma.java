public class Arma extends Equipo{
    //Atributos
    private TipoArma tipo;
    private int modificadorDefensa;
    private int modificadorAtaque;
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
        terminalTexto.showln("| Modificador de defensa: " + this.modificadorDefensa);
        terminalTexto.showln("| Modificador de ataque: " + this.modificadorAtaque);
        terminalTexto.showln("|-------------------------------|");
    }
}
