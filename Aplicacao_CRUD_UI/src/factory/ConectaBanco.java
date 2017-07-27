package factory;
import java.sql.Connection;
import java.sql.DriverManager;

public class ConectaBanco {

    private static final String USUARIO = "root";
    private static final String SENHA = "";
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/banco";
    private static final String DRIVER = "com.mysql.jdbc.Driver";

    // Conectar ao banco
    public static Connection abrir() throws Exception {

    	Connection conn;
    	try{
    		Class.forName(DRIVER);
    		conn = DriverManager.getConnection(URL, USUARIO, SENHA);
    		return conn;
    	} catch (ClassNotFoundException e) {
			System.err.println("Classe não encontrada!" + e.getMessage());
		}
    	return null;
    }
}