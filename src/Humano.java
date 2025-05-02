public class Humano extends Esbirros {
    //Atributos
    private Grado lealtad;
    
    //Constructor
    public Humano(String nombre, Grado lealtad) {
        super(nombre);
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
