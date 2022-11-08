package Utilidades.Edwin.model;

public enum Estados {
	FAILED("Fallido"), SUCCESS("Satisfactorio"), BROKEN("Interrumpido");
	
	private String nombre;

	/**
	 * @param nombre
	 */
	private Estados(String nombre) {
		this.nombre = nombre;
	}

	public String getNombre() {
		return nombre;
	}

}
