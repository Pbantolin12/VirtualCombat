public class Humanos extends Esbirros {
    //Atributos
    private Grado lealtad;
    
    //Constructor
    public Humanos(Grado lealtad, String nombre, int salud, int ataque, int defensa) {
        super(nombre, salud, ataque, defensa);
        this.lealtad = lealtad;
    }

    //Métodos
    public Grado getGrado(){
        return lealtad;
    }

    public void setGrado(Grado lealtad){
        this.lealtad = lealtad;
    }
}
