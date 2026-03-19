package mantencionHirata.exception;

/**
 * Excepción personalizada utilizada para manejar errores relacionados con la clase Conductor
 * @author 
 * @version (2026.03.15 - 2.0)
 */
public class ConductorException extends Exception
{
    /**
     * Constructor con parámetros de la Exception ConductorException
     * @param mensaje El mensaje que se emitirá al formulario
     */
    public ConductorException(String mensaje)
    {
        super(mensaje);
    }
}
