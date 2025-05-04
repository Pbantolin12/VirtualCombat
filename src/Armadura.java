import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;

public class Armadura extends Equipo implements Serializable {
    //Atributos
    private transient TerminalTexto terminalTexto = TerminalTexto.getInstance();

    //Constructor
    public Armadura(String nombre, int modificadorDefensa, int modificadorAtaque) {
        super(nombre, modificadorDefensa, modificadorAtaque);
    }

    //Métodos
    public void mostrarCaracteristicas() {
        terminalTexto.showln("|---Características de la armadura---|");
        terminalTexto.showln("| Armadura: " + this.getNombre());
        terminalTexto.showln("| Modificador de defensa: " + this.getModificadorDefensa());
        terminalTexto.showln("| Modificador de ataque: " + this.getModificadorAtaque());
        terminalTexto.showln("|-------------------------------------|");
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        this.terminalTexto = TerminalTexto.getInstance();
    }
}
