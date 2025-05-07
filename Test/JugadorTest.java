import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JugadorTest {

    private Jugador jugador;

    @Mock
    private GestorUsuarios gestorUsuarios;

    @Mock
    private Personaje personaje;

    @Mock
    private GestorJuego gestorJuego;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Primero mockeamos TerminalTexto a nivel estático
        TerminalTexto terminalMock = mock(TerminalTexto.class);
        TerminalTexto.setInstanceForTesting(terminalMock);

        jugador = new Jugador("jugadorPrueba", "jugadorPrueba", "jugador1234", gestorUsuarios);
        jugador.setPersonaje(personaje);

        // Configurar gestorUsuarios
        when(gestorUsuarios.getGestorJuego()).thenReturn(gestorJuego);
    }

    @Test
    void testModificarPersonaje() {
        // Configurar el terminal (que debería ser un singleton)
        TerminalTexto terminalMock = TerminalTexto.getInstance();

        // Configurar respuestas del terminal
        when(terminalMock.readInt())
                .thenReturn(1)    // Opción 1: cambiar nombre
                .thenReturn(5);   // Opción 5: salir (según implementación)

        when(terminalMock.readStr()).thenReturn("NuevoNombre");

        // Ejecutar el método a probar
        jugador.modificarPersonaje();

        // Verificar interacciones
        verify(personaje).setNombre("NuevoNombre");
    }

    @Test
    void testModificarEquipo() {
        // Obtener el terminal singleton
        TerminalTexto terminalMock = TerminalTexto.getInstance();

        // Mocks para arma y armadura
        Arma arma = mock(Arma.class);
        Armadura armadura = mock(Armadura.class);

        // Configurar personaje
        when(personaje.getArmaActiva()).thenReturn(arma);
        when(personaje.getArmaduraActiva()).thenReturn(armadura);

        // Configurar respuestas del terminal
        when(terminalMock.readInt())
                .thenReturn(1)    // Ver arma
                .thenReturn(2)    // Ver armadura
                .thenReturn(5);   // Salir (el valor correcto según implementación)

        // Ejecutar método
        jugador.modificarEquipo();

        // Verificaciones
        verify(arma).mostrarCaracteristicas();
        verify(armadura).mostrarCaracteristicas();
    }

    @Test
    void testDesafiarJugador() {
        // Obtener terminal singleton
        TerminalTexto terminalMock = TerminalTexto.getInstance();

        // 1. Configurar personaje del jugador desafiante con oro suficiente
        when(personaje.getOro()).thenReturn(100);

        // 2. Crear jugador desafiado con su personaje
        Jugador jugadorDesafiado = mock(Jugador.class);
        Personaje personajeDesafiado = mock(Personaje.class);
        when(jugadorDesafiado.getNombre()).thenReturn("JugadorDesafiado");
        when(jugadorDesafiado.getNick()).thenReturn("adversario");
        when(jugadorDesafiado.getDesafioPendiente()).thenReturn(false);
        when(jugadorDesafiado.getPersonaje()).thenReturn(personajeDesafiado);

        // 3. Configurar la búsqueda de jugador por nick
        when(gestorUsuarios.getJugadorNick("adversario")).thenReturn(jugadorDesafiado);

        // 4. Configurar usuario logeado en GestorJuego
        when(gestorJuego.getUsuarioLogeado()).thenReturn(jugador);

        // 5. Configurar entradas del usuario
        when(terminalMock.readStr()).thenReturn("adversario"); // Nick del oponente
        when(terminalMock.readInt()).thenReturn(50);          // Cantidad apostada

        // 6. Ejecutar el método
        jugador.desafiarJugador();

        // 7. Verificaciones
        verify(personaje).setOro(50); // 100 original - 50 apostados
        verify(terminalMock).info(contains("Tu desafío a adversario ha sido enviado"));
    }

    @Test
    void testDesafiarJugadorSinOroSuficiente() {
        // Obtener terminal singleton
        TerminalTexto terminalMock = TerminalTexto.getInstance();

        // 1. Configurar personaje del jugador desafiante con oro suficiente
        when(personaje.getOro()).thenReturn(10);

        // 2. Crear jugador desafiado con su personaje
        Jugador jugadorDesafiado = mock(Jugador.class);
        Personaje personajeDesafiado = mock(Personaje.class);
        when(jugadorDesafiado.getNombre()).thenReturn("JugadorDesafiado");
        when(jugadorDesafiado.getNick()).thenReturn("adversario");
        when(jugadorDesafiado.getDesafioPendiente()).thenReturn(false);
        when(jugadorDesafiado.getPersonaje()).thenReturn(personajeDesafiado);

        // 3. Configurar la búsqueda de jugador por nick
        when(gestorUsuarios.getJugadorNick("adversario")).thenReturn(jugadorDesafiado);

        // 4. Configurar usuario logeado en GestorJuego
        when(gestorJuego.getUsuarioLogeado()).thenReturn(jugador);

        // 5. Configurar entradas del usuario
        when(terminalMock.readStr()).thenReturn("adversario"); // Nick del oponente
        when(terminalMock.readInt()).thenReturn(50);          // Cantidad apostada

        // 6. Ejecutar el método
        jugador.desafiarJugador();

        // 7. Verificaciones
        verify(terminalMock).error(contains("No tienes suficiente oro para realizar la apuesta"));
    }

    @Test
    void testDesafiarASiMismo() {
        // Obtener terminal singleton
        TerminalTexto terminalMock = TerminalTexto.getInstance();

        // 1. Configurar personaje del jugador con oro suficiente
        when(personaje.getOro()).thenReturn(100);

        // 2. No necesitamos mockear jugador.getNick() porque es una instancia real
        // El "jugadorPrueba" ya está establecido en el constructor

        // 3. Configurar la búsqueda de jugador para que devuelva al mismo jugador
        when(gestorUsuarios.getJugadorNick("jugadorPrueba")).thenReturn(jugador);

        // 4. Configurar usuario logeado en GestorJuego
        when(gestorJuego.getUsuarioLogeado()).thenReturn(jugador);

        // 5. Configurar entrada del usuario - se intenta desafiar a sí mismo
        when(terminalMock.readStr()).thenReturn("jugadorPrueba");

        // 6. Ejecutar el método
        jugador.desafiarJugador();

        // 7. Verificaciones
        verify(terminalMock).error(contains("No puedes desafiarte a ti mismo"));

        // Verificar que no se continúa con el proceso de apuesta
        verify(terminalMock, never()).readInt(); // No debe pedir la cantidad a apostar
        verify(personaje, never()).setOro(anyInt()); // No debe modificarse el oro
    }

    @Test
    void testDesafiarJugadorConDesafioPendiente() {
        // Obtener terminal singleton
        TerminalTexto terminalMock = TerminalTexto.getInstance();

        // 1. Configurar personaje del jugador desafiante con oro suficiente
        when(personaje.getOro()).thenReturn(100);

        // 2. Crear jugador desafiado con su personaje y un desafío pendiente
        Jugador jugadorDesafiado = mock(Jugador.class);
        Personaje personajeDesafiado = mock(Personaje.class);
        when(jugadorDesafiado.getNombre()).thenReturn("JugadorDesafiado");
        when(jugadorDesafiado.getNick()).thenReturn("adversario");
        when(jugadorDesafiado.getDesafioPendiente()).thenReturn(true); // Ya tiene un desafío pendiente
        when(jugadorDesafiado.getPersonaje()).thenReturn(personajeDesafiado);

        // 3. Configurar la búsqueda de jugador por nick
        when(gestorUsuarios.getJugadorNick("adversario")).thenReturn(jugadorDesafiado);

        // 4. Configurar usuario logeado en GestorJuego
        when(gestorJuego.getUsuarioLogeado()).thenReturn(jugador);

        // 5. Configurar entrada del usuario
        when(terminalMock.readStr()).thenReturn("adversario"); // Nick del oponente

        // 6. Ejecutar el método
        jugador.desafiarJugador();

        // 7. Verificaciones
        verify(terminalMock).error(contains("ya tiene un desafío pendiente"));

        // Verificar que no se continúa con el proceso de apuesta
        verify(terminalMock, never()).readInt(); // No debe pedir la cantidad a apostar
        verify(personaje, never()).setOro(anyInt()); // No debe modificarse el oro
    }

    @Test
    void testDesafiarJugadorSinPersonaje() {
        // Obtener terminal singleton
        TerminalTexto terminalMock = TerminalTexto.getInstance();

        // 1. Configurar personaje del jugador desafiante con oro suficiente
        when(personaje.getOro()).thenReturn(100);

        // 2. Crear jugador desafiado sin personaje
        Jugador jugadorDesafiado = mock(Jugador.class);
        when(jugadorDesafiado.getNombre()).thenReturn("JugadorDesafiado");
        when(jugadorDesafiado.getNick()).thenReturn("adversario");
        when(jugadorDesafiado.getDesafioPendiente()).thenReturn(false);
        when(jugadorDesafiado.getPersonaje()).thenReturn(null); // No tiene personaje

        // 3. Configurar la búsqueda de jugador por nick
        when(gestorUsuarios.getJugadorNick("adversario")).thenReturn(jugadorDesafiado);

        // 4. Configurar usuario logeado en GestorJuego
        when(gestorJuego.getUsuarioLogeado()).thenReturn(jugador);

        // 5. Configurar entrada del usuario
        when(terminalMock.readStr()).thenReturn("adversario"); // Nick del oponente

        // 6. Ejecutar el método
        jugador.desafiarJugador();

        // 7. Verificaciones
        verify(terminalMock).error(contains("no tiene personaje creado"));

        // Verificar que no se continúa con el proceso de apuesta
        verify(terminalMock, never()).readInt(); // No debe pedir la cantidad a apostar
        verify(personaje, never()).setOro(anyInt()); // No debe modificarse el oro
    }

    @Test
    void testDesafiarJugadorSinPersonajePropio() {
        // Obtener terminal singleton
        TerminalTexto terminalMock = TerminalTexto.getInstance();

        // 1. Configurar que el jugador desafiante NO tiene personaje
        jugador.setPersonaje(null);

        // 2. Crear jugador desafiado con su personaje
        Jugador jugadorDesafiado = mock(Jugador.class);
        Personaje personajeDesafiado = mock(Personaje.class);
        when(jugadorDesafiado.getNombre()).thenReturn("JugadorDesafiado");
        when(jugadorDesafiado.getNick()).thenReturn("adversario");
        when(jugadorDesafiado.getDesafioPendiente()).thenReturn(false);
        when(jugadorDesafiado.getPersonaje()).thenReturn(personajeDesafiado);

        // 3. Configurar la búsqueda de jugador por nick
        when(gestorUsuarios.getJugadorNick("adversario")).thenReturn(jugadorDesafiado);

        // 4. Configurar usuario logeado en GestorJuego
        when(gestorJuego.getUsuarioLogeado()).thenReturn(jugador);

        // 5. Configurar entrada del usuario
        when(terminalMock.readStr()).thenReturn("adversario"); // Nick del oponente

        // 6. Ejecutar el método
        jugador.desafiarJugador();

        // 7. Verificaciones
        verify(terminalMock).error(contains("No tienes un personaje creado"));

        // Verificar que no se continúa con el proceso de apuesta
        verify(terminalMock, never()).readInt(); // No debe pedir la cantidad a apostar
    }

    @Test
    void testAceptarDesafio() {
        // 1. Crear mock para el desafío
        Desafio desafioMock = mock(Desafio.class);

        // 2. Mockear los jugadores involucrados en el desafío
        Jugador jugadorDesafiante = mock(Jugador.class);
        when(desafioMock.getJugadorDesafiante()).thenReturn(jugadorDesafiante);
        when(jugadorDesafiante.getNick()).thenReturn("desafiante");

        // 3. Crear mocks para arma y armadura
        Arma armaMock = mock(Arma.class);
        Armadura armaduraMock = mock(Armadura.class);

        // 4. Configurar el personaje para que devuelva los mocks
        when(personaje.getArmaActiva()).thenReturn(armaMock);
        when(personaje.getArmaduraActiva()).thenReturn(armaduraMock);

        // 5. Configurar el desafío actual del jugador
        jugador.setDesafio(desafioMock);
        jugador.setDesafioPendiente(true);

        // 6. Obtener el terminal singleton mock
        TerminalTexto terminalMock = TerminalTexto.getInstance();

        // 7. Configurar que seleccione directamente la opción 3
        when(terminalMock.readInt()).thenReturn(3);

        // 8. Ejecutar el método
        jugador.aceptarDesafio();

        // 9. Verificaciones
        assertFalse(jugador.getDesafioPendiente());
        verify(desafioMock).setDesafioAceptado(true);
        verify(terminalMock).info(contains("Has aceptado el desafío de desafiante"));
    }

    @Test
    void testRechazarDesafio() {
        // 1. Crear mock para el desafío
        Desafio desafioMock = mock(Desafio.class);

        // 2. Configurar el desafío actual del jugador
        jugador.setDesafio(desafioMock);
        jugador.setDesafioPendiente(true);

        // 3. Ejecutar el método
        jugador.rechazarDesafio();

        // 4. Verificaciones
        assertFalse(jugador.getDesafioPendiente());
        verify(desafioMock).setDesafioAceptado(false);
    }

    @Test
    void testConsultarHistorialPartidas() {
        // 1. Obtener terminal singleton mock
        TerminalTexto terminalMock = TerminalTexto.getInstance();

        // 2. Crear mocks para los desafíos completados
        Desafio desafioMock1 = mock(Desafio.class);
        Desafio desafioMock2 = mock(Desafio.class);

        // 3. Crear mocks para los jugadores ganadores
        Jugador jugadorGanador1 = mock(Jugador.class);
        Jugador jugadorGanador2 = mock(Jugador.class);

        // 4. Configurar los mocks
        when(desafioMock1.getId()).thenReturn(1);
        when(desafioMock1.getGanador()).thenReturn(jugadorGanador1);
        when(desafioMock1.getOroApostado()).thenReturn(50);
        when(jugadorGanador1.getNick()).thenReturn("ganador1");

        when(desafioMock2.getId()).thenReturn(2);
        when(desafioMock2.getGanador()).thenReturn(jugadorGanador2);
        when(desafioMock2.getOroApostado()).thenReturn(100);
        when(jugadorGanador2.getNick()).thenReturn("ganador2");

        // 5. Configurar la lista de desafíos completados del jugador
        List<Desafio> desafiosCompletados = new ArrayList<>();
        desafiosCompletados.add(desafioMock1);
        desafiosCompletados.add(desafioMock2);
        jugador.setDesafiosCompletados(desafiosCompletados);

        // 6. Ejecutar el método
        jugador.consultarHistorialPartidas();

        // 7. Verificaciones
        verify(terminalMock).showln("Historial de partidas:");
        verify(terminalMock).showln(contains("Desafío: 1 - Ganador: ganador1 - Apostado: 50"));
        verify(terminalMock).showln(contains("Desafío: 2 - Ganador: ganador2 - Apostado: 100"));
    }
}