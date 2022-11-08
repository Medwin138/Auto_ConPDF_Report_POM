package Utilidades.Edwin.manager.param;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import Utilidades.Edwin.model.TipoCone;

public class FormatoUSSD {
	private String nombreHoja;
	private String nombreArchivo;
	Properties prop = new Properties();

	/*
	 * @param tipoCone
	 * @param nombreHoja
	 * @param nombreCaso
	 * @throws IOException
	 */
	public FormatoUSSD(String nombreHoja) throws IOException {
		getProperties();
		this.nombreHoja = nombreHoja;
	}

	private void getProperties() throws IOException {
		InputStream entrada = new FileInputStream("Config.properties");
		prop.load(entrada);
		this.nombreArchivo = new File(".").getCanonicalPath() + "\\parametros\\" + prop.getProperty("archivoExcel");
	}

	public Object[][] getParams() {

		Map<Object, Object> datos = new HashMap<Object, Object>();

		ArrayList<Object> paramEncabezado = new ArrayList<>();
		ArrayList<Object> valoresParam = new ArrayList<>();
		ArrayList<Object> datosExcel = new ArrayList<>();

		Object[][] conjuntoDatos = null;

		try {
			boolean guardaValores = false;

			FileInputStream file = new FileInputStream(new File(nombreArchivo));
			XSSFWorkbook objWorkbook = new XSSFWorkbook(file);
			XSSFSheet objSheet = objWorkbook.getSheet(nombreHoja);
			Iterator<Row> rowIterator = objSheet.iterator();

			Row row;
			
			while (rowIterator.hasNext()) {
				row = rowIterator.next();
				if (row.getCell(1) != null) {
					if (!(row.getCell(1).getStringCellValue().isEmpty())) {
						// Obtiene los ENCABEZADOS de las varibles:
						if (guardaValores) {
							Iterator<Cell> cellIterator = row.cellIterator();
							Cell celda;



							if (valoresParam.get(2).toString().toLowerCase().equals("si")) {
								for (int i = 0; i < paramEncabezado.size(); i++) {
									datos.put(paramEncabezado.get(i), valoresParam.get(i));
								}

								// Crea el conjunto de datos.
								datosExcel.add(datos);
							}

							// Inicializo para crear una nueva fila en el pool
							paramEncabezado = new ArrayList<>();
							valoresParam = new ArrayList<>();
							datos = new HashMap<Object, Object>();

							guardaValores = false;

						} else {
							Iterator<Cell> cellIterator = row.cellIterator();
							Cell celda;
							celda = cellIterator.next();

							// Establece el ENCABEZADO para el valor Nombre caso
							// de
							// la columna A.
							paramEncabezado.add("&NombreCaso");
							// Establece el VALOR del encabezado &NombreCaso de
							// la
							// columna A.
							valoresParam.add(celda.getStringCellValue());

							// Establece el nombre del ArchivoJson para buscar
							// la configuracion
							// Columna B
							celda = cellIterator.next();
							paramEncabezado.add("&" + celda.getStringCellValue());

							// Establece el numeral que desea marcar
							// Columna C
							celda = cellIterator.next();
							paramEncabezado.add("&" + celda.getStringCellValue());
							
							// Establece el Ejecutar para determinar si se
							// ejecuta o no.
							// ValidaAltamira
							// Columna D
							celda = cellIterator.next();
							paramEncabezado.add("&" + celda.getStringCellValue());

							// Establece el ENCABEZADO para el valor Numero para
							// validacion
							// en diferentes plataformas
							// Columna E
							celda = cellIterator.next();
							paramEncabezado.add("&" + celda.getStringCellValue());

							int i = 0;
							while (cellIterator.hasNext()) {
								celda = cellIterator.next();
								i++;


							}
							guardaValores = true;
						}
					}
				}
			}
			objWorkbook.close();

			conjuntoDatos = new Object[datosExcel.size()][1];

			for (int i = 0; i < datosExcel.size(); i++) {
				conjuntoDatos[i][0] = datosExcel.get(i);
			}

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error consultando datos de conexion. Detalle: " + e.getMessage());
		}

		return conjuntoDatos;
	}

	public Object[][] getParams(String pNombreCasoPrueba) {

		Map<Object, Object> datos = new HashMap<Object, Object>();

		ArrayList<Object> paramEncabezado = new ArrayList<>();
		ArrayList<Object> valoresParam = new ArrayList<>();
		ArrayList<Object> datosExcel = new ArrayList<>();

		Object[][] conjuntoDatos = null;

		try {
			boolean guardaValores = false;

			FileInputStream file = new FileInputStream(new File(nombreArchivo));
			XSSFWorkbook objWorkbook = new XSSFWorkbook(file);
			XSSFSheet objSheet = objWorkbook.getSheet(nombreHoja);
			Iterator<Row> rowIterator = objSheet.iterator();

			objWorkbook.close();

			conjuntoDatos = new Object[datosExcel.size()][1];

			for (int i = 0; i < datosExcel.size(); i++) {
				conjuntoDatos[i][0] = datosExcel.get(i);
			}

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error consultando datos de conexion. Detalle: " + e.getMessage());
		}

		return conjuntoDatos;
	}
}
