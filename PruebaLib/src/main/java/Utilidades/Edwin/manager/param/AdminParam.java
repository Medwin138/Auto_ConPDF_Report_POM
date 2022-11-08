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

import Utilidades.Edwin.model.TipoCone;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class AdminParam {
    TipoCone tipoConexion;
    private String nombreHoja;
    private String nombreCaso;
    private String nombreArchivo;
    public Map<Object, Object> resultado;
    Properties prop = new Properties();

    /*
     * @param tipoCone
     * @param nombreHoja
     * @param nombreCaso
     * @throws IOException
     */
    public AdminParam(TipoCone tipoConexion, String nombreHoja) throws IOException {
        getProperties();
        this.tipoConexion = tipoConexion;
        this.nombreHoja = nombreHoja;
        this.nombreCaso = Thread.currentThread().getStackTrace()[2].getMethodName();
    }

    public AdminParam(TipoCone tipoConexion, String nombreHoja, String nombreCaso) throws IOException {
        getProperties();
        this.tipoConexion = tipoConexion;
        this.nombreHoja = nombreHoja;
        this.nombreCaso = nombreCaso;
    }

    private void getProperties() throws IOException {
        InputStream entrada = new FileInputStream("Config.properties");
        prop.load(entrada);
        this.nombreArchivo = new File(".").getCanonicalPath() + "\\parametros\\" + prop.getProperty("archivoExcel");
    }

    public Map<Object, Object> ObtenerParametros() {
        Map<Object, Object> result = new HashMap<Object, Object>();
        result = getParams();
        return result;
    }

    public Map<Object, Object> getParams() {

        Map<Object, Object> datos = new HashMap<Object, Object>();

        ArrayList<Object> llavesParam = new ArrayList<>();
        ArrayList<Object> valoresParam = new ArrayList<>();

        try {
            boolean guardaValores = false;
            boolean guardaLlaves = false;
            boolean encuentraCP = false;

            FileInputStream file = new FileInputStream(new File(nombreArchivo));
            XSSFWorkbook objWorkbook = new XSSFWorkbook(file);
            XSSFSheet objSheet = objWorkbook.getSheet(nombreHoja);
            Iterator<Row> rowIterator = objSheet.iterator();
            Row row;

            while (rowIterator.hasNext()) {
                if (guardaLlaves && encuentraCP && !guardaValores) {
                    guardaLlaves = false;
                    guardaValores = true;
                } else if (encuentraCP && guardaValores && !guardaLlaves) {
                    encuentraCP = false;
                    guardaValores = false;
                }

                row = rowIterator.next();
                Iterator<Cell> cellIterator = row.cellIterator();

                Cell celda;

//                while (cellIterator.hasNext()) {
//
//                    celda = cellIterator.next();
//
//                    if (encuentraCP) {
//
//                        switch (celda.getCellTypeEnum()) {
//
//                            case NUMERIC:
//                                if (DateUtil.isCellDateFormatted(celda)) {
//                                    if (guardaLlaves) {
//                                        llavesParam.add(celda.getDateCellValue());
//                                    } else if (guardaValores) {
//                                        valoresParam.add(celda.getDateCellValue());
//                                    }
//                                }
//                                break;
//                            case STRING:
//                                if (guardaLlaves) {
//                                    llavesParam.add(celda.getStringCellValue());
//                                } else if (guardaValores) {
//                                    valoresParam.add(celda.getStringCellValue());
//                                }
//                                break;
//                            default:
//                                break;
//                        }
//                    }
//
//                    if (celda.getCellTypeEnum() == CellType.STRING) {
//                        if (celda.getStringCellValue().equals(nombreCaso)) {
//                            encuentraCP = true;
//                            guardaLlaves = true;
//                            guardaValores = false;
//                        }
//                    }
//                }
            }
            objWorkbook.close();

            for (int i = 0; i < llavesParam.size(); i++) {
                datos.put(llavesParam.get(i), valoresParam.get(i));
            }

        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("Error consultando datos de conexion. Detalle: " + e.getMessage());
            return new HashMap<Object, Object>();
        }
        resultado = datos;
        return datos;
    }

}
