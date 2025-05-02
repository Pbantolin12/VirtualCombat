public class Usuario {
    //Atributos
    private String nombre;
    private String nick;
    private String contrasena;

    //Constructor
    public Usuario(String nombre, String nick, String contrasena){
        this.nombre = nombre;
        this.nick = nick;
        this.contrasena = contrasena;
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
}