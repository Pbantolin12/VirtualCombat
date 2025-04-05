public class Administrador extends Usuario implements Observador{

    //Constructor
    public Administrador(){
    }

    //Métodos
    public void modificarPersonaje(Personaje personaje){}

    public void añadirArma(Arma arma){}

    public void añadirArmadura(Armadura arma){}

    public void añadirFortaleza(String fortaleza){}

    public void añadirDebilidad(String debilidad){}

    public void añadirEsbirros(Esbirros esbirro){}

    public void validarDesafio(Desafio desafio){}

    public void gestionarFortalezas(Desafio desafio){}

    public void gestionarDebilidades(Desafio desafio){}

    public void bloquearUsuario(Usuario usuario){}

    public void desbloquearUsuario(Usuario usuario){}

    @Override
    public void actualizar(){}
}
