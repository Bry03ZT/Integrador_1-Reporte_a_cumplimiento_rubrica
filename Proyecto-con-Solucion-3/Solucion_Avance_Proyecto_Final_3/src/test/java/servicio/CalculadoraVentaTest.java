package servicio;

import org.junit.Assert;
import org.junit.Test;
import servicio.CalculadoraVentaService;

/**
 * Pruebas automatizadas para demostrar el ciclo TDD (Rojo, Verde, Refactor).
 *
 * CICLO TDD APLICADO:
 *  - FASE ROJO   : Se escribe cada @Test ANTES de implementar el método.
 *                  El código ni compila o la aserción falla → ROJO.
 *  - FASE VERDE  : Se implementa el mínimo código en CalculadoraVentaService
 *                  para que la prueba pase → VERDE.
 *  - FASE REFACTOR: Se limpia y optimiza el código sin romper ningún test.
 */
public class CalculadoraVentaTest {

    // =========================================================
    // BLOQUE 1: PRUEBAS DE calcularDescuento()
    // Al menos 2 pruebas por cada regla de negocio (TDD)
    // =========================================================

    /*
     * FASE ROJO → VERDE
     * Regla: subtotal > 5000 → descuento del 10%
     */
    @Test
    public void testDescuento10PorcientoConSubtotal6000() {
        // Arrange
        CalculadoraVentaService calc = new CalculadoraVentaService();
        double subtotal = 6000.0;

        // Act
        double descuento = calc.calcularDescuento(subtotal);

        // Assert — Esperamos el 10% de 6000 = 600
        Assert.assertEquals(600.0, descuento, 0.001);
    }

    @Test
    public void testDescuento10PorcientoConSubtotal8000() {
        // Arrange
        CalculadoraVentaService calc = new CalculadoraVentaService();
        double subtotal = 8000.0;

        // Act
        double descuento = calc.calcularDescuento(subtotal);

        // Assert — Esperamos el 10% de 8000 = 800
        Assert.assertEquals(800.0, descuento, 0.001);
    }

    /*
     * FASE ROJO → VERDE
     * Regla: subtotal > 2000 y <= 5000 → descuento del 5%
     */
    @Test
    public void testDescuento5PorcientoConSubtotal3000() {
        // Arrange
        CalculadoraVentaService calc = new CalculadoraVentaService();
        double subtotal = 3000.0;

        // Act
        double descuento = calc.calcularDescuento(subtotal);

        // Assert — Esperamos el 5% de 3000 = 150
        Assert.assertEquals(150.0, descuento, 0.001);
    }

    @Test
    public void testDescuento5PorcientoConSubtotal5000() {
        // Arrange
        CalculadoraVentaService calc = new CalculadoraVentaService();
        double subtotal = 5000.0; // Exactamente en el límite → 5% (no supera 5000)

        // Act
        double descuento = calc.calcularDescuento(subtotal);

        // Assert — Esperamos el 5% de 5000 = 250
        Assert.assertEquals(250.0, descuento, 0.001);
    }

    /*
     * FASE ROJO → VERDE
     * Regla: subtotal <= 2000 → sin descuento (0%)
     */
    @Test
    public void testSinDescuentoConSubtotal1000() {
        // Arrange
        CalculadoraVentaService calc = new CalculadoraVentaService();
        double subtotal = 1000.0;

        // Act
        double descuento = calc.calcularDescuento(subtotal);

        // Assert — Sin descuento → 0.0
        Assert.assertEquals(0.0, descuento, 0.001);
    }

    @Test
    public void testSinDescuentoConSubtotalCero() {
        // Arrange — Caso borde: venta con subtotal 0
        CalculadoraVentaService calc = new CalculadoraVentaService();
        double subtotal = 0.0;

        // Act
        double descuento = calc.calcularDescuento(subtotal);

        // Assert — Sin descuento → 0.0
        Assert.assertEquals(0.0, descuento, 0.001);
    }

    // =========================================================
    // BLOQUE 2: PRUEBAS DE calcularTotalFinal()
    // Al menos 2 pruebas (FASE REFACTOR — código ya limpio)
    // =========================================================

    /*
     * FASE REFACTOR
     * Verificamos que el total final = subtotal - descuento
     * con múltiples escenarios para asegurar consistencia
     */
    @Test
    public void testTotalFinalConDescuentoAlto() {
        // Arrange
        CalculadoraVentaService calc = new CalculadoraVentaService();
        double subtotal = 6000.0; // Descuento 10% = 600

        // Act
        double totalFinal = calc.calcularTotalFinal(subtotal);

        // Assert — 6000 - 600 = 5400
        Assert.assertEquals(5400.0, totalFinal, 0.001);
    }

    @Test
    public void testTotalFinalConDescuentoMedio() {
        // Arrange
        CalculadoraVentaService calc = new CalculadoraVentaService();
        double subtotal = 3000.0; // Descuento 5% = 150

        // Act
        double totalFinal = calc.calcularTotalFinal(subtotal);

        // Assert — 3000 - 150 = 2850
        Assert.assertEquals(2850.0, totalFinal, 0.001);
    }

    @Test
    public void testTotalFinalSinDescuento() {
        // Arrange
        CalculadoraVentaService calc = new CalculadoraVentaService();
        double subtotal = 500.0; // Sin descuento

        // Act
        double totalFinal = calc.calcularTotalFinal(subtotal);

        // Assert — 500 - 0 = 500
        Assert.assertEquals(500.0, totalFinal, 0.001);
    }
}
