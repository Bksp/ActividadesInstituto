package mantencionHirata.datos;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import mantencionHirata.beans.Camion;
import mantencionHirata.exception.CamionException;
import java.util.ArrayList;
import java.util.List;
import mantencionHirata.db.DataBase;

/**
 * Clase CamionDAO (Data Access Object) para la gestión de objetos Camion.
 * Esta clase proporciona los métodos necesarios para realizar operaciones CRUD (Crear, Leer, Actualizar, Eliminar) y una conexión a base de datos.
 * @authors Sebastián Pérez, Fabian Cares, Ayleen Martínez, Catherine Gomez
 * @version (2026.03.18 - 2.0)
 */
public class CamionDAO {    
    /**
     * Lista para almacenar temporalmente los objetos Camion.
     * Simula una base de datos en memoria para la persistencia de los datos.
     */
    private static ArrayList<Camion> listaCamion;
    
    /**
     * Conexión a Base de Datos.
     * Se utiliza para gestionar la comunicación con una base de datos relacional.
     */
    private Connection con;
    
    /**
     * Constructor por defecto de la clase CamionDAO.
     * Su función principal es asegurar que la lista de Camion exista en memoria.
     * Si la listaCamion es nula, se inicializa como un nuevo.
     */
    public CamionDAO() {
        if(listaCamion == null) 
        {
            listaCamion = new ArrayList();
        }
    }
    
    /**
     * Valida la existencia de un Camión en la lista de camiones utilizando su Patente.
     * @param patente Representa el ID único del camión a buscar.
     */
    public boolean ValidarExistencia(String patente) {
    boolean existe = false;
    for(Patente patente : listaCamion) {
        if( patente.getPatente() == patente) {
            existe = true;
            break;
            }
        }
        return existe;
    }
    
    /**
     * Abre la conexión con la base de Datos, si no está abierta.
     * @throws SQLException 
     */
    private void abrirConexion() throws SQLException {
        if (con == null || con.isClosed()) 
        {
            con = DataBase.getConnection();
        }
    }

    /**
     * Cierra la conexión con la Base de datos, si está abierta.
     */
    private void cerrarConexion() {
        try {
            if (con != null && !con.isClosed()) 
            {
                con.close();
            }
        } catch (SQLException e) 
        {
            System.err.println("Error al cerrar conexión: " + e.getMessage());
        }
    }
    
    
    /**
     * Agrega una nueva Cancion a la lista de almacenamiento, si su ID no existe previamente.
     * Se verifica que el ID de la Cancion no esté ya registrado en la lista de canciones.
     * @throws CamionException Si el ID de la canción ya se encuentra registrado en la lista.
     */
    public void agregarCamion(Camion camion) throws CamionException, SQLException {
        int cantidadIngresada = 0;
        Camion camionExiste = this.buscarPatente(camion.getPatente());
        
        if(camionExiste == null)
        {
            abrirConexion();
            String sql = "insert into camion (Patente, Modelo, FechaMantencion, Kilometraje, Conductor) ";
            sql += "VALUES((?), (?), (?), (?), (?)";
            try(PreparedStatement ps = con.prepareStatement(sql))
            {
                ps.setString(1, camion.getPatente());
                ps.setString(2, camion.getModelo());
                ps.setString(3, camion.getFechaMantencion());
                ps.setInt(4, camion.getKilometraje());
                ps.setString(5, camion.getConductor());
                ps.setString(cantidadIngresada, sql);
                cantidadIngresada = ps.executeUpdate();
                if(cantidadIngresada < 1)
                {
                    throw new CamionException("Camión no fue registrado.");
                }
            }
            finally
            {
                cerrarConexion();
            }
        }
        else
        {
            throw new CamionException("Camion ya registrado.");
        }  
    }      

    
    /**
     * Recupera y devuelve todos los objetos Cancion almacenados en la base de datos.
     * Este método establece una conexión a la Base de Datos y ejecuta una consulta SQL.
     * @throws CancionException Si ocurre un error específico de la lógica del código
     * @throws SQLException Si ocurre un error al acceder, leer o interactuar con la base de datos.     
     */
    public ArrayList<Camion> ListarCamion() throws CamionException, SQLException {
        abrirConexion();
        ArrayList<Camion> listaCamion = new ArrayList<>();
        String sql = "select id, nombre, duracion from cancion";
        try (
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
            )
        {
            Cancion cancion = null;
            while(rs.next())
            {
                cancion = new Cancion( 
                        rs.getInt("id"), 
                        rs.getString("nombre"),
                        rs.getInt("duracion")
                        );
                listaCancion.add(cancion);
            }
        }
        finally
        {
            cerrarConexion();
        }
        return listaCancion;
    }   
    
    
    /**
     * Busca y recupera un objeto Cancion de la base de datos utilizando su ID.
     * Si se encuentra una cancion, este es devuelto.
     * Si no se encuentra ninguna cancion con el ID, se devuelve una excepción.
     * @param idCancion que contiene el ID de la canción a buscar.
     * @return El objeto Cancion encontrado que coincide con el ID.
     * @throws CancionException Si el ID ingresado es nulo/vacío, o si no se encuentra ninguna canción con el ID.
     * @throws SQLException Si ocurre un error al acceder o interactuar con la base de datos.
     */
    public Cancion buscarCancion(int idCancion) throws CancionException, SQLException {
        Cancion cancionEncontrada = null;
        if(idCancion > 0) 
        {
            abrirConexion();
            String sql = "select patente, modelo, fechaMantencion, kilometraje, conductor from cancion where patente = (?)";
            
            try(PreparedStatement ps = con.prepareStatement(sql))
            {
                ps.setInt(1, idCancion);    
                try( ResultSet rs = ps.executeQuery() )
                {
                    while(rs.next())
                    {
                        cancionEncontrada = new Cancion( 
                                rs.getInt("id"),
                                rs.getString("nombre"),
                                rs.getInt("duracion")
                                );
                        break;
                    }
                }
            }
            finally
            {
                cerrarConexion();
            }

            if(cancionEncontrada == null)
            {
                throw new CancionException("Canción no encontrada con el ID: " + idCancion);
            }
            return cancionEncontrada;
        } 
        else 
        {
            throw new CancionException("Debe ingresar un ID mayor que 0."); 
        } 
    }
    
    
    /**
     * Elimina una canción de la base de datos utilizando su ID.
     * @throws CancionException Se utiliza si el método falla al no encontrar la canción.
     * @throws SQLException Si ocurre un error al acceder o interactuar con la base de datos.
     */ 
    public boolean eliminarCancion(int idCancion) throws CancionException, SQLException
    {
        boolean seElimino = false;
        Cancion cancionEncontrada = null;
        int resultado = 0;
        
        cancionEncontrada = this.buscarCancion(idCancion);
        if(cancionEncontrada != null)
        {
            abrirConexion();
            String sql = "delete from cancion where id = (?)";
            try(PreparedStatement ps = con.prepareStatement(sql))
            {
                ps.setInt(1, idCancion);
                resultado = ps.executeUpdate();
                seElimino = resultado > 0;
            }
            finally
            {
                cerrarConexion();
            }
        }
        return seElimino;
    }
    
    
    /**
     * Busca una canción por su nombre, mostrando diversos resultados.
     * @throws CancionException Se utiliza si el método falla al no encontrar la canción.
     * @throws SQLException Si ocurre un error al acceder o interactuar con la base de datos.
     */
    public ArrayList<Cancion> buscarNombreCancion(String nombre) throws CancionException, SQLException {
        ArrayList<Cancion> cancionEncontrada = new ArrayList<>();

        if (nombre != null && nombre.trim().length() > 0) {
            abrirConexion();
            String sql = "select id, nombre, duracion from cancion where nombre like (?)";

            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, "%" + nombre.trim() + "%");

                try (ResultSet rs = ps.executeQuery()) {

                    while (rs.next()) {
                        Cancion cancion = new Cancion(
                                rs.getInt("id"),
                                rs.getString("nombre"),
                                rs.getInt("duracion")
                        );
                        cancionEncontrada.add(cancion);
                    }
                }
            } finally {
                cerrarConexion();
            }
            if (cancionEncontrada.isEmpty()) {
                throw new CancionException("Canción no encontrada en sistema.");
            }

            return cancionEncontrada;
        } else {
            throw new CancionException("Debe ingresar el nombre de la canción.");
        }
    }
    
    /**
     * Modifica los atributos de la canción buscando su primary key (idCancion)
     * @throws CancionException Se utiliza si el método falla al no encontrar la canción.
     * @throws SQLException Si ocurre un error al acceder o interactuar con la base de datos.
     */
    public boolean modificarCancion(Cancion cancion) throws CancionException, SQLException {
        boolean seModifico = false;
        int filasAfectadas = 0;
        Cancion cancionBuscada = this.buscarCancion(cancion.getIdCancion());
        if(cancionBuscada != null) {
            abrirConexion();
            String sql = "update cancion ";
            sql += "set nombre = (?), duracion = (?) ";
            sql += "where Id = (?)";
            try(PreparedStatement ps = con.prepareStatement(sql))
            {
                ps.setString(1, cancion.getNombreCancion());
                ps.setInt(2, cancion.getDuracionCancion());
                ps.setInt(3, cancion.getIdCancion());
                
                filasAfectadas = ps.executeUpdate();
                seModifico = filasAfectadas > 0;
        }
        finally
        {
            cerrarConexion();
        }
    }
    else
    {
        throw new CancionException("La canción no fue encontrada :c");
    }
    return seModifico;
    }
    
    /**
     * Se busca el nombre exacto de una canción para validar que no se encuentre registrado.
     * @throws CancionException Se utiliza si el método falla al no encontrar la canción.
     * @throws SQLException Si ocurre un error al acceder o interactuar con la base de datos.     
     */
    public Cancion buscarNombreCancionExacto(String nombre) throws CancionException, SQLException {
        Cancion cancion = null;        
        if (nombre != null && nombre.trim().length() > 0) {
            abrirConexion();
            String sql = "select id, nombre, duracion from Cancion where UPPER(nombre) = (?)";

            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, nombre.trim().toUpperCase());

                try (ResultSet rs = ps.executeQuery()) {

                    while (rs.next()) {
                        cancion = new Cancion(
                                rs.getInt("id"),
                                rs.getString("nombre"),
                                rs.getInt("duracion")
                        );
                        break;
                    }
                }
            } finally {
                cerrarConexion();
            }
            return cancion;
        } else {
            throw new CancionException("Debe ingresar el nombre de la canción.");
        }
    }
}