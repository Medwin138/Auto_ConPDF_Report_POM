/**
 * 
 */
package Utilidades.Edwin.model;

import java.awt.image.BufferedImage;
import java.io.Serializable;

/**
 * @author jsavilama
 *
 */
public class EvidenciaNodo implements Serializable{
    private String comentario;
    private BufferedImage evidencia;
    private DispositivoPrueba dispositivo;
	/**
	 * 
	 */
	public EvidenciaNodo() {
		this.comentario ="";
		this.evidencia=null;
	}
	public String getComentario() {
		return comentario;
	}
	public void setComentario(String comentario) {
		this.comentario = comentario;
	}
	public BufferedImage getEvidencia() {
		return evidencia;
	}
	public void setEvidencia(BufferedImage evidencia) {
		this.evidencia = evidencia;
	}
	public DispositivoPrueba getDispositivo() {
		return dispositivo;
	}
	public void setDispositivo(DispositivoPrueba dispositivo) {
		this.dispositivo = dispositivo;
	}

}
