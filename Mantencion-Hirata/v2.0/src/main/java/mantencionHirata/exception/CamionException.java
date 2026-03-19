package mantencionHirata.exception;

/**
 * Excepción personalizada para errores relacionados con Camion.
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