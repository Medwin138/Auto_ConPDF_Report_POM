package Utilidades.Edwin.model;

import java.io.File;

public class Utils {

	public void crearDirectorio(String directorioArchivo) {
		File theDir = new File(directorioArchivo);
		if (!theDir.exists()) {
			theDir.mkdir();
		}
	}

}
