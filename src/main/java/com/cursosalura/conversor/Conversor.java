package com.cursosalura.conversor;

import com.google.gson.Gson;
import io.github.cdimascio.dotenv.Dotenv; // Asegúrate de importar Dotenv

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class Conversor {
    private final Dotenv dotenv; // Declarar dotenv
    private final String API_URL; // Declarar API_URL

    // Constructor
    public Conversor() {
        dotenv = Dotenv.load(); // Inicializar dotenv
        API_URL = "https://v6.exchangerate-api.com/v6/" + dotenv.get("API_KEY") + "/latest/"; // Inicializar API_URL
    }

    public Map<String, Double> obtenerTasasFiltradas(String monedaBase, String[] codigosMonedas) {
        try {
            // Llamada a la API
            URL url = new URL(API_URL + monedaBase);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // Parsear la respuesta JSON
            Gson gson = new Gson();
            TasaCambio tasaCambio = gson.fromJson(response.toString(), TasaCambio.class);

            // Obtener las tasas filtradas
            return tasaCambio.getConversionRates(); // O ajusta según tu lógica
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void mostrarTasas(Map<String, Double> tasasFiltradas) {
        System.out.println("Tasas de cambio:");
        for (Map.Entry<String, Double> entrada : tasasFiltradas.entrySet()) {
            System.out.printf("%s: %.4f%n", entrada.getKey(), entrada.getValue());
        }
    }

    public double convertir(double monto, String monedaOrigen, String monedaDestino, Map<String, Double> tasasFiltradas) {
        // Verifica que las monedas existan en las tasas filtradas
        if (!tasasFiltradas.containsKey(monedaOrigen) || !tasasFiltradas.containsKey(monedaDestino)) {
            System.out.println("Una de las monedas no es válida.");
            return 0.0;
        }

        // Obtiene las tasas de cambio
        double tasaOrigen = tasasFiltradas.get(monedaOrigen);
        double tasaDestino = tasasFiltradas.get(monedaDestino);

        // Realiza la conversión
        double montoEnBase = monto / tasaOrigen; // Convierte el monto a la moneda base
        double montoConvertido = montoEnBase * tasaDestino; // Convierte a la moneda de destino

        return montoConvertido;
    }

    public void mostrarTiposMoneda() {
        // Lista de monedas para mostrar al usuario
        System.out.println("Tipos de moneda disponibles:");
        System.out.println("ARS - Peso Argentino");
        System.out.println("BOB - Boliviano Boliviano");
        System.out.println("BRL - Real Brasileño");
        System.out.println("CLP - Peso Chileno");
        System.out.println("COP - Peso Colombiano");
        System.out.println("USD - Dólar Estadounidense");
        System.out.println("DOP - Peso Dominicano");
        System.out.println("EUR - Euro");
    }
}
