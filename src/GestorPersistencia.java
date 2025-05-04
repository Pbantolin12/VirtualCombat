import java.io.*;

public class GestorPersistencia {
    private static final String CONFIG_DIR = "config" + File.separator;
    private static final String GESTOR_USUARIOS_FOLDER = "gestorUsuarios";

    // Verifica que exista la carpeta principal y la de GestorUsuarios; si no, las crea.
    private static void verificarCarpetas() {
        File config = new File(CONFIG_DIR);
        if (!config.exists() && !config.mkdirs()) {
            System.err.println("No se pudo crear la carpeta principal: " + CONFIG_DIR);
        }

        File carpetaGestorUsuarios = new File(CONFIG_DIR + GESTOR_USUARIOS_FOLDER);
        if (!carpetaGestorUsuarios.exists() && !carpetaGestorUsuarios.mkdirs()) {
            System.err.println("No se pudo crear la carpeta: " + carpetaGestorUsuarios.getPath());
        }
    }

    // Guarda el objeto en la carpeta de GestorUsuarios con el nombre de archivo indicado.
    public static void guardarObjeto(String nombreArchivo, Object objeto) {
        if (!(objeto instanceof Serializable)) {
            throw new IllegalArgumentException("El objeto debe implementar Serializable.");
        }
        verificarCarpetas();
        String ruta = CONFIG_DIR + GESTOR_USUARIOS_FOLDER + File.separator + nombreArchivo;
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ruta))) {
            oos.writeObject(objeto);
            System.out.println("Objeto guardado exitosamente en: " + ruta);
        } catch (IOException e) {
            System.err.println("Error al guardar el objeto en " + ruta + ": " + e.getMessage());
        }
    }

    // Carga y devuelve el objeto desde la carpeta de GestorUsuarios con el nombre de archivo indicado.
    public static Object cargarObjeto(String nombreArchivo) {
        String ruta = CONFIG_DIR + GESTOR_USUARIOS_FOLDER + File.separator + nombreArchivo;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ruta))) {
            return ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error al cargar el objeto de " + ruta + ": " + e.getMessage());
        }
        return null;
    }
}

