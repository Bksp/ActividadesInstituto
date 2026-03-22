package mantencionHirata.beans;
import mantencionHirata.exception.ConductorException;
import java.util.ArrayList;

/**
 * Clase asociada a Conductor, que contiene los atributos de:
 * RUT, nombre, edad, 
 * @authors Sebastián Pérez, Fabian Cares, Ayleen Martínez, Catherine Gomez
 * @version (2026.03.18 - 2.0)
 */
public class Conductor
{
    // Atributos de clase Conductor
    private String rut;
    private String nombre;
    private int edad;

    /**
     * Constructor por defecto de clase Conductor
     * Inicializa los atributos con valores predeterminados.
     */
    public Conductor()
    {
        this.rut = this.nombre = new String();
        this.edad = 0;
    }
    
    /**
     * Constructor con parámetros para inicializar un disco completo.
     * @param rut ID del Conductor (no puede ser vacío)
     * @param nombre Nombre del conductor (no puede estar vacío)
     * @param edad Edad del conductor (mayor a 0)
     * @throws ConductorException si algún parámetro no cumple con los requisitos
     */
    //Constructor con parametros, iniciar valores por defecto.
    public Conductor(String rut, String nombre, int edad) throws ConductorException
    {
        this();
        this.setRut(rut);
        this.setNombre(nombre);
        this.setEdad(edad);
    }
    
    /**
     * Estable el rut del conductor.
     * @param rut Rut no debe ser vacío.
     * @throws ConductorException si el RUT es vacío.
     */
    public void setRut(String rut) throws ConductorException
    {
        if (rut != null && rut.trim().length() > 0)
        {
            this.rut = rut;
        }
        else
        {
            throw new ConductorException("Querido usuario, debe ingresar el RUT del conductor.");
        }
    }    
    
    /**
     * Establece el nombre del conductor.
     * @param nombre Nombre del conductor.
     * @throws ConductorException si el nombre está vacío.
     */
    public void setNombre(String nombre) throws ConductorException
    {
        if (nombre != null && nombre.trim().length() > 0)
        {
            this.nombre = nombre;
        }
        else
        {
            throw new ConductorException("Querido usuario, debe ingresar el nombre del conductor.");
        }
    }

    /**
     * Establece la edad del conductor.
     * @param edad Edad del conductor.
     * @throws ConductorException si el nombre es nulo o vacío.
     */
    public void setEdad(int edad) throws ConductorException    
    {
        if (edad > 0) 
        {
            this.edad = edad;
        } 
        else 
        {
            throw new ConductorException("Querido usuario, la edad del conductor debe ser mayor a 0.");
        }
    }
        
    /**
     * Retonar el RUT del conductor..
     * @return RUT del conductor.
     */
    public String getRut()
    {
        return this.rut;
    }
    
    /**
     * Retorna el nombre del conductor.
     * @return Nombre del conductor.
     */
    public String getNombre() 
    {
        return this.nombre;
    }
    
    /**
     * Retorna la edad del conductor.
     * @return Edad del conductor.
     */
    public int getEdad()
    {
        return this.edad;
    }
}