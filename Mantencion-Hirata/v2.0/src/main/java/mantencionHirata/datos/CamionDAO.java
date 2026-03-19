package mantencionHirata.datos;

import java.util.ArrayList;
import mantencionHirata.beans.Camion;
import mantencionHirata.exception.CamionException;

/**
 * Clase CamionDAO para gestionar objetos Camion en memoria.
 * Por ahora no usa base de datos.
 */
public class CamionDAO
{
    private static ArrayList<Camion> listaCamion;

    /**
     * Constructor por defecto.
     */
    public CamionDAO()
    {
        if (listaCamion == null)
        {
            listaCamion = new ArrayList<>();
        }
    }

    /**
     * Valida si existe un camión por su patente.
     * @param patenteCamion patente del camión
     * @return true si existe, false si no
     */
    public boolean validarExistencia(String patenteCamion)
    {
        if (patenteCamion == null || patenteCamion.trim().length() == 0)
        {
            return false;
        }

        for (Camion camion : listaCamion)
        {
            if (camion.getPatenteCamion().equalsIgnoreCase(patenteCamion.trim()))
            {
                return true;
            }
        }

        return false;
    }

    /**
     * Agrega un camión a la lista.
     * @param camion camión a agregar
     * @throws CamionException si el camión es nulo o ya existe
     */
    public void agregarCamion(Camion camion) throws CamionException
    {
        if (camion == null)
        {
            throw new CamionException("El camión no puede ser nulo.");
        }

        if (this.validarExistencia(camion.getPatenteCamion()))
        {
            throw new CamionException("Camión ya registrado.");
        }

        listaCamion.add(camion);
    }

    /**
     * Retorna la lista de camiones.
     * @return lista de camiones
     */
    public ArrayList<Camion> listarCamion()
    {
        return new ArrayList<>(listaCamion);
    }

    /**
     * Busca un camión por patente.
     * @param patenteCamion patente del camión
     * @return objeto Camion encontrado
     * @throws CamionException si la patente es inválida o no se encuentra
     */
    public Camion buscarCamion(String patenteCamion) throws CamionException
    {
        if (patenteCamion == null || patenteCamion.trim().length() == 0)
        {
            throw new CamionException("Debe ingresar una patente válida.");
        }

        for (Camion camion : listaCamion)
        {
            if (camion.getPatenteCamion().equalsIgnoreCase(patenteCamion.trim()))
            {
                return camion;
            }
        }

        throw new CamionException("Camión no encontrado con la patente: " + patenteCamion);
    }

    /**
     * Elimina un camión por patente.
     * @param patenteCamion patente del camión
     * @return true si se eliminó correctamente
     * @throws CamionException si no se encuentra el camión
     */
    public boolean eliminarCamion(String patenteCamion) throws CamionException
    {
        Camion camionEncontrado = this.buscarCamion(patenteCamion);
        return listaCamion.remove(camionEncontrado);
    }

    /**
     * Busca camiones por nombre del conductor.
     * @param nombreConductor nombre o parte del nombre del conductor
     * @return lista de camiones encontrados
     * @throws CamionException si el nombre es inválido o no hay coincidencias
     */
    public ArrayList<Camion> buscarPorNombreConductor(String nombreConductor) throws CamionException
    {
        ArrayList<Camion> camionesEncontrados = new ArrayList<>();

        if (nombreConductor == null || nombreConductor.trim().length() == 0)
        {
            throw new CamionException("Debe ingresar el nombre del conductor.");
        }

        for (Camion camion : listaCamion)
        {
            if (camion.getNombreConductor().toLowerCase().contains(nombreConductor.trim().toLowerCase()))
            {
                camionesEncontrados.add(camion);
            }
        }

        if (camionesEncontrados.isEmpty())
        {
            throw new CamionException("No se encontraron camiones para ese conductor.");
        }

        return camionesEncontrados;
    }

    /**
     * Modifica los datos de un camión existente.
     * La patente se usa como identificador.
     * @param camionModificado objeto con los nuevos datos
     * @return true si se modificó correctamente
     * @throws CamionException si el camión es nulo o no existe
     */
    public boolean modificarCamion(Camion camionModificado) throws CamionException
    {
        if (camionModificado == null)
        {
            throw new CamionException("El camión no puede ser nulo.");
        }

        for (int i = 0; i < listaCamion.size(); i++)
        {
            if (listaCamion.get(i).getPatenteCamion().equalsIgnoreCase(camionModificado.getPatenteCamion()))
            {
                listaCamion.set(i, camionModificado);
                return true;
            }
        }

        throw new CamionException("El camión no fue encontrado.");
    }
}