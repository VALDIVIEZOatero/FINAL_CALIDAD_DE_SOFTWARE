package pe.edu.idat.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class Checkoutpage {
    private WebDriver driver;
    private WebDriverWait wait;

    public Checkoutpage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    public void llenarDatosPersonales(String email, String nom, String ape, String dni, String tel) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("billing_email"))).sendKeys(email);
        driver.findElement(By.id("billing_first_name")).sendKeys(nom);
        driver.findElement(By.id("billing_last_name")).sendKeys(ape);
        driver.findElement(By.id("billing_dni")).sendKeys(dni);
        driver.findElement(By.id("billing_phone")).sendKeys(tel);
    }

    public void seleccionarUbicacionCompleta(String dep, String prov, String dist) {
        forzarSeleccion("billing_state", dep);
        forzarSeleccion("billing_city", prov);
        forzarSeleccion("billing_address_text", dist);
    }

    private void forzarSeleccion(String id, String valor) {
        try {
            WebElement dropdown = wait.until(ExpectedConditions.presenceOfElementLocated(By.id(id)));
            ((JavascriptExecutor) driver).executeScript(
                    "var select = arguments[0]; " +
                            "for(var i=0; i<select.options.length; i++){ " +
                            "  if(select.options[i].text.toUpperCase().includes(arguments[1].toUpperCase())){ " +
                            "    select.selectedIndex = i; " +
                            "    select.dispatchEvent(new Event('change')); " +
                            "    break; " +
                            "  } " +
                            "}", dropdown, valor);
            Thread.sleep(1500);
        } catch (Exception e) {
            System.out.println("Aviso: Error en selección de " + id);
        }
    }

    public void llenarDireccionYReferencia(String dir, String ref) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("billing_address_1"))).sendKeys(dir);
        driver.findElement(By.id("billing_address_2")).sendKeys(ref);
    }

    public void procederAlPago() {
        WebElement btn = driver.findElement(By.id("place_order"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn);
    }
}