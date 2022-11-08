package Utilidades.Edwin.manager.config;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;

public class ConfigJson {
	String casoPrueba;
	String archivoJson;
	
	public ConfigJson(String casoPrueba, String archivoJson) throws IOException{
		this.casoPrueba=casoPrueba;
		this.archivoJson=new File(".").getCanonicalPath() + "\\config\\"+archivoJson;
	}
	
	public String[][] obtenerValidaciones() {
		// TODO Auto-generated method stub
		String[][] validacionesPltfms = null;
		try {
			JsonParser objJsonParser = new JsonParser();
			FileReader objFileReader= new FileReader(archivoJson);

			JsonElement datosJson = objJsonParser.parse(objFileReader);

			JsonElement jsonCasoPrueba = getElementoDescendiente(datosJson, casoPrueba);

			JsonElement jsonAplicacionBES = getElementoDescendiente(jsonCasoPrueba, "BES");
			validacionesPltfms = obtenerElemento(jsonAplicacionBES, "BES");

			JsonElement jsonAplicacionSPN = getElementoDescendiente(jsonCasoPrueba, "SPN");
			validacionesPltfms = unificarValidacionesPltfms(validacionesPltfms, obtenerElemento(jsonAplicacionSPN, "SPN"));

			JsonElement jsonAplicacionActivador = getElementoDescendiente(jsonCasoPrueba, "Activador");
			validacionesPltfms = unificarValidacionesPltfms(validacionesPltfms, obtenerElemento(jsonAplicacionActivador, "Activador"));

			JsonElement jsonAplicacionAltamira = getElementoDescendiente(jsonCasoPrueba, "Altamira");
			validacionesPltfms = unificarValidacionesPltfms(validacionesPltfms, obtenerElemento(jsonAplicacionAltamira, "Altamira"));
			
			JsonElement jsonAplicacionMTH = getElementoDescendiente(jsonCasoPrueba, "MTH");
			validacionesPltfms = unificarValidacionesPltfms(validacionesPltfms, obtenerElemento(jsonAplicacionMTH, "MTH"));

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("El archivo del Stream no existe: " + e.getMessage());
			System.exit(1);
		}

		return validacionesPltfms;
	}

	private static String[][] unificarValidacionesPltfms(String[][] pConsolidado, String[][] pAgregar) {

		String[][] unificado = new String[pConsolidado.length + pAgregar.length][pConsolidado[0].length];

		for (int i = 0; i < unificado.length; i++) {
			for (int j = 0; j < unificado[i].length; j++) {

				if (i < pConsolidado.length) {
					unificado[i][j] = pConsolidado[i][j];
				} else {

					unificado[i][j] = pAgregar[i - pConsolidado.length][j];
				}
			}
		}

		return unificado;
	}

	private static JsonElement getElementoDescendiente(JsonElement pElemento, String pCasoPrueba) {

		JsonObject objJsonObject = null;
		JsonElement elementoAplicativo = null;

		try {
			if (pElemento.isJsonObject()) {
				objJsonObject = pElemento.getAsJsonObject();

				java.util.Set<java.util.Map.Entry<String, JsonElement>> entradas = objJsonObject.entrySet();
				java.util.Iterator<java.util.Map.Entry<String, JsonElement>> iter = entradas.iterator();

				while (iter.hasNext()) {
					java.util.Map.Entry<String, JsonElement> entrada = iter.next();

					if (entrada.getKey().equals(pCasoPrueba)) {
						elementoAplicativo = entrada.getValue();
					}
				}
			}
		} catch (NullPointerException e) {
			System.out.println("El caso no existe. Revisar el json - " + pCasoPrueba);
			System.exit(1);
		}

		return elementoAplicativo;
	}

	private static String[][] obtenerElemento(JsonElement pElemento, String pAplicacion) {

		String[][] Respuesta = null;

		try {

			JsonObject obj = pElemento.getAsJsonObject();
			java.util.Set<java.util.Map.Entry<String, JsonElement>> entradas = obj.entrySet();
			java.util.Iterator<java.util.Map.Entry<String, JsonElement>> iter = entradas.iterator();

			// System.out.println("Cantidad de lineas: "+entradas.size());

			Respuesta = new String[entradas.size()][3];
			int contador = 0;

			while (iter.hasNext()) {
				java.util.Map.Entry<String, JsonElement> entrada = iter.next();
				JsonPrimitive valor = entrada.getValue().getAsJsonPrimitive();
				// System.out.println(aplicacion+" - "+entrada.getKey()+" - " +
				// valor.getAsString() );
				Respuesta[contador][0] = pAplicacion;
				Respuesta[contador][1] = entrada.getKey();
				Respuesta[contador][2] = valor.getAsString();
				contador++;
			}

		} catch (NullPointerException e) {
			System.out.println("La aplicaci�n " + pAplicacion + " no existe. Revisar el json");
			System.exit(1);
		}

		return Respuesta;
	}

}
