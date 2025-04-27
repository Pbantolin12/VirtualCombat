public class Administrador extends Usuario implements Observador{

    //Atributos
    private TerminalTexto terminalTexto = TerminalTexto.getInstance();

    //Constructor
    public Administrador(String nombre, String nick, String contrasena){
        super(nombre, nick, contrasena);
    }

    //Factory Method
    public static Administrador registrar(String nombre, String nick, String contrasena){
        if (nombre.isEmpty()){
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }
        if (nick.isEmpty()){
            throw new IllegalArgumentException("El nick no puede estar vacío");
        }
        if (contrasena.length() < 8 || contrasena.length() > 12) {
            throw new IllegalArgumentException("La contraseña debe tener entre 8 y 12 caracteres");
        }
        return new Administrador(nombre, nick, contrasena);
    }

    //Métodos
    public void modificarPersonaje(Personaje personaje){}

    public void añadirArma(Arma arma){}

    public void añadirArmadura(Armadura arma){}

    public void añadirFortaleza(String fortaleza){}

    public void añadirDebilidad(String debilidad){}

    public void añadirEsbirros(){}

    public void validarDesafio(){}

    public void gestionarFortalezas(Desafio desafio){}

    public void gestionarDebilidades(Desafio desafio){}

    public void bloquearUsuario(Usuario usuario){}

    public void desbloquearUsuario(Usuario usuario){}

    public void eliminarPersonaje(Personaje personaje){}

    public void eliminarArma(){}

    public void eliminarArmadura(){}

    public void añadirArma(){}

    public void añadirArmadura(){}

    public void modificarEquipo(){
        int optEquipo;
        do {
            optEquipo = menuModificarEquipo();
            switch (optEquipo) {
                case 1 -> añadirArma();
                case 2 -> añadirArmadura();
                case 3 -> eliminarArma();
                case 4 -> eliminarArmadura();
            }
        } while (optEquipo != 5);
    }

    public int menuModificarEquipo(){
        terminalTexto.showln(" _________________________________");
        terminalTexto.showln("|_____Menu_Modificar_Equipo_______|");
        terminalTexto.showln("| 1. Añadir arma                  |");
        terminalTexto.showln("| 2. Añadir armadura              |");
        terminalTexto.showln("| 3. Eliminar arma                |");
        terminalTexto.showln("| 4. Eliminar armadura            |");
        terminalTexto.showln("| 5. Volver                       |");
        terminalTexto.showln("|_________________________________|");
        terminalTexto.show("Introduce una opción: ");
        return terminalTexto.readInt();
    }

    @Override
    public void actualizar(){}
}