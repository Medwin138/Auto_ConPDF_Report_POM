package Utilidades.Edwin.evidencia.doc.text;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

public class AdminDocText {
	private String fecha;
	private String prefijo;
	private String ext;
	private String rutaArchivo;
	Properties prop = new Properties();
	FileWriter fichero = null;
	PrintWriter pw = null;

	public String getFecha() {
		return fecha;
	}

	public String getPrefijo() {
		return prefijo;
	}

	public String getExt() {
		return ext;
	}

	private void getProperties() throws IOException {
		InputStream entrada = new FileInputStream("Config.properties");
		prop.load(entrada);
		this.fecha = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
		this.rutaArchivo = new File(".").getCanonicalPath() + "\\" + prop.getProperty("NombreApp");

	}

	// M�todo para la generacion o apertura del archivo
	public AdminDocText(String nombreArchivo) throws IOException {
		getProperties();
		rutaArchivo = rutaArchivo + "\\" + nombreArchivo + "_" + fecha + ".txt";
		fichero = new FileWriter(rutaArchivo, true);
		pw = new PrintWriter(fichero);
	}

	// M�todo para ingresar cadenas de texto al archivo
	public void InsertarArchivo(String pTexto) {

		pw.println(pTexto);
	}

	// M�todo para cerrar y cuardar el archivo
	public void cerrarArchivo() throws IOException {

		fichero.close();
	}
}
