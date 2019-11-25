package dpo2_u2_a3_arcm;

//importacion de librerias
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Ari
 */
public class Conexion {
    //se crea la conexion
    private static Connection conn;    
    
    //configuro driver
    private static final String driver = "com.mysql.cj.jdbc.Driver";
    private static final String user = "root";
    private static final String password = "Arirox81";
    private static final String url = "jdbc:mysql://localhost:3306/restaurantebd?useLegacyDatetimeCode&serverTimezone=UTC";
    
    //para asegurar conexion o detectar errores
    public Conexion() {
        conn = null;
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, user, password);
            if(conn != null){
                System.out.println("Conexión Establecida");
            }    
        }catch (ClassNotFoundException | SQLException e){
            System.out.println("Error al conectar" +e);
        }
    }
    
    //retorna la conexion
    public Connection getConnection(){
        return conn;
    }
    
    //desconecta a la bd
    public void desconectar(){
        conn = null;
        if (conn== null) {
            System.out.println("Conexión terminada");            
        }
    }
}
