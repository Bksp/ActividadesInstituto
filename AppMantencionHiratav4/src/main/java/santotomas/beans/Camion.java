package santotomas.beans;
import santotomas.exception.CamionException;

/**
 * Clase asociada a Camion, que contiene los atributos de:
 * patente, modelo, anio, km_acumulado
 * Permite la creación de instancias y validación de atributos.
 * @authors Sebastián Pérez, Fabian Cares, Ayleen Martínez, Catherine Gomez
 * @version (2026.03.18 - 2.0)
 */
public class Camion
{
    private String patente;
    private String modelo;
    private int anio;
    private int kmAcumulado;
    
    /**
     * Constructor por defecto.
     */
    public Camion()
    {
        this.patente = this.modelo = new String();
        this.anio = this.kmAcumulado = 0;
    }

    /**
     * Constructor con parámetros.
     * @param patente Identificador del camión.
     * @param modelo Modelo del camión.
     * @param anio Año del camión.
     * @param kmAcumulado Kilometraje total del camión.
     * @throws CamionException si alguno de los valores no cumple con los requisitos.
     */
    public Camion(String patente, String modelo, int anio, int kmAcumulado) throws CamionException
    {
        this();
        this.setPatente(patente);
        this.setModelo(modelo);
        this.setAnio(anio);
        this.setKmAcumulado(kmAcumulado);
    }

    /**
     * Establece la patente del camión.
     * @param patente patente del camión
     * @throws CamionException si la patente es nula o vacía
     */
    public void setPatente(String patente) throws CamionException
    {
        if (patente != null && patente.trim().length() > 0)
        {
            this.patente = patente.trim();
        }
        else
        {
            throw new CamionException("Debe ingresar la patente del camión.");
        }
    }

     /**
     * Establece el modelo del camión.
     * @param modelo patente del camión
     * @throws CamionException si la patente es nula o vacía
     */
    public void setModelo(String modelo) throws CamionException
    {
        if (modelo != null && modelo.trim().length() > 0)
        {
            this.modelo = modelo.trim();
        }
        else
        {
            throw new CamionException("Debe ingresar el modelo del camión.");
        }
    }
    
     /**
     * Establece el año del camión.
     * @param anio Año del camión
     * @throws CamionException si la patente es nula o vacía
     */
    public void setAnio(int anio) throws CamionException
    {
        if (anio < 2000 || anio > 2026) {
            throw new CamionException("Debe ingresar el año, no puede ser menor a 2000 y superior a 2026.");
        }
        this.anio = anio;
    }
    
    /**
     * Establece el kilometraje acumulado del camión.
     * @param kmAcumulado kilometraje acumulado del camión.
     * @throws CamionException si el kilometraje es menor que 0
     */
    public void setKmAcumulado(int kmAcumulado) throws CamionException
    {
        if (kmAcumulado >= 0 && kmAcumulado <= 99999) {
            this.kmAcumulado = kmAcumulado;
        } else {
            throw new CamionException("El kilometraje del camión no puede ser negativo o 0.");
        }
    }

    /**
     * Retorna la patente del camión.
     * @return patente del camión
     */
    public String getPatente()
    {
        return this.patente;
    }
    
    /**
     * Retorna el modelo del camión.
     * @return modelo del camión
     */
    public String getModelo()
    {
        return this.modelo;
    }
    
    /**
     * Retorna el año del camión.
     * @return año del camión
     */
    public int getAnio()
    {
        return this.anio;
    }   
    
    /**
     * Retorna el kilometraje del camión.
     * @return kilometraje del camión
     */
    public int getKmAcumulado()
    {
        return this.kmAcumulado;
    }
}