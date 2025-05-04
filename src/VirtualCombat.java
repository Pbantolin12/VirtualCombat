public class VirtualCombat {
    public static void main(String[] args) {

        GestorUsuarios gestorUsuarios = (GestorUsuarios) GestorPersistencia.cargarObjeto("gestorUsuarios.dat");
        if (gestorUsuarios == null) {
            gestorUsuarios = GestorUsuarios.getInstance();
        }
        gestorUsuarios.iniciar();
        GestorPersistencia.guardarObjeto("gestorUsuarios.dat", gestorUsuarios);
    }
}