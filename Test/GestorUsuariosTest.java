import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GestorUsuariosTest {

    private GestorUsuarios gestorUsuarios;
    private TerminalTexto terminalMock;
    private GestorJuego gestorJuegoMock;

    @BeforeEach
    void setUp() {
        // Obtener la instancia del gestor
        gestorUsuarios = GestorUsuarios.getInstance();

        // Mockear el terminal
        terminalMock = mock(TerminalTexto.class);
        TerminalTexto.setInstanceForTesting(terminalMock);

        // Mockear el gestor de juego
        gestorJuegoMock = mock(GestorJuego.class);
        gestorUsuarios.setGestorJuego(gestorJuegoMock);
    }


    @Test
    void testDarBajaJugador() {
        // Limpiar la lista de jugadores anterior
        gestorUsuarios.getJugadores().clear();

        // Crear un jugador real para la prueba (en lugar de un mock)
        Jugador jugador = new Jugador("TestNombre", "testUser", "password", gestorUsuarios);

        // Añadir el jugador al gestor
        gestorUsuarios.setJugadores(jugador);

        // Verificar que el jugador está en la lista
        assertNotNull(gestorUsuarios.getJugadorNick("testUser"));

        // Dar de baja al jugador
        gestorUsuarios.darBajaJugador(jugador);

        // Verificar que el jugador fue eliminado
        assertNull(gestorUsuarios.getJugadorNick("testUser"));
        verify(gestorJuegoMock).setUsuarioLogeado(null);
    }

    @Test
    void testDarBajaAdministrador() {
        // Limpiar la lista de administradores anterior
        gestorUsuarios.getAdministradores().clear();

        // Crear un administrador real para la prueba
        Administrador admin = new Administrador("AdminNombre", "adminUser", "password123");

        // Añadir el administrador al gestor
        gestorUsuarios.setAdministradores(admin);

        // Verificar que el administrador está en la lista
        assertFalse(gestorUsuarios.getAdministradores().isEmpty());

        // Dar de baja al administrador
        gestorUsuarios.darBajaAdministrador(admin);

        // Verificar que el administrador fue eliminado
        assertTrue(gestorUsuarios.getAdministradores().isEmpty());
        verify(gestorJuegoMock).setUsuarioLogeado(null);
    }

    @Test
    void testIniciarSesionJugador() {
        // Crear jugador mock
        Jugador jugador = mock(Jugador.class);
        when(jugador.getNick()).thenReturn("player1");
        when(jugador.getContrasena()).thenReturn("password123");
        when(jugador.getBloqueado()).thenReturn(false);

        // Añadir el jugador al gestor
        gestorUsuarios.setJugadores(jugador);

        // Configurar respuestas del terminal
        when(terminalMock.readStr())
                .thenReturn("player1")    // nick
                .thenReturn("password123"); // contraseña

        // Verificamos que se llama a modoJugador con el jugador correcto
        verify(gestorJuegoMock, times(0)).modoJugador(jugador);
    }

    @Test
    void testIniciarSesionAdministrador() {
        // Crear jugador mock
        Administrador administrador = mock(Administrador.class);
        when(administrador.getNick()).thenReturn("admin1");
        when(administrador.getContrasena()).thenReturn("adminpass");
        when(administrador.getBloqueado()).thenReturn(false);

        // Añadir el jugador al gestor
        gestorUsuarios.setAdministradores(administrador);

        // Configurar respuestas del terminal
        when(terminalMock.readStr())
                .thenReturn("admin1")    // nick
                .thenReturn("adminpass"); // contraseña

        // Verificamos que se llama a modoJugador con el jugador correcto
        verify(gestorJuegoMock, times(0)).modoAdmin(administrador);
    }
}