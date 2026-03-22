package santotomas.datos;
import santotomas.db.DataBase;
import santotomas.beans.Camion;
import santotomas.exception.CamionException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase CamionDAO (Data Access Object) para la gestión de objetos Camion.
 * Esta clase proporciona los métodos necesarios para realizar operaciones CRUD y una conexión a base de datos.
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
     * Valida la existencia de una Camion en la lista de camiones utilizando su ID de patente.
     * @param patente Representa el ID único del camión a buscar.
     */
    public boolean ValidarExistencia(String patente) {
    boolean existe = false;
    for(Camion camion : listaCamion) {
        if( camion.getPatente() == patente) {
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
     * Se busca el nombre exacto de la patente para validar que no se encuentre registrado.
     * @throws CamionException Se utiliza si el método falla al no encontrar la camión.
     * @throws SQLException Si ocurre un error al acceder o interactuar con la base de datos.     
     */
    public Camion buscarPatente(String patente) throws CamionException, SQLException {
        Camion camion = null;        
        if (patente != null && patente.trim().length() > 0) {
            abrirConexion();
            String sql = "select patente, modelo, anio, kmAcumulado from Camion where UPPER(patente) = (?)";

            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, patente.trim().toUpperCase());

                try (ResultSet rs = ps.executeQuery()) {

                    while (rs.next()) {
                        camion = new Camion(
                                rs.getString("patente"),
                                rs.getString("modelo"),
                                rs.getInt("anio"),
                                rs.getInt("kmAcumulado")
                        );
                        break;
                    }
                }
            } finally {
                cerrarConexion();
            }
            return camion;
        } else {
            throw new CamionException("Debe ingresar la patente del camión.");
        }
    }
    
    
    /**
     * Agrega una nueva Camion a la lista de almacenamiento, si su ID no existe previamente.
     * Se verifica que el ID del Camion no esté ya registrado en la lista de camiones.
     * @throws CamionException Si el ID de la camión ya se encuentra registrado en la lista.
     */
    public void agregarCamion(Camion camion) throws CamionException, SQLException {
        int cantidadIngresada = 0;
        Camion camionExiste = this.buscarPatente(camion.getPatente());
        
        if(camionExiste == null)
        {
            abrirConexion();
            String sql = "insert into camion (modelo, anio, kmAcumulado) ";
            sql += "VALUES((?), (?), (?))";
            try(PreparedStatement ps = con.prepareStatement(sql))
            {
                ps.setString(1, camion.getModelo());
                ps.setInt(2, camion.getAnio());
                ps.setInt(3, camion.getKmAcumulado());
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
            throw new CamionException("Camión ya registrado.");
        }  
    }      

    
    /**
     * Recupera y devuelve todos los objetos Camion almacenados en la base de datos.
     * Este método establece una conexión a la Base de Datos y ejecuta una consulta SQL.
     * @throws CamionException Si ocurre un error específico de la lógica del código
     * @throws SQLException Si ocurre un error al acceder, leer o interactuar con la base de datos.     
     */
    public ArrayList<Camion> ListarCamion() throws CamionException, SQLException {
        abrirConexion();
        ArrayList<Camion> listaCamion = new ArrayList<>();
        String sql = "select patente, modelo, anio, kmAcumulado from camion";
        try (
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
            )
        {
            Camion camion = null;
            while(rs.next())
            {
                camion = new Camion( 
                        rs.getString("patente"),
                        rs.getString("modelo"),
                        rs.getInt("anio"),
                        rs.getInt("kmAcumulado")
                        );
                listaCamion.add(camion);
            }
        }
        finally
        {
            cerrarConexion();
        }
        return listaCamion;
    }   
    
    
    /**
     * Busca y recupera un objeto Camion de la base de datos utilizando su ID.
     * Si se encuentra una camion, este es devuelto.
     * Si no se encuentra ningun camion con el ID, se devuelve una excepción.
     * @param patente que contiene el ID del camión a buscar.
     * @return El objeto Camion encontrado que coincide con el ID.
     * @throws CamionException Si el ID ingresado es nulo/vacío, o si no se encuentra ningun camión con el ID.
     * @throws SQLException Si ocurre un error al acceder o interactuar con la base de datos.
     */
    public Camion buscarCamion(String patente) throws CamionException, SQLException {
        Camion camionEncontrado = null;
        if(patente == null) 
        {
            abrirConexion();
            String sql = "select patente, modelo, anio, kmAcumulado from camion where patente = (?)";
            
            try(PreparedStatement ps = con.prepareStatement(sql))
            {
                ps.setString(1, patente);    
                try( ResultSet rs = ps.executeQuery() )
                {
                    while(rs.next())
                    {
                        camionEncontrado = new Camion( 
                                rs.getString("patente"),
                                rs.getString("modelo"),
                                rs.getInt("anio"),
                                rs.getInt("kmAcumulado")
                                );
                        break;
                    }
                }
            }
            finally
            {
                cerrarConexion();
            }

            if(camionEncontrado == null)
            {
                throw new CamionException("Camion no encontrado con el ID: " + patente);
            }
            return camionEncontrado;
        } 
        else 
        {
            throw new CamionException("Debe ingresar una patente."); 
        } 
    }
    
    
    /**
     * Elimina un camión de la base de datos utilizando su ID.
     * @throws CamionException Se utiliza si el método falla al no encontrar el camión.
     * @throws SQLException Si ocurre un error al acceder o interactuar con la base de datos.
     */ 
    public boolean eliminarCamion(String patente) throws CamionException, SQLException
    {
        boolean seElimino = false;
        Camion camionEncontrado = null;
        int resultado = 0;
        
        camionEncontrado = this.buscarPatente(patente);
        if(camionEncontrado != null)
        {
            abrirConexion();
            String sql = "delete from camion where patente = (?)";
            try(PreparedStatement ps = con.prepareStatement(sql))
            {
                ps.setString(1, patente);
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
     * Modifica los atributos de un camión buscando su primary key (patente)
     * @throws CamionException Se utiliza si el método falla al no encontrar el camión.
     * @throws SQLException Si ocurre un error al acceder o interactuar con la base de datos.
     */
    public boolean modificarCamion(Camion camion) throws CamionException, SQLException {
        boolean seModifico = false;
        int filasAfectadas = 0;
        Camion camionBuscado = this.buscarCamion(camion.getPatente());
        if(camionBuscado != null) {
            abrirConexion();
            String sql = "update camion ";
            sql += "set modelo = (?), anio = (?), kmAcumulado = (?)";
            sql += "where patente = (?)";
            try(PreparedStatement ps = con.prepareStatement(sql))
            {
                ps.setString(1, camion.getModelo());
                ps.setInt(2, camion.getAnio());
                ps.setInt(3, camion.getKmAcumulado());
                ps.setString(4, camion.getPatente());
                
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
        throw new CamionException("El camión no fue encontrado");
    }
    return seModifico;
    }
}