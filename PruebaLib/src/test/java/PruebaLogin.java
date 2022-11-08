import Utilidades.Edwin.evidencia.doc.pdf.AdminDocPdf;
import Utilidades.Edwin.model.Ambientes;
import Utilidades.Edwin.model.Estados;
import Utilidades.Edwin.model.Navegadores;
import com.assertthat.selenium_shutterbug.core.Shutterbug;
import com.lowagie.text.DocumentException;
import io.github.bonigarcia.wdm.WebDriverManager;
import objetosPG.LoginPage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;


import java.io.IOException;

import static Utilidades.Edwin.model.DispositivoPrueba.WEB;

public class PruebaLogin {
    WebDriver driver;
    LoginPage objlogin;


    @BeforeTest
    public void setup() {
        WebDriverManager.chromedriver().setup();

        driver = new ChromeDriver();
        driver.get("file:///D:/Repositorio/PGPrueba/Untitled-1.html");
    }
    @Test
    public void testLoginCorrect() throws IOException, DocumentException {
        objlogin = new LoginPage(driver);
        String title = objlogin.getTitulo();
        Assert.assertTrue(title.toLowerCase().contains("pom"));
        AdminDocPdf objAdminDocPdf = new AdminDocPdf(Ambientes.NA, Navegadores.CHROME,WEB,"Data");
        objlogin.asignarNombre("Miller");

        objAdminDocPdf.generaEvidencia("Ingreso de valores para el login",Shutterbug.shootPage(driver).getImage());
        objlogin.asignarPassword("123456");
        objAdminDocPdf.generaEvidencia("Ingreso de valores para el login",Shutterbug.shootPage(driver).getImage());
        objAdminDocPdf.crearDocumento(Estados.SUCCESS);
    }
}