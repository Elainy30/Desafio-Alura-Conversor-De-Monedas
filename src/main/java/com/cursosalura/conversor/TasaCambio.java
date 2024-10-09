package com.cursosalura.conversor;

import java.util.HashMap;
import java.util.Map;

public class TasaCambio {
    private String result;          // Resultado de la consulta
    private String base_code;       // Moneda base
    private String fecha;           // Fecha de las tasas
    private Map<String, Double> conversion_rates; // Tasas de cambio

    // Constructor
    public TasaCambio(String result, String base_code, String fecha, Map<String, Double> conversion_rates) {
        this.result = result;
        this.base_code = base_code;
        this.fecha = fecha;
        this.conversion_rates = conversion_rates;
    }

    // Getters
    public String getResult() {
        return result;
    }

    public String getBaseCode() {
        return base_code;
    }

    public String getFecha() {
        return fecha;
    }

    public Map<String, Double> getConversionRates() {
        return conversion_rates;
    }

    // Metodo para obtener la tasa de cambio para una moneda específica
    public Double obtenerTasa(String monedaDestino) {
        return conversion_rates.get(monedaDestino);
    }

    // Metodo para obtener tasas filtradas según códigos de moneda
    public Map<String, Double> obtenerTasasFiltradas(String[] codigosMonedas) {
        Map<String, Double> tasasFiltradas = new HashMap<>();
        for (String codigo : codigosMonedas) {
            if (conversion_rates.containsKey(codigo)) {
                tasasFiltradas.put(codigo, conversion_rates.get(codigo));
            }
        }
        return tasasFiltradas;
    }
}
