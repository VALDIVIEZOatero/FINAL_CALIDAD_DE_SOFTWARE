package pe.edu.idat.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class LoginPage {
    private WebDriver driver;
    private WebDriverWait wait;

    private By btnIconoUsuario = By.xpath("//a[@aria-label='Usuario']");
    private By txtUsername = By.id("username");
    private By txtPassword = By.id("password");
    private By btnLogin = By.name("login");

    // Selector flexible para capturar el error de Pepito
    private By lblError = By.xpath("//ul[contains(@class, 'woocommerce-error')] | //div[contains(@class, 'woocommerce-notices-wrapper')]");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    public void irALogin() {
        WebElement icono = wait.until(ExpectedConditions.visibilityOfElementLocated(btnIconoUsuario));
        try {
            wait.until(ExpectedConditions.elementToBeClickable(btnIconoUsuario)).click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", icono);
        }
    }

    public void login(String usuario, String clave) {
        WebElement userField = wait.until(ExpectedConditions.visibilityOfElementLocated(txtUsername));
        userField.clear();
        userField.sendKeys(usuario);

        WebElement passField = driver.findElement(txtPassword);
        passField.clear();
        passField.sendKeys(clave);

        WebElement loginBtn = driver.findElement(btnLogin);
        try {
            loginBtn.click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", loginBtn);
        }

        try { Thread.sleep(2000); } catch (InterruptedException e) {}
    }

    // Este método es el que hace que el test de Pepito sea EXITOSO
    public boolean esVisibleMensajeError() {
        try {
            WebDriverWait waitErr = new WebDriverWait(driver, Duration.ofSeconds(15));
            WebElement errorElement = waitErr.until(ExpectedConditions.visibilityOfElementLocated(lblError));
            return errorElement.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}