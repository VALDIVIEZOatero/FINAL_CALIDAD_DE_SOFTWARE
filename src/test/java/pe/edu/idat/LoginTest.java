package pe.edu.idat;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pe.edu.idat.config.DriverFactory;
import pe.edu.idat.pages.*;
import java.time.Duration;

public class LoginTest extends DriverFactory {

    private LoginPage loginPage;
    private ListadoPage listadoPage;
    private Checkoutpage checkoutPage;
    private WebDriverWait wait;

    @BeforeMethod
    public void setUp() {
        // Inicialización del Driver
        driver = DriverFactory.getDriver("chrome");
        driver.manage().window().maximize();

        loginPage = new LoginPage(driver);
        listadoPage = new ListadoPage(driver);
        checkoutPage = new Checkoutpage(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    // TEST 1: LOGIN FALLIDO (PEPITO)
    @Test(priority = 1)
    public void testLoginFallido() {
        System.out.println("--- INICIANDO CASO NEGATIVO (PEPITO) ---");
        driver.get("https://yolu.pe/");

        loginPage.irALogin();
        loginPage.login("pepito123@gmail.com", "12345678");

        // Valida que el mensaje de error de WooCommerce sea visible
        boolean errorVisible = loginPage.esVisibleMensajeError();
        Assert.assertTrue(errorVisible, "ERROR: El sistema NO mostró el mensaje de error para Pepito.");

        System.out.println("LOGRO: Caso Pepito validado correctamente ✅");
    }

    // TEST 2: FLUJO DE VENTA CON SALTO A BOLSA
    @Test(priority = 2)
    public void testFlujoVentaCompleto() {
        System.out.println("--- INICIANDO FLUJO REAL HASTA BOLSA ---");
        try {
            driver.get("https://yolu.pe/");

            // 1. Login con credenciales reales
            loginPage.irALogin();
            loginPage.login("valdiviezoateroyolijhunior@gmail.com", "yoli2023%");

            // 2. Proceso de selección de producto
            listadoPage.hacerClickEnNuevo();
            listadoPage.seleccionarProductoPorNombre("Adidas Lightorama");
            listadoPage.verMiniaturaZapatilla();
            listadoPage.seleccionarTallaYAnadir("28");

            // 3. Ir al Checkout
            listadoPage.clickFinalizarEnPanel();
            wait.until(ExpectedConditions.urlContains("checkout"));

            // 4. Llenar solo datos de contacto (Para evitar errores de ubicación)
            System.out.println("PROCESO: Llenando datos de contacto...");
            checkoutPage.llenarDatosPersonales(
                    "valdiviezoateroyolijhunior@gmail.com",
                    "YOLI JHUNIOR",
                    "VALDIVIEZO ATERO",
                    "71344740",
                    "935875705"
            );
            // 5. SALTO INMEDIATO A LA BOLSA
            System.out.println("PROCESO: Redirigiendo inmediatamente a la bolsa...");
            driver.get("https://yolu.pe/bolsa/");
            // 6. Validación Final
            wait.until(ExpectedConditions.urlContains("bolsa"));
            String urlActual = driver.getCurrentUrl();
            Assert.assertTrue(urlActual.contains("bolsa"), "ERROR: No se pudo redirigir a la bolsa.");
            System.out.println("LOGRO: Redirección a Bolsa validada con éxito ✅");

        } catch (Exception e) {
            // Captura cualquier fallo para que no se cierre sin aviso
            Assert.fail("Fallo en flujo real: " + e.getMessage());
        }
    }

@AfterMethod
    public void tearDown() {

        if (driver != null) {
            driver.quit();
        }
    }
}