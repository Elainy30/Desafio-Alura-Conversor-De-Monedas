package com.cursosalura.conversor;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class ConversorTest {

    @Test
    public void testConvertir() {
        Conversor conversor = new Conversor(); // Crear instancia de Conversor

        // Preparar tasas de cambio
        Map<String, Double> tasas = new HashMap<>();
        tasas.put("USD", 1.0);
        tasas.put("EUR", 0.85);
        tasas.put("DOP", 58.0);

        // Convertir 100 USD a EUR
        double resultadoEUR = conversor.convertir(100, "USD", "EUR", tasas);
        Assertions.assertEquals(85.0, resultadoEUR, 0.01); // 100 USD = 85 EUR (con un error de 0.01)

        // Convertir 100 USD a DOP
        double resultadoDOP = conversor.convertir(100, "USD", "DOP", tasas);
        Assertions.assertEquals(5800.0, resultadoDOP, 0.01); // 100 USD = 5800 DOP
    }

    @Test
    public void testObtenerTasasFiltradas() {
        Conversor conversor = new Conversor(); // Crear instancia de Conversor

        String[] codigosMonedas = {"USD", "EUR", "DOP"};
        String monedaBase = "USD";

        Map<String, Double> tasasFiltradas = conversor.obtenerTasasFiltradas(monedaBase, codigosMonedas);

        // Verificar que se obtienen las tasas de cambio
        Assertions.assertNotNull(tasasFiltradas);
        Assertions.assertTrue(tasasFiltradas.containsKey("EUR"));
        Assertions.assertTrue(tasasFiltradas.containsKey("DOP"));
    }
}
