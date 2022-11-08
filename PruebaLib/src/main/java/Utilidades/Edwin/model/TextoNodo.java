package Utilidades.Edwin.model;

public class TextoNodo {
	private String nombreCaso;
	private String comentario;
	/**
	 * @param nombreCaso
	 * @param comentario
	 */
	public TextoNodo(String nombreCaso, String comentario) {
		this.nombreCaso = nombreCaso;
		this.comentario = comentario;
	}
	public String getNombreCaso() {
		return nombreCaso;
	}
	public void setNombreCaso(String nombreCaso) {
		this.nombreCaso = nombreCaso;
	}
	public String getComentario() {
		return comentario;
	}
	public void setComentario(String comentario) {
		this.comentario = comentario;
	}
	
	
}
