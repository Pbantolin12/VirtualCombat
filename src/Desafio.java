import java.io.Serializable;
import java.util.List;
import java.util.Random;

public class Desafio implements Serializable {
    //Atributos
    private int id;
    private int oroApostado;
    private Jugador jugadorDesafiado;
    private Jugador jugadorDesafiante;
    private List<Observador> observadores;
    private Jugador ganador;
    private boolean desafioAceptado;
    private static final Random random = new Random();
    private TerminalTexto terminalTexto = TerminalTexto.getInstance();
    private boolean validado;
    private Random rand = new Random();

    //Constructor
    public Desafio(int oroApostado, Jugador jugadorDesafiado, Jugador jugadorDesafiante, List<Observador> observadores) {
        this.id = generarId();
        this.oroApostado = oroApostado;
        this.jugadorDesafiado = jugadorDesafiado;
        this.jugadorDesafiante = jugadorDesafiante;
        this.observadores = observadores;
        this.ganador = null;
        this.desafioAceptado = false;
        this.validado = false;
    }

    //Métodos
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOroApostado() {
        return oroApostado;
    }

    public void setOroApostado(int oroApostado) {
        this.oroApostado = oroApostado;
    }

    public Jugador getJugadorDesafiado() {
        return jugadorDesafiado;
    }

    public void setJugadorDesafiado(Jugador jugadorDesafiado) {
        this.jugadorDesafiado = jugadorDesafiado;
    }

    public Jugador getJugadorDesafiante() {
        return jugadorDesafiante;
    }

    public void setJugadorDesafiante(Jugador jugadorDesafiante) {
        this.jugadorDesafiante = jugadorDesafiante;
    }

    public List<Observador> getObservadores() {
        return observadores;
    }

    public void setObservadores(List<Observador> observadores) {
        this.observadores = observadores;
    }

    public Jugador getGanador() {
        return ganador;
    }

    public void setGanador(Jugador ganador) {
        this.ganador = ganador;
    }

    public boolean getDesafioAceptado() {
        return desafioAceptado;
    }

    public void setDesafioAceptado(boolean estado) {
        this.desafioAceptado = estado;
    }

    private int generarId() {
        return random.nextInt(1000); // Genera un ID aleatorio entre 0 y 999
    }

    public void setValidado(boolean validado) {
        this.validado = validado;
    }

    public boolean getValidado() {
        return validado;
    }

    public void iniciarDesafio() {
        int ronda = 1;

        while (this.jugadorDesafiante.getPersonaje().getSalud() != 0 || this.jugadorDesafiado.getPersonaje().getSalud() != 0) {
            int ataqueDesafiante = calcularPotencialAtaque(this.jugadorDesafiante);
            int defensaDesafiado = calcularPotencialDefensa(this.jugadorDesafiado);
            int ataqueDesafiado = calcularPotencialAtaque(this.jugadorDesafiado);
            int defensaDesafiante = calcularPotencialDefensa(this.jugadorDesafiante);
            int saludEsbirrosDesafiante = calcularSaludEsbirros(this.jugadorDesafiante);
            int saludEsbirrosDesafiado = calcularSaludEsbirros(this.jugadorDesafiado);

            terminalTexto.info("|---RONDA " + ronda + "---|");
            ataqueDesafiante(ataqueDesafiante, defensaDesafiado, saludEsbirrosDesafiante, saludEsbirrosDesafiado);
            ataqueDesafiado(ataqueDesafiado, defensaDesafiante, saludEsbirrosDesafiante, saludEsbirrosDesafiado);
        }
        if (this.jugadorDesafiante.getPersonaje().getSalud() == 0) {
            this.ganador = this.jugadorDesafiado;
            terminalTexto.info("El ganador es: " + this.jugadorDesafiado.getNombre());
        } else if (this.jugadorDesafiado.getPersonaje().getSalud() == 0) {
            this.ganador = this.jugadorDesafiante;
            terminalTexto.info("El ganador es: " + this.jugadorDesafiante.getNombre());
        } else  {
            this.ganador = null;
            terminalTexto.info("El desafío ha terminado en empate");
        }
    }

    private void ataqueDesafiante(int ataqueDesafiante, int defensaDesafiado, int saludEsbirrosDesafiante,
                                  int saludEsbirrosDesafiado){
        int res;
        res = ataqueDesafiante - defensaDesafiado;
        if(res >= 0){
            terminalTexto.info("Ataque [" + this.jugadorDesafiante.getNombre() + "] exitoso");
            if (saludEsbirrosDesafiado > 0){
                saludEsbirrosDesafiado--;
                this.jugadorDesafiado.getPersonaje().getConjuntoEsbirros().setSalud(saludEsbirrosDesafiado);
            } else {
                this.jugadorDesafiado.getPersonaje().setSalud(this.jugadorDesafiado.getPersonaje().getSalud() - 1);
            }
        } else if(res < 0){
            terminalTexto.info("Ataque [" + this.jugadorDesafiado.getNombre() + "] exitoso");
            if (saludEsbirrosDesafiante > 0){
                saludEsbirrosDesafiante--;
                this.jugadorDesafiante.getPersonaje().getConjuntoEsbirros().setSalud(saludEsbirrosDesafiante);
            } else {
                this.jugadorDesafiante.getPersonaje().setSalud(this.jugadorDesafiante.getPersonaje().getSalud() - 1);
            }
        }
    }

    private void ataqueDesafiado(int ataqueDesafiado, int defensaDesafiante,
                                  int saludEsbirrosDesafiante, int saludEsbirrosDesafiado){
        int res;
        res = ataqueDesafiado - defensaDesafiante;
        if(res >= 0){
            terminalTexto.info("Ataque [" + this.jugadorDesafiado.getNombre() + "] exitoso");
            if (saludEsbirrosDesafiante > 0){
                saludEsbirrosDesafiante--;
                this.jugadorDesafiante.getPersonaje().getConjuntoEsbirros().setSalud(saludEsbirrosDesafiante);
            } else {
                this.jugadorDesafiante.getPersonaje().setSalud(this.jugadorDesafiante.getPersonaje().getSalud() - 1);
            }
        } else if(res < 0){
            terminalTexto.info("Ataque [" + this.jugadorDesafiante.getNombre() + "] exitoso");
            if (saludEsbirrosDesafiado > 0){
                saludEsbirrosDesafiado--;
                this.jugadorDesafiado.getPersonaje().getConjuntoEsbirros().setSalud(saludEsbirrosDesafiado);
            } else {
                this.jugadorDesafiado.getPersonaje().setSalud(this.jugadorDesafiado.getPersonaje().getSalud() - 1);
            }
        }
    }

    public void mostrarDesafio(){
        terminalTexto.info("Desafío: " + this.id);
        terminalTexto.info("Oro Apostado: " + this.oroApostado);
        terminalTexto.info("Jugador Desafiado: " + this.jugadorDesafiado.getNombre());
        terminalTexto.info("Jugador Desafiante: " + this.jugadorDesafiante.getNombre());
    }

    private int calcularPotencialAtaque(Jugador jugador){
        int potencial = 0;
        int potencialDevuelto = 0;
        if (jugador.getPersonaje() instanceof Licantropo){
            potencial =  ((Licantropo) jugador.getPersonaje()).calcularAtaque();
        } else if (jugador.getPersonaje() instanceof Vampiro){
            potencial = ((Vampiro) jugador.getPersonaje()).calcularAtaque();
        } else if (jugador.getPersonaje() instanceof Cazador){
            potencial = ((Cazador) jugador.getPersonaje()).calcularAtaque();
        }
        for(int i = 0; i < potencial; i++){
            if (rand.nextInt(6) + 1 >= 5){
                potencialDevuelto++;
            }
        }
        return potencialDevuelto;
    }

    private int calcularPotencialDefensa(Jugador jugador){
        int potencial = 0;
        int potencialDevuelto = 0;
        if (jugador.getPersonaje() instanceof Licantropo){
            potencial = ((Licantropo) jugador.getPersonaje()).calcularDefensa();
        } else if (jugador.getPersonaje() instanceof Vampiro){
            potencial = ((Vampiro) jugador.getPersonaje()).calcularDefensa();
        } else if (jugador.getPersonaje() instanceof Cazador){
            potencial = ((Cazador) jugador.getPersonaje()).calcularDefensa();
        }
        for(int i = 0; i < potencial; i++){
            if (rand.nextInt(6) + 1 >= 5){
                potencialDevuelto++;
            }
        }
        return potencialDevuelto;
    }

    private int calcularSaludEsbirros(Jugador jugador){
        int saludEsbirros;
        if(jugador.getPersonaje().getConjuntoEsbirros() == null){
            return 0;
        } else {
            saludEsbirros = jugador.getPersonaje().getConjuntoEsbirros().getSalud();
            if (jugador.getPersonaje().getConjuntoEsbirros() instanceof Demonio) {
                if (((Demonio) jugador.getPersonaje().getConjuntoEsbirros()).getConjuntoEsbirros() != null) {
                    saludEsbirros += ((Demonio) jugador.getPersonaje().getConjuntoEsbirros()).getConjuntoEsbirros().getSalud();
                }
            }
        }
        return saludEsbirros;
    }
}
