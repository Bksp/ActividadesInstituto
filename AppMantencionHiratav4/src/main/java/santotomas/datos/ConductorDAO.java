package santotomas.datos;
import santotomas.db.DataBase;
import santotomas.beans.Conductor;
import santotomas.exception.ConductorException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase ConductorDAO (Data Access Object) para la gestión de objetos Conductor.
 * Esta clase proporciona los métodos necesarios para realizar operaciones CRUD y una conexión a base de datos.
 * @authors Sebastián Pérez, Fabian Cares, Ayleen Martínez, Catherine Gomez
 * @version (2026.03.18 - 2.0)
 */
public class ConductorDAO {    
    /**
     * Lista para almacenar temporalmente los objetos Conductor.
     * Simula una base de datos en memoria para la persistencia de los datos.
     */
    private static ArrayList<Conductor> listaConductor;
    
    /**
     * Conexión a Base de Datos.
     * Se utiliza para gestionar la comunicación con una base de datos relacional.
     */
    private Connection con;
    
    /**
     * Constructor por defecto de la clase ConductorDAO.
     * Su función principal es asegurar que la lista de Conductor exista en memoria.
     * Si la listaConductor es nula, se inicializa como un nuevo.
     */
    public ConductorDAO() {
        if(listaConductor == null) 
        {
            listaConductor = new ArrayList();
        }
    }
    
    /**
     * Valida la existencia de una Conductor en la lista de conductores utilizando su RUT.
     * @param rut Representa el ID único del conductor a buscar.
     */
    public boolean ValidarExistencia(String rut) {
    boolean existe = false;
    for(Conductor conductor : listaConductor) {
        if( conductor.getRut() == rut) {
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
     * Se busca el RUT para validar que no se encuentre registrado.
     * @throws ConductorException Se utiliza si el método falla al no encontrar el conductor.
     * @throws SQLException Si ocurre un error al acceder o interactuar con la base de datos.     
     */
    public Conductor buscarRut(String rut) throws ConductorException, SQLException {
        Conductor conductor = null;        
        if (rut != null && rut.trim().length() > 0) {
            abrirConexion();
            String sql = "select rut, nombre, edad from Conductor where UPPER(rut) = (?)";

            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, rut.trim().toUpperCase());

                try (ResultSet rs = ps.executeQuery()) {

                    while (rs.next()) {
                        conductor = new Conductor(
                                rs.getString("rut"),
                                rs.getString("nombre"),
                                rs.getInt("edad")
                        );
                        break;
                    }
                }
            } finally {
                cerrarConexion();
            }
            return conductor;
        } else {
            throw new ConductorException("Debe ingresar el RUT del conductor.");
        }
    }
    
    
    /**
     * Agrega una nueva Conductor a la lista de almacenamiento, si su ID no existe previamente.
     * Se verifica que el ID del Conductor no esté ya registrado en la lista de conductores.
     * @throws ConductorException Si el ID del conductor ya se encuentra registrado en la lista.
     */
    public void agregarConductor(Conductor conductor) throws ConductorException, SQLException {
        int cantidadIngresada = 0;
        Conductor conductorExiste = this.buscarRut(conductor.getRut());
        
        if(conductorExiste == null)
        {
            abrirConexion();
            String sql = "insert into conductor (nombre, edad) ";
            sql += "VALUES((?), (?))";
            try(PreparedStatement ps = con.prepareStatement(sql))
            {
                ps.setString(1, conductor.getNombre());
                ps.setInt(2, conductor.getEdad());
                cantidadIngresada = ps.executeUpdate();
                if(cantidadIngresada < 1)
                {
                    throw new ConductorException("Conductor no fue registrado.");
                }
            }
            finally
            {
                cerrarConexion();
            }
        }
        else
        {
            throw new ConductorException("Conductor ya registrado.");
        }  
    }      

    
    /**
     * Recupera y devuelve todos los objetos Conductor almacenados en la base de datos.
     * Este método establece una conexión a la Base de Datos y ejecuta una consulta SQL.
     * @throws ConductorException Si ocurre un error específico de la lógica del código
     * @throws SQLException Si ocurre un error al acceder, leer o interactuar con la base de datos.     
     */
    public ArrayList<Conductor> ListarConductor() throws ConductorException, SQLException {
        abrirConexion();
        ArrayList<Conductor> listaConductor = new ArrayList<>();
        String sql = "select rut, nombre, edad from conductor";
        try (
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
            )
        {
            Conductor conductor = null;
            while(rs.next())
            {
                conductor = new Conductor( 
                        rs.getString("rut"),
                        rs.getString("nombre"),
                        rs.getInt("edad")
                        );
                listaConductor.add(conductor);
            }
        }
        finally
        {
            cerrarConexion();
        }
        return listaConductor;
    }   
    
    
    /**
     * Busca y recupera un objeto Conductor de la base de datos utilizando su ID.
     * Si se encuentra un conductor, este es devuelto.
     * Si no se encuentra ningun conductor con el ID, se devuelve una excepción.
     * @param rut que contiene el ID del conductor a buscar.
     * @return El objeto Conductor encontrado que coincide con el ID.
     * @throws ConductorException Si el ID ingresado es nulo/vacío, o si no se encuentra ningun conductor con el ID.
     * @throws SQLException Si ocurre un error al acceder o interactuar con la base de datos.
     */
    public Conductor buscarConductor(String rut) throws ConductorException, SQLException {
        Conductor conductorEncontrado = null;
        if(rut == null) 
        {
            abrirConexion();
            String sql = "select rut, nombre, edad from conductor where rut = (?)";
            
            try(PreparedStatement ps = con.prepareStatement(sql))
            {
                ps.setString(1, rut);    
                try( ResultSet rs = ps.executeQuery() )
                {
                    while(rs.next())
                    {
                        conductorEncontrado = new Conductor( 
                                rs.getString("rut"),
                                rs.getString("nombre"),
                                rs.getInt("edad")
                                );
                        break;
                    }
                }
            }
            finally
            {
                cerrarConexion();
            }

            if(conductorEncontrado == null)
            {
                throw new ConductorException("Conductor no encontrado con el RUT " + rut);
            }
            return conductorEncontrado;
        } 
        else 
        {
            throw new ConductorException("Debe ingresar un RUT."); 
        } 
    }
    
    
    /**
     * Elimina un conductor de la base de datos utilizando su RUT.
     * @throws ConductorException Se utiliza si el método falla al no encontrar el camión.
     * @throws SQLException Si ocurre un error al acceder o interactuar con la base de datos.
     */ 
    public boolean eliminarConductor(String rut) throws ConductorException, SQLException
    {
        boolean seElimino = false;
        Conductor conductorEncontrado = null;
        int resultado = 0;
        
        conductorEncontrado = this.buscarRut(rut);
        if(conductorEncontrado != null)
        {
            abrirConexion();
            String sql = "delete from conductor where rut = (?)";
            try(PreparedStatement ps = con.prepareStatement(sql))
            {
                ps.setString(1, rut);
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
     * Modifica los atributos de un conductor buscando su primary key (rut)
     * @throws ConductorException Se utiliza si el método falla al no encontrar el conductor.
     * @throws SQLException Si ocurre un error al acceder o interactuar con la base de datos.
     */
    public boolean modificarConductor(Conductor conductor) throws ConductorException, SQLException {
        boolean seModifico = false;
        int filasAfectadas = 0;
        Conductor conductorBuscado = this.buscarConductor(conductor.getRut());
        if(conductorBuscado != null) {
            abrirConexion();
            String sql = "update conductor ";
            sql += "set nombre = (?), edad = (?)";
            sql += "where rut = (?)";
            try(PreparedStatement ps = con.prepareStatement(sql))
            {
                ps.setString(1, conductor.getNombre());
                ps.setInt(2, conductor.getEdad());
                ps.setString(3, conductor.getRut());
                
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
        throw new ConductorException("El conductor no fue encontrado");
    }
    return seModifico;
    }
}