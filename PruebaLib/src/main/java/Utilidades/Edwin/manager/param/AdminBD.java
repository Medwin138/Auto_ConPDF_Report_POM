package Utilidades.Edwin.manager.param;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.sql.*;

public class AdminBD {

	private Connection conexion;

	public void conectar() throws IOException {

		Properties prop = new Properties();
		InputStream entrada = new FileInputStream("config.properties");
		prop.load(entrada);

		String ipBD = prop.getProperty("ipBD");
		String puertoBD = prop.getProperty("puertoBD");
		String NombreBD = prop.getProperty("NombreBD");
		String UsuarioBD = prop.getProperty("UsuarioBD");
		String PassBD = prop.getProperty("PassBD");
		
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			String BaseDeDatos = "jdbc:oracle:thin:@" + ipBD + ":" + puertoBD + ":" + NombreBD;

			conexion = DriverManager.getConnection(BaseDeDatos, UsuarioBD, PassBD);

			if (conexion != null) {
				// System.out.println("Conexion exitosa!");
			} else {
				System.out.println("Conexion fallida!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void DesconectarBD() {
		if (conexion != null) {
			try {
				conexion.close();
				System.out.println("Conexion con la BD Cerrada");
			} catch (Exception e) {

				System.out.println("No se logro cerrar la conexion con la BD");

			}
		}
	}

	public ResultSet consultar(String sql) {
		ResultSet resultado = null;
		try {
			Statement sentencia;
			sentencia = conexion.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			resultado = sentencia.executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return resultado;
	}

}
