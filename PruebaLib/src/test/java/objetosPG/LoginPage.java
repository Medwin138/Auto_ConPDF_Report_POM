package objetosPG;

import Utilidades.Edwin.control.elementos.ObjetosConfigAux;
import Utilidades.Edwin.evidencia.doc.pdf.AdminDocPdf;
import Utilidades.Edwin.model.Ambientes;
import Utilidades.Edwin.model.Navegadores;
import com.assertthat.selenium_shutterbug.core.Shutterbug;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.awt.image.BufferedImage;
import java.io.IOException;

import static Utilidades.Edwin.model.DispositivoPrueba.WEB;


public class LoginPage {

    ObjetosConfigAux objAux;
    AdminDocPdf objAdminDocPdf;

    WebDriver driver;
    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    By nombre = By.name("usuario");
    By password = By.name("pass");
    By titulo = By.xpath("/html/body/h1");
    By btnIngresar = By.xpath("/html/body/div[2]/form/p[3]/button");

    public LoginPage(ObjetosConfigAux objAux) throws IOException {
        this.objAux = objAux;

    }

    public String getTitulo() {
        return driver.findElement(titulo).getText();
    }
    public void asignarNombre(String sNombre) throws IOException {

        driver.findElement(nombre).sendKeys(sNombre);


    }
    public void asignarPassword(String spassword) {
        driver.findElement(password).sendKeys(spassword);
    }
    public void clickBtnIngresar() {
        driver.findElement(btnIngresar).click();
    }


    public void IngresarLogin (String sNombre, String sPassword) throws IOException {
        this.asignarNombre(sNombre);
        this.asignarPassword(sPassword);
        this.clickBtnIngresar();


    }

}
