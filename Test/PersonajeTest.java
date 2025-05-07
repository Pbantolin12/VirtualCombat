import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PersonajeTest {

    private Personaje personaje;

    @Mock
    private TerminalTexto terminal;

    @Before
    public void inicializar() {
        MockitoAnnotations.openMocks(this);

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

        // Crear el personaje
        personaje = new Personaje("GuerreroTest");
    }

    @Test
    public void testCreacionPersonaje() {
        // Verifica la correcta inicialización del personaje
        assertEquals("GuerreroTest", personaje.getNombre());
        assertEquals(1000, personaje.getOro());
        assertEquals(5, personaje.getSalud());
        assertEquals(2, personaje.getPoder());

        // Verificar colecciones
        assertNotNull(personaje.getConjuntoArmas());
        assertFalse(personaje.getConjuntoArmas().isEmpty());
        assertNotNull(personaje.getConjuntoArmaduras());
        assertFalse(personaje.getConjuntoArmaduras().isEmpty());

        // Verificar arma y armadura activa
        assertNotNull(personaje.getArmaActiva());
        assertEquals("Espada", personaje.getArmaActiva().getNombre());
        assertNotNull(personaje.getArmaduraActiva());
        assertEquals("Cota de malla", personaje.getArmaduraActiva().getNombre());
    }

    @Test
    public void testFortalezasYDebilidades() {
        // Verificar que se han creado correctamente
        assertNotNull(personaje.getFortalezas());
        assertNotNull(personaje.getDebilidades());

        // Verificar contenido
        assertTrue(personaje.getFortalezas().containsKey("Fuerte"));
        assertEquals(Integer.valueOf(4), personaje.getFortalezas().get("Fuerte"));

        assertTrue(personaje.getDebilidades().containsKey("Lento"));
        assertEquals(Integer.valueOf(3), personaje.getDebilidades().get("Lento"));
    }

    @Test
    public void testEsbirros() {
        // Verificar que se ha creado el esbirro
        assertNotNull(personaje.getConjuntoEsbirros());
        assertEquals("EsbirroTest", personaje.getConjuntoEsbirros().getNombre());

        // Verificar tipo de esbirro
        assertTrue(personaje.getConjuntoEsbirros() instanceof Ghoul);
        Ghoul ghoul = (Ghoul) personaje.getConjuntoEsbirros();
        assertEquals(3, ghoul.getDependencia());
    }

    @Test
    public void testModificarAtributos() {
        // Prueba modificación de atributos básicos
        personaje.setNombre("NuevoNombre");
        assertEquals("NuevoNombre", personaje.getNombre());

        personaje.setOro(2000);
        assertEquals(2000, personaje.getOro());

        personaje.setSalud(10);
        assertEquals(10, personaje.getSalud());

        personaje.setPoder(5);
        assertEquals(5, personaje.getPoder());
    }

    @Test
    public void testArmas() {
        // Verificar búsqueda de armas
        Arma espada = personaje.getArma("Espada");
        assertNotNull(espada);
        assertEquals("Espada", espada.getNombre());

        // Verificar arma inexistente
        assertNull(personaje.getArma("ArmaInventada"));

        // Verificar cambio de arma activa
        Arma hacha = personaje.getArma("Hacha");
        personaje.setArmaActiva(hacha);
        assertEquals(hacha, personaje.getArmaActiva());
    }

    @Test
    public void testArmaduras() {
        // Verificar búsqueda de armaduras
        Armadura cuero = personaje.getArmadura("Cuero");
        assertNotNull(cuero);
        assertEquals("Cuero", cuero.getNombre());

        // Verificar armadura inexistente
        assertNull(personaje.getArmadura("ArmaduraInventada"));

        // Verificar cambio de armadura activa
        Armadura metal = personaje.getArmadura("Metal");
        personaje.setArmaduraActiva(metal);
        assertEquals(metal, personaje.getArmaduraActiva());
    }

    @Test
    public void testEliminarDebilidad() {
        // Verificamos que la debilidad "Lento" existe antes de eliminarla
        assertTrue(personaje.getDebilidades().containsKey("Lento"));

        // Reseteamos el mock para eliminar configuraciones anteriores
        reset(terminal);

        // Configuramos el mock para simular la interacción con el menú de debilidades
        when(terminal.readInt())
                .thenReturn(2)  // Opción 2: Eliminar debilidad
                .thenReturn(4); // Opción 4: Confirmar

        when(terminal.readStr())
                .thenReturn("Lento"); // Nombre de la debilidad a eliminar

        // Ejecutamos el método que utiliza internamente eliminarDebilidad()
        personaje.modificarDebilidades();

        // Verificamos que la debilidad ha sido eliminada
        assertFalse(personaje.getDebilidades().containsKey("Lento"));
        assertTrue(personaje.getDebilidades().isEmpty());
    }

    @Test
    public void testEliminarDebilidadInexistente() {
        // Reseteamos el mock
        reset(terminal);

        // Configuramos el mock para simular la eliminación de una debilidad inexistente
        when(terminal.readInt())
                .thenReturn(2)  // Opción 2: Eliminar debilidad
                .thenReturn(4); // Opción 4: Confirmar

        when(terminal.readStr())
                .thenReturn("DebilidadInexistente"); // Debilidad que no existe

        // Ejecutamos el método
        personaje.modificarDebilidades();

        // Verificamos que se mostró mensaje de error
        verify(terminal).error("La debilidad no existe");

        // La debilidad original sigue existiendo
        assertTrue(personaje.getDebilidades().containsKey("Lento"));
    }

    @Test
    public void testEliminarFortaleza() {
        // Verificamos que la fortaleza "Fuerte" existe antes de eliminarla
        assertTrue(personaje.getFortalezas().containsKey("Fuerte"));

        // Reseteamos el mock
        reset(terminal);

        // Configuramos el mock para simular la interacción con el menú de fortalezas
        when(terminal.readInt())
                .thenReturn(2)  // Opción 2: Eliminar fortaleza
                .thenReturn(4); // Opción 4: Confirmar

        when(terminal.readStr())
                .thenReturn("Fuerte"); // Nombre de la fortaleza a eliminar

        // Ejecutamos el método que utiliza internamente eliminarFortaleza()
        personaje.modificarFortalezas();

        // Verificamos que la fortaleza ha sido eliminada
        assertFalse(personaje.getFortalezas().containsKey("Fuerte"));
        assertTrue(personaje.getFortalezas().isEmpty());
    }

    @Test
    public void testEliminarFortalezaInexistente() {
        // Reseteamos el mock
        reset(terminal);

        // Configuramos el mock para simular la eliminación de una fortaleza inexistente
        when(terminal.readInt())
                .thenReturn(2)  // Opción 2: Eliminar fortaleza
                .thenReturn(4); // Opción 4: Confirmar

        when(terminal.readStr())
                .thenReturn("FortalezaInexistente"); // Fortaleza que no existe

        // Ejecutamos el método
        personaje.modificarFortalezas();

        // Verificamos que se mostró mensaje de error
        verify(terminal).error("La fortaleza no existe");

        // La fortaleza original sigue existiendo
        assertTrue(personaje.getFortalezas().containsKey("Fuerte"));
    }

    @Test
    public void testSetFortalezasActivas() {
        // Verificamos que inicialmente no hay fortalezas activas
        assertTrue(personaje.getFortalezas().containsKey("Fuerte"));

        // Reseteamos el mock
        reset(terminal);

        // Configuramos el mock para activar la fortaleza "Fuerte" y luego salir
        when(terminal.readInt())
                .thenReturn(1)  // Seleccionar la fortaleza "Fuerte"
                .thenReturn(0); // Confirmar/salir

        // Ejecutamos el método
        personaje.setfortalezasActivas();

        // Verificamos que se mostró el estado inicial
        verify(terminal).showln(contains("Fuerte - Valor: 4 --> [inactiva]"));

        // Verificamos que se mostró el mensaje de activación
        verify(terminal).info("Fortaleza [Fuerte] activada");

        // Ejecutamos de nuevo para desactivar
        reset(terminal);
        when(terminal.readInt())
                .thenReturn(1)  // Seleccionar la fortaleza "Fuerte" de nuevo
                .thenReturn(0); // Confirmar/salir

        personaje.setfortalezasActivas();

        // Verificamos que se mostró el estado con la fortaleza activa
        verify(terminal).showln(contains("Fuerte - Valor: 4 --> [activa]"));

        // Verificamos que se mostró el mensaje de desactivación
        verify(terminal).info("Fortaleza [Fuerte] desactivada");
    }

    @Test
    public void testSetDebilidadesActivas() {
        // Verificamos que inicialmente no hay debilidades activas
        assertTrue(personaje.getDebilidades().containsKey("Lento"));

        // Reseteamos el mock
        reset(terminal);

        // Configuramos el mock para activar la debilidad "Lento" y luego salir
        when(terminal.readInt())
                .thenReturn(1)  // Seleccionar la debilidad "Lento"
                .thenReturn(0); // Confirmar/salir

        // Ejecutamos el método
        personaje.setdebilidadesActivas();

        // Verificamos que se mostró el estado inicial
        verify(terminal).showln(contains("Lento - Valor: 3 --> [inactiva]"));

        // Verificamos que se mostró el mensaje de activación
        verify(terminal).info("Debilidad [Lento] activada");

        // Ejecutamos de nuevo para desactivar
        reset(terminal);
        when(terminal.readInt())
                .thenReturn(1)  // Seleccionar la debilidad "Lento" de nuevo
                .thenReturn(0); // Confirmar/salir

        personaje.setdebilidadesActivas();

        // Verificamos que se mostró el estado con la debilidad activa
        verify(terminal).showln(contains("Lento - Valor: 3 --> [activa]"));

        // Verificamos que se mostró el mensaje de desactivación
        verify(terminal).info("Debilidad [Lento] desactivada");
    }
}