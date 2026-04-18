package pe.edu.idat.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class ListadoPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Localizadores
    private By btnNuevo = By.xpath("//a[@href='/tienda/?orderby=date' and contains(@class, 'ekit-menu-nav-link')]");
    private By miniaturaZapatilla = By.xpath("(//div[contains(@class, 'wvg-gallery-thumbnail-image')]//img)[2]");

    // Selector del botón añadir
    private By btnAnadirCarrito = By.cssSelector("button.single_add_to_cart_button");

    // NUEVO SELECTOR: Basado en tu captura F12 del mini-carrito lateral
    private By btnFinalizarCompraPanel = By.cssSelector("a.elementor-button--checkout");

    public ListadoPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    public void hacerClickEnNuevo() {
        wait.until(ExpectedConditions.elementToBeClickable(btnNuevo)).click();
    }

    public void seleccionarProductoPorNombre(String nombreProducto) {
        By productoLocator = By.xpath("//h2[contains(text(), '" + nombreProducto + "')]");
        wait.until(ExpectedConditions.elementToBeClickable(productoLocator)).click();
    }

    public void verMiniaturaZapatilla() {
        wait.until(ExpectedConditions.elementToBeClickable(miniaturaZapatilla)).click();
    }

    public void seleccionarTallaYAnadir(String talla) {
        // 1. Seleccionar talla
        By tallaLocator = By.xpath("//span[text()='" + talla + "']");
        wait.until(ExpectedConditions.elementToBeClickable(tallaLocator)).click();

        // 2. Espera para que el botón se habilite
        try { Thread.sleep(2000); } catch (InterruptedException e) { e.printStackTrace(); }

        // 3. Clic en Añadir al Carrito
        WebElement botonAnadir = wait.until(ExpectedConditions.elementToBeClickable(btnAnadirCarrito));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", botonAnadir);
    }

    public void clickFinalizarEnPanel() {
        // Espera a que el panel lateral cargue y el botón sea visible
        WebElement btnFinalizar = wait.until(ExpectedConditions.visibilityOfElementLocated(btnFinalizarCompraPanel));

        // Usamos JavaScript para el clic porque a veces el overlay bloquea el clic normal de Selenium
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btnFinalizar);
    }
}