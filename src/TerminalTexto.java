import java.util.Scanner;

public class TerminalTexto {

    /*Se aplica el patrón singleton*/

    //Atributos
    private static TerminalTexto instance = new TerminalTexto(); //Instancia única

    //Constructor
    private TerminalTexto(){}

    //Métodos

    //Obtenemos la instancia
    public static TerminalTexto getInstance(){
        if(instance == null){
            instance = new TerminalTexto();
        }
        return instance;
    }

    //Muestra el texto sin salto de línea
    public void show(String text){
        System.out.print(text);
    }

    //Muestra el texto con un salto de línea
    public void showln(String text){
        System.out.println(text);
    }

    //Introduce un salto de línea
    public void nextLine(){
        System.out.println(" ");
    }

    //Muestra un mensaje de error
    public void error(String desc){
        this.nextLine();
        System.out.println("//-ERROR-\\\\ --> " + desc);
        this.nextLine();
    }

    //Muestra un mensaje informando de algo
    public void info(String info){
        this.nextLine();
        System.out.println("**" + info + "**");
        this.nextLine();
    }

    //Lee lo que el usuario introduce (int)
    public int readInt(){
        Scanner scan = new Scanner(System.in);
        return scan.nextInt();
    }

    //Lee lo que el usuario introduce (string)
    public String readStr(){
        Scanner scan = new Scanner(System.in);
        return scan.nextLine();
    }

    //Muestra un "prompt" para pedir al usuario que introduzca información
    public void askInfo(String text){
        System.out.print(">>" + text);
    }
}
