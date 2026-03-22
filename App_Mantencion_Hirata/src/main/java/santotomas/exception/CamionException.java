package santotomas.exception;

/**
 * Excepción personalizada utilizada para manejar errores relacionados con la clase Camion
 * @authors Sebastián Pérez, Fabian Cares, Ayleen Martínez, Catherine Gomez 
 * @version (2026.03.15 - 1.0)
 */
public class CamionException extends Exception
{
    /**
     * Constructor con mensaje.
     * @param mensaje mensaje de error
     */
    public CamionException(String mensaje)
    {
        super(mensaje);
    }
}