public class Usuario {
    //Atributos
    private String nombre;
    private String nick;
    private String contraseña;

    //Constructor
    public Usuario(String nombre, String nick, String contraseña){
        this.nombre = nombre;
        this.nick = nick;
        this.contraseña = contraseña;
    }

    public Usuario(){}

    //Métodos
    public void registrarse(){}

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

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }
}