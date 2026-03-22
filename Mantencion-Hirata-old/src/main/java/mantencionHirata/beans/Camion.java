package mantencionHirata.beans;
import mantencionHirata.exception.CamionException;

/**
 * Clase asociada a Camion, que contiene los atributos de:
 * patenteCamion, nombreConductor, kilometraje.
 * Permite la creación de instancias y validación de atributos.
 * @authors Sebastián Pérez, Fabian Cares, Ayleen Martínez, Catherine Gomez
 * @version (2026.03.18 - 2.0)
 */
public class Camion
{
    private String patente;
    private String modelo;
    private String fechaMantencion;
    private int kilometraje;
    private String conductor;
    
    /**
     * Constructor por defecto.
     */
    public Camion()
    {
        this.patente = this.modelo = this.fechaMantencion = this.conductor = new String();
        this.kilometraje = 0;
    }

    /**
     * Constructor con parámetros.
     * @param patente Identificador del camión.
     * @param modelo Modelo del camión
     * @param fechaMantencion Ultima fecha de mantención del vehículo.
     * @param kilometraje Kilometraje total del camión.
     * @param conductor Nombre del conductor a cargo del camión.
     * @throws CamionException si alguno de los valores no cumple con los requisitos.
     */
    public Camion(String patente, String modelo, String fechaMantencion, int kilometraje, String conductor) throws CamionException
    {
        this();
        this.setPatente(patente);
        this.setModelo(modelo);
        this.setFechaMantencion(fechaMantencion);
        this.setKilometraje(kilometraje);
        this.setConductor(conductor);
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
     * Establece la fecha de mantención del camión.
     * @param fechaMantencion Fecha de mantención del camión
     * @throws CamionException si la patente es nula o vacía
     */
    public void setFechaMantencion(String fechaMantencion) throws CamionException
    {
        if (fechaMantencion != null && fechaMantencion.trim().length() > 0)
        {
            this.fechaMantencion = fechaMantencion.trim();
        }
        else
        {
            throw new CamionException("Debe ingresar la última fecha de mantención del camión.");
        }
    }
    
    /**
     * Establece el nombre del conductor.
     * @param conductor nombre del conductor
     * @throws CamionException si el nombre es nulo o vacío
     */
    public void setConductor(String conductor) throws CamionException
    {
        if (conductor != null && conductor.trim().length() > 0)
        {
            this.conductor = conductor.trim();
        }
        else
        {
            throw new CamionException("Debe ingresar el nombre del conductor.");
        }
    }

    /**
     * Establece el kilometraje del camión.
     * @param kilometraje kilometraje del camión
     * @throws CamionException si el kilometraje es menor que 0
     */
    public void setKilometraje(int kilometraje) throws CamionException
    {
        if (kilometraje >= 0)
        {
            this.kilometraje = kilometraje;
        }
        else
        {
            throw new CamionException("El kilometraje del camión no puede ser negativo.");
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
     * Retorna la fecha de mantención del camión.
     * @return Fecha de mantención del camión
     */
    public String getFechaMantencion()
    {
        return this.fechaMantencion;
    }

    /**
     * Retorna el nombre del conductor.
     * @return nombre del conductor
     */
    public String getConductor()
    {
        return this.conductor;
    }

    /**
     * Retorna el kilometraje del camión.
     * @return kilometraje del camión
     */
    public int getKilometraje()
    {
        return this.kilometraje;
    }
}