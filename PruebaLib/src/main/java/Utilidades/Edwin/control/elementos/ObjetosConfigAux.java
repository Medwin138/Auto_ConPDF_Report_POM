package Utilidades.Edwin.control.elementos;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import Utilidades.Edwin.model.Estados;
import Utilidades.Edwin.model.Navegadores;
import Utilidades.Edwin.model.Utils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.ProfilesIni;

import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.internal.TouchAction;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;



import Utilidades.Edwin.evidencia.doc.pdf.AdminDocPdf;

import Utilidades.Edwin.manager.param.AdminParam;



import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.UnhandledAlertException;

public class ObjetosConfigAux extends Utils {

	private WebDriver driver;
	private DesiredCapabilities capabilities;

	private String appPackage;
	private String appActivity;
	private String urlAppium;

	private String urlBase;
	private int timeOut = 0;
	private String pathChromeAppium;

	public AdminDocPdf AdminDocPdf;
	public AdminParam AdminParam;
	private String usuario;
	private String contrasena;
	private String versionAndroid;
	private boolean autoWebview;

	private Navegadores navegador;

	private String dirDescargas;

	private int nTiempo;

	Properties prop = new Properties();

	/*
	 * Constructor de la clase para acceder al Driver de selenium y los metodo
	 * auxiliares seleccionando el tipo de navegador. Este metodo inicia el
	 * navegador.
	 * 
	 * @param navegadores
	 *            El parametro navegadores corresponde al valor String de la
	 *            lista Navegadores
	 *            ("1"=IE,"2"=CHROME,"3"=FIREFOX,"4"=EDGE,"5"=SAFARI,"6"=OPERA,"7"=N/A)
	 */
	public ObjetosConfigAux(String deviceName, boolean noReset) throws IOException, InterruptedException {
		// Optinee parametros de configuracion
		getProperties();

		capabilities = new DesiredCapabilities();

		// Set android deviceName desired capability. Set your device name.
		capabilities.setCapability("deviceName", deviceName);

		// Set android VERSION desired capability. Set your mobile device's OS
		// version.
		capabilities.setCapability(CapabilityType.VERSION, versionAndroid);

		// Set android platformName desired capability. It's Android in our case
		// here.
		capabilities.setCapability("platformName", "Android");

		// Set BROWSER_NAME desired capability. It's Android in our case here.
		capabilities.setCapability("autoWebview", autoWebview);

		// Set android appPackage desired capability. It is
		// com.android.calculator2 for calculator application.
		// Set your application's appPackage if you are using any other app.
		capabilities.setCapability("appPackage", appPackage);

		// Set android appActivity desired capability. It is
		// com.android.calculator2.Calculator for calculator application.
		// Set your application's appPackage if you are using any other app.
		capabilities.setCapability("appActivity", appActivity);

		// No reset
		capabilities.setCapability("noReset", noReset);

		// Permisos
		capabilities.setCapability("autoGrantPermissions", "true");

		// Version de android con la version del navegador
		capabilities.setCapability("chromedriverExecutable", pathChromeAppium);

		// Created object of RemoteWebDriver will all set capabilities.
		// Set appium server address and port number in URL string.
		// It will launch calculator app in android device.


		driver.manage().timeouts().implicitlyWait(timeOut, TimeUnit.SECONDS);
	}

	public ObjetosConfigAux(String navegadores, int timeOut) throws IOException, InterruptedException {
		for (Navegadores n : Navegadores.values()) {
			if (n.getNavegador() == Integer.parseInt(navegadores)) {
				navegador = n;
				break;
			}
		}
		getProperties();
		this.timeOut = timeOut;

	}

	/**
	 * Constructor de la clase para acceder al Driver de selenium y los metodo
	 * auxiliares seleccionando el tipo de navegador. Este metodo inicia el
	 * navegador.
	 * 
	 * @param navegadores
	 *            El parametro navegadores corresponde al valor String de la
	 *            lista Navegadores
	 *            ("1"=IE,"2"=CHROME,"3"=FIREFOX,"4"=EDGE,"5"=SAFARI,"6"=OPERA,"7"=N/A)
	 */
	public ObjetosConfigAux(String navegadores) throws IOException, InterruptedException {
		for (Navegadores n : Navegadores.values()) {
			if (n.getNavegador() == Integer.parseInt(navegadores)) {
				navegador = n;
				break;
			}
		}
		getProperties();

	}



	public Navegadores getNavegador() {
		return navegador;
	}

	public void getFirefox() throws InterruptedException {
		ProfilesIni profile = new ProfilesIni();
		FirefoxProfile myprofile = profile.getProfile("default");

		setDriver(new FirefoxDriver());

		getDriver().manage().timeouts().implicitlyWait(timeOut, TimeUnit.SECONDS);
		getDriver().get(urlBase);

	}



	public String getDirDescargas() {
		return dirDescargas;
	}

	public void getExplorer() throws InterruptedException {
		InternetExplorerOptions ieOptions = new InternetExplorerOptions();
		ieOptions.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
		ieOptions.setCapability("ACCEPT_SSL_CERTS", true);
		ieOptions.setCapability(InternetExplorerDriver.ENABLE_PERSISTENT_HOVERING, false);

		setDriver(new InternetExplorerDriver(ieOptions));

		try {
			getDriver().get(urlBase);
			getDriver().manage().window().maximize();
		} catch (UnhandledAlertException f) {
			try {
				Alert alert = driver.switchTo().alert();
				String alertText = alert.getText();
				System.out.println("Alert data: " + alertText);
				alert.accept();
			} catch (NoAlertPresentException c) {
				c.printStackTrace();
			}
		}
	}

	/**
	 * Metodo que permite obtener el valor de las propiedades que se encuentra
	 * en el archivo config.properties
	 * 
	 * @throws IOException
	 */
	private void getProperties() throws IOException {
		InputStream entrada = new FileInputStream("Config.properties");
		prop.load(entrada);
		appActivity = prop.getProperty("appActivity");
		appPackage = prop.getProperty("appPackage");
		urlAppium = prop.getProperty("urlAppium");
		usuario = prop.getProperty("usuario");
		contrasena = prop.getProperty("contrasenia");
		versionAndroid = prop.getProperty("version");
		autoWebview = Boolean.parseBoolean(prop.getProperty("autoWebview"));
		this.urlBase = prop.getProperty("urlBase");

		try {
			nTiempo = Integer.parseInt(prop.getProperty("Tiempo"));
		} catch (Exception e) {
			// TODO: handle exception
			nTiempo = 10;
		}

		try {
			pathChromeAppium = prop.getProperty("PathChromeAppium");
		} catch (Exception e) {
			// TODO: handle exception
			pathChromeAppium = "C:\\Selenium\\Driver\\chromedriver.exe";
		}

	}



	/**
	 * Metodo que permite
	 * 
	 * @return
	 */
	public String getUsuario() {
		return usuario;
	}

	public String getContrasenia() {
		return contrasena;
	}

	/**
	 * Metodo que permite establecer el tiempo de espera
	 * 
	 * @param timeOut
	 */
	public void setTimeOut(int timeOut) {
		this.timeOut = timeOut;
	}

	public WebDriver getDriver() {
		return driver;
	}



	public void setDriver(WebDriver driver) {
		this.driver = driver;
	}

	public void refreshDriver() throws InterruptedException {
		Thread.sleep(3000);
		getDriver().get(urlBase);
	}

	public String buscaElementoParametro(String pNombre) {
		String valor;
		try {
			valor = AdminParam.resultado.get(pNombre).toString();
		} catch (Exception e) {
			// TODO: handle exception
			valor = "";
		}
		return valor;
	}

	public String buscaElementoParametroContiene(String pNombre) {
		String valor = "";
		try {
			for (Object key : AdminParam.resultado.keySet()) {
				if (key.toString().contains(pNombre)) {

					valor = AdminParam.resultado.get(key.toString()).toString();
					break;
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			valor = "";
		}
		return valor;
	}



	public boolean EsperaElemento(WebDriver driver, By elemento) {
		WebDriverWait wait = new WebDriverWait(driver, nTiempo);
		WebElement elementoVisible = null;

		try {
			elementoVisible = wait.until(ExpectedConditions.visibilityOfElementLocated(elemento));
		} catch (Exception e) {
			// TODO: handle exception
		}

		if (elementoVisible == null) {
			return false;
		} else {
			return true;
		}
	}

	public boolean EsperaElemento(WebDriver driver, By elemento, int pintentos) throws InterruptedException {
		boolean encontrado = false;
		int intento = 1;
		WebElement elementoVisible = null;
		while (!encontrado && intento <= pintentos) {
			WebDriverWait wait = new WebDriverWait(driver, 2);
			try {
				elementoVisible = wait.until(ExpectedConditions.visibilityOfElementLocated(elemento));
			} catch (Exception e) {
				// TODO: handle exception
			}

			if (elementoVisible == null) {
				encontrado = false;
				intento++;
			} else {
				encontrado = true;
			}

		}
		return encontrado;
	}

	public boolean EsperaDesapareceElemento(WebDriver driver, By elemento, int intentos) {
		boolean existe = true;
		int i = 0;
		while (existe && i <= intentos) {
			try {
				if (driver.findElement(elemento).isDisplayed() && driver.findElement(elemento).isEnabled()) {
					Thread.sleep(5000);
					i++;
				} else {
					existe = false;
				}

				if (intentos == i) {
					i++;
				}
			} catch (Exception e) {
				i = intentos + 1;
				existe = false;
			}
		}
		return existe;
	}

	public boolean EsperaDesapareceElemento(WebElement elemento, int intentos) {
		boolean existe = true;
		int i = 0;
		while (existe && i <= intentos) {
			try {
				if (elemento.isDisplayed() && elemento.isEnabled()) {
					Thread.sleep(5000);
					i++;
				} else {
					existe = false;
				}

				if (intentos == i) {
					throw new Exception("Elemento no se encuentra desplegado o tarda en desaparecer.");
				}
			} catch (Exception e) {
				i = intentos + 1;
				existe = false;
			}
		}
		return existe;
	}

	public void cambiarVentana() throws InterruptedException {
		Set<String> allHandles = getDriver().getWindowHandles();

		allHandles.remove(allHandles.iterator().next());

		String lastHandle = allHandles.iterator().next();
		lastHandle = allHandles.iterator().next();

		getDriver().switchTo().window(lastHandle);

	}

	public void cambiarVentanaAnterior() throws InterruptedException {
		for (String handle : getDriver().getWindowHandles()) {
			getDriver().switchTo().window(handle);
			getDriver().manage().window().maximize();
		}

	}

	public String obtenerValorProperties(String nombreVariabe) throws IOException {
		InputStream entrada = new FileInputStream("Config.properties");
		prop.load(entrada);
		return prop.getProperty(nombreVariabe);
	}

	public void ubicarFrame(String cIframe) throws InterruptedException {
		boolean encontrouFrame = false;

		for (int i = 0; i < 10; i++) {
			Thread.sleep(1000);
			try {
				driver.switchTo().defaultContent();
				driver.switchTo().frame(driver.findElement(By.xpath("//iframe[@innerid='" + cIframe + "']")));
				encontrouFrame = true;
				break;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (!encontrouFrame) {
			// EFA.cv_onError = "Frame "+cIframe+" was not found.";
			throw new RuntimeException("El siguiente Frame: " + cIframe + " no fue encontrado en el entorno.");
		}
	}

	public void bajarScroll(String pCantPixels) {

		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("window.scrollBy(0," + pCantPixels + ")", "");
	}

	public void ubicarIdFrame(String idFrame) throws InterruptedException {
		By frame = By.xpath("//*[@id='" + idFrame + "']");
		Thread.sleep(1000);
		EsperaElemento(driver, frame);
		driver.switchTo().frame(driver.findElement(frame));

	}

	public boolean validaRangoFecha(String pFecha, String pFormato, int pTiempo) throws ParseException {
		DateFormat format = new SimpleDateFormat(pFormato);
		Date fAplicacion = format.parse(pFecha);
		Date fEjecucion = new Date();

		long minutosDiferencia = (fEjecucion.getTime() - fAplicacion.getTime()) / 60000;

		if (minutosDiferencia < (long) pTiempo && minutosDiferencia >= 0
				&& fAplicacion.getMonth() == fEjecucion.getMonth() && fAplicacion.getDay() == fEjecucion.getDay()
				&& fAplicacion.getYear() == fAplicacion.getYear()) {
			return true;
		} else {
			return false;
		}
	}

	public void cambiarPestana(int pNumPestana) {
		ArrayList<String> tabs;
		tabs = new ArrayList<String>(driver.getWindowHandles());
		driver.switchTo().window(tabs.get(pNumPestana));
	}

	public boolean validarTextElemento(By pElemento, String pPuntoControl, String pMensajeExitoso, String pMensajeError)
			throws IOException {

		boolean vRespuesta = false;

		EsperaElemento(driver, pElemento);

		String txtObjeto = driver.findElement(pElemento).getText();

		if (txtObjeto.equals(pPuntoControl)) {

			vRespuesta = true;

			AdminDocPdf.generaEvidencia("Validaci�n Exitosa ->" + pMensajeExitoso + " parametro " + "(" + pPuntoControl
					+ ") y dato retornado en el BES (" + txtObjeto + ")");

		}

		else {

			AdminDocPdf.generaEvidencia("Error en Validaci�n -> " + pMensajeError + " parametro " + " (" + pPuntoControl
					+ ") y dato retornado en el BES (" + txtObjeto + ")");

			AdminDocPdf.setEstado(Estados.FAILED);

		}

		return vRespuesta;
	}

	public void removerPropiedadElementoWeb(By pElemento, String pAtributo) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		WebElement element = driver.findElement(pElemento);

		js.executeScript("arguments[0].removeAttribute('" + pAtributo + "')", element);
	}

	public String calcularFechaPorDias(Date fecha, int dias) {
		if (dias == 0)
			return fecha.toString();
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");
		calendar.setTime(fecha);
		calendar.add(Calendar.DAY_OF_YEAR, dias);
		Date date = calendar.getTime();
		return formateador.format(date);
	}

	public void BajarScrollElemento(By elemento) {
		WebElement DIVelement = driver.findElement(elemento);
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("arguments[0].scrollIntoView(true)", DIVelement);
	}

	public String obtenerCodigoVerificacion(String linea) throws IOException, InterruptedException {
		String Codigo = "";
		String[] arrLinea = linea.split(" ");
		for (int i = 0; i < arrLinea.length; i++) {
			if (tryParseInt(arrLinea[i]) && arrLinea[i].length() > 3) {
				Codigo = arrLinea[i];
			}
		}
		return Codigo;
	}

	public void ubicarElementoPantalla(By pElemento) {
		try {
			if (EsperaElemento(driver, pElemento)) {
				new Actions(driver).moveToElement(driver.findElement(pElemento)).build().perform();
			} else {
				System.out.println("No se encuentra el elemento no se encuentra visible o no existe");
			}

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Excepcion controlada al intentar ubicar elemento en pantalla.");
		}

	}

	boolean tryParseInt(String value) {
		try {
			Integer.parseInt(value);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	public String encriptar(String s) throws UnsupportedEncodingException {
		return Base64.getEncoder().encodeToString(s.getBytes("utf-8"));
	}

	public String desencriptar(String s) throws UnsupportedEncodingException {
		byte[] decode = Base64.getDecoder().decode(s.getBytes());
		return new String(decode, "utf-8");
	}

	public boolean validaDosFechas(String pFechaInicio, String pFormato, String pFechaFinal) throws ParseException {
		DateFormat format = new SimpleDateFormat(pFormato);
		Date fInicio = format.parse(pFechaInicio);
		Date fFinal = format.parse(pFechaFinal);

		if (fInicio.getTime() < fFinal.getTime() && fInicio.getMonth() == fFinal.getMonth()
				&& fInicio.getDay() == fFinal.getDay() && fInicio.getYear() == fInicio.getYear()) {

			return true;

		} else {
			return false;
		}
	}
/*
	public void desplazarElementoIzquierda(WebDriver driver, int cantidad, int y_start) {
		Dimension size = null;
		int x_start = 0;
		int x_end = 0;

		((AndroidDriver) driver).context("NATIVE_APP");
		size = driver.manage().window().getSize();
		((AndroidDriver) driver).context("WEBVIEW");

		for (int i = 0; i < cantidad; i++) {

			((AndroidDriver) driver).context("NATIVE_APP");
			x_start = (int) (size.width * 0.10);
			x_end = (int) (size.width * 0.60);

			TouchAction action = new TouchAction((AndroidDriver) driver);
			action.press(PointOption.point(x_start, y_start))
					.waitAction(WaitOptions.waitOptions(Duration.ofMillis(2000)))
					.moveTo(PointOption.point(x_end, y_start)).release().perform();

			((AndroidDriver) driver).context("WEBVIEW");
		}
	}

	public void desplazarPantallaIzquierda(WebDriver driver, int cantidad) {
		Dimension size = null;
		int x_start = 0;
		int x_end = 0;

		((AndroidDriver) driver).context("NATIVE_APP");
		size = driver.manage().window().getSize();
		((AndroidDriver) driver).context("WEBVIEW");

		for (int i = 0; i < cantidad; i++) {

			((AndroidDriver) driver).context("NATIVE_APP");
			x_start = (int) (size.width * 0.10);
			x_end = (int) (size.width * 0.60);
			int y = size.height / 2;

			TouchAction action = new TouchAction((AndroidDriver) driver);
			action.press(PointOption.point(x_start, y)).waitAction(WaitOptions.waitOptions(Duration.ofMillis(2000)))
					.moveTo(PointOption.point(x_end, y)).release().perform();

			((AndroidDriver) driver).context("WEBVIEW");
		}
	}

	public void desplazarPantalla(int xStart, int xEnd, double yStartPercent, double yEndPercent, int cantidad) {
		Dimension size = null;
		int y_start = 0;
		int y_end = 0;

		((AndroidDriver) driver).context("NATIVE_APP");
		size = driver.manage().window().getSize();

		for (int i = 0; i < cantidad; i++) {
			y_start = (int) (size.height * yStartPercent);
			y_end = (int) (size.height * yEndPercent);
			TouchAction action = new TouchAction((AndroidDriver) driver);
			action.press(PointOption.point(xStart, y_start))
					.waitAction(WaitOptions.waitOptions(Duration.ofMillis(3000))).moveTo(PointOption.point(xEnd, y_end))
					.release().perform();

		}
		((AndroidDriver) driver).context("WEBVIEW");
	}

	public void desplazarPantalla(int xStart, int xEnd, int yStart, int yEnd, int cantidad) {

		((AndroidDriver) driver).context("NATIVE_APP");

		for (int i = 0; i < cantidad; i++) {
			TouchAction action = new TouchAction((AndroidDriver) driver);
			action.press(PointOption.point(xStart, yStart)).waitAction(WaitOptions.waitOptions(Duration.ofMillis(3000)))
					.moveTo(PointOption.point(xEnd, yEnd)).release().perform();

		}
		((AndroidDriver) driver).context("WEBVIEW");
	}

	public boolean esperaElementoClickleable(By pElemento, int pIntentos) {
		boolean encontrado = false;
		int intento = 1;
		WebElement elementoClickelable = null;
		while (!encontrado && intento <= pIntentos) {
			WebDriverWait wait = new WebDriverWait(driver, 2);
			try {
				elementoClickelable = wait.until(ExpectedConditions.elementToBeClickable(pElemento));
				elementoClickelable = wait.until(ExpectedConditions.elementToBeClickable(pElemento));
			} catch (Exception e) {
				// TODO: handle exception
			}

			if (elementoClickelable == null) {
				encontrado = false;
				intento++;
			} else {
				encontrado = true;
			}

		}
		return encontrado;

	}

	public void doClick(By pElemento) throws Exception {
		try {
		WebDriverWait wait = new WebDriverWait(driver, 3);
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(pElemento));
		
		if (driver.findElement(pElemento).isEnabled()) {
			driver.findElement(pElemento).click();
		}
		else
		{
			throw new Exception("El elemento no se encuentra disponible para realizar Click.");
		}
		}
		catch (Exception e) {
			// TODO: handle exception
			throw new Exception("Ocurrio un erro al realizar Click: " + e.getMessage());
		}
	}
*/
	public void doSendKeys(By pElemento, String pTexto) throws Exception {
		try {
		WebDriverWait wait = new WebDriverWait(driver, 3);
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(pElemento));
		
		if (driver.findElement(pElemento).isEnabled()) {
			driver.findElement(pElemento).sendKeys(pTexto);
		}
		else
		{
			throw new Exception("El elemento no se encuentra disponible para ingresar texto.");
		}
		}
		catch (Exception e) {
			// TODO: handle exception
			throw new Exception("Ocurrio un erro al intentar ingresar Texto: " + e.getMessage());
		}
	}
	
	public void doSelectVisibleText(By pElemento, String pOpcion) throws Exception {
		try {
		WebDriverWait wait = new WebDriverWait(driver, 3);
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(pElemento));
		
		if (driver.findElement(pElemento).isEnabled()) {
			new Select(driver.findElement(pElemento)).selectByVisibleText(pOpcion);
		}
		else
		{
			throw new Exception("El elemento no se encuentra disponible para seleccionar un valor.");
		}
		}
		catch (Exception e) {
			// TODO: handle exception
			throw new Exception("Ocurrio un erro al intentar seleccionar una opcion: " + e.getMessage());
		}
	}

}
