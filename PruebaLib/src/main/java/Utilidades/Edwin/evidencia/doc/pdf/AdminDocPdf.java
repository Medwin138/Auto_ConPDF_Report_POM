package Utilidades.Edwin.evidencia.doc.pdf;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.imageio.ImageIO;
import javax.swing.event.DocumentListener;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.*;

import Utilidades.Edwin.model.*;

import com.lowagie.text.Document;
import org.openqa.selenium.WebDriver;

import com.assertthat.selenium_shutterbug.core.Shutterbug;
import com.lowagie.text.BadElementException;
import com.lowagie.text.Chunk;

import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.*;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.imageio.ImageIO;


import org.openqa.selenium.WebDriver;

import com.assertthat.selenium_shutterbug.core.Shutterbug;
import com.lowagie.text.BadElementException;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document.*;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;



import  Utilidades.Edwin.model.DispositivoPrueba;
import  Utilidades.Edwin.model.Estados;
import  Utilidades.Edwin.model.EvidenciaNodo;
import  Utilidades.Edwin.model.Navegadores;
import  Utilidades.Edwin.model.Utils;





public class AdminDocPdf extends Utils {
	private String nombreArea;
	private String nombreProyecto;
	private String nombreCasoPrueba;
	private Estados estado;
	private DispositivoPrueba dispositivo;
	String fechaEjecucion;
	private List<EvidenciaNodo> nodos;
	private Document documentoPDF;
	private String textoAdicionalCabecera;
	Properties prop = new Properties();
	private Navegadores navegador;
	private String nombreAmbiente;
	private PdfPTable table = new PdfPTable(1);

	/*
	 * @param driver
	 * @param nombreArea
	 * @param nombreProyecto
	 * @throws IOException
	 */
	public AdminDocPdf(Ambientes ambiente, Navegadores navegador, DispositivoPrueba dispositivo) throws IOException {
		getProperties();
		this.nombreCasoPrueba = Thread.currentThread().getStackTrace()[2].getMethodName();
		fechaEjecucion = new SimpleDateFormat("dd_MM_yyyy_hh-mm-ss").format(new Date());
		nodos = new ArrayList<EvidenciaNodo>();
		this.dispositivo = dispositivo;
		this.navegador = navegador;
		this.nombreAmbiente = ambiente.getNombre();
		this.textoAdicionalCabecera = Thread.currentThread().getStackTrace()[2].getClassName();
		this.estado= Estados.SUCCESS;
	}

	public AdminDocPdf(Ambientes ambiente, Navegadores navegador, DispositivoPrueba dispositivo, String NombreCasodePrueba) throws IOException {
		getProperties();
		this.nombreCasoPrueba = NombreCasodePrueba;
		fechaEjecucion = new SimpleDateFormat("dd_MM_yyyy_hh-mm-ss").format(new Date());
		nodos = new ArrayList<EvidenciaNodo>();
		this.dispositivo = dispositivo;
		this.navegador = navegador;
		this.nombreAmbiente = ambiente.getNombre();
		this.textoAdicionalCabecera = Thread.currentThread().getStackTrace()[2].getClassName();
		this.estado= Estados.SUCCESS;
	}

	public AdminDocPdf() throws IOException {
		getProperties();
		this.nombreCasoPrueba = Thread.currentThread().getStackTrace()[2].getMethodName();
		fechaEjecucion = new SimpleDateFormat("dd_MM_yyyy_hh-mm-ss").format(new Date());
		nodos = new ArrayList<EvidenciaNodo>();
		this.dispositivo = DispositivoPrueba.Movil;
		this.navegador = Navegadores.ANDROID;
		this.textoAdicionalCabecera = Thread.currentThread().getStackTrace()[2].getClassName();
		this.estado= Estados.SUCCESS;
	}

	public void setNombreCasoPrueba(String nombreCasoPrueba) {
		this.nombreCasoPrueba = nombreCasoPrueba;
	}

	private void getProperties() throws IOException {
		InputStream entrada = new FileInputStream("Config.properties");
		prop.load(entrada);
		this.nombreArea = prop.getProperty("NombreArea");
		this.nombreProyecto = prop.getProperty("NombreApp");
		this.nombreAmbiente = prop.getProperty("NombreAmbiente");
	}

	public void generaEvidencia(String comentario) throws IOException {
		EvidenciaNodo nodo = new EvidenciaNodo();
		try {
			String fechaComentario = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss").format(new Date());
			nodo.setComentario(fechaComentario + ": " + comentario);
			nodos.add(nodo);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void generaEvidencia(String comentario, BufferedImage evidencia) throws IOException {
		EvidenciaNodo nodo = new EvidenciaNodo();
		try {
			String fechaComentario = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss").format(new Date());
			nodo.setEvidencia(evidencia);
			nodo.setComentario(fechaComentario + ": " + comentario);
			nodo.setDispositivo(DispositivoPrueba.WEB);
			nodos.add(nodo);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
//
//	public void generaEvidencia(String comentario, WebDriver movilDriver) throws IOException {
//		boolean vWebView = false;
//		if (((AndroidDriver) movilDriver).getContext().contains("WEBVIEW")) {
//			((AndroidDriver) movilDriver).context("NATIVE_APP");
//			vWebView = true;
//		}
//
//		EvidenciaNodo nodo = new EvidenciaNodo();
//		try {
//			String fechaComentario = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss").format(new Date());
//			nodo.setEvidencia(Shutterbug.shootPage(movilDriver).getImage());
//			nodo.setComentario(fechaComentario + ": " + comentario);
//			nodo.setDispositivo(DispositivoPrueba.Movil);
//			nodos.add(nodo);
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		} finally {
//			if (vWebView) {
//				((AndroidDriver) movilDriver).context("WEBVIEW");
//			}
//		}
//	}

	public void crearDocumento(Estados pEstados) throws MalformedURLException, DocumentException, IOException{
		this.estado=pEstados;
		creacionDocumentoPDF();
	}

	public void crearDocumento() throws MalformedURLException, DocumentException, IOException{
		creacionDocumentoPDF();
	}

	private String creacionDocumentoPDF() throws DocumentException, MalformedURLException, IOException {
		String path = "";
		documentoPDF = null;

		try {
			documentoPDF = new Document(PageSize.LETTER, 80, 80, 75, 75);

			String directorioArchivo = new File(".").getCanonicalPath() + "\\" + nombreProyecto;
			crearDirectorio(directorioArchivo);

			// directorioArchivo = directorioArchivo + "\\" + fechaEjecucion;
			// crearDirectorio(directorioArchivo);

			directorioArchivo = directorioArchivo + "\\" + nombreCasoPrueba + "_" + navegador.getNombreCorto() + "_"
					+ estado.getNombre();
			crearDirectorio(directorioArchivo);
			String nombreArchivo = nombreCasoPrueba;

			path = directorioArchivo + "\\" + nombreArchivo + "_" + estado.getNombre() + "_" + fechaEjecucion + ".pdf";

			@SuppressWarnings("unused")
			PdfWriter writer = PdfWriter.getInstance(documentoPDF, new FileOutputStream(path, true));

			documentoPDF.open();

			// Tabla encabezado
			PdfPTable encabezado = new PdfPTable(2);
			encabezado.setWidthPercentage(100);

			FileInputStream logoTelefonica = new FileInputStream("src/main/java/Utilidades/Edwin/Logo/Logo.jpg");

			Paragraph titulo = new Paragraph();
			titulo.setAlignment(Paragraph.ALIGN_CENTER);
			titulo.setFont(FontFactory.getFont("Sans", 15, Font.BOLD));
			titulo.add(nombreArea);
			titulo.add(Chunk.NEWLINE);
			titulo.setFont(FontFactory.getFont("Sans", 10, Font.BOLD));
			titulo.add("Ejecucion Prueba Automatica " );
			titulo.add(Chunk.NEWLINE);
			titulo.setFont(FontFactory.getFont("Sans", 10, Font.BOLD));
			titulo.add("Ambiente de Ejecución ");
			titulo.add(Chunk.NEWLINE);
			titulo.setFont(FontFactory.getFont("Sans", 10, Font.NORMAL));
			titulo.add(nombreProyecto);
			PdfPCell cellInfo = new PdfPCell(new Phrase(titulo));
			cellInfo.setHorizontalAlignment(Element.ALIGN_CENTER);
			encabezado.addCell(cellInfo);
			encabezado.addCell(Image.getInstance(ImageIO.read(logoTelefonica), null));

			titulo = new Paragraph();
			titulo.setAlignment(Paragraph.ALIGN_CENTER);
			titulo.setFont(FontFactory.getFont("Sans", 10, Font.BOLD));
			titulo.add("Nombre Proyecto:");
			titulo.add(Chunk.NEWLINE);
			titulo.setFont(FontFactory.getFont("Sans", 10, Font.NORMAL));
			titulo.add(nombreProyecto);
			titulo.add(Chunk.NEWLINE);
			titulo.setFont(FontFactory.getFont("Sans", 10, Font.BOLD));
			titulo.add("Caso de prueba:");
			titulo.add(Chunk.NEWLINE);
			titulo.setFont(FontFactory.getFont("Sans", 10, Font.NORMAL));
			titulo.add(nombreCasoPrueba);

			cellInfo = new PdfPCell(new Phrase(titulo));
			cellInfo.setRowspan(4);

			encabezado.addCell(cellInfo);

			titulo = new Paragraph();
			titulo.setAlignment(Paragraph.ALIGN_CENTER);
			titulo.setFont(FontFactory.getFont("Sans", 10, Font.NORMAL));
			titulo.add("Fecha: " + fechaEjecucion);
			encabezado.addCell(titulo);

			titulo = new Paragraph();
			titulo.setAlignment(Paragraph.ALIGN_CENTER);
			titulo.setFont(FontFactory.getFont("Sans", 10, Font.NORMAL));
			titulo.add("Ejecutado Por: " + System.getProperty("user.name"));
			encabezado.addCell(titulo);

			titulo = new Paragraph();
			titulo.setAlignment(Paragraph.ALIGN_CENTER);
			titulo.setFont(FontFactory.getFont("Sans", 10, Font.NORMAL));
			titulo.add("Resultado: " + this.estado.getNombre());
			encabezado.addCell(titulo);

			titulo = new Paragraph();
			titulo.setAlignment(Paragraph.ALIGN_CENTER);
			titulo.setFont(FontFactory.getFont("Sans", 10, Font.NORMAL));
			titulo.add("Navegador: " + this.navegador.getNombre());
			encabezado.addCell(titulo);

			if (!(textoAdicionalCabecera == null)) {

				titulo = new Paragraph();
				titulo.setAlignment(Paragraph.ALIGN_CENTER);
				titulo.setFont(FontFactory.getFont("Sans", 10, Font.NORMAL));
				titulo.add("Modulo / Componente: " + this.textoAdicionalCabecera);

				cellInfo = new PdfPCell(new Phrase(titulo));
				cellInfo.setColspan(2);

				encabezado.addCell(cellInfo);
			}

			documentoPDF.add(encabezado);

			titulo = new Paragraph();
			titulo.setAlignment(Paragraph.ALIGN_CENTER);
			titulo.setFont(FontFactory.getFont("Sans", 15, Font.NORMAL));
			titulo.add(Chunk.NEWLINE);
			titulo.add("Evidencias resultado de ejecución");
			documentoPDF.add(titulo);

			table.setWidthPercentage(100);
			for (EvidenciaNodo nodo : nodos) {
				addTexto(nodo.getComentario());
				if (nodo.getEvidencia() != null) {
					addImage(nodo.getEvidencia(), nodo.getDispositivo());
				}
			}
			table.setSplitLate(false);
			table.setSplitRows(true);
			documentoPDF.add(table);

		} catch (Exception e) {
			System.out.println(e.toString());
		}

		finally {
			if (documentoPDF.isOpen()) {
				documentoPDF.close();
			}
		}
		return path;
	}

	/* Método para añadir texto al PDF */
	public void addTexto(String Cadena) {
		PdfPCell cell;
		Paragraph parrafo = new Paragraph();
		parrafo.setFont(FontFactory.getFont("Sans", 12, Font.NORMAL));
		parrafo.setAlignment(Paragraph.ALIGN_LEFT);
		parrafo.add(Chunk.NEWLINE);
		parrafo.add(Cadena);
		cell = new PdfPCell(parrafo);
		cell.setBorder(Rectangle.NO_BORDER);
		table.addCell(cell);
	}

	/* Método para agregar una imágen a un PDF */
	public void addImage(BufferedImage vImagen, DispositivoPrueba dispositivo) throws IOException {
		Image imagen;
		java.awt.Image convertImage;
		try {
			PdfPCell cell;
			convertImage = Toolkit.getDefaultToolkit().createImage(vImagen.getSource());
			imagen = Image.getInstance(convertImage, null);
			float scaler;
			if (dispositivo == DispositivoPrueba.WEB) {
				scaler = ((documentoPDF.getPageSize().getWidth() - documentoPDF.leftMargin()
						- documentoPDF.rightMargin()) / imagen.getWidth() * 100);
			} else {
				scaler = ((documentoPDF.getPageSize().getHeight() - documentoPDF.topMargin() - documentoPDF.bottom()
						- 250) / imagen.getHeight() * 100);
			}

			imagen.scalePercent(scaler);
			//imagen.setBorder(Rectangle.BOX);
			//imagen.setBorderWidth(5);

			cell = new PdfPCell(imagen);
			cell.setBorder(Rectangle.NO_BORDER);
			cell.setVerticalAlignment(cell.ALIGN_CENTER);
			cell.setBackgroundColor(new Color(255, 255, 255));

			table.addCell(cell);
		} catch (BadElementException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}

	public void agregaNodosExternos(List<EvidenciaNodo> nodosExt){
		for (int i=0;i < nodosExt.size();i++){
			nodos.add(nodosExt.get(i));
		}

		for (int i=0;i < nodosExt.size();i++){
			nodosExt.remove(i);
		}

	}

	public List<EvidenciaNodo> getNodos(){
		return nodos;
	}

	public void setEstado(Estados pEstado){
		this.estado = pEstado;
	}

	public Estados getEstado(){
		return estado;
	}
}
