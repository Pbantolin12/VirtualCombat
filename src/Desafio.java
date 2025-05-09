import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.List;
import java.util.Random;

public class Desafio implements Serializable {
    //Atributos
    private int id;
    private int oroApostado;
    private Jugador jugadorDesafiado;
    private Jugador jugadorDesafiante;
    private Jugador ganador;
    private boolean desafioAceptado;
    private static final Random random = new Random();
    private transient TerminalTexto terminalTexto = TerminalTexto.getInstance();
    private boolean validado;
    private static final Random rand = new Random();
    private GestorEventos gestorEventos;
    private static final String dCreado = "Desafio_Creado";
    private static final String dAceptado = "Desafio_Aceptado";
    private static final String dValidado = "Desafio_Validado";
    private static final String dTerminado = "Desafio_Terminado";

    //Constructor
    public Desafio(int oroApostado, Jugador jugadorDesafiado, Jugador jugadorDesafiante, List<Administrador> administradores) {
        this.id = generarId();
        this.oroApostado = oroApostado;
        this.jugadorDesafiado = jugadorDesafiado;
        this.jugadorDesafiante = jugadorDesafiante;
        this.ganador = null;
        this.desafioAceptado = false;
        this.validado = false;
        this.gestorEventos = new GestorEventos();
        this.gestorEventos.anadirObservador(dValidado, this.jugadorDesafiado);
        this.gestorEventos.anadirObservador(dCreado, this.jugadorDesafiante);
        this.gestorEventos.anadirObservador(dTerminado, this.jugadorDesafiado);
        this.gestorEventos.anadirObservador(dTerminado, this.jugadorDesafiante);
        this.gestorEventos.anadirObservador(dTerminado, GestorJuego.getInstance().getRanking());
        for (Administrador admin : administradores) {
            this.gestorEventos.anadirObservador(dCreado, admin);
        }
        this.gestorEventos.notificar(dCreado, this);
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

    public Jugador getGanador() {
        return ganador;
    }

    public void setGanador(Jugador ganador) {
        boolean cambio = this.ganador != ganador;
        this.ganador = ganador;
        this.ganador.getPersonaje().setOro(this.oroApostado * 2);
        if (cambio) {
            this.gestorEventos.notificar(dTerminado, this);
        }
    }

    public boolean getDesafioAceptado() {
        return desafioAceptado;
    }

    public void setDesafioAceptado(boolean estado) {
        boolean cambio = this.desafioAceptado != estado;
        this.desafioAceptado = estado;
        if (estado){
            this.jugadorDesafiado.getPersonaje().setOro(this.jugadorDesafiado.getPersonaje().getOro() - this.oroApostado);
        } else {
            this.jugadorDesafiado.getPersonaje().setOro(this.jugadorDesafiado.getPersonaje().getOro() -
                    this.calcularPorcentajeApuestas(this.oroApostado));
            this.jugadorDesafiante.getPersonaje().setOro(this.jugadorDesafiado.getPersonaje().getOro() +
                    this.calcularPorcentajeApuestas(this.oroApostado) + this.oroApostado);
        }
        if (cambio) {
            this.gestorEventos.notificar(dAceptado, this);
        }
    }

    public int calcularPorcentajeApuestas(int apuesta) {
        return (int) (apuesta * 0.1);
    }

    private int generarId() {
        return random.nextInt(1000); // Genera un ID aleatorio entre 0 y 999
    }

    public void setValidado(boolean validado) {
        this.validado = validado;
        this.jugadorDesafiado.setDesafioPendiente(true);
        this.jugadorDesafiado.setDesafio(this);
        this.gestorEventos.notificar(dValidado, this);
    }

    public boolean getValidado() {
        return validado;
    }

    public void iniciarDesafio() {
        int ronda = 1;

        while (this.jugadorDesafiante.getPersonaje().getSalud() != 0 && this.jugadorDesafiado.getPersonaje().getSalud() != 0) {
            int ataqueDesafiante = calcularPotencialAtaque(this.jugadorDesafiante);
            int defensaDesafiado = calcularPotencialDefensa(this.jugadorDesafiado);
            int ataqueDesafiado = calcularPotencialAtaque(this.jugadorDesafiado);
            int defensaDesafiante = calcularPotencialDefensa(this.jugadorDesafiante);
            int saludEsbirrosDesafiante = calcularSaludEsbirros(this.jugadorDesafiante);
            int saludEsbirrosDesafiado = calcularSaludEsbirros(this.jugadorDesafiado);

            terminalTexto.info("EMPIEZA EL COMBATE");
            terminalTexto.showln("|--------RONDA " + ronda++ + "--------|");
            ataqueDesafiante(ataqueDesafiante, defensaDesafiado, saludEsbirrosDesafiante, saludEsbirrosDesafiado);
            ataqueDesafiado(ataqueDesafiado, defensaDesafiante, saludEsbirrosDesafiante, saludEsbirrosDesafiado);
        }
        if (this.jugadorDesafiante.getPersonaje().getSalud() == 0) {
            this.setGanador(this.jugadorDesafiado);
            terminalTexto.info("El ganador es: " + this.jugadorDesafiado.getNombre());
            this.jugadorDesafiado.setPartidasGanadas(this.jugadorDesafiado.getPartidasGanadas() + 1);
        } else if (this.jugadorDesafiado.getPersonaje().getSalud() == 0) {
            this.setGanador(this.jugadorDesafiante);
            terminalTexto.info("El ganador es: " + this.jugadorDesafiante.getNombre());
            this.jugadorDesafiante.setPartidasGanadas(this.jugadorDesafiante.getPartidasGanadas() + 1);
        } else {
            this.ganador = null;
            terminalTexto.info("El desafío ha terminado en empate");
        }
        this.jugadorDesafiante.getPersonaje().setSalud(5);
        this.jugadorDesafiante.getPersonaje().getConjuntoEsbirros().setSalud(3);
        this.jugadorDesafiado.getPersonaje().setSalud(5);
        this.jugadorDesafiado.getPersonaje().getConjuntoEsbirros().setSalud(3);
    }

    private void ataqueDesafiante(int ataqueDesafiante, int defensaDesafiado, int saludEsbirrosDesafiante,
                                  int saludEsbirrosDesafiado) {
        int res;
        res = ataqueDesafiante - defensaDesafiado;
        if (res >= 0) {
            terminalTexto.showln("| Ataque [" + this.jugadorDesafiante.getNombre() + "] --> exitoso");
            if (saludEsbirrosDesafiado > 0) {
                saludEsbirrosDesafiado--;
                this.jugadorDesafiado.getPersonaje().getConjuntoEsbirros().setSalud(saludEsbirrosDesafiado);
            } else {
                this.jugadorDesafiado.getPersonaje().setSalud(this.jugadorDesafiado.getPersonaje().getSalud() - 1);
            }
        } else if (res < 0) {
            terminalTexto.showln("| Ataque [" + this.jugadorDesafiado.getNombre() + "] --> exitoso");
            if (saludEsbirrosDesafiante > 0) {
                saludEsbirrosDesafiante--;
                this.jugadorDesafiante.getPersonaje().getConjuntoEsbirros().setSalud(saludEsbirrosDesafiante);
            } else {
                this.jugadorDesafiante.getPersonaje().setSalud(this.jugadorDesafiante.getPersonaje().getSalud() - 1);
            }
        }
    }

    private void ataqueDesafiado(int ataqueDesafiado, int defensaDesafiante,
                                 int saludEsbirrosDesafiante, int saludEsbirrosDesafiado) {
        int res;
        res = ataqueDesafiado - defensaDesafiante;
        if (res >= 0) {
            terminalTexto.showln("| Ataque [" + this.jugadorDesafiado.getNombre() + "] --> exitoso");
            if (saludEsbirrosDesafiante > 0) {
                saludEsbirrosDesafiante--;
                this.jugadorDesafiante.getPersonaje().getConjuntoEsbirros().setSalud(saludEsbirrosDesafiante);
            } else {
                this.jugadorDesafiante.getPersonaje().setSalud(this.jugadorDesafiante.getPersonaje().getSalud() - 1);
            }
        } else if (res < 0) {
            terminalTexto.showln("| Ataque [" + this.jugadorDesafiante.getNombre() + "] --> exitoso");
            if (saludEsbirrosDesafiado > 0) {
                saludEsbirrosDesafiado--;
                this.jugadorDesafiado.getPersonaje().getConjuntoEsbirros().setSalud(saludEsbirrosDesafiado);
            } else {
                this.jugadorDesafiado.getPersonaje().setSalud(this.jugadorDesafiado.getPersonaje().getSalud() - 1);
            }
        }
    }

    public void mostrarDesafio() {
        terminalTexto.showln("Desafío: " + this.id);
        terminalTexto.showln("Oro Apostado: " + this.oroApostado);
        terminalTexto.showln("Jugador Desafiado: " + this.jugadorDesafiado.getNick());
        terminalTexto.showln("Jugador Desafiante: " + this.jugadorDesafiante.getNick());
    }

    private int calcularPotencialAtaque(Jugador jugador) {
        int potencial = 0;
        int potencialDevuelto = 0;
        if (jugador.getPersonaje() instanceof Licantropo) {
            potencial = ((Licantropo) jugador.getPersonaje()).calcularAtaque();
        } else if (jugador.getPersonaje() instanceof Vampiro) {
            potencial = ((Vampiro) jugador.getPersonaje()).calcularAtaque();
        } else if (jugador.getPersonaje() instanceof Cazador) {
            potencial = ((Cazador) jugador.getPersonaje()).calcularAtaque();
        }
        for (int i = 0; i < potencial; i++) {
            if (rand.nextInt(6) + 1 >= 5) {
                potencialDevuelto++;
            }
        }
        return potencialDevuelto;
    }

    private int calcularPotencialDefensa(Jugador jugador) {
        int potencial = 0;
        int potencialDevuelto = 0;
        if (jugador.getPersonaje() instanceof Licantropo) {
            potencial = ((Licantropo) jugador.getPersonaje()).calcularDefensa();
        } else if (jugador.getPersonaje() instanceof Vampiro) {
            potencial = ((Vampiro) jugador.getPersonaje()).calcularDefensa();
        } else if (jugador.getPersonaje() instanceof Cazador) {
            potencial = ((Cazador) jugador.getPersonaje()).calcularDefensa();
        }
        for (int i = 0; i < potencial; i++) {
            if (rand.nextInt(6) + 1 >= 5) {
                potencialDevuelto++;
            }
        }
        return potencialDevuelto;
    }

    private int calcularSaludEsbirros(Jugador jugador) {
        int saludEsbirros;
        if (jugador.getPersonaje().getConjuntoEsbirros() == null) {
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

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        this.terminalTexto = TerminalTexto.getInstance();
    }
}
