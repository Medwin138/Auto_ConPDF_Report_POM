package Utilidades.Edwin.model;

public enum Ambientes {
	PRODUCCION("Producción"), PROYECTOS(": Pre-Pruductivo"), PRECOL(": QA"),NA(": N/A");
	
	private String nombre;

	/**
	 * @param nombre
	 */
	private Ambientes(String nombre) {
		this.nombre = nombre;
	}

	public String getNombre() {
		return nombre;
	}

}
