package Utilidades.Edwin.evidencia.image;

import java.awt.image.BufferedImage;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;

import Utilidades.Edwin.model.Utils;


public class AdminImages extends Utils {
	private String nombreProyecto;
	private String nombreCasoPrueba;
	String fechaEjecucion;

	/*
	 * @param nombreArea
	 * @param nombreProyecto
	 * @param nombreCasoPrueba
	 */
	public AdminImages(String nombreProyecto, String nombreCasoPrueba) {
		this.nombreProyecto = nombreProyecto;
		this.nombreCasoPrueba = nombreCasoPrueba;
		fechaEjecucion = new SimpleDateFormat("dd_MM_yyyy hh-mm-ss").format(new Date());
	}

	public void generaEvidencia(BufferedImage evidencia, String nombre) {

		String directorioArchivo = null;

		try {
			directorioArchivo = new File(".").getCanonicalPath() + "\\" + nombreProyecto ;
			crearDirectorio(directorioArchivo);
		
			directorioArchivo = directorioArchivo+"\\" + nombreCasoPrueba;
			crearDirectorio(directorioArchivo);
			
			directorioArchivo= directorioArchivo + "\\" + fechaEjecucion;
			crearDirectorio(directorioArchivo);
			
			directorioArchivo = directorioArchivo + "\\" + nombre + ".jpg";

			File archivoImagen = new File(directorioArchivo);
			ImageIO.write(evidencia, "jpg", archivoImagen);

		} catch (Exception e) {

			System.out.println("Error guardando ScrenShot " + nombre + " en " + directorioArchivo);
			e.printStackTrace();
		}
	}
}
