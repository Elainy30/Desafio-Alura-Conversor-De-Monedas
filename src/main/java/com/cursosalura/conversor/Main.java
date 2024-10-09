package com.cursosalura.conversor;

import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Conversor conversor = new Conversor(); // Crear una instancia de Conversor
        String monedaBase = "USD"; // Cambia esto por la moneda base que quieras
        String[] codigosMonedas = {"ARS", "BOB", "BRL", "CLP", "COP", "USD", "DOP", "EUR"}; // Códigos de moneda a filtrar
        Map<String, Double> tasasFiltradas = conversor.obtenerTasasFiltradas(monedaBase, codigosMonedas);

        if (tasasFiltradas == null) {
            System.out.println("No se pudieron obtener las tasas de cambio.");
            scanner.close();
            return;
        }

        boolean continuar = true;

        while (continuar) {
            System.out.println("\n*** Menú de Conversor de Monedas ***");
            System.out.println("1. Ver tasas de cambio");
            System.out.println("2. Convertir monedas");
            System.out.println("3. Ver tipos de moneda");
            System.out.println("4. Salir");
            System.out.print("Seleccione una opción: ");

            // Manejo de errores al leer la opción
            int opcion = 0;
            try {
                opcion = scanner.nextInt();
            } catch (Exception e) {
                System.out.println("Por favor, ingrese un número válido.");
                scanner.next(); // Limpiar el buffer del scanner
                continue; // Volver al inicio del bucle
            }

            switch (opcion) {
                case 1:
                    conversor.mostrarTasas(tasasFiltradas);
                    break;
                case 2:
                    realizarConversion(scanner, conversor, tasasFiltradas);
                    break;
                case 3:
                    conversor.mostrarTiposMoneda();
                    break;
                case 4:
                    continuar = false;
                    break;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
        }
        System.out.println("Gracias por usar el conversor de monedas. ¡Hasta luego!");
        scanner.close();
    }

    public static void realizarConversion(Scanner scanner, Conversor conversor, Map<String, Double> tasasFiltradas) {
        System.out.print("Ingrese el monto a convertir: ");
        double monto = 0;

        // Manejo de errores al leer el monto
        try {
            monto = scanner.nextDouble();
        } catch (Exception e) {
            System.out.println("Por favor, ingrese un número válido para el monto.");
            scanner.next(); // Limpiar el buffer del scanner
            return; // Salir de la función
        }

        System.out.print("Ingrese el código de la moneda de origen: ");
        String monedaOrigen = scanner.next().toUpperCase();
        System.out.print("Ingrese el código de la moneda de destino: ");
        String monedaDestino = scanner.next().toUpperCase();

        double resultado = conversor.convertir(monto, monedaOrigen, monedaDestino, tasasFiltradas);
        if (resultado > 0) {
            System.out.printf("El monto convertido de %s a %s es: %.2f%n", monedaOrigen, monedaDestino, resultado);
        } else {
            System.out.println("La conversión no se pudo realizar.");
        }
    }
}
