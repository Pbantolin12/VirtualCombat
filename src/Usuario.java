import java.io.Serializable;

public class Usuario implements Serializable {
    //Atributos
    private String nombre;
    private String nick;
    private String contrasena;
    private boolean bloqueado;

    //Constructor
    public Usuario(String nombre, String nick, String contrasena){
        this.nombre = nombre;
        this.nick = nick;
        this.contrasena = contrasena;
        this.bloqueado = false;
    }

    public Usuario(){}

    //Métodos
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public boolean getBloqueado() {
        return bloqueado;
    }

    public void setBloqueado(boolean bloqueado) {
        this.bloqueado = bloqueado;
    }
}