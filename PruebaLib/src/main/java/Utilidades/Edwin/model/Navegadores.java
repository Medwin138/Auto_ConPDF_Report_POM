package Utilidades.Edwin.model;

public enum Navegadores {
	IE(1,"Internet Explorer","IE"), CHROME(2,"Google Chrome","GC"), FIREFOX(3,"Mozilla Firefox","MF"), EDGE(4,"Internet Explorer Edge","IEE"), SAFARI(5,
			"Safari","SF"), OPERA(6,"Opera","OP"), ANDROID(7,"Android","AND"), NA(8,"NA","NA");

	private String nombre;
	private int posicion;
	private String nombrecorto;

	/**
	 * @param nombre
	 */
	private Navegadores(int posicion,String nombre, String nombrecorto) {
		this.nombre = nombre;
		this.posicion = posicion;
		this.nombrecorto = nombrecorto;
	}

	public String getNombre() {
		return nombre;
	}
	
	public String getNombreCorto() {
		return nombrecorto;
	}
	
	public int getNavegador() {
		return posicion;
	}

}