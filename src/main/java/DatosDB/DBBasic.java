package DatosDB;

import interfazDB.IAlquilerDAO;
import interfazDB.IConfigDAO;
import interfazDB.IDBDAO;
import interfazDB.IEditorialDAO;
import interfazDB.IJuegoDAO;
import interfazDB.IJugadorDAO;
import interfazDB.IMonederoDAO;
import interfazDB.IPasaporteDAO;
import interfazDB.IUbicacionDAO;
import interfazDB.IUsuarioDAO;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DBBasic extends IDBDAO {
	
	// 1. Cambiamos el Driver al de MariaDB
	//public static final String DRIVER = "org.mariadb.jdbc.Driver";
	public static final String DRIVER = "com.mysql.jdbc.Driver";
	
	public Connection conexion;
	public static String BD = "gesjornadas";
	
	// 2. Cambiamos el protocolo de la URL de 'mysql' a 'mariadb'
    //public static String DBURL = "jdbc:mariadb://localhost:3306/gesjornadas";
	public static String DBURL = "jdbc:mysql://localhost:3306/gesjornadas";
	
    public static String LOGIN = "root";
	public static String PASS = "J3sta2023#";
	
	//Valores de conexion con Mariadb
	public static String LOGINDEBUG = "root";
	public static String PASSDEBUG = "EiiMii";
	
	private static boolean DEBUG = true;

	
	
	public DBBasic() throws IOException {
		Properties prop = new Properties();
		String rutaConfig = DEBUG ? "/tmp/config.properties" : "config.properties";
		
		File fileConfig = new File(rutaConfig);
	
	    if (fileConfig.exists()) {
	        // 1. Intentar cargar el archivo existente
	        try (InputStream input = new FileInputStream(fileConfig)) {
	        	String path = fileConfig.getAbsolutePath();
	        	System.out.println(path);
	        	prop.load(input);
	            BD = prop.getProperty("servidor.BD");
	            DBURL = prop.getProperty("servidor.DBURL");
	            LOGIN = prop.getProperty("servidor.LOGIN");
	            PASS = prop.getProperty("servidor.PASS");
	            System.out.println("Configuración cargada desde archivo.");
	        	
	        } catch (IOException e) {
	            System.err.println("Error al leer el archivo, se solicitarán datos.");
	            DatosDB(prop, fileConfig);
	        }
	     }
	    else {
	    	DatosDB(prop, fileConfig);
	    }
	    	
	}
	
	private void DatosDB (Properties prop, File fileConfig) throws IOException {
		prop.setProperty("servidor.BD", BD);
		prop.setProperty("servidor.DBURL", DBURL);
		if (DEBUG) {
			prop.setProperty("servidor.LOGIN", LOGINDEBUG);
			prop.setProperty("servidor.PASS", PASSDEBUG);
		} else {
			prop.setProperty("servidor.LOGIN", LOGIN);
			prop.setProperty("servidor.PASS", PASS);
		}

	    // Guardar en el fichero
	    try (OutputStream out = new FileOutputStream(fileConfig)) {
	        prop.store(out, "Configuración guardada automáticamente");
	        System.out.println("Datos guardados con éxito en: " + fileConfig.getAbsolutePath());
	    }
	    catch(IOException io) {
	        io.printStackTrace(); // Esto saldrá en la consola de Eclipse (Server Logs)
	        throw io;             // Esto enviará el error hacia arriba
	    }
	}
	
	public boolean Conectar() throws Exception {
        try {
            // 3. Cargamos el nuevo Driver de MariaDB
            Class.forName(DRIVER); 
            
            if (DEBUG) {
                this.conexion = DriverManager.getConnection(DBURL, LOGINDEBUG, PASSDEBUG);
            } else {
                this.conexion = DriverManager.getConnection(DBURL, LOGIN, PASS);
            }

            return true;
        } catch (Exception var2) {
            var2.printStackTrace();
            throw var2;
        }
    }

	public void desconectar() {
		try {
			if (this.conexion != null) {
				this.conexion.close();
				this.conexion = null;
			}
		} catch (Exception var2) {
			var2.printStackTrace();
		}

	}

	public IConfigDAO getConfigDAO() {
		return new ConfigDAO();
	}

	public IUsuarioDAO getUsuarioDAO() {
		return new UsuarioDAO();
	}

	public IJugadorDAO getJugadorDAO() {
		return new JugadorDAO();
	}

	public IUbicacionDAO getUbicacionDAO() {
		return new UbicacionDAO();
	}

	public IJuegoDAO getJuegoDAO() {
		return new JuegoDAO();
	}

	public IAlquilerDAO getAlquilerDAO() {
		return new AlquilerDAO();
	}

	public IPasaporteDAO getPasaporteDAO() {
		return new PasaporteDAO();
	}

	public IMonederoDAO getMonederoDAO() {
		return new MonederoDAO();
	}

	public IEditorialDAO getEditorialDAO() {
		return new EditorialDAO();
	}
}