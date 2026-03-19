package mantencionHirata.beans;

import mantencionHirata.exception.CamionException;

/**
 * Clase asociada a Camion, que contiene los atributos de:
 * patenteCamion, nombreConductor, kilometraje.
 * Permite la creación de instancias y validación de atributos.
 */
public class Camion
{
    private String patenteCamion;
    private String nombreConductor;
    private int kilometraje;

    /**
     * Constructor por defecto.
     */
    public Camion()
    {
        this.patenteCamion = "";
        this.nombreConductor = "";
        this.kilometraje = 0;
    }

    /**
     * Constructor con parámetros.
     * @param patenteCamion Identificador del camión.
     * @param nombreConductor Nombre del conductor a cargo del camión.
     * @param kilometraje Kilometraje total del camión.
     * @throws CamionException si alguno de los valores no cumple con los requisitos.
     */
    public Camion(String patenteCamion, String nombreConductor, int kilometraje) throws CamionException
    {
        this();
        this.setPatenteCamion(patenteCamion);
        this.setNombreConductor(nombreConductor);
        this.setKilometraje(kilometraje);
    }

    /**
     * Establece la patente del camión.
     * @param patenteCamion patente del camión
     * @throws CamionException si la patente es nula o vacía
     */
    public void setPatenteCamion(String patenteCamion) throws CamionException
    {
        if (patenteCamion != null && patenteCamion.trim().length() > 0)
        {
            this.patenteCamion = patenteCamion.trim();
        }
        else
        {
            throw new CamionException("Debe ingresar la patente del camión.");
        }
    }

    /**
     * Establece el nombre del conductor.
     * @param nombreConductor nombre del conductor
     * @throws CamionException si el nombre es nulo o vacío
     */
    public void setNombreConductor(String nombreConductor) throws CamionException
    {
        if (nombreConductor != null && nombreConductor.trim().length() > 0)
        {
            this.nombreConductor = nombreConductor.trim();
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
    public String getPatenteCamion()
    {
        return this.patenteCamion;
    }

    /**
     * Retorna el nombre del conductor.
     * @return nombre del conductor
     */
    public String getNombreConductor()
    {
        return this.nombreConductor;
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