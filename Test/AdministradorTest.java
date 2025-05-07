import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AdministradorTest {

    private Administrador administrador;

    @Mock
    private TerminalTexto terminal;

    @Before
    public void inicializar() {
        // Configuramos el mock de TerminalTexto
        TerminalTexto.setInstanceForTesting(terminal);

        // Inicializamos administrador
        administrador = new Administrador("Pedro", "admin", "admin123456");
    }

    @Test
    public void testRegistrarFalloContrasena() {
        // Datos de prueba
        String nombre = "NuevoAdmin";
        String nick = "nuevouser";
        String contrasena = "pass123"; // 7 caracteres (inválida)

        try {
            // Ejecutamos el método de registro con contraseña inválida
            Administrador.registrar(nombre, nick, contrasena);
            fail("Debería haber lanzado una excepción");
        } catch (IllegalArgumentException e) {
            // Verificamos que la excepción contiene el mensaje correcto
            assertEquals("La contraseña debe tener entre 8 y 12 caracteres", e.getMessage());
        }
    }

    @Test
    public void testRegistrar() {
        // Datos de prueba
        String nombre = "NuevoAdmin";
        String nick = "NuevoAdmin12";
        String contrasena = "admin1234";

        // Ejecutamos el método de registro con los parámetros correctos
        Administrador adminRegistrado = Administrador.registrar(nombre, nick, contrasena);

        // Verificamos que el administrador se creó correctamente
        assertNotNull(adminRegistrado);
        assertEquals(nombre, adminRegistrado.getNombre());
        assertEquals(nick, adminRegistrado.getNick());
        assertEquals(contrasena, adminRegistrado.getContrasena());
    }

    @Test
    public void testModificarPersonajeNombre() {
        // Configuramos múltiples respuestas para simular todas las interacciones
        // Para selección de opciones
        when(terminal.readInt())
                // Para elegir arma activa
                .thenReturn(1)
                // Para elegir armadura activa
                .thenReturn(1)
                // Para valor de debilidad
                .thenReturn(3)
                // Para valor de fortaleza
                .thenReturn(4)
                // Para elegir tipo de esbirro (Ghoul)
                .thenReturn(1)
                // Para dependencia del Ghoul
                .thenReturn(3);

        // Para entradas de texto (readStr)
        when(terminal.readStr())
                // Para descripción de debilidad
                .thenReturn("Lento")
                // Para descripción de fortaleza
                .thenReturn("Fuerte")
                // Para nombre del esbirro
                .thenReturn("EsbirroTest");

        // Establecer el mock como instancia de TerminalTexto
        TerminalTexto.setInstanceForTesting(terminal);

        // Crear el personaje de prueba
        Personaje personajePrueba = new Personaje("PresonajePrueba");

        // Configurar respuestas para el terminal
        reset(terminal);
        when(terminal.readInt())
                .thenReturn(1)      // Opción 1: Cambiar nombre
                .thenReturn(8);     // Opción 8: Volver (salir)

        when(terminal.readStr())
                .thenReturn("NuevoNombre");  // Nuevo nombre del personaje

        // Ejecutar el método que estamos probando
        administrador.modificarPersonaje(personajePrueba);

        // Verificar que el nombre del personaje fue modificado
        assertEquals("NuevoNombre", personajePrueba.getNombre());
    }

    @Test
    public void testModificarPersonajeOro() {
        // Configuramos múltiples respuestas para simular todas las interacciones
        // Para selección de opciones
        when(terminal.readInt())
                // Para elegir arma activa
                .thenReturn(1)
                // Para elegir armadura activa
                .thenReturn(1)
                // Para valor de debilidad
                .thenReturn(3)
                // Para valor de fortaleza
                .thenReturn(4)
                // Para elegir tipo de esbirro (Ghoul)
                .thenReturn(1)
                // Para dependencia del Ghoul
                .thenReturn(3);

        // Para entradas de texto (readStr)
        when(terminal.readStr())
                // Para descripción de debilidad
                .thenReturn("Lento")
                // Para descripción de fortaleza
                .thenReturn("Fuerte")
                // Para nombre del esbirro
                .thenReturn("EsbirroTest");

        // Establecer el mock como instancia de TerminalTexto
        TerminalTexto.setInstanceForTesting(terminal);

        // Crear el personaje de prueba
        Personaje personajePrueba = new Personaje("PresonajePrueba");

        // Configurar respuestas para el terminal
        reset(terminal);
        when(terminal.readInt())
                .thenReturn(6)      // Opción 6: Cambiar oro
                .thenReturn(200)    // Nuevo valor de oro
                .thenReturn(8);     // Opción 8: Volver

        // Ejecutar el método
        administrador.modificarPersonaje(personajePrueba);

        // Verificar que el oro del personaje fue modificado
        assertEquals(200, personajePrueba.getOro());
    }

    @Test
    public void testModificarPersonajeSalud() {
        // Configuramos múltiples respuestas para simular todas las interacciones
        when(terminal.readInt())
                .thenReturn(1)  // Para elegir arma activa
                .thenReturn(1)  // Para elegir armadura activa
                .thenReturn(3)  // Para valor de debilidad
                .thenReturn(4)  // Para valor de fortaleza
                .thenReturn(1)  // Para elegir tipo de esbirro (Ghoul)
                .thenReturn(3); // Para dependencia del Ghoul

        when(terminal.readStr())
                .thenReturn("Lento")       // Para descripción de debilidad
                .thenReturn("Fuerte")      // Para descripción de fortaleza
                .thenReturn("EsbirroTest"); // Para nombre del esbirro

        TerminalTexto.setInstanceForTesting(terminal);

        // Crear el personaje de prueba
        Personaje personajePrueba = new Personaje("PersonajePrueba");

        // Configurar respuestas para este test específico
        reset(terminal);
        when(terminal.readInt())
                .thenReturn(7)  // Opción 7: Cambiar salud
                .thenReturn(4)  // Nuevo valor de salud
                .thenReturn(8); // Opción 8: Volver

        // Ejecutar el método
        administrador.modificarPersonaje(personajePrueba);

        // Verificar que la salud del personaje fue modificada
        assertEquals(4, personajePrueba.getSalud());

        // Verificar interacciones con el terminal
        verify(terminal).askInfo("Introduce la nueva salud del personaje: ");
    }

    @Test
    public void testModificarPersonajeEquipo() {
        // Configuramos respuestas para crear el personaje
        when(terminal.readInt())
                .thenReturn(1)  // Para elegir arma activa
                .thenReturn(1)  // Para elegir armadura activa
                .thenReturn(3)  // Para valor de debilidad
                .thenReturn(4)  // Para valor de fortaleza
                .thenReturn(1)  // Para elegir tipo de esbirro
                .thenReturn(3); // Para dependencia del esbirro

        when(terminal.readStr())
                .thenReturn("Lento")       // Debilidad
                .thenReturn("Fuerte")      // Fortaleza
                .thenReturn("EsbirroTest"); // Nombre esbirro

        TerminalTexto.setInstanceForTesting(terminal);

        // Crear personaje de prueba
        Personaje personajePrueba = new Personaje("PersonajePrueba");

        // Configurar respuestas para añadir un arma
        reset(terminal);
        when(terminal.readInt())
                .thenReturn(5)       // Opción 5: Cambiar equipo
                .thenReturn(1)       // Opción 1: Añadir arma
                .thenReturn(2)       // Modificador defensa
                .thenReturn(3)       // Modificador ataque
                .thenReturn(5)       // Volver al menú principal
                .thenReturn(8);      // Opción 8: Volver

        when(terminal.readStr())
                .thenReturn("Espada")        // Nombre del arma
                .thenReturn("UNA_MANO");     // Tipo de arma

        // Ejecutar el método
        administrador.modificarPersonaje(personajePrueba);

        // Verificar que se añadió un arma al personaje
        assertTrue(personajePrueba.getConjuntoArmas().size() > 0);

        // Verificar interacciones con el terminal
        verify(terminal, atLeastOnce()).askInfo("Introduce el nombre del arma: ");
    }

    @Test
    public void testModificarPersonajeDebilidades() {
        // Configuramos respuestas para crear el personaje (mantener esto)
        when(terminal.readInt())
                .thenReturn(1)  // Para elegir arma activa
                .thenReturn(1)  // Para elegir armadura activa
                .thenReturn(3)  // Para valor de debilidad
                .thenReturn(4)  // Para valor de fortaleza
                .thenReturn(1)  // Para elegir tipo de esbirro
                .thenReturn(3); // Para dependencia del esbirro

        when(terminal.readStr())
                .thenReturn("Lento")       // Debilidad
                .thenReturn("Fuerte")      // Fortaleza
                .thenReturn("EsbirroTest"); // Nombre esbirro

        TerminalTexto.setInstanceForTesting(terminal);

        // Crear personaje de prueba
        Personaje personajePrueba = new Personaje("PersonajePrueba");
        int debilidadesIniciales = personajePrueba.getDebilidades().size();

        // Configurar respuestas para el terminal - CORREGIDO
        reset(terminal);
        when(terminal.readInt())
                .thenReturn(3)       // Opción 3: Cambiar debilidades
                .thenReturn(1)       // Opción 1: Añadir debilidad
                .thenReturn(2)       // Valor numérico de debilidad
                .thenReturn(4)       // Opción 4: Volver al menú de modificar debilidades
                .thenReturn(8);      // Opción 8: Volver al menú principal

        when(terminal.readStr())
                .thenReturn("Frágil");     // Descripción de la debilidad

        // Ejecutar el método
        administrador.modificarPersonaje(personajePrueba);

        // Verificamos que se añadió una debilidad
        assertEquals(debilidadesIniciales + 1, personajePrueba.getDebilidades().size());
        assertTrue(personajePrueba.getDebilidades().containsKey("Frágil"));
    }

    @Test
    public void testModificarPersonajeFortalezas() {
        // Configuramos respuestas para crear el personaje (mantener esto)
        when(terminal.readInt())
                .thenReturn(1)  // Para elegir arma activa
                .thenReturn(1)  // Para elegir armadura activa
                .thenReturn(3)  // Para valor de debilidad
                .thenReturn(4)  // Para valor de fortaleza
                .thenReturn(1)  // Para elegir tipo de esbirro
                .thenReturn(3); // Para dependencia del esbirro

        when(terminal.readStr())
                .thenReturn("Lento")       // Debilidad
                .thenReturn("Fuerte")      // Fortaleza
                .thenReturn("EsbirroTest"); // Nombre esbirro

        TerminalTexto.setInstanceForTesting(terminal);

        // Crear personaje de prueba
        Personaje personajePrueba = new Personaje("PersonajePrueba");
        int fortalezasIniciales = personajePrueba.getFortalezas().size();

        // Configurar respuestas para el terminal - CORREGIDO
        reset(terminal);
        when(terminal.readInt())
                .thenReturn(4)       // Opción 4: Cambiar fortalezas
                .thenReturn(1)       // Opción 1: Añadir fortaleza
                .thenReturn(3)       // Valor numérico de fortaleza
                .thenReturn(4)       // Opción 4: Volver al menú de modificar fortalezas
                .thenReturn(8);      // Opción 8: Volver al menú principal

        when(terminal.readStr())
                .thenReturn("Veloz");     // Descripción de la fortaleza

        // Ejecutar el método
        administrador.modificarPersonaje(personajePrueba);

        // Verificamos que se añadió una fortaleza
        assertEquals(fortalezasIniciales + 1, personajePrueba.getFortalezas().size());
        assertTrue(personajePrueba.getFortalezas().containsKey("Veloz"));
    }

    @Test
    public void testAnadirArma() {
        // Configuramos respuestas para crear el personaje (mantener esto)
        when(terminal.readInt())
                .thenReturn(1)  // Para elegir arma activa
                .thenReturn(1)  // Para elegir armadura activa
                .thenReturn(3)  // Para valor de debilidad
                .thenReturn(4)  // Para valor de fortaleza
                .thenReturn(1)  // Para elegir tipo de esbirro
                .thenReturn(3); // Para dependencia del esbirro

        when(terminal.readStr())
                .thenReturn("Lento")       // Debilidad
                .thenReturn("Fuerte")      // Fortaleza
                .thenReturn("EsbirroTest"); // Nombre esbirro

        TerminalTexto.setInstanceForTesting(terminal);

        // Crear personaje de prueba
        Personaje personajePrueba = new Personaje("PersonajePrueba");

        // Establecer el personaje como el que se va a modificar
        reset(terminal);
        when(terminal.readInt()).thenReturn(8); // Salir directamente
        administrador.modificarPersonaje(personajePrueba);

        // Obtener el tamaño inicial del conjunto de armas
        int armasIniciales = personajePrueba.getConjuntoArmas().size();

        // Configurar SOLO las respuestas necesarias
        reset(terminal);
        when(terminal.readInt())
                .thenReturn(1)       // Opción 1: Añadir arma
                .thenReturn(2)       // Modificador defensa
                .thenReturn(3)       // Modificador ataque
                .thenReturn(5);      // Opción 5: Volver

        when(terminal.readStr())
                .thenReturn("Espada")     // Nombre del arma
                .thenReturn("UNA_MANO");  // Tipo de arma

        // Ejecutar el método
        administrador.modificarEquipo();

        // Verificación
        assertEquals(armasIniciales + 1, personajePrueba.getConjuntoArmas().size());
        assertNotNull(personajePrueba.getArma("Espada"));
    }

    @Test
    public void testAnadirArmadura() {
        // Configuramos respuestas para crear el personaje (mantener esto)
        when(terminal.readInt())
                .thenReturn(1)  // Para elegir arma activa
                .thenReturn(1)  // Para elegir armadura activa
                .thenReturn(3)  // Para valor de debilidad
                .thenReturn(4)  // Para valor de fortaleza
                .thenReturn(1)  // Para elegir tipo de esbirro
                .thenReturn(3); // Para dependencia del esbirro

        when(terminal.readStr())
                .thenReturn("Lento")       // Debilidad
                .thenReturn("Fuerte")      // Fortaleza
                .thenReturn("EsbirroTest"); // Nombre esbirro

        TerminalTexto.setInstanceForTesting(terminal);

        // Crear personaje de prueba
        Personaje personajePrueba = new Personaje("PersonajePrueba");

        // Establecer el personaje como el que se va a modificar
        reset(terminal);
        when(terminal.readInt()).thenReturn(8); // Salir directamente
        administrador.modificarPersonaje(personajePrueba);

        // Verificar tamaño inicial del conjunto de armaduras
        int armadurasIniciales = personajePrueba.getConjuntoArmaduras().size();

        // Configurar respuestas SOLO LAS NECESARIAS
        reset(terminal);
        when(terminal.readInt())
                .thenReturn(2)       // Opción 2: Añadir armadura
                .thenReturn(2)       // Modificador defensa
                .thenReturn(1)       // Modificador ataque
                .thenReturn(5);      // Opción 5: Volver

        when(terminal.readStr())
                .thenReturn("Cota de Malla");   // Nombre de la armadura

        // Ejecutar el método
        administrador.modificarEquipo();

        // Verificación simple
        assertTrue("No se añadió la armadura", personajePrueba.getConjuntoArmaduras().size() > armadurasIniciales);
    }

    @Test
    public void testBloquearUsuario() {
        // Crear un mock de Usuario
        Usuario usuarioMock = mock(Usuario.class);
        when(usuarioMock.getNombre()).thenReturn("UsuarioPrueba");
        when(usuarioMock.getBloqueado()).thenReturn(false); // Usuario no bloqueado inicialmente

        // Ejecutar el método para bloquear el usuario
        administrador.bloquearUsuario(usuarioMock);

        // Verificar que se llamó al método setBloqueado con true
        verify(usuarioMock).setBloqueado(true);
        // Verificar que se mostró el mensaje correcto
        verify(terminal).showln("Usuario UsuarioPrueba bloqueado");
    }

    @Test
    public void testBloquearUsuarioYaBloqueado() {
        // Crear un mock de Usuario que ya está bloqueado
        Usuario usuarioMock = mock(Usuario.class);
        when(usuarioMock.getNombre()).thenReturn("UsuarioBloqueado");
        when(usuarioMock.getBloqueado()).thenReturn(true); // Usuario ya bloqueado

        // Ejecutar el método para bloquear el usuario
        administrador.bloquearUsuario(usuarioMock);

        // Verificar que NO se llamó al método setBloqueado
        verify(usuarioMock, never()).setBloqueado(anyBoolean());
        // Verificar que se mostró el mensaje de error
        verify(terminal).error("El usuario UsuarioBloqueadoya está bloqueado");
    }

    @Test
    public void testDesbloquearUsuario() {
        // Crear un mock de Usuario bloqueado
        Usuario usuarioMock = mock(Usuario.class);
        when(usuarioMock.getNombre()).thenReturn("UsuarioBloqueado");
        when(usuarioMock.getBloqueado()).thenReturn(true); // Usuario bloqueado inicialmente

        // Ejecutar el método para desbloquear el usuario
        administrador.desbloquearUsuario(usuarioMock);

        // Verificar que se llamó al método setBloqueado con false
        verify(usuarioMock).setBloqueado(false);
        // Verificar que se mostró el mensaje correcto
        verify(terminal).showln("Usuario UsuarioBloqueado desbloqueado");
    }

    @Test
    public void testDesbloquearUsuarioNoEstaBloqueado() {
        // Crear un mock de Usuario que no está bloqueado
        Usuario usuarioMock = mock(Usuario.class);
        when(usuarioMock.getNombre()).thenReturn("UsuarioNormal");
        when(usuarioMock.getBloqueado()).thenReturn(false); // Usuario no está bloqueado

        // Ejecutar el método para desbloquear el usuario
        administrador.desbloquearUsuario(usuarioMock);

        // Verificar que NO se llamó al método setBloqueado
        verify(usuarioMock, never()).setBloqueado(anyBoolean());
        // Verificar que se mostró el mensaje de error
        verify(terminal).error("El usuario UsuarioNormal no está bloqueado");
    }

    @Test
    public void testEliminarPersonajeExistente() {
        // Crear un mock de Jugador con un personaje
        Jugador jugadorMock = mock(Jugador.class);
        Personaje personajeMock = mock(Personaje.class);
        when(personajeMock.getNombre()).thenReturn("PersonajePrueba");
        when(jugadorMock.getPersonaje()).thenReturn(personajeMock);

        // Ejecutar el método para eliminar el personaje
        administrador.eliminarPersonaje(jugadorMock);

        // Verificar que se llamó al método setPersonaje con null
        verify(jugadorMock).setPersonaje(null);

        // Verificar que se mostró el mensaje correcto
        verify(terminal).showln("Personaje PersonajePrueba eliminado");
    }

    @Test
    public void testEliminarPersonajeInexistente() {
        // Crear un mock de Jugador sin personaje
        Jugador jugadorMock = mock(Jugador.class);
        when(jugadorMock.getPersonaje()).thenReturn(null);

        // Ejecutar el método para eliminar el personaje
        administrador.eliminarPersonaje(jugadorMock);

        // Verificar que NO se llamó al método setPersonaje
        verify(jugadorMock, never()).setPersonaje(any());

        // Verificar que se mostró el mensaje de error
        verify(terminal).error("El personaje no existe");
    }

    @Test
    public void testValidarDesafio() throws Exception {
        // Crear mocks para los jugadores y sus personajes
        Jugador jugadorDesafiante = mock(Jugador.class);
        Jugador jugadorDesafiado = mock(Jugador.class);

        Personaje personajeDesafiante = mock(Personaje.class);
        Personaje personajeDesafiado = mock(Personaje.class);
        when(jugadorDesafiante.getPersonaje()).thenReturn(personajeDesafiante);
        when(jugadorDesafiado.getPersonaje()).thenReturn(personajeDesafiado);

        // Crear mock del desafío entre ambos jugadores
        Desafio desafioMock = mock(Desafio.class);
        when(desafioMock.getJugadorDesafiante()).thenReturn(jugadorDesafiante);
        when(desafioMock.getJugadorDesafiado()).thenReturn(jugadorDesafiado);

        // Configurar GestorUsuarios y GestorJuego mediante reflection
        GestorJuego gestorJuegoMock = mock(GestorJuego.class);
        GestorUsuarios gestorUsuariosMock = mock(GestorUsuarios.class);
        when(gestorUsuariosMock.getGestorJuego()).thenReturn(gestorJuegoMock);

        Field gestorUsuariosField = Administrador.class.getDeclaredField("gestorUsuarios");
        gestorUsuariosField.setAccessible(true);
        gestorUsuariosField.set(administrador, gestorUsuariosMock);

        // Lista de desafíos pendientes
        List<Desafio> desafios = new ArrayList<>();
        desafios.add(desafioMock);

        // Configurar respuestas del terminal para el flujo de validación
        when(terminal.readInt())
                .thenReturn(1)  // Opción 1: Gestionar jugador desafiante
                .thenReturn(2)  // Opción 2: Gestionar jugador desafiado
                .thenReturn(3)  // Opción 3: Confirmar
                .thenReturn(1); // Opción 1: Salir después de validar

        // Ejecutar método
        administrador.validarDesafio(desafios);

        // Verificaciones
        verify(desafioMock).mostrarDesafio();
        verify(personajeDesafiante).setfortalezasActivas();
        verify(personajeDesafiante).setdebilidadesActivas();
        verify(personajeDesafiado).setfortalezasActivas();
        verify(personajeDesafiado).setdebilidadesActivas();
        verify(desafioMock).setValidado(true);
        verify(gestorJuegoMock).setDesafiosPendientes(desafioMock);

        // La lista debería quedar vacía tras procesar el desafío
        assertTrue(desafios.isEmpty());
    }
}