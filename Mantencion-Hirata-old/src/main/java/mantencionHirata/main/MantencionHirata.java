package mantencionHirata.main;
import mantencionHirata.gui.FramePrincipal;

/**
 * Clase principal del sistema Disquera
 * @authors Sebastián Pérez, Fabian Cares, Ayleen Martínez, Catherine Gomez
 * @version (2026.03.15 - 1.0)
 */
public class MantencionHirata
{
    /**
     * Punto de entrada de la aplicación.
     * @main Su función es iniciar la interfaz gráfica del programa mediante la clase FramePrincipal.
     * @param args arreglo de cadenas de texto
     */
    public static void main(String[] args) 
    {
        FramePrincipal oFramePrincipal = new FramePrincipal();
        oFramePrincipal.setVisible(true);
    }   
}
